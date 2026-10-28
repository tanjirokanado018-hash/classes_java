package f;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.zenoatom.app.LoginActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class av implements TextWatcher {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ EditText[] f348a;
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ LoginActivity f349c;

    public av(LoginActivity loginActivity, EditText[] editTextArr, int i2) {
        this.f349c = loginActivity;
        this.f348a = editTextArr;
        this.b = i2;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        LoginActivity loginActivity = this.f349c;
        if (loginActivity.f211e) {
            return;
        }
        int length = editable.length();
        int i2 = this.b;
        EditText[] editTextArr = this.f348a;
        if (length != 1) {
            if (editable.length() == 0) {
                editTextArr[i2].setBackgroundResource(2131165375);
            }
        } else {
            editTextArr[i2].setBackgroundResource(2131165374);
            if (i2 >= editTextArr.length - 1 || editTextArr[i2 + 1] == null) {
                loginActivity.b.postDelayed(new o(5, this), 100L);
            } else {
                editTextArr[i2 + 1].requestFocus();
            }
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }
}
