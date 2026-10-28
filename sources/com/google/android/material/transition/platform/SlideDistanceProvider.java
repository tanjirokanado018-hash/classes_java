package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(21)
public final class SlideDistanceProvider implements VisibilityAnimatorProvider {
    private static final int DEFAULT_DISTANCE = -1;

    @Px
    private int slideDistance = -1;
    private int slideEdge;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface GravityFlag {
    }

    public SlideDistanceProvider(int i2) {
        this.slideEdge = i2;
    }

    private static Animator createTranslationAppearAnimator(View view, View view2, int i2, @Px int i3) {
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        if (i2 == 3) {
            return createTranslationXAnimator(view2, i3 + translationX, translationX, translationX);
        }
        if (i2 == 5) {
            return createTranslationXAnimator(view2, translationX - i3, translationX, translationX);
        }
        if (i2 == 48) {
            return createTranslationYAnimator(view2, translationY - i3, translationY, translationY);
        }
        if (i2 == 80) {
            return createTranslationYAnimator(view2, i3 + translationY, translationY, translationY);
        }
        if (i2 == 8388611) {
            return createTranslationXAnimator(view2, isRtl(view) ? i3 + translationX : translationX - i3, translationX, translationX);
        }
        if (i2 == 8388613) {
            return createTranslationXAnimator(view2, isRtl(view) ? translationX - i3 : i3 + translationX, translationX, translationX);
        }
        throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.c("Invalid slide direction: ", i2));
    }

    private static Animator createTranslationDisappearAnimator(View view, View view2, int i2, @Px int i3) {
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        if (i2 == 3) {
            return createTranslationXAnimator(view2, translationX, translationX - i3, translationX);
        }
        if (i2 == 5) {
            return createTranslationXAnimator(view2, translationX, i3 + translationX, translationX);
        }
        if (i2 == 48) {
            return createTranslationYAnimator(view2, translationY, i3 + translationY, translationY);
        }
        if (i2 == 80) {
            return createTranslationYAnimator(view2, translationY, translationY - i3, translationY);
        }
        if (i2 == 8388611) {
            return createTranslationXAnimator(view2, translationX, isRtl(view) ? translationX - i3 : i3 + translationX, translationX);
        }
        if (i2 == 8388613) {
            return createTranslationXAnimator(view2, translationX, isRtl(view) ? i3 + translationX : translationX - i3, translationX);
        }
        throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.c("Invalid slide direction: ", i2));
    }

    private static Animator createTranslationXAnimator(final View view, float f2, float f3, final float f4) {
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_X, f2, f3));
        objectAnimatorOfPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.SlideDistanceProvider.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setTranslationX(f4);
            }
        });
        return objectAnimatorOfPropertyValuesHolder;
    }

    private static Animator createTranslationYAnimator(final View view, float f2, float f3, final float f4) {
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, f2, f3));
        objectAnimatorOfPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.SlideDistanceProvider.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setTranslationY(f4);
            }
        });
        return objectAnimatorOfPropertyValuesHolder;
    }

    private int getSlideDistanceOrDefault(Context context) {
        int i2 = this.slideDistance;
        return i2 != -1 ? i2 : context.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
    }

    private static boolean isRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    @Nullable
    public Animator createAppear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return createTranslationAppearAnimator(viewGroup, view, this.slideEdge, getSlideDistanceOrDefault(view.getContext()));
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    @Nullable
    public Animator createDisappear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return createTranslationDisappearAnimator(viewGroup, view, this.slideEdge, getSlideDistanceOrDefault(view.getContext()));
    }

    @Px
    public int getSlideDistance() {
        return this.slideDistance;
    }

    public int getSlideEdge() {
        return this.slideEdge;
    }

    public void setSlideDistance(@Px int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
        }
        this.slideDistance = i2;
    }

    public void setSlideEdge(int i2) {
        this.slideEdge = i2;
    }
}
