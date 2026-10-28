package f;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import com.zenoatom.app.LoginActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class au implements TextWatcher {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ LoginActivity f347a;

    public au(LoginActivity loginActivity) {
        this.f347a = loginActivity;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        String string = editable.toString();
        boolean z2 = string.length() == 11 && string.startsWith("097");
        LoginActivity loginActivity = this.f347a;
        Button button = loginActivity.f225s;
        if (button != null) {
            button.setEnabled(z2);
            loginActivity.f225s.setAlpha(z2 ? 1.0f : 0.45f);
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }
}
