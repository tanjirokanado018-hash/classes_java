package f;

import android.R;
import com.google.android.material.snackbar.Snackbar;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ac implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ z f313c;

    public /* synthetic */ ac(z zVar, int i2) {
        this.b = i2;
        this.f313c = zVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        z zVar = this.f313c;
        switch (i2) {
            case 0:
                Snackbar snackbar = zVar.f419c.ar;
                if (snackbar != null && snackbar.isShown()) {
                    DashboardActivity dashboardActivity = zVar.f419c;
                    dashboardActivity.ar.dismiss();
                    dashboardActivity.ar = null;
                    break;
                }
                break;
            case 1:
                zVar.f419c.am.setRefreshing(false);
                DashboardActivity dashboardActivity2 = zVar.f419c;
                Snackbar snackbar2 = dashboardActivity2.ar;
                if (snackbar2 == null || !snackbar2.isShown()) {
                    Snackbar action = Snackbar.make(dashboardActivity2.findViewById(R.id.content), "ချိတ်ဆက်မှု မအောင်မြင်ပါ။ ပြန်လည်ကြိုးစားရန် နှိပ်ပါ။", -2).setAction("Reload", new y(19, this));
                    dashboardActivity2.ar = action;
                    action.show();
                }
                break;
            default:
                zVar.f419c.am.setRefreshing(false);
                break;
        }
    }
}
