package com.google.android.material.motion;

import android.animation.ValueAnimator;
import android.view.ViewGroup;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.navigation.DrawerLayoutUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f112a;
    public final /* synthetic */ ViewGroup b;

    public /* synthetic */ b(ViewGroup viewGroup, int i2) {
        this.f112a = i2;
        this.b = viewGroup;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i2 = this.f112a;
        ViewGroup viewGroup = this.b;
        switch (i2) {
            case 0:
                MaterialMainContainerBackHelper.lambda$createCornerAnimator$0((ClippableRoundedCornerLayout) viewGroup, valueAnimator);
                break;
            default:
                DrawerLayoutUtils.lambda$getScrimCloseAnimatorUpdateListener$0((DrawerLayout) viewGroup, valueAnimator);
                break;
        }
    }
}
