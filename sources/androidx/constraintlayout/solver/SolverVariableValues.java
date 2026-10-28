package androidx.constraintlayout.solver;

import androidx.appcompat.graphics.drawable.a;
import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class SolverVariableValues implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean HASH = true;
    private static float epsilon = 0.001f;
    protected final Cache mCache;
    private final ArrayRow mRow;
    private final int NONE = -1;
    private int SIZE = 16;
    private int HASH_SIZE = 16;
    int[] keys = new int[16];
    int[] nextKeys = new int[16];
    int[] variables = new int[16];
    float[] values = new float[16];
    int[] previous = new int[16];
    int[] next = new int[16];
    int mCount = 0;
    int head = -1;

    public SolverVariableValues(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
        clear();
    }

    private void addToHashMap(SolverVariable solverVariable, int i2) {
        int[] iArr;
        int i3 = solverVariable.id % this.HASH_SIZE;
        int[] iArr2 = this.keys;
        int i4 = iArr2[i3];
        if (i4 == -1) {
            iArr2[i3] = i2;
        } else {
            while (true) {
                iArr = this.nextKeys;
                int i5 = iArr[i4];
                if (i5 == -1) {
                    break;
                } else {
                    i4 = i5;
                }
            }
            iArr[i4] = i2;
        }
        this.nextKeys[i2] = -1;
    }

    private void addVariable(int i2, SolverVariable solverVariable, float f2) {
        this.variables[i2] = solverVariable.id;
        this.values[i2] = f2;
        this.previous[i2] = -1;
        this.next[i2] = -1;
        solverVariable.addToRow(this.mRow);
        solverVariable.usageInRowCount++;
        this.mCount++;
    }

    private void displayHash() {
        for (int i2 = 0; i2 < this.HASH_SIZE; i2++) {
            if (this.keys[i2] != -1) {
                String str = hashCode() + " hash [" + i2 + "] => ";
                int i3 = this.keys[i2];
                boolean z2 = false;
                while (!z2) {
                    str = str + " " + this.variables[i3];
                    int i4 = this.nextKeys[i3];
                    if (i4 != -1) {
                        i3 = i4;
                    } else {
                        z2 = HASH;
                    }
                }
                System.out.println(str);
            }
        }
    }

    private int findEmptySlot() {
        for (int i2 = 0; i2 < this.SIZE; i2++) {
            if (this.variables[i2] == -1) {
                return i2;
            }
        }
        return -1;
    }

    private void increaseSize() {
        int i2 = this.SIZE * 2;
        this.variables = Arrays.copyOf(this.variables, i2);
        this.values = Arrays.copyOf(this.values, i2);
        this.previous = Arrays.copyOf(this.previous, i2);
        this.next = Arrays.copyOf(this.next, i2);
        this.nextKeys = Arrays.copyOf(this.nextKeys, i2);
        for (int i3 = this.SIZE; i3 < i2; i3++) {
            this.variables[i3] = -1;
            this.nextKeys[i3] = -1;
        }
        this.SIZE = i2;
    }

    private void insertVariable(int i2, SolverVariable solverVariable, float f2) {
        int iFindEmptySlot = findEmptySlot();
        addVariable(iFindEmptySlot, solverVariable, f2);
        if (i2 != -1) {
            this.previous[iFindEmptySlot] = i2;
            int[] iArr = this.next;
            iArr[iFindEmptySlot] = iArr[i2];
            iArr[i2] = iFindEmptySlot;
        } else {
            this.previous[iFindEmptySlot] = -1;
            if (this.mCount > 0) {
                this.next[iFindEmptySlot] = this.head;
                this.head = iFindEmptySlot;
            } else {
                this.next[iFindEmptySlot] = -1;
            }
        }
        int i3 = this.next[iFindEmptySlot];
        if (i3 != -1) {
            this.previous[i3] = iFindEmptySlot;
        }
        addToHashMap(solverVariable, iFindEmptySlot);
    }

    private void removeFromHashMap(SolverVariable solverVariable) {
        int[] iArr;
        int i2;
        int i3 = solverVariable.id;
        int i4 = i3 % this.HASH_SIZE;
        int[] iArr2 = this.keys;
        int i5 = iArr2[i4];
        if (i5 == -1) {
            return;
        }
        if (this.variables[i5] == i3) {
            int[] iArr3 = this.nextKeys;
            iArr2[i4] = iArr3[i5];
            iArr3[i5] = -1;
            return;
        }
        while (true) {
            iArr = this.nextKeys;
            i2 = iArr[i5];
            if (i2 == -1 || this.variables[i2] == i3) {
                break;
            } else {
                i5 = i2;
            }
        }
        if (i2 == -1 || this.variables[i2] != i3) {
            return;
        }
        iArr[i5] = iArr[i2];
        iArr[i2] = -1;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void add(SolverVariable solverVariable, float f2, boolean z2) {
        float f3 = epsilon;
        if (f2 <= (-f3) || f2 >= f3) {
            int iIndexOf = indexOf(solverVariable);
            if (iIndexOf == -1) {
                put(solverVariable, f2);
                return;
            }
            float[] fArr = this.values;
            float f4 = fArr[iIndexOf] + f2;
            fArr[iIndexOf] = f4;
            float f5 = epsilon;
            if (f4 <= (-f5) || f4 >= f5) {
                return;
            }
            fArr[iIndexOf] = 0.0f;
            remove(solverVariable, z2);
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void clear() {
        int i2 = this.mCount;
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                variable.removeFromRow(this.mRow);
            }
        }
        for (int i4 = 0; i4 < this.SIZE; i4++) {
            this.variables[i4] = -1;
            this.nextKeys[i4] = -1;
        }
        for (int i5 = 0; i5 < this.HASH_SIZE; i5++) {
            this.keys[i5] = -1;
        }
        this.mCount = 0;
        this.head = -1;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public boolean contains(SolverVariable solverVariable) {
        if (indexOf(solverVariable) != -1) {
            return HASH;
        }
        return false;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void display() {
        int i2 = this.mCount;
        System.out.print("{ ");
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                System.out.print(variable + " = " + getVariableValue(i3) + " ");
            }
        }
        System.out.println(" }");
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void divideByAmount(float f2) {
        int i2 = this.mCount;
        int i3 = this.head;
        for (int i4 = 0; i4 < i2; i4++) {
            float[] fArr = this.values;
            fArr[i3] = fArr[i3] / f2;
            i3 = this.next[i3];
            if (i3 == -1) {
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float get(SolverVariable solverVariable) {
        int iIndexOf = indexOf(solverVariable);
        if (iIndexOf != -1) {
            return this.values[iIndexOf];
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int getCurrentSize() {
        return this.mCount;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public SolverVariable getVariable(int i2) {
        int i3 = this.mCount;
        if (i3 == 0) {
            return null;
        }
        int i4 = this.head;
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 == i2 && i4 != -1) {
                return this.mCache.mIndexedVariables[this.variables[i4]];
            }
            i4 = this.next[i4];
            if (i4 == -1) {
                break;
            }
        }
        return null;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float getVariableValue(int i2) {
        int i3 = this.mCount;
        int i4 = this.head;
        for (int i5 = 0; i5 < i3; i5++) {
            if (i5 == i2) {
                return this.values[i4];
            }
            i4 = this.next[i4];
            if (i4 == -1) {
                return 0.0f;
            }
        }
        return 0.0f;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int indexOf(SolverVariable solverVariable) {
        if (this.mCount == 0) {
            return -1;
        }
        int i2 = solverVariable.id;
        int i3 = this.keys[i2 % this.HASH_SIZE];
        if (i3 == -1) {
            return -1;
        }
        if (this.variables[i3] == i2) {
            return i3;
        }
        do {
            i3 = this.nextKeys[i3];
            if (i3 == -1) {
                break;
            }
        } while (this.variables[i3] != i2);
        if (i3 != -1 && this.variables[i3] == i2) {
            return i3;
        }
        return -1;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void invert() {
        int i2 = this.mCount;
        int i3 = this.head;
        for (int i4 = 0; i4 < i2; i4++) {
            float[] fArr = this.values;
            fArr[i3] = fArr[i3] * (-1.0f);
            i3 = this.next[i3];
            if (i3 == -1) {
                return;
            }
        }
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public void put(SolverVariable solverVariable, float f2) {
        float f3 = epsilon;
        if (f2 > (-f3) && f2 < f3) {
            remove(solverVariable, HASH);
            return;
        }
        if (this.mCount == 0) {
            addVariable(0, solverVariable, f2);
            addToHashMap(solverVariable, 0);
            this.head = 0;
            return;
        }
        int iIndexOf = indexOf(solverVariable);
        if (iIndexOf != -1) {
            this.values[iIndexOf] = f2;
            return;
        }
        if (this.mCount + 1 >= this.SIZE) {
            increaseSize();
        }
        int i2 = this.mCount;
        int i3 = this.head;
        int i4 = -1;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = this.variables[i3];
            int i7 = solverVariable.id;
            if (i6 == i7) {
                this.values[i3] = f2;
                return;
            }
            if (i6 < i7) {
                i4 = i3;
            }
            i3 = this.next[i3];
            if (i3 == -1) {
                break;
            }
        }
        insertVariable(i4, solverVariable, f2);
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float remove(SolverVariable solverVariable, boolean z2) {
        int iIndexOf = indexOf(solverVariable);
        if (iIndexOf == -1) {
            return 0.0f;
        }
        removeFromHashMap(solverVariable);
        float f2 = this.values[iIndexOf];
        if (this.head == iIndexOf) {
            this.head = this.next[iIndexOf];
        }
        this.variables[iIndexOf] = -1;
        int[] iArr = this.previous;
        int i2 = iArr[iIndexOf];
        if (i2 != -1) {
            int[] iArr2 = this.next;
            iArr2[i2] = iArr2[iIndexOf];
        }
        int i3 = this.next[iIndexOf];
        if (i3 != -1) {
            iArr[i3] = iArr[iIndexOf];
        }
        this.mCount--;
        solverVariable.usageInRowCount--;
        if (z2) {
            solverVariable.removeFromRow(this.mRow);
        }
        return f2;
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public int sizeInBytes() {
        return 0;
    }

    public String toString() {
        String strE;
        String strE2;
        String strE3 = hashCode() + " { ";
        int i2 = this.mCount;
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable variable = getVariable(i3);
            if (variable != null) {
                String str = strE3 + variable + " = " + getVariableValue(i3) + " ";
                int iIndexOf = indexOf(variable);
                String strE4 = a.e(str, "[p: ");
                if (this.previous[iIndexOf] != -1) {
                    StringBuilder sbH = a.h(strE4);
                    sbH.append(this.mCache.mIndexedVariables[this.variables[this.previous[iIndexOf]]]);
                    strE = sbH.toString();
                } else {
                    strE = a.e(strE4, "none");
                }
                String strE5 = a.e(strE, ", n: ");
                if (this.next[iIndexOf] != -1) {
                    StringBuilder sbH2 = a.h(strE5);
                    sbH2.append(this.mCache.mIndexedVariables[this.variables[this.next[iIndexOf]]]);
                    strE2 = sbH2.toString();
                } else {
                    strE2 = a.e(strE5, "none");
                }
                strE3 = a.e(strE2, "]");
            }
        }
        return a.e(strE3, " }");
    }

    @Override // androidx.constraintlayout.solver.ArrayRow.ArrayRowVariables
    public float use(ArrayRow arrayRow, boolean z2) {
        float f2 = get(arrayRow.variable);
        remove(arrayRow.variable, z2);
        SolverVariableValues solverVariableValues = (SolverVariableValues) arrayRow.variables;
        int currentSize = solverVariableValues.getCurrentSize();
        int i2 = 0;
        int i3 = 0;
        while (i2 < currentSize) {
            int i4 = solverVariableValues.variables[i3];
            if (i4 != -1) {
                add(this.mCache.mIndexedVariables[i4], solverVariableValues.values[i3] * f2, z2);
                i2++;
            }
            i3++;
        }
        return f2;
    }
}
