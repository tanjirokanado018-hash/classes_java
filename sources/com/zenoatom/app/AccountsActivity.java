package com.zenoatom.app;

import android.R;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zenoatom.app.AccountsActivity;
import com.zenoatom.app.DashboardActivity;
import f.G;
import f.ViewOnClickListenerC0021a;
import java.util.ArrayList;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class AccountsActivity extends BaseActivity {
    public static final /* synthetic */ int b = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public LinearLayout f150a;

    public AccountsActivity() {
        new Handler(Looper.getMainLooper());
    }

    public static String k(String str) {
        if (str == null) {
            return "";
        }
        if (!str.startsWith("959")) {
            return str;
        }
        return "09" + str.substring(3);
    }

    public final int j(int i2) {
        return (int) (i2 * getResources().getDisplayMetrics().density);
    }

    public final void l() {
        final int i2;
        LinearLayout linearLayout = this.f150a;
        if (linearLayout == null) {
            return;
        }
        linearLayout.removeAllViews();
        ArrayList<G> arrayListW = y.w(this);
        int i3 = 0;
        String string = getSharedPreferences("zenoatom_prefs", 0).getString("active_msisdn", "");
        int i4 = -2;
        int i5 = 17;
        int i6 = -1;
        if (arrayListW.isEmpty()) {
            TextView textView = new TextView(this);
            textView.setText("Account မရှိသေးပါ\nLogin လုပ်ပြီး ထည့်ပါ");
            textView.setTextColor(-1);
            textView.setTextSize(14.0f);
            textView.setGravity(17);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            layoutParams.setMargins(0, j(40), 0, 0);
            textView.setLayoutParams(layoutParams);
            this.f150a.addView(textView);
            return;
        }
        for (final G g2 : arrayListW) {
            boolean zEquals = g2.f296a.equals(string);
            LinearLayout linearLayout2 = this.f150a;
            LinearLayout linearLayout3 = new LinearLayout(this);
            linearLayout3.setOrientation(i3);
            linearLayout3.setGravity(16);
            linearLayout3.setPadding(j(16), j(16), j(16), j(16));
            linearLayout3.setBackgroundResource(zEquals ? 2131165305 : 2131165326);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i6, i4);
            layoutParams2.setMargins(i3, i3, i3, j(12));
            linearLayout3.setLayoutParams(layoutParams2);
            LinearLayout linearLayout4 = new LinearLayout(this);
            linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(j(44), j(44)));
            linearLayout4.setBackgroundResource(zEquals ? 2131165317 : 2131165330);
            linearLayout4.setGravity(i5);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(j(22), j(22)));
            imageView.setImageResource(2131165446);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            imageView.setColorFilter(i6, mode);
            linearLayout4.addView(imageView);
            linearLayout3.addView(linearLayout4);
            LinearLayout linearLayout5 = new LinearLayout(this);
            final int i7 = 1;
            linearLayout5.setOrientation(1);
            linearLayout5.setGravity(16);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(i3, i4, 1.0f);
            layoutParams3.setMargins(j(12), i3, j(8), i3);
            linearLayout5.setLayoutParams(layoutParams3);
            TextView textView2 = new TextView(this);
            textView2.setText(k(g2.f296a));
            textView2.setTextColor(-460545);
            textView2.setTextSize(15.0f);
            textView2.setTypeface(null, 1);
            linearLayout5.addView(textView2);
            if (zEquals) {
                TextView textView3 = new TextView(this);
                textView3.setText("✔  Active");
                textView3.setTextColor(-14498466);
                textView3.setTextSize(11.0f);
                textView3.setTypeface(null, 1);
                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(i4, i4);
                layoutParams4.setMargins(0, j(3), 0, 0);
                textView3.setLayoutParams(layoutParams4);
                linearLayout5.addView(textView3);
                i6 = -1;
                i2 = 0;
            } else {
                TextView textView4 = new TextView(this);
                textView4.setText("Tap to switch");
                i6 = -1;
                textView4.setTextColor(-1);
                textView4.setTextSize(11.0f);
                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(i4, i4);
                i2 = 0;
                layoutParams5.setMargins(0, j(3), 0, 0);
                textView4.setLayoutParams(layoutParams5);
                linearLayout5.addView(textView4);
            }
            linearLayout3.addView(linearLayout5);
            if (!zEquals) {
                linearLayout3.setOnClickListener(new View.OnClickListener(this) { // from class: f.b
                    public final /* synthetic */ AccountsActivity b;

                    {
                        this.b = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i8 = i2;
                        G g3 = g2;
                        AccountsActivity accountsActivity = this.b;
                        switch (i8) {
                            case 0:
                                int i9 = AccountsActivity.b;
                                accountsActivity.getClass();
                                w.y.ai(accountsActivity, g3.f296a);
                                Intent intent = new Intent(accountsActivity, (Class<?>) DashboardActivity.class);
                                intent.addFlags(335544320);
                                accountsActivity.startActivity(intent);
                                accountsActivity.finish();
                                break;
                            default:
                                int i10 = AccountsActivity.b;
                                accountsActivity.getClass();
                                Dialog dialog = new Dialog(accountsActivity);
                                dialog.requestWindowFeature(1);
                                dialog.setCanceledOnTouchOutside(true);
                                LinearLayout linearLayout6 = new LinearLayout(accountsActivity);
                                linearLayout6.setOrientation(1);
                                linearLayout6.setGravity(1);
                                linearLayout6.setBackgroundResource(2131165390);
                                linearLayout6.setPadding(accountsActivity.j(28), accountsActivity.j(32), accountsActivity.j(28), accountsActivity.j(28));
                                LinearLayout linearLayout7 = new LinearLayout(accountsActivity);
                                LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(accountsActivity.j(64), accountsActivity.j(64));
                                layoutParams6.gravity = 1;
                                linearLayout7.setLayoutParams(layoutParams6);
                                linearLayout7.setBackgroundResource(2131165358);
                                linearLayout7.setGravity(17);
                                ImageView imageView2 = new ImageView(accountsActivity);
                                imageView2.setLayoutParams(new LinearLayout.LayoutParams(accountsActivity.j(30), accountsActivity.j(30)));
                                imageView2.setImageResource(2131165426);
                                imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                                linearLayout7.addView(imageView2);
                                linearLayout6.addView(linearLayout7);
                                TextView textView5 = new TextView(accountsActivity);
                                LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-2, -2);
                                layoutParams7.setMargins(0, accountsActivity.j(20), 0, 0);
                                layoutParams7.gravity = 1;
                                textView5.setLayoutParams(layoutParams7);
                                textView5.setText("Account ဖျက်မလား?");
                                textView5.setTextColor(-460545);
                                textView5.setTextSize(18.0f);
                                textView5.setTypeface(null, 1);
                                textView5.setGravity(17);
                                linearLayout6.addView(textView5);
                                TextView textView6 = new TextView(accountsActivity);
                                LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(-2, -2);
                                layoutParams8.setMargins(0, accountsActivity.j(10), 0, 0);
                                layoutParams8.gravity = 1;
                                textView6.setLayoutParams(layoutParams8);
                                textView6.setText(AccountsActivity.k(g3.f296a));
                                textView6.setTextColor(-14498466);
                                textView6.setTextSize(17.0f);
                                textView6.setTypeface(null, 1);
                                textView6.setGravity(17);
                                linearLayout6.addView(textView6);
                                TextView textView7 = new TextView(accountsActivity);
                                LinearLayout.LayoutParams layoutParams9 = new LinearLayout.LayoutParams(-2, -2);
                                layoutParams9.setMargins(0, accountsActivity.j(6), 0, 0);
                                layoutParams9.gravity = 1;
                                textView7.setLayoutParams(layoutParams9);
                                textView7.setText("ကို ဖျက်မှာသေချာလား?");
                                textView7.setTextColor(-1);
                                textView7.setTextSize(13.0f);
                                textView7.setGravity(17);
                                linearLayout6.addView(textView7);
                                LinearLayout linearLayout8 = new LinearLayout(accountsActivity);
                                linearLayout8.setOrientation(0);
                                linearLayout8.setGravity(17);
                                LinearLayout.LayoutParams layoutParams10 = new LinearLayout.LayoutParams(-1, -2);
                                layoutParams10.setMargins(0, accountsActivity.j(24), 0, 0);
                                linearLayout8.setLayoutParams(layoutParams10);
                                TextView textView8 = new TextView(accountsActivity);
                                LinearLayout.LayoutParams layoutParams11 = new LinearLayout.LayoutParams(0, accountsActivity.j(46), 1.0f);
                                layoutParams11.setMargins(0, 0, accountsActivity.j(8), 0);
                                textView8.setLayoutParams(layoutParams11);
                                textView8.setText("မဖျက်ဘူး");
                                textView8.setTextColor(-1);
                                textView8.setTextSize(14.0f);
                                textView8.setTypeface(null, 1);
                                textView8.setGravity(17);
                                textView8.setBackgroundResource(2131165359);
                                textView8.setOnClickListener(new ViewOnClickListenerC0023c(dialog, 0));
                                linearLayout8.addView(textView8);
                                TextView textView9 = new TextView(accountsActivity);
                                LinearLayout.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(0, accountsActivity.j(46), 1.0f);
                                layoutParams12.setMargins(accountsActivity.j(8), 0, 0, 0);
                                textView9.setLayoutParams(layoutParams12);
                                textView9.setText("ဖျက်မယ်");
                                textView9.setTextColor(-1);
                                textView9.setTextSize(14.0f);
                                textView9.setTypeface(null, 1);
                                textView9.setGravity(17);
                                textView9.setBackgroundResource(2131165310);
                                textView9.setOnClickListener(new ViewOnClickListenerC0024d(accountsActivity, dialog, g3, 0));
                                linearLayout8.addView(textView9);
                                linearLayout6.addView(linearLayout8);
                                dialog.setContentView(linearLayout6);
                                Window window = dialog.getWindow();
                                if (window != null) {
                                    window.setBackgroundDrawableResource(R.color.transparent);
                                    WindowManager.LayoutParams attributes = window.getAttributes();
                                    attributes.width = (int) (accountsActivity.getResources().getDisplayMetrics().widthPixels * 0.88f);
                                    attributes.height = -2;
                                    window.setAttributes(attributes);
                                    window.addFlags(2);
                                    window.setDimAmount(0.7f);
                                }
                                dialog.show();
                                break;
                        }
                    }
                });
            }
            LinearLayout linearLayout6 = new LinearLayout(this);
            linearLayout6.setLayoutParams(new LinearLayout.LayoutParams(j(34), j(34)));
            linearLayout6.setBackgroundResource(2131165309);
            linearLayout6.setGravity(17);
            ImageView imageView2 = new ImageView(this);
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(j(16), j(16)));
            imageView2.setImageResource(2131165426);
            imageView2.setColorFilter(-38037, mode);
            linearLayout6.addView(imageView2);
            linearLayout6.setOnClickListener(new View.OnClickListener(this) { // from class: f.b
                public final /* synthetic */ AccountsActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i8 = i7;
                    G g3 = g2;
                    AccountsActivity accountsActivity = this.b;
                    switch (i8) {
                        case 0:
                            int i9 = AccountsActivity.b;
                            accountsActivity.getClass();
                            w.y.ai(accountsActivity, g3.f296a);
                            Intent intent = new Intent(accountsActivity, (Class<?>) DashboardActivity.class);
                            intent.addFlags(335544320);
                            accountsActivity.startActivity(intent);
                            accountsActivity.finish();
                            break;
                        default:
                            int i10 = AccountsActivity.b;
                            accountsActivity.getClass();
                            Dialog dialog = new Dialog(accountsActivity);
                            dialog.requestWindowFeature(1);
                            dialog.setCanceledOnTouchOutside(true);
                            LinearLayout linearLayout7 = new LinearLayout(accountsActivity);
                            linearLayout7.setOrientation(1);
                            linearLayout7.setGravity(1);
                            linearLayout7.setBackgroundResource(2131165390);
                            linearLayout7.setPadding(accountsActivity.j(28), accountsActivity.j(32), accountsActivity.j(28), accountsActivity.j(28));
                            LinearLayout linearLayout8 = new LinearLayout(accountsActivity);
                            LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(accountsActivity.j(64), accountsActivity.j(64));
                            layoutParams6.gravity = 1;
                            linearLayout8.setLayoutParams(layoutParams6);
                            linearLayout8.setBackgroundResource(2131165358);
                            linearLayout8.setGravity(17);
                            ImageView imageView3 = new ImageView(accountsActivity);
                            imageView3.setLayoutParams(new LinearLayout.LayoutParams(accountsActivity.j(30), accountsActivity.j(30)));
                            imageView3.setImageResource(2131165426);
                            imageView3.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            linearLayout8.addView(imageView3);
                            linearLayout7.addView(linearLayout8);
                            TextView textView5 = new TextView(accountsActivity);
                            LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-2, -2);
                            layoutParams7.setMargins(0, accountsActivity.j(20), 0, 0);
                            layoutParams7.gravity = 1;
                            textView5.setLayoutParams(layoutParams7);
                            textView5.setText("Account ဖျက်မလား?");
                            textView5.setTextColor(-460545);
                            textView5.setTextSize(18.0f);
                            textView5.setTypeface(null, 1);
                            textView5.setGravity(17);
                            linearLayout7.addView(textView5);
                            TextView textView6 = new TextView(accountsActivity);
                            LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(-2, -2);
                            layoutParams8.setMargins(0, accountsActivity.j(10), 0, 0);
                            layoutParams8.gravity = 1;
                            textView6.setLayoutParams(layoutParams8);
                            textView6.setText(AccountsActivity.k(g3.f296a));
                            textView6.setTextColor(-14498466);
                            textView6.setTextSize(17.0f);
                            textView6.setTypeface(null, 1);
                            textView6.setGravity(17);
                            linearLayout7.addView(textView6);
                            TextView textView7 = new TextView(accountsActivity);
                            LinearLayout.LayoutParams layoutParams9 = new LinearLayout.LayoutParams(-2, -2);
                            layoutParams9.setMargins(0, accountsActivity.j(6), 0, 0);
                            layoutParams9.gravity = 1;
                            textView7.setLayoutParams(layoutParams9);
                            textView7.setText("ကို ဖျက်မှာသေချာလား?");
                            textView7.setTextColor(-1);
                            textView7.setTextSize(13.0f);
                            textView7.setGravity(17);
                            linearLayout7.addView(textView7);
                            LinearLayout linearLayout9 = new LinearLayout(accountsActivity);
                            linearLayout9.setOrientation(0);
                            linearLayout9.setGravity(17);
                            LinearLayout.LayoutParams layoutParams10 = new LinearLayout.LayoutParams(-1, -2);
                            layoutParams10.setMargins(0, accountsActivity.j(24), 0, 0);
                            linearLayout9.setLayoutParams(layoutParams10);
                            TextView textView8 = new TextView(accountsActivity);
                            LinearLayout.LayoutParams layoutParams11 = new LinearLayout.LayoutParams(0, accountsActivity.j(46), 1.0f);
                            layoutParams11.setMargins(0, 0, accountsActivity.j(8), 0);
                            textView8.setLayoutParams(layoutParams11);
                            textView8.setText("မဖျက်ဘူး");
                            textView8.setTextColor(-1);
                            textView8.setTextSize(14.0f);
                            textView8.setTypeface(null, 1);
                            textView8.setGravity(17);
                            textView8.setBackgroundResource(2131165359);
                            textView8.setOnClickListener(new ViewOnClickListenerC0023c(dialog, 0));
                            linearLayout9.addView(textView8);
                            TextView textView9 = new TextView(accountsActivity);
                            LinearLayout.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(0, accountsActivity.j(46), 1.0f);
                            layoutParams12.setMargins(accountsActivity.j(8), 0, 0, 0);
                            textView9.setLayoutParams(layoutParams12);
                            textView9.setText("ဖျက်မယ်");
                            textView9.setTextColor(-1);
                            textView9.setTextSize(14.0f);
                            textView9.setTypeface(null, 1);
                            textView9.setGravity(17);
                            textView9.setBackgroundResource(2131165310);
                            textView9.setOnClickListener(new ViewOnClickListenerC0024d(accountsActivity, dialog, g3, 0));
                            linearLayout9.addView(textView9);
                            linearLayout7.addView(linearLayout9);
                            dialog.setContentView(linearLayout7);
                            Window window = dialog.getWindow();
                            if (window != null) {
                                window.setBackgroundDrawableResource(R.color.transparent);
                                WindowManager.LayoutParams attributes = window.getAttributes();
                                attributes.width = (int) (accountsActivity.getResources().getDisplayMetrics().widthPixels * 0.88f);
                                attributes.height = -2;
                                window.setAttributes(attributes);
                                window.addFlags(2);
                                window.setDimAmount(0.7f);
                            }
                            dialog.show();
                            break;
                    }
                }
            });
            linearLayout3.addView(linearLayout6);
            linearLayout2.addView(linearLayout3);
            i3 = 0;
            i4 = -2;
            i5 = 17;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427356);
        LinearLayout linearLayout = (LinearLayout) findViewById(2131230817);
        this.f150a = (LinearLayout) findViewById(2131230768);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(2131230814);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new ViewOnClickListenerC0021a(0, this));
        }
        if (linearLayout2 != null) {
            linearLayout2.setOnClickListener(new ViewOnClickListenerC0021a(1, this));
        }
        l();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        l();
    }
}
