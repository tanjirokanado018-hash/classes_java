package androidx.core.location;

import android.annotation.SuppressLint;
import android.location.LocationRequest;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Preconditions;
import androidx.core.util.TimeUtils;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LocationRequestCompat {
    private static final long IMPLICIT_MIN_UPDATE_INTERVAL = -1;
    public static final long PASSIVE_INTERVAL = Long.MAX_VALUE;
    public static final int QUALITY_BALANCED_POWER_ACCURACY = 102;
    public static final int QUALITY_HIGH_ACCURACY = 100;
    public static final int QUALITY_LOW_POWER = 104;
    final long mDurationMillis;
    final long mIntervalMillis;
    final long mMaxUpdateDelayMillis;
    final int mMaxUpdates;
    final float mMinUpdateDistanceMeters;
    final long mMinUpdateIntervalMillis;
    final int mQuality;

    public static class Api19Impl {
        private static Method sCreateFromDeprecatedProviderMethod;
        private static Class<?> sLocationRequestClass;
        private static Method sSetExpireInMethod;
        private static Method sSetFastestIntervalMethod;
        private static Method sSetNumUpdatesMethod;
        private static Method sSetQualityMethod;

        private Api19Impl() {
        }

        @SuppressLint({"BanUncheckedReflection"})
        public static Object toLocationRequest(LocationRequestCompat locationRequestCompat, String str) {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sCreateFromDeprecatedProviderMethod == null) {
                    Method declaredMethod = sLocationRequestClass.getDeclaredMethod("createFromDeprecatedProvider", String.class, Long.TYPE, Float.TYPE, Boolean.TYPE);
                    sCreateFromDeprecatedProviderMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                Object objInvoke = sCreateFromDeprecatedProviderMethod.invoke(null, str, Long.valueOf(locationRequestCompat.getIntervalMillis()), Float.valueOf(locationRequestCompat.getMinUpdateDistanceMeters()), Boolean.FALSE);
                if (objInvoke == null) {
                    return null;
                }
                if (sSetQualityMethod == null) {
                    Method declaredMethod2 = sLocationRequestClass.getDeclaredMethod("setQuality", Integer.TYPE);
                    sSetQualityMethod = declaredMethod2;
                    declaredMethod2.setAccessible(true);
                }
                sSetQualityMethod.invoke(objInvoke, Integer.valueOf(locationRequestCompat.getQuality()));
                if (sSetFastestIntervalMethod == null) {
                    Method declaredMethod3 = sLocationRequestClass.getDeclaredMethod("setFastestInterval", Long.TYPE);
                    sSetFastestIntervalMethod = declaredMethod3;
                    declaredMethod3.setAccessible(true);
                }
                sSetFastestIntervalMethod.invoke(objInvoke, Long.valueOf(locationRequestCompat.getMinUpdateIntervalMillis()));
                if (locationRequestCompat.getMaxUpdates() < Integer.MAX_VALUE) {
                    if (sSetNumUpdatesMethod == null) {
                        Method declaredMethod4 = sLocationRequestClass.getDeclaredMethod("setNumUpdates", Integer.TYPE);
                        sSetNumUpdatesMethod = declaredMethod4;
                        declaredMethod4.setAccessible(true);
                    }
                    sSetNumUpdatesMethod.invoke(objInvoke, Integer.valueOf(locationRequestCompat.getMaxUpdates()));
                }
                if (locationRequestCompat.getDurationMillis() < LocationRequestCompat.PASSIVE_INTERVAL) {
                    if (sSetExpireInMethod == null) {
                        Method declaredMethod5 = sLocationRequestClass.getDeclaredMethod("setExpireIn", Long.TYPE);
                        sSetExpireInMethod = declaredMethod5;
                        declaredMethod5.setAccessible(true);
                    }
                    sSetExpireInMethod.invoke(objInvoke, Long.valueOf(locationRequestCompat.getDurationMillis()));
                }
                return objInvoke;
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                return null;
            }
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        public static LocationRequest toLocationRequest(LocationRequestCompat locationRequestCompat) {
            return new LocationRequest.Builder(locationRequestCompat.getIntervalMillis()).setQuality(locationRequestCompat.getQuality()).setMinUpdateIntervalMillis(locationRequestCompat.getMinUpdateIntervalMillis()).setDurationMillis(locationRequestCompat.getDurationMillis()).setMaxUpdates(locationRequestCompat.getMaxUpdates()).setMinUpdateDistanceMeters(locationRequestCompat.getMinUpdateDistanceMeters()).setMaxUpdateDelayMillis(locationRequestCompat.getMaxUpdateDelayMillis()).build();
        }
    }

    public static final class Builder {
        private long mDurationMillis;
        private long mIntervalMillis;
        private long mMaxUpdateDelayMillis;
        private int mMaxUpdates;
        private float mMinUpdateDistanceMeters;
        private long mMinUpdateIntervalMillis;
        private int mQuality;

        public Builder(long j2) {
            setIntervalMillis(j2);
            this.mQuality = LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY;
            this.mDurationMillis = LocationRequestCompat.PASSIVE_INTERVAL;
            this.mMaxUpdates = Integer.MAX_VALUE;
            this.mMinUpdateIntervalMillis = -1L;
            this.mMinUpdateDistanceMeters = 0.0f;
            this.mMaxUpdateDelayMillis = 0L;
        }

        @NonNull
        public LocationRequestCompat build() {
            Preconditions.checkState((this.mIntervalMillis == LocationRequestCompat.PASSIVE_INTERVAL && this.mMinUpdateIntervalMillis == -1) ? false : true, "passive location requests must have an explicit minimum update interval");
            long j2 = this.mIntervalMillis;
            return new LocationRequestCompat(j2, this.mQuality, this.mDurationMillis, this.mMaxUpdates, Math.min(this.mMinUpdateIntervalMillis, j2), this.mMinUpdateDistanceMeters, this.mMaxUpdateDelayMillis);
        }

        @NonNull
        public Builder clearMinUpdateIntervalMillis() {
            this.mMinUpdateIntervalMillis = -1L;
            return this;
        }

        @NonNull
        public Builder setDurationMillis(@IntRange(from = 1) long j2) {
            this.mDurationMillis = Preconditions.checkArgumentInRange(j2, 1L, LocationRequestCompat.PASSIVE_INTERVAL, "durationMillis");
            return this;
        }

        @NonNull
        public Builder setIntervalMillis(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) long j2) {
            this.mIntervalMillis = Preconditions.checkArgumentInRange(j2, 0L, LocationRequestCompat.PASSIVE_INTERVAL, "intervalMillis");
            return this;
        }

        @NonNull
        public Builder setMaxUpdateDelayMillis(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) long j2) {
            this.mMaxUpdateDelayMillis = j2;
            this.mMaxUpdateDelayMillis = Preconditions.checkArgumentInRange(j2, 0L, LocationRequestCompat.PASSIVE_INTERVAL, "maxUpdateDelayMillis");
            return this;
        }

        @NonNull
        public Builder setMaxUpdates(@IntRange(from = 1, to = 2147483647L) int i2) {
            this.mMaxUpdates = Preconditions.checkArgumentInRange(i2, 1, Integer.MAX_VALUE, "maxUpdates");
            return this;
        }

        @NonNull
        public Builder setMinUpdateDistanceMeters(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
            this.mMinUpdateDistanceMeters = f2;
            this.mMinUpdateDistanceMeters = Preconditions.checkArgumentInRange(f2, 0.0f, Float.MAX_VALUE, "minUpdateDistanceMeters");
            return this;
        }

        @NonNull
        public Builder setMinUpdateIntervalMillis(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) long j2) {
            this.mMinUpdateIntervalMillis = Preconditions.checkArgumentInRange(j2, 0L, LocationRequestCompat.PASSIVE_INTERVAL, "minUpdateIntervalMillis");
            return this;
        }

        @NonNull
        public Builder setQuality(int i2) {
            Preconditions.checkArgument(i2 == 104 || i2 == 102 || i2 == 100, "quality must be a defined QUALITY constant, not %d", Integer.valueOf(i2));
            this.mQuality = i2;
            return this;
        }

        public Builder(@NonNull LocationRequestCompat locationRequestCompat) {
            this.mIntervalMillis = locationRequestCompat.mIntervalMillis;
            this.mQuality = locationRequestCompat.mQuality;
            this.mDurationMillis = locationRequestCompat.mDurationMillis;
            this.mMaxUpdates = locationRequestCompat.mMaxUpdates;
            this.mMinUpdateIntervalMillis = locationRequestCompat.mMinUpdateIntervalMillis;
            this.mMinUpdateDistanceMeters = locationRequestCompat.mMinUpdateDistanceMeters;
            this.mMaxUpdateDelayMillis = locationRequestCompat.mMaxUpdateDelayMillis;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Quality {
    }

    public LocationRequestCompat(long j2, int i2, long j3, int i3, long j4, float f2, long j5) {
        this.mIntervalMillis = j2;
        this.mQuality = i2;
        this.mMinUpdateIntervalMillis = j4;
        this.mDurationMillis = j3;
        this.mMaxUpdates = i3;
        this.mMinUpdateDistanceMeters = f2;
        this.mMaxUpdateDelayMillis = j5;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationRequestCompat)) {
            return false;
        }
        LocationRequestCompat locationRequestCompat = (LocationRequestCompat) obj;
        return this.mQuality == locationRequestCompat.mQuality && this.mIntervalMillis == locationRequestCompat.mIntervalMillis && this.mMinUpdateIntervalMillis == locationRequestCompat.mMinUpdateIntervalMillis && this.mDurationMillis == locationRequestCompat.mDurationMillis && this.mMaxUpdates == locationRequestCompat.mMaxUpdates && Float.compare(locationRequestCompat.mMinUpdateDistanceMeters, this.mMinUpdateDistanceMeters) == 0 && this.mMaxUpdateDelayMillis == locationRequestCompat.mMaxUpdateDelayMillis;
    }

    @IntRange(from = 1)
    public long getDurationMillis() {
        return this.mDurationMillis;
    }

    @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY)
    public long getIntervalMillis() {
        return this.mIntervalMillis;
    }

    @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY)
    public long getMaxUpdateDelayMillis() {
        return this.mMaxUpdateDelayMillis;
    }

    @IntRange(from = 1, to = 2147483647L)
    public int getMaxUpdates() {
        return this.mMaxUpdates;
    }

    @FloatRange(from = 0.0d, to = 3.4028234663852886E38d)
    public float getMinUpdateDistanceMeters() {
        return this.mMinUpdateDistanceMeters;
    }

    @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY)
    public long getMinUpdateIntervalMillis() {
        long j2 = this.mMinUpdateIntervalMillis;
        return j2 == -1 ? this.mIntervalMillis : j2;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public int hashCode() {
        int i2 = this.mQuality * 31;
        long j2 = this.mIntervalMillis;
        int i3 = (i2 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        long j3 = this.mMinUpdateIntervalMillis;
        return i3 + ((int) (j3 ^ (j3 >>> 32)));
    }

    @NonNull
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public LocationRequest toLocationRequest() {
        return Api31Impl.toLocationRequest(this);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003e  */
    /* JADX WARN: Code duplicated, block: B:21:0x004f  */
    /* JADX WARN: Code duplicated, block: B:29:0x007a  */
    /* JADX WARN: Code duplicated, block: B:32:0x008f  */
    @NonNull
    public String toString() {
        String str;
        long j2;
        StringBuilder sbH = androidx.appcompat.graphics.drawable.a.h("Request[");
        if (this.mIntervalMillis != PASSIVE_INTERVAL) {
            sbH.append("@");
            TimeUtils.formatDuration(this.mIntervalMillis, sbH);
            int i2 = this.mQuality;
            if (i2 == 100) {
                str = " HIGH_ACCURACY";
            } else {
                if (i2 != 102) {
                    if (i2 == 104) {
                        str = " LOW_POWER";
                    }
                    if (this.mDurationMillis != PASSIVE_INTERVAL) {
                        sbH.append(", duration=");
                        TimeUtils.formatDuration(this.mDurationMillis, sbH);
                    }
                    if (this.mMaxUpdates != Integer.MAX_VALUE) {
                        sbH.append(", maxUpdates=");
                        sbH.append(this.mMaxUpdates);
                    }
                    j2 = this.mMinUpdateIntervalMillis;
                    if (j2 != -1 && j2 < this.mIntervalMillis) {
                        sbH.append(", minUpdateInterval=");
                        TimeUtils.formatDuration(this.mMinUpdateIntervalMillis, sbH);
                    }
                    if (this.mMinUpdateDistanceMeters > 0.0d) {
                        sbH.append(", minUpdateDistance=");
                        sbH.append(this.mMinUpdateDistanceMeters);
                    }
                    if (this.mMaxUpdateDelayMillis / 2 > this.mIntervalMillis) {
                        sbH.append(", maxUpdateDelay=");
                        TimeUtils.formatDuration(this.mMaxUpdateDelayMillis, sbH);
                    }
                    sbH.append(']');
                    return sbH.toString();
                }
                str = " BALANCED";
            }
        } else {
            str = "PASSIVE";
        }
        sbH.append(str);
        if (this.mDurationMillis != PASSIVE_INTERVAL) {
            sbH.append(", duration=");
            TimeUtils.formatDuration(this.mDurationMillis, sbH);
        }
        if (this.mMaxUpdates != Integer.MAX_VALUE) {
            sbH.append(", maxUpdates=");
            sbH.append(this.mMaxUpdates);
        }
        j2 = this.mMinUpdateIntervalMillis;
        if (j2 != -1) {
            sbH.append(", minUpdateInterval=");
            TimeUtils.formatDuration(this.mMinUpdateIntervalMillis, sbH);
        }
        if (this.mMinUpdateDistanceMeters > 0.0d) {
            sbH.append(", minUpdateDistance=");
            sbH.append(this.mMinUpdateDistanceMeters);
        }
        if (this.mMaxUpdateDelayMillis / 2 > this.mIntervalMillis) {
            sbH.append(", maxUpdateDelay=");
            TimeUtils.formatDuration(this.mMaxUpdateDelayMillis, sbH);
        }
        sbH.append(']');
        return sbH.toString();
    }

    @Nullable
    @SuppressLint({"NewApi"})
    public LocationRequest toLocationRequest(@NonNull String str) {
        return Build.VERSION.SDK_INT >= 31 ? toLocationRequest() : androidx.core.app.d.e(Api19Impl.toLocationRequest(this, str));
    }
}
