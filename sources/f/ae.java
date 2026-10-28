package f;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ae implements Runnable {
    public final /* synthetic */ int b = 1;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f322c;
    public final /* synthetic */ String d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ String f323e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ z f324f;

    public ae(z zVar, int i2, String str, String str2) {
        this.f324f = zVar;
        this.f322c = i2;
        this.d = str;
        this.f323e = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        String str = this.d;
        String str2 = this.f323e;
        int i3 = this.f322c;
        z zVar = this.f324f;
        switch (i2) {
            case 0:
                LinearLayout linearLayout = zVar.f419c.f175h;
                if (linearLayout != null) {
                    linearLayout.setVisibility(0);
                }
                DashboardActivity dashboardActivity = zVar.f419c;
                TextView textView = dashboardActivity.f176i;
                if (textView != null) {
                    textView.setText(str);
                }
                TextView textView2 = dashboardActivity.f177j;
                if (textView2 != null) {
                    textView2.setText(String.valueOf(i3));
                }
                TextView textView3 = dashboardActivity.f178k;
                if (textView3 != null) {
                    textView3.setText(str2);
                }
                break;
            default:
                DashboardActivity.n(zVar.f419c);
                DashboardActivity dashboardActivity2 = zVar.f419c;
                dashboardActivity2.u(false);
                if (i3 == 1) {
                    DashboardActivity.m(dashboardActivity2, str, str2, "");
                }
                dashboardActivity2.s();
                break;
        }
    }

    public ae(z zVar, String str, int i2, String str2) {
        this.f324f = zVar;
        this.d = str;
        this.f322c = i2;
        this.f323e = str2;
    }
}
