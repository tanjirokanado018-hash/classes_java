package com.zenoatom.app;

import android.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.f;
import com.google.android.material.snackbar.Snackbar;
import f.o;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class SplashActivity extends BaseActivity {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final /* synthetic */ int f253f = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Snackbar f255c;
    public o d;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ExecutorService f254a = Executors.newSingleThreadExecutor();
    public final Handler b = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f256e = false;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        NetworkInfo activeNetworkInfo;
        super.onCreate(bundle);
        setContentView(2131427363);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        Handler handler = this.b;
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) {
            Snackbar snackbar = this.f255c;
            if (snackbar == null || !snackbar.isShown()) {
                Snackbar snackbarMake = Snackbar.make(findViewById(R.id.content), "အင်တာနက်ချိပ်ဆက်ထားခြင်းမရှိပါ", -2);
                this.f255c = snackbarMake;
                snackbarMake.show();
            }
            o oVar = new o(8, this);
            this.d = oVar;
            handler.postDelayed(oVar, 1500L);
            return;
        }
        Snackbar snackbar2 = this.f255c;
        if (snackbar2 != null && snackbar2.isShown()) {
            this.f255c.dismiss();
        }
        this.f256e = true;
        o oVar2 = this.d;
        if (oVar2 != null) {
            handler.removeCallbacks(oVar2);
        }
        this.f254a.execute(new f(4, this));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        o oVar = this.d;
        if (oVar != null) {
            this.b.removeCallbacks(oVar);
        }
        this.f254a.shutdownNow();
    }
}
