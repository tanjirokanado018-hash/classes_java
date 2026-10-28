package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Flow extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] mDisplayedWidgets;
    private int mHorizontalStyle = -1;
    private int mVerticalStyle = -1;
    private int mFirstHorizontalStyle = -1;
    private int mFirstVerticalStyle = -1;
    private int mLastHorizontalStyle = -1;
    private int mLastVerticalStyle = -1;
    private float mHorizontalBias = 0.5f;
    private float mVerticalBias = 0.5f;
    private float mFirstHorizontalBias = 0.5f;
    private float mFirstVerticalBias = 0.5f;
    private float mLastHorizontalBias = 0.5f;
    private float mLastVerticalBias = 0.5f;
    private int mHorizontalGap = 0;
    private int mVerticalGap = 0;
    private int mHorizontalAlign = 2;
    private int mVerticalAlign = 2;
    private int mWrapMode = 0;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    private ArrayList<WidgetsList> mChainList = new ArrayList<>();
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private int[] mAlignedDimensions = null;
    private int mDisplayedWidgetsCount = 0;

    public class WidgetsList {
        private ConstraintAnchor mBottom;
        private ConstraintAnchor mLeft;
        private int mMax;
        private int mOrientation;
        private int mPaddingBottom;
        private int mPaddingLeft;
        private int mPaddingRight;
        private int mPaddingTop;
        private ConstraintAnchor mRight;
        private ConstraintAnchor mTop;
        private ConstraintWidget biggest = null;
        int biggestDimension = 0;
        private int mWidth = 0;
        private int mHeight = 0;
        private int mStartIndex = 0;
        private int mCount = 0;
        private int mNbMatchConstraintsWidgets = 0;

        public WidgetsList(int i2, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i3) {
            this.mPaddingLeft = 0;
            this.mPaddingTop = 0;
            this.mPaddingRight = 0;
            this.mPaddingBottom = 0;
            this.mMax = 0;
            this.mOrientation = i2;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = i3;
        }

        private void recomputeDimensions() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.biggestDimension = 0;
            int i2 = this.mCount;
            for (int i3 = 0; i3 < i2 && this.mStartIndex + i3 < Flow.this.mDisplayedWidgetsCount; i3++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i3];
                if (this.mOrientation == 0) {
                    int width = constraintWidget.getWidth();
                    int i4 = Flow.this.mHorizontalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i4 = 0;
                    }
                    this.mWidth = width + i4 + this.mWidth;
                    int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    if (this.biggest == null || this.biggestDimension < widgetHeight) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = widgetHeight;
                        this.mHeight = widgetHeight;
                    }
                } else {
                    int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                    int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    int i5 = Flow.this.mVerticalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i5 = 0;
                    }
                    this.mHeight = widgetHeight2 + i5 + this.mHeight;
                    if (this.biggest == null || this.biggestDimension < widgetWidth) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = widgetWidth;
                        this.mWidth = widgetWidth;
                    }
                }
            }
        }

        public void add(ConstraintWidget constraintWidget) {
            if (this.mOrientation == 0) {
                int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetWidth = 0;
                }
                this.mWidth = widgetWidth + (constraintWidget.getVisibility() != 8 ? Flow.this.mHorizontalGap : 0) + this.mWidth;
                int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (this.biggest == null || this.biggestDimension < widgetHeight) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetHeight;
                    this.mHeight = widgetHeight;
                }
            } else {
                int widgetWidth2 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetHeight2 = 0;
                }
                this.mHeight = widgetHeight2 + (constraintWidget.getVisibility() != 8 ? Flow.this.mVerticalGap : 0) + this.mHeight;
                if (this.biggest == null || this.biggestDimension < widgetWidth2) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        public void clear() {
            this.biggestDimension = 0;
            this.biggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        public void createConstraints(boolean z2, int i2, boolean z3) {
            ConstraintAnchor constraintAnchor;
            ConstraintAnchor constraintAnchor2;
            ConstraintAnchor constraintAnchor3;
            ConstraintAnchor constraintAnchor4;
            ConstraintAnchor constraintAnchor5;
            ConstraintAnchor constraintAnchor6;
            ConstraintWidget constraintWidget;
            ConstraintAnchor constraintAnchor7;
            ConstraintAnchor constraintAnchor8;
            int i3 = this.mCount;
            for (int i4 = 0; i4 < i3 && this.mStartIndex + i4 < Flow.this.mDisplayedWidgetsCount; i4++) {
                ConstraintWidget constraintWidget2 = Flow.this.mDisplayedWidgets[this.mStartIndex + i4];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
            }
            if (i3 == 0 || this.biggest == null) {
                return;
            }
            boolean z4 = z3 && i2 == 0;
            int i5 = -1;
            int i6 = -1;
            for (int i7 = 0; i7 < i3; i7++) {
                int i8 = z2 ? (i3 - 1) - i7 : i7;
                if (this.mStartIndex + i8 >= Flow.this.mDisplayedWidgetsCount) {
                    break;
                }
                if (Flow.this.mDisplayedWidgets[this.mStartIndex + i8].getVisibility() == 0) {
                    if (i5 == -1) {
                        i5 = i7;
                    }
                    i6 = i7;
                }
            }
            ConstraintWidget constraintWidget3 = null;
            if (this.mOrientation == 0) {
                ConstraintWidget constraintWidget4 = this.biggest;
                constraintWidget4.setVerticalChainStyle(Flow.this.mVerticalStyle);
                int i9 = this.mPaddingTop;
                if (i2 > 0) {
                    i9 += Flow.this.mVerticalGap;
                }
                constraintWidget4.mTop.connect(this.mTop, i9);
                if (z3) {
                    constraintWidget4.mBottom.connect(this.mBottom, this.mPaddingBottom);
                }
                if (i2 > 0) {
                    this.mTop.mOwner.mBottom.connect(constraintWidget4.mTop, 0);
                }
                if (Flow.this.mVerticalAlign != 3 || constraintWidget4.hasBaseline()) {
                    constraintWidget = constraintWidget4;
                    break;
                }
                int i10 = 0;
                while (true) {
                    if (i10 < i3) {
                        int i11 = z2 ? (i3 - 1) - i10 : i10;
                        if (this.mStartIndex + i11 < Flow.this.mDisplayedWidgetsCount) {
                            constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i11];
                            if (constraintWidget.hasBaseline()) {
                                break;
                            } else {
                                i10++;
                            }
                        }
                    }
                    constraintWidget = constraintWidget4;
                    break;
                }
                int i12 = 0;
                while (i12 < i3) {
                    int i13 = z2 ? (i3 - 1) - i12 : i12;
                    if (this.mStartIndex + i13 >= Flow.this.mDisplayedWidgetsCount) {
                        return;
                    }
                    ConstraintWidget constraintWidget5 = Flow.this.mDisplayedWidgets[this.mStartIndex + i13];
                    if (i12 == 0) {
                        constraintWidget5.connect(constraintWidget5.mLeft, this.mLeft, this.mPaddingLeft);
                    }
                    if (i13 == 0) {
                        int i14 = Flow.this.mHorizontalStyle;
                        float f2 = Flow.this.mHorizontalBias;
                        if (this.mStartIndex == 0 && Flow.this.mFirstHorizontalStyle != -1) {
                            i14 = Flow.this.mFirstHorizontalStyle;
                            f2 = Flow.this.mFirstHorizontalBias;
                        } else if (z3 && Flow.this.mLastHorizontalStyle != -1) {
                            i14 = Flow.this.mLastHorizontalStyle;
                            f2 = Flow.this.mLastHorizontalBias;
                        }
                        constraintWidget5.setHorizontalChainStyle(i14);
                        constraintWidget5.setHorizontalBiasPercent(f2);
                    }
                    if (i12 == i3 - 1) {
                        constraintWidget5.connect(constraintWidget5.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget3 != null) {
                        constraintWidget5.mLeft.connect(constraintWidget3.mRight, Flow.this.mHorizontalGap);
                        if (i12 == i5) {
                            constraintWidget5.mLeft.setGoneMargin(this.mPaddingLeft);
                        }
                        constraintWidget3.mRight.connect(constraintWidget5.mLeft, 0);
                        if (i12 == i6 + 1) {
                            constraintWidget3.mRight.setGoneMargin(this.mPaddingRight);
                        }
                    }
                    if (constraintWidget5 != constraintWidget4) {
                        if (Flow.this.mVerticalAlign == 3 && constraintWidget.hasBaseline() && constraintWidget5 != constraintWidget && constraintWidget5.hasBaseline()) {
                            constraintAnchor7 = constraintWidget5.mBaseline;
                            constraintAnchor8 = constraintWidget.mBaseline;
                        } else {
                            int i15 = Flow.this.mVerticalAlign;
                            if (i15 != 0) {
                                if (i15 != 1) {
                                    ConstraintAnchor constraintAnchor9 = constraintWidget5.mTop;
                                    if (z4) {
                                        constraintAnchor9.connect(this.mTop, this.mPaddingTop);
                                        constraintWidget5.mBottom.connect(this.mBottom, this.mPaddingBottom);
                                    } else {
                                        constraintAnchor9.connect(constraintWidget4.mTop, 0);
                                    }
                                }
                                constraintAnchor7 = constraintWidget5.mBottom;
                                constraintAnchor8 = constraintWidget4.mBottom;
                            } else {
                                constraintAnchor7 = constraintWidget5.mTop;
                                constraintAnchor8 = constraintWidget4.mTop;
                            }
                        }
                        constraintAnchor7.connect(constraintAnchor8, 0);
                    }
                    i12++;
                    constraintWidget3 = constraintWidget5;
                }
                return;
            }
            ConstraintWidget constraintWidget6 = this.biggest;
            constraintWidget6.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
            int i16 = this.mPaddingLeft;
            if (i2 > 0) {
                i16 += Flow.this.mHorizontalGap;
            }
            if (z2) {
                constraintWidget6.mRight.connect(this.mRight, i16);
                if (z3) {
                    constraintWidget6.mLeft.connect(this.mLeft, this.mPaddingRight);
                }
                if (i2 > 0) {
                    constraintAnchor = this.mRight.mOwner.mLeft;
                    constraintAnchor2 = constraintWidget6.mRight;
                    constraintAnchor.connect(constraintAnchor2, 0);
                }
            } else {
                constraintWidget6.mLeft.connect(this.mLeft, i16);
                if (z3) {
                    constraintWidget6.mRight.connect(this.mRight, this.mPaddingRight);
                }
                if (i2 > 0) {
                    constraintAnchor = this.mLeft.mOwner.mRight;
                    constraintAnchor2 = constraintWidget6.mLeft;
                    constraintAnchor.connect(constraintAnchor2, 0);
                }
            }
            int i17 = 0;
            while (i17 < i3 && this.mStartIndex + i17 < Flow.this.mDisplayedWidgetsCount) {
                ConstraintWidget constraintWidget7 = Flow.this.mDisplayedWidgets[this.mStartIndex + i17];
                if (i17 == 0) {
                    constraintWidget7.connect(constraintWidget7.mTop, this.mTop, this.mPaddingTop);
                    int i18 = Flow.this.mVerticalStyle;
                    float f3 = Flow.this.mVerticalBias;
                    if (this.mStartIndex == 0 && Flow.this.mFirstVerticalStyle != -1) {
                        i18 = Flow.this.mFirstVerticalStyle;
                        f3 = Flow.this.mFirstVerticalBias;
                    } else if (z3 && Flow.this.mLastVerticalStyle != -1) {
                        i18 = Flow.this.mLastVerticalStyle;
                        f3 = Flow.this.mLastVerticalBias;
                    }
                    constraintWidget7.setVerticalChainStyle(i18);
                    constraintWidget7.setVerticalBiasPercent(f3);
                }
                if (i17 == i3 - 1) {
                    constraintWidget7.connect(constraintWidget7.mBottom, this.mBottom, this.mPaddingBottom);
                }
                if (constraintWidget3 != null) {
                    constraintWidget7.mTop.connect(constraintWidget3.mBottom, Flow.this.mVerticalGap);
                    if (i17 == i5) {
                        constraintWidget7.mTop.setGoneMargin(this.mPaddingTop);
                    }
                    constraintWidget3.mBottom.connect(constraintWidget7.mTop, 0);
                    if (i17 == i6 + 1) {
                        constraintWidget3.mBottom.setGoneMargin(this.mPaddingBottom);
                    }
                }
                if (constraintWidget7 != constraintWidget6) {
                    int i19 = Flow.this.mHorizontalAlign;
                    if (!z2) {
                        if (i19 != 0) {
                            if (i19 != 1) {
                                if (i19 == 2) {
                                    ConstraintAnchor constraintAnchor10 = constraintWidget7.mLeft;
                                    if (z4) {
                                        constraintAnchor10.connect(this.mLeft, this.mPaddingLeft);
                                        constraintWidget7.mRight.connect(this.mRight, this.mPaddingRight);
                                    } else {
                                        constraintAnchor10.connect(constraintWidget6.mLeft, 0);
                                    }
                                }
                            }
                            constraintAnchor3 = constraintWidget7.mRight;
                            constraintAnchor4 = constraintWidget6.mRight;
                        } else {
                            constraintAnchor3 = constraintWidget7.mLeft;
                            constraintAnchor4 = constraintWidget6.mLeft;
                        }
                        constraintAnchor3.connect(constraintAnchor4, 0);
                    } else if (i19 == 0) {
                        constraintAnchor5 = constraintWidget7.mRight;
                        constraintAnchor6 = constraintWidget6.mRight;
                        constraintAnchor5.connect(constraintAnchor6, 0);
                    } else if (i19 == 1) {
                        constraintAnchor5 = constraintWidget7.mLeft;
                        constraintAnchor6 = constraintWidget6.mLeft;
                        constraintAnchor5.connect(constraintAnchor6, 0);
                    } else if (i19 == 2) {
                        constraintWidget7.mLeft.connect(constraintWidget6.mLeft, 0);
                        constraintAnchor5 = constraintWidget7.mRight;
                        constraintAnchor6 = constraintWidget6.mRight;
                        constraintAnchor5.connect(constraintAnchor6, 0);
                    }
                }
                i17++;
                constraintWidget3 = constraintWidget7;
            }
        }

        public int getHeight() {
            return this.mOrientation == 1 ? this.mHeight - Flow.this.mVerticalGap : this.mHeight;
        }

        public int getWidth() {
            return this.mOrientation == 0 ? this.mWidth - Flow.this.mHorizontalGap : this.mWidth;
        }

        public void measureMatchConstraints(int i2) {
            Flow flow;
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour;
            int width;
            ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour;
            int height;
            int i3 = this.mNbMatchConstraintsWidgets;
            if (i3 == 0) {
                return;
            }
            int i4 = this.mCount;
            int i5 = i2 / i3;
            for (int i6 = 0; i6 < i4 && this.mStartIndex + i6 < Flow.this.mDisplayedWidgetsCount; i6++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i6];
                if (this.mOrientation == 0) {
                    if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                        flow = Flow.this;
                        horizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                        verticalDimensionBehaviour = constraintWidget.getVerticalDimensionBehaviour();
                        height = constraintWidget.getHeight();
                        width = i5;
                        flow.measure(constraintWidget, horizontalDimensionBehaviour, width, verticalDimensionBehaviour, height);
                    }
                } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                    flow = Flow.this;
                    horizontalDimensionBehaviour = constraintWidget.getHorizontalDimensionBehaviour();
                    width = constraintWidget.getWidth();
                    verticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    height = i5;
                    flow.measure(constraintWidget, horizontalDimensionBehaviour, width, verticalDimensionBehaviour, height);
                }
            }
            recomputeDimensions();
        }

        public void setStartIndex(int i2) {
            this.mStartIndex = i2;
        }

        public void setup(int i2, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i3, int i4, int i5, int i6, int i7) {
            this.mOrientation = i2;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = i3;
            this.mPaddingTop = i4;
            this.mPaddingRight = i5;
            this.mPaddingBottom = i6;
            this.mMax = i7;
        }
    }

    private void createAlignedConstraints(boolean z2) {
        ConstraintWidget constraintWidget;
        if (this.mAlignedDimensions == null || this.mAlignedBiggestElementsInCols == null || this.mAlignedBiggestElementsInRows == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mDisplayedWidgetsCount; i2++) {
            this.mDisplayedWidgets[i2].resetAnchors();
        }
        int[] iArr = this.mAlignedDimensions;
        int i3 = iArr[0];
        int i4 = iArr[1];
        ConstraintWidget constraintWidget2 = null;
        for (int i5 = 0; i5 < i3; i5++) {
            ConstraintWidget constraintWidget3 = this.mAlignedBiggestElementsInCols[z2 ? (i3 - i5) - 1 : i5];
            if (constraintWidget3 != null && constraintWidget3.getVisibility() != 8) {
                if (i5 == 0) {
                    constraintWidget3.connect(constraintWidget3.mLeft, this.mLeft, getPaddingLeft());
                    constraintWidget3.setHorizontalChainStyle(this.mHorizontalStyle);
                    constraintWidget3.setHorizontalBiasPercent(this.mHorizontalBias);
                }
                if (i5 == i3 - 1) {
                    constraintWidget3.connect(constraintWidget3.mRight, this.mRight, getPaddingRight());
                }
                if (i5 > 0) {
                    constraintWidget3.connect(constraintWidget3.mLeft, constraintWidget2.mRight, this.mHorizontalGap);
                    constraintWidget2.connect(constraintWidget2.mRight, constraintWidget3.mLeft, 0);
                }
                constraintWidget2 = constraintWidget3;
            }
        }
        for (int i6 = 0; i6 < i4; i6++) {
            ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInRows[i6];
            if (constraintWidget4 != null && constraintWidget4.getVisibility() != 8) {
                if (i6 == 0) {
                    constraintWidget4.connect(constraintWidget4.mTop, this.mTop, getPaddingTop());
                    constraintWidget4.setVerticalChainStyle(this.mVerticalStyle);
                    constraintWidget4.setVerticalBiasPercent(this.mVerticalBias);
                }
                if (i6 == i4 - 1) {
                    constraintWidget4.connect(constraintWidget4.mBottom, this.mBottom, getPaddingBottom());
                }
                if (i6 > 0) {
                    constraintWidget4.connect(constraintWidget4.mTop, constraintWidget2.mBottom, this.mVerticalGap);
                    constraintWidget2.connect(constraintWidget2.mBottom, constraintWidget4.mTop, 0);
                }
                constraintWidget2 = constraintWidget4;
            }
        }
        for (int i7 = 0; i7 < i3; i7++) {
            for (int i8 = 0; i8 < i4; i8++) {
                int i9 = (i8 * i3) + i7;
                if (this.mOrientation == 1) {
                    i9 = (i7 * i4) + i8;
                }
                ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                if (i9 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i9]) != null && constraintWidget.getVisibility() != 8) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInCols[i7];
                    ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInRows[i8];
                    if (constraintWidget != constraintWidget5) {
                        constraintWidget.connect(constraintWidget.mLeft, constraintWidget5.mLeft, 0);
                        constraintWidget.connect(constraintWidget.mRight, constraintWidget5.mRight, 0);
                    }
                    if (constraintWidget != constraintWidget6) {
                        constraintWidget.connect(constraintWidget.mTop, constraintWidget6.mTop, 0);
                        constraintWidget.connect(constraintWidget.mBottom, constraintWidget6.mBottom, 0);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetHeight(ConstraintWidget constraintWidget, int i2) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i3 = constraintWidget.mMatchConstraintDefaultHeight;
            if (i3 == 0) {
                return 0;
            }
            if (i3 == 2) {
                int i4 = (int) (constraintWidget.mMatchConstraintPercentHeight * i2);
                if (i4 != constraintWidget.getHeight()) {
                    measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i4);
                }
                return i4;
            }
            if (i3 == 1) {
                return constraintWidget.getHeight();
            }
            if (i3 == 3) {
                return (int) ((constraintWidget.getWidth() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i2) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i3 = constraintWidget.mMatchConstraintDefaultWidth;
            if (i3 == 0) {
                return 0;
            }
            if (i3 == 2) {
                int i4 = (int) (constraintWidget.mMatchConstraintPercentWidth * i2);
                if (i4 != constraintWidget.getWidth()) {
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i4, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                }
                return i4;
            }
            if (i3 == 1) {
                return constraintWidget.getWidth();
            }
            if (i3 == 3) {
                return (int) ((constraintWidget.getHeight() * constraintWidget.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget.getWidth();
    }

    /* JADX WARN: Code duplicated, block: B:104:0x010d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:107:0x0115 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:115:0x011b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x0113 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:117:0x0057 A[ADDED_TO_REGION, EDGE_INSN: B:117:0x0057->B:40:0x0057 BREAK  A[LOOP:1: B:42:0x005a->B:122:0x005a], REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:119:0x010b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:120:0x0057 A[ADDED_TO_REGION, EDGE_INSN: B:120:0x0057->B:40:0x0057 BREAK  A[LOOP:1: B:42:0x005a->B:122:0x005a], REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:130:0x00d1 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:134:0x00eb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:137:0x0102 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x005c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x005e  */
    /* JADX WARN: Code duplicated, block: B:45:0x0068  */
    /* JADX WARN: Code duplicated, block: B:48:0x0076  */
    /* JADX WARN: Code duplicated, block: B:52:0x007e  */
    /* JADX WARN: Code duplicated, block: B:55:0x0086  */
    /* JADX WARN: Code duplicated, block: B:59:0x008e  */
    /* JADX WARN: Code duplicated, block: B:62:0x0095  */
    /* JADX WARN: Code duplicated, block: B:64:0x0098  */
    /* JADX WARN: Code duplicated, block: B:66:0x009d  */
    /* JADX WARN: Code duplicated, block: B:70:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:75:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:77:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:80:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:82:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:87:0x00db  */
    /* JADX WARN: Code duplicated, block: B:89:0x00e1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:90:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:95:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:97:0x00f8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:98:0x00fa  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:103:0x010b -> B:40:0x0057). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:104:0x010d -> B:40:0x0057). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:106:0x0113 -> B:40:0x0057). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x0115 -> B:40:0x0057). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:97:0x00f8
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private void measureAligned(androidx.constraintlayout.solver.widgets.ConstraintWidget[] r11, int r12, int r13, int r14, int[] r15) {
        /*
            Method dump skipped, instruction units count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.measureAligned(androidx.constraintlayout.solver.widgets.ConstraintWidget[], int, int, int, int[]):void");
    }

    private void measureChainWrap(ConstraintWidget[] constraintWidgetArr, int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        ConstraintAnchor constraintAnchor;
        int paddingRight;
        ConstraintAnchor constraintAnchor2;
        int paddingBottom;
        int i7;
        if (i2 == 0) {
            return;
        }
        this.mChainList.clear();
        WidgetsList widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
        this.mChainList.add(widgetsList);
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        if (i3 == 0) {
            while (i10 < i2) {
                ConstraintWidget constraintWidget = constraintWidgetArr[i10];
                int widgetWidth = getWidgetWidth(constraintWidget, i4);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i8++;
                }
                int i11 = i8;
                boolean z2 = (i9 == i4 || (this.mHorizontalGap + i9) + widgetWidth > i4) && widgetsList.biggest != null;
                if ((z2 || i10 <= 0 || (i7 = this.mMaxElementsWrap) <= 0 || i10 % i7 != 0) && !z2) {
                    if (i10 > 0) {
                        i9 = this.mHorizontalGap + widgetWidth + i9;
                    }
                    widgetsList.add(constraintWidget);
                    i10++;
                    i8 = i11;
                } else {
                    widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
                    widgetsList.setStartIndex(i10);
                    this.mChainList.add(widgetsList);
                }
                i9 = widgetWidth;
                widgetsList.add(constraintWidget);
                i10++;
                i8 = i11;
            }
        } else {
            while (i10 < i2) {
                ConstraintWidget constraintWidget2 = constraintWidgetArr[i10];
                int widgetHeight = getWidgetHeight(constraintWidget2, i4);
                if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i8++;
                }
                int i12 = i8;
                boolean z3 = (i9 == i4 || (this.mVerticalGap + i9) + widgetHeight > i4) && widgetsList.biggest != null;
                if ((z3 || i10 <= 0 || (i5 = this.mMaxElementsWrap) <= 0 || i10 % i5 != 0) && !z3) {
                    if (i10 > 0) {
                        i9 = this.mVerticalGap + widgetHeight + i9;
                    }
                    widgetsList.add(constraintWidget2);
                    i10++;
                    i8 = i12;
                } else {
                    widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
                    widgetsList.setStartIndex(i10);
                    this.mChainList.add(widgetsList);
                }
                i9 = widgetHeight;
                widgetsList.add(constraintWidget2);
                i10++;
                i8 = i12;
            }
        }
        int size = this.mChainList.size();
        ConstraintAnchor constraintAnchor3 = this.mLeft;
        ConstraintAnchor constraintAnchor4 = this.mTop;
        ConstraintAnchor constraintAnchor5 = this.mRight;
        ConstraintAnchor constraintAnchor6 = this.mBottom;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight2 = getPaddingRight();
        int paddingBottom2 = getPaddingBottom();
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z4 = horizontalDimensionBehaviour == dimensionBehaviour || getVerticalDimensionBehaviour() == dimensionBehaviour;
        if (i8 > 0 && z4) {
            for (int i13 = 0; i13 < size; i13++) {
                WidgetsList widgetsList2 = this.mChainList.get(i13);
                widgetsList2.measureMatchConstraints(i4 - (i3 == 0 ? widgetsList2.getWidth() : widgetsList2.getHeight()));
            }
        }
        int i14 = paddingTop;
        int i15 = paddingRight2;
        int height = 0;
        int width = 0;
        int i16 = 0;
        int i17 = paddingLeft;
        ConstraintAnchor constraintAnchor7 = constraintAnchor4;
        ConstraintAnchor constraintAnchor8 = constraintAnchor3;
        int i18 = paddingBottom2;
        while (i16 < size) {
            WidgetsList widgetsList3 = this.mChainList.get(i16);
            if (i3 == 0) {
                if (i16 < size - 1) {
                    constraintAnchor2 = this.mChainList.get(i16 + 1).biggest.mTop;
                    paddingBottom = 0;
                } else {
                    constraintAnchor2 = this.mBottom;
                    paddingBottom = getPaddingBottom();
                }
                ConstraintAnchor constraintAnchor9 = widgetsList3.biggest.mBottom;
                ConstraintAnchor constraintAnchor10 = constraintAnchor8;
                ConstraintAnchor constraintAnchor11 = constraintAnchor8;
                int i19 = height;
                ConstraintAnchor constraintAnchor12 = constraintAnchor7;
                int i20 = width;
                ConstraintAnchor constraintAnchor13 = constraintAnchor5;
                ConstraintAnchor constraintAnchor14 = constraintAnchor5;
                i6 = i16;
                widgetsList3.setup(i3, constraintAnchor10, constraintAnchor12, constraintAnchor13, constraintAnchor2, i17, i14, i15, paddingBottom, i4);
                int iMax = Math.max(i20, widgetsList3.getWidth());
                height = i19 + widgetsList3.getHeight();
                if (i6 > 0) {
                    height += this.mVerticalGap;
                }
                constraintAnchor8 = constraintAnchor11;
                width = iMax;
                constraintAnchor7 = constraintAnchor9;
                i14 = 0;
                constraintAnchor = constraintAnchor14;
                int i21 = paddingBottom;
                constraintAnchor6 = constraintAnchor2;
                i18 = i21;
            } else {
                ConstraintAnchor constraintAnchor15 = constraintAnchor8;
                int i22 = height;
                int i23 = width;
                i6 = i16;
                if (i6 < size - 1) {
                    constraintAnchor = this.mChainList.get(i6 + 1).biggest.mLeft;
                    paddingRight = 0;
                } else {
                    constraintAnchor = this.mRight;
                    paddingRight = getPaddingRight();
                }
                ConstraintAnchor constraintAnchor16 = widgetsList3.biggest.mRight;
                widgetsList3.setup(i3, constraintAnchor15, constraintAnchor7, constraintAnchor, constraintAnchor6, i17, i14, paddingRight, i18, i4);
                width = i23 + widgetsList3.getWidth();
                int iMax2 = Math.max(i22, widgetsList3.getHeight());
                if (i6 > 0) {
                    width += this.mHorizontalGap;
                }
                height = iMax2;
                i15 = paddingRight;
                constraintAnchor8 = constraintAnchor16;
                i17 = 0;
            }
            i16 = i6 + 1;
            constraintAnchor5 = constraintAnchor;
        }
        iArr[0] = width;
        iArr[1] = height;
    }

    private void measureNoWrap(ConstraintWidget[] constraintWidgetArr, int i2, int i3, int i4, int[] iArr) {
        WidgetsList widgetsList;
        if (i2 == 0) {
            return;
        }
        if (this.mChainList.size() == 0) {
            widgetsList = new WidgetsList(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, i4);
            this.mChainList.add(widgetsList);
        } else {
            WidgetsList widgetsList2 = this.mChainList.get(0);
            widgetsList2.clear();
            widgetsList = widgetsList2;
            widgetsList.setup(i3, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), i4);
        }
        for (int i5 = 0; i5 < i2; i5++) {
            widgetsList.add(constraintWidgetArr[i5]);
        }
        iArr[0] = widgetsList.getWidth();
        iArr[1] = widgetsList.getHeight();
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        super.addToSolver(linearSystem);
        boolean zIsRtl = getParent() != null ? ((ConstraintWidgetContainer) getParent()).isRtl() : false;
        int i2 = this.mWrapMode;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = this.mChainList.size();
                int i3 = 0;
                while (i3 < size) {
                    this.mChainList.get(i3).createConstraints(zIsRtl, i3, i3 == size + (-1));
                    i3++;
                }
            } else if (i2 == 2) {
                createAlignedConstraints(zIsRtl);
            }
        } else if (this.mChainList.size() > 0) {
            this.mChainList.get(0).createConstraints(zIsRtl, 0, true);
        }
        needsCallbackFromSolver(false);
    }

    @Override // androidx.constraintlayout.solver.widgets.HelperWidget, androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(constraintWidget, map);
        Flow flow = (Flow) constraintWidget;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0049  */
    /* JADX WARN: Code duplicated, block: B:55:0x00da  */
    /* JADX WARN: Code duplicated, block: B:56:0x00dc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:57:0x00de  */
    /* JADX WARN: Code duplicated, block: B:58:0x00e3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:62:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:63:0x00eb A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:64:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:65:0x00f2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:67:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:71:0x0104  */
    @Override // androidx.constraintlayout.solver.widgets.VirtualLayout
    public void measure(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        boolean z2;
        char c2;
        int iMin;
        int iMin2;
        if (this.mWidgetsCount > 0 && !measureChildren()) {
            setMeasure(0, 0);
            needsCallbackFromSolver(false);
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int[] iArr = new int[2];
        int i8 = (i3 - paddingLeft) - paddingRight;
        int i9 = this.mOrientation;
        if (i9 == 1) {
            i8 = (i5 - paddingTop) - paddingBottom;
        }
        int i10 = i8;
        if (i9 == 0) {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        } else {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] constraintWidgetArr = this.mWidgets;
        int i11 = 0;
        int i12 = 0;
        while (true) {
            i6 = this.mWidgetsCount;
            if (i11 >= i6) {
                break;
            }
            if (this.mWidgets[i11].getVisibility() == 8) {
                i12++;
            }
            i11++;
        }
        if (i12 > 0) {
            constraintWidgetArr = new ConstraintWidget[i6 - i12];
            int i13 = 0;
            for (int i14 = 0; i14 < this.mWidgetsCount; i14++) {
                ConstraintWidget constraintWidget = this.mWidgets[i14];
                if (constraintWidget.getVisibility() != 8) {
                    constraintWidgetArr[i13] = constraintWidget;
                    i13++;
                }
            }
            i7 = i13;
        } else {
            i7 = i6;
        }
        this.mDisplayedWidgets = constraintWidgetArr;
        this.mDisplayedWidgetsCount = i7;
        int i15 = this.mWrapMode;
        if (i15 == 0) {
            z2 = true;
            measureNoWrap(constraintWidgetArr, i7, this.mOrientation, i10, iArr);
        } else {
            if (i15 != 1) {
                if (i15 != 2) {
                    iArr = iArr;
                    c2 = 0;
                    z2 = true;
                } else {
                    z2 = true;
                    measureAligned(constraintWidgetArr, i7, this.mOrientation, i10, iArr);
                }
                iMin = iArr[c2] + paddingLeft + paddingRight;
                iMin2 = iArr[z2 ? 1 : 0] + paddingTop + paddingBottom;
                if (i2 == 1073741824) {
                    iMin = i3;
                } else if (i2 == Integer.MIN_VALUE) {
                    iMin = Math.min(iMin, i3);
                } else if (i2 != 0) {
                    iMin = 0;
                }
                if (i4 == 1073741824) {
                    iMin2 = i5;
                } else if (i4 == Integer.MIN_VALUE) {
                    iMin2 = Math.min(iMin2, i5);
                } else if (i4 != 0) {
                    iMin2 = 0;
                }
                setMeasure(iMin, iMin2);
                setWidth(iMin);
                setHeight(iMin2);
                if (this.mWidgetsCount <= 0) {
                    z2 = false;
                }
                needsCallbackFromSolver(z2);
            }
            z2 = true;
            measureChainWrap(constraintWidgetArr, i7, this.mOrientation, i10, iArr);
        }
        c2 = 0;
        iMin = iArr[c2] + paddingLeft + paddingRight;
        iMin2 = iArr[z2 ? 1 : 0] + paddingTop + paddingBottom;
        if (i2 == 1073741824) {
            iMin = i3;
        } else if (i2 == Integer.MIN_VALUE) {
            iMin = Math.min(iMin, i3);
        } else if (i2 != 0) {
            iMin = 0;
        }
        if (i4 == 1073741824) {
            iMin2 = i5;
        } else if (i4 == Integer.MIN_VALUE) {
            iMin2 = Math.min(iMin2, i5);
        } else if (i4 != 0) {
            iMin2 = 0;
        }
        setMeasure(iMin, iMin2);
        setWidth(iMin);
        setHeight(iMin2);
        if (this.mWidgetsCount <= 0) {
            z2 = false;
        }
        needsCallbackFromSolver(z2);
    }

    public void setFirstHorizontalBias(float f2) {
        this.mFirstHorizontalBias = f2;
    }

    public void setFirstHorizontalStyle(int i2) {
        this.mFirstHorizontalStyle = i2;
    }

    public void setFirstVerticalBias(float f2) {
        this.mFirstVerticalBias = f2;
    }

    public void setFirstVerticalStyle(int i2) {
        this.mFirstVerticalStyle = i2;
    }

    public void setHorizontalAlign(int i2) {
        this.mHorizontalAlign = i2;
    }

    public void setHorizontalBias(float f2) {
        this.mHorizontalBias = f2;
    }

    public void setHorizontalGap(int i2) {
        this.mHorizontalGap = i2;
    }

    public void setHorizontalStyle(int i2) {
        this.mHorizontalStyle = i2;
    }

    public void setLastHorizontalBias(float f2) {
        this.mLastHorizontalBias = f2;
    }

    public void setLastHorizontalStyle(int i2) {
        this.mLastHorizontalStyle = i2;
    }

    public void setLastVerticalBias(float f2) {
        this.mLastVerticalBias = f2;
    }

    public void setLastVerticalStyle(int i2) {
        this.mLastVerticalStyle = i2;
    }

    public void setMaxElementsWrap(int i2) {
        this.mMaxElementsWrap = i2;
    }

    public void setOrientation(int i2) {
        this.mOrientation = i2;
    }

    public void setVerticalAlign(int i2) {
        this.mVerticalAlign = i2;
    }

    public void setVerticalBias(float f2) {
        this.mVerticalBias = f2;
    }

    public void setVerticalGap(int i2) {
        this.mVerticalGap = i2;
    }

    public void setVerticalStyle(int i2) {
        this.mVerticalStyle = i2;
    }

    public void setWrapMode(int i2) {
        this.mWrapMode = i2;
    }
}
