package com.google.android.material.bottomsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import androidx.activity.BackEventCompat;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.graphics.drawable.a;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.color.utilities.Contrast;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialBottomContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> implements MaterialBackHandler {
    private static final int CORNER_ANIMATION_DURATION = 500;

    @VisibleForTesting
    static final int DEFAULT_SIGNIFICANT_VEL_THRESHOLD = 500;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final int INVALID_POSITION = -1;
    private static final int NO_MAX_SIZE = -1;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";

    @VisibleForTesting
    static final int VIEW_INDEX_ACCESSIBILITY_DELEGATE_VIEW = 1;
    private static final int VIEW_INDEX_BOTTOM_SHEET = 0;

    @Nullable
    WeakReference<View> accessibilityDelegateViewRef;
    int activePointerId;

    @Nullable
    private ColorStateList backgroundTint;

    @Nullable
    MaterialBottomContainerBackHelper bottomContainerBackHelper;

    @NonNull
    private final ArrayList<BottomSheetCallback> callbacks;
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback;
    private boolean draggable;
    float elevation;

    @VisibleForTesting
    final SparseIntArray expandHalfwayActionIds;
    private boolean expandedCornersRemoved;
    int expandedOffset;
    private boolean fitToContents;
    int fitToContentsOffset;
    private int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio;
    private float hideFriction;
    boolean hideable;
    private boolean ignoreEvents;

    @Nullable
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    private int insetBottom;
    private int insetTop;

    @Nullable
    private ValueAnimator interpolatorAnimator;
    private int lastNestedScrollDy;
    int lastStableState;
    private boolean marginLeftSystemWindowInsets;
    private boolean marginRightSystemWindowInsets;
    private boolean marginTopSystemWindowInsets;
    private MaterialShapeDrawable materialShapeDrawable;
    private int maxHeight;
    private int maxWidth;
    private float maximumVelocity;
    private boolean nestedScrolled;

    @Nullable
    WeakReference<View> nestedScrollingChildRef;
    private boolean paddingBottomSystemWindowInsets;
    private boolean paddingLeftSystemWindowInsets;
    private boolean paddingRightSystemWindowInsets;
    private boolean paddingTopSystemWindowInsets;
    int parentHeight;
    int parentWidth;
    private int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shouldRemoveExpandedCorners;
    private int significantVelocityThreshold;
    private boolean skipCollapsed;
    int state;
    private final BottomSheetBehavior<V>.StateSettlingTracker stateSettlingTracker;
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings;

    @Nullable
    private VelocityTracker velocityTracker;

    @Nullable
    ViewDragHelper viewDragHelper;

    @Nullable
    WeakReference<V> viewRef;

    public static abstract class BottomSheetCallback {
        public void onLayout(@NonNull View view) {
        }

        public abstract void onSlide(@NonNull View view, float f2);

        public abstract void onStateChanged(@NonNull View view, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface SaveFlags {
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.SavedState.1
            @Override // android.os.Parcelable.Creator
            @Nullable
            public SavedState createFromParcel(@NonNull Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            @Override // android.os.Parcelable.Creator
            @NonNull
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            @NonNull
            public SavedState createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        };
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(@NonNull Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt(this.fitToContents ? 1 : 0);
            parcel.writeInt(this.hideable ? 1 : 0);
            parcel.writeInt(this.skipCollapsed ? 1 : 0);
        }

        public SavedState(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            this.fitToContents = parcel.readInt() == 1;
            this.hideable = parcel.readInt() == 1;
            this.skipCollapsed = parcel.readInt() == 1;
        }

        @Deprecated
        public SavedState(Parcelable parcelable, int i2) {
            super(parcelable);
            this.state = i2;
        }

        public SavedState(Parcelable parcelable, @NonNull BottomSheetBehavior<?> bottomSheetBehavior) {
            super(parcelable);
            this.state = bottomSheetBehavior.state;
            this.peekHeight = ((BottomSheetBehavior) bottomSheetBehavior).peekHeight;
            this.fitToContents = ((BottomSheetBehavior) bottomSheetBehavior).fitToContents;
            this.hideable = bottomSheetBehavior.hideable;
            this.skipCollapsed = ((BottomSheetBehavior) bottomSheetBehavior).skipCollapsed;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface StableState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public class StateSettlingTracker {
        private final Runnable continueSettlingRunnable;
        private boolean isContinueSettlingRunnablePosted;
        private int targetState;

        private StateSettlingTracker() {
            this.continueSettlingRunnable = new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.StateSettlingTracker.1
                @Override // java.lang.Runnable
                public void run() {
                    StateSettlingTracker.this.isContinueSettlingRunnablePosted = false;
                    ViewDragHelper viewDragHelper = BottomSheetBehavior.this.viewDragHelper;
                    if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
                        StateSettlingTracker stateSettlingTracker = StateSettlingTracker.this;
                        stateSettlingTracker.continueSettlingToState(stateSettlingTracker.targetState);
                        return;
                    }
                    StateSettlingTracker stateSettlingTracker2 = StateSettlingTracker.this;
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                    if (bottomSheetBehavior.state == 2) {
                        bottomSheetBehavior.setStateInternal(stateSettlingTracker2.targetState);
                    }
                }
            };
        }

        public void continueSettlingToState(int i2) {
            WeakReference<V> weakReference = BottomSheetBehavior.this.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.targetState = i2;
            if (this.isContinueSettlingRunnablePosted) {
                return;
            }
            ViewCompat.postOnAnimation(BottomSheetBehavior.this.viewRef.get(), this.continueSettlingRunnable);
            this.isContinueSettlingRunnablePosted = true;
        }
    }

    public BottomSheetBehavior() {
        this.saveFlags = 0;
        this.fitToContents = true;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.stateSettlingTracker = new StateSettlingTracker();
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.lastStableState = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList<>();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            private long viewCapturedMillis;

            private boolean releasedLow(@NonNull View view) {
                int top = view.getTop();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return top > (bottomSheetBehavior.getExpandedOffset() + bottomSheetBehavior.parentHeight) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i2, int i3) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i2, int i3) {
                return MathUtils.clamp(i2, BottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange(view));
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                return BottomSheetBehavior.this.canBeHiddenByDragging() ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i2) {
                if (i2 == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i2, int i3, int i4, int i5) {
                BottomSheetBehavior.this.dispatchOnSlide(i3);
            }

            /* JADX WARN: Code duplicated, block: B:39:0x00ae  */
            /* JADX WARN: Code duplicated, block: B:6:0x0010  */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f2, float f3) {
                int i2 = 6;
                if (f3 >= 0.0f) {
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                    if (bottomSheetBehavior.hideable && bottomSheetBehavior.shouldHide(view, f3)) {
                        if ((Math.abs(f2) < Math.abs(f3) && f3 > BottomSheetBehavior.this.significantVelocityThreshold) || releasedLow(view)) {
                            i2 = 5;
                        } else if (BottomSheetBehavior.this.fitToContents || Math.abs(view.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                            i2 = 3;
                        }
                    } else if (f3 == 0.0f || Math.abs(f2) > Math.abs(f3)) {
                        int top = view.getTop();
                        if (!BottomSheetBehavior.this.fitToContents) {
                            BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.this;
                            int i3 = bottomSheetBehavior2.halfExpandedOffset;
                            if (top < i3) {
                                if (top < Math.abs(top - bottomSheetBehavior2.collapsedOffset)) {
                                    i2 = 3;
                                } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                    i2 = 4;
                                }
                            } else if (Math.abs(top - i3) >= Math.abs(top - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                i2 = 4;
                            }
                        } else if (Math.abs(top - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                            i2 = 3;
                        } else {
                            i2 = 4;
                        }
                    } else if (BottomSheetBehavior.this.fitToContents) {
                        i2 = 4;
                    } else {
                        int top2 = view.getTop();
                        if (Math.abs(top2 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(top2 - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            i2 = 4;
                        }
                    }
                } else if (BottomSheetBehavior.this.fitToContents) {
                    i2 = 3;
                } else {
                    int top3 = view.getTop();
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                    if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        BottomSheetBehavior bottomSheetBehavior3 = BottomSheetBehavior.this;
                        if (!bottomSheetBehavior3.shouldExpandOnUpwardDrag(jCurrentTimeMillis, (top3 * 100.0f) / bottomSheetBehavior3.parentHeight)) {
                            i2 = 4;
                        }
                    } else if (top3 <= BottomSheetBehavior.this.halfExpandedOffset) {
                    }
                    i2 = 3;
                }
                BottomSheetBehavior bottomSheetBehavior4 = BottomSheetBehavior.this;
                bottomSheetBehavior4.startSettling(view, i2, bottomSheetBehavior4.shouldSkipSmoothAnimation());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i2) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i3 = bottomSheetBehavior.state;
                if (i3 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i3 == 3 && bottomSheetBehavior.activePointerId == i2) {
                    WeakReference<View> weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                this.viewCapturedMillis = System.currentTimeMillis();
                WeakReference<V> weakReference2 = BottomSheetBehavior.this.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
    }

    private int addAccessibilityActionForState(View view, @StringRes int i2, int i3) {
        return ViewCompat.addAccessibilityAction(view, view.getResources().getString(i2), createAccessibilityViewCommandForState(i3));
    }

    private void calculateCollapsedOffset() {
        int iCalculatePeekHeight = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - iCalculatePeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - iCalculatePeekHeight;
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    private float calculateCornerInterpolation(float f2, @Nullable RoundedCorner roundedCorner) {
        if (roundedCorner != null) {
            float radius = roundedCorner.getRadius();
            if (radius > 0.0f && f2 > 0.0f) {
                return radius / f2;
            }
        }
        return 0.0f;
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) ((1.0f - this.halfExpandedRatio) * this.parentHeight);
    }

    private float calculateInterpolationWithCornersRemoved() {
        WeakReference<V> weakReference;
        WindowInsets rootWindowInsets;
        if (this.materialShapeDrawable == null || (weakReference = this.viewRef) == null || weakReference.get() == null || Build.VERSION.SDK_INT < 31) {
            return 0.0f;
        }
        V v2 = this.viewRef.get();
        if (!isAtTopOfScreen() || (rootWindowInsets = v2.getRootWindowInsets()) == null) {
            return 0.0f;
        }
        return Math.max(calculateCornerInterpolation(this.materialShapeDrawable.getTopLeftCornerResolvedSize(), rootWindowInsets.getRoundedCorner(0)), calculateCornerInterpolation(this.materialShapeDrawable.getTopRightCornerResolvedSize(), rootWindowInsets.getRoundedCorner(1)));
    }

    private int calculatePeekHeight() {
        int i2;
        if (this.peekHeightAuto) {
            return Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight) + this.insetBottom;
        }
        return (this.gestureInsetBottomIgnored || this.paddingBottomSystemWindowInsets || (i2 = this.gestureInsetBottom) <= 0) ? this.peekHeight + this.insetBottom : Math.max(this.peekHeight, i2 + this.peekHeightGestureInsetBuffer);
    }

    private float calculateSlideOffsetWithTop(int i2) {
        float f2;
        float expandedOffset;
        int i3 = this.collapsedOffset;
        if (i2 > i3 || i3 == getExpandedOffset()) {
            int i4 = this.collapsedOffset;
            f2 = i4 - i2;
            expandedOffset = this.parentHeight - i4;
        } else {
            int i5 = this.collapsedOffset;
            f2 = i5 - i2;
            expandedOffset = i5 - getExpandedOffset();
        }
        return f2 / expandedOffset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canBeHiddenByDragging() {
        return isHideable() && isHideableWhenDragging();
    }

    private void clearAccessibilityAction(View view, int i2) {
        if (view == null) {
            return;
        }
        ViewCompat.removeAccessibilityAction(view, 524288);
        ViewCompat.removeAccessibilityAction(view, 262144);
        ViewCompat.removeAccessibilityAction(view, 1048576);
        int i3 = this.expandHalfwayActionIds.get(i2, -1);
        if (i3 != -1) {
            ViewCompat.removeAccessibilityAction(view, i3);
            this.expandHalfwayActionIds.delete(i2);
        }
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int i2) {
        return new AccessibilityViewCommand() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.6
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(@NonNull View view, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                BottomSheetBehavior.this.setState(i2);
                return true;
            }
        };
    }

    private void createMaterialShapeDrawableIfNeeded(@NonNull Context context) {
        if (this.shapeAppearanceModelDefault == null) {
            return;
        }
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
        this.materialShapeDrawable = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(context);
        ColorStateList colorStateList = this.backgroundTint;
        if (colorStateList != null) {
            this.materialShapeDrawable.setFillColor(colorStateList);
            return;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
        this.materialShapeDrawable.setTint(typedValue.data);
    }

    private void createShapeValueAnimator() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(calculateInterpolationWithCornersRemoved(), 1.0f);
        this.interpolatorAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable != null) {
                    BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(fFloatValue);
                }
            }
        });
    }

    @NonNull
    public static <V extends View> BottomSheetBehavior<V> from(@NonNull V v2) {
        ViewGroup.LayoutParams layoutParams = v2.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    private int getChildMeasureSpec(int i2, int i3, int i4, int i5) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, i3, i5);
        if (i4 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i4), BasicMeasure.EXACTLY);
        }
        if (size != 0) {
            i4 = Math.min(size, i4);
        }
        return View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
    }

    private int getTopOffsetForState(int i2) {
        if (i2 == 3) {
            return getExpandedOffset();
        }
        if (i2 == 4) {
            return this.collapsedOffset;
        }
        if (i2 == 5) {
            return this.parentHeight;
        }
        if (i2 == 6) {
            return this.halfExpandedOffset;
        }
        throw new IllegalArgumentException(a.c("Invalid state to get top offset: ", i2));
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    private boolean isAtTopOfScreen() {
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return false;
        }
        int[] iArr = new int[2];
        this.viewRef.get().getLocationOnScreen(iArr);
        return iArr[1] == 0;
    }

    private boolean isExpandedAndShouldRemoveCorners() {
        return this.state == 3 && (this.shouldRemoveExpandedCorners || isAtTopOfScreen());
    }

    private boolean isLayouting(V v2) {
        ViewParent parent = v2.getParent();
        return parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(v2);
    }

    private void replaceAccessibilityActionForState(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, int i2) {
        ViewCompat.replaceAccessibilityAction(view, accessibilityActionCompat, null, createAccessibilityViewCommandForState(i2));
    }

    private void reset() {
        this.activePointerId = -1;
        this.initialY = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(@NonNull SavedState savedState) {
        int i2 = this.saveFlags;
        if (i2 == 0) {
            return;
        }
        if (i2 == -1 || (i2 & 1) == 1) {
            this.peekHeight = savedState.peekHeight;
        }
        if (i2 == -1 || (i2 & 2) == 2) {
            this.fitToContents = savedState.fitToContents;
        }
        if (i2 == -1 || (i2 & 4) == 4) {
            this.hideable = savedState.hideable;
        }
        if (i2 == -1 || (i2 & 8) == 8) {
            this.skipCollapsed = savedState.skipCollapsed;
        }
    }

    private void runAfterLayout(V v2, Runnable runnable) {
        if (isLayouting(v2)) {
            v2.post(runnable);
        } else {
            runnable.run();
        }
    }

    private void setWindowInsetsListener(@NonNull View view) {
        final boolean z2 = (Build.VERSION.SDK_INT < 29 || isGestureInsetBottomIgnored() || this.peekHeightAuto) ? false : true;
        if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || this.marginLeftSystemWindowInsets || this.marginRightSystemWindowInsets || this.marginTopSystemWindowInsets || z2) {
            ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.4
                /* JADX WARN: Code duplicated, block: B:22:0x0080  */
                /* JADX WARN: Code duplicated, block: B:28:0x0092  */
                /* JADX WARN: Code duplicated, block: B:34:0x00a4 A[DONT_INVERT] */
                @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                    boolean z3;
                    Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets insets2 = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.mandatorySystemGestures());
                    BottomSheetBehavior.this.insetTop = insets.top;
                    boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(view2);
                    int paddingBottom = view2.getPaddingBottom();
                    int paddingLeft = view2.getPaddingLeft();
                    int paddingRight = view2.getPaddingRight();
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets) {
                        BottomSheetBehavior.this.insetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
                        paddingBottom = relativePadding.bottom + BottomSheetBehavior.this.insetBottom;
                    }
                    if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets) {
                        paddingLeft = (zIsLayoutRtl ? relativePadding.end : relativePadding.start) + insets.left;
                    }
                    if (BottomSheetBehavior.this.paddingRightSystemWindowInsets) {
                        paddingRight = (zIsLayoutRtl ? relativePadding.start : relativePadding.end) + insets.right;
                    }
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                    boolean z4 = true;
                    if (BottomSheetBehavior.this.marginLeftSystemWindowInsets) {
                        int i2 = marginLayoutParams.leftMargin;
                        int i3 = insets.left;
                        if (i2 != i3) {
                            marginLayoutParams.leftMargin = i3;
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                    } else {
                        z3 = false;
                    }
                    if (BottomSheetBehavior.this.marginRightSystemWindowInsets) {
                        int i4 = marginLayoutParams.rightMargin;
                        int i5 = insets.right;
                        if (i4 != i5) {
                            marginLayoutParams.rightMargin = i5;
                        } else {
                            z4 = z3;
                        }
                    } else {
                        z4 = z3;
                    }
                    if (BottomSheetBehavior.this.marginTopSystemWindowInsets) {
                        int i6 = marginLayoutParams.topMargin;
                        int i7 = insets.top;
                        if (i6 != i7) {
                            marginLayoutParams.topMargin = i7;
                        } else if (z4) {
                        }
                        view2.setLayoutParams(marginLayoutParams);
                    } else if (z4) {
                        view2.setLayoutParams(marginLayoutParams);
                    }
                    view2.setPadding(paddingLeft, view2.getPaddingTop(), paddingRight, paddingBottom);
                    if (z2) {
                        BottomSheetBehavior.this.gestureInsetBottom = insets2.bottom;
                    }
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets || z2) {
                        BottomSheetBehavior.this.updatePeekHeight(false);
                    }
                    return windowInsetsCompat;
                }
            });
        }
    }

    private boolean shouldHandleDraggingWithHelper() {
        return this.viewDragHelper != null && (this.draggable || this.state == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSettling(View view, int i2, boolean z2) {
        int topOffsetForState = getTopOffsetForState(i2);
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper == null || (!z2 ? viewDragHelper.smoothSlideViewTo(view, view.getLeft(), topOffsetForState) : viewDragHelper.settleCapturedViewAt(view.getLeft(), topOffsetForState))) {
            setStateInternal(i2);
            return;
        }
        setStateInternal(2);
        updateDrawableForTargetState(i2, true);
        this.stateSettlingTracker.continueSettlingToState(i2);
    }

    private void updateAccessibilityActions() {
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference != null) {
            updateAccessibilityActions(weakReference.get(), 0);
        }
        WeakReference<View> weakReference2 = this.accessibilityDelegateViewRef;
        if (weakReference2 != null) {
            updateAccessibilityActions(weakReference2.get(), 1);
        }
    }

    private void updateDrawableForTargetState(int i2, boolean z2) {
        boolean zIsExpandedAndShouldRemoveCorners;
        ValueAnimator valueAnimator;
        if (i2 == 2 || this.expandedCornersRemoved == (zIsExpandedAndShouldRemoveCorners = isExpandedAndShouldRemoveCorners()) || this.materialShapeDrawable == null) {
            return;
        }
        this.expandedCornersRemoved = zIsExpandedAndShouldRemoveCorners;
        if (!z2 || (valueAnimator = this.interpolatorAnimator) == null) {
            ValueAnimator valueAnimator2 = this.interpolatorAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.interpolatorAnimator.cancel();
            }
            this.materialShapeDrawable.setInterpolation(this.expandedCornersRemoved ? calculateInterpolationWithCornersRemoved() : 1.0f);
            return;
        }
        if (valueAnimator.isRunning()) {
            this.interpolatorAnimator.reverse();
        } else {
            this.interpolatorAnimator.setFloatValues(this.materialShapeDrawable.getInterpolation(), zIsExpandedAndShouldRemoveCorners ? calculateInterpolationWithCornersRemoved() : 1.0f);
            this.interpolatorAnimator.start();
        }
    }

    private void updateImportantForAccessibility(boolean z2) {
        Map<View, Integer> map;
        int iIntValue;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = weakReference.get().getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z2) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                } else {
                    this.importantForAccessibilityMap = new HashMap(childCount);
                }
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt != this.viewRef.get()) {
                    if (z2) {
                        this.importantForAccessibilityMap.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                        if (this.updateImportantForAccessibilityOnSiblings) {
                            iIntValue = 4;
                            ViewCompat.setImportantForAccessibility(childAt, iIntValue);
                        }
                    } else if (this.updateImportantForAccessibilityOnSiblings && (map = this.importantForAccessibilityMap) != null && map.containsKey(childAt)) {
                        iIntValue = this.importantForAccessibilityMap.get(childAt).intValue();
                        ViewCompat.setImportantForAccessibility(childAt, iIntValue);
                    }
                }
            }
            if (!z2) {
                this.importantForAccessibilityMap = null;
            } else if (this.updateImportantForAccessibilityOnSiblings) {
                this.viewRef.get().sendAccessibilityEvent(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePeekHeight(boolean z2) {
        V v2;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state != 4 || (v2 = this.viewRef.get()) == null) {
                return;
            }
            if (z2) {
                setState(4);
            } else {
                v2.requestLayout();
            }
        }
    }

    public void addBottomSheetCallback(@NonNull BottomSheetCallback bottomSheetCallback) {
        if (this.callbacks.contains(bottomSheetCallback)) {
            return;
        }
        this.callbacks.add(bottomSheetCallback);
    }

    public float calculateSlideOffset() {
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return -1.0f;
        }
        return calculateSlideOffsetWithTop(this.viewRef.get().getTop());
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public void cancelBackProgress() {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        materialBottomContainerBackHelper.cancelBackProgress();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public void dispatchOnSlide(int i2) {
        V v2 = this.viewRef.get();
        if (v2 == null || this.callbacks.isEmpty()) {
            return;
        }
        float fCalculateSlideOffsetWithTop = calculateSlideOffsetWithTop(i2);
        for (int i3 = 0; i3 < this.callbacks.size(); i3++) {
            this.callbacks.get(i3).onSlide(v2, fCalculateSlideOffsetWithTop);
        }
    }

    @Nullable
    @VisibleForTesting
    public View findScrollingChild(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View viewFindScrollingChild = findScrollingChild(viewGroup.getChildAt(i2));
                if (viewFindScrollingChild != null) {
                    return viewFindScrollingChild;
                }
            }
        }
        return null;
    }

    @Nullable
    @VisibleForTesting
    public MaterialBottomContainerBackHelper getBackHelper() {
        return this.bottomContainerBackHelper;
    }

    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN)
    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public float getHideFriction() {
        return this.hideFriction;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getLastStableState() {
        return this.lastStableState;
    }

    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return this.materialShapeDrawable;
    }

    @Px
    public int getMaxHeight() {
        return this.maxHeight;
    }

    @Px
    public int getMaxWidth() {
        return this.maxWidth;
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    @VisibleForTesting
    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    public int getSignificantVelocityThreshold() {
        return this.significantVelocityThreshold;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public int getState() {
        return this.state;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public void handleBackInvoked() {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        BackEventCompat backEventCompatOnHandleBackInvoked = materialBottomContainerBackHelper.onHandleBackInvoked();
        if (backEventCompatOnHandleBackInvoked == null || Build.VERSION.SDK_INT < 34) {
            setState(this.hideable ? 5 : 4);
        } else if (this.hideable) {
            this.bottomContainerBackHelper.finishBackProgressNotPersistent(backEventCompatOnHandleBackInvoked, new AnimatorListenerAdapter() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BottomSheetBehavior.this.setStateInternal(5);
                    WeakReference<V> weakReference = BottomSheetBehavior.this.viewRef;
                    if (weakReference == null || weakReference.get() == null) {
                        return;
                    }
                    BottomSheetBehavior.this.viewRef.get().requestLayout();
                }
            });
        } else {
            this.bottomContainerBackHelper.finishBackProgressPersistent(backEventCompatOnHandleBackInvoked, null);
            setState(4);
        }
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public boolean isHideable() {
        return this.hideable;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isHideableWhenDragging() {
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isNestedScrollingCheckEnabled() {
        return true;
    }

    public boolean isShouldRemoveExpandedCorners() {
        return this.shouldRemoveExpandedCorners;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull MotionEvent motionEvent) {
        int i2;
        ViewDragHelper viewDragHelper;
        if (!v2.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x2 = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            if (this.state != 2) {
                WeakReference<View> weakReference = this.nestedScrollingChildRef;
                View view = weakReference != null ? weakReference.get() : null;
                if (view != null && coordinatorLayout.isPointInChildBounds(view, x2, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(v2, x2, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && (viewDragHelper = this.viewDragHelper) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference<View> weakReference2 = this.nestedScrollingChildRef;
        View view2 = weakReference2 != null ? weakReference2.get() : null;
        return (actionMasked != 2 || view2 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view2, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || (i2 = this.initialY) == -1 || Math.abs(((float) i2) - motionEvent.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x009c A[PHI: r5 r7
      0x009c: PHI (r5v23 int) = (r5v22 int), (r5v24 int) binds: [B:34:0x00a6, B:29:0x0099] A[DONT_GENERATE, DONT_INLINE]
      0x009c: PHI (r7v9 int) = (r7v7 int), (r7v2 int) binds: [B:34:0x00a6, B:29:0x0099] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:58:0x0103 A[LOOP:0: B:56:0x00fb->B:58:0x0103, LOOP_END] */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, int i2) {
        int expandedOffset;
        int i3;
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v2)) {
            v2.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            setWindowInsetsListener(v2);
            ViewCompat.setWindowInsetsAnimationCallback(v2, new InsetsAnimationCallback(v2));
            this.viewRef = new WeakReference<>(v2);
            this.bottomContainerBackHelper = new MaterialBottomContainerBackHelper(v2);
            MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
            if (materialShapeDrawable != null) {
                ViewCompat.setBackground(v2, materialShapeDrawable);
                MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
                float elevation = this.elevation;
                if (elevation == -1.0f) {
                    elevation = ViewCompat.getElevation(v2);
                }
                materialShapeDrawable2.setElevation(elevation);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.setBackgroundTintList(v2, colorStateList);
                }
            }
            updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(v2) == 0) {
                ViewCompat.setImportantForAccessibility(v2, 1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int top = v2.getTop();
        coordinatorLayout.onLayoutChild(v2, i2);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        int height = v2.getHeight();
        this.childHeight = height;
        int iMin = this.parentHeight;
        int i4 = iMin - height;
        int i5 = this.insetTop;
        if (i4 < i5) {
            if (this.paddingTopSystemWindowInsets) {
                i3 = this.maxHeight;
                if (i3 != -1) {
                    iMin = Math.min(iMin, i3);
                }
            } else {
                iMin -= i5;
                i3 = this.maxHeight;
                if (i3 != -1) {
                    iMin = Math.min(iMin, i3);
                }
            }
            this.childHeight = iMin;
        }
        this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        int i6 = this.state;
        if (i6 == 3) {
            expandedOffset = getExpandedOffset();
        } else if (i6 == 6) {
            expandedOffset = this.halfExpandedOffset;
        } else {
            if (!this.hideable || i6 != 5) {
                if (i6 == 4) {
                    expandedOffset = this.collapsedOffset;
                } else if (i6 == 1 || i6 == 2) {
                    ViewCompat.offsetTopAndBottom(v2, top - v2.getTop());
                }
                updateDrawableForTargetState(this.state, false);
                this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(v2));
                for (int i7 = 0; i7 < this.callbacks.size(); i7++) {
                    this.callbacks.get(i7).onLayout(v2);
                }
                return true;
            }
            expandedOffset = this.parentHeight;
        }
        ViewCompat.offsetTopAndBottom(v2, expandedOffset);
        updateDrawableForTargetState(this.state, false);
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(v2));
        while (i7 < this.callbacks.size()) {
            this.callbacks.get(i7).onLayout(v2);
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) v2.getLayoutParams();
        v2.measure(getChildMeasureSpec(i2, coordinatorLayout.getPaddingRight() + coordinatorLayout.getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, this.maxWidth, marginLayoutParams.width), getChildMeasureSpec(i4, coordinatorLayout.getPaddingBottom() + coordinatorLayout.getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i5, this.maxHeight, marginLayoutParams.height));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, float f2, float f3) {
        WeakReference<View> weakReference;
        if (isNestedScrollingCheckEnabled() && (weakReference = this.nestedScrollingChildRef) != null && view == weakReference.get()) {
            return this.state != 3 || super.onNestedPreFling(coordinatorLayout, v2, view, f2, f3);
        }
        return false;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2, int i3, @NonNull int[] iArr, int i4) {
        int i5;
        if (i4 == 1) {
            return;
        }
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        View view2 = weakReference != null ? weakReference.get() : null;
        if (!isNestedScrollingCheckEnabled() || view == view2) {
            int top = v2.getTop();
            int i6 = top - i3;
            if (i3 > 0) {
                if (i6 < getExpandedOffset()) {
                    int expandedOffset = top - getExpandedOffset();
                    iArr[1] = expandedOffset;
                    ViewCompat.offsetTopAndBottom(v2, -expandedOffset);
                    i5 = 3;
                    setStateInternal(i5);
                } else {
                    if (!this.draggable) {
                        return;
                    }
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    setStateInternal(1);
                }
            } else if (i3 < 0 && !view.canScrollVertically(-1)) {
                if (i6 > this.collapsedOffset && !canBeHiddenByDragging()) {
                    int i7 = top - this.collapsedOffset;
                    iArr[1] = i7;
                    ViewCompat.offsetTopAndBottom(v2, -i7);
                    i5 = 4;
                    setStateInternal(i5);
                } else {
                    if (!this.draggable) {
                        return;
                    }
                    iArr[1] = i3;
                    ViewCompat.offsetTopAndBottom(v2, -i3);
                    setStateInternal(1);
                }
            }
            dispatchOnSlide(v2.getTop());
            this.lastNestedScrollDy = i3;
            this.nestedScrolled = true;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2, int i3, int i4, int i5, int i6, @NonNull int[] iArr) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v2, savedState.getSuperState());
        restoreOptionalState(savedState);
        int i2 = savedState.state;
        if (i2 == 1 || i2 == 2) {
            i2 = 4;
        }
        this.state = i2;
        this.lastStableState = i2;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @NonNull
    public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v2), (BottomSheetBehavior<?>) this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, @NonNull View view2, int i2, int i3) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i2 & 2) != 0;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0092  */
    /* JADX WARN: Code duplicated, block: B:51:0x00a9  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2) {
        WeakReference<View> weakReference;
        int i3 = 3;
        if (v2.getTop() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        if (!isNestedScrollingCheckEnabled() || ((weakReference = this.nestedScrollingChildRef) != null && view == weakReference.get() && this.nestedScrolled)) {
            if (this.lastNestedScrollDy > 0) {
                if (!this.fitToContents && v2.getTop() > this.halfExpandedOffset) {
                    i3 = 6;
                }
            } else if (this.hideable && shouldHide(v2, getYVelocity())) {
                i3 = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int top = v2.getTop();
                if (!this.fitToContents) {
                    int i4 = this.halfExpandedOffset;
                    if (top < i4) {
                        if (top >= Math.abs(top - this.collapsedOffset)) {
                            if (shouldSkipHalfExpandedStateWhenDragging()) {
                                i3 = 4;
                            } else {
                                i3 = 6;
                            }
                        }
                    } else if (Math.abs(top - i4) < Math.abs(top - this.collapsedOffset)) {
                        i3 = 6;
                    } else {
                        i3 = 4;
                    }
                } else if (Math.abs(top - this.fitToContentsOffset) >= Math.abs(top - this.collapsedOffset)) {
                    i3 = 4;
                }
            } else {
                if (!this.fitToContents) {
                    int top2 = v2.getTop();
                    if (Math.abs(top2 - this.halfExpandedOffset) < Math.abs(top2 - this.collapsedOffset)) {
                        i3 = 6;
                    }
                }
                i3 = 4;
            }
            startSettling(v2, i3, false);
            this.nestedScrolled = false;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull MotionEvent motionEvent) {
        if (!v2.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper()) {
            this.viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (shouldHandleDraggingWithHelper() && actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getY()) > this.viewDragHelper.getTouchSlop()) {
            this.viewDragHelper.captureChildView(v2, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public void removeBottomSheetCallback(@NonNull BottomSheetCallback bottomSheetCallback) {
        this.callbacks.remove(bottomSheetCallback);
    }

    public void setAccessibilityDelegateView(@Nullable View view) {
        WeakReference<View> weakReference;
        if (view != null || (weakReference = this.accessibilityDelegateViewRef) == null) {
            this.accessibilityDelegateViewRef = new WeakReference<>(view);
            updateAccessibilityActions(view, 1);
        } else {
            clearAccessibilityAction(weakReference.get(), 1);
            this.accessibilityDelegateViewRef = null;
        }
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.callbacks.clear();
        if (bottomSheetCallback != null) {
            this.callbacks.add(bottomSheetCallback);
        }
    }

    public void setDraggable(boolean z2) {
        this.draggable = z2;
    }

    public void setExpandedOffset(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("offset must be greater than or equal to 0");
        }
        this.expandedOffset = i2;
        updateDrawableForTargetState(this.state, true);
    }

    public void setFitToContents(boolean z2) {
        if (this.fitToContents == z2) {
            return;
        }
        this.fitToContents = z2;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
        }
        setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
        updateDrawableForTargetState(this.state, true);
        updateAccessibilityActions();
    }

    public void setGestureInsetBottomIgnored(boolean z2) {
        this.gestureInsetBottomIgnored = z2;
    }

    public void setHalfExpandedRatio(@FloatRange(from = 0.0d, fromInclusive = false, to = Contrast.RATIO_MIN, toInclusive = false) float f2) {
        if (f2 <= 0.0f || f2 >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = f2;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    public void setHideFriction(float f2) {
        this.hideFriction = f2;
    }

    public void setHideable(boolean z2) {
        if (this.hideable != z2) {
            this.hideable = z2;
            if (!z2 && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setHideableInternal(boolean z2) {
        this.hideable = z2;
    }

    public void setMaxHeight(@Px int i2) {
        this.maxHeight = i2;
    }

    public void setMaxWidth(@Px int i2) {
        this.maxWidth = i2;
    }

    public void setPeekHeight(int i2) {
        setPeekHeight(i2, false);
    }

    public void setSaveFlags(int i2) {
        this.saveFlags = i2;
    }

    public void setShouldRemoveExpandedCorners(boolean z2) {
        if (this.shouldRemoveExpandedCorners != z2) {
            this.shouldRemoveExpandedCorners = z2;
            updateDrawableForTargetState(getState(), true);
        }
    }

    public void setSignificantVelocityThreshold(int i2) {
        this.significantVelocityThreshold = i2;
    }

    public void setSkipCollapsed(boolean z2) {
        this.skipCollapsed = z2;
    }

    public void setState(int i2) {
        if (i2 == 1 || i2 == 2) {
            throw new IllegalArgumentException(a.g(new StringBuilder("STATE_"), i2 == 1 ? "DRAGGING" : "SETTLING", " should not be set externally."));
        }
        if (this.hideable || i2 != 5) {
            final int i3 = (i2 == 6 && this.fitToContents && getTopOffsetForState(i2) <= this.fitToContentsOffset) ? 3 : i2;
            WeakReference<V> weakReference = this.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                setStateInternal(i2);
            } else {
                final V v2 = this.viewRef.get();
                runAfterLayout(v2, new Runnable() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BottomSheetBehavior.this.startSettling(v2, i3, false);
                    }
                });
            }
        }
    }

    public void setStateInternal(int i2) {
        V v2;
        if (this.state == i2) {
            return;
        }
        this.state = i2;
        if (i2 == 4 || i2 == 3 || i2 == 6 || (this.hideable && i2 == 5)) {
            this.lastStableState = i2;
        }
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || (v2 = weakReference.get()) == null) {
            return;
        }
        if (i2 == 3) {
            updateImportantForAccessibility(true);
        } else if (i2 == 6 || i2 == 5 || i2 == 4) {
            updateImportantForAccessibility(false);
        }
        updateDrawableForTargetState(i2, true);
        for (int i3 = 0; i3 < this.callbacks.size(); i3++) {
            this.callbacks.get(i3).onStateChanged(v2, i2);
        }
        updateAccessibilityActions();
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean z2) {
        this.updateImportantForAccessibilityOnSiblings = z2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean shouldExpandOnUpwardDrag(long j2, @FloatRange(from = 0.0d, to = 100.0d) float f2) {
        return false;
    }

    public boolean shouldHide(@NonNull View view, float f2) {
        if (this.skipCollapsed) {
            return true;
        }
        if (!isHideableWhenDragging() || view.getTop() < this.collapsedOffset) {
            return false;
        }
        return Math.abs(((f2 * this.hideFriction) + ((float) view.getTop())) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean shouldSkipHalfExpandedStateWhenDragging() {
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean shouldSkipSmoothAnimation() {
        return true;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public void startBackProgress(@NonNull BackEventCompat backEventCompat) {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        materialBottomContainerBackHelper.startBackProgress(backEventCompat);
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public void updateBackProgress(@NonNull BackEventCompat backEventCompat) {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper = this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        materialBottomContainerBackHelper.updateBackProgress(backEventCompat);
    }

    public BottomSheetBehavior(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        int i2;
        super(context, attributeSet);
        this.saveFlags = 0;
        this.fitToContents = true;
        this.updateImportantForAccessibilityOnSiblings = false;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.stateSettlingTracker = new StateSettlingTracker();
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.lastStableState = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList<>();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.bottomsheet.BottomSheetBehavior.5
            private long viewCapturedMillis;

            private boolean releasedLow(@NonNull View view) {
                int top = view.getTop();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                return top > (bottomSheetBehavior.getExpandedOffset() + bottomSheetBehavior.parentHeight) / 2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i3, int i4) {
                return view.getLeft();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(@NonNull View view, int i3, int i4) {
                return MathUtils.clamp(i3, BottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange(view));
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(@NonNull View view) {
                return BottomSheetBehavior.this.canBeHiddenByDragging() ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i3) {
                if (i3 == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i3, int i4, int i5, int i6) {
                BottomSheetBehavior.this.dispatchOnSlide(i4);
            }

            /* JADX WARN: Code duplicated, block: B:39:0x00ae  */
            /* JADX WARN: Code duplicated, block: B:6:0x0010  */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f2, float f3) {
                int i3 = 6;
                if (f3 >= 0.0f) {
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                    if (bottomSheetBehavior.hideable && bottomSheetBehavior.shouldHide(view, f3)) {
                        if ((Math.abs(f2) < Math.abs(f3) && f3 > BottomSheetBehavior.this.significantVelocityThreshold) || releasedLow(view)) {
                            i3 = 5;
                        } else if (BottomSheetBehavior.this.fitToContents || Math.abs(view.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(view.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                            i3 = 3;
                        }
                    } else if (f3 == 0.0f || Math.abs(f2) > Math.abs(f3)) {
                        int top = view.getTop();
                        if (!BottomSheetBehavior.this.fitToContents) {
                            BottomSheetBehavior bottomSheetBehavior2 = BottomSheetBehavior.this;
                            int i4 = bottomSheetBehavior2.halfExpandedOffset;
                            if (top < i4) {
                                if (top < Math.abs(top - bottomSheetBehavior2.collapsedOffset)) {
                                    i3 = 3;
                                } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                    i3 = 4;
                                }
                            } else if (Math.abs(top - i4) >= Math.abs(top - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                                i3 = 4;
                            }
                        } else if (Math.abs(top - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                            i3 = 3;
                        } else {
                            i3 = 4;
                        }
                    } else if (BottomSheetBehavior.this.fitToContents) {
                        i3 = 4;
                    } else {
                        int top2 = view.getTop();
                        if (Math.abs(top2 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(top2 - BottomSheetBehavior.this.collapsedOffset) || BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            i3 = 4;
                        }
                    }
                } else if (BottomSheetBehavior.this.fitToContents) {
                    i3 = 3;
                } else {
                    int top3 = view.getTop();
                    long jCurrentTimeMillis = System.currentTimeMillis() - this.viewCapturedMillis;
                    if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        BottomSheetBehavior bottomSheetBehavior3 = BottomSheetBehavior.this;
                        if (!bottomSheetBehavior3.shouldExpandOnUpwardDrag(jCurrentTimeMillis, (top3 * 100.0f) / bottomSheetBehavior3.parentHeight)) {
                            i3 = 4;
                        }
                    } else if (top3 <= BottomSheetBehavior.this.halfExpandedOffset) {
                    }
                    i3 = 3;
                }
                BottomSheetBehavior bottomSheetBehavior4 = BottomSheetBehavior.this;
                bottomSheetBehavior4.startSettling(view, i3, bottomSheetBehavior4.shouldSkipSmoothAnimation());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i3) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                int i4 = bottomSheetBehavior.state;
                if (i4 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                    return false;
                }
                if (i4 == 3 && bottomSheetBehavior.activePointerId == i3) {
                    WeakReference<View> weakReference = bottomSheetBehavior.nestedScrollingChildRef;
                    View view2 = weakReference != null ? weakReference.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                this.viewCapturedMillis = System.currentTimeMillis();
                WeakReference<V> weakReference2 = BottomSheetBehavior.this.viewRef;
                return weakReference2 != null && weakReference2.get() == view;
            }
        };
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint)) {
            this.backgroundTint = MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, R.styleable.BottomSheetBehavior_Layout_backgroundTint);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance)) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attributeSet, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
        }
        createMaterialShapeDrawableIfNeeded(context);
        createShapeValueAnimator();
        this.elevation = typedArrayObtainStyledAttributes.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxWidth)) {
            setMaxWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxWidth, -1));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxHeight)) {
            setMaxHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxHeight, -1));
        }
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        setPeekHeight((typedValuePeekValue == null || (i2 = typedValuePeekValue.data) != -1) ? typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1) : i2);
        setHideable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setSaveFlags(typedArrayObtainStyledAttributes.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(typedArrayObtainStyledAttributes.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
        setExpandedOffset((typedValuePeekValue2 == null || typedValuePeekValue2.type != 16) ? typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0) : typedValuePeekValue2.data);
        setSignificantVelocityThreshold(typedArrayObtainStyledAttributes.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_significantVelocityThreshold, 500));
        this.paddingBottomSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.paddingTopSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        this.marginLeftSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginLeftSystemWindowInsets, false);
        this.marginRightSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginRightSystemWindowInsets, false);
        this.marginTopSystemWindowInsets = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginTopSystemWindowInsets, false);
        this.shouldRemoveExpandedCorners = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_shouldRemoveExpandedCorners, true);
        typedArrayObtainStyledAttributes.recycle();
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    private void updateAccessibilityActions(View view, int i2) {
        int i3;
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat;
        if (view == null) {
            return;
        }
        clearAccessibilityAction(view, i2);
        if (!this.fitToContents && this.state != 6) {
            this.expandHalfwayActionIds.put(i2, addAccessibilityActionForState(view, R.string.bottomsheet_action_expand_halfway, 6));
        }
        if (this.hideable && isHideableWhenDragging() && this.state != 5) {
            replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
        }
        int i4 = this.state;
        if (i4 == 3) {
            i3 = this.fitToContents ? 4 : 6;
            accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE;
        } else {
            if (i4 != 4) {
                if (i4 != 6) {
                    return;
                }
                replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                return;
            }
            i3 = this.fitToContents ? 3 : 6;
            accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND;
        }
        replaceAccessibilityActionForState(view, accessibilityActionCompat, i3);
    }

    public final void setPeekHeight(int i2, boolean z2) {
        if (i2 == -1) {
            if (this.peekHeightAuto) {
                return;
            } else {
                this.peekHeightAuto = true;
            }
        } else {
            if (!this.peekHeightAuto && this.peekHeight == i2) {
                return;
            }
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, i2);
        }
        updatePeekHeight(z2);
    }
}
