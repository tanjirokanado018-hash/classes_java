package androidx.core.location;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class n implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ LocationManagerCompat.PreRGnssStatusTransport f44c;
    public final /* synthetic */ Executor d;

    public /* synthetic */ n(LocationManagerCompat.PreRGnssStatusTransport preRGnssStatusTransport, Executor executor, int i2) {
        this.b = i2;
        this.f44c = preRGnssStatusTransport;
        this.d = executor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Executor executor = this.d;
        LocationManagerCompat.PreRGnssStatusTransport preRGnssStatusTransport = this.f44c;
        switch (i2) {
            case 0:
                preRGnssStatusTransport.lambda$onStopped$1(executor);
                break;
            default:
                preRGnssStatusTransport.lambda$onStarted$0(executor);
                break;
        }
    }
}
