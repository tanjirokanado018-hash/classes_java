package com.google.android.material.snackbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.SplashActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f136a;
    public final /* synthetic */ Object b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f137c;

    public /* synthetic */ a(int i2, Object obj, Object obj2) {
        this.f136a = i2;
        this.b = obj;
        this.f137c = obj2;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002b  */
    /* JADX WARN: Instruction removed from duplicated block: B:11:0x002b, please report this as an issue */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f136a;
        Object obj = this.f137c;
        Object obj2 = this.b;
        switch (i2) {
            case 0:
                ((Snackbar) obj2).lambda$setAction$0((View.OnClickListener) obj, view);
                break;
            case 1:
                DashboardActivity dashboardActivity = (DashboardActivity) obj2;
                String str = (String) obj;
                int i3 = DashboardActivity.L;
                if (str != null) {
                    dashboardActivity.getClass();
                    if (str.isEmpty()) {
                        str = "https://play.google.com/store/apps/details?id=" + dashboardActivity.getPackageName();
                    }
                } else {
                    str = "https://play.google.com/store/apps/details?id=" + dashboardActivity.getPackageName();
                }
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                    intent.addFlags(268435456);
                    dashboardActivity.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(dashboardActivity, "Link ဖွင့်၍ မရပါ", 0).show();
                    return;
                }
                break;
            default:
                SplashActivity splashActivity = (SplashActivity) obj2;
                int i4 = SplashActivity.f253f;
                splashActivity.getClass();
                ((Dialog) obj).dismiss();
                splashActivity.finishAffinity();
                break;
        }
    }
}
