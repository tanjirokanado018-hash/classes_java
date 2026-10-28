package f;

import android.content.Intent;
import android.view.View;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.zenoatom.app.AccountsActivity;
import com.zenoatom.app.LoginActivity;

/* JADX INFO: renamed from: f.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class ViewOnClickListenerC0021a implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f306a;
    public final /* synthetic */ Object b;

    public /* synthetic */ ViewOnClickListenerC0021a(int i2, Object obj) {
        this.f306a = i2;
        this.b = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f306a;
        Object obj = this.b;
        switch (i2) {
            case 0:
                int i3 = AccountsActivity.b;
                ((AccountsActivity) obj).finish();
                break;
            case 1:
                AccountsActivity accountsActivity = (AccountsActivity) obj;
                int i4 = AccountsActivity.b;
                accountsActivity.getClass();
                Intent intent = new Intent(accountsActivity, (Class<?>) LoginActivity.class);
                intent.putExtra("ADD_ACCOUNT_MODE", true);
                accountsActivity.startActivity(intent);
                break;
            default:
                ((MaterialDatePicker) obj).lambda$initHeaderToggle$0(view);
                break;
        }
    }
}
