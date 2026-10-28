package com.zenoatom.app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.zenoatom.app.PointsActivity;
import f.F;
import f.G;
import f.ViewOnClickListenerC0023c;
import f.az;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class PointsActivity extends BaseActivity {
    public static final /* synthetic */ int ai = 0;
    public ImageView aa;
    public ImageView ab;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public G f230c;
    public ProgressBar d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ScrollView f231e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public TextView f232f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public TextView f233g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public TextView f234h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public ProgressBar f235i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public TextView f236j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public TextView f237k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public TextView f238l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public LinearLayout f239m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public TextView f240n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public ImageView f241o;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public LinearLayout f243q;

    /* JADX INFO: renamed from: r, reason: collision with root package name */
    public LinearLayout f244r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public LinearLayout f245s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public LinearLayout f246t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public TextView f247u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public TextView f248v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public TextView f249w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public TextView f250x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public ImageView f251y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public ImageView f252z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ExecutorService f229a = Executors.newCachedThreadPool();
    public final Handler b = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public long f242p = 0;
    public String ac = "data";
    public JSONArray ad = new JSONArray();
    public JSONArray ae = new JSONArray();
    public JSONArray af = new JSONArray();
    public JSONArray ag = new JSONArray();
    public LinearLayout ah = null;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:4:0x0067  */
    public final void j(JSONObject jSONObject) {
        int i2;
        int i3;
        int i4;
        final String strOptString = jSONObject.optString("keyword", "");
        final String strOptString2 = jSONObject.optString("title", strOptString);
        final String strOptString3 = jSONObject.optString("desc", "");
        final String strOptString4 = jSONObject.optString("category", "");
        final String strOptString5 = jSONObject.optString("partner", "");
        final String strOptString6 = jSONObject.optString("type", "");
        final long jOptLong = jSONObject.optLong("redeemPoint", 0L);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(k(12), k(12), k(12), k(12));
        linearLayout.setBackgroundResource(2131165326);
        LinearLayout linearLayout2 = new LinearLayout(this);
        String str = this.ac;
        str.getClass();
        switch (str) {
            case "sms":
                i2 = 2131165333;
                i3 = 2131165451;
                i4 = -8286984;
                break;
            case "cashback":
                i2 = 2131165331;
                i3 = 2131165459;
                i4 = -13315175;
                break;
            case "voice":
                i2 = 2131165334;
                i3 = 2131165446;
                i4 = -278748;
                break;
            default:
                i2 = 2131165332;
                i3 = 2131165461;
                i4 = -7092739;
                break;
        }
        linearLayout2.setBackgroundResource(i2);
        linearLayout2.setGravity(17);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(k(36), k(36));
        layoutParams.setMargins(0, 0, 0, k(8));
        linearLayout2.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(i3);
        imageView.setColorFilter(i4, PorterDuff.Mode.SRC_IN);
        linearLayout2.addView(imageView, new LinearLayout.LayoutParams(k(18), k(18)));
        linearLayout.addView(linearLayout2);
        TextView textView = new TextView(this);
        textView.setText(strOptString2);
        textView.setTextColor(-460545);
        textView.setTextSize(13.0f);
        textView.setTypeface(null, 1);
        textView.setMaxLines(2);
        TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
        textView.setEllipsize(truncateAt);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(0, 0, 0, k(4));
        textView.setLayoutParams(layoutParams2);
        linearLayout.addView(textView);
        if (!strOptString3.isEmpty()) {
            TextView textView2 = new TextView(this);
            textView2.setText(strOptString3);
            textView2.setTextColor(-5592406);
            textView2.setTextSize(10.0f);
            textView2.setMaxLines(2);
            textView2.setEllipsize(truncateAt);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams3.setMargins(0, 0, 0, k(6));
            textView2.setLayoutParams(layoutParams3);
            linearLayout.addView(textView2);
        }
        TextView textView3 = new TextView(this);
        textView3.setText(jOptLong + " Pts");
        textView3.setTextColor(-460545);
        textView3.setTextSize(16.0f);
        textView3.setTypeface(null, 1);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams4.setMargins(0, 0, 0, k(10));
        textView3.setLayoutParams(layoutParams4);
        linearLayout.addView(textView3);
        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        linearLayout.addView(view);
        final LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setBackgroundResource(2131165312);
        linearLayout3.setGravity(17);
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-1, k(38)));
        linearLayout3.setPadding(k(8), k(4), k(8), k(4));
        final TextView textView4 = new TextView(this);
        textView4.setText("လဲမည်");
        textView4.setTextColor(-1);
        textView4.setTextSize(13.0f);
        textView4.setTypeface(null, 1);
        linearLayout3.addView(textView4);
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: f.A
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                int i5 = PointsActivity.ai;
                final PointsActivity pointsActivity = this.f257a;
                View viewInflate = pointsActivity.getLayoutInflater().inflate(2131427389, (ViewGroup) null);
                TextView textView5 = (TextView) viewInflate.findViewById(2131231328);
                TextView textView6 = (TextView) viewInflate.findViewById(2131231327);
                TextView textView7 = (TextView) viewInflate.findViewById(2131231326);
                TextView textView8 = (TextView) viewInflate.findViewById(2131231325);
                TextView textView9 = (TextView) viewInflate.findViewById(2131231324);
                TextView textView10 = (TextView) viewInflate.findViewById(2131231323);
                LinearLayout linearLayout4 = (LinearLayout) viewInflate.findViewById(2131230832);
                LinearLayout linearLayout5 = (LinearLayout) viewInflate.findViewById(2131230824);
                LinearLayout linearLayout6 = (LinearLayout) viewInflate.findViewById(2131230840);
                final String str2 = strOptString4;
                textView5.setText(((str2 == null || str2.isEmpty()) ? "Reward" : str2).concat(" လဲလှယ်ရန် အတည်ပြုပါ"));
                final String str3 = strOptString2;
                textView6.setText(str3);
                String str4 = strOptString3;
                if (str4 == null || str4.isEmpty()) {
                    textView7.setVisibility(8);
                } else {
                    textView7.setText(str4);
                    textView7.setVisibility(0);
                }
                long j2 = jOptLong;
                textView8.setText(String.valueOf(j2));
                textView9.setText("လက်ကျန် points: " + pointsActivity.f242p + " pts");
                textView10.setText("လဲပြီးနောက်: " + (pointsActivity.f242p - j2) + " pts");
                final Dialog dialog = new Dialog(pointsActivity);
                dialog.requestWindowFeature(1);
                dialog.setContentView(viewInflate);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.getWindow().setLayout((int) (pointsActivity.getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
                }
                ViewOnClickListenerC0023c viewOnClickListenerC0023c = new ViewOnClickListenerC0023c(dialog, 7);
                linearLayout5.setOnClickListener(viewOnClickListenerC0023c);
                linearLayout6.setOnClickListener(viewOnClickListenerC0023c);
                final String str5 = strOptString5;
                final String str6 = strOptString6;
                final LinearLayout linearLayout7 = linearLayout3;
                final TextView textView11 = textView4;
                final String str7 = strOptString;
                linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: f.C

                    /* JADX INFO: renamed from: j, reason: collision with root package name */
                    public final /* synthetic */ String f282j = "လဲမည်";

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        int i6 = PointsActivity.ai;
                        final PointsActivity pointsActivity2 = pointsActivity;
                        pointsActivity2.getClass();
                        dialog.dismiss();
                        final LinearLayout linearLayout8 = linearLayout7;
                        linearLayout8.setEnabled(false);
                        final TextView textView12 = textView11;
                        if (textView12 != null) {
                            textView12.setText("...");
                        }
                        final String str8 = str2;
                        final String str9 = str7;
                        final String str10 = str5;
                        final String str11 = str6;
                        final String str12 = str3;
                        final String str13 = this.f282j;
                        pointsActivity2.f229a.execute(new Runnable() { // from class: f.D
                            @Override // java.lang.Runnable
                            public final void run() {
                                Handler handler;
                                String str14 = str8;
                                String str15 = str9;
                                String str16 = str10;
                                String str17 = str11;
                                final String str18 = str12;
                                final LinearLayout linearLayout9 = linearLayout8;
                                final TextView textView13 = textView12;
                                final String str19 = str13;
                                final PointsActivity pointsActivity3 = pointsActivity2;
                                Handler handler2 = pointsActivity3.b;
                                try {
                                    final JSONObject jSONObjectAf = w.y.af(pointsActivity3.f230c, str14, str15, str16, str17, str18);
                                    final String strOptString7 = jSONObjectAf.optString(NotificationCompat.CATEGORY_STATUS, "");
                                    handler = handler2;
                                    try {
                                        handler.post(new Runnable() { // from class: f.E
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                int i7 = PointsActivity.ai;
                                                PointsActivity pointsActivity4 = pointsActivity3;
                                                pointsActivity4.getClass();
                                                linearLayout9.setEnabled(true);
                                                TextView textView14 = textView13;
                                                if (textView14 != null) {
                                                    textView14.setText(str19);
                                                }
                                                String str20 = strOptString7;
                                                if (!"success".equals(str20) && !"ok".equalsIgnoreCase(str20)) {
                                                    JSONObject jSONObjectOptJSONObject = jSONObjectAf.optJSONObject("errors");
                                                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("message") : null;
                                                    String strOptString8 = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optString("message", null) : null;
                                                    if (strOptString8 == null || strOptString8.isEmpty()) {
                                                        strOptString8 = "လဲလှယ်မှု မအောင်မြင်ပါ";
                                                    }
                                                    pointsActivity4.m(strOptString8);
                                                    return;
                                                }
                                                String strG = androidx.appcompat.graphics.drawable.a.g(new StringBuilder(), str18, " လဲလှယ်ခြင်း အောင်မြင်သည်!");
                                                View viewInflate2 = pointsActivity4.getLayoutInflater().inflate(2131427390, (ViewGroup) null);
                                                TextView textView15 = (TextView) viewInflate2.findViewById(2131231336);
                                                TextView textView16 = (TextView) viewInflate2.findViewById(2131231335);
                                                LinearLayout linearLayout10 = (LinearLayout) viewInflate2.findViewById(2131230827);
                                                LinearLayout linearLayout11 = (LinearLayout) viewInflate2.findViewById(2131230841);
                                                textView15.setText("အောင်မြင်သည်");
                                                if (strG != null) {
                                                    textView16.setText(strG);
                                                }
                                                Dialog dialog2 = new Dialog(pointsActivity4);
                                                dialog2.requestWindowFeature(1);
                                                dialog2.setContentView(viewInflate2);
                                                if (dialog2.getWindow() != null) {
                                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                                    dialog2.getWindow().setLayout((int) (pointsActivity4.getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
                                                }
                                                ViewOnClickListenerC0023c viewOnClickListenerC0023c2 = new ViewOnClickListenerC0023c(dialog2, 9);
                                                linearLayout10.setOnClickListener(viewOnClickListenerC0023c2);
                                                linearLayout11.setOnClickListener(viewOnClickListenerC0023c2);
                                                dialog2.show();
                                                ProgressBar progressBar = pointsActivity4.d;
                                                if (progressBar != null) {
                                                    progressBar.setVisibility(0);
                                                }
                                                ScrollView scrollView = pointsActivity4.f231e;
                                                if (scrollView != null) {
                                                    scrollView.setVisibility(8);
                                                }
                                                pointsActivity4.f229a.execute(new az(pointsActivity4, 0));
                                            }
                                        });
                                    } catch (Exception e2) {
                                        e = e2;
                                        handler.post(new RunnableC0026f(pointsActivity3, linearLayout9, textView13, str19, e.getMessage() != null ? e.getMessage() : "လဲလှယ်မှု မအောင်မြင်ပါ"));
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    handler = handler2;
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
        linearLayout.addView(linearLayout3);
        if (this.ah != null) {
            LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            layoutParams5.setMargins(k(4), 0, 0, 0);
            linearLayout.setLayoutParams(layoutParams5);
            this.ah.addView(linearLayout);
            LinearLayout linearLayout4 = this.ah;
            linearLayout4.getViewTreeObserver().addOnGlobalLayoutListener(new F(linearLayout4));
            this.ah = null;
            return;
        }
        LinearLayout linearLayout5 = new LinearLayout(this);
        this.ah = linearLayout5;
        linearLayout5.setOrientation(0);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams6.setMargins(0, 0, 0, k(8));
        this.ah.setLayoutParams(layoutParams6);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(0, -2, 1.0f);
        layoutParams7.setMargins(0, 0, k(4), 0);
        linearLayout.setLayoutParams(layoutParams7);
        this.ah.addView(linearLayout);
        LinearLayout linearLayout6 = this.f239m;
        if (linearLayout6 != null) {
            linearLayout6.addView(this.ah);
        }
    }

    public final int k(int i2) {
        return (int) (i2 * getResources().getDisplayMetrics().density);
    }

    public final void l() {
        JSONArray jSONArray;
        LinearLayout linearLayout = this.f239m;
        if (linearLayout == null) {
            return;
        }
        linearLayout.removeAllViews();
        this.ah = null;
        n();
        String str = this.ac;
        str.getClass();
        switch (str) {
            case "sms":
                jSONArray = this.ag;
                break;
            case "cashback":
                jSONArray = this.af;
                break;
            case "voice":
                jSONArray = this.ae;
                break;
            default:
                jSONArray = this.ad;
                break;
        }
        if (jSONArray == null || jSONArray.length() == 0) {
            TextView textView = new TextView(this);
            textView.setText("ရရှိနိုင်သော rewards မရှိပါ");
            textView.setTextColor(-6511697);
            textView.setTextSize(14.0f);
            textView.setGravity(17);
            textView.setPadding(0, k(32), 0, k(32));
            this.f239m.addView(textView);
            return;
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                j(jSONArray.getJSONObject(i2));
            } catch (Exception unused) {
            }
        }
        if (this.ah != null) {
            View view = new View(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
            layoutParams.setMargins(k(4), 0, 0, 0);
            view.setLayoutParams(layoutParams);
            this.ah.addView(view);
            this.ah = null;
        }
    }

    public final void m(String str) {
        View viewInflate = getLayoutInflater().inflate(2131427386, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(2131231301);
        TextView textView2 = (TextView) viewInflate.findViewById(2131231300);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230826);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(2131230837);
        textView.setText("မအောင်မြင်ပါ");
        if (str != null) {
            textView2.setText(str);
        }
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(viewInflate);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
        }
        ViewOnClickListenerC0023c viewOnClickListenerC0023c = new ViewOnClickListenerC0023c(dialog, 8);
        linearLayout.setOnClickListener(viewOnClickListenerC0023c);
        linearLayout2.setOnClickListener(viewOnClickListenerC0023c);
        dialog.show();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:89:0x011e  */
    public final void n() {
        int i2;
        TextView textView;
        String str;
        LinearLayout linearLayout = this.f243q;
        if (linearLayout != null) {
            linearLayout.setBackgroundResource("data".equals(this.ac) ? 2131165385 : 2131165386);
        }
        LinearLayout linearLayout2 = this.f244r;
        if (linearLayout2 != null) {
            linearLayout2.setBackgroundResource("voice".equals(this.ac) ? 2131165385 : 2131165386);
        }
        LinearLayout linearLayout3 = this.f245s;
        if (linearLayout3 != null) {
            linearLayout3.setBackgroundResource("cashback".equals(this.ac) ? 2131165385 : 2131165386);
        }
        LinearLayout linearLayout4 = this.f246t;
        if (linearLayout4 != null) {
            linearLayout4.setBackgroundResource("sms".equals(this.ac) ? 2131165385 : 2131165386);
        }
        TextView textView2 = this.f247u;
        if (textView2 != null) {
            textView2.setTextColor("data".equals(this.ac) ? -1 : -9735552);
        }
        TextView textView3 = this.f248v;
        if (textView3 != null) {
            textView3.setTextColor("voice".equals(this.ac) ? -1 : -9735552);
        }
        TextView textView4 = this.f249w;
        if (textView4 != null) {
            textView4.setTextColor("cashback".equals(this.ac) ? -1 : -9735552);
        }
        TextView textView5 = this.f250x;
        if (textView5 != null) {
            textView5.setTextColor("sms".equals(this.ac) ? -1 : -9735552);
        }
        ImageView imageView = this.f251y;
        if (imageView != null) {
            imageView.setColorFilter("data".equals(this.ac) ? -1 : -9735552, PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView2 = this.f252z;
        if (imageView2 != null) {
            imageView2.setColorFilter("voice".equals(this.ac) ? -1 : -9735552, PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView3 = this.aa;
        if (imageView3 != null) {
            imageView3.setColorFilter("cashback".equals(this.ac) ? -1 : -9735552, PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView4 = this.ab;
        if (imageView4 != null) {
            imageView4.setColorFilter("sms".equals(this.ac) ? -1 : -9735552, PorterDuff.Mode.SRC_IN);
        }
        if (this.f240n != null) {
            String str2 = this.ac;
            str2.getClass();
            switch (str2) {
                case "sms":
                    textView = this.f240n;
                    str = "POINTS ဖြင့် SMS လဲလှယ်နန်";
                    break;
                case "data":
                    textView = this.f240n;
                    str = "POINTS ဖြင့် DATA လဲလှယ်နန်";
                    break;
                case "cashback":
                    textView = this.f240n;
                    str = "POINTS ဖြင့် CASHBACK လဲလှယ်နန်";
                    break;
                case "voice":
                    textView = this.f240n;
                    str = "POINTS ဖြင့် VOICE လဲလှယ်နန်";
                    break;
            }
            textView.setText(str);
        }
        if (this.f241o != null) {
            String str3 = this.ac;
            str3.getClass();
            switch (str3) {
                case "sms":
                    i2 = -8286984;
                    break;
                case "cashback":
                    i2 = -13315175;
                    break;
                case "voice":
                    i2 = -278748;
                    break;
                default:
                    i2 = -14498466;
                    break;
            }
            this.f241o.setColorFilter(i2, PorterDuff.Mode.SRC_IN);
            TextView textView6 = this.f240n;
            if (textView6 != null) {
                textView6.setTextColor(i2);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        G gV = y.v(this);
        if (gV == null) {
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            finish();
            return;
        }
        this.f230c = gV;
        setContentView(2131427362);
        LinearLayout linearLayout = (LinearLayout) findViewById(2131230816);
        final int i2 = 0;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: f.ay
                public final /* synthetic */ PointsActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i3 = i2;
                    PointsActivity pointsActivity = this.b;
                    switch (i3) {
                        case 0:
                            int i4 = PointsActivity.ai;
                            pointsActivity.finish();
                            break;
                        case 1:
                            int i5 = PointsActivity.ai;
                            pointsActivity.ac = "data";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 2:
                            int i6 = PointsActivity.ai;
                            pointsActivity.ac = "voice";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 3:
                            int i7 = PointsActivity.ai;
                            pointsActivity.ac = "cashback";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        default:
                            int i8 = PointsActivity.ai;
                            pointsActivity.ac = "sms";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                    }
                }
            });
        }
        this.d = (ProgressBar) findViewById(2131231146);
        this.f231e = (ScrollView) findViewById(2131230895);
        this.f232f = (TextView) findViewById(2131231332);
        this.f233g = (TextView) findViewById(2131231319);
        this.f234h = (TextView) findViewById(2131231303);
        this.f235i = (ProgressBar) findViewById(2131231266);
        this.f236j = (TextView) findViewById(2131231342);
        this.f237k = (TextView) findViewById(2131231341);
        this.f238l = (TextView) findViewById(2131231343);
        this.f239m = (LinearLayout) findViewById(2131231155);
        this.f240n = (TextView) findViewById(2131231330);
        this.f241o = (ImageView) findViewById(2131231009);
        this.f243q = (LinearLayout) findViewById(2131231225);
        this.f244r = (LinearLayout) findViewById(2131231228);
        this.f245s = (LinearLayout) findViewById(2131231224);
        this.f246t = (LinearLayout) findViewById(2131231227);
        this.f247u = (TextView) findViewById(2131231338);
        this.f248v = (TextView) findViewById(2131231340);
        this.f249w = (TextView) findViewById(2131231337);
        this.f250x = (TextView) findViewById(2131231339);
        this.f251y = (ImageView) findViewById(2131230988);
        this.f252z = (ImageView) findViewById(2131230994);
        this.aa = (ImageView) findViewById(2131230987);
        this.ab = (ImageView) findViewById(2131230991);
        LinearLayout linearLayout2 = this.f243q;
        if (linearLayout2 != null) {
            final int i3 = 1;
            linearLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: f.ay
                public final /* synthetic */ PointsActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i4 = i3;
                    PointsActivity pointsActivity = this.b;
                    switch (i4) {
                        case 0:
                            int i5 = PointsActivity.ai;
                            pointsActivity.finish();
                            break;
                        case 1:
                            int i6 = PointsActivity.ai;
                            pointsActivity.ac = "data";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 2:
                            int i7 = PointsActivity.ai;
                            pointsActivity.ac = "voice";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 3:
                            int i8 = PointsActivity.ai;
                            pointsActivity.ac = "cashback";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        default:
                            int i9 = PointsActivity.ai;
                            pointsActivity.ac = "sms";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                    }
                }
            });
        }
        LinearLayout linearLayout3 = this.f244r;
        if (linearLayout3 != null) {
            final int i4 = 2;
            linearLayout3.setOnClickListener(new View.OnClickListener(this) { // from class: f.ay
                public final /* synthetic */ PointsActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i5 = i4;
                    PointsActivity pointsActivity = this.b;
                    switch (i5) {
                        case 0:
                            int i6 = PointsActivity.ai;
                            pointsActivity.finish();
                            break;
                        case 1:
                            int i7 = PointsActivity.ai;
                            pointsActivity.ac = "data";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 2:
                            int i8 = PointsActivity.ai;
                            pointsActivity.ac = "voice";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 3:
                            int i9 = PointsActivity.ai;
                            pointsActivity.ac = "cashback";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        default:
                            int i10 = PointsActivity.ai;
                            pointsActivity.ac = "sms";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                    }
                }
            });
        }
        LinearLayout linearLayout4 = this.f245s;
        if (linearLayout4 != null) {
            final int i5 = 3;
            linearLayout4.setOnClickListener(new View.OnClickListener(this) { // from class: f.ay
                public final /* synthetic */ PointsActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i6 = i5;
                    PointsActivity pointsActivity = this.b;
                    switch (i6) {
                        case 0:
                            int i7 = PointsActivity.ai;
                            pointsActivity.finish();
                            break;
                        case 1:
                            int i8 = PointsActivity.ai;
                            pointsActivity.ac = "data";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 2:
                            int i9 = PointsActivity.ai;
                            pointsActivity.ac = "voice";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 3:
                            int i10 = PointsActivity.ai;
                            pointsActivity.ac = "cashback";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        default:
                            int i11 = PointsActivity.ai;
                            pointsActivity.ac = "sms";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                    }
                }
            });
        }
        LinearLayout linearLayout5 = this.f246t;
        if (linearLayout5 != null) {
            final int i6 = 4;
            linearLayout5.setOnClickListener(new View.OnClickListener(this) { // from class: f.ay
                public final /* synthetic */ PointsActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i7 = i6;
                    PointsActivity pointsActivity = this.b;
                    switch (i7) {
                        case 0:
                            int i8 = PointsActivity.ai;
                            pointsActivity.finish();
                            break;
                        case 1:
                            int i9 = PointsActivity.ai;
                            pointsActivity.ac = "data";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 2:
                            int i10 = PointsActivity.ai;
                            pointsActivity.ac = "voice";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        case 3:
                            int i11 = PointsActivity.ai;
                            pointsActivity.ac = "cashback";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                        default:
                            int i12 = PointsActivity.ai;
                            pointsActivity.ac = "sms";
                            pointsActivity.n();
                            pointsActivity.l();
                            break;
                    }
                }
            });
        }
        n();
        ProgressBar progressBar = this.d;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        ScrollView scrollView = this.f231e;
        if (scrollView != null) {
            scrollView.setVisibility(8);
        }
        this.f229a.execute(new az(this, i2));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.f229a.shutdownNow();
    }
}
