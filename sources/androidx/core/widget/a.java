package androidx.core.widget;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ContentLoadingProgressBar f60c;

    public /* synthetic */ a(ContentLoadingProgressBar contentLoadingProgressBar, int i2) {
        this.b = i2;
        this.f60c = contentLoadingProgressBar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        ContentLoadingProgressBar contentLoadingProgressBar = this.f60c;
        switch (i2) {
            case 0:
                contentLoadingProgressBar.lambda$new$0();
                break;
            case 1:
                contentLoadingProgressBar.lambda$new$1();
                break;
            case 2:
                contentLoadingProgressBar.showOnUiThread();
                break;
            default:
                contentLoadingProgressBar.hideOnUiThread();
                break;
        }
    }
}
