package f;

import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class am implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f339c;
    public final /* synthetic */ z d;

    public /* synthetic */ am(z zVar, String str, int i2) {
        this.b = i2;
        this.d = zVar;
        this.f339c = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        String str = this.f339c;
        z zVar = this.d;
        switch (i2) {
            case 0:
                DashboardActivity.o(zVar.f419c, str);
                zVar.f419c.s();
                break;
            default:
                DashboardActivity.p(zVar.f419c, str);
                break;
        }
    }
}
