package com.zenoatom.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.zenoatom.app.ClaimActivity;
import f.G;
import f.o;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ClaimActivity extends BaseActivity {

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final /* synthetic */ int f165g = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ExecutorService f166a = Executors.newCachedThreadPool();
    public final Handler b = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public G f167c;
    public ProgressBar d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public ScrollView f168e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public LinearLayout f169f;

    public static void j(final ClaimActivity claimActivity, JSONObject jSONObject) {
        claimActivity.getClass();
        final long jOptLong = jSONObject.optLong("id", -1L);
        int iOptInt = jSONObject.optInt("point", 0);
        String strOptString = jSONObject.optString("campaign_name", "App daily check in");
        String strOptString2 = jSONObject.optString("claim_date", "");
        final String strOptString3 = jSONObject.optString("label", "");
        boolean zOptBoolean = jSONObject.optBoolean("enable", false);
        String strReplace = strOptString2.replace("|", " | ");
        TextView textView = new TextView(claimActivity);
        textView.setText(strReplace);
        textView.setTextColor(-1);
        textView.setTextSize(12.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, claimActivity.k(12), 0, claimActivity.k(6));
        textView.setLayoutParams(layoutParams);
        LinearLayout linearLayout = claimActivity.f169f;
        if (linearLayout != null) {
            linearLayout.addView(textView);
        }
        LinearLayout linearLayout2 = new LinearLayout(claimActivity);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(16);
        linearLayout2.setPadding(claimActivity.k(14), claimActivity.k(14), claimActivity.k(14), claimActivity.k(14));
        linearLayout2.setBackgroundResource(2131165324);
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        LinearLayout linearLayout3 = new LinearLayout(claimActivity);
        linearLayout3.setOrientation(1);
        linearLayout3.setGravity(1);
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(claimActivity.k(52), -2));
        TextView textView2 = new TextView(claimActivity);
        textView2.setText(String.valueOf(iOptInt));
        textView2.setTextColor(-14498466);
        textView2.setTextSize(20.0f);
        textView2.setTypeface(null, 1);
        textView2.setGravity(1);
        linearLayout3.addView(textView2);
        TextView textView3 = new TextView(claimActivity);
        textView3.setText("Points");
        textView3.setTextColor(-1);
        textView3.setTextSize(10.0f);
        textView3.setGravity(1);
        linearLayout3.addView(textView3);
        linearLayout2.addView(linearLayout3);
        TextView textView4 = new TextView(claimActivity);
        textView4.setText(strOptString);
        textView4.setTextColor(-460545);
        textView4.setTextSize(14.0f);
        textView4.setTypeface(null, 1);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, -2, 1.0f);
        layoutParams2.setMargins(claimActivity.k(12), 0, claimActivity.k(12), 0);
        textView4.setLayoutParams(layoutParams2);
        linearLayout2.addView(textView4);
        final TextView textView5 = new TextView(claimActivity);
        textView5.setText(strOptString3);
        textView5.setTextSize(13.0f);
        textView5.setTypeface(null, 1);
        textView5.setPadding(claimActivity.k(14), claimActivity.k(8), claimActivity.k(14), claimActivity.k(8));
        textView5.setGravity(17);
        textView5.setBackgroundResource(zOptBoolean ? 2131165319 : 2131165324);
        textView5.setTextColor(-1);
        if (zOptBoolean && jOptLong >= 0) {
            textView5.setOnClickListener(new View.OnClickListener() { // from class: f.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i2 = ClaimActivity.f165g;
                    ClaimActivity claimActivity2 = this.f410a;
                    claimActivity2.getClass();
                    TextView textView6 = textView5;
                    textView6.setEnabled(false);
                    textView6.setText("...");
                    claimActivity2.f166a.execute(new w(claimActivity2, jOptLong, textView6, strOptString3));
                }
            });
        }
        linearLayout2.addView(textView5);
        LinearLayout linearLayout4 = claimActivity.f169f;
        if (linearLayout4 != null) {
            linearLayout4.addView(linearLayout2);
        }
    }

    public final int k(int i2) {
        return (int) (i2 * getResources().getDisplayMetrics().density);
    }

    public final void l() {
        ProgressBar progressBar = this.d;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        ScrollView scrollView = this.f168e;
        if (scrollView != null) {
            scrollView.setVisibility(8);
        }
        this.f166a.execute(new o(2, this));
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
        this.f167c = gV;
        setContentView(2131427358);
        LinearLayout linearLayout = (LinearLayout) findViewById(2131230816);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(2131230850);
        this.d = (ProgressBar) findViewById(2131231144);
        this.f168e = (ScrollView) findViewById(2131230893);
        this.f169f = (LinearLayout) findViewById(2131230883);
        if (linearLayout != null) {
            final int i2 = 0;
            linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: f.s
                public final /* synthetic */ ClaimActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i3 = i2;
                    ClaimActivity claimActivity = this.b;
                    switch (i3) {
                        case 0:
                            int i4 = ClaimActivity.f165g;
                            claimActivity.finish();
                            break;
                        default:
                            int i5 = ClaimActivity.f165g;
                            claimActivity.l();
                            break;
                    }
                }
            });
        }
        if (linearLayout2 != null) {
            final int i3 = 1;
            linearLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: f.s
                public final /* synthetic */ ClaimActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i4 = i3;
                    ClaimActivity claimActivity = this.b;
                    switch (i4) {
                        case 0:
                            int i5 = ClaimActivity.f165g;
                            claimActivity.finish();
                            break;
                        default:
                            int i6 = ClaimActivity.f165g;
                            claimActivity.l();
                            break;
                    }
                }
            });
        }
        l();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.f166a.shutdownNow();
    }
}
