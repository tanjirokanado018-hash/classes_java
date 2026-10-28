package com.google.android.material.motion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.BackEventCompat;
import androidx.annotation.GravityInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class MaterialSideContainerBackHelper extends MaterialBackAnimationHelper<View> {
    private final float maxScaleXDistanceGrow;
    private final float maxScaleXDistanceShrink;
    private final float maxScaleYDistance;

    public MaterialSideContainerBackHelper(@NonNull View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistanceShrink = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_x_distance_shrink);
        this.maxScaleXDistanceGrow = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_x_distance_grow);
        this.maxScaleYDistance = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_y_distance);
    }

    private boolean checkAbsoluteGravity(@GravityInt int i2, @GravityInt int i3) {
        return (GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this.view)) & i3) == i3;
    }

    private int getEdgeMargin(boolean z2) {
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return 0;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return z2 ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
    }

    public void cancelBackProgress() {
        if (super.onCancelBackProgress() == null) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.view, (Property<V, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(this.view, (Property<V, Float>) View.SCALE_Y, 1.0f));
        V v2 = this.view;
        if (v2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v2;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                animatorSet.playTogether(ObjectAnimator.ofFloat(viewGroup.getChildAt(i2), (Property<View, Float>) View.SCALE_Y, 1.0f));
            }
        }
        animatorSet.setDuration(this.cancelDuration);
        animatorSet.start();
    }

    public void finishBackProgress(@NonNull BackEventCompat backEventCompat, @GravityInt final int i2, @Nullable Animator.AnimatorListener animatorListener, @Nullable ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        final boolean z2 = backEventCompat.getSwipeEdge() == 0;
        boolean zCheckAbsoluteGravity = checkAbsoluteGravity(i2, 3);
        float scaleX = (this.view.getScaleX() * this.view.getWidth()) + getEdgeMargin(zCheckAbsoluteGravity);
        V v2 = this.view;
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[1];
        if (zCheckAbsoluteGravity) {
            scaleX = -scaleX;
        }
        fArr[0] = scaleX;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(v2, (Property<V, Float>) property, fArr);
        if (animatorUpdateListener != null) {
            objectAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        }
        objectAnimatorOfFloat.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimatorOfFloat.setDuration(AnimationUtils.lerp(this.hideDurationMax, this.hideDurationMin, backEventCompat.getProgress()));
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.motion.MaterialSideContainerBackHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                MaterialSideContainerBackHelper.this.view.setTranslationX(0.0f);
                MaterialSideContainerBackHelper.this.updateBackProgress(0.0f, z2, i2);
            }
        });
        if (animatorListener != null) {
            objectAnimatorOfFloat.addListener(animatorListener);
        }
        objectAnimatorOfFloat.start();
    }

    public void startBackProgress(@NonNull BackEventCompat backEventCompat) {
        super.onStartBackProgress(backEventCompat);
    }

    @VisibleForTesting
    public void updateBackProgress(float f2, boolean z2, @GravityInt int i2) {
        float fInterpolateProgress = interpolateProgress(f2);
        boolean zCheckAbsoluteGravity = checkAbsoluteGravity(i2, 3);
        boolean z3 = z2 == zCheckAbsoluteGravity;
        int width = this.view.getWidth();
        int height = this.view.getHeight();
        float f3 = width;
        if (f3 > 0.0f) {
            float f4 = height;
            if (f4 <= 0.0f) {
                return;
            }
            float f5 = this.maxScaleXDistanceShrink / f3;
            float f6 = this.maxScaleXDistanceGrow / f3;
            float f7 = this.maxScaleYDistance / f4;
            V v2 = this.view;
            if (zCheckAbsoluteGravity) {
                f3 = 0.0f;
            }
            v2.setPivotX(f3);
            if (!z3) {
                f6 = -f5;
            }
            float fLerp = AnimationUtils.lerp(0.0f, f6, fInterpolateProgress);
            float f8 = fLerp + 1.0f;
            this.view.setScaleX(f8);
            float fLerp2 = 1.0f - AnimationUtils.lerp(0.0f, f7, fInterpolateProgress);
            this.view.setScaleY(fLerp2);
            V v3 = this.view;
            if (v3 instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v3;
                for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                    View childAt = viewGroup.getChildAt(i3);
                    childAt.setPivotX(zCheckAbsoluteGravity ? childAt.getWidth() + (width - childAt.getRight()) : -childAt.getLeft());
                    childAt.setPivotY(-childAt.getTop());
                    float f9 = z3 ? 1.0f - fLerp : 1.0f;
                    float f10 = fLerp2 != 0.0f ? (f8 / fLerp2) * f9 : 1.0f;
                    childAt.setScaleX(f9);
                    childAt.setScaleY(f10);
                }
            }
        }
    }

    public void updateBackProgress(@NonNull BackEventCompat backEventCompat, @GravityInt int i2) {
        if (super.onUpdateBackProgress(backEventCompat) == null) {
            return;
        }
        updateBackProgress(backEventCompat.getProgress(), backEventCompat.getSwipeEdge() == 0, i2);
    }
}
