package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.view.View;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import com.google.android.material.internal.FadeThroughDrawable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f114a;
    public final /* synthetic */ Object b;

    public /* synthetic */ b(int i2, Object obj) {
        this.f114a = i2;
        this.b = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i2 = this.f114a;
        Object obj = this.b;
        switch (i2) {
            case 0:
                ((View) obj).setAlpha(0.0f);
                break;
            case 1:
                SearchViewAnimationHelper.lambda$addDrawerArrowDrawableAnimatorIfNeeded$3((DrawerArrowDrawable) obj, valueAnimator);
                break;
            default:
                SearchViewAnimationHelper.lambda$addFadeThroughDrawableAnimatorIfNeeded$4((FadeThroughDrawable) obj, valueAnimator);
                break;
        }
    }
}
