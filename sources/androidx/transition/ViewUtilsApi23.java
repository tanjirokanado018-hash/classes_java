package androidx.transition;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(23)
class ViewUtilsApi23 extends ViewUtilsApi22 {
    private static boolean sTryHiddenSetTransitionVisibility = true;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static void setTransitionVisibility(View view, int i2) {
            view.setTransitionVisibility(i2);
        }
    }

    @Override // androidx.transition.ViewUtilsApi19
    @SuppressLint({"NewApi"})
    public void setTransitionVisibility(@NonNull View view, int i2) {
        if (Build.VERSION.SDK_INT == 28) {
            super.setTransitionVisibility(view, i2);
        } else if (sTryHiddenSetTransitionVisibility) {
            try {
                Api29Impl.setTransitionVisibility(view, i2);
            } catch (NoSuchMethodError unused) {
                sTryHiddenSetTransitionVisibility = false;
            }
        }
    }
}
