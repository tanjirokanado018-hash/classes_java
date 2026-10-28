package androidx.constraintlayout.solver;

import androidx.appcompat.graphics.drawable.a;
import java.util.Arrays;
import java.util.HashSet;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class SolverVariable {
    private static final boolean INTERNAL_DEBUG = false;
    static final int MAX_STRENGTH = 9;
    public static final int STRENGTH_BARRIER = 6;
    public static final int STRENGTH_CENTERING = 7;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_FIXED = 8;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static final boolean VAR_USE_HASH = false;
    private static int uniqueConstantId = 1;
    private static int uniqueErrorId = 1;
    private static int uniqueId = 1;
    private static int uniqueSlackId = 1;
    private static int uniqueUnrestrictedId = 1;
    public float computedValue;
    int definitionId;
    float[] goalStrengthVector;
    public int id;
    public boolean inGoal;
    HashSet<ArrayRow> inRows;
    public boolean isFinalValue;
    ArrayRow[] mClientEquations;
    int mClientEquationsCount;
    private String mName;
    Type mType;
    public int strength;
    float[] strengthVector;
    public int usageInRowCount;

    /* JADX INFO: renamed from: androidx.constraintlayout.solver.SolverVariable$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type = iArr;
            try {
                iArr[Type.UNRESTRICTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[Type.CONSTANT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[Type.SLACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[Type.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[Type.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public SolverVariable(Type type, String str) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.strengthVector = new float[9];
        this.goalStrengthVector = new float[9];
        this.mClientEquations = new ArrayRow[16];
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.inRows = null;
        this.mType = type;
    }

    private static String getUniqueName(Type type, String str) {
        StringBuilder sb;
        int i2;
        if (str != null) {
            sb = a.h(str);
            i2 = uniqueErrorId;
        } else {
            int i3 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[type.ordinal()];
            if (i3 == 1) {
                sb = new StringBuilder("U");
                i2 = uniqueUnrestrictedId + 1;
                uniqueUnrestrictedId = i2;
            } else if (i3 == 2) {
                sb = new StringBuilder("C");
                i2 = uniqueConstantId + 1;
                uniqueConstantId = i2;
            } else if (i3 == 3) {
                sb = new StringBuilder("S");
                i2 = uniqueSlackId + 1;
                uniqueSlackId = i2;
            } else if (i3 == 4) {
                sb = new StringBuilder("e");
                i2 = uniqueErrorId + 1;
                uniqueErrorId = i2;
            } else {
                if (i3 != 5) {
                    throw new AssertionError(type.name());
                }
                sb = new StringBuilder("V");
                i2 = uniqueId + 1;
                uniqueId = i2;
            }
        }
        sb.append(i2);
        return sb.toString();
    }

    public static void increaseErrorId() {
        uniqueErrorId++;
    }

    public final void addToRow(ArrayRow arrayRow) {
        int i2 = 0;
        while (true) {
            int i3 = this.mClientEquationsCount;
            if (i2 >= i3) {
                ArrayRow[] arrayRowArr = this.mClientEquations;
                if (i3 >= arrayRowArr.length) {
                    this.mClientEquations = (ArrayRow[]) Arrays.copyOf(arrayRowArr, arrayRowArr.length * 2);
                }
                ArrayRow[] arrayRowArr2 = this.mClientEquations;
                int i4 = this.mClientEquationsCount;
                arrayRowArr2[i4] = arrayRow;
                this.mClientEquationsCount = i4 + 1;
                return;
            }
            if (this.mClientEquations[i2] == arrayRow) {
                return;
            } else {
                i2++;
            }
        }
    }

    public void clearStrengths() {
        for (int i2 = 0; i2 < 9; i2++) {
            this.strengthVector[i2] = 0.0f;
        }
    }

    public String getName() {
        return this.mName;
    }

    public final void removeFromRow(ArrayRow arrayRow) {
        int i2 = this.mClientEquationsCount;
        int i3 = 0;
        while (i3 < i2) {
            if (this.mClientEquations[i3] == arrayRow) {
                while (i3 < i2 - 1) {
                    ArrayRow[] arrayRowArr = this.mClientEquations;
                    int i4 = i3 + 1;
                    arrayRowArr[i3] = arrayRowArr[i4];
                    i3 = i4;
                }
                this.mClientEquationsCount--;
                return;
            }
            i3++;
        }
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.isFinalValue = false;
        int i2 = this.mClientEquationsCount;
        for (int i3 = 0; i3 < i2; i3++) {
            this.mClientEquations[i3] = null;
        }
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.inGoal = false;
        Arrays.fill(this.goalStrengthVector, 0.0f);
    }

    public void setFinalValue(LinearSystem linearSystem, float f2) {
        this.computedValue = f2;
        this.isFinalValue = true;
        int i2 = this.mClientEquationsCount;
        for (int i3 = 0; i3 < i2; i3++) {
            this.mClientEquations[i3].updateFromFinalVariable(linearSystem, this, false);
        }
        this.mClientEquationsCount = 0;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setType(Type type, String str) {
        this.mType = type;
    }

    public String strengthsToString() {
        String strE = this + "[";
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = true;
        while (i2 < this.strengthVector.length) {
            StringBuilder sbH = a.h(strE);
            sbH.append(this.strengthVector[i2]);
            String string = sbH.toString();
            float[] fArr = this.strengthVector;
            float f2 = fArr[i2];
            if (f2 > 0.0f) {
                z2 = false;
            } else if (f2 < 0.0f) {
                z2 = true;
            }
            if (f2 != 0.0f) {
                z3 = false;
            }
            strE = a.e(string, i2 < fArr.length - 1 ? ", " : "] ");
            i2++;
        }
        if (z2) {
            strE = a.e(strE, " (-)");
        }
        return z3 ? a.e(strE, " (*)") : strE;
    }

    public String toString() {
        StringBuilder sb;
        if (this.mName != null) {
            sb = new StringBuilder("");
            sb.append(this.mName);
        } else {
            sb = new StringBuilder("");
            sb.append(this.id);
        }
        return sb.toString();
    }

    public final void updateReferencesWithNewDefinition(ArrayRow arrayRow) {
        int i2 = this.mClientEquationsCount;
        for (int i3 = 0; i3 < i2; i3++) {
            this.mClientEquations[i3].updateFromRow(arrayRow, false);
        }
        this.mClientEquationsCount = 0;
    }

    public SolverVariable(String str, Type type) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.isFinalValue = false;
        this.strengthVector = new float[9];
        this.goalStrengthVector = new float[9];
        this.mClientEquations = new ArrayRow[16];
        this.mClientEquationsCount = 0;
        this.usageInRowCount = 0;
        this.inRows = null;
        this.mName = str;
        this.mType = type;
    }
}
