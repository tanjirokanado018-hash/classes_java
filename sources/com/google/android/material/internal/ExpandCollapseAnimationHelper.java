package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ExpandCollapseAnimationHelper {

    @Nullable
    private ValueAnimator.AnimatorUpdateListener additionalUpdateListener;
    private final View collapsedView;
    private int collapsedViewOffsetY;
    private long duration;
    private final View expandedView;
    private int expandedViewOffsetY;
    private final List<AnimatorListenerAdapter> listeners = new ArrayList();
    private final List<View> endAnchoredViews = new ArrayList();

    public ExpandCollapseAnimationHelper(@NonNull View view, @NonNull View view2) {
        this.collapsedView = view;
        this.expandedView = view2;
    }

    private void addListeners(Animator animator, List<AnimatorListenerAdapter> list) {
        Iterator<AnimatorListenerAdapter> it = list.iterator();
        while (it.hasNext()) {
            animator.addListener(it.next());
        }
    }

    private AnimatorSet getAnimatorSet(boolean z2) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(getExpandCollapseAnimator(z2), getExpandedViewChildrenAlphaAnimator(z2), getEndAnchoredViewsTranslateAnimator(z2));
        return animatorSet;
    }

    private Animator getEndAnchoredViewsTranslateAnimator(boolean z2) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat((this.collapsedView.getRight() - this.expandedView.getRight()) + (this.expandedView.getLeft() - this.collapsedView.getLeft()), 0.0f);
        valueAnimatorOfFloat.addUpdateListener(MultiViewUpdateListener.translationXListener(this.endAnchoredViews));
        valueAnimatorOfFloat.setDuration(this.duration);
        valueAnimatorOfFloat.setInterpolator(ReversableAnimatedValueInterpolator.of(z2, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return valueAnimatorOfFloat;
    }

    private Animator getExpandCollapseAnimator(boolean z2) {
        Rect rectCalculateRectFromBounds = ViewUtils.calculateRectFromBounds(this.collapsedView, this.collapsedViewOffsetY);
        Rect rectCalculateRectFromBounds2 = ViewUtils.calculateRectFromBounds(this.expandedView, this.expandedViewOffsetY);
        Rect rect = new Rect(rectCalculateRectFromBounds);
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new RectEvaluator(rect), rectCalculateRectFromBounds, rectCalculateRectFromBounds2);
        valueAnimatorOfObject.addUpdateListener(new com.google.android.material.appbar.b(1, this, rect));
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.additionalUpdateListener;
        if (animatorUpdateListener != null) {
            valueAnimatorOfObject.addUpdateListener(animatorUpdateListener);
        }
        valueAnimatorOfObject.setDuration(this.duration);
        valueAnimatorOfObject.setInterpolator(ReversableAnimatedValueInterpolator.of(z2, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return valueAnimatorOfObject;
    }

    private Animator getExpandedViewChildrenAlphaAnimator(boolean z2) {
        List<View> children = ViewUtils.getChildren(this.expandedView);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(MultiViewUpdateListener.alphaListener(children));
        valueAnimatorOfFloat.setDuration(this.duration);
        valueAnimatorOfFloat.setInterpolator(ReversableAnimatedValueInterpolator.of(z2, AnimationUtils.LINEAR_INTERPOLATOR));
        return valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getExpandCollapseAnimator$0(Rect rect, ValueAnimator valueAnimator) {
        ViewUtils.setBoundsFromRect(this.expandedView, rect);
    }

    @NonNull
    public ExpandCollapseAnimationHelper addEndAnchoredViews(@NonNull Collection<View> collection) {
        this.endAnchoredViews.addAll(collection);
        return this;
    }

    @NonNull
    public ExpandCollapseAnimationHelper addListener(@NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        this.listeners.add(animatorListenerAdapter);
        return this;
    }

    @NonNull
    public Animator getCollapseAnimator() {
        AnimatorSet animatorSet = getAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.internal.ExpandCollapseAnimationHelper.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ExpandCollapseAnimationHelper.this.expandedView.setVisibility(8);
            }
        });
        addListeners(animatorSet, this.listeners);
        return animatorSet;
    }

    @NonNull
    public Animator getExpandAnimator() {
        AnimatorSet animatorSet = getAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.internal.ExpandCollapseAnimationHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                ExpandCollapseAnimationHelper.this.expandedView.setVisibility(0);
            }
        });
        addListeners(animatorSet, this.listeners);
        return animatorSet;
    }

    @NonNull
    public ExpandCollapseAnimationHelper setAdditionalUpdateListener(@Nullable ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.additionalUpdateListener = animatorUpdateListener;
        return this;
    }

    @NonNull
    public ExpandCollapseAnimationHelper setCollapsedViewOffsetY(int i2) {
        this.collapsedViewOffsetY = i2;
        return this;
    }

    @NonNull
    public ExpandCollapseAnimationHelper setDuration(long j2) {
        this.duration = j2;
        return this;
    }

    @NonNull
    public ExpandCollapseAnimationHelper setExpandedViewOffsetY(int i2) {
        this.expandedViewOffsetY = i2;
        return this;
    }

    @NonNull
    public ExpandCollapseAnimationHelper addEndAnchoredViews(@NonNull View... viewArr) {
        Collections.addAll(this.endAnchoredViews, viewArr);
        return this;
    }
}
