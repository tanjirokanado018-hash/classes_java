package f;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.LoginActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class aj implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f333a;
    public final /* synthetic */ Dialog b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DashboardActivity f334c;

    public /* synthetic */ aj(DashboardActivity dashboardActivity, Dialog dialog, int i2) {
        this.f333a = i2;
        this.f334c = dashboardActivity;
        this.b = dialog;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f333a;
        DashboardActivity dashboardActivity = this.f334c;
        Dialog dialog = this.b;
        switch (i2) {
            case 0:
                dialog.dismiss();
                break;
            case 1:
                dialog.dismiss();
                int i3 = DashboardActivity.L;
                dashboardActivity.getClass();
                dashboardActivity.ap.execute(new z(dashboardActivity, 7));
                break;
            case 2:
                dialog.dismiss();
                break;
            case 3:
                dialog.dismiss();
                break;
            case 4:
                dialog.dismiss();
                break;
            case 5:
                dialog.dismiss();
                break;
            case 6:
                dialog.dismiss();
                break;
            case 7:
                dialog.dismiss();
                dashboardActivity.getSharedPreferences("zenoatom_prefs", 0).edit().clear().apply();
                dashboardActivity.startActivity(new Intent(dashboardActivity, (Class<?>) LoginActivity.class));
                dashboardActivity.finish();
                break;
            case 8:
                dialog.dismiss();
                break;
            default:
                dialog.dismiss();
                break;
        }
    }
}
