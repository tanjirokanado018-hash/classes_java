package androidx.activity;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$1 extends l implements p.l {
    public static final ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$1 INSTANCE = new ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$1();

    public ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$1() {
        super(1);
    }

    @Override // p.l
    public final View invoke(View it) {
        k.g(it, "it");
        Object parent = it.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}
