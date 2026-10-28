package com.zenoatom.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import f.I;
import f.J;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class BaseActivity extends AppCompatActivity {
    public static void i(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTypeface(Typeface.create("sans-serif", 0), textView.getTypeface() != null ? textView.getTypeface().getStyle() : 0);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                i(viewGroup.getChildAt(i2));
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.fontScale = 1.0f;
        super.attachBaseContext(context.createConfigurationContext(configuration));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    public final void h() {
        J j2;
        if ((this instanceof SplashActivity) || (this instanceof LoginActivity)) {
            return;
        }
        int iQ = y.q(this);
        J[] jArr = J.f301g;
        if (iQ == -2) {
            j2 = jArr[0];
        } else {
            j2 = (iQ < 0 || iQ >= 4) ? new J() : jArr[iQ];
        }
        I.a(this, j2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void setContentView(int i2) {
        super.setContentView(i2);
        i(getWindow().getDecorView());
        h();
    }
}
