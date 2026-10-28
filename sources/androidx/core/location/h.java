package androidx.core.location;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class h implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Executor f36c;
    public final /* synthetic */ int d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f37e;

    public /* synthetic */ h(Object obj, Executor executor, int i2, int i3) {
        this.b = i3;
        this.f37e = obj;
        this.f36c = executor;
        this.d = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        int i3 = this.d;
        Executor executor = this.f36c;
        Object obj = this.f37e;
        switch (i2) {
            case 0:
                ((LocationManagerCompat.GnssMeasurementsTransport) obj).lambda$onStatusChanged$1(executor, i3);
                break;
            case 1:
                ((LocationManagerCompat.GpsStatusTransport) obj).lambda$onGpsStatusChanged$2(executor, i3);
                break;
            default:
                ((LocationManagerCompat.PreRGnssStatusTransport) obj).lambda$onFirstFix$2(executor, i3);
                break;
        }
    }
}
