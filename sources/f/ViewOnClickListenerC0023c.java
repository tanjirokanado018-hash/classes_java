package f;

import android.app.Dialog;
import android.view.View;
import com.zenoatom.app.AccountsActivity;
import com.zenoatom.app.CityRunActivity;
import com.zenoatom.app.ClaimActivity;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.PointsActivity;

/* JADX INFO: renamed from: f.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class ViewOnClickListenerC0023c implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f357a;
    public final /* synthetic */ Dialog b;

    public /* synthetic */ ViewOnClickListenerC0023c(Dialog dialog, int i2) {
        this.f357a = i2;
        this.b = dialog;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f357a;
        Dialog dialog = this.b;
        switch (i2) {
            case 0:
                int i3 = AccountsActivity.b;
                dialog.dismiss();
                break;
            case 1:
                int i4 = CityRunActivity.f151p;
                dialog.dismiss();
                break;
            case 2:
                int i5 = CityRunActivity.f151p;
                dialog.dismiss();
                break;
            case 3:
                int i6 = CityRunActivity.f151p;
                dialog.dismiss();
                break;
            case 4:
                int i7 = ClaimActivity.f165g;
                dialog.dismiss();
                break;
            case 5:
                int i8 = DashboardActivity.L;
                dialog.dismiss();
                break;
            case 6:
                int i9 = DashboardActivity.L;
                dialog.dismiss();
                break;
            case 7:
                int i10 = PointsActivity.ai;
                dialog.dismiss();
                break;
            case 8:
                int i11 = PointsActivity.ai;
                dialog.dismiss();
                break;
            default:
                int i12 = PointsActivity.ai;
                dialog.dismiss();
                break;
        }
    }
}
