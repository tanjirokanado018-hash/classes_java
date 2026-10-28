package androidx.activity;

import android.view.View;
import android.view.Window;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class EdgeToEdgeBase implements EdgeToEdgeImpl {
    @Override // androidx.activity.EdgeToEdgeImpl
    public void setUp(SystemBarStyle statusBarStyle, SystemBarStyle navigationBarStyle, Window window, View view, boolean z2, boolean z3) {
        k.g(statusBarStyle, "statusBarStyle");
        k.g(navigationBarStyle, "navigationBarStyle");
        k.g(window, "window");
        k.g(view, "view");
    }
}
