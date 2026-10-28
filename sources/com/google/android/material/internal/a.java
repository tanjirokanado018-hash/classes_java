package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements MultiViewUpdateListener.Listener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f109a;

    public /* synthetic */ a(int i2) {
        this.f109a = i2;
    }

    @Override // com.google.android.material.internal.MultiViewUpdateListener.Listener
    public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
        switch (this.f109a) {
            case 0:
                MultiViewUpdateListener.setTranslationX(valueAnimator, view);
                break;
            case 1:
                MultiViewUpdateListener.setTranslationX(valueAnimator, view);
                break;
            case 2:
                MultiViewUpdateListener.setScale(valueAnimator, view);
                break;
            case 3:
                MultiViewUpdateListener.setTranslationY(valueAnimator, view);
                break;
            case 4:
                MultiViewUpdateListener.setTranslationY(valueAnimator, view);
                break;
            case 5:
                MultiViewUpdateListener.setScale(valueAnimator, view);
                break;
            case 6:
                MultiViewUpdateListener.setAlpha(valueAnimator, view);
                break;
            default:
                MultiViewUpdateListener.setAlpha(valueAnimator, view);
                break;
        }
    }
}
