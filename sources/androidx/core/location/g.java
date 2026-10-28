package androidx.core.location;

import android.location.GnssMeasurementsEvent;
import android.location.GnssStatus;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Executor f34c;
    public final /* synthetic */ Object d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f35e;

    public /* synthetic */ g(Object obj, Executor executor, Object obj2, int i2) {
        this.b = i2;
        this.d = obj;
        this.f34c = executor;
        this.f35e = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Executor executor = this.f34c;
        Object obj = this.f35e;
        Object obj2 = this.d;
        switch (i2) {
            case 0:
                ((LocationManagerCompat.GnssMeasurementsTransport) obj2).lambda$onGnssMeasurementsReceived$0(executor, (GnssMeasurementsEvent) obj);
                break;
            case 1:
                ((LocationManagerCompat.GpsStatusTransport) obj2).lambda$onGpsStatusChanged$3(executor, (GnssStatusCompat) obj);
                break;
            default:
                ((LocationManagerCompat.PreRGnssStatusTransport) obj2).lambda$onSatelliteStatusChanged$3(executor, (GnssStatus) obj);
                break;
        }
    }
}
