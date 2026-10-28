package androidx.core.view;

import android.view.View;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class h implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f53c;

    public /* synthetic */ h(int i2, Object obj) {
        this.b = i2;
        this.f53c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f53c;
        switch (i2) {
            case 0:
                SoftwareKeyboardControllerCompat.Impl20.lambda$show$0((View) obj);
                break;
            default:
                ((p.a) obj).invoke();
                break;
        }
    }
}
