package androidx.core.location;

import android.annotation.SuppressLint;
import android.location.GnssMeasurementsEvent;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.DoNotInline;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.collection.SimpleArrayMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LocationManagerCompat {
    private static final long GET_CURRENT_LOCATION_TIMEOUT_MS = 30000;
    private static final long MAX_CURRENT_LOCATION_AGE_MS = 10000;
    private static final long PRE_N_LOOPER_TIMEOUT_S = 5;
    private static Field sContextField;
    private static Method sGnssRequestBuilderBuildMethod;
    private static Class<?> sGnssRequestBuilderClass;

    @GuardedBy("sLocationListeners")
    static final WeakHashMap<LocationListenerKey, WeakReference<LocationListenerTransport>> sLocationListeners = new WeakHashMap<>();
    private static Method sRegisterGnssMeasurementsCallbackMethod;

    public static class Api19Impl {
        private static Class<?> sLocationRequestClass;
        private static Method sRequestLocationUpdatesLooperMethod;

        private Api19Impl() {
        }

        @DoNotInline
        @SuppressLint({"BanUncheckedReflection"})
        public static boolean tryRequestLocationUpdates(LocationManager locationManager, String str, LocationRequestCompat locationRequestCompat, LocationListenerCompat locationListenerCompat, Looper looper) {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", sLocationRequestClass, LocationListener.class, Looper.class);
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest locationRequest = locationRequestCompat.toLocationRequest(str);
                if (locationRequest != null) {
                    sRequestLocationUpdatesLooperMethod.invoke(locationManager, locationRequest, locationListenerCompat, looper);
                    return true;
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException unused) {
            }
            return false;
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        @SuppressLint({"BanUncheckedReflection"})
        public static boolean tryRequestLocationUpdates(LocationManager locationManager, String str, LocationRequestCompat locationRequestCompat, LocationListenerTransport locationListenerTransport) {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sRequestLocationUpdatesLooperMethod == null) {
                    Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", sLocationRequestClass, LocationListener.class, Looper.class);
                    sRequestLocationUpdatesLooperMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                LocationRequest locationRequest = locationRequestCompat.toLocationRequest(str);
                if (locationRequest != null) {
                    synchronized (LocationManagerCompat.sLocationListeners) {
                        sRequestLocationUpdatesLooperMethod.invoke(locationManager, locationRequest, locationListenerTransport, Looper.getMainLooper());
                        LocationManagerCompat.registerLocationListenerTransport(locationManager, locationListenerTransport);
                    }
                    return true;
                }
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException unused) {
            }
            return false;
        }
    }

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        @DoNotInline
        public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent.Callback callback) {
            return locationManager.registerGnssMeasurementsCallback(callback);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        public static boolean registerGnssStatusCallback(LocationManager locationManager, Handler handler, Executor executor, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(handler != null);
            SimpleArrayMap<Object, Object> simpleArrayMap = GnssListenersHolder.sGnssStatusListeners;
            synchronized (simpleArrayMap) {
                try {
                    PreRGnssStatusTransport preRGnssStatusTransport = (PreRGnssStatusTransport) simpleArrayMap.get(callback);
                    if (preRGnssStatusTransport == null) {
                        preRGnssStatusTransport = new PreRGnssStatusTransport(callback);
                    } else {
                        preRGnssStatusTransport.unregister();
                    }
                    preRGnssStatusTransport.register(executor);
                    if (!locationManager.registerGnssStatusCallback(preRGnssStatusTransport, handler)) {
                        return false;
                    }
                    simpleArrayMap.put(callback, preRGnssStatusTransport);
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @DoNotInline
        public static void unregisterGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent.Callback callback) {
            locationManager.unregisterGnssMeasurementsCallback(callback);
        }

        @DoNotInline
        public static void unregisterGnssStatusCallback(LocationManager locationManager, Object obj) {
            if (obj instanceof PreRGnssStatusTransport) {
                ((PreRGnssStatusTransport) obj).unregister();
            }
            locationManager.unregisterGnssStatusCallback((GnssStatus.Callback) obj);
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        @DoNotInline
        public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent.Callback callback, @NonNull Handler handler) {
            return locationManager.registerGnssMeasurementsCallback(callback, handler);
        }
    }

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        public static String getGnssHardwareModelName(LocationManager locationManager) {
            return locationManager.getGnssHardwareModelName();
        }

        @DoNotInline
        public static int getGnssYearOfHardware(LocationManager locationManager) {
            return locationManager.getGnssYearOfHardware();
        }

        @DoNotInline
        public static boolean isLocationEnabled(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static class Api30Impl {
        private static Class<?> sLocationRequestClass;
        private static Method sRequestLocationUpdatesExecutorMethod;

        private Api30Impl() {
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        public static void getCurrentLocation(LocationManager locationManager, @NonNull String str, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
            Objects.requireNonNull(consumer);
            locationManager.getCurrentLocation(str, cancellationSignal, executor, new java.util.function.Consumer() { // from class: androidx.core.location.d
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    consumer.accept((Location) obj);
                }
            });
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        public static boolean registerGnssStatusCallback(LocationManager locationManager, Handler handler, Executor executor, GnssStatusCompat.Callback callback) {
            SimpleArrayMap<Object, Object> simpleArrayMap = GnssListenersHolder.sGnssStatusListeners;
            synchronized (simpleArrayMap) {
                try {
                    GnssStatusTransport gnssStatusTransport = (GnssStatusTransport) simpleArrayMap.get(callback);
                    if (gnssStatusTransport == null) {
                        gnssStatusTransport = new GnssStatusTransport(callback);
                    }
                    if (!locationManager.registerGnssStatusCallback(executor, gnssStatusTransport)) {
                        return false;
                    }
                    simpleArrayMap.put(callback, gnssStatusTransport);
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @DoNotInline
        public static boolean tryRequestLocationUpdates(LocationManager locationManager, String str, LocationRequestCompat locationRequestCompat, Executor executor, LocationListenerCompat locationListenerCompat) {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    if (sLocationRequestClass == null) {
                        sLocationRequestClass = Class.forName("android.location.LocationRequest");
                    }
                    if (sRequestLocationUpdatesExecutorMethod == null) {
                        Method declaredMethod = LocationManager.class.getDeclaredMethod("requestLocationUpdates", sLocationRequestClass, Executor.class, LocationListener.class);
                        sRequestLocationUpdatesExecutorMethod = declaredMethod;
                        declaredMethod.setAccessible(true);
                    }
                    LocationRequest locationRequest = locationRequestCompat.toLocationRequest(str);
                    if (locationRequest != null) {
                        sRequestLocationUpdatesExecutorMethod.invoke(locationManager, locationRequest, executor, locationListenerCompat);
                        return true;
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | UnsupportedOperationException | InvocationTargetException unused) {
                }
            }
            return false;
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        public static boolean hasProvider(LocationManager locationManager, @NonNull String str) {
            return locationManager.hasProvider(str);
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        @DoNotInline
        public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssMeasurementsEvent.Callback callback) {
            return locationManager.registerGnssMeasurementsCallback(executor, callback);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        public static void requestLocationUpdates(LocationManager locationManager, @NonNull String str, @NonNull LocationRequest locationRequest, @NonNull Executor executor, @NonNull LocationListener locationListener) {
            locationManager.requestLocationUpdates(str, locationRequest, executor, locationListener);
        }
    }

    public static final class CancellableLocationListener implements LocationListener {
        private Consumer<Location> mConsumer;
        private final Executor mExecutor;
        private final LocationManager mLocationManager;
        private final Handler mTimeoutHandler = new Handler(Looper.getMainLooper());

        @Nullable
        Runnable mTimeoutRunnable;

        @GuardedBy("this")
        private boolean mTriggered;

        public CancellableLocationListener(LocationManager locationManager, Executor executor, Consumer<Location> consumer) {
            this.mLocationManager = locationManager;
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        private void cleanup() {
            this.mConsumer = null;
            this.mLocationManager.removeUpdates(this);
            Runnable runnable = this.mTimeoutRunnable;
            if (runnable != null) {
                this.mTimeoutHandler.removeCallbacks(runnable);
                this.mTimeoutRunnable = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startTimeout$0() {
            this.mTimeoutRunnable = null;
            onLocationChanged((Location) null);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void cancel() {
            synchronized (this) {
                try {
                    if (this.mTriggered) {
                        return;
                    }
                    this.mTriggered = true;
                    cleanup();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.location.LocationListener
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onLocationChanged(@Nullable Location location) {
            synchronized (this) {
                try {
                    if (this.mTriggered) {
                        return;
                    }
                    this.mTriggered = true;
                    this.mExecutor.execute(new f(this.mConsumer, location, 0));
                    cleanup();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.location.LocationListener
        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onProviderDisabled(@NonNull String str) {
            onLocationChanged((Location) null);
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(@NonNull String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i2, Bundle bundle) {
        }

        @SuppressLint({"MissingPermission"})
        public void startTimeout(long j2) {
            synchronized (this) {
                try {
                    if (this.mTriggered) {
                        return;
                    }
                    Runnable runnable = new Runnable() { // from class: androidx.core.location.e
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.b.lambda$startTimeout$0();
                        }
                    };
                    this.mTimeoutRunnable = runnable;
                    this.mTimeoutHandler.postDelayed(runnable, j2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static class GnssListenersHolder {

        @GuardedBy("sGnssStatusListeners")
        static final SimpleArrayMap<Object, Object> sGnssStatusListeners = new SimpleArrayMap<>();

        @GuardedBy("sGnssMeasurementListeners")
        static final SimpleArrayMap<GnssMeasurementsEvent.Callback, GnssMeasurementsEvent.Callback> sGnssMeasurementListeners = new SimpleArrayMap<>();

        private GnssListenersHolder() {
        }
    }

    @RequiresApi(24)
    public static class GnssMeasurementsTransport extends GnssMeasurementsEvent.Callback {
        final GnssMeasurementsEvent.Callback mCallback;

        @Nullable
        volatile Executor mExecutor;

        public GnssMeasurementsTransport(@NonNull GnssMeasurementsEvent.Callback callback, @NonNull Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGnssMeasurementsReceived$0(Executor executor, GnssMeasurementsEvent gnssMeasurementsEvent) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onGnssMeasurementsReceived(gnssMeasurementsEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusChanged$1(Executor executor, int i2) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStatusChanged(i2);
        }

        @Override // android.location.GnssMeasurementsEvent.Callback
        public void onGnssMeasurementsReceived(GnssMeasurementsEvent gnssMeasurementsEvent) {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new g(this, executor, gnssMeasurementsEvent, 0));
        }

        @Override // android.location.GnssMeasurementsEvent.Callback
        public void onStatusChanged(int i2) {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new h(this, executor, i2, 0));
        }

        public void unregister() {
            this.mExecutor = null;
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static class GnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;

        public GnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(int i2) {
            this.mCallback.onFirstFix(i2);
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
        }

        @Override // android.location.GnssStatus.Callback
        public void onStarted() {
            this.mCallback.onStarted();
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            this.mCallback.onStopped();
        }
    }

    public static class GpsStatusTransport implements GpsStatus.Listener {
        final GnssStatusCompat.Callback mCallback;

        @Nullable
        volatile Executor mExecutor;
        private final LocationManager mLocationManager;

        public GpsStatusTransport(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mLocationManager = locationManager;
            this.mCallback = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$0(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStarted();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$1(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStopped();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$2(Executor executor, int i2) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onFirstFix(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGpsStatusChanged$3(Executor executor, GnssStatusCompat gnssStatusCompat) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onSatelliteStatusChanged(gnssStatusCompat);
        }

        @Override // android.location.GpsStatus.Listener
        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        public void onGpsStatusChanged(int i2) {
            Runnable runnable;
            Runnable hVar;
            GpsStatus gpsStatus;
            final Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            final int i3 = 1;
            if (i2 == 1) {
                final int i4 = 0;
                runnable = new Runnable(this) { // from class: androidx.core.location.i

                    /* JADX INFO: renamed from: c, reason: collision with root package name */
                    public final /* synthetic */ LocationManagerCompat.GpsStatusTransport f38c;

                    {
                        this.f38c = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i5 = i4;
                        Executor executor2 = executor;
                        LocationManagerCompat.GpsStatusTransport gpsStatusTransport = this.f38c;
                        switch (i5) {
                            case 0:
                                gpsStatusTransport.lambda$onGpsStatusChanged$0(executor2);
                                break;
                            default:
                                gpsStatusTransport.lambda$onGpsStatusChanged$1(executor2);
                                break;
                        }
                    }
                };
            } else {
                if (i2 != 2) {
                    if (i2 == 3) {
                        GpsStatus gpsStatus2 = this.mLocationManager.getGpsStatus(null);
                        if (gpsStatus2 == null) {
                            return;
                        } else {
                            hVar = new h(this, executor, gpsStatus2.getTimeToFirstFix(), i3);
                        }
                    } else if (i2 != 4 || (gpsStatus = this.mLocationManager.getGpsStatus(null)) == null) {
                        return;
                    } else {
                        hVar = new g(this, executor, GnssStatusCompat.wrap(gpsStatus), i3);
                    }
                    executor.execute(hVar);
                    return;
                }
                runnable = new Runnable(this) { // from class: androidx.core.location.i

                    /* JADX INFO: renamed from: c, reason: collision with root package name */
                    public final /* synthetic */ LocationManagerCompat.GpsStatusTransport f38c;

                    {
                        this.f38c = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i5 = i3;
                        Executor executor2 = executor;
                        LocationManagerCompat.GpsStatusTransport gpsStatusTransport = this.f38c;
                        switch (i5) {
                            case 0:
                                gpsStatusTransport.lambda$onGpsStatusChanged$0(executor2);
                                break;
                            default:
                                gpsStatusTransport.lambda$onGpsStatusChanged$1(executor2);
                                break;
                        }
                    }
                };
            }
            executor.execute(runnable);
        }

        public void register(Executor executor) {
            Preconditions.checkState(this.mExecutor == null);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }
    }

    public static final class InlineHandlerExecutor implements Executor {
        private final Handler mHandler;

        public InlineHandlerExecutor(@NonNull Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                runnable.run();
            } else {
                if (this.mHandler.post((Runnable) Preconditions.checkNotNull(runnable))) {
                    return;
                }
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    public static class LocationListenerKey {
        final LocationListenerCompat mListener;
        final String mProvider;

        public LocationListenerKey(String str, LocationListenerCompat locationListenerCompat) {
            this.mProvider = (String) ObjectsCompat.requireNonNull(str, "invalid null provider");
            this.mListener = (LocationListenerCompat) ObjectsCompat.requireNonNull(locationListenerCompat, "invalid null listener");
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof LocationListenerKey)) {
                return false;
            }
            LocationListenerKey locationListenerKey = (LocationListenerKey) obj;
            return this.mProvider.equals(locationListenerKey.mProvider) && this.mListener.equals(locationListenerKey.mListener);
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mProvider, this.mListener);
        }
    }

    public static class LocationListenerTransport implements LocationListener {
        final Executor mExecutor;

        @Nullable
        volatile LocationListenerKey mKey;

        public LocationListenerTransport(@Nullable LocationListenerKey locationListenerKey, Executor executor) {
            this.mKey = locationListenerKey;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFlushComplete$2(int i2) {
            LocationListenerKey locationListenerKey = this.mKey;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.mListener.onFlushComplete(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocationChanged$0(Location location) {
            LocationListenerKey locationListenerKey = this.mKey;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.mListener.onLocationChanged(location);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocationChanged$1(List list) {
            LocationListenerKey locationListenerKey = this.mKey;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.mListener.onLocationChanged((List<Location>) list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProviderDisabled$5(String str) {
            LocationListenerKey locationListenerKey = this.mKey;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.mListener.onProviderDisabled(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProviderEnabled$4(String str) {
            LocationListenerKey locationListenerKey = this.mKey;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.mListener.onProviderEnabled(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusChanged$3(String str, int i2, Bundle bundle) {
            LocationListenerKey locationListenerKey = this.mKey;
            if (locationListenerKey == null) {
                return;
            }
            locationListenerKey.mListener.onStatusChanged(str, i2, bundle);
        }

        public LocationListenerKey getKey() {
            return (LocationListenerKey) ObjectsCompat.requireNonNull(this.mKey);
        }

        @Override // android.location.LocationListener
        public void onFlushComplete(final int i2) {
            if (this.mKey == null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: androidx.core.location.l
                @Override // java.lang.Runnable
                public final void run() {
                    this.b.lambda$onFlushComplete$2(i2);
                }
            });
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(@NonNull Location location) {
            if (this.mKey == null) {
                return;
            }
            this.mExecutor.execute(new k(this, location, 1));
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(@NonNull String str) {
            if (this.mKey == null) {
                return;
            }
            this.mExecutor.execute(new j(this, str, 1));
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(@NonNull String str) {
            if (this.mKey == null) {
                return;
            }
            this.mExecutor.execute(new j(this, str, 0));
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(final String str, final int i2, final Bundle bundle) {
            if (this.mKey == null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: androidx.core.location.m
                @Override // java.lang.Runnable
                public final void run() {
                    this.b.lambda$onStatusChanged$3(str, i2, bundle);
                }
            });
        }

        public void unregister() {
            this.mKey = null;
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(@NonNull List<Location> list) {
            if (this.mKey == null) {
                return;
            }
            this.mExecutor.execute(new k(this, list, 0));
        }
    }

    @RequiresApi(24)
    public static class PreRGnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;

        @Nullable
        volatile Executor mExecutor;

        public PreRGnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFirstFix$2(Executor executor, int i2) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onFirstFix(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSatelliteStatusChanged$3(Executor executor, GnssStatus gnssStatus) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(gnssStatus));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStarted$0(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStarted();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStopped$1(Executor executor) {
            if (this.mExecutor != executor) {
                return;
            }
            this.mCallback.onStopped();
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(int i2) {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new h(this, executor, i2, 2));
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new g(this, executor, gnssStatus, 2));
        }

        @Override // android.location.GnssStatus.Callback
        public void onStarted() {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new n(this, executor, 1));
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            Executor executor = this.mExecutor;
            if (executor == null) {
                return;
            }
            executor.execute(new n(this, executor, 0));
        }

        public void register(Executor executor) {
            Preconditions.checkArgument(executor != null, "invalid null executor");
            Preconditions.checkState(this.mExecutor == null);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }
    }

    private LocationManagerCompat() {
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void getCurrentLocation(@NonNull LocationManager locationManager, @NonNull String str, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull Consumer<Location> consumer) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api30Impl.getCurrentLocation(locationManager, str, cancellationSignal, executor, consumer);
            return;
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(str);
        if (lastKnownLocation != null && SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(lastKnownLocation) < MAX_CURRENT_LOCATION_AGE_MS) {
            executor.execute(new f(consumer, lastKnownLocation, 1));
            return;
        }
        final CancellableLocationListener cancellableLocationListener = new CancellableLocationListener(locationManager, executor, consumer);
        locationManager.requestLocationUpdates(str, 0L, 0.0f, cancellableLocationListener, Looper.getMainLooper());
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.core.location.b
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    cancellableLocationListener.cancel();
                }
            });
        }
        cancellableLocationListener.startTimeout(GET_CURRENT_LOCATION_TIMEOUT_MS);
    }

    @Nullable
    public static String getGnssHardwareModelName(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssHardwareModelName(locationManager);
        }
        return null;
    }

    public static int getGnssYearOfHardware(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssYearOfHardware(locationManager);
        }
        return 0;
    }

    public static boolean hasProvider(@NonNull LocationManager locationManager, @NonNull String str) {
        if (Build.VERSION.SDK_INT >= 31) {
            return Api31Impl.hasProvider(locationManager, str);
        }
        if (locationManager.getAllProviders().contains(str)) {
            return true;
        }
        try {
            return locationManager.getProvider(str) != null;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.isLocationEnabled(locationManager);
        }
        return locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$registerGnssStatusCallback$1(LocationManager locationManager, GpsStatusTransport gpsStatusTransport) {
        return Boolean.valueOf(locationManager.addGpsStatusListener(gpsStatusTransport));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @RequiresApi(24)
    public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent.Callback callback, @NonNull Handler handler) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 > 30) {
            return Api24Impl.registerGnssMeasurementsCallback(locationManager, callback, handler);
        }
        if (i2 == 30) {
            return registerGnssMeasurementsCallbackOnR(locationManager, ExecutorCompat.create(handler), callback);
        }
        SimpleArrayMap<GnssMeasurementsEvent.Callback, GnssMeasurementsEvent.Callback> simpleArrayMap = GnssListenersHolder.sGnssMeasurementListeners;
        synchronized (simpleArrayMap) {
            try {
                unregisterGnssMeasurementsCallback(locationManager, callback);
                if (!Api24Impl.registerGnssMeasurementsCallback(locationManager, callback, handler)) {
                    return false;
                }
                simpleArrayMap.put(callback, callback);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    private static boolean registerGnssMeasurementsCallbackOnR(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssMeasurementsEvent.Callback callback) {
        if (Build.VERSION.SDK_INT != 30) {
            throw new IllegalStateException();
        }
        try {
            if (sGnssRequestBuilderClass == null) {
                sGnssRequestBuilderClass = Class.forName("android.location.GnssRequest$Builder");
            }
            if (sGnssRequestBuilderBuildMethod == null) {
                Method declaredMethod = sGnssRequestBuilderClass.getDeclaredMethod("build", new Class[0]);
                sGnssRequestBuilderBuildMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            if (sRegisterGnssMeasurementsCallbackMethod == null) {
                Method declaredMethod2 = LocationManager.class.getDeclaredMethod("registerGnssMeasurementsCallback", Class.forName("android.location.GnssRequest"), Executor.class, androidx.appcompat.app.c.ac());
                sRegisterGnssMeasurementsCallbackMethod = declaredMethod2;
                declaredMethod2.setAccessible(true);
            }
            Object objInvoke = sRegisterGnssMeasurementsCallbackMethod.invoke(locationManager, sGnssRequestBuilderBuildMethod.invoke(sGnssRequestBuilderClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]), new Object[0]), executor, callback);
            return objInvoke != null && ((Boolean) objInvoke).booleanValue();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:64:0x00cc A[Catch: all -> 0x00a7, TryCatch #7 {all -> 0x00a7, blocks: (B:27:0x0056, B:60:0x00ad, B:61:0x00c3, B:62:0x00c4, B:64:0x00cc, B:66:0x00d4, B:67:0x00da, B:68:0x00db, B:69:0x00e0, B:70:0x00e1, B:71:0x00e7), top: B:79:0x0056 }] */
    /* JADX WARN: Code duplicated, block: B:66:0x00d4 A[Catch: all -> 0x00a7, TryCatch #7 {all -> 0x00a7, blocks: (B:27:0x0056, B:60:0x00ad, B:61:0x00c3, B:62:0x00c4, B:64:0x00cc, B:66:0x00d4, B:67:0x00da, B:68:0x00db, B:69:0x00e0, B:70:0x00e1, B:71:0x00e7), top: B:79:0x0056 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x00db A[Catch: all -> 0x00a7, TryCatch #7 {all -> 0x00a7, blocks: (B:27:0x0056, B:60:0x00ad, B:61:0x00c3, B:62:0x00c4, B:64:0x00cc, B:66:0x00d4, B:67:0x00da, B:68:0x00db, B:69:0x00e0, B:70:0x00e1, B:71:0x00e7), top: B:79:0x0056 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x00e1 A[Catch: all -> 0x00a7, TryCatch #7 {all -> 0x00a7, blocks: (B:27:0x0056, B:60:0x00ad, B:61:0x00c3, B:62:0x00c4, B:64:0x00cc, B:66:0x00d4, B:67:0x00da, B:68:0x00db, B:69:0x00e0, B:70:0x00e1, B:71:0x00e7), top: B:79:0x0056 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x00ea A[Catch: all -> 0x002f, TRY_ENTER, TryCatch #10 {all -> 0x002f, blocks: (B:16:0x0021, B:18:0x0029, B:22:0x0035, B:24:0x004c, B:33:0x0079, B:34:0x0080, B:43:0x008d, B:44:0x0094, B:73:0x00ea, B:74:0x00f1, B:25:0x0050, B:75:0x00f2, B:76:0x0108, B:21:0x0032), top: B:80:0x0021 }] */
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    private static boolean registerGnssStatusCallback(final LocationManager locationManager, Handler handler, Executor executor, GnssStatusCompat.Callback callback) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            return Api30Impl.registerGnssStatusCallback(locationManager, handler, executor, callback);
        }
        if (i2 >= 24) {
            return Api24Impl.registerGnssStatusCallback(locationManager, handler, executor, callback);
        }
        boolean z2 = false;
        Preconditions.checkArgument(handler != null);
        SimpleArrayMap<Object, Object> simpleArrayMap = GnssListenersHolder.sGnssStatusListeners;
        synchronized (simpleArrayMap) {
            try {
                final GpsStatusTransport gpsStatusTransport = (GpsStatusTransport) simpleArrayMap.get(callback);
                if (gpsStatusTransport == null) {
                    gpsStatusTransport = new GpsStatusTransport(locationManager, callback);
                } else {
                    gpsStatusTransport.unregister();
                }
                gpsStatusTransport.register(executor);
                FutureTask futureTask = new FutureTask(new Callable() { // from class: androidx.core.location.c
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return LocationManagerCompat.lambda$registerGnssStatusCallback$1(locationManager, gpsStatusTransport);
                    }
                });
                if (Looper.myLooper() == handler.getLooper()) {
                    futureTask.run();
                } else if (!handler.post(futureTask)) {
                    throw new IllegalStateException(handler + " is shutting down");
                }
                try {
                    try {
                        long nanos = TimeUnit.SECONDS.toNanos(PRE_N_LOOPER_TIMEOUT_S);
                        long jNanoTime = System.nanoTime() + nanos;
                        boolean z3 = false;
                        while (((Boolean) futureTask.get(nanos, TimeUnit.NANOSECONDS)).booleanValue()) {
                            try {
                                try {
                                    GnssListenersHolder.sGnssStatusListeners.put(callback, gpsStatusTransport);
                                    if (z3) {
                                        Thread.currentThread().interrupt();
                                    }
                                    return true;
                                } catch (ExecutionException e2) {
                                    e = e2;
                                    if (!(e.getCause() instanceof RuntimeException)) {
                                        throw ((RuntimeException) e.getCause());
                                    }
                                    if (e.getCause() instanceof Error) {
                                        throw ((Error) e.getCause());
                                    }
                                    throw new IllegalStateException(e);
                                } catch (TimeoutException e3) {
                                    e = e3;
                                    throw new IllegalStateException(handler + " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread", e);
                                } catch (Throwable th) {
                                    th = th;
                                    z2 = true;
                                    if (z2) {
                                        Thread.currentThread().interrupt();
                                    }
                                    throw th;
                                }
                            } catch (InterruptedException unused) {
                                nanos = jNanoTime - System.nanoTime();
                                z3 = true;
                            } catch (ExecutionException e4) {
                                e = e4;
                                if (!(e.getCause() instanceof RuntimeException)) {
                                    throw ((RuntimeException) e.getCause());
                                }
                                if (e.getCause() instanceof Error) {
                                    throw ((Error) e.getCause());
                                }
                                throw new IllegalStateException(e);
                            } catch (TimeoutException e5) {
                                e = e5;
                                throw new IllegalStateException(handler + " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread", e);
                            } catch (Throwable th2) {
                                th = th2;
                                z2 = z3;
                                if (z2) {
                                    Thread.currentThread().interrupt();
                                }
                                throw th;
                            }
                        }
                        if (z3) {
                            Thread.currentThread().interrupt();
                        }
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (ExecutionException e6) {
                    e = e6;
                } catch (TimeoutException e7) {
                    e = e7;
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @GuardedBy("sLocationListeners")
    public static void registerLocationListenerTransport(LocationManager locationManager, LocationListenerTransport locationListenerTransport) {
        WeakReference<LocationListenerTransport> weakReferencePut = sLocationListeners.put(locationListenerTransport.getKey(), new WeakReference<>(locationListenerTransport));
        LocationListenerTransport locationListenerTransport2 = weakReferencePut != null ? weakReferencePut.get() : null;
        if (locationListenerTransport2 != null) {
            locationListenerTransport2.unregister();
            locationManager.removeUpdates(locationListenerTransport2);
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void removeUpdates(@NonNull LocationManager locationManager, @NonNull LocationListenerCompat locationListenerCompat) {
        WeakHashMap<LocationListenerKey, WeakReference<LocationListenerTransport>> weakHashMap = sLocationListeners;
        synchronized (weakHashMap) {
            try {
                Iterator<WeakReference<LocationListenerTransport>> it = weakHashMap.values().iterator();
                ArrayList arrayList = null;
                while (it.hasNext()) {
                    LocationListenerTransport locationListenerTransport = it.next().get();
                    if (locationListenerTransport != null) {
                        LocationListenerKey key = locationListenerTransport.getKey();
                        if (key.mListener == locationListenerCompat) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(key);
                            locationListenerTransport.unregister();
                            locationManager.removeUpdates(locationListenerTransport);
                        }
                    }
                }
                if (arrayList != null) {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        sLocationListeners.remove((LocationListenerKey) it2.next());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        locationManager.removeUpdates(locationListenerCompat);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void requestLocationUpdates(@NonNull LocationManager locationManager, @NonNull String str, @NonNull LocationRequestCompat locationRequestCompat, @NonNull LocationListenerCompat locationListenerCompat, @NonNull Looper looper) {
        if (Build.VERSION.SDK_INT >= 31) {
            Api31Impl.requestLocationUpdates(locationManager, str, locationRequestCompat.toLocationRequest(), ExecutorCompat.create(new Handler(looper)), locationListenerCompat);
        } else {
            if (Api19Impl.tryRequestLocationUpdates(locationManager, str, locationRequestCompat, locationListenerCompat, looper)) {
                return;
            }
            locationManager.requestLocationUpdates(str, locationRequestCompat.getIntervalMillis(), locationRequestCompat.getMinUpdateDistanceMeters(), locationListenerCompat, looper);
        }
    }

    @RequiresApi(24)
    public static void unregisterGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull GnssMeasurementsEvent.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api24Impl.unregisterGnssMeasurementsCallback(locationManager, callback);
            return;
        }
        SimpleArrayMap<GnssMeasurementsEvent.Callback, GnssMeasurementsEvent.Callback> simpleArrayMap = GnssListenersHolder.sGnssMeasurementListeners;
        synchronized (simpleArrayMap) {
            try {
                GnssMeasurementsEvent.Callback callbackG = androidx.appcompat.app.c.g(simpleArrayMap.remove(callback));
                if (callbackG != null) {
                    if (callbackG instanceof GnssMeasurementsTransport) {
                        ((GnssMeasurementsTransport) callbackG).unregister();
                    }
                    Api24Impl.unregisterGnssMeasurementsCallback(locationManager, callbackG);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void unregisterGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 24) {
            SimpleArrayMap<Object, Object> simpleArrayMap = GnssListenersHolder.sGnssStatusListeners;
            synchronized (simpleArrayMap) {
                try {
                    Object objRemove = simpleArrayMap.remove(callback);
                    if (objRemove != null) {
                        Api24Impl.unregisterGnssStatusCallback(locationManager, objRemove);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        SimpleArrayMap<Object, Object> simpleArrayMap2 = GnssListenersHolder.sGnssStatusListeners;
        synchronized (simpleArrayMap2) {
            try {
                GpsStatusTransport gpsStatusTransport = (GpsStatusTransport) simpleArrayMap2.remove(callback);
                if (gpsStatusTransport != null) {
                    gpsStatusTransport.unregister();
                    locationManager.removeGpsStatusListener(gpsStatusTransport);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @Deprecated
    public static void getCurrentLocation(@NonNull LocationManager locationManager, @NonNull String str, @Nullable androidx.core.os.CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull Consumer<Location> consumer) {
        getCurrentLocation(locationManager, str, cancellationSignal != null ? (CancellationSignal) cancellationSignal.getCancellationSignalObject() : null, executor, consumer);
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @RequiresApi(24)
    public static boolean registerGnssMeasurementsCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssMeasurementsEvent.Callback callback) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 > 30) {
            return Api31Impl.registerGnssMeasurementsCallback(locationManager, executor, callback);
        }
        if (i2 == 30) {
            return registerGnssMeasurementsCallbackOnR(locationManager, executor, callback);
        }
        SimpleArrayMap<GnssMeasurementsEvent.Callback, GnssMeasurementsEvent.Callback> simpleArrayMap = GnssListenersHolder.sGnssMeasurementListeners;
        synchronized (simpleArrayMap) {
            try {
                GnssMeasurementsTransport gnssMeasurementsTransport = new GnssMeasurementsTransport(callback, executor);
                unregisterGnssMeasurementsCallback(locationManager, callback);
                if (!Api24Impl.registerGnssMeasurementsCallback(locationManager, gnssMeasurementsTransport)) {
                    return false;
                }
                simpleArrayMap.put(callback, gnssMeasurementsTransport);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback, @NonNull Handler handler) {
        return Build.VERSION.SDK_INT >= 30 ? registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback) : registerGnssStatusCallback(locationManager, new InlineHandlerExecutor(handler), callback);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void requestLocationUpdates(@NonNull LocationManager locationManager, @NonNull String str, @NonNull LocationRequestCompat locationRequestCompat, @NonNull Executor executor, @NonNull LocationListenerCompat locationListenerCompat) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            Api31Impl.requestLocationUpdates(locationManager, str, locationRequestCompat.toLocationRequest(), executor, locationListenerCompat);
            return;
        }
        if (i2 < 30 || !Api30Impl.tryRequestLocationUpdates(locationManager, str, locationRequestCompat, executor, locationListenerCompat)) {
            LocationListenerTransport locationListenerTransport = new LocationListenerTransport(new LocationListenerKey(str, locationListenerCompat), executor);
            if (Api19Impl.tryRequestLocationUpdates(locationManager, str, locationRequestCompat, locationListenerTransport)) {
                return;
            }
            synchronized (sLocationListeners) {
                locationManager.requestLocationUpdates(str, locationRequestCompat.getIntervalMillis(), locationRequestCompat.getMinUpdateDistanceMeters(), locationListenerTransport, Looper.getMainLooper());
                registerLocationListenerTransport(locationManager, locationListenerTransport);
            }
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, null, executor, callback);
        }
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            looperMyLooper = Looper.getMainLooper();
        }
        return registerGnssStatusCallback(locationManager, new Handler(looperMyLooper), executor, callback);
    }
}
