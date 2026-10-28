package androidx.core.content.res;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.fragment.app.strictmode.Violation;
import androidx.lifecycle.DispatchQueue;
import androidx.profileinstaller.ProfileInstallerInitializer;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.LoginActivity;
import com.zenoatom.app.PointsActivity;
import com.zenoatom.app.SplashActivity;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f29c;
    public final /* synthetic */ Object d;

    public /* synthetic */ a(int i2, Object obj, Object obj2) {
        this.b = i2;
        this.f29c = obj;
        this.d = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.d;
        Object obj2 = this.f29c;
        switch (i2) {
            case 0:
                ((ResourcesCompat.FontCallback) obj2).lambda$callbackSuccessAsync$0((Typeface) obj);
                break;
            case 1:
                FragmentStrictMode.m10handlePolicyViolation$lambda0((FragmentStrictMode.Policy) obj2, (Violation) obj);
                break;
            case 2:
                FragmentStrictMode.m11handlePolicyViolation$lambda1((String) obj2, (Violation) obj);
                break;
            case 3:
                DispatchQueue.dispatchAndEnqueue$lambda$2$lambda$1((DispatchQueue) obj2, (Runnable) obj);
                break;
            case 4:
                ((ProfileInstallerInitializer) obj2).lambda$delayAfterFirstFrame$0((Context) obj);
                break;
            case 5:
                PointsActivity pointsActivity = (PointsActivity) obj2;
                Exception exc = (Exception) obj;
                ProgressBar progressBar = pointsActivity.d;
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                }
                Toast.makeText(pointsActivity, "Error: " + exc.getMessage(), 1).show();
                break;
            default:
                SplashActivity splashActivity = (SplashActivity) obj2;
                int i3 = SplashActivity.f253f;
                splashActivity.getClass();
                if (!"close".equalsIgnoreCase((String) obj)) {
                    splashActivity.startActivity(new Intent(splashActivity, (Class<?>) (y.v(splashActivity) != null ? DashboardActivity.class : LoginActivity.class)));
                    splashActivity.finish();
                } else {
                    View viewInflate = splashActivity.getLayoutInflater().inflate(2131427383, (ViewGroup) null);
                    LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230825);
                    Dialog dialog = new Dialog(splashActivity);
                    dialog.requestWindowFeature(1);
                    dialog.setContentView(viewInflate);
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        dialog.getWindow().setLayout((int) (splashActivity.getResources().getDisplayMetrics().widthPixels * 0.9f), -2);
                    }
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    linearLayout.setOnClickListener(new com.google.android.material.snackbar.a(2, splashActivity, dialog));
                    dialog.show();
                }
                break;
        }
    }
}
