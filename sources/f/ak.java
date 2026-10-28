package f;

import android.app.Dialog;
import android.view.View;
import com.zenoatom.app.DashboardActivity;
import java.io.Serializable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ak implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f335a = 0;
    public final /* synthetic */ Dialog b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DashboardActivity f336c;
    public final /* synthetic */ Serializable d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Serializable f337e;

    public ak(DashboardActivity dashboardActivity, Dialog dialog, String str, String str2) {
        this.f336c = dashboardActivity;
        this.b = dialog;
        this.d = str;
        this.f337e = str2;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f335a;
        DashboardActivity dashboardActivity = this.f336c;
        Object obj = this.f337e;
        Dialog dialog = this.b;
        Object obj2 = this.d;
        switch (i2) {
            case 0:
                dialog.dismiss();
                int i3 = DashboardActivity.L;
                dashboardActivity.getClass();
                dashboardActivity.ap.execute(new v(dashboardActivity, (String) obj2, (String) obj, 2));
                break;
            default:
                int i4 = view.getId() == 2131231262 ? 0 : view.getId() == 2131231263 ? 1 : -1;
                if (i4 >= 0) {
                    int[] iArr = (int[]) obj2;
                    if (i4 != iArr[0]) {
                        dashboardActivity.getSharedPreferences("theme_prefs", 0).edit().putInt("bg_index", i4).apply();
                        iArr[0] = i4;
                        ((Runnable[]) obj)[0].run();
                        dialog.dismiss();
                        dashboardActivity.h();
                    } else {
                        dashboardActivity.getSharedPreferences("theme_prefs", 0).edit().putInt("bg_index", -1).remove("custom_bg_uri").apply();
                        iArr[0] = -1;
                        ((Runnable[]) obj)[0].run();
                        dialog.dismiss();
                        I.a(dashboardActivity, new J());
                    }
                    break;
                }
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ak(DashboardActivity dashboardActivity, int[] iArr, Runnable[] runnableArr, Dialog dialog) {
        this.f336c = dashboardActivity;
        this.d = iArr;
        this.f337e = runnableArr;
        this.b = dialog;
    }
}
