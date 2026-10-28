package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ConstraintReference implements Reference {
    private Object key;
    private ConstraintWidget mConstraintWidget;
    Dimension mHorizontalDimension;
    final State mState;
    Dimension mVerticalDimension;
    private Object mView;
    int mHorizontalChainStyle = 0;
    int mVerticalChainStyle = 0;
    float mHorizontalBias = 0.5f;
    float mVerticalBias = 0.5f;
    int mMarginLeft = 0;
    int mMarginRight = 0;
    int mMarginStart = 0;
    int mMarginEnd = 0;
    int mMarginTop = 0;
    int mMarginBottom = 0;
    int mMarginLeftGone = 0;
    int mMarginRightGone = 0;
    int mMarginStartGone = 0;
    int mMarginEndGone = 0;
    int mMarginTopGone = 0;
    int mMarginBottomGone = 0;
    Object mLeftToLeft = null;
    Object mLeftToRight = null;
    Object mRightToLeft = null;
    Object mRightToRight = null;
    Object mStartToStart = null;
    Object mStartToEnd = null;
    Object mEndToStart = null;
    Object mEndToEnd = null;
    Object mTopToTop = null;
    Object mTopToBottom = null;
    Object mBottomToTop = null;
    Object mBottomToBottom = null;
    Object mBaselineToBaseline = null;
    State.Constraint mLast = null;

    /* JADX INFO: renamed from: androidx.constraintlayout.solver.state.ConstraintReference$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint;

        static {
            int[] iArr = new int[State.Constraint.values().length];
            $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint = iArr;
            try {
                iArr[State.Constraint.LEFT_TO_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.LEFT_TO_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.RIGHT_TO_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.RIGHT_TO_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.START_TO_START.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.START_TO_END.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.END_TO_START.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.END_TO_END.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.TOP_TO_TOP.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.TOP_TO_BOTTOM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.BOTTOM_TO_TOP.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.BOTTOM_TO_BOTTOM.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.BASELINE_TO_BASELINE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.CENTER_HORIZONTALLY.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[State.Constraint.CENTER_VERTICALLY.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    public interface ConstraintReferenceFactory {
        ConstraintReference create(State state);
    }

    public class IncorrectConstraintException extends Exception {
        private final ArrayList<String> mErrors;

        public IncorrectConstraintException(ArrayList<String> arrayList) {
            this.mErrors = arrayList;
        }

        public ArrayList<String> getErrors() {
            return this.mErrors;
        }

        @Override // java.lang.Throwable
        public String toString() {
            return "IncorrectConstraintException: " + this.mErrors.toString();
        }
    }

    public ConstraintReference(State state) {
        Object obj = Dimension.WRAP_DIMENSION;
        this.mHorizontalDimension = Dimension.Fixed(obj);
        this.mVerticalDimension = Dimension.Fixed(obj);
        this.mState = state;
    }

    private void applyConnection(ConstraintWidget constraintWidget, Object obj, State.Constraint constraint) {
        ConstraintAnchor.Type type;
        ConstraintAnchor anchor;
        ConstraintAnchor anchor2;
        int i2;
        int i3;
        ConstraintAnchor.Type type2;
        ConstraintAnchor.Type type3;
        ConstraintAnchor.Type type4;
        ConstraintAnchor.Type type5;
        ConstraintAnchor.Type type6;
        ConstraintWidget target = getTarget(obj);
        if (target == null) {
        }
        int[] iArr = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint;
        int i4 = iArr[constraint.ordinal()];
        switch (iArr[constraint.ordinal()]) {
            case 1:
                type = ConstraintAnchor.Type.LEFT;
                anchor = constraintWidget.getAnchor(type);
                anchor2 = target.getAnchor(type);
                i2 = this.mMarginLeft;
                i3 = this.mMarginLeftGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 2:
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
                type = ConstraintAnchor.Type.RIGHT;
                anchor2 = target.getAnchor(type);
                i2 = this.mMarginLeft;
                i3 = this.mMarginLeftGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 3:
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
                type2 = ConstraintAnchor.Type.LEFT;
                anchor2 = target.getAnchor(type2);
                i2 = this.mMarginRight;
                i3 = this.mMarginRightGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 4:
                type2 = ConstraintAnchor.Type.RIGHT;
                anchor = constraintWidget.getAnchor(type2);
                anchor2 = target.getAnchor(type2);
                i2 = this.mMarginRight;
                i3 = this.mMarginRightGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 5:
                type3 = ConstraintAnchor.Type.LEFT;
                anchor = constraintWidget.getAnchor(type3);
                anchor2 = target.getAnchor(type3);
                i2 = this.mMarginStart;
                i3 = this.mMarginStartGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 6:
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
                type3 = ConstraintAnchor.Type.RIGHT;
                anchor2 = target.getAnchor(type3);
                i2 = this.mMarginStart;
                i3 = this.mMarginStartGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 7:
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
                type4 = ConstraintAnchor.Type.LEFT;
                anchor2 = target.getAnchor(type4);
                i2 = this.mMarginEnd;
                i3 = this.mMarginEndGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 8:
                type4 = ConstraintAnchor.Type.RIGHT;
                anchor = constraintWidget.getAnchor(type4);
                anchor2 = target.getAnchor(type4);
                i2 = this.mMarginEnd;
                i3 = this.mMarginEndGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 9:
                type5 = ConstraintAnchor.Type.TOP;
                anchor = constraintWidget.getAnchor(type5);
                anchor2 = target.getAnchor(type5);
                i2 = this.mMarginTop;
                i3 = this.mMarginTopGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 10:
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
                type5 = ConstraintAnchor.Type.BOTTOM;
                anchor2 = target.getAnchor(type5);
                i2 = this.mMarginTop;
                i3 = this.mMarginTopGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 11:
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
                type6 = ConstraintAnchor.Type.TOP;
                anchor2 = target.getAnchor(type6);
                i2 = this.mMarginBottom;
                i3 = this.mMarginBottomGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 12:
                type6 = ConstraintAnchor.Type.BOTTOM;
                anchor = constraintWidget.getAnchor(type6);
                anchor2 = target.getAnchor(type6);
                i2 = this.mMarginBottom;
                i3 = this.mMarginBottomGone;
                anchor.connect(anchor2, i2, i3, false);
                break;
            case 13:
                ConstraintAnchor.Type type7 = ConstraintAnchor.Type.BASELINE;
                constraintWidget.immediateConnect(type7, target, type7, 0, 0);
                break;
        }
    }

    private void dereference() {
        this.mLeftToLeft = get(this.mLeftToLeft);
        this.mLeftToRight = get(this.mLeftToRight);
        this.mRightToLeft = get(this.mRightToLeft);
        this.mRightToRight = get(this.mRightToRight);
        this.mStartToStart = get(this.mStartToStart);
        this.mStartToEnd = get(this.mStartToEnd);
        this.mEndToStart = get(this.mEndToStart);
        this.mEndToEnd = get(this.mEndToEnd);
        this.mTopToTop = get(this.mTopToTop);
        this.mTopToBottom = get(this.mTopToBottom);
        this.mBottomToTop = get(this.mBottomToTop);
        this.mBottomToBottom = get(this.mBottomToBottom);
        this.mBaselineToBaseline = get(this.mBaselineToBaseline);
    }

    private Object get(Object obj) {
        if (obj == null) {
            return null;
        }
        return !(obj instanceof ConstraintReference) ? this.mState.reference(obj) : obj;
    }

    private ConstraintWidget getTarget(Object obj) {
        if (obj instanceof Reference) {
            return ((Reference) obj).getConstraintWidget();
        }
        return null;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void apply() {
        ConstraintWidget constraintWidget = this.mConstraintWidget;
        if (constraintWidget == null) {
            return;
        }
        this.mHorizontalDimension.apply(this.mState, constraintWidget, 0);
        this.mVerticalDimension.apply(this.mState, this.mConstraintWidget, 1);
        dereference();
        applyConnection(this.mConstraintWidget, this.mLeftToLeft, State.Constraint.LEFT_TO_LEFT);
        applyConnection(this.mConstraintWidget, this.mLeftToRight, State.Constraint.LEFT_TO_RIGHT);
        applyConnection(this.mConstraintWidget, this.mRightToLeft, State.Constraint.RIGHT_TO_LEFT);
        applyConnection(this.mConstraintWidget, this.mRightToRight, State.Constraint.RIGHT_TO_RIGHT);
        applyConnection(this.mConstraintWidget, this.mStartToStart, State.Constraint.START_TO_START);
        applyConnection(this.mConstraintWidget, this.mStartToEnd, State.Constraint.START_TO_END);
        applyConnection(this.mConstraintWidget, this.mEndToStart, State.Constraint.END_TO_START);
        applyConnection(this.mConstraintWidget, this.mEndToEnd, State.Constraint.END_TO_END);
        applyConnection(this.mConstraintWidget, this.mTopToTop, State.Constraint.TOP_TO_TOP);
        applyConnection(this.mConstraintWidget, this.mTopToBottom, State.Constraint.TOP_TO_BOTTOM);
        applyConnection(this.mConstraintWidget, this.mBottomToTop, State.Constraint.BOTTOM_TO_TOP);
        applyConnection(this.mConstraintWidget, this.mBottomToBottom, State.Constraint.BOTTOM_TO_BOTTOM);
        applyConnection(this.mConstraintWidget, this.mBaselineToBaseline, State.Constraint.BASELINE_TO_BASELINE);
        int i2 = this.mHorizontalChainStyle;
        if (i2 != 0) {
            this.mConstraintWidget.setHorizontalChainStyle(i2);
        }
        int i3 = this.mVerticalChainStyle;
        if (i3 != 0) {
            this.mConstraintWidget.setVerticalChainStyle(i3);
        }
        this.mConstraintWidget.setHorizontalBiasPercent(this.mHorizontalBias);
        this.mConstraintWidget.setVerticalBiasPercent(this.mVerticalBias);
    }

    public ConstraintReference baseline() {
        this.mLast = State.Constraint.BASELINE_TO_BASELINE;
        return this;
    }

    public ConstraintReference baselineToBaseline(Object obj) {
        this.mLast = State.Constraint.BASELINE_TO_BASELINE;
        this.mBaselineToBaseline = obj;
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference bias(float f2) {
        State.Constraint constraint = this.mLast;
        if (constraint == null) {
            return this;
        }
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[constraint.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 14:
                this.mHorizontalBias = f2;
                return this;
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
                this.mVerticalBias = f2;
                return this;
            case 13:
            default:
                return this;
        }
    }

    public ConstraintReference bottom() {
        this.mLast = this.mBottomToTop != null ? State.Constraint.BOTTOM_TO_TOP : State.Constraint.BOTTOM_TO_BOTTOM;
        return this;
    }

    public ConstraintReference bottomToBottom(Object obj) {
        this.mLast = State.Constraint.BOTTOM_TO_BOTTOM;
        this.mBottomToBottom = obj;
        return this;
    }

    public ConstraintReference bottomToTop(Object obj) {
        this.mLast = State.Constraint.BOTTOM_TO_TOP;
        this.mBottomToTop = obj;
        return this;
    }

    public ConstraintReference centerHorizontally(Object obj) {
        Object obj2 = get(obj);
        this.mStartToStart = obj2;
        this.mEndToEnd = obj2;
        this.mLast = State.Constraint.CENTER_HORIZONTALLY;
        this.mHorizontalBias = 0.5f;
        return this;
    }

    public ConstraintReference centerVertically(Object obj) {
        Object obj2 = get(obj);
        this.mTopToTop = obj2;
        this.mBottomToBottom = obj2;
        this.mLast = State.Constraint.CENTER_VERTICALLY;
        this.mVerticalBias = 0.5f;
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference clear() {
        State.Constraint constraint = this.mLast;
        if (constraint != null) {
            switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[constraint.ordinal()]) {
                case 1:
                case 2:
                    this.mLeftToLeft = null;
                    this.mLeftToRight = null;
                    this.mMarginLeft = 0;
                    this.mMarginLeftGone = 0;
                    break;
                case 3:
                case 4:
                    this.mRightToLeft = null;
                    this.mRightToRight = null;
                    this.mMarginRight = 0;
                    this.mMarginRightGone = 0;
                    break;
                case 5:
                case 6:
                    this.mStartToStart = null;
                    this.mStartToEnd = null;
                    this.mMarginStart = 0;
                    this.mMarginStartGone = 0;
                    break;
                case 7:
                case 8:
                    this.mEndToStart = null;
                    this.mEndToEnd = null;
                    this.mMarginEnd = 0;
                    this.mMarginEndGone = 0;
                    break;
                case 9:
                case 10:
                    this.mTopToTop = null;
                    this.mTopToBottom = null;
                    this.mMarginTop = 0;
                    this.mMarginTopGone = 0;
                    break;
                case 11:
                case 12:
                    this.mBottomToTop = null;
                    this.mBottomToBottom = null;
                    this.mMarginBottom = 0;
                    break;
                case 13:
                    this.mBaselineToBaseline = null;
                    break;
            }
        } else {
            this.mLeftToLeft = null;
            this.mLeftToRight = null;
            this.mMarginLeft = 0;
            this.mRightToLeft = null;
            this.mRightToRight = null;
            this.mMarginRight = 0;
            this.mStartToStart = null;
            this.mStartToEnd = null;
            this.mMarginStart = 0;
            this.mEndToStart = null;
            this.mEndToEnd = null;
            this.mMarginEnd = 0;
            this.mTopToTop = null;
            this.mTopToBottom = null;
            this.mMarginTop = 0;
            this.mBottomToTop = null;
            this.mBottomToBottom = null;
            this.mMarginBottom = 0;
            this.mBaselineToBaseline = null;
            this.mHorizontalBias = 0.5f;
            this.mVerticalBias = 0.5f;
            this.mMarginLeftGone = 0;
            this.mMarginRightGone = 0;
            this.mMarginStartGone = 0;
            this.mMarginEndGone = 0;
            this.mMarginTopGone = 0;
        }
        this.mMarginBottomGone = 0;
        return this;
    }

    public ConstraintReference clearHorizontal() {
        start().clear();
        end().clear();
        left().clear();
        right().clear();
        return this;
    }

    public ConstraintReference clearVertical() {
        top().clear();
        baseline().clear();
        bottom().clear();
        return this;
    }

    public ConstraintWidget createConstraintWidget() {
        return new ConstraintWidget(getWidth().getValue(), getHeight().getValue());
    }

    public ConstraintReference end() {
        this.mLast = this.mEndToStart != null ? State.Constraint.END_TO_START : State.Constraint.END_TO_END;
        return this;
    }

    public ConstraintReference endToEnd(Object obj) {
        this.mLast = State.Constraint.END_TO_END;
        this.mEndToEnd = obj;
        return this;
    }

    public ConstraintReference endToStart(Object obj) {
        this.mLast = State.Constraint.END_TO_START;
        this.mEndToStart = obj;
        return this;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public ConstraintWidget getConstraintWidget() {
        if (this.mConstraintWidget == null) {
            ConstraintWidget constraintWidgetCreateConstraintWidget = createConstraintWidget();
            this.mConstraintWidget = constraintWidgetCreateConstraintWidget;
            constraintWidgetCreateConstraintWidget.setCompanionWidget(this.mView);
        }
        return this.mConstraintWidget;
    }

    public Dimension getHeight() {
        return this.mVerticalDimension;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public Object getKey() {
        return this.key;
    }

    public int getVerticalChainStyle(int i2) {
        return this.mVerticalChainStyle;
    }

    public Object getView() {
        return this.mView;
    }

    public Dimension getWidth() {
        return this.mHorizontalDimension;
    }

    public ConstraintReference height(Dimension dimension) {
        return setHeight(dimension);
    }

    public ConstraintReference horizontalBias(float f2) {
        this.mHorizontalBias = f2;
        return this;
    }

    public ConstraintReference left() {
        this.mLast = this.mLeftToLeft != null ? State.Constraint.LEFT_TO_LEFT : State.Constraint.LEFT_TO_RIGHT;
        return this;
    }

    public ConstraintReference leftToLeft(Object obj) {
        this.mLast = State.Constraint.LEFT_TO_LEFT;
        this.mLeftToLeft = obj;
        return this;
    }

    public ConstraintReference leftToRight(Object obj) {
        this.mLast = State.Constraint.LEFT_TO_RIGHT;
        this.mLeftToRight = obj;
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference margin(int i2) {
        State.Constraint constraint = this.mLast;
        if (constraint != null) {
            switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[constraint.ordinal()]) {
                case 1:
                case 2:
                    this.mMarginLeft = i2;
                    break;
                case 3:
                case 4:
                    this.mMarginRight = i2;
                    break;
                case 5:
                case 6:
                    this.mMarginStart = i2;
                    break;
                case 7:
                case 8:
                    this.mMarginEnd = i2;
                    break;
                case 9:
                case 10:
                    this.mMarginTop = i2;
                    break;
            }
        } else {
            this.mMarginLeft = i2;
            this.mMarginRight = i2;
            this.mMarginStart = i2;
            this.mMarginEnd = i2;
            this.mMarginTop = i2;
        }
        this.mMarginBottom = i2;
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ConstraintReference marginGone(int i2) {
        State.Constraint constraint = this.mLast;
        if (constraint != null) {
            switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$state$State$Constraint[constraint.ordinal()]) {
                case 1:
                case 2:
                    this.mMarginLeftGone = i2;
                    break;
                case 3:
                case 4:
                    this.mMarginRightGone = i2;
                    break;
                case 5:
                case 6:
                    this.mMarginStartGone = i2;
                    break;
                case 7:
                case 8:
                    this.mMarginEndGone = i2;
                    break;
                case 9:
                case 10:
                    this.mMarginTopGone = i2;
                    break;
            }
        } else {
            this.mMarginLeftGone = i2;
            this.mMarginRightGone = i2;
            this.mMarginStartGone = i2;
            this.mMarginEndGone = i2;
            this.mMarginTopGone = i2;
        }
        this.mMarginBottomGone = i2;
        return this;
    }

    public ConstraintReference right() {
        this.mLast = this.mRightToLeft != null ? State.Constraint.RIGHT_TO_LEFT : State.Constraint.RIGHT_TO_RIGHT;
        return this;
    }

    public ConstraintReference rightToLeft(Object obj) {
        this.mLast = State.Constraint.RIGHT_TO_LEFT;
        this.mRightToLeft = obj;
        return this;
    }

    public ConstraintReference rightToRight(Object obj) {
        this.mLast = State.Constraint.RIGHT_TO_RIGHT;
        this.mRightToRight = obj;
        return this;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void setConstraintWidget(ConstraintWidget constraintWidget) {
        if (constraintWidget == null) {
            return;
        }
        this.mConstraintWidget = constraintWidget;
        constraintWidget.setCompanionWidget(this.mView);
    }

    public ConstraintReference setHeight(Dimension dimension) {
        this.mVerticalDimension = dimension;
        return this;
    }

    public void setHorizontalChainStyle(int i2) {
        this.mHorizontalChainStyle = i2;
    }

    @Override // androidx.constraintlayout.solver.state.Reference
    public void setKey(Object obj) {
        this.key = obj;
    }

    public void setVerticalChainStyle(int i2) {
        this.mVerticalChainStyle = i2;
    }

    public void setView(Object obj) {
        this.mView = obj;
        ConstraintWidget constraintWidget = this.mConstraintWidget;
        if (constraintWidget != null) {
            constraintWidget.setCompanionWidget(obj);
        }
    }

    public ConstraintReference setWidth(Dimension dimension) {
        this.mHorizontalDimension = dimension;
        return this;
    }

    public ConstraintReference start() {
        this.mLast = this.mStartToStart != null ? State.Constraint.START_TO_START : State.Constraint.START_TO_END;
        return this;
    }

    public ConstraintReference startToEnd(Object obj) {
        this.mLast = State.Constraint.START_TO_END;
        this.mStartToEnd = obj;
        return this;
    }

    public ConstraintReference startToStart(Object obj) {
        this.mLast = State.Constraint.START_TO_START;
        this.mStartToStart = obj;
        return this;
    }

    public ConstraintReference top() {
        this.mLast = this.mTopToTop != null ? State.Constraint.TOP_TO_TOP : State.Constraint.TOP_TO_BOTTOM;
        return this;
    }

    public ConstraintReference topToBottom(Object obj) {
        this.mLast = State.Constraint.TOP_TO_BOTTOM;
        this.mTopToBottom = obj;
        return this;
    }

    public ConstraintReference topToTop(Object obj) {
        this.mLast = State.Constraint.TOP_TO_TOP;
        this.mTopToTop = obj;
        return this;
    }

    public void validate() throws IncorrectConstraintException {
        ArrayList arrayList = new ArrayList();
        if (this.mLeftToLeft != null && this.mLeftToRight != null) {
            arrayList.add("LeftToLeft and LeftToRight both defined");
        }
        if (this.mRightToLeft != null && this.mRightToRight != null) {
            arrayList.add("RightToLeft and RightToRight both defined");
        }
        if (this.mStartToStart != null && this.mStartToEnd != null) {
            arrayList.add("StartToStart and StartToEnd both defined");
        }
        if (this.mEndToStart != null && this.mEndToEnd != null) {
            arrayList.add("EndToStart and EndToEnd both defined");
        }
        if ((this.mLeftToLeft != null || this.mLeftToRight != null || this.mRightToLeft != null || this.mRightToRight != null) && (this.mStartToStart != null || this.mStartToEnd != null || this.mEndToStart != null || this.mEndToEnd != null)) {
            arrayList.add("Both left/right and start/end constraints defined");
        }
        if (arrayList.size() > 0) {
            throw new IncorrectConstraintException(arrayList);
        }
    }

    public ConstraintReference verticalBias(float f2) {
        this.mVerticalBias = f2;
        return this;
    }

    public ConstraintReference width(Dimension dimension) {
        return setWidth(dimension);
    }

    public ConstraintReference margin(Object obj) {
        return margin(this.mState.convertDimension(obj));
    }
}
