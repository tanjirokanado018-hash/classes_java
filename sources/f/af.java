package f;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class af implements Runnable {
    public final /* synthetic */ String b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f325c;
    public final /* synthetic */ int d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ int f326e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ z f327f;

    public af(z zVar, String str, int i2, int i3, int i4) {
        this.f327f = zVar;
        this.b = str;
        this.f325c = i2;
        this.d = i3;
        this.f326e = i4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str = this.b;
        boolean zIsEmpty = str.isEmpty();
        z zVar = this.f327f;
        if (!zIsEmpty) {
            zVar.f419c.f170a.setText(str);
        }
        TextView textView = zVar.f419c.f179l;
        int i2 = this.f325c;
        textView.setText(String.valueOf(i2));
        zVar.f419c.f180m.setText("Normal " + this.d);
        DashboardActivity dashboardActivity = zVar.f419c;
        dashboardActivity.f192y = i2 <= 0;
        dashboardActivity.r();
        if (this.f326e == 1) {
            DashboardActivity dashboardActivity2 = zVar.f419c;
            View viewInflate = dashboardActivity2.getLayoutInflater().inflate(2131427385, (ViewGroup) null);
            TextView textView2 = (TextView) viewInflate.findViewById(2131231297);
            Button button = (Button) viewInflate.findViewById(2131230833);
            if (textView2 != null) {
                textView2.setText("Daily Live 1 ရပါပြီ!");
            }
            Dialog dialog = new Dialog(dashboardActivity2);
            dialog.requestWindowFeature(1);
            dialog.setContentView(viewInflate);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.getWindow().setLayout((int) (dashboardActivity2.getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
            }
            if (button != null) {
                button.setOnClickListener(new ViewOnClickListenerC0023c(dialog, 5));
            }
            dialog.show();
        }
    }
}
