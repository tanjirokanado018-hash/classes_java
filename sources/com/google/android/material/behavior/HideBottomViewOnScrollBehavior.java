package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class HideBottomViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int DEFAULT_ENTER_ANIMATION_DURATION_MS = 225;
    private static final int DEFAULT_EXIT_ANIMATION_DURATION_MS = 175;
    public static final int STATE_SCROLLED_DOWN = 1;
    public static final int STATE_SCROLLED_UP = 2;
    private int additionalHiddenOffsetY;

    @Nullable
    private ViewPropertyAnimator currentAnimator;

    @ScrollState
    private int currentState;
    private int enterAnimDuration;
    private TimeInterpolator enterAnimInterpolator;
    private int exitAnimDuration;
    private TimeInterpolator exitAnimInterpolator;
    private int height;

    @NonNull
    private final LinkedHashSet<OnScrollStateChangedListener> onScrollStateChangedListeners;
    private static final int ENTER_ANIM_DURATION_ATTR = R.attr.motionDurationLong2;
    private static final int EXIT_ANIM_DURATION_ATTR = R.attr.motionDurationMedium4;
    private static final int ENTER_EXIT_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;

    public interface OnScrollStateChangedListener {
        void onStateChanged(@NonNull View view, @ScrollState int i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollState {
    }

    public HideBottomViewOnScrollBehavior() {
        this.onScrollStateChangedListeners = new LinkedHashSet<>();
        this.height = 0;
        this.currentState = 2;
        this.additionalHiddenOffsetY = 0;
    }

    private void animateChildTo(@NonNull V v2, int i2, long j2, TimeInterpolator timeInterpolator) {
        this.currentAnimator = v2.animate().translationY(i2).setInterpolator(timeInterpolator).setDuration(j2).setListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.behavior.HideBottomViewOnScrollBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                HideBottomViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }

    private void updateCurrentState(@NonNull V v2, @ScrollState int i2) {
        this.currentState = i2;
        Iterator<OnScrollStateChangedListener> it = this.onScrollStateChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onStateChanged(v2, this.currentState);
        }
    }

    public void addOnScrollStateChangedListener(@NonNull OnScrollStateChangedListener onScrollStateChangedListener) {
        this.onScrollStateChangedListeners.add(onScrollStateChangedListener);
    }

    public void clearOnScrollStateChangedListeners() {
        this.onScrollStateChangedListeners.clear();
    }

    public boolean isScrolledDown() {
        return this.currentState == 1;
    }

    public boolean isScrolledUp() {
        return this.currentState == 2;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, int i2) {
        this.height = v2.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) v2.getLayoutParams()).bottomMargin;
        this.enterAnimDuration = MotionUtils.resolveThemeDuration(v2.getContext(), ENTER_ANIM_DURATION_ATTR, DEFAULT_ENTER_ANIMATION_DURATION_MS);
        this.exitAnimDuration = MotionUtils.resolveThemeDuration(v2.getContext(), EXIT_ANIM_DURATION_ATTR, DEFAULT_EXIT_ANIMATION_DURATION_MS);
        Context context = v2.getContext();
        int i3 = ENTER_EXIT_ANIM_EASING_ATTR;
        this.enterAnimInterpolator = MotionUtils.resolveThemeInterpolator(context, i3, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator = MotionUtils.resolveThemeInterpolator(v2.getContext(), i3, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        return super.onLayoutChild(coordinatorLayout, v2, i2);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, int i2, int i3, int i4, int i5, int i6, @NonNull int[] iArr) {
        if (i3 > 0) {
            slideDown(v2);
        } else if (i3 < 0) {
            slideUp(v2);
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull View view, @NonNull View view2, int i2, int i3) {
        return i2 == 2;
    }

    public void removeOnScrollStateChangedListener(@NonNull OnScrollStateChangedListener onScrollStateChangedListener) {
        this.onScrollStateChangedListeners.remove(onScrollStateChangedListener);
    }

    public void setAdditionalHiddenOffsetY(@NonNull V v2, @Dimension int i2) {
        this.additionalHiddenOffsetY = i2;
        if (this.currentState == 1) {
            v2.setTranslationY(this.height + i2);
        }
    }

    public void slideDown(@NonNull V v2) {
        slideDown(v2, true);
    }

    public void slideUp(@NonNull V v2) {
        slideUp(v2, true);
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.onScrollStateChangedListeners = new LinkedHashSet<>();
        this.height = 0;
        this.currentState = 2;
        this.additionalHiddenOffsetY = 0;
    }

    public void slideDown(@NonNull V v2, boolean z2) {
        if (isScrolledDown()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            v2.clearAnimation();
        }
        updateCurrentState(v2, 1);
        int i2 = this.height + this.additionalHiddenOffsetY;
        if (z2) {
            animateChildTo(v2, i2, this.exitAnimDuration, this.exitAnimInterpolator);
        } else {
            v2.setTranslationY(i2);
        }
    }

    public void slideUp(@NonNull V v2, boolean z2) {
        if (isScrolledUp()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            v2.clearAnimation();
        }
        updateCurrentState(v2, 2);
        if (z2) {
            animateChildTo(v2, 0, this.enterAnimDuration, this.enterAnimInterpolator);
        } else {
            v2.setTranslationY(0);
        }
    }
}
