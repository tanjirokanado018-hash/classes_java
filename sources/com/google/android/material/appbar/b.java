package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.google.android.material.internal.ExpandCollapseAnimationHelper;
import com.google.android.material.shape.MaterialShapeDrawable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f85a;
    public final /* synthetic */ Object b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f86c;

    public /* synthetic */ b(int i2, Object obj, Object obj2) {
        this.f85a = i2;
        this.b = obj;
        this.f86c = obj2;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i2 = this.f85a;
        Object obj = this.f86c;
        Object obj2 = this.b;
        switch (i2) {
            case 0:
                ((AppBarLayout) obj2).lambda$initializeLiftOnScrollWithElevation$1((MaterialShapeDrawable) obj, valueAnimator);
                break;
            default:
                ((ExpandCollapseAnimationHelper) obj2).lambda$getExpandCollapseAnimator$0((Rect) obj, valueAnimator);
                break;
        }
    }
}
