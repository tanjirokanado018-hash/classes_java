package androidx.constraintlayout.solver.widgets;

import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Barrier extends HelperWidget {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private int mBarrierType = 0;
    private boolean mAllowsGoneWidget = true;
    private int mMargin = 0;

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        ConstraintAnchor[] constraintAnchorArr;
        boolean z2;
        SolverVariable solverVariable;
        ConstraintAnchor constraintAnchor;
        int i2;
        int i3;
        int i4;
        ConstraintAnchor[] constraintAnchorArr2 = this.mListAnchors;
        constraintAnchorArr2[0] = this.mLeft;
        constraintAnchorArr2[2] = this.mTop;
        constraintAnchorArr2[1] = this.mRight;
        constraintAnchorArr2[3] = this.mBottom;
        int i5 = 0;
        while (true) {
            constraintAnchorArr = this.mListAnchors;
            if (i5 >= constraintAnchorArr.length) {
                break;
            }
            ConstraintAnchor constraintAnchor2 = constraintAnchorArr[i5];
            constraintAnchor2.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor2);
            i5++;
        }
        int i6 = this.mBarrierType;
        if (i6 < 0 || i6 >= 4) {
            return;
        }
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i6];
        int i7 = 0;
        while (true) {
            if (i7 >= this.mWidgetsCount) {
                z2 = false;
                break;
            }
            ConstraintWidget constraintWidget = this.mWidgets[i7];
            if ((this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) && ((((i3 = this.mBarrierType) == 0 || i3 == 1) && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget != null) || (((i4 = this.mBarrierType) == 2 || i4 == 3) && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null))) {
                z2 = true;
                break;
            }
            i7++;
        }
        boolean z3 = this.mLeft.hasCenteredDependents() || this.mRight.hasCenteredDependents();
        boolean z4 = this.mTop.hasCenteredDependents() || this.mBottom.hasCenteredDependents();
        int i8 = !(!z2 && (((i2 = this.mBarrierType) == 0 && z3) || ((i2 == 2 && z4) || ((i2 == 1 && z3) || (i2 == 3 && z4))))) ? 4 : 5;
        for (int i9 = 0; i9 < this.mWidgetsCount; i9++) {
            ConstraintWidget constraintWidget2 = this.mWidgets[i9];
            if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                SolverVariable solverVariableCreateObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.mBarrierType]);
                ConstraintAnchor[] constraintAnchorArr3 = constraintWidget2.mListAnchors;
                int i10 = this.mBarrierType;
                ConstraintAnchor constraintAnchor4 = constraintAnchorArr3[i10];
                constraintAnchor4.mSolverVariable = solverVariableCreateObjectVariable;
                ConstraintAnchor constraintAnchor5 = constraintAnchor4.mTarget;
                int i11 = (constraintAnchor5 == null || constraintAnchor5.mOwner != this) ? 0 : constraintAnchor4.mMargin;
                if (i10 == 0 || i10 == 2) {
                    linearSystem.addLowerBarrier(constraintAnchor3.mSolverVariable, solverVariableCreateObjectVariable, this.mMargin - i11, z2);
                } else {
                    linearSystem.addGreaterBarrier(constraintAnchor3.mSolverVariable, solverVariableCreateObjectVariable, this.mMargin + i11, z2);
                }
                linearSystem.addEquality(constraintAnchor3.mSolverVariable, solverVariableCreateObjectVariable, this.mMargin + i11, i8);
            }
        }
        int i12 = this.mBarrierType;
        if (i12 == 0) {
            linearSystem.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 4);
            solverVariable = this.mLeft.mSolverVariable;
            constraintAnchor = this.mParent.mLeft;
        } else if (i12 == 1) {
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 4);
            solverVariable = this.mLeft.mSolverVariable;
            constraintAnchor = this.mParent.mRight;
        } else if (i12 == 2) {
            linearSystem.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 4);
            solverVariable = this.mTop.mSolverVariable;
            constraintAnchor = this.mParent.mTop;
        } else {
            if (i12 != 3) {
                return;
            }
            linearSystem.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 8);
            linearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 4);
            solverVariable = this.mTop.mSolverVariable;
            constraintAnchor = this.mParent.mBottom;
        }
        linearSystem.addEquality(solverVariable, constraintAnchor.mSolverVariable, 0, 0);
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public boolean allowedInBarrier() {
        return true;
    }

    public boolean allowsGoneWidget() {
        return this.mAllowsGoneWidget;
    }

    @Override // androidx.constraintlayout.solver.widgets.HelperWidget, androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(constraintWidget, map);
        Barrier barrier = (Barrier) constraintWidget;
        this.mBarrierType = barrier.mBarrierType;
        this.mAllowsGoneWidget = barrier.mAllowsGoneWidget;
        this.mMargin = barrier.mMargin;
    }

    public int getBarrierType() {
        return this.mBarrierType;
    }

    public int getMargin() {
        return this.mMargin;
    }

    public void markWidgets() {
        for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
            ConstraintWidget constraintWidget = this.mWidgets[i2];
            int i3 = this.mBarrierType;
            if (i3 == 0 || i3 == 1) {
                constraintWidget.setInBarrier(0, true);
            } else if (i3 == 2 || i3 == 3) {
                constraintWidget.setInBarrier(1, true);
            }
        }
    }

    public void setAllowsGoneWidget(boolean z2) {
        this.mAllowsGoneWidget = z2;
    }

    public void setBarrierType(int i2) {
        this.mBarrierType = i2;
    }

    public void setMargin(int i2) {
        this.mMargin = i2;
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public String toString() {
        String string = "[Barrier] " + getDebugName() + " {";
        for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
            ConstraintWidget constraintWidget = this.mWidgets[i2];
            if (i2 > 0) {
                string = a.e(string, ", ");
            }
            StringBuilder sbH = a.h(string);
            sbH.append(constraintWidget.getDebugName());
            string = sbH.toString();
        }
        return a.e(string, "}");
    }
}
