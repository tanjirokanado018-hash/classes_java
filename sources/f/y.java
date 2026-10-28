package f;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zenoatom.app.AccountsActivity;
import com.zenoatom.app.CityRunActivity;
import com.zenoatom.app.ClaimActivity;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.HistoryActivity;
import com.zenoatom.app.PointsActivity;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class y implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f418a;
    public final /* synthetic */ Object b;

    public /* synthetic */ y(int i2, Object obj) {
        this.f418a = i2;
        this.b = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r14v4, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r2v24, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r4v13, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r9v18, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r9v4, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ?? r0;
        List list;
        boolean z2;
        boolean z3;
        int i2 = this.f418a;
        int i3 = 2;
        int i4 = 8;
        int i5 = 0;
        int i6 = 1;
        Object obj = this.b;
        switch (i2) {
            case 0:
                DashboardActivity dashboardActivity = (DashboardActivity) obj;
                int i7 = DashboardActivity.L;
                View viewInflate = dashboardActivity.getLayoutInflater().inflate(2131427382, (ViewGroup) null);
                LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230835);
                ?? r9 = (LinearLayout) viewInflate.findViewById(2131231002);
                LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(2131231026);
                Dialog dialog = new Dialog(dashboardActivity);
                dialog.requestWindowFeature(1);
                dialog.setContentView(viewInflate);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.getWindow().setLayout((int) (dashboardActivity.getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
                }
                dialog.setCanceledOnTouchOutside(true);
                linearLayout.setOnClickListener(new aj(dashboardActivity, dialog, i3));
                float f2 = 11.0f;
                if (r9 != 0 && (list = dashboardActivity.F) != null && !list.isEmpty()) {
                    r9.setVisibility(0);
                    for (String str : dashboardActivity.F) {
                        TextView textView = new TextView(dashboardActivity);
                        textView.setText(str);
                        textView.setTextColor(-208051);
                        textView.setTextSize(2, f2);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                        layoutParams.setMargins(0, 0, 0, 4);
                        textView.setLayoutParams(layoutParams);
                        r9.addView(textView);
                        f2 = 11.0f;
                    }
                } else if (r9 != 0) {
                    r9.setVisibility(8);
                }
                if (linearLayout2 != null && dashboardActivity.E != null) {
                    float f3 = dashboardActivity.getResources().getDisplayMetrics().density;
                    int i8 = (int) (f3 * 12.0f);
                    int i9 = (int) (16.0f * f3);
                    int i10 = (int) (7.0f * f3);
                    int i11 = (int) (8.0f * f3);
                    int i12 = (int) (f3 * 36.0f);
                    int i13 = 0;
                    ?? r10 = linearLayout2;
                    while (i13 < dashboardActivity.E.length()) {
                        JSONObject jSONObjectOptJSONObject = dashboardActivity.E.optJSONObject(i13);
                        if (jSONObjectOptJSONObject != null && "toh_toh_united_pack".equals(jSONObjectOptJSONObject.optString("type"))) {
                            String strOptString = jSONObjectOptJSONObject.optString("offerId", "");
                            String strOptString2 = jSONObjectOptJSONObject.optString("title", "");
                            String strOptString3 = jSONObjectOptJSONObject.optString("desc", "");
                            int iOptInt = jSONObjectOptJSONObject.optInt("chances", 0);
                            String str2 = strOptString2 + " - " + strOptString3;
                            ?? linearLayout3 = new LinearLayout(dashboardActivity);
                            linearLayout3.setOrientation(0);
                            linearLayout3.setGravity(16);
                            linearLayout3.setBackgroundResource(2131165326);
                            linearLayout3.setPadding(i8, i8, i8, i8);
                            ?? r19 = r10;
                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
                            layoutParams2.setMargins(0, 0, 0, i11);
                            linearLayout3.setLayoutParams(layoutParams2);
                            LinearLayout linearLayout4 = new LinearLayout(dashboardActivity);
                            linearLayout4.setGravity(17);
                            linearLayout4.setBackgroundResource(2131165330);
                            linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(i12, i12));
                            ImageView imageView = new ImageView(dashboardActivity);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(i9, i9));
                            imageView.setImageResource(2131165432);
                            imageView.setColorFilter(-14498466, PorterDuff.Mode.SRC_IN);
                            linearLayout4.addView(imageView);
                            linearLayout3.addView(linearLayout4);
                            ?? linearLayout5 = new LinearLayout(dashboardActivity);
                            linearLayout5.setOrientation(1);
                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, -2, 1.0f);
                            layoutParams3.setMarginStart(i8);
                            linearLayout5.setLayoutParams(layoutParams3);
                            ?? linearLayout6 = new LinearLayout(dashboardActivity);
                            linearLayout6.setOrientation(0);
                            linearLayout6.setGravity(16);
                            TextView textView2 = new TextView(dashboardActivity);
                            textView2.setText(strOptString2);
                            textView2.setTextColor(-460545);
                            textView2.setTextSize(2, 14.0f);
                            textView2.setTypeface(null, 1);
                            linearLayout6.addView(textView2);
                            if (iOptInt >= 12) {
                                TextView textView3 = new TextView(dashboardActivity);
                                textView3.setText(" Best");
                                textView3.setTextColor(-1);
                                textView3.setTextSize(2, 9.0f);
                                textView3.setTypeface(null, 1);
                                textView3.setBackgroundResource(2131165378);
                                int i14 = (int) (6.0f * f3);
                                int i15 = (int) (2.0f * f3);
                                textView3.setPadding(i14, i15, i14, i15);
                                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
                                layoutParams4.setMarginStart(i11);
                                textView3.setLayoutParams(layoutParams4);
                                linearLayout6.addView(textView3);
                            }
                            linearLayout5.addView(linearLayout6);
                            TextView textView4 = new TextView(dashboardActivity);
                            textView4.setText(strOptString3);
                            textView4.setTextColor(-1);
                            textView4.setTextSize(2, 11.0f);
                            linearLayout5.addView(textView4);
                            linearLayout3.addView(linearLayout5);
                            LinearLayout linearLayout7 = new LinearLayout(dashboardActivity);
                            linearLayout7.setGravity(17);
                            linearLayout7.setBackgroundResource(2131165308);
                            linearLayout7.setPadding(i9, i10, i9, i10);
                            linearLayout7.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                            TextView textView5 = new TextView(dashboardActivity);
                            textView5.setText("ဝယ်မည်");
                            textView5.setTextColor(-1);
                            textView5.setTextSize(2, 12.0f);
                            textView5.setTypeface(null, 1);
                            linearLayout7.addView(textView5);
                            linearLayout3.addView(linearLayout7);
                            linearLayout7.setOnClickListener(new ak(dashboardActivity, dialog, strOptString, str2));
                            r0 = r19;
                            r0.addView(linearLayout3);
                        } else {
                            r0 = r10;
                        }
                        i13++;
                        r10 = r0;
                        i12 = i12;
                    }
                }
                dialog.show();
                break;
            case 1:
                DashboardActivity dashboardActivity2 = (DashboardActivity) obj;
                dashboardActivity2.startActivity(new Intent(dashboardActivity2, (Class<?>) ClaimActivity.class));
                break;
            case 2:
                DashboardActivity dashboardActivity3 = (DashboardActivity) obj;
                dashboardActivity3.startActivity(new Intent(dashboardActivity3, (Class<?>) AccountsActivity.class));
                break;
            case 3:
                DashboardActivity dashboardActivity4 = (DashboardActivity) obj;
                dashboardActivity4.startActivity(new Intent(dashboardActivity4, (Class<?>) HistoryActivity.class));
                break;
            case 4:
                DashboardActivity dashboardActivity5 = (DashboardActivity) obj;
                dashboardActivity5.startActivity(new Intent(dashboardActivity5, (Class<?>) PointsActivity.class));
                break;
            case 5:
                DashboardActivity dashboardActivity6 = (DashboardActivity) obj;
                int i16 = DashboardActivity.L;
                View viewInflate2 = dashboardActivity6.getLayoutInflater().inflate(2131427387, (ViewGroup) null);
                Dialog dialog2 = new Dialog(dashboardActivity6);
                dialog2.requestWindowFeature(1);
                dialog2.setContentView(viewInflate2);
                if (dialog2.getWindow() != null) {
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog2.getWindow().setLayout((int) (dashboardActivity6.getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
                }
                LinearLayout linearLayout8 = (LinearLayout) viewInflate2.findViewById(2131230831);
                TextView textView6 = (TextView) viewInflate2.findViewById(2131230823);
                LinearLayout linearLayout9 = (LinearLayout) viewInflate2.findViewById(2131230838);
                if (linearLayout8 != null) {
                    linearLayout8.setOnClickListener(new aj(dashboardActivity6, dialog2, 7));
                }
                if (textView6 != null) {
                    textView6.setOnClickListener(new aj(dashboardActivity6, dialog2, i4));
                }
                if (linearLayout9 != null) {
                    linearLayout9.setOnClickListener(new aj(dashboardActivity6, dialog2, 9));
                }
                dialog2.show();
                break;
            case 6:
                DashboardActivity dashboardActivity7 = (DashboardActivity) obj;
                int i17 = DashboardActivity.L;
                View viewInflate3 = dashboardActivity7.getLayoutInflater().inflate(2131427391, (ViewGroup) null);
                Dialog dialog3 = new Dialog(dashboardActivity7);
                dialog3.requestWindowFeature(1);
                dialog3.setContentView(viewInflate3);
                if (dialog3.getWindow() != null) {
                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog3.getWindow().setLayout((int) (dashboardActivity7.getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
                }
                dialog3.setCancelable(true);
                dialog3.setCanceledOnTouchOutside(true);
                FrameLayout frameLayout = (FrameLayout) viewInflate3.findViewById(2131231262);
                FrameLayout frameLayout2 = (FrameLayout) viewInflate3.findViewById(2131231263);
                J[] jArr = J.f301g;
                int[] iArr = {w.y.q(dashboardActivity7)};
                Runnable[] runnableArr = {null};
                ao aoVar = new ao(dashboardActivity7, new FrameLayout[]{frameLayout, frameLayout2}, iArr, jArr);
                runnableArr[0] = aoVar;
                aoVar.run();
                ak akVar = new ak(dashboardActivity7, iArr, runnableArr, dialog3);
                if (frameLayout != null) {
                    frameLayout.setOnClickListener(akVar);
                }
                if (frameLayout2 != null) {
                    frameLayout2.setOnClickListener(akVar);
                }
                TextView textView7 = (TextView) viewInflate3.findViewById(2131230860);
                if (textView7 != null) {
                    textView7.setOnClickListener(new aj(dashboardActivity7, dialog3, 6));
                }
                dialog3.show();
                break;
            case 7:
                DashboardActivity dashboardActivity8 = (DashboardActivity) obj;
                DashboardActivity.j(dashboardActivity8, "DATA", dashboardActivity8.G, -10443270);
                break;
            case 8:
                DashboardActivity dashboardActivity9 = (DashboardActivity) obj;
                DashboardActivity.j(dashboardActivity9, "VOICE", dashboardActivity9.H, -11870592);
                break;
            case 9:
                DashboardActivity dashboardActivity10 = (DashboardActivity) obj;
                DashboardActivity.j(dashboardActivity10, "SMS", dashboardActivity10.I, -4160260);
                break;
            case 10:
                DashboardActivity dashboardActivity11 = (DashboardActivity) obj;
                int i18 = DashboardActivity.L;
                dashboardActivity11.getClass();
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(dashboardActivity11, 2131755296);
                View viewInflate4 = dashboardActivity11.getLayoutInflater().inflate(2131427364, (ViewGroup) null);
                bottomSheetDialog.setContentView(viewInflate4);
                TextView textView8 = (TextView) viewInflate4.findViewById(2131231318);
                ?? r2 = (LinearLayout) viewInflate4.findViewById(2131231129);
                TextView textView9 = (TextView) viewInflate4.findViewById(2131231317);
                if (textView8 != null) {
                    textView8.setText("BALANCE");
                }
                if (textView9 != null) {
                    textView9.setVisibility(8);
                }
                if (r2 != 0) {
                    r2.setVisibility(0);
                    float f4 = dashboardActivity11.getResources().getDisplayMetrics().density;
                    int i19 = (int) (f4 * 14.0f);
                    int i20 = (int) (f4 * 12.0f);
                    ?? linearLayout10 = new LinearLayout(dashboardActivity11);
                    linearLayout10.setOrientation(0);
                    linearLayout10.setGravity(16);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(-15062976);
                    gradientDrawable.setCornerRadius(dashboardActivity11.getResources().getDisplayMetrics().density * 14.0f);
                    linearLayout10.setBackground(gradientDrawable);
                    LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -2);
                    layoutParams5.setMargins(0, 0, 0, (int) (dashboardActivity11.getResources().getDisplayMetrics().density * 10.0f));
                    linearLayout10.setLayoutParams(layoutParams5);
                    linearLayout10.setPadding(i19, i19, i19, i19);
                    TextView textView10 = new TextView(dashboardActivity11);
                    textView10.setText("လက်ကျန် Balance");
                    textView10.setTextColor(-460545);
                    textView10.setTextSize(2, 15.0f);
                    textView10.setTypeface(null, 1);
                    textView10.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
                    linearLayout10.addView(textView10);
                    TextView textView11 = new TextView(dashboardActivity11);
                    textView11.setText(dashboardActivity11.K.isEmpty() ? "--" : dashboardActivity11.K);
                    textView11.setTextColor(-278748);
                    textView11.setTextSize(2, 17.0f);
                    textView11.setTypeface(null, 1);
                    LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams6.setMarginStart(i20);
                    textView11.setLayoutParams(layoutParams6);
                    linearLayout10.addView(textView11);
                    r2.addView(linearLayout10);
                }
                bottomSheetDialog.setOnShowListener(new ap(bottomSheetDialog));
                bottomSheetDialog.show();
                break;
            case 11:
                DashboardActivity dashboardActivity12 = (DashboardActivity) obj;
                DashboardActivity.j(dashboardActivity12, "CASHBACK", dashboardActivity12.J, -278748);
                break;
            case 12:
                DashboardActivity dashboardActivity13 = (DashboardActivity) obj;
                boolean z4 = !dashboardActivity13.f171c;
                dashboardActivity13.f171c = z4;
                dashboardActivity13.b.setText(z4 ? DashboardActivity.t(dashboardActivity13.an.f296a) : "+" + dashboardActivity13.an.f296a);
                break;
            case 13:
                int i21 = DashboardActivity.L;
                ((DashboardActivity) obj).w();
                break;
            case 14:
                DashboardActivity dashboardActivity14 = (DashboardActivity) obj;
                dashboardActivity14.af.setVisibility(0);
                dashboardActivity14.ae.setVisibility(8);
                dashboardActivity14.ah.setBackgroundResource(2131165382);
                dashboardActivity14.ag.setBackgroundResource(0);
                ImageView imageView2 = dashboardActivity14.aj;
                if (imageView2 != null) {
                    imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                }
                ImageView imageView3 = dashboardActivity14.ai;
                if (imageView3 != null) {
                    imageView3.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                }
                TextView textView12 = dashboardActivity14.al;
                if (textView12 != null) {
                    textView12.setTextColor(-1);
                    dashboardActivity14.al.setTypeface(null, 1);
                }
                TextView textView13 = dashboardActivity14.ak;
                if (textView13 != null) {
                    textView13.setTextColor(-1);
                    dashboardActivity14.ak.setTypeface(null, 0);
                }
                break;
            case 15:
                DashboardActivity dashboardActivity15 = (DashboardActivity) obj;
                dashboardActivity15.startActivity(new Intent(dashboardActivity15, (Class<?>) CityRunActivity.class));
                break;
            case 16:
                DashboardActivity dashboardActivity16 = (DashboardActivity) obj;
                boolean z5 = dashboardActivity16.f190w;
                if (!z5 && !(z2 = dashboardActivity16.f192y) && !z5 && !z2) {
                    dashboardActivity16.v(true);
                    dashboardActivity16.ap.execute(new z(dashboardActivity16, 3));
                    break;
                }
                break;
            case 17:
                DashboardActivity dashboardActivity17 = (DashboardActivity) obj;
                boolean z6 = dashboardActivity17.f191x;
                if (!z6 && !(z3 = dashboardActivity17.f193z) && !z6 && !z3) {
                    dashboardActivity17.u(true);
                    dashboardActivity17.ap.execute(new z(dashboardActivity17, 5));
                    break;
                }
                break;
            case 18:
                DashboardActivity dashboardActivity18 = (DashboardActivity) obj;
                int i22 = DashboardActivity.L;
                View viewInflate5 = dashboardActivity18.getLayoutInflater().inflate(2131427381, (ViewGroup) null);
                LinearLayout linearLayout11 = (LinearLayout) viewInflate5.findViewById(2131230829);
                TextView textView14 = (TextView) viewInflate5.findViewById(2131230821);
                LinearLayout linearLayout12 = (LinearLayout) viewInflate5.findViewById(2131230834);
                Dialog dialog4 = new Dialog(dashboardActivity18);
                dialog4.requestWindowFeature(1);
                dialog4.setContentView(viewInflate5);
                if (dialog4.getWindow() != null) {
                    dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog4.getWindow().setLayout((int) (dashboardActivity18.getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
                }
                aj ajVar = new aj(dashboardActivity18, dialog4, i5);
                textView14.setOnClickListener(ajVar);
                linearLayout12.setOnClickListener(ajVar);
                linearLayout11.setOnClickListener(new aj(dashboardActivity18, dialog4, i6));
                dialog4.show();
                break;
            default:
                DashboardActivity dashboardActivity19 = ((ac) obj).f313c.f419c;
                int i23 = DashboardActivity.L;
                dashboardActivity19.s();
                break;
        }
    }
}
