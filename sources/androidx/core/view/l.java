package androidx.core.view;

import android.view.ViewConfiguration;
import androidx.core.util.Supplier;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class l implements Supplier {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f57a;
    public final /* synthetic */ ViewConfiguration b;

    public /* synthetic */ l(ViewConfiguration viewConfiguration, int i2) {
        this.f57a = i2;
        this.b = viewConfiguration;
    }

    @Override // androidx.core.util.Supplier
    public final Object get() {
        int i2 = this.f57a;
        ViewConfiguration viewConfiguration = this.b;
        switch (i2) {
            case 0:
                return Integer.valueOf(viewConfiguration.getScaledMaximumFlingVelocity());
            default:
                return Integer.valueOf(viewConfiguration.getScaledMinimumFlingVelocity());
        }
    }
}
