package androidx.constraintlayout.solver.widgets;

import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ChainHead {
    private boolean mDefined;
    protected ConstraintWidget mFirst;
    protected ConstraintWidget mFirstMatchConstraintWidget;
    protected ConstraintWidget mFirstVisibleWidget;
    protected boolean mHasComplexMatchWeights;
    protected boolean mHasDefinedWeights;
    protected boolean mHasRatio;
    protected boolean mHasUndefinedWeights;
    protected ConstraintWidget mHead;
    private boolean mIsRtl;
    protected ConstraintWidget mLast;
    protected ConstraintWidget mLastMatchConstraintWidget;
    protected ConstraintWidget mLastVisibleWidget;
    boolean mOptimizable;
    private int mOrientation;
    int mTotalMargins;
    int mTotalSize;
    protected float mTotalWeight = 0.0f;
    int mVisibleWidgets;
    protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
    protected int mWidgetsCount;
    protected int mWidgetsMatchCount;

    public ChainHead(ConstraintWidget constraintWidget, int i2, boolean z2) {
        this.mFirst = constraintWidget;
        this.mOrientation = i2;
        this.mIsRtl = z2;
    }

    private void defineChainProperties() {
        int i2 = this.mOrientation * 2;
        ConstraintWidget constraintWidget = this.mFirst;
        this.mOptimizable = true;
        ConstraintWidget constraintWidget2 = constraintWidget;
        boolean z2 = false;
        while (!z2) {
            this.mWidgetsCount++;
            ConstraintWidget[] constraintWidgetArr = constraintWidget.mNextChainWidget;
            int i3 = this.mOrientation;
            ConstraintWidget constraintWidget3 = null;
            constraintWidgetArr[i3] = null;
            constraintWidget.mListNextMatchConstraintsWidget[i3] = null;
            if (constraintWidget.getVisibility() != 8) {
                this.mVisibleWidgets++;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(this.mOrientation);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour != dimensionBehaviour2) {
                    this.mTotalSize = constraintWidget.getLength(this.mOrientation) + this.mTotalSize;
                }
                int margin = constraintWidget.mListAnchors[i2].getMargin() + this.mTotalSize;
                this.mTotalSize = margin;
                int i4 = i2 + 1;
                this.mTotalSize = constraintWidget.mListAnchors[i4].getMargin() + margin;
                int margin2 = constraintWidget.mListAnchors[i2].getMargin() + this.mTotalMargins;
                this.mTotalMargins = margin2;
                this.mTotalMargins = constraintWidget.mListAnchors[i4].getMargin() + margin2;
                if (this.mFirstVisibleWidget == null) {
                    this.mFirstVisibleWidget = constraintWidget;
                }
                this.mLastVisibleWidget = constraintWidget;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                int i5 = this.mOrientation;
                if (dimensionBehaviourArr[i5] == dimensionBehaviour2) {
                    int i6 = constraintWidget.mResolvedMatchConstraintDefault[i5];
                    if (i6 == 0 || i6 == 3 || i6 == 2) {
                        this.mWidgetsMatchCount++;
                        float f2 = constraintWidget.mWeight[i5];
                        if (f2 > 0.0f) {
                            this.mTotalWeight += f2;
                        }
                        if (isMatchConstraintEqualityCandidate(constraintWidget, i5)) {
                            if (f2 < 0.0f) {
                                this.mHasUndefinedWeights = true;
                            } else {
                                this.mHasDefinedWeights = true;
                            }
                            if (this.mWeightedMatchConstraintsWidgets == null) {
                                this.mWeightedMatchConstraintsWidgets = new ArrayList<>();
                            }
                            this.mWeightedMatchConstraintsWidgets.add(constraintWidget);
                        }
                        if (this.mFirstMatchConstraintWidget == null) {
                            this.mFirstMatchConstraintWidget = constraintWidget;
                        }
                        ConstraintWidget constraintWidget4 = this.mLastMatchConstraintWidget;
                        if (constraintWidget4 != null) {
                            constraintWidget4.mListNextMatchConstraintsWidget[this.mOrientation] = constraintWidget;
                        }
                        this.mLastMatchConstraintWidget = constraintWidget;
                    }
                    if (this.mOrientation != 0 ? !(constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.mMatchConstraintMinHeight == 0 && constraintWidget.mMatchConstraintMaxHeight == 0) : !(constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.mMatchConstraintMinWidth == 0 && constraintWidget.mMatchConstraintMaxWidth == 0)) {
                        this.mOptimizable = false;
                    }
                    if (constraintWidget.mDimensionRatio != 0.0f) {
                        this.mOptimizable = false;
                        this.mHasRatio = true;
                    }
                }
            }
            if (constraintWidget2 != constraintWidget) {
                constraintWidget2.mNextChainWidget[this.mOrientation] = constraintWidget;
            }
            ConstraintAnchor constraintAnchor = constraintWidget.mListAnchors[i2 + 1].mTarget;
            if (constraintAnchor != null) {
                ConstraintWidget constraintWidget5 = constraintAnchor.mOwner;
                ConstraintAnchor constraintAnchor2 = constraintWidget5.mListAnchors[i2].mTarget;
                if (constraintAnchor2 != null && constraintAnchor2.mOwner == constraintWidget) {
                    constraintWidget3 = constraintWidget5;
                }
            }
            if (constraintWidget3 == null) {
                constraintWidget3 = constraintWidget;
                z2 = true;
            }
            constraintWidget2 = constraintWidget;
            constraintWidget = constraintWidget3;
        }
        ConstraintWidget constraintWidget6 = this.mFirstVisibleWidget;
        if (constraintWidget6 != null) {
            this.mTotalSize -= constraintWidget6.mListAnchors[i2].getMargin();
        }
        ConstraintWidget constraintWidget7 = this.mLastVisibleWidget;
        if (constraintWidget7 != null) {
            this.mTotalSize -= constraintWidget7.mListAnchors[i2 + 1].getMargin();
        }
        this.mLast = constraintWidget;
        if (this.mOrientation == 0 && this.mIsRtl) {
            this.mHead = constraintWidget;
        } else {
            this.mHead = this.mFirst;
        }
        this.mHasComplexMatchWeights = this.mHasDefinedWeights && this.mHasUndefinedWeights;
    }

    private static boolean isMatchConstraintEqualityCandidate(ConstraintWidget constraintWidget, int i2) {
        int i3;
        return constraintWidget.getVisibility() != 8 && constraintWidget.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((i3 = constraintWidget.mResolvedMatchConstraintDefault[i2]) == 0 || i3 == 3);
    }

    public void define() {
        if (!this.mDefined) {
            defineChainProperties();
        }
        this.mDefined = true;
    }

    public ConstraintWidget getFirst() {
        return this.mFirst;
    }

    public ConstraintWidget getFirstMatchConstraintWidget() {
        return this.mFirstMatchConstraintWidget;
    }

    public ConstraintWidget getFirstVisibleWidget() {
        return this.mFirstVisibleWidget;
    }

    public ConstraintWidget getHead() {
        return this.mHead;
    }

    public ConstraintWidget getLast() {
        return this.mLast;
    }

    public ConstraintWidget getLastMatchConstraintWidget() {
        return this.mLastMatchConstraintWidget;
    }

    public ConstraintWidget getLastVisibleWidget() {
        return this.mLastVisibleWidget;
    }

    public float getTotalWeight() {
        return this.mTotalWeight;
    }
}
