package androidx.core.view;

import android.os.Build;
import android.view.ViewStructure;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ViewStructureCompat {
    private final Object mWrappedObj;

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static void setClassName(ViewStructure viewStructure, String str) {
            viewStructure.setClassName(str);
        }

        @DoNotInline
        public static void setContentDescription(ViewStructure viewStructure, CharSequence charSequence) {
            viewStructure.setContentDescription(charSequence);
        }

        @DoNotInline
        public static void setDimens(ViewStructure viewStructure, int i2, int i3, int i4, int i5, int i6, int i7) {
            viewStructure.setDimens(i2, i3, i4, i5, i6, i7);
        }

        @DoNotInline
        public static void setText(ViewStructure viewStructure, CharSequence charSequence) {
            viewStructure.setText(charSequence);
        }
    }

    private ViewStructureCompat(@NonNull ViewStructure viewStructure) {
        this.mWrappedObj = viewStructure;
    }

    @NonNull
    @RequiresApi(23)
    public static ViewStructureCompat toViewStructureCompat(@NonNull ViewStructure viewStructure) {
        return new ViewStructureCompat(viewStructure);
    }

    public void setClassName(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setClassName(androidx.core.text.a.g(this.mWrappedObj), str);
        }
    }

    public void setContentDescription(@NonNull CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setContentDescription(androidx.core.text.a.g(this.mWrappedObj), charSequence);
        }
    }

    public void setDimens(int i2, int i3, int i4, int i5, int i6, int i7) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setDimens(androidx.core.text.a.g(this.mWrappedObj), i2, i3, i4, i5, i6, i7);
        }
    }

    public void setText(@NonNull CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setText(androidx.core.text.a.g(this.mWrappedObj), charSequence);
        }
    }

    @NonNull
    @RequiresApi(23)
    public ViewStructure toViewStructure() {
        return androidx.core.text.a.g(this.mWrappedObj);
    }
}
