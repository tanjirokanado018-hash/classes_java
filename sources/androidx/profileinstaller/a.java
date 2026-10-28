package androidx.profileinstaller;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f72c;
    public final /* synthetic */ int d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f73e;

    public /* synthetic */ a(Object obj, int i2, Object obj2, int i3) {
        this.b = i3;
        this.f72c = obj;
        this.d = i2;
        this.f73e = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f73e;
        int i3 = this.d;
        Object obj2 = this.f72c;
        switch (i2) {
            case 0:
                ((ProfileInstaller.DiagnosticsCallback) obj2).onResultReceived(i3, obj);
                break;
            case 1:
                ((ProfileInstaller.DiagnosticsCallback) obj2).onDiagnosticReceived(i3, obj);
                break;
            default:
                ((DeviceProfileWriter) obj2).lambda$result$0(i3, obj);
                break;
        }
    }
}
