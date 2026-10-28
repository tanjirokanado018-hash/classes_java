package com.zenoatom.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.zenoatom.app.LoginActivity;
import f.au;
import f.av;
import f.aw;
import f.ax;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class LoginActivity extends BaseActivity {

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public static final /* synthetic */ int f208w = 0;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public LinearLayout f212f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public LinearLayout f213g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public LinearLayout f214h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public LinearLayout f215i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public LinearLayout f216j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public EditText f217k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public EditText f218l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public EditText f219m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public EditText f220n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public EditText f221o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public EditText f222p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public EditText f223q;

    /* JADX INFO: renamed from: r, reason: collision with root package name */
    public TextView f224r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public Button f225s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public Button f226t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public Button f227u;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ExecutorService f209a = Executors.newCachedThreadPool();
    public final Handler b = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public String f210c = "";
    public String d = "";

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f211e = false;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f228v = false;

    public final void j() {
        EditText[] editTextArr = {this.f218l, this.f219m, this.f220n, this.f221o, this.f222p, this.f223q};
        this.f211e = true;
        for (int i2 = 0; i2 < 6; i2++) {
            EditText editText = editTextArr[i2];
            if (editText != null) {
                editText.setText("");
                editText.setBackgroundResource(2131165375);
            }
        }
        this.f211e = false;
        EditText editText2 = this.f218l;
        if (editText2 != null) {
            editText2.requestFocus();
        }
    }

    public final void k() {
        int i2 = 1;
        EditText[] editTextArr = {this.f218l, this.f219m, this.f220n, this.f221o, this.f222p, this.f223q};
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < 6; i3++) {
            EditText editText = editTextArr[i3];
            if (editText != null) {
                sb.append(editText.getText().toString());
            }
        }
        String string = sb.toString();
        if (string.length() < 6) {
            n("Enter full 6-digit OTP");
        } else {
            m(true);
            this.f209a.execute(new ax(this, string, i2));
        }
    }

    public final void l(boolean z2) {
        LinearLayout linearLayout = this.f215i;
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 0 : 8);
        }
        Button button = this.f225s;
        if (button != null) {
            button.setEnabled(!z2);
        }
    }

    public final void m(boolean z2) {
        LinearLayout linearLayout = this.f216j;
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 0 : 8);
        }
        Button button = this.f226t;
        if (button != null) {
            button.setEnabled(!z2);
        }
    }

    public final void n(String str) {
        Toast.makeText(this, str, 1).show();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        final int i2 = 2;
        final int i3 = 1;
        super.onCreate(bundle);
        final int i4 = 0;
        boolean booleanExtra = getIntent().getBooleanExtra("ADD_ACCOUNT_MODE", false);
        this.f228v = booleanExtra;
        if (!booleanExtra && y.v(this) != null) {
            startActivity(new Intent(this, (Class<?>) DashboardActivity.class));
            finish();
            return;
        }
        setContentView(2131427361);
        this.f212f = (LinearLayout) findViewById(2131231014);
        this.f213g = (LinearLayout) findViewById(2131231016);
        this.f214h = (LinearLayout) findViewById(2131231015);
        this.f215i = (LinearLayout) findViewById(2131231033);
        this.f216j = (LinearLayout) findViewById(2131231035);
        this.f217k = (EditText) findViewById(2131230952);
        this.f218l = (EditText) findViewById(2131230946);
        this.f219m = (EditText) findViewById(2131230947);
        this.f220n = (EditText) findViewById(2131230948);
        this.f221o = (EditText) findViewById(2131230949);
        this.f222p = (EditText) findViewById(2131230950);
        this.f223q = (EditText) findViewById(2131230951);
        this.f224r = (TextView) findViewById(2131231316);
        this.f225s = (Button) findViewById(2131230852);
        this.f226t = (Button) findViewById(2131230861);
        this.f227u = (Button) findViewById(2131230818);
        Button button = this.f225s;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener(this) { // from class: f.at
                public final /* synthetic */ LoginActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i5 = i4;
                    int i6 = 0;
                    LoginActivity loginActivity = this.b;
                    switch (i5) {
                        case 0:
                            EditText editText = loginActivity.f217k;
                            if (editText != null) {
                                String strReplaceAll = editText.getText().toString().trim().replaceAll("[^0-9]", "");
                                if (strReplaceAll.startsWith("0")) {
                                    strReplaceAll = strReplaceAll.substring(1);
                                }
                                if (strReplaceAll.startsWith("95")) {
                                    strReplaceAll = strReplaceAll.substring(2);
                                }
                                if (!strReplaceAll.isEmpty()) {
                                    loginActivity.l(true);
                                    loginActivity.f209a.execute(new ax(loginActivity, strReplaceAll, i6));
                                } else {
                                    loginActivity.n("Enter phone number");
                                }
                                break;
                            }
                            break;
                        case 1:
                            int i7 = LoginActivity.f208w;
                            loginActivity.k();
                            break;
                        default:
                            LinearLayout linearLayout = loginActivity.f213g;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(0);
                            }
                            LinearLayout linearLayout2 = loginActivity.f214h;
                            if (linearLayout2 != null) {
                                linearLayout2.setVisibility(8);
                            }
                            loginActivity.j();
                            break;
                    }
                }
            });
        }
        Button button2 = this.f226t;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener(this) { // from class: f.at
                public final /* synthetic */ LoginActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i5 = i3;
                    int i6 = 0;
                    LoginActivity loginActivity = this.b;
                    switch (i5) {
                        case 0:
                            EditText editText = loginActivity.f217k;
                            if (editText != null) {
                                String strReplaceAll = editText.getText().toString().trim().replaceAll("[^0-9]", "");
                                if (strReplaceAll.startsWith("0")) {
                                    strReplaceAll = strReplaceAll.substring(1);
                                }
                                if (strReplaceAll.startsWith("95")) {
                                    strReplaceAll = strReplaceAll.substring(2);
                                }
                                if (!strReplaceAll.isEmpty()) {
                                    loginActivity.l(true);
                                    loginActivity.f209a.execute(new ax(loginActivity, strReplaceAll, i6));
                                } else {
                                    loginActivity.n("Enter phone number");
                                }
                                break;
                            }
                            break;
                        case 1:
                            int i7 = LoginActivity.f208w;
                            loginActivity.k();
                            break;
                        default:
                            LinearLayout linearLayout = loginActivity.f213g;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(0);
                            }
                            LinearLayout linearLayout2 = loginActivity.f214h;
                            if (linearLayout2 != null) {
                                linearLayout2.setVisibility(8);
                            }
                            loginActivity.j();
                            break;
                    }
                }
            });
        }
        Button button3 = this.f227u;
        if (button3 != null) {
            button3.setOnClickListener(new View.OnClickListener(this) { // from class: f.at
                public final /* synthetic */ LoginActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i5 = i2;
                    int i6 = 0;
                    LoginActivity loginActivity = this.b;
                    switch (i5) {
                        case 0:
                            EditText editText = loginActivity.f217k;
                            if (editText != null) {
                                String strReplaceAll = editText.getText().toString().trim().replaceAll("[^0-9]", "");
                                if (strReplaceAll.startsWith("0")) {
                                    strReplaceAll = strReplaceAll.substring(1);
                                }
                                if (strReplaceAll.startsWith("95")) {
                                    strReplaceAll = strReplaceAll.substring(2);
                                }
                                if (!strReplaceAll.isEmpty()) {
                                    loginActivity.l(true);
                                    loginActivity.f209a.execute(new ax(loginActivity, strReplaceAll, i6));
                                } else {
                                    loginActivity.n("Enter phone number");
                                }
                                break;
                            }
                            break;
                        case 1:
                            int i7 = LoginActivity.f208w;
                            loginActivity.k();
                            break;
                        default:
                            LinearLayout linearLayout = loginActivity.f213g;
                            if (linearLayout != null) {
                                linearLayout.setVisibility(0);
                            }
                            LinearLayout linearLayout2 = loginActivity.f214h;
                            if (linearLayout2 != null) {
                                linearLayout2.setVisibility(8);
                            }
                            loginActivity.j();
                            break;
                    }
                }
            });
        }
        Button button4 = this.f225s;
        if (button4 != null) {
            button4.setEnabled(false);
        }
        Button button5 = this.f225s;
        if (button5 != null) {
            button5.setAlpha(0.45f);
        }
        EditText editText = this.f217k;
        if (editText != null) {
            editText.addTextChangedListener(new au(this));
        }
        EditText[] editTextArr = {this.f218l, this.f219m, this.f220n, this.f221o, this.f222p, this.f223q};
        for (int i5 = 0; i5 < 6; i5++) {
            EditText editText2 = editTextArr[i5];
            if (editText2 != null) {
                editText2.addTextChangedListener(new av(this, editTextArr, i5));
                editTextArr[i5].setOnKeyListener(new aw(this, editTextArr, i5));
            }
        }
        LinearLayout linearLayout = this.f212f;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.f209a.shutdownNow();
    }
}
