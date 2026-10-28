package androidx.core.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.DifferentialMotionFlingController;
import androidx.core.view.DifferentialMotionFlingTarget;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class NestedScrollView extends FrameLayout implements NestedScrollingParent3, NestedScrollingChild3, ScrollingView {
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int DEFAULT_SMOOTH_SCROLL_DURATION = 250;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final float INFLEXION = 0.35f;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final float SCROLL_FRICTION = 0.015f;
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo;

    @VisibleForTesting
    DifferentialMotionFlingController mDifferentialMotionFlingController;

    @VisibleForTesting
    final DifferentialMotionFlingTargetImpl mDifferentialMotionFlingTarget;

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    public EdgeEffect mEdgeGlowBottom;

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    public EdgeEffect mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged;
    private boolean mIsLaidOut;
    private boolean mIsLayoutDirty;
    private int mLastMotionY;
    private long mLastScroll;
    private int mLastScrollerY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private final float mPhysicalCoeff;
    private SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;
    private static final float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    private static final int[] SCROLLVIEW_STYLEABLE = {R.attr.fillViewport};

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0);
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
            AccessibilityRecordCompat.setMaxScrollX(accessibilityEvent, nestedScrollView.getScrollX());
            AccessibilityRecordCompat.setMaxScrollY(accessibilityEvent, nestedScrollView.getScrollRange());
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int scrollRange;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (!nestedScrollView.isEnabled() || (scrollRange = nestedScrollView.getScrollRange()) <= 0) {
                return;
            }
            accessibilityNodeInfoCompat.setScrollable(true);
            if (nestedScrollView.getScrollY() > 0) {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
            }
            if (nestedScrollView.getScrollY() < scrollRange) {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
            if (super.performAccessibilityAction(view, i2, bundle)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            int height = nestedScrollView.getHeight();
            Rect rect = new Rect();
            if (nestedScrollView.getMatrix().isIdentity() && nestedScrollView.getGlobalVisibleRect(rect)) {
                height = rect.height();
            }
            if (i2 != 4096) {
                if (i2 == 8192 || i2 == 16908344) {
                    int iMax = Math.max(nestedScrollView.getScrollY() - ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                    if (iMax == nestedScrollView.getScrollY()) {
                        return false;
                    }
                    nestedScrollView.smoothScrollTo(0, iMax, true);
                    return true;
                }
                if (i2 != 16908346) {
                    return false;
                }
            }
            int iMin = Math.min(nestedScrollView.getScrollY() + ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange());
            if (iMin == nestedScrollView.getScrollY()) {
                return false;
            }
            nestedScrollView.smoothScrollTo(0, iMin, true);
            return true;
        }
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static boolean getClipToPadding(ViewGroup viewGroup) {
            return viewGroup.getClipToPadding();
        }
    }

    public class DifferentialMotionFlingTargetImpl implements DifferentialMotionFlingTarget {
        public DifferentialMotionFlingTargetImpl() {
        }

        @Override // androidx.core.view.DifferentialMotionFlingTarget
        public float getScaledScrollFactor() {
            return -NestedScrollView.this.getVerticalScrollFactorCompat();
        }

        @Override // androidx.core.view.DifferentialMotionFlingTarget
        public boolean startDifferentialMotionFling(float f2) {
            if (f2 == 0.0f) {
                return false;
            }
            stopDifferentialMotionFling();
            NestedScrollView.this.fling((int) f2);
            return true;
        }

        @Override // androidx.core.view.DifferentialMotionFlingTarget
        public void stopDifferentialMotionFling() {
            NestedScrollView.this.mScroller.abortAnimation();
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChange(@NonNull NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.core.widget.NestedScrollView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };
        public int scrollPosition;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        @NonNull
        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.scrollPosition + "}";
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.scrollPosition);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public NestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    private void abortAnimatedScroll() {
        this.mScroller.abortAnimation();
        stopNestedScroll(1);
    }

    private boolean canOverScroll() {
        int overScrollMode = getOverScrollMode();
        if (overScrollMode != 0) {
            return overScrollMode == 1 && getScrollRange() > 0;
        }
        return true;
    }

    private boolean canScroll() {
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        return (childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin > (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private static int clamp(int i2, int i3, int i4) {
        if (i3 >= i4 || i2 < 0) {
            return 0;
        }
        return i3 + i2 > i4 ? i4 - i3 : i2;
    }

    private void doScrollY(int i2) {
        if (i2 != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(0, i2);
            } else {
                scrollBy(0, i2);
            }
        }
    }

    private boolean edgeEffectFling(int i2) {
        EdgeEffect edgeEffect;
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0f) {
            if (shouldAbsorb(this.mEdgeGlowTop, i2)) {
                edgeEffect = this.mEdgeGlowTop;
                edgeEffect.onAbsorb(i2);
            } else {
                i2 = -i2;
                fling(i2);
            }
        } else {
            if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) == 0.0f) {
                return false;
            }
            i2 = -i2;
            if (shouldAbsorb(this.mEdgeGlowBottom, i2)) {
                edgeEffect = this.mEdgeGlowBottom;
                edgeEffect.onAbsorb(i2);
            } else {
                fling(i2);
            }
        }
        return true;
    }

    private void endTouchDrag() {
        this.mActivePointerId = -1;
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
        stopNestedScroll(0);
        this.mEdgeGlowTop.onRelease();
        this.mEdgeGlowBottom.onRelease();
    }

    /* JADX WARN: Code duplicated, block: B:29:0x004f  */
    private View findFocusableViewInBounds(boolean z2, int i2, int i3) {
        ArrayList<View> focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z3 = false;
        for (int i4 = 0; i4 < size; i4++) {
            View view2 = focusables.get(i4);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i2 < bottom && top < i3) {
                boolean z4 = i2 < top && bottom < i3;
                if (view == null) {
                    view = view2;
                    z3 = z4;
                } else {
                    boolean z5 = (z2 && top < view.getTop()) || (!z2 && bottom > view.getBottom());
                    if (z3) {
                        if (z4 && z5) {
                            view = view2;
                        }
                    } else if (z4) {
                        view = view2;
                        z3 = true;
                    } else if (z5) {
                        view = view2;
                    }
                }
            }
        }
        return view;
    }

    private float getSplineFlingDistance(int i2) {
        double dLog = Math.log((Math.abs(i2) * INFLEXION) / (this.mPhysicalCoeff * SCROLL_FRICTION));
        float f2 = DECELERATION_RATE;
        return (float) (Math.exp((((double) f2) / (((double) f2) - 1.0d)) * dLog) * ((double) (this.mPhysicalCoeff * SCROLL_FRICTION)));
    }

    private boolean inChild(int i2, int i3) {
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollY = getScrollY();
        View childAt = getChildAt(0);
        return i3 >= childAt.getTop() - scrollY && i3 < childAt.getBottom() - scrollY && i2 >= childAt.getLeft() && i2 < childAt.getRight();
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void initScrollView() {
        this.mScroller = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void initializeTouchDrag(int i2, int i3) {
        this.mLastMotionY = i2;
        this.mActivePointerId = i3;
        startNestedScroll(2, 0);
    }

    private boolean isOffScreen(View view) {
        return !isWithinDeltaOfScreen(view, 0, getHeight());
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewDescendantOf((View) parent, view2);
    }

    private boolean isWithinDeltaOfScreen(View view, int i2, int i3) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + i2 >= getScrollY() && this.mTempRect.top - i2 <= getScrollY() + i3;
    }

    private void onNestedScrollInternal(int i2, int i3, @Nullable int[] iArr) {
        int scrollY = getScrollY();
        scrollBy(0, i2);
        int scrollY2 = getScrollY() - scrollY;
        if (iArr != null) {
            iArr[1] = iArr[1] + scrollY2;
        }
        this.mChildHelper.dispatchNestedScroll(0, scrollY2, 0, i2 - scrollY2, null, i3, iArr);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.mLastMotionY = (int) motionEvent.getY(i2);
            this.mActivePointerId = motionEvent.getPointerId(i2);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private int releaseVerticalGlow(int i2, float f2) {
        float fOnPullDistance;
        EdgeEffect edgeEffect;
        float width = f2 / getWidth();
        float height = i2 / getHeight();
        float f3 = 0.0f;
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0f) {
            fOnPullDistance = -EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, -height, width);
            if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) == 0.0f) {
                edgeEffect = this.mEdgeGlowTop;
                edgeEffect.onRelease();
            }
            f3 = fOnPullDistance;
        } else if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) != 0.0f) {
            fOnPullDistance = EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, height, 1.0f - width);
            if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) == 0.0f) {
                edgeEffect = this.mEdgeGlowBottom;
                edgeEffect.onRelease();
            }
            f3 = fOnPullDistance;
        }
        int iRound = Math.round(f3 * getHeight());
        if (iRound != 0) {
            invalidate();
        }
        return iRound;
    }

    private void runAnimatedScroll(boolean z2) {
        if (z2) {
            startNestedScroll(2, 1);
        } else {
            stopNestedScroll(1);
        }
        this.mLastScrollerY = getScrollY();
        postInvalidateOnAnimation();
    }

    private boolean scrollAndFocus(int i2, int i3, int i4) {
        int height = getHeight();
        int scrollY = getScrollY();
        int i5 = height + scrollY;
        boolean z2 = false;
        boolean z3 = i2 == 33;
        View viewFindFocusableViewInBounds = findFocusableViewInBounds(z3, i3, i4);
        if (viewFindFocusableViewInBounds == null) {
            viewFindFocusableViewInBounds = this;
        }
        if (i3 < scrollY || i4 > i5) {
            scrollBy(z3 ? i3 - scrollY : i4 - i5, 0, 1, true);
            z2 = true;
        }
        if (viewFindFocusableViewInBounds != findFocus()) {
            viewFindFocusableViewInBounds.requestFocus(i2);
        }
        return z2;
    }

    private int scrollBy(int i2, int i3, int i4, boolean z2) {
        int i5;
        int i6;
        EdgeEffect edgeEffect;
        VelocityTracker velocityTracker;
        if (i4 == 1) {
            startNestedScroll(2, i4);
        }
        if (dispatchNestedPreScroll(0, i2, this.mScrollConsumed, this.mScrollOffset, i4)) {
            i5 = i2 - this.mScrollConsumed[1];
            i6 = this.mScrollOffset[1];
        } else {
            i5 = i2;
            i6 = 0;
        }
        int scrollY = getScrollY();
        int scrollRange = getScrollRange();
        boolean z3 = canOverScroll() && !z2;
        boolean z4 = overScrollByCompat(0, i5, 0, scrollY, 0, scrollRange, 0, 0, true) && !hasNestedScrollingParent(i4);
        int scrollY2 = getScrollY() - scrollY;
        int[] iArr = this.mScrollConsumed;
        iArr[1] = 0;
        dispatchNestedScroll(0, scrollY2, 0, i5 - scrollY2, this.mScrollOffset, i4, iArr);
        int i7 = i6 + this.mScrollOffset[1];
        int i8 = i5 - this.mScrollConsumed[1];
        int i9 = scrollY + i8;
        if (i9 < 0) {
            if (z3) {
                EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, (-i8) / getHeight(), i3 / getWidth());
                if (!this.mEdgeGlowBottom.isFinished()) {
                    edgeEffect = this.mEdgeGlowBottom;
                    edgeEffect.onRelease();
                }
            }
        } else if (i9 > scrollRange && z3) {
            EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, i8 / getHeight(), 1.0f - (i3 / getWidth()));
            if (!this.mEdgeGlowTop.isFinished()) {
                edgeEffect = this.mEdgeGlowTop;
                edgeEffect.onRelease();
            }
        }
        if (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished()) {
            postInvalidateOnAnimation();
        } else if (z4 && i4 == 0 && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.clear();
        }
        if (i4 == 1) {
            stopNestedScroll(i4);
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        return i7;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (iComputeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean z2) {
        int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z3 = iComputeScrollDeltaToGetChildRectOnScreen != 0;
        if (z3) {
            if (z2) {
                scrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen);
            } else {
                smoothScrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen);
            }
        }
        return z3;
    }

    private boolean shouldAbsorb(@NonNull EdgeEffect edgeEffect, int i2) {
        if (i2 > 0) {
            return true;
        }
        return getSplineFlingDistance(-i2) < EdgeEffectCompat.getDistance(edgeEffect) * ((float) getHeight());
    }

    private boolean stopGlowAnimations(MotionEvent motionEvent) {
        boolean z2;
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0f) {
            EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, 0.0f, motionEvent.getX() / getWidth());
            z2 = true;
        } else {
            z2 = false;
        }
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) == 0.0f) {
            return z2;
        }
        EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, 0.0f, 1.0f - (motionEvent.getX() / getWidth()));
        return true;
    }

    @Override // android.view.ViewGroup
    public void addView(@NonNull View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public boolean arrowScroll(int i2) {
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i2);
        int maxScrollAmount = getMaxScrollAmount();
        if (viewFindNextFocus == null || !isWithinDeltaOfScreen(viewFindNextFocus, maxScrollAmount, getHeight())) {
            if (i2 == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i2 == 130 && getChildCount() > 0) {
                View childAt = getChildAt(0);
                maxScrollAmount = Math.min((childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin) - ((getHeight() + getScrollY()) - getPaddingBottom()), maxScrollAmount);
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i2 != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            scrollBy(maxScrollAmount, 0, 1, true);
        } else {
            viewFindNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.mTempRect);
            scrollBy(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect), 0, 1, true);
            viewFindNextFocus.requestFocus(i2);
        }
        if (viewFindFocus != null && viewFindFocus.isFocused() && isOffScreen(viewFindFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    public void computeScroll() {
        EdgeEffect edgeEffect;
        if (this.mScroller.isFinished()) {
            return;
        }
        this.mScroller.computeScrollOffset();
        int currY = this.mScroller.getCurrY();
        int iConsumeFlingInVerticalStretch = consumeFlingInVerticalStretch(currY - this.mLastScrollerY);
        this.mLastScrollerY = currY;
        int[] iArr = this.mScrollConsumed;
        iArr[1] = 0;
        dispatchNestedPreScroll(0, iConsumeFlingInVerticalStretch, iArr, null, 1);
        int i2 = iConsumeFlingInVerticalStretch - this.mScrollConsumed[1];
        int scrollRange = getScrollRange();
        if (i2 != 0) {
            int scrollY = getScrollY();
            overScrollByCompat(0, i2, getScrollX(), scrollY, 0, scrollRange, 0, 0, false);
            int scrollY2 = getScrollY() - scrollY;
            int i3 = i2 - scrollY2;
            int[] iArr2 = this.mScrollConsumed;
            iArr2[1] = 0;
            dispatchNestedScroll(0, scrollY2, 0, i3, this.mScrollOffset, 1, iArr2);
            i2 = i3 - this.mScrollConsumed[1];
        }
        if (i2 != 0) {
            int overScrollMode = getOverScrollMode();
            if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) {
                if (i2 < 0) {
                    if (this.mEdgeGlowTop.isFinished()) {
                        edgeEffect = this.mEdgeGlowTop;
                        edgeEffect.onAbsorb((int) this.mScroller.getCurrVelocity());
                    }
                } else if (this.mEdgeGlowBottom.isFinished()) {
                    edgeEffect = this.mEdgeGlowBottom;
                    edgeEffect.onAbsorb((int) this.mScroller.getCurrVelocity());
                }
            }
            abortAnimatedScroll();
        }
        if (this.mScroller.isFinished()) {
            stopNestedScroll(1);
        } else {
            postInvalidateOnAnimation();
        }
    }

    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i2 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int i3 = rect.bottom < (childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin ? i2 - verticalFadingEdgeLength : i2;
        int i4 = rect.bottom;
        if (i4 > i3 && rect.top > scrollY) {
            return Math.min(rect.height() > height ? rect.top - scrollY : rect.bottom - i3, (childAt.getBottom() + layoutParams.bottomMargin) - i2);
        }
        if (rect.top >= scrollY || i4 >= i3) {
            return 0;
        }
        return Math.max(rect.height() > height ? 0 - (i3 - rect.bottom) : 0 - (scrollY - rect.top), -getScrollY());
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.View, androidx.core.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        View childAt = getChildAt(0);
        int bottom = childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
        int scrollY = getScrollY();
        int iMax = Math.max(0, bottom - height);
        if (scrollY < 0) {
            return bottom - scrollY;
        }
        return scrollY > iMax ? bottom + (scrollY - iMax) : bottom;
    }

    public int consumeFlingInVerticalStretch(int i2) {
        int height = getHeight();
        if (i2 > 0 && EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0f) {
            int iRound = Math.round(EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, ((-i2) * FLING_DESTRETCH_FACTOR) / height, 0.5f) * ((-height) / FLING_DESTRETCH_FACTOR));
            if (iRound != i2) {
                this.mEdgeGlowTop.finish();
            }
            return i2 - iRound;
        }
        if (i2 >= 0 || EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) == 0.0f) {
            return i2;
        }
        float f2 = height;
        int iRound2 = Math.round(EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, (i2 * FLING_DESTRETCH_FACTOR) / f2, 0.5f) * (f2 / FLING_DESTRETCH_FACTOR));
        if (iRound2 != i2) {
            this.mEdgeGlowBottom.finish();
        }
        return i2 - iRound2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.mChildHelper.dispatchNestedFling(f2, f3, z2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.mChildHelper.dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2) {
        return dispatchNestedPreScroll(i2, i3, iArr, iArr2, 0);
    }

    @Override // androidx.core.view.NestedScrollingChild3
    public void dispatchNestedScroll(int i2, int i3, int i4, int i5, @Nullable int[] iArr, int i6, @NonNull int[] iArr2) {
        this.mChildHelper.dispatchNestedScroll(i2, i3, i4, i5, iArr, i6, iArr2);
    }

    @Override // android.view.View
    public void draw(@NonNull Canvas canvas) {
        int paddingLeft;
        super.draw(canvas);
        int scrollY = getScrollY();
        int paddingLeft2 = 0;
        if (!this.mEdgeGlowTop.isFinished()) {
            int iSave = canvas.save();
            int width = getWidth();
            int height = getHeight();
            int iMin = Math.min(0, scrollY);
            if (Api21Impl.getClipToPadding(this)) {
                width -= getPaddingRight() + getPaddingLeft();
                paddingLeft = getPaddingLeft();
            } else {
                paddingLeft = 0;
            }
            if (Api21Impl.getClipToPadding(this)) {
                height -= getPaddingBottom() + getPaddingTop();
                iMin += getPaddingTop();
            }
            canvas.translate(paddingLeft, iMin);
            this.mEdgeGlowTop.setSize(width, height);
            if (this.mEdgeGlowTop.draw(canvas)) {
                postInvalidateOnAnimation();
            }
            canvas.restoreToCount(iSave);
        }
        if (this.mEdgeGlowBottom.isFinished()) {
            return;
        }
        int iSave2 = canvas.save();
        int width2 = getWidth();
        int height2 = getHeight();
        int iMax = Math.max(getScrollRange(), scrollY) + height2;
        if (Api21Impl.getClipToPadding(this)) {
            width2 -= getPaddingRight() + getPaddingLeft();
            paddingLeft2 = getPaddingLeft();
        }
        if (Api21Impl.getClipToPadding(this)) {
            height2 -= getPaddingBottom() + getPaddingTop();
            iMax -= getPaddingBottom();
        }
        canvas.translate(paddingLeft2 - width2, iMax);
        canvas.rotate(180.0f, width2, 0.0f);
        this.mEdgeGlowBottom.setSize(width2, height2);
        if (this.mEdgeGlowBottom.draw(canvas)) {
            postInvalidateOnAnimation();
        }
        canvas.restoreToCount(iSave2);
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        int i2 = 130;
        if (!canScroll()) {
            if (!isFocused() || keyEvent.getKeyCode() == 4) {
                return false;
            }
            View viewFindFocus = findFocus();
            if (viewFindFocus == this) {
                viewFindFocus = null;
            }
            View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, 130);
            return (viewFindNextFocus == null || viewFindNextFocus == this || !viewFindNextFocus.requestFocus(130)) ? false : true;
        }
        if (keyEvent.getAction() != 0) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 19) {
            if (keyCode != 20) {
                if (keyCode != 62) {
                    if (keyCode != 92) {
                        if (keyCode != 93) {
                            if (keyCode == 122) {
                                pageScroll(33);
                                return false;
                            }
                            if (keyCode != 123) {
                                return false;
                            }
                        }
                    }
                } else if (keyEvent.isShiftPressed()) {
                    i2 = 33;
                }
                pageScroll(i2);
                return false;
            }
            if (!keyEvent.isAltPressed()) {
                return arrowScroll(130);
            }
            return fullScroll(130);
        }
        if (!keyEvent.isAltPressed()) {
            return arrowScroll(33);
        }
        return fullScroll(33);
    }

    public void fling(int i2) {
        if (getChildCount() > 0) {
            this.mScroller.fling(getScrollX(), getScrollY(), 0, i2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            runAnimatedScroll(true);
        }
    }

    public boolean fullScroll(int i2) {
        int childCount;
        boolean z2 = i2 == 130;
        int height = getHeight();
        Rect rect = this.mTempRect;
        rect.top = 0;
        rect.bottom = height;
        if (z2 && (childCount = getChildCount()) > 0) {
            View childAt = getChildAt(childCount - 1);
            this.mTempRect.bottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            Rect rect2 = this.mTempRect;
            rect2.top = rect2.bottom - height;
        }
        Rect rect3 = this.mTempRect;
        return scrollAndFocus(i2, rect3.top, rect3.bottom);
    }

    @Override // android.view.View
    public float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = ((childAt.getBottom() + layoutParams.bottomMargin) - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int) (getHeight() * 0.5f);
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    public int getScrollRange() {
        if (getChildCount() <= 0) {
            return 0;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        return Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom()));
    }

    @Override // android.view.View
    public float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return scrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    @VisibleForTesting
    public float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = getContext();
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return hasNestedScrollingParent(0);
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    @Override // android.view.ViewGroup
    public void measureChild(@NonNull View view, int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    @Override // android.view.ViewGroup
    public void measureChildWithMargins(View view, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(@NonNull MotionEvent motionEvent) {
        int i2;
        float axisValue;
        int width;
        if (motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
            if (MotionEventCompat.isFromSource(motionEvent, 2)) {
                i2 = 9;
                axisValue = motionEvent.getAxisValue(9);
                width = (int) motionEvent.getX();
            } else if (MotionEventCompat.isFromSource(motionEvent, 4194304)) {
                axisValue = motionEvent.getAxisValue(26);
                width = getWidth() / 2;
                i2 = 26;
            } else {
                i2 = 0;
                axisValue = 0.0f;
                width = 0;
            }
            if (axisValue != 0.0f) {
                scrollBy(-((int) (getVerticalScrollFactorCompat() * axisValue)), width, 1, MotionEventCompat.isFromSource(motionEvent, 8194));
                if (i2 != 0) {
                    this.mDifferentialMotionFlingController.onMotionEvent(motionEvent, i2);
                }
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0063  */
    /* JADX WARN: Code duplicated, block: B:33:0x0081  */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(@NonNull MotionEvent motionEvent) {
        int iFindPointerIndex;
        int action = motionEvent.getAction();
        boolean z2 = true;
        if (action == 2 && this.mIsBeingDragged) {
            return true;
        }
        int i2 = action & 255;
        if (i2 == 0) {
            int y2 = (int) motionEvent.getY();
            if (inChild((int) motionEvent.getX(), y2)) {
                this.mLastMotionY = y2;
                this.mActivePointerId = motionEvent.getPointerId(0);
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mScroller.computeScrollOffset();
                if (!stopGlowAnimations(motionEvent) && this.mScroller.isFinished()) {
                    z2 = false;
                }
                this.mIsBeingDragged = z2;
                startNestedScroll(2, 0);
            } else {
                if (!stopGlowAnimations(motionEvent) && this.mScroller.isFinished()) {
                    z2 = false;
                }
                this.mIsBeingDragged = z2;
                recycleVelocityTracker();
            }
        } else if (i2 == 1) {
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            recycleVelocityTracker();
            if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                postInvalidateOnAnimation();
            }
            stopNestedScroll(0);
        } else if (i2 == 2) {
            int i3 = this.mActivePointerId;
            if (i3 != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i3)) != -1) {
                int y3 = (int) motionEvent.getY(iFindPointerIndex);
                if (Math.abs(y3 - this.mLastMotionY) > this.mTouchSlop && (2 & getNestedScrollAxes()) == 0) {
                    this.mIsBeingDragged = true;
                    this.mLastMotionY = y3;
                    initVelocityTrackerIfNotExists();
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mNestedYOffset = 0;
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
            }
        } else if (i2 == 3) {
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            recycleVelocityTracker();
            if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                postInvalidateOnAnimation();
            }
            stopNestedScroll(0);
        } else if (i2 == 6) {
            onSecondaryPointerUp(motionEvent);
        }
        return this.mIsBeingDragged;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        int measuredHeight = 0;
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && isViewDescendantOf(view, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                scrollTo(getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
            int paddingTop = ((i5 - i3) - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            int iClamp = clamp(scrollY, paddingTop, measuredHeight);
            if (iClamp != scrollY) {
                scrollTo(getScrollX(), iClamp);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.mIsLaidOut = true;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.mFillViewport && View.MeasureSpec.getMode(i3) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredHeight2 = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - layoutParams.topMargin) - layoutParams.bottomMargin;
            if (measuredHeight < measuredHeight2) {
                childAt.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight2, BasicMeasure.EXACTLY));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(@NonNull View view, float f2, float f3, boolean z2) {
        if (z2) {
            return false;
        }
        dispatchNestedFling(0.0f, f3, true);
        fling((int) f3);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(@NonNull View view, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(@NonNull View view, int i2, int i3, @NonNull int[] iArr) {
        onNestedPreScroll(view, i2, i3, iArr, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5) {
        onNestedScrollInternal(i5, 0, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i2) {
        onNestedScrollAccepted(view, view2, i2, 0);
    }

    @Override // android.view.View
    public void onOverScrolled(int i2, int i3, boolean z2, boolean z3) {
        super.scrollTo(i2, i3);
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (i2 == 2) {
            i2 = 130;
        } else if (i2 == 1) {
            i2 = 33;
        }
        FocusFinder focusFinder = FocusFinder.getInstance();
        View viewFindNextFocus = rect == null ? focusFinder.findNextFocus(this, null, i2) : focusFinder.findNextFocusFromRect(this, rect, i2);
        if (viewFindNextFocus == null || isOffScreen(viewFindNextFocus)) {
            return false;
        }
        return viewFindNextFocus.requestFocus(i2, rect);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSavedState = savedState;
        requestLayout();
    }

    @Override // android.view.View
    @NonNull
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = getScrollY();
        return savedState;
    }

    @Override // android.view.View
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
        OnScrollChangeListener onScrollChangeListener = this.mOnScrollChangeListener;
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChange(this, i2, i3, i4, i5);
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        View viewFindFocus = findFocus();
        if (viewFindFocus == null || this == viewFindFocus || !isWithinDeltaOfScreen(viewFindFocus, 0, i5)) {
            return;
        }
        viewFindFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(viewFindFocus, this.mTempRect);
        doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i2) {
        return onStartNestedScroll(view, view2, i2, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(@NonNull View view) {
        onStopNestedScroll(view, 0);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0070  */
    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        ViewParent parent;
        initVelocityTrackerIfNotExists();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.offsetLocation(0.0f, this.mNestedYOffset);
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                if (Math.abs(yVelocity) >= this.mMinimumVelocity) {
                    if (!edgeEffectFling(yVelocity)) {
                        int i2 = -yVelocity;
                        float f2 = i2;
                        if (!dispatchNestedPreFling(0.0f, f2)) {
                            dispatchNestedFling(0.0f, f2, true);
                            fling(i2);
                        }
                    }
                } else if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    postInvalidateOnAnimation();
                }
                endTouchDrag();
            } else if (actionMasked == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (iFindPointerIndex != -1) {
                    int y2 = (int) motionEvent.getY(iFindPointerIndex);
                    int i3 = this.mLastMotionY - y2;
                    int iReleaseVerticalGlow = i3 - releaseVerticalGlow(i3, motionEvent.getX(iFindPointerIndex));
                    if (!this.mIsBeingDragged && Math.abs(iReleaseVerticalGlow) > this.mTouchSlop) {
                        ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        int i4 = this.mTouchSlop;
                        iReleaseVerticalGlow = iReleaseVerticalGlow > 0 ? iReleaseVerticalGlow - i4 : iReleaseVerticalGlow + i4;
                    }
                    if (this.mIsBeingDragged) {
                        int iScrollBy = scrollBy(iReleaseVerticalGlow, (int) motionEvent.getX(iFindPointerIndex), 0, false);
                        this.mLastMotionY = y2 - iScrollBy;
                        this.mNestedYOffset += iScrollBy;
                    }
                }
            } else if (actionMasked == 3) {
                if (this.mIsBeingDragged && getChildCount() > 0 && this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    postInvalidateOnAnimation();
                }
                endTouchDrag();
            } else if (actionMasked == 5) {
                int actionIndex = motionEvent.getActionIndex();
                this.mLastMotionY = (int) motionEvent.getY(actionIndex);
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
            } else if (actionMasked == 6) {
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionY = (int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
            }
        } else {
            if (getChildCount() == 0) {
                return false;
            }
            if (this.mIsBeingDragged && (parent = getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                abortAnimatedScroll();
            }
            initializeTouchDrag((int) motionEvent.getY(), motionEvent.getPointerId(0));
        }
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEventObtain);
        }
        motionEventObtain.recycle();
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0057  */
    /* JADX WARN: Code duplicated, block: B:40:0x005a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x005c  */
    /* JADX WARN: Code duplicated, block: B:42:0x005e  */
    /* JADX WARN: Code duplicated, block: B:44:0x0061  */
    /* JADX WARN: Code duplicated, block: B:49:0x0083 A[ADDED_TO_REGION] */
    public boolean overScrollByCompat(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2) {
        boolean z3;
        boolean z4;
        int overScrollMode = getOverScrollMode();
        boolean z5 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean z6 = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean z7 = overScrollMode == 0 || (overScrollMode == 1 && z5);
        boolean z8 = overScrollMode == 0 || (overScrollMode == 1 && z6);
        int i10 = i4 + i2;
        int i11 = !z7 ? 0 : i8;
        int i12 = i5 + i3;
        int i13 = !z8 ? 0 : i9;
        int i14 = -i11;
        int i15 = i11 + i6;
        int i16 = -i13;
        int i17 = i13 + i7;
        if (i10 <= i15) {
            if (i10 < i14) {
                i10 = i14;
            } else {
                z3 = false;
            }
            if (i12 > i17) {
                if (i12 < i16) {
                    i12 = i16;
                } else {
                    z4 = false;
                }
                if (z4 && !hasNestedScrollingParent(1)) {
                    this.mScroller.springBack(i10, i12, 0, 0, 0, getScrollRange());
                }
                onOverScrolled(i10, i12, z3, z4);
                return !z3 || z4;
            }
            i12 = i17;
            z4 = true;
            if (z4) {
                this.mScroller.springBack(i10, i12, 0, 0, 0, getScrollRange());
            }
            onOverScrolled(i10, i12, z3, z4);
            if (z3) {
            }
        }
        i10 = i15;
        z3 = true;
        if (i12 > i17) {
            if (i12 < i16) {
                i12 = i16;
            } else {
                z4 = false;
            }
            if (z4) {
                this.mScroller.springBack(i10, i12, 0, 0, 0, getScrollRange());
            }
            onOverScrolled(i10, i12, z3, z4);
            if (z3) {
            }
        }
        i12 = i17;
        z4 = true;
        if (z4) {
            this.mScroller.springBack(i10, i12, 0, 0, 0, getScrollRange());
        }
        onOverScrolled(i10, i12, z3, z4);
        if (z3) {
        }
    }

    public boolean pageScroll(int i2) {
        Rect rect;
        int i3 = 0;
        boolean z2 = i2 == 130;
        int height = getHeight();
        if (z2) {
            this.mTempRect.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                int paddingBottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
                rect = this.mTempRect;
                if (rect.top + height > paddingBottom) {
                    i3 = paddingBottom - height;
                    rect.top = i3;
                }
            }
        } else {
            this.mTempRect.top = getScrollY() - height;
            rect = this.mTempRect;
            if (rect.top < 0) {
                rect.top = i3;
            }
        }
        Rect rect2 = this.mTempRect;
        int i4 = rect2.top;
        int i5 = height + i4;
        rect2.bottom = i5;
        return scrollAndFocus(i2, i4, i5);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (this.mIsLayoutDirty) {
            this.mChildToScrollTo = view2;
        } else {
            scrollToChild(view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(@NonNull View view, Rect rect, boolean z2) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return scrollToChildRect(rect, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z2) {
        if (z2) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override // android.view.View
    public void scrollTo(int i2, int i3) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
            int width2 = childAt.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int height2 = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int iClamp = clamp(i2, width, width2);
            int iClamp2 = clamp(i3, height, height2);
            if (iClamp == getScrollX() && iClamp2 == getScrollY()) {
                return;
            }
            super.scrollTo(iClamp, iClamp2);
        }
    }

    public void setFillViewport(boolean z2) {
        if (z2 != this.mFillViewport) {
            this.mFillViewport = z2;
            requestLayout();
        }
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z2) {
        this.mChildHelper.setNestedScrollingEnabled(z2);
    }

    public void setOnScrollChangeListener(@Nullable OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setSmoothScrollingEnabled(boolean z2) {
        this.mSmoothScrollingEnabled = z2;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void smoothScrollBy(int i2, int i3) {
        smoothScrollBy(i2, i3, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, false);
    }

    public final void smoothScrollTo(int i2, int i3) {
        smoothScrollTo(i2, i3, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, false);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i2) {
        return startNestedScroll(i2, 0);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        stopNestedScroll(0);
    }

    public NestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, androidx.core.R.attr.nestedScrollViewStyle);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i2);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2, int i4) {
        return this.mChildHelper.dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, @Nullable int[] iArr) {
        return this.mChildHelper.dispatchNestedScroll(i2, i3, i4, i5, iArr);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean hasNestedScrollingParent(int i2) {
        return this.mChildHelper.hasNestedScrollingParent(i2);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(@NonNull View view, int i2, int i3, @NonNull int[] iArr, int i4) {
        dispatchNestedPreScroll(i2, i3, iArr, null, i4);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5, int i6) {
        onNestedScrollInternal(i5, i6, null);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i2, int i3) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, i2, i3);
        startNestedScroll(2, i3);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i2, int i3) {
        return (i2 & 2) != 0;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(@NonNull View view, int i2) {
        this.mParentHelper.onStopNestedScroll(view, i2);
        stopNestedScroll(i2);
    }

    public final void smoothScrollBy(int i2, int i3, int i4) {
        smoothScrollBy(i2, i3, i4, false);
    }

    public final void smoothScrollTo(int i2, int i3, int i4) {
        smoothScrollTo(i2, i3, i4, false);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean startNestedScroll(int i2, int i3) {
        return this.mChildHelper.startNestedScroll(i2, i3);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public void stopNestedScroll(int i2) {
        this.mChildHelper.stopNestedScroll(i2);
    }

    public NestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mIsLaidOut = false;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        DifferentialMotionFlingTargetImpl differentialMotionFlingTargetImpl = new DifferentialMotionFlingTargetImpl();
        this.mDifferentialMotionFlingTarget = differentialMotionFlingTargetImpl;
        this.mDifferentialMotionFlingController = new DifferentialMotionFlingController(getContext(), differentialMotionFlingTargetImpl);
        this.mEdgeGlowTop = EdgeEffectCompat.create(context, attributeSet);
        this.mEdgeGlowBottom = EdgeEffectCompat.create(context, attributeSet);
        this.mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        initScrollView();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, i2, 0);
        setFillViewport(typedArrayObtainStyledAttributes.getBoolean(0, false));
        typedArrayObtainStyledAttributes.recycle();
        this.mParentHelper = new NestedScrollingParentHelper(this);
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
    }

    private void smoothScrollBy(int i2, int i3, int i4, boolean z2) {
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int height = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            this.mScroller.startScroll(getScrollX(), scrollY, 0, Math.max(0, Math.min(i3 + scrollY, Math.max(0, height - height2))) - scrollY, i4);
            runAnimatedScroll(z2);
        } else {
            if (!this.mScroller.isFinished()) {
                abortAnimatedScroll();
            }
            scrollBy(i2, i3);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i2, layoutParams);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, @Nullable int[] iArr, int i6) {
        return this.mChildHelper.dispatchNestedScroll(i2, i3, i4, i5, iArr, i6);
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(@NonNull View view, int i2, int i3, int i4, int i5, int i6, @NonNull int[] iArr) {
        onNestedScrollInternal(i5, i6, iArr);
    }

    public void smoothScrollTo(int i2, int i3, int i4, boolean z2) {
        smoothScrollBy(i2 - getScrollX(), i3 - getScrollY(), i4, z2);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    public void smoothScrollTo(int i2, int i3, boolean z2) {
        smoothScrollTo(i2, i3, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, z2);
    }
}
