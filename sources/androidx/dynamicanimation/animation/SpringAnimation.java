package androidx.dynamicanimation.animation;

import android.os.Looper;
import android.util.AndroidRuntimeException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SpringAnimation extends DynamicAnimation<SpringAnimation> {
    private static final float UNSET = Float.MAX_VALUE;
    private boolean mEndRequested;
    private float mPendingPosition;
    private SpringForce mSpring;

    public SpringAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    private void sanityCheck() {
        SpringForce springForce = this.mSpring;
        if (springForce == null) {
            throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
        }
        double finalPosition = springForce.getFinalPosition();
        if (finalPosition > this.mMaxValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        if (finalPosition < this.mMinValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
        }
    }

    public void animateToFinalPosition(float f2) {
        if (isRunning()) {
            this.mPendingPosition = f2;
            return;
        }
        if (this.mSpring == null) {
            this.mSpring = new SpringForce(f2);
        }
        this.mSpring.setFinalPosition(f2);
        start();
    }

    public boolean canSkipToEnd() {
        return this.mSpring.mDampingRatio > 0.0d;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public float getAcceleration(float f2, float f3) {
        return this.mSpring.getAcceleration(f2, f3);
    }

    public SpringForce getSpring() {
        return this.mSpring;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public boolean isAtEquilibrium(float f2, float f3) {
        return this.mSpring.isAtEquilibrium(f2, f3);
    }

    public SpringAnimation setSpring(SpringForce springForce) {
        this.mSpring = springForce;
        return this;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public void setValueThreshold(float f2) {
    }

    public void skipToEnd() {
        if (!canSkipToEnd()) {
            throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        }
        if (this.mRunning) {
            this.mEndRequested = true;
        }
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public void start() {
        sanityCheck();
        this.mSpring.setValueThreshold(getValueThreshold());
        super.start();
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public boolean updateValueAndVelocity(long j2) {
        SpringForce springForce;
        double d;
        double d2;
        long j3;
        if (this.mEndRequested) {
            float f2 = this.mPendingPosition;
            if (f2 != Float.MAX_VALUE) {
                this.mSpring.setFinalPosition(f2);
                this.mPendingPosition = Float.MAX_VALUE;
            }
            this.mValue = this.mSpring.getFinalPosition();
            this.mVelocity = 0.0f;
            this.mEndRequested = false;
            return true;
        }
        if (this.mPendingPosition != Float.MAX_VALUE) {
            this.mSpring.getFinalPosition();
            j3 = j2 / 2;
            DynamicAnimation.MassState massStateUpdateValues = this.mSpring.updateValues(this.mValue, this.mVelocity, j3);
            this.mSpring.setFinalPosition(this.mPendingPosition);
            this.mPendingPosition = Float.MAX_VALUE;
            springForce = this.mSpring;
            d = massStateUpdateValues.mValue;
            d2 = massStateUpdateValues.mVelocity;
        } else {
            springForce = this.mSpring;
            d = this.mValue;
            d2 = this.mVelocity;
            j3 = j2;
        }
        DynamicAnimation.MassState massStateUpdateValues2 = springForce.updateValues(d, d2, j3);
        this.mValue = massStateUpdateValues2.mValue;
        this.mVelocity = massStateUpdateValues2.mVelocity;
        float fMax = Math.max(this.mValue, this.mMinValue);
        this.mValue = fMax;
        float fMin = Math.min(fMax, this.mMaxValue);
        this.mValue = fMin;
        if (!isAtEquilibrium(fMin, this.mVelocity)) {
            return false;
        }
        this.mValue = this.mSpring.getFinalPosition();
        this.mVelocity = 0.0f;
        return true;
    }

    public <K> SpringAnimation(K k2, FloatPropertyCompat<K> floatPropertyCompat) {
        super(k2, floatPropertyCompat);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public <K> SpringAnimation(K k2, FloatPropertyCompat<K> floatPropertyCompat, float f2) {
        super(k2, floatPropertyCompat);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new SpringForce(f2);
    }
}
