package androidx.profileinstaller;

import android.content.Context;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Context f74c;

    public /* synthetic */ b(Context context, int i2) {
        this.b = i2;
        this.f74c = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Context context = this.f74c;
        switch (i2) {
            case 0:
                ProfileInstallerInitializer.writeInBackground(context);
                break;
            default:
                ProfileInstaller.writeProfile(context);
                break;
        }
    }
}
