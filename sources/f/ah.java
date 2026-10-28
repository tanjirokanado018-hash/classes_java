package f;

import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ah implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f329c;
    public final /* synthetic */ String d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ String f330e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ z f331f;

    public ah(z zVar, int i2, String str, String str2, String str3) {
        this.f331f = zVar;
        this.b = i2;
        this.f329c = str;
        this.d = str2;
        this.f330e = str3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        z zVar = this.f331f;
        DashboardActivity.l(zVar.f419c);
        zVar.f419c.v(false);
        if (this.b == 1) {
            DashboardActivity.m(zVar.f419c, this.f329c, this.d, this.f330e);
        }
        zVar.f419c.s();
    }
}
