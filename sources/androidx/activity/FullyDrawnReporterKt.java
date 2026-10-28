package androidx.activity;

import g.i;
import p.l;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class FullyDrawnReporterKt {

    /* JADX INFO: renamed from: androidx.activity.FullyDrawnReporterKt$reportWhenComplete$1, reason: invalid class name */
    @k.e(c = "androidx.activity.FullyDrawnReporterKt", f = "FullyDrawnReporter.kt", l = {185}, m = "reportWhenComplete")
    public static final class AnonymousClass1 extends k.c {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(i.e eVar) {
            super(eVar);
        }

        @Override // k.a
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FullyDrawnReporterKt.reportWhenComplete(null, null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public static final Object reportWhenComplete(FullyDrawnReporter fullyDrawnReporter, l lVar, i.e eVar) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (eVar instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) eVar;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(eVar);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(eVar);
        }
        Object obj = anonymousClass1.result;
        Object obj2 = j.a.b;
        int i3 = anonymousClass1.label;
        i iVar = i.f423a;
        try {
            if (i3 == 0) {
                y.aj(obj);
                fullyDrawnReporter.addReporter();
                if (fullyDrawnReporter.isFullyDrawnReported()) {
                    return iVar;
                }
                anonymousClass1.L$0 = fullyDrawnReporter;
                anonymousClass1.label = 1;
                if (lVar.invoke(anonymousClass1) == obj2) {
                    return obj2;
                }
            } else {
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                fullyDrawnReporter = (FullyDrawnReporter) anonymousClass1.L$0;
                y.aj(obj);
            }
            fullyDrawnReporter.removeReporter();
            return iVar;
        } catch (Throwable th) {
            fullyDrawnReporter.removeReporter();
            throw th;
        }
    }

    private static final Object reportWhenComplete$$forInline(FullyDrawnReporter fullyDrawnReporter, l lVar, i.e eVar) {
        fullyDrawnReporter.addReporter();
        boolean zIsFullyDrawnReported = fullyDrawnReporter.isFullyDrawnReported();
        i iVar = i.f423a;
        if (zIsFullyDrawnReported) {
            return iVar;
        }
        try {
            lVar.invoke(eVar);
            return iVar;
        } finally {
            fullyDrawnReporter.removeReporter();
        }
    }
}
