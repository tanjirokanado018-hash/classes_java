package androidx.activity;

import android.view.View;
import android.view.Window;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
final class EdgeToEdgeApi29 implements EdgeToEdgeImpl {
    @Override // androidx.activity.EdgeToEdgeImpl
    @DoNotInline
    public void setUp(SystemBarStyle statusBarStyle, SystemBarStyle navigationBarStyle, Window window, View view, boolean z2, boolean z3) {
        k.g(statusBarStyle, "statusBarStyle");
        k.g(navigationBarStyle, "navigationBarStyle");
        k.g(window, "window");
        k.g(view, "view");
        WindowCompat.setDecorFitsSystemWindows(window, false);
        window.setStatusBarColor(statusBarStyle.getScrimWithEnforcedContrast$activity_release(z2));
        window.setNavigationBarColor(navigationBarStyle.getScrimWithEnforcedContrast$activity_release(z3));
        window.setStatusBarContrastEnforced(false);
        window.setNavigationBarContrastEnforced(navigationBarStyle.getNightMode$activity_release() == 0);
        WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, view);
        windowInsetsControllerCompat.setAppearanceLightStatusBars(!z2);
        windowInsetsControllerCompat.setAppearanceLightNavigationBars(true ^ z3);
    }
}
