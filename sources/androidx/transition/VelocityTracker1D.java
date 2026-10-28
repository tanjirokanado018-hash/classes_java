package androidx.transition;

import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class VelocityTracker1D {
    private static final int ASSUME_POINTER_MOVE_STOPPED_MILLIS = 40;
    private static final int HISTORY_SIZE = 20;
    private static final int HORIZON_MILLIS = 100;
    private float[] mDataSamples = new float[20];
    private int mIndex = 0;
    private long[] mTimeSamples;

    public VelocityTracker1D() {
        long[] jArr = new long[20];
        this.mTimeSamples = jArr;
        Arrays.fill(jArr, Long.MIN_VALUE);
    }

    private float kineticEnergyToVelocity(float f2) {
        return (float) (Math.sqrt(Math.abs(f2) * 2.0f) * ((double) Math.signum(f2)));
    }

    public void addDataPoint(long j2, float f2) {
        int i2 = (this.mIndex + 1) % 20;
        this.mIndex = i2;
        this.mTimeSamples[i2] = j2;
        this.mDataSamples[i2] = f2;
    }

    public float calculateVelocity() {
        float fKineticEnergyToVelocity;
        int i2 = this.mIndex;
        if (i2 == 0 && this.mTimeSamples[i2] == Long.MIN_VALUE) {
            return 0.0f;
        }
        long j2 = this.mTimeSamples[i2];
        int i3 = 0;
        long j3 = j2;
        while (true) {
            long j4 = this.mTimeSamples[i2];
            if (j4 == Long.MIN_VALUE) {
                break;
            }
            float f2 = j2 - j4;
            float fAbs = Math.abs(j4 - j3);
            if (f2 > 100.0f || fAbs > 40.0f) {
                break;
            }
            if (i2 == 0) {
                i2 = 20;
            }
            i2--;
            i3++;
            if (i3 >= 20) {
                break;
            }
            j3 = j4;
        }
        if (i3 < 2) {
            return 0.0f;
        }
        if (i3 == 2) {
            int i4 = this.mIndex;
            int i5 = i4 == 0 ? 19 : i4 - 1;
            long[] jArr = this.mTimeSamples;
            float f3 = jArr[i4] - jArr[i5];
            if (f3 == 0.0f) {
                return 0.0f;
            }
            float[] fArr = this.mDataSamples;
            fKineticEnergyToVelocity = (fArr[i4] - fArr[i5]) / f3;
        } else {
            int i6 = this.mIndex;
            int i7 = ((i6 - i3) + 21) % 20;
            int i8 = (i6 + 21) % 20;
            long j5 = this.mTimeSamples[i7];
            float f4 = this.mDataSamples[i7];
            int i9 = i7 + 1;
            float f5 = 0.0f;
            for (int i10 = i9 % 20; i10 != i8; i10 = (i10 + 1) % 20) {
                long j6 = this.mTimeSamples[i10];
                float f6 = j6 - j5;
                if (f6 != 0.0f) {
                    float f7 = this.mDataSamples[i10];
                    float f8 = (f7 - f4) / f6;
                    float fAbs2 = (Math.abs(f8) * (f8 - kineticEnergyToVelocity(f5))) + f5;
                    if (i10 == i9) {
                        fAbs2 *= 0.5f;
                    }
                    f5 = fAbs2;
                    f4 = f7;
                    j5 = j6;
                }
            }
            fKineticEnergyToVelocity = kineticEnergyToVelocity(f5);
        }
        return fKineticEnergyToVelocity * 1000.0f;
    }

    public void resetTracking() {
        this.mIndex = 0;
        Arrays.fill(this.mTimeSamples, Long.MIN_VALUE);
        Arrays.fill(this.mDataSamples, 0.0f);
    }
}
