package com.zenoatom.app;

import android.R;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.a;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import f.G;
import f.aa;
import f.ai;
import f.aj;
import f.aq;
import f.z;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DashboardActivity extends BaseActivity {
    public static final /* synthetic */ int L = 0;
    public z A;
    public GestureDetector B;
    public z C;
    public JSONArray J;
    public String K;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public TextView f170a;
    public z aa;
    public z ab;
    public LinearLayout ac;
    public LinearLayout ad;
    public LinearLayout ae;
    public LinearLayout af;
    public LinearLayout ag;
    public LinearLayout ah;
    public ImageView ai;
    public ImageView aj;
    public TextView ak;
    public TextView al;
    public SwipeRefreshLayout am;
    public G an;
    public Snackbar aq;
    public Snackbar ar;
    public LinearLayout as;
    public LinearLayout at;
    public View au;
    public View av;
    public TextView aw;
    public TextView ax;
    public TextView b;
    public TextView d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public TextView f172e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public TextView f173f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public TextView f174g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public LinearLayout f175h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public TextView f176i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public TextView f177j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public TextView f178k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public TextView f179l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public TextView f180m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public Button f181n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public LinearLayout f182o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public TextView f183p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public TextView f184q;

    /* JADX INFO: renamed from: r, reason: collision with root package name */
    public Button f185r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public LinearLayout f186s;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f171c = true;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public int f187t = 30;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public int f188u = 0;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public int f189v = 0;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public boolean f190w = false;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f191x = false;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f192y = false;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public boolean f193z = false;
    public final Handler ao = new Handler();
    public final ExecutorService ap = Executors.newCachedThreadPool();
    public int ay = 0;
    public final Handler az = new Handler(Looper.getMainLooper());
    public final Handler D = new Handler(Looper.getMainLooper());
    public JSONArray E = null;
    public List F = null;
    public JSONArray G = new JSONArray();
    public JSONArray H = new JSONArray();
    public JSONArray I = new JSONArray();

    public DashboardActivity() {
        new JSONArray();
        this.J = new JSONArray();
        this.K = "--";
    }

    public static void j(DashboardActivity dashboardActivity, String str, JSONArray jSONArray, int i2) {
        int i3;
        Object obj;
        JSONArray jSONArray2 = jSONArray;
        dashboardActivity.getClass();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(dashboardActivity, 2131755296);
        Object obj2 = null;
        View viewInflate = dashboardActivity.getLayoutInflater().inflate(2131427364, (ViewGroup) null);
        bottomSheetDialog.setContentView(viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(2131231318);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131231129);
        TextView textView2 = (TextView) viewInflate.findViewById(2131231317);
        if (textView != null) {
            textView.setText(str);
        }
        int i4 = 0;
        boolean z2 = jSONArray2 == null || jSONArray.length() == 0;
        if (textView2 != null) {
            textView2.setVisibility(z2 ? 0 : 8);
        }
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 8 : 0);
        }
        if (!z2 && linearLayout != null) {
            float f2 = dashboardActivity.getResources().getDisplayMetrics().density;
            int i5 = (int) (f2 * 14.0f);
            int i6 = (int) (12.0f * f2);
            int i7 = (int) (3.0f * f2);
            Math.max(1, (int) (f2 * 1.0f));
            int i8 = 0;
            while (i8 < jSONArray.length()) {
                JSONObject jSONObjectOptJSONObject = jSONArray2.optJSONObject(i8);
                if (jSONObjectOptJSONObject == null) {
                    obj = obj2;
                } else {
                    String strOptString = jSONObjectOptJSONObject.optString("title", "--");
                    String strOptString2 = jSONObjectOptJSONObject.optString("expireAtV2", "");
                    int iOptInt = jSONObjectOptJSONObject.optInt("remainingAmount", i4);
                    String strOptString3 = jSONObjectOptJSONObject.optString("unit", "");
                    LinearLayout linearLayout2 = new LinearLayout(dashboardActivity);
                    linearLayout2.setOrientation(i4);
                    linearLayout2.setGravity(16);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(-15062976);
                    gradientDrawable.setCornerRadius(dashboardActivity.getResources().getDisplayMetrics().density * 14.0f);
                    linearLayout2.setBackground(gradientDrawable);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                    layoutParams.setMargins(0, 0, 0, (int) (dashboardActivity.getResources().getDisplayMetrics().density * 10.0f));
                    linearLayout2.setLayoutParams(layoutParams);
                    linearLayout2.setPadding(i5, i5, i5, i5);
                    LinearLayout linearLayout3 = new LinearLayout(dashboardActivity);
                    linearLayout3.setOrientation(1);
                    linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
                    TextView textView3 = new TextView(dashboardActivity);
                    textView3.setText(strOptString);
                    textView3.setTextColor(-460545);
                    textView3.setTextSize(2, 15.0f);
                    textView3.setTypeface(null, 1);
                    linearLayout3.addView(textView3);
                    if (strOptString2 == null || strOptString2.isEmpty()) {
                        i3 = 0;
                    } else {
                        TextView textView4 = new TextView(dashboardActivity);
                        textView4.setText(strOptString2);
                        textView4.setTextColor(-7035976);
                        textView4.setTextSize(2, 11.0f);
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                        i3 = 0;
                        layoutParams2.setMargins(0, i7, 0, 0);
                        textView4.setLayoutParams(layoutParams2);
                        linearLayout3.addView(textView4);
                    }
                    linearLayout2.addView(linearLayout3);
                    LinearLayout linearLayout4 = new LinearLayout(dashboardActivity);
                    linearLayout4.setOrientation(i3);
                    linearLayout4.setGravity(16);
                    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams3.setMarginStart(i6);
                    linearLayout4.setLayoutParams(layoutParams3);
                    TextView textView5 = new TextView(dashboardActivity);
                    textView5.setText(String.valueOf(iOptInt));
                    textView5.setTextColor(i2);
                    textView5.setTextSize(2, 17.0f);
                    obj = null;
                    textView5.setTypeface(null, 1);
                    linearLayout4.addView(textView5);
                    if (strOptString3 != null && !strOptString3.isEmpty()) {
                        TextView textView6 = new TextView(dashboardActivity);
                        textView6.setText(" ".concat(strOptString3));
                        textView6.setTextColor(i2);
                        textView6.setTextSize(2, 11.0f);
                        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
                        layoutParams4.setMargins(0, 0, 0, i7);
                        textView6.setLayoutParams(layoutParams4);
                        linearLayout4.addView(textView6);
                    }
                    linearLayout2.addView(linearLayout4);
                    linearLayout.addView(linearLayout2);
                }
                i8++;
                jSONArray2 = jSONArray;
                obj2 = obj;
                i5 = i5;
                bottomSheetDialog = bottomSheetDialog;
                i4 = 0;
            }
        }
        BottomSheetDialog bottomSheetDialog2 = bottomSheetDialog;
        bottomSheetDialog2.setOnShowListener(new aq(bottomSheetDialog2));
        bottomSheetDialog2.show();
    }

    public static JSONArray k(DashboardActivity dashboardActivity, JSONObject jSONObject, String str) {
        JSONObject jSONObjectOptJSONObject;
        JSONArray jSONArrayOptJSONArray;
        dashboardActivity.getClass();
        try {
            if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject(str)) == null || (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("packsList")) == null) {
                jSONArrayOptJSONArray = new JSONArray();
            }
            return jSONArrayOptJSONArray;
        } catch (Exception unused) {
            return new JSONArray();
        }
    }

    public static void l(DashboardActivity dashboardActivity) {
        z zVar = dashboardActivity.aa;
        Handler handler = dashboardActivity.ao;
        if (zVar != null) {
            handler.removeCallbacks(zVar);
        }
        dashboardActivity.f188u = dashboardActivity.f187t;
        dashboardActivity.f190w = true;
        Button button = dashboardActivity.f181n;
        if (button != null) {
            button.setEnabled(false);
            dashboardActivity.f181n.setClickable(false);
            dashboardActivity.f181n.setAlpha(0.5f);
        }
        z zVar2 = new z(dashboardActivity, 4);
        dashboardActivity.aa = zVar2;
        handler.post(zVar2);
    }

    public static void m(DashboardActivity dashboardActivity, String str, String str2, String str3) {
        View viewInflate = dashboardActivity.getLayoutInflater().inflate(2131427393, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(2131231351);
        TextView textView2 = (TextView) viewInflate.findViewById(2131231349);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131231017);
        TextView textView3 = (TextView) viewInflate.findViewById(2131231350);
        Button button = (Button) viewInflate.findViewById(2131230862);
        if (str != null && !str.isEmpty()) {
            textView.setText(str);
        }
        textView2.setText(str2);
        if (str3 == null || str3.isEmpty()) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
            textView3.setText(str3);
        }
        Dialog dialog = new Dialog(dashboardActivity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(viewInflate);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout((int) (dashboardActivity.getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
        }
        button.setOnClickListener(new aj(dashboardActivity, dialog, 5));
        dialog.show();
    }

    public static void n(DashboardActivity dashboardActivity) {
        z zVar = dashboardActivity.ab;
        Handler handler = dashboardActivity.ao;
        if (zVar != null) {
            handler.removeCallbacks(zVar);
        }
        dashboardActivity.f189v = dashboardActivity.f187t;
        dashboardActivity.f191x = true;
        Button button = dashboardActivity.f185r;
        if (button != null) {
            button.setEnabled(false);
            dashboardActivity.f185r.setClickable(false);
            dashboardActivity.f185r.setAlpha(0.5f);
        }
        z zVar2 = new z(dashboardActivity, 6);
        dashboardActivity.ab = zVar2;
        handler.post(zVar2);
    }

    public static void o(DashboardActivity dashboardActivity, String str) {
        View viewInflate = dashboardActivity.getLayoutInflater().inflate(2131427390, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(2131231336);
        TextView textView2 = (TextView) viewInflate.findViewById(2131231335);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230827);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(2131230841);
        textView.setText("အောင်မြင်ပါသည်");
        if (str != null) {
            textView2.setText(str);
        }
        Dialog dialog = new Dialog(dashboardActivity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(viewInflate);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout((int) (dashboardActivity.getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
        }
        aj ajVar = new aj(dashboardActivity, dialog, 4);
        linearLayout.setOnClickListener(ajVar);
        linearLayout2.setOnClickListener(ajVar);
        dialog.show();
    }

    public static void p(DashboardActivity dashboardActivity, String str) {
        View viewInflate = dashboardActivity.getLayoutInflater().inflate(2131427386, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(2131231301);
        TextView textView2 = (TextView) viewInflate.findViewById(2131231300);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230826);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(2131230837);
        textView.setText("မအောင်မြင်ပါ");
        if (str != null) {
            textView2.setText(str);
        }
        Dialog dialog = new Dialog(dashboardActivity);
        dialog.requestWindowFeature(1);
        dialog.setContentView(viewInflate);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout((int) (dashboardActivity.getResources().getDisplayMetrics().widthPixels * 0.92f), -2);
        }
        aj ajVar = new aj(dashboardActivity, dialog, 3);
        linearLayout.setOnClickListener(ajVar);
        linearLayout2.setOnClickListener(ajVar);
        dialog.show();
    }

    public static String t(String str) {
        String strK = a.k("+", str);
        if (strK.length() <= 9) {
            return strK;
        }
        return strK.substring(0, 4) + "*****" + strK.substring(9 > strK.length() ? strK.length() - 1 : 9);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        NetworkInfo activeNetworkInfo;
        super.onCreate(bundle);
        setContentView(2131427359);
        G gV = y.v(this);
        this.an = gV;
        if (gV == null) {
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            finish();
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        int i2 = 1;
        int i3 = 0;
        int i4 = 8;
        if (!((connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true)) {
            Snackbar snackbar = this.aq;
            if (snackbar == null || !snackbar.isShown()) {
                Snackbar snackbarMake = Snackbar.make(findViewById(R.id.content), "အင်တာနက်ချိပ်ဆက်ထားခြင်းမရှိပါ", -2);
                this.aq = snackbarMake;
                snackbarMake.show();
            }
            z zVar = new z(this, i4);
            this.C = zVar;
            this.D.postDelayed(zVar, 1500L);
            return;
        }
        this.f170a = (TextView) findViewById(2131231311);
        this.b = (TextView) findViewById(2131231310);
        this.d = (TextView) findViewById(2131231298);
        this.f172e = (TextView) findViewById(2131231347);
        this.f173f = (TextView) findViewById(2131231331);
        this.f174g = (TextView) findViewById(2131231288);
        this.f175h = (LinearLayout) findViewById(2131230869);
        this.f176i = (TextView) findViewById(2131231321);
        this.f177j = (TextView) findViewById(2131231322);
        this.f178k = (TextView) findViewById(2131231320);
        this.f179l = (TextView) findViewById(2131231308);
        this.f180m = (TextView) findViewById(2131231312);
        this.f181n = (Button) findViewById(2131230815);
        this.f182o = (LinearLayout) findViewById(2131231034);
        this.f183p = (TextView) findViewById(2131231304);
        this.f184q = (TextView) findViewById(2131231305);
        this.f185r = (Button) findViewById(2131230842);
        this.f186s = (LinearLayout) findViewById(2131231032);
        this.ac = (LinearLayout) findViewById(2131230819);
        this.ad = (LinearLayout) findViewById(2131230820);
        this.ae = (LinearLayout) findViewById(2131231186);
        this.af = (LinearLayout) findViewById(2131231183);
        this.ag = (LinearLayout) findViewById(2131230858);
        this.ah = (LinearLayout) findViewById(2131230855);
        this.ai = (ImageView) findViewById(2131230992);
        this.aj = (ImageView) findViewById(2131230989);
        this.ak = (TextView) findViewById(2131231250);
        this.al = (TextView) findViewById(2131231248);
        this.am = (SwipeRefreshLayout) findViewById(2131231223);
        this.as = (LinearLayout) findViewById(2131230804);
        this.at = (LinearLayout) findViewById(2131230805);
        this.au = findViewById(2131230923);
        this.av = findViewById(2131230924);
        this.aw = (TextView) findViewById(2131231306);
        this.ax = (TextView) findViewById(2131231307);
        this.B = new GestureDetector(this, new ai(this));
        FrameLayout frameLayout = (FrameLayout) findViewById(2131230803);
        if (frameLayout != null) {
            frameLayout.setOnTouchListener(new View.OnTouchListener() { // from class: f.x
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    this.f417a.B.onTouchEvent(motionEvent);
                    return true;
                }
            });
        }
        x();
        this.f170a.setText("");
        this.b.setText(t(this.an.f296a));
        this.b.setOnClickListener(new f.y(12, this));
        w();
        this.ag.setOnClickListener(new f.y(13, this));
        this.ah.setOnClickListener(new f.y(14, this));
        LinearLayout linearLayout = (LinearLayout) findViewById(2131230867);
        if (linearLayout != null) {
            String string = getSharedPreferences("app_config", 0).getString("game_cooldown", "");
            if (!string.isEmpty()) {
                try {
                    int i5 = Integer.parseInt(string);
                    if (i5 > 0) {
                        this.f187t = i5;
                    }
                } catch (Exception unused) {
                }
            }
            String string2 = getSharedPreferences("app_config", 0).getString("cityrun", "");
            if (!string2.isEmpty()) {
                linearLayout.setVisibility("true".equalsIgnoreCase(string2) ? 0 : 8);
            }
            linearLayout.setOnClickListener(new f.y(15, this));
        }
        this.f181n.setOnClickListener(new f.y(16, this));
        this.f185r.setOnClickListener(new f.y(17, this));
        LinearLayout linearLayout2 = this.ac;
        if (linearLayout2 != null) {
            linearLayout2.setOnClickListener(new f.y(18, this));
        }
        LinearLayout linearLayout3 = this.ad;
        if (linearLayout3 != null) {
            linearLayout3.setOnClickListener(new f.y(i3, this));
        }
        LinearLayout linearLayout4 = (LinearLayout) findViewById(2131230843);
        if (linearLayout4 != null) {
            linearLayout4.setOnClickListener(new f.y(i2, this));
        }
        LinearLayout linearLayout5 = (LinearLayout) findViewById(2131230813);
        if (linearLayout5 != null) {
            linearLayout5.setOnClickListener(new f.y(2, this));
        }
        LinearLayout linearLayout6 = (LinearLayout) findViewById(2131230846);
        if (linearLayout6 != null) {
            linearLayout6.setOnClickListener(new f.y(3, this));
        }
        LinearLayout linearLayout7 = (LinearLayout) findViewById(2131230844);
        if (linearLayout7 != null) {
            linearLayout7.setOnClickListener(new f.y(4, this));
        }
        LinearLayout linearLayout8 = (LinearLayout) findViewById(2131230848);
        if (linearLayout8 != null) {
            linearLayout8.setOnClickListener(new f.y(5, this));
        }
        LinearLayout linearLayout9 = (LinearLayout) findViewById(2131230859);
        if (linearLayout9 != null) {
            linearLayout9.setOnClickListener(new f.y(6, this));
        }
        View viewFindViewById = findViewById(2131230868);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(new f.y(7, this));
        }
        View viewFindViewById2 = findViewById(2131230871);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(new f.y(i4, this));
        }
        View viewFindViewById3 = findViewById(2131230870);
        if (viewFindViewById3 != null) {
            viewFindViewById3.setOnClickListener(new f.y(9, this));
        }
        View viewFindViewById4 = findViewById(2131230865);
        if (viewFindViewById4 != null) {
            viewFindViewById4.setOnClickListener(new f.y(10, this));
        }
        View viewFindViewById5 = findViewById(2131230866);
        if (viewFindViewById5 != null) {
            viewFindViewById5.setOnClickListener(new f.y(11, this));
        }
        this.am.setColorSchemeColors(-14498466, -14498466);
        this.am.setOnRefreshListener(new aa(this));
        s();
        TextView textView = this.f179l;
        if (textView != null) {
            textView.setTextColor(-14498466);
        }
        TextView textView2 = this.f183p;
        if (textView2 != null) {
            textView2.setTextColor(-14498466);
        }
        this.ao.postDelayed(new z(this, i2), 1500L);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.ap.shutdownNow();
        z zVar = this.aa;
        Handler handler = this.ao;
        if (zVar != null) {
            handler.removeCallbacks(zVar);
        }
        z zVar2 = this.ab;
        if (zVar2 != null) {
            handler.removeCallbacks(zVar2);
        }
        z zVar3 = this.C;
        if (zVar3 != null) {
            this.D.removeCallbacks(zVar3);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        z zVar = this.A;
        if (zVar != null) {
            this.az.removeCallbacks(zVar);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        x();
        G gV = y.v(this);
        if (gV == null) {
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            finish();
            return;
        }
        G g2 = this.an;
        String str = g2 != null ? g2.f296a : "";
        String str2 = gV.f296a;
        if (str2.equals(str)) {
            return;
        }
        this.an = gV;
        TextView textView = this.b;
        if (textView != null) {
            textView.setText(t(str2));
        }
        TextView textView2 = this.f170a;
        if (textView2 != null) {
            textView2.setText("");
        }
        if (this.am != null) {
            s();
        }
    }

    public final void q() {
        Button button = this.f185r;
        if (button == null) {
            return;
        }
        boolean z2 = this.f191x || this.f193z;
        button.setEnabled(!z2);
        this.f185r.setClickable(!z2);
        this.f185r.setAlpha(z2 ? 0.5f : 1.0f);
    }

    public final void r() {
        Button button = this.f181n;
        if (button == null) {
            return;
        }
        boolean z2 = this.f190w || this.f192y;
        button.setEnabled(!z2);
        this.f181n.setClickable(!z2);
        this.f181n.setAlpha(z2 ? 0.5f : 1.0f);
    }

    public final void s() {
        SwipeRefreshLayout swipeRefreshLayout = this.am;
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setRefreshing(true);
        this.ap.execute(new z(this, 2));
    }

    public final void u(boolean z2) {
        LinearLayout linearLayout = this.f186s;
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 0 : 8);
        }
        Button button = this.f185r;
        if (button != null) {
            if (!z2) {
                q();
                return;
            }
            button.setEnabled(false);
            this.f185r.setClickable(false);
            this.f185r.setAlpha(0.5f);
        }
    }

    public final void v(boolean z2) {
        LinearLayout linearLayout = this.f182o;
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 0 : 8);
        }
        Button button = this.f181n;
        if (button != null) {
            if (!z2) {
                r();
                return;
            }
            button.setEnabled(false);
            this.f181n.setClickable(false);
            this.f181n.setAlpha(0.5f);
        }
    }

    public final void w() {
        this.ae.setVisibility(0);
        this.af.setVisibility(8);
        this.ag.setBackgroundResource(2131165382);
        this.ah.setBackgroundResource(0);
        ImageView imageView = this.ai;
        if (imageView != null) {
            imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView2 = this.aj;
        if (imageView2 != null) {
            imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        }
        TextView textView = this.ak;
        if (textView != null) {
            textView.setTextColor(-1);
            this.ak.setTypeface(null, 1);
        }
        TextView textView2 = this.al;
        if (textView2 != null) {
            textView2.setTextColor(-1);
            this.al.setTypeface(null, 0);
        }
    }

    public final void x() {
        z zVar = this.A;
        Handler handler = this.az;
        if (zVar != null) {
            handler.removeCallbacks(zVar);
        }
        z zVar2 = new z(this, 0);
        this.A = zVar2;
        handler.postDelayed(zVar2, 5000L);
    }

    public final void y() {
        if (this.au == null || this.av == null) {
            return;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        gradientDrawable.setColor(this.ay == 0 ? -14498466 : -13156015);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setShape(1);
        gradientDrawable2.setColor(this.ay == 1 ? -14498466 : -13156015);
        this.au.setBackground(gradientDrawable);
        this.av.setBackground(gradientDrawable2);
    }
}
