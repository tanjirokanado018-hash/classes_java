package androidx.transition;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(22)
class ViewUtilsApi22 extends ViewUtilsApi21 {
    private static boolean sTryHiddenSetLeftTopRightBottom = true;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static void setLeftTopRightBottom(View view, int i2, int i3, int i4, int i5) {
            view.setLeftTopRightBottom(i2, i3, i4, i5);
        }
    }

    @Override // androidx.transition.ViewUtilsApi19
    @SuppressLint({"NewApi"})
    public void setLeftTopRightBottom(@NonNull View view, int i2, int i3, int i4, int i5) {
        if (sTryHiddenSetLeftTopRightBottom) {
            try {
                Api29Impl.setLeftTopRightBottom(view, i2, i3, i4, i5);
            } catch (NoSuchMethodError unused) {
                sTryHiddenSetLeftTopRightBottom = false;
            }
        }
    }
}
