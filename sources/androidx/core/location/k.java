package androidx.core.location;

import android.location.Location;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f40c;
    public final /* synthetic */ Object d;

    public /* synthetic */ k(LocationManagerCompat.LocationListenerTransport locationListenerTransport, Object obj, int i2) {
        this.b = i2;
        this.f40c = locationListenerTransport;
        this.d = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        LocationManagerCompat.LocationListenerTransport locationListenerTransport = this.f40c;
        Object obj = this.d;
        switch (i2) {
            case 0:
                locationListenerTransport.lambda$onLocationChanged$1((List) obj);
                break;
            default:
                locationListenerTransport.lambda$onLocationChanged$0((Location) obj);
                break;
        }
    }
}
