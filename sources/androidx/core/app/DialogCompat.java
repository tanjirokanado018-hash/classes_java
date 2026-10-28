package androidx.core.app;

import android.app.Dialog;
import android.os.Build;
import android.view.View;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DialogCompat {

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        public static <T> T requireViewById(Dialog dialog, int i2) {
            return (T) dialog.requireViewById(i2);
        }
    }

    private DialogCompat() {
    }

    @NonNull
    public static View requireViewById(@NonNull Dialog dialog, int i2) {
        if (Build.VERSION.SDK_INT >= 28) {
            return (View) Api28Impl.requireViewById(dialog, i2);
        }
        View viewFindViewById = dialog.findViewById(i2);
        if (viewFindViewById != null) {
            return viewFindViewById;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
    }
}
