package androidx.core.location;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class j implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f39c;
    public final /* synthetic */ String d;

    public /* synthetic */ j(LocationManagerCompat.LocationListenerTransport locationListenerTransport, String str, int i2) {
        this.b = i2;
        this.f39c = locationListenerTransport;
        this.d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        String str = this.d;
        LocationManagerCompat.LocationListenerTransport locationListenerTransport = this.f39c;
        switch (i2) {
            case 0:
                locationListenerTransport.lambda$onProviderEnabled$4(str);
                break;
            default:
                locationListenerTransport.lambda$onProviderDisabled$5(str);
                break;
        }
    }
}
