package androidx.appcompat.widget;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Toolbar f14c;

    public /* synthetic */ c(Toolbar toolbar, int i2) {
        this.b = i2;
        this.f14c = toolbar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Toolbar toolbar = this.f14c;
        switch (i2) {
            case 0:
                toolbar.collapseActionView();
                break;
            default:
                toolbar.invalidateMenu();
                break;
        }
    }
}
