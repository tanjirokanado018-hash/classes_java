package f;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.zenoatom.app.LoginActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class aw implements View.OnKeyListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ EditText[] f350a;
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ LoginActivity f351c;

    public aw(LoginActivity loginActivity, EditText[] editTextArr, int i2) {
        this.f351c = loginActivity;
        this.f350a = editTextArr;
        this.b = i2;
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (i2 == 67 && keyEvent.getAction() == 0) {
            EditText[] editTextArr = this.f350a;
            int i3 = this.b;
            if (editTextArr[i3].getText().toString().isEmpty() && i3 > 0 && editTextArr[i3 - 1] != null) {
                LoginActivity loginActivity = this.f351c;
                loginActivity.f211e = true;
                editTextArr[i3 - 1].setText("");
                editTextArr[i3 - 1].setBackgroundResource(2131165375);
                editTextArr[i3 - 1].requestFocus();
                loginActivity.f211e = false;
                return true;
            }
        }
        return false;
    }
}
