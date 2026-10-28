package f;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ab implements Runnable {
    public final /* synthetic */ String b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f308c;
    public final /* synthetic */ String d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ String f309e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ String f310f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ String f311g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ u f312h;

    public ab(u uVar, String str, String str2, String str3, String str4, String str5, String str6) {
        this.f312h = uVar;
        this.b = str;
        this.f308c = str2;
        this.d = str3;
        this.f309e = str4;
        this.f310f = str5;
        this.f311g = str6;
    }

    @Override // java.lang.Runnable
    public final void run() {
        u uVar = this.f312h;
        String str = (String) uVar.f412c;
        String str2 = this.b;
        if (!str.equalsIgnoreCase(str2)) {
            DashboardActivity dashboardActivity = (DashboardActivity) uVar.d;
            Object obj = uVar.f412c;
            int i2 = DashboardActivity.L;
            View viewInflate = dashboardActivity.getLayoutInflater().inflate(2131427392, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(2131231346);
            LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230845);
            textView.setText("Version- " + str2);
            Dialog dialog = new Dialog(dashboardActivity);
            dialog.requestWindowFeature(1);
            dialog.setContentView(viewInflate);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.getWindow().setLayout((int) (dashboardActivity.getResources().getDisplayMetrics().widthPixels * 0.9f), -2);
            }
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            linearLayout.setOnClickListener(new com.google.android.material.snackbar.a(1, dashboardActivity, this.f308c));
            dialog.show();
            return;
        }
        String str3 = this.d;
        if (str3.isEmpty()) {
            return;
        }
        DashboardActivity dashboardActivity2 = (DashboardActivity) uVar.d;
        int i3 = DashboardActivity.L;
        View viewInflate2 = dashboardActivity2.getLayoutInflater().inflate(2131427388, (ViewGroup) null);
        TextView textView2 = (TextView) viewInflate2.findViewById(2131231314);
        TextView textView3 = (TextView) viewInflate2.findViewById(2131231315);
        TextView textView4 = (TextView) viewInflate2.findViewById(2131231313);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate2.findViewById(2131230849);
        LinearLayout linearLayout3 = (LinearLayout) viewInflate2.findViewById(2131230839);
        textView2.setText(str3);
        String str4 = this.f309e;
        if (str4 != null && !str4.isEmpty()) {
            textView3.setText(str4);
        }
        String str5 = this.f310f;
        if (str5 != null && !str5.isEmpty()) {
            textView4.setText(str5);
        }
        Dialog dialog2 = new Dialog(dashboardActivity2);
        dialog2.requestWindowFeature(1);
        dialog2.setContentView(viewInflate2);
        if (dialog2.getWindow() != null) {
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog2.getWindow().setLayout((int) (dashboardActivity2.getResources().getDisplayMetrics().widthPixels * 0.9f), -2);
        }
        dialog2.setCancelable(false);
        dialog2.setCanceledOnTouchOutside(false);
        linearLayout3.setOnClickListener(new ViewOnClickListenerC0023c(dialog2, 6));
        linearLayout2.setOnClickListener(new ViewOnClickListenerC0024d(dashboardActivity2, dialog2, this.f311g, 2));
        dialog2.show();
    }
}
