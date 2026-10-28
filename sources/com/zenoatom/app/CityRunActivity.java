package com.zenoatom.app;

import android.R;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.inputmethod.a;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.snackbar.Snackbar;
import com.zenoatom.app.CityRunActivity;
import f.RunnableC0026f;
import f.RunnableC0028h;
import f.RunnableC0029i;
import f.ViewOnClickListenerC0023c;
import f.ViewOnClickListenerC0024d;
import f.ViewOnClickListenerC0025e;
import f.o;
import f.p;
import f.r;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class CityRunActivity extends BaseActivity {

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public static final /* synthetic */ int f151p = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public LinearLayout f152a;
    public LinearLayout b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public WebView f153c;
    public View d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public LinearLayout f154e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public TextView f155f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public View f156g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public SwipeRefreshLayout f157h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final LinkedHashMap f158i = new LinkedHashMap();

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final LinkedHashMap f159j = new LinkedHashMap();

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final LinkedHashMap f160k = new LinkedHashMap();

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final Handler f161l = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final ExecutorService f162m = Executors.newCachedThreadPool();

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public volatile boolean f163n = false;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public o f164o;

    public static boolean j(CityRunActivity cityRunActivity, String str) {
        String strGroup;
        if (cityRunActivity.f163n) {
            return true;
        }
        int i2 = 0;
        if (str.contains("msisdn=")) {
            try {
                strGroup = Uri.parse(str).getQueryParameter("msisdn");
            } catch (Exception unused) {
                strGroup = null;
            }
            if (strGroup == null || strGroup.isEmpty()) {
                Matcher matcher = Pattern.compile("[?&]msisdn=([^&\\s#]+)").matcher(str);
                if (matcher.find()) {
                    try {
                        strGroup = URLDecoder.decode(matcher.group(1), "UTF-8");
                    } catch (Exception unused2) {
                        strGroup = matcher.group(1);
                    }
                }
            }
            if (strGroup != null && !strGroup.isEmpty()) {
                cityRunActivity.f163n = true;
                cityRunActivity.f161l.post(new RunnableC0028h(cityRunActivity, 0));
                cityRunActivity.f162m.execute(new RunnableC0029i(cityRunActivity, strGroup, i2));
                return true;
            }
        }
        return false;
    }

    public static String o(String str) {
        if (str == null || str.length() < 6) {
            return str != null ? str : "Unknown";
        }
        return str.substring(0, Math.min(4, str.length())) + "****" + str.substring(Math.max(0, str.length() - 3));
    }

    /* JADX WARN: Code duplicated, block: B:112:0x03db  */
    /* JADX WARN: Code duplicated, block: B:115:0x0400  */
    /* JADX WARN: Code duplicated, block: B:117:0x0404  */
    /* JADX WARN: Code duplicated, block: B:128:0x046e  */
    /* JADX WARN: Code duplicated, block: B:132:0x048c  */
    /* JADX WARN: Code duplicated, block: B:133:0x0494  */
    /* JADX WARN: Code duplicated, block: B:17:0x007a  */
    /* JADX WARN: Code duplicated, block: B:84:0x02b4  */
    public final void k(View view, final String str) {
        String str2;
        byte b;
        String strValueOf;
        String str3;
        int i2;
        String str4;
        LinearLayout linearLayout;
        JSONArray jSONArray;
        ArrayList arrayList;
        LinearLayout linearLayout2;
        JSONArray jSONArray2;
        ArrayList arrayList2;
        r rVar;
        LinearLayout linearLayout3;
        int i3;
        LinearLayout linearLayout4;
        LinearLayout linearLayout5;
        final int i4;
        int i5;
        int iM;
        int i6;
        String str5 = "";
        r rVar2 = (r) this.f158i.get(str);
        if (rVar2 == null) {
            return;
        }
        ((TextView) view.findViewById(2131231310)).setText(o(rVar2.f385a));
        TextView textView = (TextView) view.findViewById(2131231334);
        String str6 = rVar2.f386c;
        if (str6 == null || str6.isEmpty()) {
            str2 = "cityrun.pro";
        } else {
            str2 = "SUBSCRIBER  ·  ID:" + rVar2.f386c;
        }
        textView.setText(str2);
        TextView textView2 = (TextView) view.findViewById(2131231333);
        String str7 = rVar2.f403u;
        str7.getClass();
        int iHashCode = str7.hashCode();
        if (iHashCode != -1884319283) {
            if (iHashCode != 96784904) {
                if (iHashCode == 1550783935 && str7.equals("running")) {
                    b = 2;
                } else {
                    b = -1;
                }
            } else if (str7.equals("error")) {
                b = 1;
            } else {
                b = -1;
            }
        } else if (str7.equals("stopped")) {
            b = 0;
        } else {
            b = -1;
        }
        if (b != 0) {
            if (b == 1) {
                textView2.setVisibility(0);
                textView2.setText("ERROR");
                textView2.setTextColor(-1096636);
                i6 = 2131165351;
            } else if (b != 2) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
                textView2.setText("RUNNING");
                textView2.setTextColor(-8635667);
                i6 = 2131165353;
            }
            textView2.setBackgroundResource(i6);
        } else {
            textView2.setVisibility(0);
            textView2.setText("STOPPED");
            textView2.setTextColor(-16337196);
            textView2.setBackgroundResource(2131165352);
        }
        TextView textView3 = (TextView) view.findViewById(2131231299);
        int i7 = rVar2.f401s;
        if (i7 >= 1000000) {
            strValueOf = String.format("%.1fM", Float.valueOf(i7 / 1000000.0f));
        } else {
            strValueOf = i7 >= 1000 ? String.format("%.1fK", Float.valueOf(i7 / 1000.0f)) : String.valueOf(i7);
        }
        textView3.setText(strValueOf);
        ((TextView) view.findViewById(2131231329)).setText(String.valueOf(rVar2.f402t));
        ((TextView) view.findViewById(2131231309)).setText(String.valueOf(rVar2.f398p));
        LinearLayout linearLayout6 = (LinearLayout) view.findViewById(2131231027);
        TextView textView4 = (TextView) view.findViewById(2131231287);
        ImageView imageView = (ImageView) view.findViewById(2131231007);
        if (!rVar2.f389g) {
            if (!"error".equals(rVar2.f403u) || (str3 = rVar2.f404v) == null || str3.isEmpty()) {
                linearLayout6.setVisibility(8);
            } else {
                linearLayout6.setVisibility(0);
                linearLayout6.setBackgroundResource(2131165336);
                imageView.setImageResource(2131165428);
                i2 = -495247;
                imageView.setColorFilter(-495247, PorterDuff.Mode.SRC_IN);
                str4 = rVar2.f404v;
            }
            linearLayout = (LinearLayout) view.findViewById(2131231031);
            linearLayout.removeAllViews();
            jSONArray = rVar2.f406x;
            arrayList = rVar2.f394l;
            if (jSONArray != null || jSONArray.length() <= 0) {
                linearLayout.setVisibility(8);
            } else {
                linearLayout.setVisibility(0);
                int i8 = 0;
                while (i8 < rVar2.f406x.length()) {
                    try {
                        JSONObject jSONObject = rVar2.f406x.getJSONObject(i8);
                        jSONObject.optString("milestone_datapack_value", str5);
                        boolean z2 = rVar2.f389g || "1".equals(jSONObject.optString("milestone_claimed", "0")) || arrayList.contains(jSONObject.optString("milestone_id", str5));
                        LinearLayout linearLayout7 = new LinearLayout(this);
                        linearLayout7.setOrientation(1);
                        String str8 = str5;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                        if (i8 < rVar2.f406x.length() - 1) {
                            layoutParams.setMargins(0, 0, m(4), 0);
                        }
                        linearLayout7.setLayoutParams(layoutParams);
                        ImageView imageView2 = new ImageView(this);
                        imageView2.setImageResource(2131165412);
                        imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        int iM2 = m(18);
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(iM2, iM2);
                        layoutParams2.gravity = 1;
                        layoutParams2.setMargins(0, 0, 0, m(3));
                        imageView2.setLayoutParams(layoutParams2);
                        linearLayout7.setGravity(1);
                        linearLayout7.addView(imageView2);
                        LinearLayout linearLayout8 = new LinearLayout(this);
                        linearLayout8.setBackgroundResource(2131165348);
                        linearLayout8.setLayoutParams(new LinearLayout.LayoutParams(-1, m(4)));
                        View view2 = new View(this);
                        view2.setBackgroundResource(z2 ? 2131165346 : 2131165347);
                        view2.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                        linearLayout8.addView(view2);
                        linearLayout7.addView(linearLayout8);
                        linearLayout.addView(linearLayout7);
                        i8++;
                        str5 = str8;
                    } catch (Exception unused) {
                    }
                }
            }
            linearLayout2 = (LinearLayout) view.findViewById(2131231029);
            LinearLayout linearLayout9 = (LinearLayout) view.findViewById(2131231030);
            TextView textView5 = (TextView) view.findViewById(2131231302);
            linearLayout9.removeAllViews();
            jSONArray2 = rVar2.f407y;
            if (jSONArray2 != null || jSONArray2.length() <= 0) {
                arrayList2 = arrayList;
                rVar = rVar2;
                linearLayout2.setVisibility(8);
            } else {
                linearLayout2.setVisibility(0);
                textView5.setText(rVar2.f401s + " Diamond");
                try {
                    int length = rVar2.f407y.length();
                    int i9 = 0;
                    while (i9 < length) {
                        LinearLayout linearLayout10 = new LinearLayout(this);
                        linearLayout10.setOrientation(0);
                        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
                        int i10 = i9 + 2;
                        if (i10 < length) {
                            try {
                                iM = m(8);
                            } catch (Exception unused2) {
                            }
                        } else {
                            iM = 0;
                        }
                        layoutParams3.setMargins(0, 0, 0, iM);
                        linearLayout10.setLayoutParams(layoutParams3);
                        JSONObject jSONObject2 = rVar2.f407y.getJSONObject(i9);
                        int i11 = i9;
                        arrayList2 = arrayList;
                        rVar = rVar2;
                        try {
                            linearLayout10.addView(l(jSONObject2.optInt("coins_threshold_value", 0), jSONObject2.optInt("coins_runs", 0), jSONObject2.optInt("coins_id", 0), rVar2.f401s, rVar2.f387e, str));
                            int i12 = i11 + 1;
                            if (i12 < length) {
                                View view3 = new View(this);
                                view3.setLayoutParams(new LinearLayout.LayoutParams(m(8), 1));
                                linearLayout10.addView(view3);
                                JSONObject jSONObject3 = rVar.f407y.getJSONObject(i12);
                                linearLayout10.addView(l(jSONObject3.optInt("coins_threshold_value", 0), jSONObject3.optInt("coins_runs", 0), jSONObject3.optInt("coins_id", 0), rVar.f401s, rVar.f387e, str));
                            } else {
                                View view4 = new View(this);
                                view4.setLayoutParams(new LinearLayout.LayoutParams(0, 1, 1.0f));
                                linearLayout10.addView(view4);
                            }
                            linearLayout9.addView(linearLayout10);
                            rVar2 = rVar;
                            i9 = i10;
                            arrayList = arrayList2;
                        } catch (Exception unused3) {
                            linearLayout3 = (LinearLayout) view.findViewById(2131231028);
                            TextView textView6 = (TextView) view.findViewById(2131231294);
                            TextView textView7 = (TextView) view.findViewById(2131231295);
                            if (linearLayout3 == null) {
                                i3 = 8;
                            } else {
                                if (rVar.f387e) {
                                }
                                i3 = 8;
                                linearLayout3.setVisibility(8);
                            }
                            linearLayout4 = (LinearLayout) view.findViewById(2131230853);
                            linearLayout5 = (LinearLayout) view.findViewById(2131230854);
                            if (rVar.f387e) {
                                linearLayout4.setVisibility(i3);
                                i4 = 0;
                                linearLayout5.setVisibility(0);
                            } else {
                                i4 = 0;
                                linearLayout4.setVisibility(0);
                                linearLayout5.setVisibility(i3);
                            }
                            linearLayout4.setOnClickListener(new View.OnClickListener(this) { // from class: f.j
                                public final /* synthetic */ CityRunActivity b;

                                {
                                    this.b = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view5) {
                                    int i13 = i4;
                                    int i14 = 0;
                                    String str9 = str;
                                    CityRunActivity cityRunActivity = this.b;
                                    switch (i13) {
                                        case 0:
                                            r rVar3 = (r) cityRunActivity.f158i.get(str9);
                                            if (rVar3 != null) {
                                                cityRunActivity.f162m.execute(new l(cityRunActivity, rVar3, str9, i14));
                                                break;
                                            }
                                            break;
                                        default:
                                            r rVar4 = (r) cityRunActivity.f158i.get(str9);
                                            if (rVar4 != null) {
                                                rVar4.f387e = false;
                                                rVar4.f403u = "stopped";
                                                rVar4.a("info", "Bot stopped");
                                                Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str9);
                                                Handler handler = cityRunActivity.f161l;
                                                if (runnable != null) {
                                                    handler.removeCallbacks(runnable);
                                                }
                                                cityRunActivity.r();
                                                cityRunActivity.u(str9);
                                                o oVar = cityRunActivity.f164o;
                                                if (oVar != null) {
                                                    handler.removeCallbacks(oVar);
                                                    cityRunActivity.f164o = null;
                                                }
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            final int i13 = 1;
                            linearLayout5.setOnClickListener(new View.OnClickListener(this) { // from class: f.j
                                public final /* synthetic */ CityRunActivity b;

                                {
                                    this.b = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view5) {
                                    int i14 = i13;
                                    int i15 = 0;
                                    String str9 = str;
                                    CityRunActivity cityRunActivity = this.b;
                                    switch (i14) {
                                        case 0:
                                            r rVar3 = (r) cityRunActivity.f158i.get(str9);
                                            if (rVar3 != null) {
                                                cityRunActivity.f162m.execute(new l(cityRunActivity, rVar3, str9, i15));
                                                break;
                                            }
                                            break;
                                        default:
                                            r rVar4 = (r) cityRunActivity.f158i.get(str9);
                                            if (rVar4 != null) {
                                                rVar4.f387e = false;
                                                rVar4.f403u = "stopped";
                                                rVar4.a("info", "Bot stopped");
                                                Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str9);
                                                Handler handler = cityRunActivity.f161l;
                                                if (runnable != null) {
                                                    handler.removeCallbacks(runnable);
                                                }
                                                cityRunActivity.r();
                                                cityRunActivity.u(str9);
                                                o oVar = cityRunActivity.f164o;
                                                if (oVar != null) {
                                                    handler.removeCallbacks(oVar);
                                                    cityRunActivity.f164o = null;
                                                }
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                        }
                    }
                    arrayList2 = arrayList;
                    rVar = rVar2;
                } catch (Exception unused4) {
                    arrayList2 = arrayList;
                    rVar = rVar2;
                }
            }
            linearLayout3 = (LinearLayout) view.findViewById(2131231028);
            TextView textView8 = (TextView) view.findViewById(2131231294);
            TextView textView9 = (TextView) view.findViewById(2131231295);
            if (linearLayout3 == null) {
                i3 = 8;
            } else if (rVar.f387e || rVar.f389g) {
                i3 = 8;
                linearLayout3.setVisibility(8);
            } else {
                linearLayout3.setVisibility(0);
                long jMax = Math.max(0L, n(arrayList2.size()) - ((System.currentTimeMillis() / 1000) - rVar.f393k));
                if (textView8 == null) {
                    i5 = 1;
                } else if (jMax == 0) {
                    textView8.setText("CLAIMING...");
                    i5 = 1;
                } else {
                    i5 = 1;
                    textView8.setText(String.format("%02d:%02d", Long.valueOf(jMax / 60), Long.valueOf(jMax % 60)));
                }
                if (textView9 != null) {
                    textView9.setText("Milestone " + (arrayList2.size() + i5) + " ကို စောင့်နေသည်");
                }
                i3 = 8;
            }
            linearLayout4 = (LinearLayout) view.findViewById(2131230853);
            linearLayout5 = (LinearLayout) view.findViewById(2131230854);
            if (rVar.f387e) {
                linearLayout4.setVisibility(i3);
                i4 = 0;
                linearLayout5.setVisibility(0);
            } else {
                i4 = 0;
                linearLayout4.setVisibility(0);
                linearLayout5.setVisibility(i3);
            }
            linearLayout4.setOnClickListener(new View.OnClickListener(this) { // from class: f.j
                public final /* synthetic */ CityRunActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view5) {
                    int i14 = i4;
                    int i15 = 0;
                    String str9 = str;
                    CityRunActivity cityRunActivity = this.b;
                    switch (i14) {
                        case 0:
                            r rVar3 = (r) cityRunActivity.f158i.get(str9);
                            if (rVar3 != null) {
                                cityRunActivity.f162m.execute(new l(cityRunActivity, rVar3, str9, i15));
                                break;
                            }
                            break;
                        default:
                            r rVar4 = (r) cityRunActivity.f158i.get(str9);
                            if (rVar4 != null) {
                                rVar4.f387e = false;
                                rVar4.f403u = "stopped";
                                rVar4.a("info", "Bot stopped");
                                Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str9);
                                Handler handler = cityRunActivity.f161l;
                                if (runnable != null) {
                                    handler.removeCallbacks(runnable);
                                }
                                cityRunActivity.r();
                                cityRunActivity.u(str9);
                                o oVar = cityRunActivity.f164o;
                                if (oVar != null) {
                                    handler.removeCallbacks(oVar);
                                    cityRunActivity.f164o = null;
                                }
                                break;
                            }
                            break;
                    }
                }
            });
            final int i14 = 1;
            linearLayout5.setOnClickListener(new View.OnClickListener(this) { // from class: f.j
                public final /* synthetic */ CityRunActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view5) {
                    int i15 = i14;
                    int i16 = 0;
                    String str9 = str;
                    CityRunActivity cityRunActivity = this.b;
                    switch (i15) {
                        case 0:
                            r rVar3 = (r) cityRunActivity.f158i.get(str9);
                            if (rVar3 != null) {
                                cityRunActivity.f162m.execute(new l(cityRunActivity, rVar3, str9, i16));
                                break;
                            }
                            break;
                        default:
                            r rVar4 = (r) cityRunActivity.f158i.get(str9);
                            if (rVar4 != null) {
                                rVar4.f387e = false;
                                rVar4.f403u = "stopped";
                                rVar4.a("info", "Bot stopped");
                                Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str9);
                                Handler handler = cityRunActivity.f161l;
                                if (runnable != null) {
                                    handler.removeCallbacks(runnable);
                                }
                                cityRunActivity.r();
                                cityRunActivity.u(str9);
                                o oVar = cityRunActivity.f164o;
                                if (oVar != null) {
                                    handler.removeCallbacks(oVar);
                                    cityRunActivity.f164o = null;
                                }
                                break;
                            }
                            break;
                    }
                }
            });
        }
        linearLayout6.setVisibility(0);
        linearLayout6.setBackgroundResource(2131165337);
        imageView.setImageResource(2131165421);
        i2 = -9508937;
        imageView.setColorFilter(-9508937, PorterDuff.Mode.SRC_IN);
        str4 = "ယနေ့ Milestones Claim ပြီး — မနက်ဖြန် ပြန်ရမည်";
        textView4.setText(str4);
        textView4.setTextColor(i2);
        linearLayout = (LinearLayout) view.findViewById(2131231031);
        linearLayout.removeAllViews();
        jSONArray = rVar2.f406x;
        arrayList = rVar2.f394l;
        if (jSONArray != null) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(8);
        }
        linearLayout2 = (LinearLayout) view.findViewById(2131231029);
        LinearLayout linearLayout11 = (LinearLayout) view.findViewById(2131231030);
        TextView textView10 = (TextView) view.findViewById(2131231302);
        linearLayout11.removeAllViews();
        jSONArray2 = rVar2.f407y;
        if (jSONArray2 != null) {
            arrayList2 = arrayList;
            rVar = rVar2;
            linearLayout2.setVisibility(8);
        } else {
            arrayList2 = arrayList;
            rVar = rVar2;
            linearLayout2.setVisibility(8);
        }
        linearLayout3 = (LinearLayout) view.findViewById(2131231028);
        TextView textView11 = (TextView) view.findViewById(2131231294);
        TextView textView12 = (TextView) view.findViewById(2131231295);
        if (linearLayout3 == null) {
            i3 = 8;
        } else {
            if (rVar.f387e) {
            }
            i3 = 8;
            linearLayout3.setVisibility(8);
        }
        linearLayout4 = (LinearLayout) view.findViewById(2131230853);
        linearLayout5 = (LinearLayout) view.findViewById(2131230854);
        if (rVar.f387e) {
            linearLayout4.setVisibility(i3);
            i4 = 0;
            linearLayout5.setVisibility(0);
        } else {
            i4 = 0;
            linearLayout4.setVisibility(0);
            linearLayout5.setVisibility(i3);
        }
        linearLayout4.setOnClickListener(new View.OnClickListener(this) { // from class: f.j
            public final /* synthetic */ CityRunActivity b;

            {
                this.b = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                int i15 = i4;
                int i16 = 0;
                String str9 = str;
                CityRunActivity cityRunActivity = this.b;
                switch (i15) {
                    case 0:
                        r rVar3 = (r) cityRunActivity.f158i.get(str9);
                        if (rVar3 != null) {
                            cityRunActivity.f162m.execute(new l(cityRunActivity, rVar3, str9, i16));
                            break;
                        }
                        break;
                    default:
                        r rVar4 = (r) cityRunActivity.f158i.get(str9);
                        if (rVar4 != null) {
                            rVar4.f387e = false;
                            rVar4.f403u = "stopped";
                            rVar4.a("info", "Bot stopped");
                            Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str9);
                            Handler handler = cityRunActivity.f161l;
                            if (runnable != null) {
                                handler.removeCallbacks(runnable);
                            }
                            cityRunActivity.r();
                            cityRunActivity.u(str9);
                            o oVar = cityRunActivity.f164o;
                            if (oVar != null) {
                                handler.removeCallbacks(oVar);
                                cityRunActivity.f164o = null;
                            }
                            break;
                        }
                        break;
                }
            }
        });
        final int i15 = 1;
        linearLayout5.setOnClickListener(new View.OnClickListener(this) { // from class: f.j
            public final /* synthetic */ CityRunActivity b;

            {
                this.b = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view5) {
                int i16 = i15;
                int i17 = 0;
                String str9 = str;
                CityRunActivity cityRunActivity = this.b;
                switch (i16) {
                    case 0:
                        r rVar3 = (r) cityRunActivity.f158i.get(str9);
                        if (rVar3 != null) {
                            cityRunActivity.f162m.execute(new l(cityRunActivity, rVar3, str9, i17));
                            break;
                        }
                        break;
                    default:
                        r rVar4 = (r) cityRunActivity.f158i.get(str9);
                        if (rVar4 != null) {
                            rVar4.f387e = false;
                            rVar4.f403u = "stopped";
                            rVar4.a("info", "Bot stopped");
                            Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str9);
                            Handler handler = cityRunActivity.f161l;
                            if (runnable != null) {
                                handler.removeCallbacks(runnable);
                            }
                            cityRunActivity.r();
                            cityRunActivity.u(str9);
                            o oVar = cityRunActivity.f164o;
                            if (oVar != null) {
                                handler.removeCallbacks(oVar);
                                cityRunActivity.f164o = null;
                            }
                            break;
                        }
                        break;
                }
            }
        });
    }

    public final LinearLayout l(final int i2, final int i3, final int i4, int i5, boolean z2, final String str) {
        boolean z3 = i5 >= i2;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        linearLayout.setBackgroundResource(2131165340);
        linearLayout.setTag("theme_card_inner");
        linearLayout.setPadding(m(10), m(14), m(10), m(12));
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(17);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(0, 0, 0, m(3));
        linearLayout2.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(2131165427);
        imageView.clearColorFilter();
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(m(14), m(14));
        layoutParams2.gravity = 16;
        layoutParams2.setMargins(0, 0, m(4), 0);
        imageView.setLayoutParams(layoutParams2);
        linearLayout2.addView(imageView);
        TextView textView = new TextView(this);
        textView.setText(String.valueOf(i2));
        textView.setTextColor(-278748);
        textView.setTextSize(15.0f);
        Typeface typeface = Typeface.MONOSPACE;
        textView.setTypeface(typeface, 1);
        linearLayout2.addView(textView);
        linearLayout.addView(linearLayout2);
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(17);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.setMargins(0, 0, 0, m(10));
        linearLayout3.setLayoutParams(layoutParams3);
        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(2131165447);
        imageView2.clearColorFilter();
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(m(11), m(11));
        layoutParams4.gravity = 16;
        layoutParams4.setMargins(0, 0, m(3), 0);
        imageView2.setLayoutParams(layoutParams4);
        linearLayout3.addView(imageView2);
        TextView textView2 = new TextView(this);
        textView2.setText(i3 + " runs");
        textView2.setTextColor(-10443270);
        textView2.setTextSize(11.0f);
        textView2.setTypeface(typeface, 1);
        linearLayout3.addView(textView2);
        linearLayout.addView(linearLayout3);
        final TextView textView3 = new TextView(this);
        textView3.setText("REDEEM");
        textView3.setTextColor(-278748);
        textView3.setTextSize(9.0f);
        textView3.setTypeface(null, 1);
        textView3.setBackgroundResource(2131165349);
        textView3.setGravity(17);
        textView3.setPadding(m(6), m(6), m(6), m(6));
        textView3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        textView3.setAlpha(z3 ? 1.0f : 0.35f);
        textView3.setEnabled(z3 && !z2);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: f.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final CityRunActivity cityRunActivity = this.f370a;
                LinkedHashMap linkedHashMap = cityRunActivity.f158i;
                final String str2 = str;
                final r rVar = (r) linkedHashMap.get(str2);
                if (rVar == null) {
                    return;
                }
                TextView textView4 = textView3;
                textView4.setEnabled(false);
                textView4.setText("...");
                final int i6 = i2;
                final int i7 = i3;
                final int i8 = i4;
                cityRunActivity.f162m.execute(new Runnable() { // from class: f.m
                    @Override // java.lang.Runnable
                    public final void run() {
                        final boolean zEquals;
                        r rVar2 = rVar;
                        int i9 = i8;
                        int i10 = CityRunActivity.f151p;
                        final CityRunActivity cityRunActivity2 = cityRunActivity;
                        cityRunActivity2.getClass();
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("option_id", String.valueOf(i9));
                            zEquals = "success".equals(w.y.b(rVar2, "/processCoinsRedemption", jSONObject).optString(NotificationCompat.CATEGORY_STATUS, ""));
                        } catch (Exception unused) {
                            zEquals = false;
                        }
                        if (zEquals) {
                            rVar2.a("success", "Redeemed " + i6 + "c → " + i7 + "r");
                            w.y.r(rVar2);
                            cityRunActivity2.r();
                        }
                        Handler handler = cityRunActivity2.f161l;
                        final String str3 = str2;
                        handler.post(new Runnable() { // from class: f.n
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i11 = CityRunActivity.f151p;
                                CityRunActivity cityRunActivity3 = cityRunActivity2;
                                cityRunActivity3.getClass();
                                cityRunActivity3.t(zEquals ? "Redeemed" : "Redeem မအောင်မြင်");
                                cityRunActivity3.u(str3);
                            }
                        });
                    }
                });
            }
        });
        linearLayout.addView(textView3);
        return linearLayout;
    }

    public final int m(int i2) {
        return Math.round(i2 * getResources().getDisplayMetrics().density);
    }

    public final long n(int i2) {
        String string = getSharedPreferences("app_config", 0).getString("cityrun_cooldowns", "");
        if (!string.isEmpty()) {
            String[] strArrSplit = string.split(",");
            try {
                return Long.parseLong(strArrSplit[Math.min(i2, strArrSplit.length - 1)].trim());
            } catch (Exception unused) {
            }
        }
        return (((long) i2) * 60) + 60;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        if (this.b.getVisibility() != 0) {
            super.onBackPressed();
        } else {
            if (this.f153c.canGoBack()) {
                this.f153c.goBack();
                return;
            }
            this.f153c.stopLoading();
            this.b.setVisibility(8);
            this.f163n = false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427357);
        getWindow().setStatusBarColor(-3632116);
        getWindow().getDecorView().setSystemUiVisibility(8192);
        this.f152a = (LinearLayout) findViewById(2131230769);
        this.b = (LinearLayout) findViewById(2131231366);
        this.f153c = (WebView) findViewById(2131231365);
        this.d = findViewById(2131231364);
        this.f154e = (LinearLayout) findViewById(2131231036);
        this.f155f = (TextView) findViewById(2131231348);
        this.f156g = findViewById(2131230848);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(2131231223);
        this.f157h = swipeRefreshLayout;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeColors(-3632116, -680437, -9508937);
            this.f157h.setProgressBackgroundColorSchemeColor(-15063755);
            this.f157h.setOnRefreshListener(new a(this, 4));
        }
        findViewById(2131230816).setOnClickListener(new ViewOnClickListenerC0025e(this, 0));
        findViewById(2131230828).setOnClickListener(new ViewOnClickListenerC0025e(this, 1));
        this.f156g.setOnClickListener(new ViewOnClickListenerC0025e(this, 2));
        WebSettings settings = this.f153c.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android 12; Redmi K30 5G Build/SKQ1.211006.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/149.0.7827.91 Mobile Safari/537.36");
        this.f153c.setWebViewClient(new p(this));
        try {
            String string = getSharedPreferences("cityrun_prefs", 0).getString("bots_json", null);
            if (string != null && !string.isEmpty()) {
                String str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                JSONArray jSONArray = new JSONArray(string);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    r rVarB = r.b(jSONArray.getJSONObject(i2));
                    String str2 = rVarB.f385a;
                    if (str2 != null && !str2.isEmpty()) {
                        rVarB.f387e = false;
                        rVarB.f403u = "idle";
                        if (rVarB.f389g && !rVarB.f395m.isEmpty() && !str.equals(rVarB.f395m)) {
                            rVarB.f389g = false;
                            rVarB.f394l.clear();
                            rVarB.f390h = false;
                            rVarB.f395m = "";
                            rVarB.f393k = 0L;
                        }
                        this.f158i.put(rVarB.f385a, rVarB);
                    }
                }
            }
        } catch (Exception unused) {
        }
        q();
        p(null);
        if (this.f158i.isEmpty()) {
            this.f163n = false;
            this.f152a.removeAllViews();
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(1);
            linearLayout.setGravity(17);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, m(380)));
            ProgressBar progressBar = new ProgressBar(this);
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(-3632116, PorterDuff.Mode.SRC_IN);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(m(52), m(52));
            layoutParams.gravity = 1;
            layoutParams.setMargins(0, 0, 0, m(22));
            progressBar.setLayoutParams(layoutParams);
            linearLayout.addView(progressBar);
            TextView textView = new TextView(this);
            textView.setText("Auto Login လုပ်နေသည်…");
            textView.setTextColor(-2236963);
            textView.setTextSize(15.0f);
            textView.setTypeface(null, 1);
            textView.setGravity(17);
            linearLayout.addView(textView);
            TextView textView2 = new TextView(this);
            textView2.setText("Atom Sim မှ MSISDN ကိုရယူနေသည်");
            textView2.setTextColor(-10066296);
            textView2.setTextSize(11.0f);
            textView2.setGravity(17);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.setMargins(0, m(8), 0, 0);
            textView2.setLayoutParams(layoutParams2);
            linearLayout.addView(textView2);
            this.f152a.addView(linearLayout);
            this.f153c.loadUrl("http://blatom.cityrun.pro/home/GetGWMDN?source=https%3a%2f%2fcityrun.pro%2f%3fpop2home%3dfalse");
            this.f161l.postDelayed(new RunnableC0028h(this, 2), 18000L);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.f153c.destroy();
        this.f162m.shutdownNow();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        p(null);
    }

    public final void p(RunnableC0028h runnableC0028h) {
        LinkedHashMap linkedHashMap = this.f158i;
        boolean zIsEmpty = linkedHashMap.isEmpty();
        Handler handler = this.f161l;
        if (zIsEmpty) {
            if (runnableC0028h != null) {
                handler.post(runnableC0028h);
                return;
            }
            return;
        }
        AtomicInteger atomicInteger = new AtomicInteger(linkedHashMap.size());
        for (String str : new ArrayList(linkedHashMap.keySet())) {
            r rVar = (r) linkedHashMap.get(str);
            if (rVar != null) {
                this.f162m.execute(new RunnableC0026f(this, rVar, str, atomicInteger, runnableC0028h));
            } else if (atomicInteger.decrementAndGet() == 0 && runnableC0028h != null) {
                handler.post(runnableC0028h);
            }
        }
    }

    public final void q() {
        this.f152a.removeAllViews();
        LinkedHashMap linkedHashMap = this.f160k;
        linkedHashMap.clear();
        LinkedHashMap linkedHashMap2 = this.f158i;
        if (!linkedHashMap2.isEmpty()) {
            View view = this.f156g;
            if (view != null) {
                view.setVisibility(0);
            }
            this.f152a.setPadding(m(14), m(14), m(14), m(24));
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(this);
            for (String str : linkedHashMap2.keySet()) {
                View viewInflate = layoutInflaterFrom.inflate(2131427396, (ViewGroup) this.f152a, false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                layoutParams.setMargins(0, 0, 0, m(16));
                viewInflate.setLayoutParams(layoutParams);
                linkedHashMap.put(str, viewInflate);
                k(viewInflate, str);
                this.f152a.addView(viewInflate);
            }
            h();
            return;
        }
        View view2 = this.f156g;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        this.f152a.setPadding(0, 0, 0, 0);
        int height = this.f152a.getHeight();
        if (height <= 0) {
            int i2 = getResources().getDisplayMetrics().heightPixels;
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            height = (i2 - (identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0)) - m(56);
        }
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, height));
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        frameLayout.setBackgroundColor(-14800310);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(2131165335);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(imageView);
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-14800310);
        float fM = m(18);
        gradientDrawable.setCornerRadii(new float[]{fM, fM, fM, fM, fM, fM, fM, fM});
        linearLayout2.setBackground(gradientDrawable);
        linearLayout2.setPadding(m(18), m(22), m(18), m(24));
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2, 80);
        layoutParams2.leftMargin = m(16);
        layoutParams2.rightMargin = m(16);
        layoutParams2.bottomMargin = m(16);
        linearLayout2.setLayoutParams(layoutParams2);
        TextView textView = new TextView(this);
        textView.setText("ကျေးဇူးပြု၍ ATOM မိုဘိုင်းဒေတာ ဖြင့်\nVPN ပိတ်ပြီးအသုံးပြုပေးပါ။");
        textView.setTextColor(-1);
        textView.setTextSize(17.0f);
        textView.setTypeface(null, 1);
        textView.setGravity(17);
        textView.setLineSpacing(m(4), 1.0f);
        linearLayout2.addView(textView);
        frameLayout.addView(linearLayout2);
        linearLayout.addView(frameLayout);
        TextView textView2 = new TextView(this);
        textView2.setText("ပြန်သွားရန်!");
        textView2.setTextColor(-1);
        textView2.setTextSize(16.0f);
        textView2.setTypeface(null, 1);
        textView2.setGravity(17);
        textView2.setBackgroundColor(-7740402);
        textView2.setPadding(0, m(18), 0, m(18));
        textView2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        textView2.setClickable(true);
        textView2.setFocusable(true);
        textView2.setOnClickListener(new ViewOnClickListenerC0025e(this, 3));
        linearLayout.addView(textView2);
        this.f152a.addView(linearLayout);
        h();
    }

    public final void r() {
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator it = this.f158i.values().iterator();
            while (it.hasNext()) {
                jSONArray.put(((r) it.next()).c());
            }
            getSharedPreferences("cityrun_prefs", 0).edit().putString("bots_json", jSONArray.toString()).apply();
        } catch (Exception unused) {
        }
    }

    public final void s(String str) {
        r rVar = (r) this.f158i.get(str);
        String strO = rVar != null ? o(rVar.f385a) : str;
        View viewInflate = getLayoutInflater().inflate(2131427384, (ViewGroup) null);
        Dialog dialog = new Dialog(this);
        int i2 = 1;
        dialog.requestWindowFeature(1);
        dialog.setContentView(viewInflate);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
        }
        TextView textView = (TextView) viewInflate.findViewById(2131231296);
        if (textView != null) {
            textView.setText(strO + " ကို ဖျက်ပြီး bot ရပ်သွားမည်");
        }
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230830);
        TextView textView2 = (TextView) viewInflate.findViewById(2131230822);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(2131230836);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new ViewOnClickListenerC0024d(this, dialog, str, 1));
        }
        if (textView2 != null) {
            textView2.setOnClickListener(new ViewOnClickListenerC0023c(dialog, i2));
        }
        if (linearLayout2 != null) {
            linearLayout2.setOnClickListener(new ViewOnClickListenerC0023c(dialog, 2));
        }
        dialog.show();
    }

    public final void t(String str) {
        Snackbar snackbarMake = Snackbar.make(findViewById(R.id.content), str, -1);
        snackbarMake.getView().setBackgroundColor(-15063755);
        TextView textView = (TextView) snackbarMake.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        if (textView != null) {
            textView.setTextColor(-680437);
            textView.setTextSize(2, 13.0f);
            textView.setTypeface(null, 1);
        }
        snackbarMake.show();
    }

    public final void u(String str) {
        String str2;
        int i2;
        int i3;
        View view = (View) this.f160k.get(str);
        if (view == null) {
            q();
            return;
        }
        k(view, str);
        h();
        r rVar = (r) this.f158i.get(str);
        if (rVar == null || (str2 = rVar.f408z) == null) {
            return;
        }
        rVar.f408z = null;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        linearLayout.setBackgroundResource(2131165390);
        linearLayout.setPadding(m(20), m(26), m(20), m(20));
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(2131165431);
        imageView.setColorFilter(-680437, PorterDuff.Mode.SRC_IN);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(m(56), m(56));
        layoutParams.gravity = 1;
        layoutParams.setMargins(0, 0, 0, m(6));
        imageView.setLayoutParams(layoutParams);
        linearLayout.addView(imageView);
        TextView textView = new TextView(this);
        textView.setText("Reward ရပြီ");
        textView.setTextColor(-460545);
        textView.setTextSize(18.0f);
        textView.setTypeface(null, 1);
        int i4 = 17;
        textView.setGravity(17);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.setMargins(0, 0, 0, m(18));
        textView.setLayoutParams(layoutParams2);
        linearLayout.addView(textView);
        String[] strArrSplit = str2.split("\n");
        int length = strArrSplit.length;
        int i5 = 0;
        while (i5 < length) {
            String str3 = strArrSplit[i5];
            if (!str3.trim().isEmpty()) {
                LinearLayout linearLayout2 = new LinearLayout(this);
                linearLayout2.setOrientation(0);
                linearLayout2.setGravity(i4);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(-14404268);
                gradientDrawable.setCornerRadius(m(10));
                linearLayout2.setBackground(gradientDrawable);
                linearLayout2.setTag("theme_card_inner");
                linearLayout2.setPadding(m(14), m(13), m(14), m(13));
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
                layoutParams3.setMargins(0, 0, 0, m(8));
                linearLayout2.setLayoutParams(layoutParams3);
                if (str3.startsWith("Data")) {
                    i3 = 2131165461;
                    i2 = -14498466;
                } else {
                    if (str3.startsWith("Diamond")) {
                        i3 = 2131165427;
                    } else if (str3.startsWith("Run")) {
                        i3 = 2131165447;
                    } else {
                        i2 = -3632116;
                        i3 = 2131165431;
                    }
                    i2 = 0;
                }
                ImageView imageView2 = new ImageView(this);
                imageView2.setImageResource(i3);
                if (i2 != 0) {
                    imageView2.setColorFilter(i2, PorterDuff.Mode.SRC_IN);
                } else {
                    imageView2.clearColorFilter();
                }
                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(m(18), m(18));
                layoutParams4.setMargins(0, 0, m(12), 0);
                layoutParams4.gravity = 16;
                imageView2.setLayoutParams(layoutParams4);
                linearLayout2.addView(imageView2);
                TextView textView2 = new TextView(this);
                textView2.setText(str3.trim());
                textView2.setTextColor(-460545);
                textView2.setTextSize(13.0f);
                textView2.setTypeface(null, 1);
                linearLayout2.addView(textView2);
                linearLayout.addView(linearLayout2);
            }
            i5++;
            i4 = 17;
        }
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(17);
        linearLayout3.setBackgroundResource(2131165339);
        linearLayout3.setClickable(true);
        linearLayout3.setFocusable(true);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, m(50));
        layoutParams5.setMargins(0, m(14), 0, 0);
        linearLayout3.setLayoutParams(layoutParams5);
        TextView textView3 = new TextView(this);
        textView3.setText("ဆုရယူမည်");
        textView3.setTextColor(-1);
        textView3.setTextSize(15.0f);
        textView3.setTypeface(null, 1);
        linearLayout3.addView(textView3);
        linearLayout.addView(linearLayout3);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(linearLayout);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
        }
        linearLayout3.setOnClickListener(new ViewOnClickListenerC0023c(dialog, 3));
        dialog.show();
    }
}
