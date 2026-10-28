package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.color.utilities.Contrast;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class FadeProvider implements VisibilityAnimatorProvider {
    private float incomingEndThreshold = 1.0f;

    private static Animator createFadeAnimator(final View view, final float f2, final float f3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) final float f4, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) final float f5, final float f6) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.transition.FadeProvider.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha(TransitionUtils.lerp(f2, f3, f4, f5, ((Float) valueAnimator.getAnimatedValue()).floatValue()));
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.FadeProvider.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setAlpha(f6);
            }
        });
        return valueAnimatorOfFloat;
    }

    @Override // com.google.android.material.transition.VisibilityAnimatorProvider
    @Nullable
    public Animator createAppear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        float alpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeAnimator(view, 0.0f, alpha, 0.0f, this.incomingEndThreshold, alpha);
    }

    @Override // com.google.android.material.transition.VisibilityAnimatorProvider
    @Nullable
    public Animator createDisappear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        float alpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeAnimator(view, alpha, 0.0f, 0.0f, 1.0f, alpha);
    }

    public float getIncomingEndThreshold() {
        return this.incomingEndThreshold;
    }

    public void setIncomingEndThreshold(float f2) {
        this.incomingEndThreshold = f2;
    }
}
