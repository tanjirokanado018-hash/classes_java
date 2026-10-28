package androidx.core.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DifferentialMotionFlingController {
    private final Context mContext;
    private final int[] mFlingVelocityThresholds;
    private float mLastFlingVelocity;
    private int mLastProcessedAxis;
    private int mLastProcessedDeviceId;
    private int mLastProcessedSource;
    private final DifferentialMotionFlingTarget mTarget;
    private final DifferentialVelocityProvider mVelocityProvider;
    private final FlingVelocityThresholdCalculator mVelocityThresholdCalculator;

    @Nullable
    private VelocityTracker mVelocityTracker;

    @VisibleForTesting
    public interface DifferentialVelocityProvider {
        float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i2);
    }

    @VisibleForTesting
    public interface FlingVelocityThresholdCalculator {
        void calculateFlingVelocityThresholds(Context context, int[] iArr, MotionEvent motionEvent, int i2);
    }

    public DifferentialMotionFlingController(@NonNull Context context, @NonNull DifferentialMotionFlingTarget differentialMotionFlingTarget) {
        this(context, differentialMotionFlingTarget, new b(), new b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void calculateFlingVelocityThresholds(Context context, int[] iArr, MotionEvent motionEvent, int i2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        iArr[0] = ViewConfigurationCompat.getScaledMinimumFlingVelocity(context, viewConfiguration, motionEvent.getDeviceId(), i2, motionEvent.getSource());
        iArr[1] = ViewConfigurationCompat.getScaledMaximumFlingVelocity(context, viewConfiguration, motionEvent.getDeviceId(), i2, motionEvent.getSource());
    }

    private float getCurrentVelocity(MotionEvent motionEvent, int i2) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        return this.mVelocityProvider.getCurrentVelocity(this.mVelocityTracker, motionEvent, i2);
    }

    public void onMotionEvent(@NonNull MotionEvent motionEvent, int i2) {
        boolean zCalculateFlingVelocityThresholds = calculateFlingVelocityThresholds(motionEvent, i2);
        if (this.mFlingVelocityThresholds[0] == Integer.MAX_VALUE) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
                return;
            }
            return;
        }
        float scaledScrollFactor = this.mTarget.getScaledScrollFactor() * getCurrentVelocity(motionEvent, i2);
        float fSignum = Math.signum(scaledScrollFactor);
        if (zCalculateFlingVelocityThresholds || (fSignum != Math.signum(this.mLastFlingVelocity) && fSignum != 0.0f)) {
            this.mTarget.stopDifferentialMotionFling();
        }
        float fAbs = Math.abs(scaledScrollFactor);
        int[] iArr = this.mFlingVelocityThresholds;
        if (fAbs < iArr[0]) {
            return;
        }
        int i3 = iArr[1];
        float fMax = Math.max(-i3, Math.min(scaledScrollFactor, i3));
        this.mLastFlingVelocity = this.mTarget.startDifferentialMotionFling(fMax) ? fMax : 0.0f;
    }

    @VisibleForTesting
    public DifferentialMotionFlingController(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget, FlingVelocityThresholdCalculator flingVelocityThresholdCalculator, DifferentialVelocityProvider differentialVelocityProvider) {
        this.mLastProcessedAxis = -1;
        this.mLastProcessedSource = -1;
        this.mLastProcessedDeviceId = -1;
        this.mFlingVelocityThresholds = new int[]{Integer.MAX_VALUE, 0};
        this.mContext = context;
        this.mTarget = differentialMotionFlingTarget;
        this.mVelocityThresholdCalculator = flingVelocityThresholdCalculator;
        this.mVelocityProvider = differentialVelocityProvider;
    }

    private boolean calculateFlingVelocityThresholds(MotionEvent motionEvent, int i2) {
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        if (this.mLastProcessedSource == source && this.mLastProcessedDeviceId == deviceId && this.mLastProcessedAxis == i2) {
            return false;
        }
        this.mVelocityThresholdCalculator.calculateFlingVelocityThresholds(this.mContext, this.mFlingVelocityThresholds, motionEvent, i2);
        this.mLastProcessedSource = source;
        this.mLastProcessedDeviceId = deviceId;
        this.mLastProcessedAxis = i2;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i2) {
        VelocityTrackerCompat.addMovement(velocityTracker, motionEvent);
        VelocityTrackerCompat.computeCurrentVelocity(velocityTracker, 1000);
        return VelocityTrackerCompat.getAxisVelocity(velocityTracker, i2);
    }
}
