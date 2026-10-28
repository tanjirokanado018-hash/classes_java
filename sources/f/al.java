package f;

import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class al implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f338c;
    public final /* synthetic */ v d;

    public /* synthetic */ al(v vVar, String str, int i2) {
        this.b = i2;
        this.d = vVar;
        this.f338c = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        String str = this.f338c;
        v vVar = this.d;
        switch (i2) {
            case 0:
                DashboardActivity.o((DashboardActivity) vVar.f414e, str);
                ((DashboardActivity) vVar.f414e).s();
                break;
            default:
                DashboardActivity.p((DashboardActivity) vVar.f414e, str);
                break;
        }
    }
}
