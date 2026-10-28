package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ConstraintWidgetContainer extends WidgetContainer {
    private static final boolean DEBUG = false;
    static final boolean DEBUG_GRAPH = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final int MAX_ITERATIONS = 8;
    BasicMeasure mBasicMeasureSolver;
    int mDebugSolverPassCount;
    public DependencyGraph mDependencyGraph;
    public boolean mGroupsWrapOptimized;
    private boolean mHeightMeasuredTooSmall;
    ChainHead[] mHorizontalChainsArray;
    int mHorizontalChainsSize;
    public boolean mHorizontalWrapOptimized;
    private boolean mIsRtl;
    protected BasicMeasure.Measurer mMeasurer;
    public Metrics mMetrics;
    private int mOptimizationLevel;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    public boolean mSkipSolver;
    protected LinearSystem mSystem;
    ChainHead[] mVerticalChainsArray;
    int mVerticalChainsSize;
    public boolean mVerticalWrapOptimized;
    private boolean mWidthMeasuredTooSmall;
    public int mWrapFixedHeight;
    public int mWrapFixedWidth;

    public ConstraintWidgetContainer() {
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = Optimizer.OPTIMIZATION_STANDARD;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    private void addHorizontalChain(ConstraintWidget constraintWidget) {
        int i2 = this.mHorizontalChainsSize + 1;
        ChainHead[] chainHeadArr = this.mHorizontalChainsArray;
        if (i2 >= chainHeadArr.length) {
            this.mHorizontalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(constraintWidget, 0, isRtl());
        this.mHorizontalChainsSize++;
    }

    private void addVerticalChain(ConstraintWidget constraintWidget) {
        int i2 = this.mVerticalChainsSize + 1;
        ChainHead[] chainHeadArr = this.mVerticalChainsArray;
        if (i2 >= chainHeadArr.length) {
            this.mVerticalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(constraintWidget, 1, isRtl());
        this.mVerticalChainsSize++;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    public void addChain(ConstraintWidget constraintWidget, int i2) {
        if (i2 == 0) {
            addHorizontalChain(constraintWidget);
        } else if (i2 == 1) {
            addVerticalChain(constraintWidget);
        }
    }

    public boolean addChildrenToSolver(LinearSystem linearSystem) {
        addToSolver(linearSystem);
        int size = this.mChildren.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i2);
            constraintWidget.setInBarrier(0, false);
            constraintWidget.setInBarrier(1, false);
            if (constraintWidget instanceof Barrier) {
                z2 = true;
            }
        }
        if (z2) {
            for (int i3 = 0; i3 < size; i3++) {
                ConstraintWidget constraintWidget2 = this.mChildren.get(i3);
                if (constraintWidget2 instanceof Barrier) {
                    ((Barrier) constraintWidget2).markWidgets();
                }
            }
        }
        for (int i4 = 0; i4 < size; i4++) {
            ConstraintWidget constraintWidget3 = this.mChildren.get(i4);
            if (constraintWidget3.addFirst()) {
                constraintWidget3.addToSolver(linearSystem);
            }
        }
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget4 = this.mChildren.get(i5);
            if (constraintWidget4 instanceof ConstraintWidgetContainer) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget4.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour == dimensionBehaviour3) {
                    constraintWidget4.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (dimensionBehaviour2 == dimensionBehaviour3) {
                    constraintWidget4.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                constraintWidget4.addToSolver(linearSystem);
                if (dimensionBehaviour == dimensionBehaviour3) {
                    constraintWidget4.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (dimensionBehaviour2 == dimensionBehaviour3) {
                    constraintWidget4.setVerticalDimensionBehaviour(dimensionBehaviour2);
                }
            } else {
                Optimizer.checkMatchParent(this, linearSystem, constraintWidget4);
                if (!constraintWidget4.addFirst()) {
                    constraintWidget4.addToSolver(linearSystem);
                }
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 0);
        }
        if (this.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 1);
        }
        return true;
    }

    public void defineTerminalWidgets() {
        this.mDependencyGraph.defineTerminalWidgets(getHorizontalDimensionBehaviour(), getVerticalDimensionBehaviour());
    }

    public boolean directMeasure(boolean z2) {
        return this.mDependencyGraph.directMeasure(z2);
    }

    public boolean directMeasureSetup(boolean z2) {
        return this.mDependencyGraph.directMeasureSetup(z2);
    }

    public boolean directMeasureWithOrientation(boolean z2, int i2) {
        return this.mDependencyGraph.directMeasureWithOrientation(z2, i2);
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        this.mSystem.fillMetrics(metrics);
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<>();
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i2);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 0) {
                    arrayList.add(guideline);
                }
            }
        }
        return arrayList;
    }

    public BasicMeasure.Measurer getMeasurer() {
        return this.mMeasurer;
    }

    public int getOptimizationLevel() {
        return this.mOptimizationLevel;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public String getType() {
        return "ConstraintLayout";
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<>();
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i2);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 1) {
                    arrayList.add(guideline);
                }
            }
        }
        return arrayList;
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public void invalidateGraph() {
        this.mDependencyGraph.invalidateGraph();
    }

    public void invalidateMeasures() {
        this.mDependencyGraph.invalidateMeasures();
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public boolean isRtl() {
        return this.mIsRtl;
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    /* JADX WARN: Code duplicated, block: B:84:0x01c3 A[PHI: r0 r5
      0x01c3: PHI (r0v26 boolean) = (r0v25 boolean), (r0v28 boolean), (r0v28 boolean), (r0v28 boolean) binds: [B:71:0x0188, B:79:0x01ab, B:80:0x01ad, B:82:0x01b3] A[DONT_GENERATE, DONT_INLINE]
      0x01c3: PHI (r5v11 boolean) = (r5v10 boolean), (r5v13 boolean), (r5v13 boolean), (r5v13 boolean) binds: [B:71:0x0188, B:79:0x01ab, B:80:0x01ad, B:82:0x01b3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r11v9, types: [boolean] */
    @Override // androidx.constraintlayout.solver.widgets.WidgetContainer
    public void layout() {
        boolean z2;
        ?? r11;
        boolean z3;
        this.mX = 0;
        this.mY = 0;
        int iMax = Math.max(0, getWidth());
        int iMax2 = Math.max(0, getHeight());
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        boolean z4 = optimizeFor(64) || optimizeFor(128);
        LinearSystem linearSystem = this.mSystem;
        linearSystem.graphOptimizer = false;
        linearSystem.newgraphOptimizer = false;
        if (this.mOptimizationLevel != 0 && z4) {
            linearSystem.newgraphOptimizer = true;
        }
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[1];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
        ArrayList<ConstraintWidget> arrayList = this.mChildren;
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z5 = horizontalDimensionBehaviour == dimensionBehaviour3 || getVerticalDimensionBehaviour() == dimensionBehaviour3;
        resetChains();
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i2);
            if (constraintWidget instanceof WidgetContainer) {
                ((WidgetContainer) constraintWidget).layout();
            }
        }
        int i3 = 0;
        boolean zAddChildrenToSolver = true;
        boolean z6 = false;
        while (zAddChildrenToSolver) {
            int i4 = i3 + 1;
            try {
                this.mSystem.reset();
                resetChains();
                createObjectVariables(this.mSystem);
                for (int i5 = 0; i5 < size; i5++) {
                    this.mChildren.get(i5).createObjectVariables(this.mSystem);
                }
                zAddChildrenToSolver = addChildrenToSolver(this.mSystem);
                if (zAddChildrenToSolver) {
                    this.mSystem.minimize();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                System.out.println("EXCEPTION : " + e2);
            }
            LinearSystem linearSystem2 = this.mSystem;
            if (zAddChildrenToSolver) {
                updateChildrenFromSolver(linearSystem2, Optimizer.flags);
            } else {
                updateFromSolver(linearSystem2);
                for (int i6 = 0; i6 < size; i6++) {
                    this.mChildren.get(i6).updateFromSolver(this.mSystem);
                }
            }
            if (z5 && i4 < 8 && Optimizer.flags[2]) {
                int iMax3 = 0;
                int iMax4 = 0;
                for (int i7 = 0; i7 < size; i7++) {
                    ConstraintWidget constraintWidget2 = this.mChildren.get(i7);
                    iMax3 = Math.max(iMax3, constraintWidget2.getWidth() + constraintWidget2.mX);
                    iMax4 = Math.max(iMax4, constraintWidget2.getHeight() + constraintWidget2.mY);
                }
                int iMax5 = Math.max(this.mMinWidth, iMax3);
                int iMax6 = Math.max(this.mMinHeight, iMax4);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour2 != dimensionBehaviour4 || getWidth() >= iMax5) {
                    z2 = false;
                } else {
                    setWidth(iMax5);
                    this.mListDimensionBehaviors[0] = dimensionBehaviour4;
                    z2 = true;
                    z6 = true;
                }
                if (dimensionBehaviour == dimensionBehaviour4 && getHeight() < iMax6) {
                    setHeight(iMax6);
                    this.mListDimensionBehaviors[1] = dimensionBehaviour4;
                    z2 = true;
                    z6 = true;
                }
            } else {
                z2 = false;
            }
            int iMax7 = Math.max(this.mMinWidth, getWidth());
            if (iMax7 > getWidth()) {
                setWidth(iMax7);
                this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                z2 = true;
                z6 = true;
            }
            int iMax8 = Math.max(this.mMinHeight, getHeight());
            if (iMax8 > getHeight()) {
                setHeight(iMax8);
                r11 = 1;
                this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                z2 = true;
                z3 = true;
            } else {
                r11 = 1;
                z3 = z6;
            }
            if (z3) {
                zAddChildrenToSolver = z2;
                z6 = z3;
            } else {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = this.mListDimensionBehaviors[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (dimensionBehaviour5 == dimensionBehaviour6 && iMax > 0 && getWidth() > iMax) {
                    this.mWidthMeasuredTooSmall = r11;
                    this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                    setWidth(iMax);
                    z2 = true;
                    z3 = true;
                }
                if (this.mListDimensionBehaviors[r11] != dimensionBehaviour6 || iMax2 <= 0 || getHeight() <= iMax2) {
                    zAddChildrenToSolver = z2;
                    z6 = z3;
                } else {
                    this.mHeightMeasuredTooSmall = r11;
                    this.mListDimensionBehaviors[r11] = ConstraintWidget.DimensionBehaviour.FIXED;
                    setHeight(iMax2);
                    zAddChildrenToSolver = true;
                    z6 = true;
                }
            }
            i3 = i4;
        }
        this.mChildren = arrayList;
        if (z6) {
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = this.mListDimensionBehaviors;
            dimensionBehaviourArr2[0] = dimensionBehaviour2;
            dimensionBehaviourArr2[1] = dimensionBehaviour;
        }
        resetSolverVariables(this.mSystem.getCache());
    }

    public long measure(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.mPaddingLeft = i9;
        this.mPaddingTop = i10;
        return this.mBasicMeasureSolver.solverMeasure(this, i2, i9, i10, i3, i4, i5, i6, i7, i8);
    }

    public boolean optimizeFor(int i2) {
        return (this.mOptimizationLevel & i2) == i2;
    }

    @Override // androidx.constraintlayout.solver.widgets.WidgetContainer, androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        this.mSkipSolver = false;
        super.reset();
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
        this.mDependencyGraph.setMeasurer(measurer);
    }

    public void setOptimizationLevel(int i2) {
        this.mOptimizationLevel = i2;
        LinearSystem.OPTIMIZED_ENGINE = Optimizer.enabled(i2, 256);
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
        this.mPaddingLeft = i2;
        this.mPaddingTop = i3;
        this.mPaddingRight = i4;
        this.mPaddingBottom = i5;
    }

    public void setRtl(boolean z2) {
        this.mIsRtl = z2;
    }

    public void updateChildrenFromSolver(LinearSystem linearSystem, boolean[] zArr) {
        zArr[2] = false;
        updateFromSolver(linearSystem);
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mChildren.get(i2).updateFromSolver(linearSystem);
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void updateFromRuns(boolean z2, boolean z3) {
        super.updateFromRuns(z2, z3);
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mChildren.get(i2).updateFromRuns(z2, z3);
        }
    }

    public void updateHierarchy() {
        this.mBasicMeasureSolver.updateHierarchy(this);
    }

    public ConstraintWidgetContainer(int i2, int i3) {
        super(i2, i3);
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = Optimizer.OPTIMIZATION_STANDARD;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }

    public ConstraintWidgetContainer(int i2, int i3, int i4, int i5) {
        super(i2, i3, i4, i5);
        this.mBasicMeasureSolver = new BasicMeasure(this);
        this.mDependencyGraph = new DependencyGraph(this);
        this.mMeasurer = null;
        this.mIsRtl = false;
        this.mSystem = new LinearSystem();
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
        this.mVerticalChainsArray = new ChainHead[4];
        this.mHorizontalChainsArray = new ChainHead[4];
        this.mGroupsWrapOptimized = false;
        this.mHorizontalWrapOptimized = false;
        this.mVerticalWrapOptimized = false;
        this.mWrapFixedWidth = 0;
        this.mWrapFixedHeight = 0;
        this.mOptimizationLevel = Optimizer.OPTIMIZATION_STANDARD;
        this.mSkipSolver = false;
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        this.mDebugSolverPassCount = 0;
    }
}
