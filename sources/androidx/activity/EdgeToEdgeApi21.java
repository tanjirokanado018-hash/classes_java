package androidx.activity;

import android.view.View;
import android.view.Window;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.core.view.WindowCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(21)
final class EdgeToEdgeApi21 implements EdgeToEdgeImpl {
    @Override // androidx.activity.EdgeToEdgeImpl
    @DoNotInline
    public void setUp(SystemBarStyle statusBarStyle, SystemBarStyle navigationBarStyle, Window window, View view, boolean z2, boolean z3) {
        k.g(statusBarStyle, "statusBarStyle");
        k.g(navigationBarStyle, "navigationBarStyle");
        k.g(window, "window");
        k.g(view, "view");
        WindowCompat.setDecorFitsSystemWindows(window, false);
        window.addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.addFlags(134217728);
    }
}
