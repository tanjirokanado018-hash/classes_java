package androidx.activity;

import android.view.View;
import kotlin.jvm.internal.k;
import u.j;
import u.m;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeFullyDrawnReporterOwner {
    public static final FullyDrawnReporterOwner get(View view) {
        k.g(view, "<this>");
        return (FullyDrawnReporterOwner) j.aa(j.ab(m.z(view, ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$1.INSTANCE), ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$2.INSTANCE));
    }

    public static final void set(View view, FullyDrawnReporterOwner fullyDrawnReporterOwner) {
        k.g(view, "<this>");
        k.g(fullyDrawnReporterOwner, "fullyDrawnReporterOwner");
        view.setTag(R.id.report_drawn, fullyDrawnReporterOwner);
    }
}
