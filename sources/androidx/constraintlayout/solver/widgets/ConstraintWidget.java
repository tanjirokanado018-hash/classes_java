package androidx.constraintlayout.solver.widgets;

import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ConstraintWidget {
    public static final int ANCHOR_BASELINE = 4;
    public static final int ANCHOR_BOTTOM = 3;
    public static final int ANCHOR_LEFT = 0;
    public static final int ANCHOR_RIGHT = 1;
    public static final int ANCHOR_TOP = 2;
    private static final boolean AUTOTAG_CENTER = false;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    static final int DIMENSION_HORIZONTAL = 0;
    static final int DIMENSION_VERTICAL = 1;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    private static final int WRAP = -2;
    private boolean hasBaseline;
    public ChainRun horizontalChainRun;
    public HorizontalWidgetRun horizontalRun;
    private boolean inPlaceholder;
    public boolean[] isTerminalWidget;
    protected ArrayList<ConstraintAnchor> mAnchors;
    ConstraintAnchor mBaseline;
    int mBaselineDistance;
    public ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    public float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    boolean mGroupsToSolver;
    int mHeight;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    boolean mHorizontalWrapVisited;
    private boolean mInVirtuaLayout;
    public boolean mIsHeightWrapContent;
    private boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    public ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    protected int mOffsetX;
    protected int mOffsetY;
    boolean mOptimizerMeasurable;
    public ConstraintWidget mParent;
    int mRelX;
    int mRelY;
    float mResolvedDimensionRatio;
    int mResolvedDimensionRatioSide;
    boolean mResolvedHasRatio;
    public int[] mResolvedMatchConstraintDefault;
    public ConstraintAnchor mRight;
    boolean mRightHasCentered;
    public ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    public float[] mWeight;
    int mWidth;
    protected int mX;
    protected int mY;
    public boolean measured;
    public WidgetRun[] run;
    public ChainRun verticalChainRun;
    public VerticalWidgetRun verticalRun;
    public int[] wrapMeasure;

    /* JADX INFO: renamed from: androidx.constraintlayout.solver.widgets.ConstraintWidget$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type;
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour;

        static {
            int[] iArr = new int[DimensionBehaviour.values().length];
            $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour = iArr;
            try {
                iArr[DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type = iArr2;
            try {
                iArr2[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public ConstraintWidget() {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = new HorizontalWidgetRun(this);
        this.verticalRun = new VerticalWidgetRun(this);
        this.isTerminalWidget = new boolean[]{true, true};
        this.wrapMeasure = new int[]{0, 0, 0, 0};
        this.mResolvedHasRatio = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtuaLayout = false;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f2 = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f2;
        this.mVerticalBiasPercent = f2;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mOptimizerMeasurable = false;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        addAnchors();
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x019b  */
    /* JADX WARN: Code duplicated, block: B:102:0x01ab A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:179:0x02b9  */
    /* JADX WARN: Code duplicated, block: B:181:0x02bf  */
    /* JADX WARN: Code duplicated, block: B:183:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:184:0x02c8  */
    /* JADX WARN: Code duplicated, block: B:186:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:189:0x02fd A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:190:0x02fe A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:191:0x0300  */
    /* JADX WARN: Code duplicated, block: B:199:0x0310  */
    /* JADX WARN: Code duplicated, block: B:201:0x032a  */
    /* JADX WARN: Code duplicated, block: B:209:0x0341  */
    /* JADX WARN: Code duplicated, block: B:211:0x0347  */
    /* JADX WARN: Code duplicated, block: B:215:0x0350  */
    /* JADX WARN: Code duplicated, block: B:235:0x037a  */
    /* JADX WARN: Code duplicated, block: B:243:0x0398  */
    /* JADX WARN: Code duplicated, block: B:245:0x039c  */
    /* JADX WARN: Code duplicated, block: B:246:0x03a1  */
    /* JADX WARN: Code duplicated, block: B:248:0x03a4  */
    /* JADX WARN: Code duplicated, block: B:269:0x03db A[ADDED_TO_REGION, REMOVE] */
    /* JADX WARN: Code duplicated, block: B:271:0x03ea A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:297:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x0085  */
    /* JADX WARN: Code duplicated, block: B:31:0x0089  */
    /* JADX WARN: Code duplicated, block: B:33:0x008d  */
    /* JADX WARN: Code duplicated, block: B:37:0x0098  */
    /* JADX WARN: Code duplicated, block: B:38:0x009d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x009f A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:42:0x00ae A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:43:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:45:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:48:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:49:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:53:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:65:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:66:0x0101  */
    /* JADX WARN: Code duplicated, block: B:68:0x0104  */
    /* JADX WARN: Code duplicated, block: B:69:0x0106  */
    /* JADX WARN: Code duplicated, block: B:76:0x0111  */
    /* JADX WARN: Code duplicated, block: B:79:0x011b  */
    /* JADX WARN: Code duplicated, block: B:82:0x0120  */
    /* JADX WARN: Code duplicated, block: B:85:0x0129 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:86:0x012b  */
    /* JADX WARN: Code duplicated, block: B:87:0x012f  */
    /* JADX WARN: Code duplicated, block: B:89:0x013b  */
    /* JADX WARN: Code duplicated, block: B:91:0x013e  */
    /* JADX WARN: Code duplicated, block: B:98:0x016b  */
    private void applyConstraints(LinearSystem linearSystem, boolean z2, boolean z3, boolean z4, boolean z5, SolverVariable solverVariable, SolverVariable solverVariable2, DimensionBehaviour dimensionBehaviour, boolean z6, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2, int i3, int i4, int i5, float f2, boolean z7, boolean z8, boolean z9, boolean z10, int i6, int i7, int i8, int i9, float f3, boolean z11) {
        int i10;
        boolean z12;
        int iMin;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        boolean z13;
        ConstraintAnchor.Type type;
        ConstraintAnchor.Type type2;
        SolverVariable solverVariableCreateObjectVariable;
        ConstraintWidget constraintWidget;
        ConstraintAnchor.Type type3;
        int i16;
        ConstraintAnchor constraintAnchor3;
        int i17;
        boolean z14;
        boolean z15;
        int i18;
        int i19;
        boolean z16;
        boolean z17;
        boolean z18;
        int i20;
        ConstraintWidget constraintWidget2;
        int i21;
        LinearSystem linearSystem2;
        SolverVariable solverVariable3;
        int i22;
        int iMin2;
        int i23;
        SolverVariable solverVariable4;
        int i24;
        int margin;
        ConstraintWidget constraintWidget3;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        boolean z19;
        boolean z20;
        SolverVariable solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(constraintAnchor);
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(constraintAnchor2);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(constraintAnchor.getTarget());
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem.createObjectVariable(constraintAnchor2.getTarget());
        if (LinearSystem.getMetrics() != null) {
            LinearSystem.getMetrics().nonresolvedWidgets++;
        }
        boolean zIsConnected = constraintAnchor.isConnected();
        boolean zIsConnected2 = constraintAnchor2.isConnected();
        boolean zIsConnected3 = this.mCenter.isConnected();
        int i31 = zIsConnected2 ? (zIsConnected ? 1 : 0) + 1 : zIsConnected ? 1 : 0;
        if (zIsConnected3) {
            i31++;
        }
        int i32 = z7 ? 3 : i6;
        int i33 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[dimensionBehaviour.ordinal()];
        if (i33 != 1 && i33 != 2 && i33 != 3 && i33 == 4) {
            i10 = i32;
            if (i10 != 4) {
                z12 = true;
            }
            if (this.mVisibility == 8) {
                iMin = 0;
                z12 = false;
            } else {
                iMin = i3;
            }
            if (z11) {
                if (zIsConnected && !zIsConnected2 && !zIsConnected3) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable2, i2);
                } else if (zIsConnected && !zIsConnected2) {
                    i11 = 8;
                    linearSystem.addEquality(solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), 8);
                }
                i11 = 8;
            } else {
                i11 = 8;
            }
            if (!z12) {
                if (i31 == 2 && !z7 && (i10 == 1 || i10 == 0)) {
                    int iMax = Math.max(i8, iMin);
                    if (i9 > 0) {
                        iMax = Math.min(i9, iMax);
                    }
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMax, 8);
                    i15 = i8;
                    i13 = i9;
                    solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                    solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                    z13 = false;
                    i14 = i31;
                } else {
                    if (i8 == -2) {
                        i12 = iMin;
                    } else {
                        i12 = i8;
                    }
                    if (i9 == -2) {
                        i13 = iMin;
                    } else {
                        i13 = i9;
                    }
                    if (iMin > 0 && i10 != 1) {
                        iMin = 0;
                    }
                    if (i12 > 0) {
                        linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i12, 8);
                        iMin = Math.max(iMin, i12);
                    }
                    if (i13 > 0) {
                        if (z3 || i10 != 1) {
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i13, 8);
                        }
                        iMin = Math.min(iMin, i13);
                    }
                    if (i10 == 1) {
                        if (z3) {
                            linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, 8);
                        } else {
                            linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, 5);
                            linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, 8);
                        }
                        i15 = i12;
                    } else if (i10 == 2) {
                        type = constraintAnchor.getType();
                        type2 = ConstraintAnchor.Type.TOP;
                        if (type != type2 || constraintAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                            solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                            constraintWidget = this.mParent;
                            type3 = ConstraintAnchor.Type.BOTTOM;
                        } else {
                            solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                            constraintWidget = this.mParent;
                            type3 = ConstraintAnchor.Type.RIGHT;
                        }
                        SolverVariable solverVariableCreateObjectVariable6 = linearSystem.createObjectVariable(constraintWidget.getAnchor(type3));
                        SolverVariable solverVariable5 = solverVariableCreateObjectVariable;
                        ArrayRow arrayRowCreateRow = linearSystem.createRow();
                        int i34 = i12;
                        solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                        i14 = i31 == true ? 1 : 0;
                        solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                        solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                        linearSystem.addConstraint(arrayRowCreateRow.createRowDimensionRatio(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable6, solverVariable5, f3));
                        z5 = z5;
                        i15 = i34;
                        z13 = false;
                    } else {
                        solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                        solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                        int i35 = i12;
                        solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                        i14 = i31;
                        i15 = i35;
                        z13 = z12;
                        z5 = true;
                    }
                }
                if (z11 || z8) {
                    i16 = 8;
                    if (i14 >= 2 && z3 && z5) {
                        r10.addGreaterThan(solverVariableCreateObjectVariable2, solverVariable, 0, i16);
                        boolean z21 = z2 || this.mBaseline.mTarget == null;
                        if (!z2 && (constraintAnchor3 = this.mBaseline.mTarget) != null) {
                            ConstraintWidget constraintWidget4 = constraintAnchor3.mOwner;
                            if (constraintWidget4.mDimensionRatio == 0.0f) {
                                return;
                            }
                            DimensionBehaviour[] dimensionBehaviourArr = constraintWidget4.mListDimensionBehaviors;
                            DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
                            DimensionBehaviour dimensionBehaviour3 = DimensionBehaviour.MATCH_CONSTRAINT;
                            if (dimensionBehaviour2 != dimensionBehaviour3 || dimensionBehaviourArr[1] != dimensionBehaviour3) {
                                return;
                            }
                        } else if (!z21) {
                            return;
                        }
                        linearSystem.addGreaterThan(solverVariable2, solverVariableCreateObjectVariable3, 0, i16);
                        return;
                    }
                    return;
                }
                if ((zIsConnected || zIsConnected2 || zIsConnected3) && (!zIsConnected || zIsConnected2)) {
                    if (zIsConnected || !zIsConnected2) {
                        if (zIsConnected && zIsConnected2) {
                            ConstraintWidget constraintWidget5 = constraintAnchor.mTarget.mOwner;
                            ConstraintWidget constraintWidget6 = constraintAnchor2.mTarget.mOwner;
                            ConstraintWidget parent = getParent();
                            int i36 = 6;
                            if (z13) {
                                if (i10 == 0) {
                                    if (i13 == 0 && i15 == 0) {
                                        i29 = 8;
                                        i30 = 8;
                                        z19 = false;
                                        z20 = true;
                                    } else {
                                        i29 = 5;
                                        i30 = 5;
                                        z19 = true;
                                        z20 = false;
                                    }
                                    if ((constraintWidget5 instanceof Barrier) || (constraintWidget6 instanceof Barrier)) {
                                        z15 = z19;
                                        z16 = z20;
                                        z14 = false;
                                        i18 = 4;
                                        i19 = i29;
                                        i17 = 6;
                                    } else {
                                        z15 = z19;
                                        z16 = z20;
                                        i19 = i29;
                                        i18 = i30;
                                        i17 = 6;
                                        z14 = false;
                                    }
                                } else if (i10 == 1) {
                                    i17 = 6;
                                    z14 = true;
                                    z15 = true;
                                    i18 = 4;
                                    i19 = 8;
                                    z16 = false;
                                } else if (i10 == 3) {
                                    if (this.mResolvedDimensionRatioSide == -1) {
                                        if (z9) {
                                            i17 = z3 ? 5 : 4;
                                        } else {
                                            i17 = 8;
                                        }
                                        z14 = true;
                                        z15 = true;
                                        i18 = 5;
                                        i19 = 8;
                                    } else if (z7) {
                                        if (i7 == 2 || i7 == 1) {
                                            i27 = 5;
                                            i28 = 4;
                                        } else {
                                            i27 = 8;
                                            i28 = 5;
                                        }
                                        i19 = i27;
                                        i18 = i28;
                                        i17 = 6;
                                        z14 = true;
                                        z15 = true;
                                    } else {
                                        if (i13 > 0) {
                                            i17 = 6;
                                            z14 = true;
                                            z15 = true;
                                            i18 = 5;
                                        } else if (i13 != 0 || i15 != 0) {
                                            i17 = 6;
                                            z14 = true;
                                            z15 = true;
                                            i18 = 4;
                                        } else if (z9) {
                                            i19 = (constraintWidget5 == parent || constraintWidget6 == parent) ? 5 : 4;
                                            i17 = 6;
                                            z14 = true;
                                            z15 = true;
                                            i18 = 4;
                                        } else {
                                            i17 = 6;
                                            z14 = true;
                                            z15 = true;
                                            i18 = 8;
                                        }
                                        i19 = 5;
                                    }
                                    z16 = true;
                                } else {
                                    i17 = 6;
                                    z14 = false;
                                    z15 = false;
                                }
                                if (z14 || solverVariableCreateObjectVariable4 != solverVariableCreateObjectVariable5 || constraintWidget5 == parent) {
                                    z17 = z14;
                                    z18 = true;
                                } else {
                                    z17 = false;
                                    z18 = false;
                                }
                                if (z15) {
                                    if (this.mVisibility == 8) {
                                        i26 = 4;
                                    } else {
                                        i26 = i17;
                                    }
                                    i20 = i10;
                                    constraintWidget2 = parent;
                                    i21 = 8;
                                    linearSystem.addCentering(solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), f2, solverVariableCreateObjectVariable5, solverVariableCreateObjectVariable3, constraintAnchor2.getMargin(), i26);
                                } else {
                                    i20 = i10;
                                    constraintWidget2 = parent;
                                    i21 = 8;
                                }
                                if (this.mVisibility == i21) {
                                    return;
                                }
                                if (z17) {
                                    if (z3 || solverVariableCreateObjectVariable4 == solverVariableCreateObjectVariable5 || z13 || !((constraintWidget5 instanceof Barrier) || (constraintWidget6 instanceof Barrier))) {
                                        i25 = i19;
                                    } else {
                                        i25 = 6;
                                    }
                                    linearSystem2 = linearSystem;
                                    solverVariable3 = solverVariableCreateObjectVariable2;
                                    i22 = 8;
                                    linearSystem2.addGreaterThan(solverVariable3, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), i25);
                                    linearSystem2.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, -constraintAnchor2.getMargin(), i25);
                                    i19 = i25;
                                } else {
                                    linearSystem2 = linearSystem;
                                    solverVariable3 = solverVariableCreateObjectVariable2;
                                    i22 = 8;
                                }
                                if (z3 || !z10 || (constraintWidget5 instanceof Barrier) || (constraintWidget6 instanceof Barrier)) {
                                    iMin2 = i18;
                                    i23 = i19;
                                } else {
                                    iMin2 = 6;
                                    i23 = 6;
                                    z18 = true;
                                }
                                if (z18) {
                                    if (z16 || (z9 && !z4)) {
                                        constraintWidget3 = constraintWidget2;
                                    } else {
                                        constraintWidget3 = constraintWidget2;
                                        if (constraintWidget5 != constraintWidget3 && constraintWidget6 != constraintWidget3) {
                                            i36 = iMin2;
                                        }
                                        if ((constraintWidget5 instanceof Guideline) || (constraintWidget6 instanceof Guideline)) {
                                            i36 = 5;
                                        }
                                        if ((constraintWidget5 instanceof Barrier) || (constraintWidget6 instanceof Barrier)) {
                                            i36 = 5;
                                        }
                                        iMin2 = Math.max(z9 ? 5 : i36, iMin2);
                                    }
                                    if (z3) {
                                        iMin2 = Math.min(i23, iMin2);
                                        if (z7 && !z9 && (constraintWidget5 == constraintWidget3 || constraintWidget6 == constraintWidget3)) {
                                            iMin2 = 4;
                                        }
                                    }
                                    linearSystem2.addEquality(solverVariable3, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), iMin2);
                                    linearSystem2.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, -constraintAnchor2.getMargin(), iMin2);
                                }
                                if (z3) {
                                    if (solverVariable == solverVariableCreateObjectVariable4) {
                                        margin = constraintAnchor.getMargin();
                                    } else {
                                        margin = 0;
                                    }
                                    if (solverVariableCreateObjectVariable4 != solverVariable) {
                                        linearSystem2.addGreaterThan(solverVariable3, solverVariable, margin, 5);
                                    }
                                }
                                if (!z3 && z13) {
                                    solverVariable4 = solverVariableCreateObjectVariable3;
                                    if (i4 == 0 && i15 == 0) {
                                        if (z13 && i20 == 3) {
                                            i24 = 0;
                                        } else {
                                            i24 = 0;
                                            i22 = 5;
                                        }
                                        linearSystem2.addGreaterThan(solverVariable4, solverVariable3, i24, i22);
                                    }
                                }
                            } else {
                                i17 = 6;
                                z14 = true;
                                z15 = true;
                            }
                            i18 = 4;
                            i19 = 5;
                            z16 = false;
                            if (z14) {
                                z17 = z14;
                                z18 = true;
                            } else {
                                z17 = z14;
                                z18 = true;
                            }
                            if (z15) {
                                if (this.mVisibility == 8) {
                                    i26 = 4;
                                } else {
                                    i26 = i17;
                                }
                                i20 = i10;
                                constraintWidget2 = parent;
                                i21 = 8;
                                linearSystem.addCentering(solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), f2, solverVariableCreateObjectVariable5, solverVariableCreateObjectVariable3, constraintAnchor2.getMargin(), i26);
                            } else {
                                i20 = i10;
                                constraintWidget2 = parent;
                                i21 = 8;
                            }
                            if (this.mVisibility == i21) {
                                return;
                            }
                            if (z17) {
                                if (z3) {
                                    i25 = i19;
                                } else {
                                    i25 = i19;
                                }
                                linearSystem2 = linearSystem;
                                solverVariable3 = solverVariableCreateObjectVariable2;
                                i22 = 8;
                                linearSystem2.addGreaterThan(solverVariable3, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), i25);
                                linearSystem2.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, -constraintAnchor2.getMargin(), i25);
                                i19 = i25;
                            } else {
                                linearSystem2 = linearSystem;
                                solverVariable3 = solverVariableCreateObjectVariable2;
                                i22 = 8;
                            }
                            if (z3) {
                                iMin2 = i18;
                                i23 = i19;
                            } else {
                                iMin2 = i18;
                                i23 = i19;
                            }
                            if (z18) {
                                if (z16) {
                                    constraintWidget3 = constraintWidget2;
                                } else {
                                    constraintWidget3 = constraintWidget2;
                                }
                                if (z3) {
                                    iMin2 = Math.min(i23, iMin2);
                                    if (z7) {
                                        iMin2 = 4;
                                    }
                                }
                                linearSystem2.addEquality(solverVariable3, solverVariableCreateObjectVariable4, constraintAnchor.getMargin(), iMin2);
                                linearSystem2.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, -constraintAnchor2.getMargin(), iMin2);
                            }
                            if (z3) {
                                if (solverVariable == solverVariableCreateObjectVariable4) {
                                    margin = constraintAnchor.getMargin();
                                } else {
                                    margin = 0;
                                }
                                if (solverVariableCreateObjectVariable4 != solverVariable) {
                                    linearSystem2.addGreaterThan(solverVariable3, solverVariable, margin, 5);
                                }
                            }
                            if (!z3) {
                            }
                        }
                        solverVariable4 = solverVariableCreateObjectVariable3;
                    } else {
                        linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable5, -constraintAnchor2.getMargin(), 8);
                        if (z3) {
                            linearSystem.addGreaterThan(solverVariableCreateObjectVariable2, solverVariable, 0, 5);
                        }
                    }
                    linearSystem2 = linearSystem;
                    solverVariable4 = solverVariableCreateObjectVariable3;
                } else {
                    linearSystem2 = linearSystem;
                    solverVariable4 = solverVariableCreateObjectVariable3;
                }
                if (z3 && z5) {
                    int margin2 = constraintAnchor2.mTarget != null ? constraintAnchor2.getMargin() : 0;
                    if (solverVariableCreateObjectVariable5 != solverVariable2) {
                        linearSystem2.addGreaterThan(solverVariable2, solverVariable4, margin2, 5);
                        return;
                    }
                    return;
                }
                return;
            }
            if (z6) {
                linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, 0, 3);
                if (i4 > 0) {
                    linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i4, 8);
                }
                if (i5 < Integer.MAX_VALUE) {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i5, 8);
                }
            } else {
                linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, i11);
            }
            i15 = i8;
            i13 = i9;
            z13 = z12;
            i14 = i31;
            if (z11) {
                i16 = 8;
            } else {
                i16 = 8;
            }
            if (i14 >= 2) {
            }
        }
        i10 = i32;
        z12 = false;
        if (this.mVisibility == 8) {
            iMin = 0;
            z12 = false;
        } else {
            iMin = i3;
        }
        if (z11) {
            i11 = 8;
        } else if (zIsConnected) {
            if (zIsConnected) {
                i11 = 8;
            } else {
                i11 = 8;
            }
        } else if (zIsConnected) {
            i11 = 8;
        } else {
            i11 = 8;
        }
        if (!z12) {
            if (i31 == 2) {
            }
            if (i8 == -2) {
                i12 = iMin;
            } else {
                i12 = i8;
            }
            if (i9 == -2) {
                i13 = iMin;
            } else {
                i13 = i9;
            }
            if (iMin > 0) {
                iMin = 0;
            }
            if (i12 > 0) {
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i12, 8);
                iMin = Math.max(iMin, i12);
            }
            if (i13 > 0) {
                if (z3) {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i13, 8);
                } else {
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i13, 8);
                }
                iMin = Math.min(iMin, i13);
            }
            if (i10 == 1) {
                if (z3) {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, 8);
                } else {
                    linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, 5);
                    linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, 8);
                }
                i15 = i12;
            } else if (i10 == 2) {
                type = constraintAnchor.getType();
                type2 = ConstraintAnchor.Type.TOP;
                if (type != type2) {
                    solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                    constraintWidget = this.mParent;
                    type3 = ConstraintAnchor.Type.BOTTOM;
                } else {
                    solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mParent.getAnchor(type2));
                    constraintWidget = this.mParent;
                    type3 = ConstraintAnchor.Type.BOTTOM;
                }
                SolverVariable solverVariableCreateObjectVariable7 = linearSystem.createObjectVariable(constraintWidget.getAnchor(type3));
                SolverVariable solverVariable6 = solverVariableCreateObjectVariable;
                ArrayRow arrayRowCreateRow2 = linearSystem.createRow();
                int i37 = i12;
                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                i14 = i31 == true ? 1 : 0;
                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                linearSystem.addConstraint(arrayRowCreateRow2.createRowDimensionRatio(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, solverVariableCreateObjectVariable7, solverVariable6, f3));
                z5 = z5;
                i15 = i37;
                z13 = false;
            } else {
                solverVariableCreateObjectVariable4 = solverVariableCreateObjectVariable4;
                solverVariableCreateObjectVariable3 = solverVariableCreateObjectVariable3;
                int i38 = i12;
                solverVariableCreateObjectVariable5 = solverVariableCreateObjectVariable5;
                i14 = i31;
                i15 = i38;
                z13 = z12;
                z5 = true;
            }
            if (z11) {
                i16 = 8;
            } else {
                i16 = 8;
            }
            if (i14 >= 2) {
            }
        }
        if (z6) {
            linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, 0, 3);
            if (i4 > 0) {
                linearSystem.addGreaterThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i4, 8);
            }
            if (i5 < Integer.MAX_VALUE) {
                linearSystem.addLowerThan(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, i5, 8);
            }
        } else {
            linearSystem.addEquality(solverVariableCreateObjectVariable3, solverVariableCreateObjectVariable2, iMin, i11);
        }
        i15 = i8;
        i13 = i9;
        z13 = z12;
        i14 = i31;
        if (z11) {
            i16 = 8;
        } else {
            i16 = 8;
        }
        if (i14 >= 2) {
        }
    }

    private boolean isChainHead(int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        int i3 = i2 * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i3];
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return (constraintAnchor4 == null || constraintAnchor4.mTarget == constraintAnchor3 || (constraintAnchor2 = (constraintAnchor = constraintAnchorArr[i3 + 1]).mTarget) == null || constraintAnchor2.mTarget != constraintAnchor) ? false : true;
    }

    public boolean addFirst() {
        return (this instanceof VirtualLayout) || (this instanceof Guideline);
    }

    /* JADX WARN: Code duplicated, block: B:197:0x03bd  */
    /* JADX WARN: Code duplicated, block: B:200:0x03cf  */
    /* JADX WARN: Code duplicated, block: B:207:0x03e6  */
    /* JADX WARN: Code duplicated, block: B:209:0x03ea  */
    /* JADX WARN: Code duplicated, block: B:216:0x03f8  */
    /* JADX WARN: Code duplicated, block: B:219:0x03fe  */
    /* JADX WARN: Code duplicated, block: B:220:0x0405  */
    /* JADX WARN: Code duplicated, block: B:223:0x040b  */
    /* JADX WARN: Code duplicated, block: B:228:0x041b  */
    /* JADX WARN: Code duplicated, block: B:230:0x0428  */
    /* JADX WARN: Code duplicated, block: B:232:0x0431  */
    /* JADX WARN: Code duplicated, block: B:234:0x043e  */
    /* JADX WARN: Code duplicated, block: B:236:0x0442  */
    /* JADX WARN: Code duplicated, block: B:241:0x0495  */
    /* JADX WARN: Code duplicated, block: B:243:0x049e  */
    /* JADX WARN: Code duplicated, block: B:245:0x04ac  */
    /* JADX WARN: Code duplicated, block: B:248:0x04bf  */
    /* JADX WARN: Code duplicated, block: B:254:? A[RETURN, SYNTHETIC] */
    public void addToSolver(LinearSystem linearSystem) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i2;
        int i3;
        int i4;
        boolean z6;
        boolean z7;
        boolean z8;
        SolverVariable solverVariable;
        DependencyNode dependencyNode;
        LinearSystem linearSystem2;
        SolverVariable solverVariable2;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        int i5;
        int i6;
        int i7;
        boolean z9;
        boolean z10;
        boolean z11;
        ConstraintWidget constraintWidget;
        SolverVariable solverVariableCreateObjectVariable;
        ConstraintAnchor constraintAnchor;
        boolean z12;
        int i8;
        int i9;
        SolverVariable solverVariable5;
        SolverVariable solverVariable6;
        SolverVariable solverVariable7;
        SolverVariable solverVariable8;
        int i10;
        boolean zIsInHorizontalChain;
        boolean zIsInVerticalChain;
        ConstraintWidget constraintWidget2 = this;
        SolverVariable solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(constraintWidget2.mLeft);
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(constraintWidget2.mRight);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(constraintWidget2.mTop);
        SolverVariable solverVariableCreateObjectVariable5 = linearSystem.createObjectVariable(constraintWidget2.mBottom);
        SolverVariable solverVariableCreateObjectVariable6 = linearSystem.createObjectVariable(constraintWidget2.mBaseline);
        Metrics metrics = LinearSystem.sMetrics;
        if (metrics != null) {
            metrics.widgets++;
        }
        HorizontalWidgetRun horizontalWidgetRun = constraintWidget2.horizontalRun;
        DependencyNode dependencyNode2 = horizontalWidgetRun.start;
        if (dependencyNode2.resolved && horizontalWidgetRun.end.resolved) {
            VerticalWidgetRun verticalWidgetRun = constraintWidget2.verticalRun;
            if (verticalWidgetRun.start.resolved && verticalWidgetRun.end.resolved) {
                if (metrics != null) {
                    metrics.graphSolved++;
                }
                linearSystem.addEquality(solverVariableCreateObjectVariable2, dependencyNode2.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable3, constraintWidget2.horizontalRun.end.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable4, constraintWidget2.verticalRun.start.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable5, constraintWidget2.verticalRun.end.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable6, constraintWidget2.verticalRun.baseline.value);
                ConstraintWidget constraintWidget3 = constraintWidget2.mParent;
                if (constraintWidget3 != null) {
                    boolean z13 = constraintWidget3 != null && constraintWidget3.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
                    boolean z14 = constraintWidget3 != null && constraintWidget3.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
                    if (z13 && constraintWidget2.isTerminalWidget[0] && !isInHorizontalChain()) {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mParent.mRight), solverVariableCreateObjectVariable3, 0, 8);
                    }
                    if (z14 && constraintWidget2.isTerminalWidget[1] && !isInVerticalChain()) {
                        linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mParent.mBottom), solverVariableCreateObjectVariable5, 0, 8);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        if (metrics != null) {
            metrics.linearSolved++;
        }
        ConstraintWidget constraintWidget4 = constraintWidget2.mParent;
        if (constraintWidget4 != null) {
            boolean z15 = constraintWidget4 != null && constraintWidget4.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
            z2 = constraintWidget4 != null && constraintWidget4.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
            if (constraintWidget2.isChainHead(0)) {
                ((ConstraintWidgetContainer) constraintWidget2.mParent).addChain(constraintWidget2, 0);
                zIsInHorizontalChain = true;
            } else {
                zIsInHorizontalChain = isInHorizontalChain();
            }
            if (constraintWidget2.isChainHead(1)) {
                ((ConstraintWidgetContainer) constraintWidget2.mParent).addChain(constraintWidget2, 1);
                zIsInVerticalChain = true;
            } else {
                zIsInVerticalChain = isInVerticalChain();
            }
            if (!zIsInHorizontalChain && z15 && constraintWidget2.mVisibility != 8 && constraintWidget2.mLeft.mTarget == null && constraintWidget2.mRight.mTarget == null) {
                linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mParent.mRight), solverVariableCreateObjectVariable3, 0, 1);
            }
            if (!zIsInVerticalChain && z2 && constraintWidget2.mVisibility != 8 && constraintWidget2.mTop.mTarget == null && constraintWidget2.mBottom.mTarget == null && constraintWidget2.mBaseline == null) {
                linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mParent.mBottom), solverVariableCreateObjectVariable5, 0, 1);
            }
            z3 = z15;
            z5 = zIsInHorizontalChain;
            z4 = zIsInVerticalChain;
        } else {
            z2 = false;
            z3 = false;
            z4 = false;
            z5 = false;
        }
        int i11 = constraintWidget2.mWidth;
        int i12 = constraintWidget2.mMinWidth;
        if (i11 >= i12) {
            i12 = i11;
        }
        int i13 = constraintWidget2.mHeight;
        int i14 = constraintWidget2.mMinHeight;
        if (i13 >= i14) {
            i14 = i13;
        }
        DimensionBehaviour[] dimensionBehaviourArr = constraintWidget2.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.MATCH_CONSTRAINT;
        SolverVariable solverVariable9 = solverVariableCreateObjectVariable6;
        boolean z16 = dimensionBehaviour != dimensionBehaviour2;
        DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[1];
        SolverVariable solverVariable10 = solverVariableCreateObjectVariable5;
        SolverVariable solverVariable11 = solverVariableCreateObjectVariable4;
        boolean z17 = dimensionBehaviour3 != dimensionBehaviour2;
        int i15 = constraintWidget2.mDimensionRatioSide;
        constraintWidget2.mResolvedDimensionRatioSide = i15;
        int i16 = i12;
        float f2 = constraintWidget2.mDimensionRatio;
        constraintWidget2.mResolvedDimensionRatio = f2;
        int i17 = i14;
        int i18 = constraintWidget2.mMatchConstraintDefaultWidth;
        int i19 = constraintWidget2.mMatchConstraintDefaultHeight;
        SolverVariable solverVariable12 = solverVariableCreateObjectVariable2;
        if (f2 <= 0.0f || constraintWidget2.mVisibility == 8) {
            i2 = i19;
            i18 = i18;
            i3 = i16;
            i4 = i17;
            z6 = false;
        } else {
            if (dimensionBehaviour == dimensionBehaviour2 && i18 == 0) {
                i18 = 3;
            }
            if (dimensionBehaviour3 == dimensionBehaviour2 && i19 == 0) {
                i19 = 3;
            }
            if (dimensionBehaviour == dimensionBehaviour2 && dimensionBehaviour3 == dimensionBehaviour2 && i18 == 3 && i19 == 3) {
                constraintWidget2.setupDimensionRatio(z3, z2, z16, z17);
            } else if (dimensionBehaviour == dimensionBehaviour2 && i18 == 3) {
                constraintWidget2.mResolvedDimensionRatioSide = 0;
                i3 = (int) (f2 * i13);
                i2 = i19;
                if (dimensionBehaviour3 != dimensionBehaviour2) {
                    i4 = i17;
                    z6 = false;
                    i18 = 4;
                } else {
                    i4 = i17;
                    z6 = true;
                }
            } else if (dimensionBehaviour3 == dimensionBehaviour2 && i19 == 3) {
                constraintWidget2.mResolvedDimensionRatioSide = 1;
                if (i15 == -1) {
                    constraintWidget2.mResolvedDimensionRatio = 1.0f / f2;
                }
                i4 = (int) (constraintWidget2.mResolvedDimensionRatio * i11);
                if (dimensionBehaviour != dimensionBehaviour2) {
                    i18 = i18;
                    i3 = i16;
                    z6 = false;
                    i2 = 4;
                } else {
                    i2 = i19;
                    i18 = i18;
                    i3 = i16;
                    z6 = true;
                }
            }
            i2 = i19;
            i3 = i16;
            i4 = i17;
            z6 = true;
        }
        int[] iArr = constraintWidget2.mResolvedMatchConstraintDefault;
        iArr[0] = i18;
        iArr[1] = i2;
        constraintWidget2.mResolvedHasRatio = z6;
        boolean z18 = z6 && ((i10 = constraintWidget2.mResolvedDimensionRatioSide) == 0 || i10 == -1);
        DimensionBehaviour dimensionBehaviour4 = constraintWidget2.mListDimensionBehaviors[0];
        DimensionBehaviour dimensionBehaviour5 = DimensionBehaviour.WRAP_CONTENT;
        boolean z19 = dimensionBehaviour4 == dimensionBehaviour5 && (constraintWidget2 instanceof ConstraintWidgetContainer);
        int i20 = z19 ? 0 : i3;
        boolean z20 = !constraintWidget2.mCenter.isConnected();
        boolean[] zArr = constraintWidget2.mIsInBarrier;
        boolean z21 = zArr[0];
        boolean z22 = zArr[1];
        if (constraintWidget2.mHorizontalResolution != 2) {
            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidget2.horizontalRun;
            DependencyNode dependencyNode3 = horizontalWidgetRun2.start;
            if (dependencyNode3.resolved && horizontalWidgetRun2.end.resolved) {
                linearSystem.addEquality(solverVariable12, dependencyNode3.value);
                linearSystem.addEquality(solverVariableCreateObjectVariable3, constraintWidget2.horizontalRun.end.value);
                if (constraintWidget2.mParent != null && z3 && constraintWidget2.isTerminalWidget[0] && !isInHorizontalChain()) {
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(constraintWidget2.mParent.mRight), solverVariableCreateObjectVariable3, 0, 8);
                }
                z7 = z2;
                dimensionBehaviour5 = dimensionBehaviour5;
                z6 = z6;
                solverVariable = solverVariableCreateObjectVariable3;
                solverVariable12 = solverVariable12;
                z8 = z3;
                solverVariable9 = solverVariable9;
                solverVariable10 = solverVariable10;
                solverVariable11 = solverVariable11;
            } else {
                ConstraintWidget constraintWidget5 = constraintWidget2.mParent;
                SolverVariable solverVariableCreateObjectVariable7 = constraintWidget5 != null ? linearSystem.createObjectVariable(constraintWidget5.mRight) : null;
                ConstraintWidget constraintWidget6 = constraintWidget2.mParent;
                z8 = z3;
                z7 = z2;
                solverVariable = solverVariableCreateObjectVariable3;
                solverVariable12 = solverVariable12;
                applyConstraints(linearSystem, true, z8, z7, constraintWidget2.isTerminalWidget[0], constraintWidget6 != null ? linearSystem.createObjectVariable(constraintWidget6.mLeft) : null, solverVariableCreateObjectVariable7, constraintWidget2.mListDimensionBehaviors[0], z19, constraintWidget2.mLeft, constraintWidget2.mRight, constraintWidget2.mX, i20, constraintWidget2.mMinWidth, constraintWidget2.mMaxDimension[0], constraintWidget2.mHorizontalBiasPercent, z18, z5, z4, z21, i18, i2, constraintWidget2.mMatchConstraintMinWidth, constraintWidget2.mMatchConstraintMaxWidth, constraintWidget2.mMatchConstraintPercentWidth, z20);
            }
            VerticalWidgetRun verticalWidgetRun2 = constraintWidget2.verticalRun;
            dependencyNode = verticalWidgetRun2.start;
            if (dependencyNode.resolved || !verticalWidgetRun2.end.resolved) {
                linearSystem2 = linearSystem;
                solverVariable2 = solverVariable9;
                solverVariable3 = solverVariable10;
                solverVariable4 = solverVariable11;
                i5 = 8;
                i6 = 0;
                i7 = 1;
                z9 = true;
            } else {
                linearSystem2 = linearSystem;
                solverVariable4 = solverVariable11;
                linearSystem2.addEquality(solverVariable4, dependencyNode.value);
                solverVariable3 = solverVariable10;
                linearSystem2.addEquality(solverVariable3, constraintWidget2.verticalRun.end.value);
                solverVariable2 = solverVariable9;
                linearSystem2.addEquality(solverVariable2, constraintWidget2.verticalRun.baseline.value);
                ConstraintWidget constraintWidget7 = constraintWidget2.mParent;
                if (constraintWidget7 == null || z4 || !z7) {
                    i5 = 8;
                    i6 = 0;
                    i7 = 1;
                } else {
                    i7 = 1;
                    if (constraintWidget2.isTerminalWidget[1]) {
                        i5 = 8;
                        i6 = 0;
                        linearSystem2.addGreaterThan(linearSystem2.createObjectVariable(constraintWidget7.mBottom), solverVariable3, 0, 8);
                    } else {
                        i5 = 8;
                        i6 = 0;
                    }
                }
                z9 = false;
            }
            if (constraintWidget2.mVerticalResolution != 2 && z9) {
                if (constraintWidget2.mListDimensionBehaviors[i7] == dimensionBehaviour5 || !(constraintWidget2 instanceof ConstraintWidgetContainer)) {
                    z10 = false;
                } else {
                    z10 = true;
                }
                if (z10) {
                    i4 = 0;
                }
                if (z6 || !((i8 = constraintWidget2.mResolvedDimensionRatioSide) == i7 || i8 == -1)) {
                    z11 = false;
                } else {
                    z11 = true;
                }
                constraintWidget = constraintWidget2.mParent;
                if (constraintWidget != null) {
                    solverVariableCreateObjectVariable = linearSystem2.createObjectVariable(constraintWidget.mBottom);
                } else {
                    solverVariableCreateObjectVariable = null;
                }
                ConstraintWidget constraintWidget8 = constraintWidget2.mParent;
                SolverVariable solverVariableCreateObjectVariable8 = constraintWidget8 != null ? linearSystem2.createObjectVariable(constraintWidget8.mTop) : null;
                if (constraintWidget2.mBaselineDistance <= 0 || constraintWidget2.mVisibility == i5) {
                    linearSystem2.addEquality(solverVariable2, solverVariable4, getBaselineDistance(), i5);
                    constraintAnchor = constraintWidget2.mBaseline.mTarget;
                    if (constraintAnchor != null) {
                        linearSystem2.addEquality(solverVariable2, linearSystem2.createObjectVariable(constraintAnchor), i6, i5);
                        if (z7) {
                            linearSystem2.addGreaterThan(solverVariableCreateObjectVariable, linearSystem2.createObjectVariable(constraintWidget2.mBottom), i6, 5);
                        }
                        z12 = false;
                    } else {
                        if (constraintWidget2.mVisibility == i5) {
                            linearSystem2.addEquality(solverVariable2, solverVariable4, i6, i5);
                        }
                        z12 = z20;
                    }
                } else {
                    z12 = z20;
                }
                applyConstraints(linearSystem, false, z7, z8, constraintWidget2.isTerminalWidget[i7], solverVariableCreateObjectVariable8, solverVariableCreateObjectVariable, constraintWidget2.mListDimensionBehaviors[i7], z10, constraintWidget2.mTop, constraintWidget2.mBottom, constraintWidget2.mY, i4, constraintWidget2.mMinHeight, constraintWidget2.mMaxDimension[i7], constraintWidget2.mVerticalBiasPercent, z11, z4, z5, z22, i2, i18, constraintWidget2.mMatchConstraintMinHeight, constraintWidget2.mMatchConstraintMaxHeight, constraintWidget2.mMatchConstraintPercentHeight, z12);
            }
            if (z6) {
                i9 = this.mResolvedDimensionRatioSide;
                float f3 = this.mResolvedDimensionRatio;
                if (i9 == 1) {
                    solverVariable5 = solverVariable3;
                    solverVariable6 = solverVariable4;
                    solverVariable7 = solverVariable;
                    solverVariable8 = solverVariable12;
                } else {
                    solverVariable5 = solverVariable;
                    solverVariable6 = solverVariable12;
                    solverVariable7 = solverVariable3;
                    solverVariable8 = solverVariable4;
                }
                linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable7, solverVariable8, f3, 8);
            }
            if (this.mCenter.isConnected()) {
                linearSystem.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
            }
        }
        z7 = z2;
        z8 = z3;
        solverVariable = solverVariableCreateObjectVariable3;
        constraintWidget2 = this;
        VerticalWidgetRun verticalWidgetRun3 = constraintWidget2.verticalRun;
        dependencyNode = verticalWidgetRun3.start;
        if (dependencyNode.resolved) {
            linearSystem2 = linearSystem;
            solverVariable2 = solverVariable9;
            solverVariable3 = solverVariable10;
            solverVariable4 = solverVariable11;
            i5 = 8;
            i6 = 0;
            i7 = 1;
            z9 = true;
        } else {
            linearSystem2 = linearSystem;
            solverVariable2 = solverVariable9;
            solverVariable3 = solverVariable10;
            solverVariable4 = solverVariable11;
            i5 = 8;
            i6 = 0;
            i7 = 1;
            z9 = true;
        }
        if (constraintWidget2.mVerticalResolution != 2) {
            if (constraintWidget2.mListDimensionBehaviors[i7] == dimensionBehaviour5) {
                z10 = false;
            } else {
                z10 = false;
            }
            if (z10) {
                i4 = 0;
            }
            if (z6) {
                z11 = false;
            } else {
                z11 = false;
            }
            constraintWidget = constraintWidget2.mParent;
            if (constraintWidget != null) {
                solverVariableCreateObjectVariable = linearSystem2.createObjectVariable(constraintWidget.mBottom);
            } else {
                solverVariableCreateObjectVariable = null;
            }
            ConstraintWidget constraintWidget9 = constraintWidget2.mParent;
            if (constraintWidget9 != null) {
            }
            if (constraintWidget2.mBaselineDistance <= 0) {
                linearSystem2.addEquality(solverVariable2, solverVariable4, getBaselineDistance(), i5);
                constraintAnchor = constraintWidget2.mBaseline.mTarget;
                if (constraintAnchor != null) {
                    linearSystem2.addEquality(solverVariable2, linearSystem2.createObjectVariable(constraintAnchor), i6, i5);
                    if (z7) {
                        linearSystem2.addGreaterThan(solverVariableCreateObjectVariable, linearSystem2.createObjectVariable(constraintWidget2.mBottom), i6, 5);
                    }
                    z12 = false;
                } else {
                    if (constraintWidget2.mVisibility == i5) {
                        linearSystem2.addEquality(solverVariable2, solverVariable4, i6, i5);
                    }
                    z12 = z20;
                }
            } else {
                linearSystem2.addEquality(solverVariable2, solverVariable4, getBaselineDistance(), i5);
                constraintAnchor = constraintWidget2.mBaseline.mTarget;
                if (constraintAnchor != null) {
                    linearSystem2.addEquality(solverVariable2, linearSystem2.createObjectVariable(constraintAnchor), i6, i5);
                    if (z7) {
                        linearSystem2.addGreaterThan(solverVariableCreateObjectVariable, linearSystem2.createObjectVariable(constraintWidget2.mBottom), i6, 5);
                    }
                    z12 = false;
                } else {
                    if (constraintWidget2.mVisibility == i5) {
                        linearSystem2.addEquality(solverVariable2, solverVariable4, i6, i5);
                    }
                    z12 = z20;
                }
            }
            applyConstraints(linearSystem, false, z7, z8, constraintWidget2.isTerminalWidget[i7], solverVariableCreateObjectVariable8, solverVariableCreateObjectVariable, constraintWidget2.mListDimensionBehaviors[i7], z10, constraintWidget2.mTop, constraintWidget2.mBottom, constraintWidget2.mY, i4, constraintWidget2.mMinHeight, constraintWidget2.mMaxDimension[i7], constraintWidget2.mVerticalBiasPercent, z11, z4, z5, z22, i2, i18, constraintWidget2.mMatchConstraintMinHeight, constraintWidget2.mMatchConstraintMaxHeight, constraintWidget2.mMatchConstraintPercentHeight, z12);
        }
        if (z6) {
            i9 = this.mResolvedDimensionRatioSide;
            float f4 = this.mResolvedDimensionRatio;
            if (i9 == 1) {
                solverVariable5 = solverVariable3;
                solverVariable6 = solverVariable4;
                solverVariable7 = solverVariable;
                solverVariable8 = solverVariable12;
            } else {
                solverVariable5 = solverVariable;
                solverVariable6 = solverVariable12;
                solverVariable7 = solverVariable3;
                solverVariable8 = solverVariable4;
            }
            linearSystem.addRatio(solverVariable5, solverVariable6, solverVariable7, solverVariable8, f4, 8);
        }
        if (this.mCenter.isConnected()) {
            linearSystem.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float) Math.toRadians(this.mCircleConstraintAngle + 90.0f), this.mCenter.getMargin());
        }
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2) {
        connect(type, constraintWidget, type2, 0);
    }

    public void connectCircularConstraint(ConstraintWidget constraintWidget, float f2, int i2) {
        ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
        immediateConnect(type, constraintWidget, type, i2, 0);
        this.mCircleConstraintAngle = f2;
    }

    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = constraintWidget.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = constraintWidget.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = constraintWidget.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = constraintWidget.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = constraintWidget.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = constraintWidget.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = constraintWidget.mIsWidthWrapContent;
        this.mIsHeightWrapContent = constraintWidget.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = constraintWidget.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = constraintWidget.mResolvedDimensionRatio;
        int[] iArr3 = constraintWidget.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = constraintWidget.mCircleConstraintAngle;
        this.hasBaseline = constraintWidget.hasBaseline;
        this.inPlaceholder = constraintWidget.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        this.mParent = this.mParent == null ? null : map.get(constraintWidget.mParent);
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.mX = constraintWidget.mX;
        this.mY = constraintWidget.mY;
        this.mRelX = constraintWidget.mRelX;
        this.mRelY = constraintWidget.mRelY;
        this.mOffsetX = constraintWidget.mOffsetX;
        this.mOffsetY = constraintWidget.mOffsetY;
        this.mBaselineDistance = constraintWidget.mBaselineDistance;
        this.mMinWidth = constraintWidget.mMinWidth;
        this.mMinHeight = constraintWidget.mMinHeight;
        this.mHorizontalBiasPercent = constraintWidget.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = constraintWidget.mVerticalBiasPercent;
        this.mCompanionWidget = constraintWidget.mCompanionWidget;
        this.mContainerItemSkip = constraintWidget.mContainerItemSkip;
        this.mVisibility = constraintWidget.mVisibility;
        this.mDebugName = constraintWidget.mDebugName;
        this.mType = constraintWidget.mType;
        this.mDistToTop = constraintWidget.mDistToTop;
        this.mDistToLeft = constraintWidget.mDistToLeft;
        this.mDistToRight = constraintWidget.mDistToRight;
        this.mDistToBottom = constraintWidget.mDistToBottom;
        this.mLeftHasCentered = constraintWidget.mLeftHasCentered;
        this.mRightHasCentered = constraintWidget.mRightHasCentered;
        this.mTopHasCentered = constraintWidget.mTopHasCentered;
        this.mBottomHasCentered = constraintWidget.mBottomHasCentered;
        this.mHorizontalWrapVisited = constraintWidget.mHorizontalWrapVisited;
        this.mVerticalWrapVisited = constraintWidget.mVerticalWrapVisited;
        this.mOptimizerMeasurable = constraintWidget.mOptimizerMeasurable;
        this.mGroupsToSolver = constraintWidget.mGroupsToSolver;
        this.mHorizontalChainStyle = constraintWidget.mHorizontalChainStyle;
        this.mVerticalChainStyle = constraintWidget.mVerticalChainStyle;
        this.mHorizontalChainFixedPosition = constraintWidget.mHorizontalChainFixedPosition;
        this.mVerticalChainFixedPosition = constraintWidget.mVerticalChainFixedPosition;
        float[] fArr = this.mWeight;
        float[] fArr2 = constraintWidget.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = constraintWidget.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = constraintWidget.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget2 = constraintWidget.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget2 == null ? null : map.get(constraintWidget2);
        ConstraintWidget constraintWidget3 = constraintWidget.mVerticalNextWidget;
        this.mVerticalNextWidget = constraintWidget3 != null ? map.get(constraintWidget3) : null;
    }

    public void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline);
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public float getBiasPercent(int i2) {
        if (i2 == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (i2 == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public DimensionBehaviour getDimensionBehaviour(int i2) {
        if (i2 == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (i2 == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public boolean getHasBaseline() {
        return this.hasBaseline;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget constraintWidget = this;
        ConstraintWidget constraintWidget2 = null;
        while (constraintWidget2 == null && constraintWidget != null) {
            ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor target = anchor == null ? null : anchor.getTarget();
            ConstraintWidget owner = target == null ? null : target.getOwner();
            if (owner == getParent()) {
                return constraintWidget;
            }
            ConstraintAnchor target2 = owner == null ? null : owner.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            if (target2 == null || target2.getOwner() == constraintWidget) {
                constraintWidget = owner;
            } else {
                constraintWidget2 = constraintWidget;
            }
        }
        return constraintWidget2;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public int getHorizontalMargin() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        int i2 = constraintAnchor != null ? constraintAnchor.mMargin : 0;
        ConstraintAnchor constraintAnchor2 = this.mRight;
        return constraintAnchor2 != null ? i2 + constraintAnchor2.mMargin : i2;
    }

    public int getLeft() {
        return getX();
    }

    public int getLength(int i2) {
        if (i2 == 0) {
            return getWidth();
        }
        if (i2 == 1) {
            return getHeight();
        }
        return 0;
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public ConstraintWidget getNextChainMember(int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i2 != 0) {
            if (i2 == 1 && (constraintAnchor2 = (constraintAnchor = this.mBottom).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public int getOptimizerWrapHeight() {
        int iMax;
        int i2 = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return i2;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            iMax = Math.max(this.mMatchConstraintMinHeight, i2);
        } else {
            iMax = this.mMatchConstraintMinHeight;
            if (iMax > 0) {
                this.mHeight = iMax;
            } else {
                iMax = 0;
            }
        }
        int i3 = this.mMatchConstraintMaxHeight;
        return (i3 <= 0 || i3 >= iMax) ? iMax : i3;
    }

    public int getOptimizerWrapWidth() {
        int iMax;
        int i2 = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return i2;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            iMax = Math.max(this.mMatchConstraintMinWidth, i2);
        } else {
            iMax = this.mMatchConstraintMinWidth;
            if (iMax > 0) {
                this.mWidth = iMax;
            } else {
                iMax = 0;
            }
        }
        int i3 = this.mMatchConstraintMaxWidth;
        return (i3 <= 0 || i3 >= iMax) ? iMax : i3;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public ConstraintWidget getPreviousChainMember(int i2) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (i2 != 0) {
            if (i2 == 1 && (constraintAnchor2 = (constraintAnchor = this.mTop).mTarget) != null && constraintAnchor2.mTarget == constraintAnchor) {
                return constraintAnchor2.mOwner;
            }
            return null;
        }
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return null;
        }
        return constraintAnchor4.mOwner;
    }

    public int getRelativePositioning(int i2) {
        if (i2 == 0) {
            return this.mRelX;
        }
        if (i2 == 1) {
            return this.mRelY;
        }
        return 0;
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getRootX() {
        return this.mX + this.mOffsetX;
    }

    public int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public WidgetRun getRun(int i2) {
        if (i2 == 0) {
            return this.horizontalRun;
        }
        if (i2 == 1) {
            return this.verticalRun;
        }
        return null;
    }

    public int getTop() {
        return getY();
    }

    public String getType() {
        return this.mType;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget constraintWidget = this;
        ConstraintWidget constraintWidget2 = null;
        while (constraintWidget2 == null && constraintWidget != null) {
            ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor target = anchor == null ? null : anchor.getTarget();
            ConstraintWidget owner = target == null ? null : target.getOwner();
            if (owner == getParent()) {
                return constraintWidget;
            }
            ConstraintAnchor target2 = owner == null ? null : owner.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            if (target2 == null || target2.getOwner() == constraintWidget) {
                constraintWidget = owner;
            } else {
                constraintWidget2 = constraintWidget;
            }
        }
        return constraintWidget2;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public int getVerticalMargin() {
        int i2 = this.mLeft != null ? this.mTop.mMargin : 0;
        return this.mRight != null ? i2 + this.mBottom.mMargin : i2;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mX : ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.mX;
    }

    public int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        return (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) ? this.mY : ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.mY;
    }

    public boolean hasBaseline() {
        return this.hasBaseline;
    }

    public void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i2, int i3) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i2, i3, true);
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public boolean isInPlaceholder() {
        return this.inPlaceholder;
    }

    public boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        return constraintAnchor4 != null && constraintAnchor4.mTarget == constraintAnchor3;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtuaLayout;
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public boolean isSpreadHeight() {
        return this.mMatchConstraintDefaultHeight == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinHeight == 0 && this.mMatchConstraintMaxHeight == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadWidth() {
        return this.mMatchConstraintDefaultWidth == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMaxWidth == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        float f2 = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f2;
        this.mVerticalBiasPercent = f2;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedHasRatio = false;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mOptimizerMeasurable = false;
        this.mGroupsToSolver = false;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtuaLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchor(ConstraintAnchor constraintAnchor) {
        if (getParent() != null && (getParent() instanceof ConstraintWidgetContainer) && ((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            return;
        }
        ConstraintAnchor anchor = getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintAnchor anchor3 = getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor anchor4 = getAnchor(ConstraintAnchor.Type.BOTTOM);
        ConstraintAnchor anchor5 = getAnchor(ConstraintAnchor.Type.CENTER);
        ConstraintAnchor anchor6 = getAnchor(ConstraintAnchor.Type.CENTER_X);
        ConstraintAnchor anchor7 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
        if (constraintAnchor != anchor5) {
            if (constraintAnchor == anchor6) {
                if (anchor.isConnected() && anchor2.isConnected() && anchor.getTarget().getOwner() == anchor2.getTarget().getOwner()) {
                    anchor.reset();
                    anchor2.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (constraintAnchor == anchor7) {
                if (anchor3.isConnected() && anchor4.isConnected() && anchor3.getTarget().getOwner() == anchor4.getTarget().getOwner()) {
                    anchor3.reset();
                    anchor4.reset();
                }
            } else if (constraintAnchor == anchor || constraintAnchor == anchor2 ? !(!anchor.isConnected() || anchor.getTarget() != anchor2.getTarget()) : !((constraintAnchor != anchor3 && constraintAnchor != anchor4) || !anchor3.isConnected() || anchor3.getTarget() != anchor4.getTarget())) {
                anchor5.reset();
            }
            constraintAnchor.reset();
        }
        if (anchor.isConnected() && anchor2.isConnected() && anchor.getTarget() == anchor2.getTarget()) {
            anchor.reset();
            anchor2.reset();
        }
        if (anchor3.isConnected() && anchor4.isConnected() && anchor3.getTarget() == anchor4.getTarget()) {
            anchor3.reset();
            anchor4.reset();
        }
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        constraintAnchor.reset();
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent != null && (parent instanceof ConstraintWidgetContainer) && ((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            return;
        }
        int size = this.mAnchors.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAnchors.get(i2).reset();
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public void setBaselineDistance(int i2) {
        this.mBaselineDistance = i2;
        this.hasBaseline = i2 > 0;
    }

    public void setCompanionWidget(Object obj) {
        this.mCompanionWidget = obj;
    }

    public void setContainerItemSkip(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mContainerItemSkip = i2;
    }

    public void setDebugName(String str) {
        this.mDebugName = str;
    }

    public void setDebugSolverName(LinearSystem linearSystem, String str) {
        this.mDebugName = str;
        SolverVariable solverVariableCreateObjectVariable = linearSystem.createObjectVariable(this.mLeft);
        SolverVariable solverVariableCreateObjectVariable2 = linearSystem.createObjectVariable(this.mTop);
        SolverVariable solverVariableCreateObjectVariable3 = linearSystem.createObjectVariable(this.mRight);
        SolverVariable solverVariableCreateObjectVariable4 = linearSystem.createObjectVariable(this.mBottom);
        solverVariableCreateObjectVariable.setName(str + ".left");
        solverVariableCreateObjectVariable2.setName(str + ".top");
        solverVariableCreateObjectVariable3.setName(str + ".right");
        solverVariableCreateObjectVariable4.setName(str + ".bottom");
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline).setName(str + ".baseline");
        }
    }

    public void setDimension(int i2, int i3) {
        this.mWidth = i2;
        int i4 = this.mMinWidth;
        if (i2 < i4) {
            this.mWidth = i4;
        }
        this.mHeight = i3;
        int i5 = this.mMinHeight;
        if (i3 < i5) {
            this.mHeight = i5;
        }
    }

    public void setDimensionRatio(float f2, int i2) {
        this.mDimensionRatio = f2;
        this.mDimensionRatioSide = i2;
    }

    public void setFrame(int i2, int i3, int i4) {
        if (i4 == 0) {
            setHorizontalDimension(i2, i3);
        } else if (i4 == 1) {
            setVerticalDimension(i2, i3);
        }
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int i2) {
        ConstraintAnchor constraintAnchor;
        int i3 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[type.ordinal()];
        if (i3 == 1) {
            constraintAnchor = this.mLeft;
        } else if (i3 == 2) {
            constraintAnchor = this.mTop;
        } else if (i3 == 3) {
            constraintAnchor = this.mRight;
        } else if (i3 != 4) {
            return;
        } else {
            constraintAnchor = this.mBottom;
        }
        constraintAnchor.mGoneMargin = i2;
    }

    public void setHasBaseline(boolean z2) {
        this.hasBaseline = z2;
    }

    public void setHeight(int i2) {
        this.mHeight = i2;
        int i3 = this.mMinHeight;
        if (i2 < i3) {
            this.mHeight = i3;
        }
    }

    public void setHeightWrapContent(boolean z2) {
        this.mIsHeightWrapContent = z2;
    }

    public void setHorizontalBiasPercent(float f2) {
        this.mHorizontalBiasPercent = f2;
    }

    public void setHorizontalChainStyle(int i2) {
        this.mHorizontalChainStyle = i2;
    }

    public void setHorizontalDimension(int i2, int i3) {
        this.mX = i2;
        int i4 = i3 - i2;
        this.mWidth = i4;
        int i5 = this.mMinWidth;
        if (i4 < i5) {
            this.mWidth = i5;
        }
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public void setHorizontalMatchStyle(int i2, int i3, int i4, float f2) {
        this.mMatchConstraintDefaultWidth = i2;
        this.mMatchConstraintMinWidth = i3;
        if (i4 == Integer.MAX_VALUE) {
            i4 = 0;
        }
        this.mMatchConstraintMaxWidth = i4;
        this.mMatchConstraintPercentWidth = f2;
        if (f2 <= 0.0f || f2 >= 1.0f || i2 != 0) {
            return;
        }
        this.mMatchConstraintDefaultWidth = 2;
    }

    public void setHorizontalWeight(float f2) {
        this.mWeight[0] = f2;
    }

    public void setInBarrier(int i2, boolean z2) {
        this.mIsInBarrier[i2] = z2;
    }

    public void setInPlaceholder(boolean z2) {
        this.inPlaceholder = z2;
    }

    public void setInVirtualLayout(boolean z2) {
        this.mInVirtuaLayout = z2;
    }

    public void setLength(int i2, int i3) {
        if (i3 == 0) {
            setWidth(i2);
        } else if (i3 == 1) {
            setHeight(i2);
        }
    }

    public void setMaxHeight(int i2) {
        this.mMaxDimension[1] = i2;
    }

    public void setMaxWidth(int i2) {
        this.mMaxDimension[0] = i2;
    }

    public void setMinHeight(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mMinHeight = i2;
    }

    public void setMinWidth(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mMinWidth = i2;
    }

    public void setOffset(int i2, int i3) {
        this.mOffsetX = i2;
        this.mOffsetY = i3;
    }

    public void setOrigin(int i2, int i3) {
        this.mX = i2;
        this.mY = i3;
    }

    public void setParent(ConstraintWidget constraintWidget) {
        this.mParent = constraintWidget;
    }

    public void setRelativePositioning(int i2, int i3) {
        if (i3 == 0) {
            this.mRelX = i2;
        } else if (i3 == 1) {
            this.mRelY = i2;
        }
    }

    public void setType(String str) {
        this.mType = str;
    }

    public void setVerticalBiasPercent(float f2) {
        this.mVerticalBiasPercent = f2;
    }

    public void setVerticalChainStyle(int i2) {
        this.mVerticalChainStyle = i2;
    }

    public void setVerticalDimension(int i2, int i3) {
        this.mY = i2;
        int i4 = i3 - i2;
        this.mHeight = i4;
        int i5 = this.mMinHeight;
        if (i4 < i5) {
            this.mHeight = i5;
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public void setVerticalMatchStyle(int i2, int i3, int i4, float f2) {
        this.mMatchConstraintDefaultHeight = i2;
        this.mMatchConstraintMinHeight = i3;
        if (i4 == Integer.MAX_VALUE) {
            i4 = 0;
        }
        this.mMatchConstraintMaxHeight = i4;
        this.mMatchConstraintPercentHeight = f2;
        if (f2 <= 0.0f || f2 >= 1.0f || i2 != 0) {
            return;
        }
        this.mMatchConstraintDefaultHeight = 2;
    }

    public void setVerticalWeight(float f2) {
        this.mWeight[1] = f2;
    }

    public void setVisibility(int i2) {
        this.mVisibility = i2;
    }

    public void setWidth(int i2) {
        this.mWidth = i2;
        int i3 = this.mMinWidth;
        if (i2 < i3) {
            this.mWidth = i3;
        }
    }

    public void setWidthWrapContent(boolean z2) {
        this.mIsWidthWrapContent = z2;
    }

    public void setX(int i2) {
        this.mX = i2;
    }

    public void setY(int i2) {
        this.mY = i2;
    }

    public void setupDimensionRatio(boolean z2, boolean z3, boolean z4, boolean z5) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (z4 && !z5) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!z4 && z5) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            int i2 = this.mMatchConstraintMinWidth;
            if (i2 > 0 && this.mMatchConstraintMinHeight == 0) {
                this.mResolvedDimensionRatioSide = 0;
            } else {
                if (i2 != 0 || this.mMatchConstraintMinHeight <= 0) {
                    return;
                }
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mType != null ? a.g(new StringBuilder("type: "), this.mType, " ") : "");
        sb.append(this.mDebugName != null ? a.g(new StringBuilder("id: "), this.mDebugName, " ") : "");
        sb.append("(");
        sb.append(this.mX);
        sb.append(", ");
        sb.append(this.mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        return sb.toString();
    }

    public void updateFromRuns(boolean z2, boolean z3) {
        int i2;
        int i3;
        boolean zIsResolved = z2 & this.horizontalRun.isResolved();
        boolean zIsResolved2 = z3 & this.verticalRun.isResolved();
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        int i4 = horizontalWidgetRun.start.value;
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        int i5 = verticalWidgetRun.start.value;
        int i6 = horizontalWidgetRun.end.value;
        int i7 = verticalWidgetRun.end.value;
        int i8 = i7 - i5;
        if (i6 - i4 < 0 || i8 < 0 || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE || i7 == Integer.MIN_VALUE || i7 == Integer.MAX_VALUE) {
            i6 = 0;
            i4 = 0;
            i7 = 0;
            i5 = 0;
        }
        int i9 = i6 - i4;
        int i10 = i7 - i5;
        if (zIsResolved) {
            this.mX = i4;
        }
        if (zIsResolved2) {
            this.mY = i5;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (zIsResolved) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && i9 < (i3 = this.mWidth)) {
                i9 = i3;
            }
            this.mWidth = i9;
            int i11 = this.mMinWidth;
            if (i9 < i11) {
                this.mWidth = i11;
            }
        }
        if (zIsResolved2) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && i10 < (i2 = this.mHeight)) {
                i10 = i2;
            }
            this.mHeight = i10;
            int i12 = this.mMinHeight;
            if (i10 < i12) {
                this.mHeight = i12;
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem) {
        int objectVariableValue = linearSystem.getObjectVariableValue(this.mLeft);
        int objectVariableValue2 = linearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = linearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = linearSystem.getObjectVariableValue(this.mBottom);
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        DependencyNode dependencyNode = horizontalWidgetRun.start;
        if (dependencyNode.resolved) {
            DependencyNode dependencyNode2 = horizontalWidgetRun.end;
            if (dependencyNode2.resolved) {
                objectVariableValue = dependencyNode.value;
                objectVariableValue3 = dependencyNode2.value;
            }
        }
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        DependencyNode dependencyNode3 = verticalWidgetRun.start;
        if (dependencyNode3.resolved) {
            DependencyNode dependencyNode4 = verticalWidgetRun.end;
            if (dependencyNode4.resolved) {
                objectVariableValue2 = dependencyNode3.value;
                objectVariableValue4 = dependencyNode4.value;
            }
        }
        int i2 = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i2 < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue = 0;
            objectVariableValue4 = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
        }
        setFrame(objectVariableValue, objectVariableValue2, objectVariableValue3, objectVariableValue4);
    }

    public ConstraintWidget(int i2, int i3) {
        this(0, 0, i2, i3);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01bc A[PHI: r9 r11
      0x01bc: PHI (r9v3 androidx.constraintlayout.solver.widgets.ConstraintAnchor) = 
      (r9v2 androidx.constraintlayout.solver.widgets.ConstraintAnchor)
      (r9v5 androidx.constraintlayout.solver.widgets.ConstraintAnchor)
     binds: [B:100:0x01ba, B:91:0x0191] A[DONT_GENERATE, DONT_INLINE]
      0x01bc: PHI (r11v6 androidx.constraintlayout.solver.widgets.ConstraintAnchor) = 
      (r11v5 androidx.constraintlayout.solver.widgets.ConstraintAnchor)
      (r11v9 androidx.constraintlayout.solver.widgets.ConstraintAnchor)
     binds: [B:100:0x01ba, B:91:0x0191] A[DONT_GENERATE, DONT_INLINE]] */
    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i2) {
        ConstraintAnchor opposite;
        ConstraintAnchor anchor;
        ConstraintAnchor anchor2;
        ConstraintAnchor.Type type3;
        ConstraintAnchor.Type type4;
        ConstraintAnchor anchor3;
        boolean z2;
        ConstraintAnchor.Type type5;
        ConstraintAnchor.Type type6 = ConstraintAnchor.Type.CENTER;
        if (type == type6) {
            ConstraintAnchor.Type type7 = ConstraintAnchor.Type.LEFT;
            if (type2 == type6) {
                ConstraintAnchor anchor4 = getAnchor(type7);
                ConstraintAnchor.Type type8 = ConstraintAnchor.Type.RIGHT;
                ConstraintAnchor anchor5 = getAnchor(type8);
                ConstraintAnchor.Type type9 = ConstraintAnchor.Type.TOP;
                ConstraintAnchor anchor6 = getAnchor(type9);
                ConstraintAnchor.Type type10 = ConstraintAnchor.Type.BOTTOM;
                ConstraintAnchor anchor7 = getAnchor(type10);
                boolean z3 = true;
                if ((anchor4 == null || !anchor4.isConnected()) && (anchor5 == null || !anchor5.isConnected())) {
                    connect(type7, constraintWidget, type7, 0);
                    connect(type8, constraintWidget, type8, 0);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if ((anchor6 == null || !anchor6.isConnected()) && (anchor7 == null || !anchor7.isConnected())) {
                    connect(type9, constraintWidget, type9, 0);
                    connect(type10, constraintWidget, type10, 0);
                } else {
                    z3 = false;
                }
                if (!z2 || !z3) {
                    if (z2) {
                        type5 = ConstraintAnchor.Type.CENTER_X;
                    } else if (!z3) {
                        return;
                    } else {
                        type5 = ConstraintAnchor.Type.CENTER_Y;
                    }
                    getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
                    return;
                }
                anchor2 = getAnchor(type6);
                anchor3 = constraintWidget.getAnchor(type6);
            } else {
                if (type2 == type7 || type2 == ConstraintAnchor.Type.RIGHT) {
                    connect(type7, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget, type2, 0);
                } else {
                    ConstraintAnchor.Type type11 = ConstraintAnchor.Type.TOP;
                    if (type2 != type11 && type2 != ConstraintAnchor.Type.BOTTOM) {
                        return;
                    }
                    connect(type11, constraintWidget, type2, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget, type2, 0);
                }
                anchor2 = getAnchor(type6);
                anchor3 = constraintWidget.getAnchor(type2);
            }
        } else {
            ConstraintAnchor.Type type12 = ConstraintAnchor.Type.CENTER_X;
            if (type == type12 && (type2 == (type4 = ConstraintAnchor.Type.LEFT) || type2 == ConstraintAnchor.Type.RIGHT)) {
                ConstraintAnchor anchor8 = getAnchor(type4);
                anchor3 = constraintWidget.getAnchor(type2);
                ConstraintAnchor anchor9 = getAnchor(ConstraintAnchor.Type.RIGHT);
                anchor8.connect(anchor3, 0);
                anchor9.connect(anchor3, 0);
                anchor2 = getAnchor(type12);
            } else {
                ConstraintAnchor.Type type13 = ConstraintAnchor.Type.CENTER_Y;
                if (type == type13 && (type2 == (type3 = ConstraintAnchor.Type.TOP) || type2 == ConstraintAnchor.Type.BOTTOM)) {
                    ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
                    getAnchor(type3).connect(anchor10, 0);
                    getAnchor(ConstraintAnchor.Type.BOTTOM).connect(anchor10, 0);
                    getAnchor(type13).connect(anchor10, 0);
                    return;
                }
                if (type == type12 && type2 == type12) {
                    ConstraintAnchor.Type type14 = ConstraintAnchor.Type.LEFT;
                    getAnchor(type14).connect(constraintWidget.getAnchor(type14), 0);
                    ConstraintAnchor.Type type15 = ConstraintAnchor.Type.RIGHT;
                    getAnchor(type15).connect(constraintWidget.getAnchor(type15), 0);
                    anchor2 = getAnchor(type12);
                } else {
                    if (type != type13 || type2 != type13) {
                        ConstraintAnchor anchor11 = getAnchor(type);
                        ConstraintAnchor anchor12 = constraintWidget.getAnchor(type2);
                        if (anchor11.isValidConnection(anchor12)) {
                            ConstraintAnchor.Type type16 = ConstraintAnchor.Type.BASELINE;
                            if (type == type16) {
                                ConstraintAnchor anchor13 = getAnchor(ConstraintAnchor.Type.TOP);
                                ConstraintAnchor anchor14 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                                if (anchor13 != null) {
                                    anchor13.reset();
                                }
                                if (anchor14 != null) {
                                    anchor14.reset();
                                }
                                i2 = 0;
                            } else if (type == ConstraintAnchor.Type.TOP || type == ConstraintAnchor.Type.BOTTOM) {
                                ConstraintAnchor anchor15 = getAnchor(type16);
                                if (anchor15 != null) {
                                    anchor15.reset();
                                }
                                ConstraintAnchor anchor16 = getAnchor(type6);
                                if (anchor16.getTarget() != anchor12) {
                                    anchor16.reset();
                                }
                                opposite = getAnchor(type).getOpposite();
                                anchor = getAnchor(type13);
                                if (anchor.isConnected()) {
                                    opposite.reset();
                                    anchor.reset();
                                }
                            } else if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                                ConstraintAnchor anchor17 = getAnchor(type6);
                                if (anchor17.getTarget() != anchor12) {
                                    anchor17.reset();
                                }
                                opposite = getAnchor(type).getOpposite();
                                anchor = getAnchor(type12);
                                if (anchor.isConnected()) {
                                    opposite.reset();
                                    anchor.reset();
                                }
                            }
                            anchor11.connect(anchor12, i2);
                            return;
                        }
                        return;
                    }
                    ConstraintAnchor.Type type17 = ConstraintAnchor.Type.TOP;
                    getAnchor(type17).connect(constraintWidget.getAnchor(type17), 0);
                    ConstraintAnchor.Type type18 = ConstraintAnchor.Type.BOTTOM;
                    getAnchor(type18).connect(constraintWidget.getAnchor(type18), 0);
                    anchor2 = getAnchor(type13);
                }
                anchor3 = constraintWidget.getAnchor(type2);
            }
        }
        anchor2.connect(anchor3, 0);
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0087 A[PHI: r0
      0x0087: PHI (r0v2 int) = (r0v1 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int), (r0v0 int) binds: [B:39:0x0086, B:36:0x007f, B:24:0x0051, B:26:0x0057, B:28:0x0063, B:30:0x0067] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x0087 -> B:41:0x0088). Please report as a decompilation issue!!! */
    public void setDimensionRatio(String str) {
        float fAbs;
        int i2 = 0;
        if (str == null || str.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int length = str.length();
        int iIndexOf = str.indexOf(44);
        int i3 = 0;
        int i4 = -1;
        if (iIndexOf > 0 && iIndexOf < length - 1) {
            String strSubstring = str.substring(0, iIndexOf);
            if (!strSubstring.equalsIgnoreCase("W")) {
                i3 = strSubstring.equalsIgnoreCase("H") ? 1 : -1;
            }
            i4 = i3;
            i3 = iIndexOf + 1;
        }
        int iIndexOf2 = str.indexOf(58);
        try {
            if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                String strSubstring2 = str.substring(i3);
                if (strSubstring2.length() > 0) {
                    fAbs = Float.parseFloat(strSubstring2);
                } else {
                    fAbs = 0.0f;
                }
            } else {
                String strSubstring3 = str.substring(i3, iIndexOf2);
                String strSubstring4 = str.substring(iIndexOf2 + 1);
                if (strSubstring3.length() <= 0 || strSubstring4.length() <= 0) {
                    fAbs = 0.0f;
                } else {
                    float f2 = Float.parseFloat(strSubstring3);
                    float f3 = Float.parseFloat(strSubstring4);
                    if (f2 <= 0.0f || f3 <= 0.0f) {
                        fAbs = 0.0f;
                    } else {
                        fAbs = i4 == 1 ? Math.abs(f3 / f2) : Math.abs(f2 / f3);
                    }
                }
            }
        } catch (NumberFormatException unused) {
        }
        i2 = (fAbs > i2 ? 1 : (fAbs == i2 ? 0 : -1));
        if (i2 > 0) {
            this.mDimensionRatio = fAbs;
            this.mDimensionRatioSide = i4;
        }
    }

    public void setFrame(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8 = i4 - i2;
        int i9 = i5 - i3;
        this.mX = i2;
        this.mY = i3;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && i8 < (i7 = this.mWidth)) {
            i8 = i7;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && i9 < (i6 = this.mHeight)) {
            i9 = i6;
        }
        this.mWidth = i8;
        this.mHeight = i9;
        int i10 = this.mMinHeight;
        if (i9 < i10) {
            this.mHeight = i10;
        }
        int i11 = this.mMinWidth;
        if (i8 < i11) {
            this.mWidth = i11;
        }
    }

    public ConstraintWidget(int i2, int i3, int i4, int i5) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = new HorizontalWidgetRun(this);
        this.verticalRun = new VerticalWidgetRun(this);
        this.isTerminalWidget = new boolean[]{true, true};
        this.wrapMeasure = new int[]{0, 0, 0, 0};
        this.mResolvedHasRatio = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtuaLayout = false;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f2 = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f2;
        this.mVerticalBiasPercent = f2;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mOptimizerMeasurable = false;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.mX = i2;
        this.mY = i3;
        this.mWidth = i4;
        this.mHeight = i5;
        addAnchors();
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2) {
        if (constraintAnchor.getOwner() == this) {
            connect(constraintAnchor.getType(), constraintAnchor2.getOwner(), constraintAnchor2.getType(), i2);
        }
    }
}
