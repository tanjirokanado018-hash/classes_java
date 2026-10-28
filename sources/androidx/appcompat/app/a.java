package androidx.appcompat.app;

import android.content.Context;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Context f10c;

    public /* synthetic */ a(Context context, int i2) {
        this.b = i2;
        this.f10c = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Context context = this.f10c;
        switch (i2) {
            case 0:
                AppCompatDelegate.lambda$syncRequestedAndStoredLocales$1(context);
                break;
            default:
                AppCompatDelegate.syncRequestedAndStoredLocales(context);
                break;
        }
    }
}
