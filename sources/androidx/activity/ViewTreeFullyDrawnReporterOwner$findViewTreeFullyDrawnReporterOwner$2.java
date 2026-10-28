package androidx.activity;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$2 extends l implements p.l {
    public static final ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$2 INSTANCE = new ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$2();

    public ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$2() {
        super(1);
    }

    @Override // p.l
    public final FullyDrawnReporterOwner invoke(View it) {
        k.g(it, "it");
        Object tag = it.getTag(R.id.report_drawn);
        if (tag instanceof FullyDrawnReporterOwner) {
            return (FullyDrawnReporterOwner) tag;
        }
        return null;
    }
}
