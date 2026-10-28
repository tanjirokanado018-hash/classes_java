package androidx.activity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f6c;

    public /* synthetic */ d(int i2, Object obj) {
        this.b = i2;
        this.f6c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f6c;
        switch (i2) {
            case 0:
                ((ComponentActivity.ReportFullyDrawnExecutorApi16Impl) obj).lambda$execute$0();
                break;
            case 1:
                ((ComponentActivity) obj).invalidateMenu();
                break;
            case 2:
                ComponentDialog.onBackPressedDispatcher$lambda$1((ComponentDialog) obj);
                break;
            default:
                FullyDrawnReporter.reportRunnable$lambda$2((FullyDrawnReporter) obj);
                break;
        }
    }
}
