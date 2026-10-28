package androidx.constraintlayout.motion.widget;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class MotionInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public abstract float getInterpolation(float f2);

    public abstract float getVelocity();
}
