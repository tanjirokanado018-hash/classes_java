package w;

import java.util.concurrent.CancellationException;

/* JADX INFO: renamed from: w.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class C0042l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f544a;
    public final AbstractC0034d b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final p.l f545c;
    public final Object d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final Throwable f546e;

    public C0042l(Object obj, AbstractC0034d abstractC0034d, p.l lVar, Object obj2, Throwable th) {
        this.f544a = obj;
        this.b = abstractC0034d;
        this.f545c = lVar;
        this.d = obj2;
        this.f546e = th;
    }

    public static C0042l a(C0042l c0042l, AbstractC0034d abstractC0034d, CancellationException cancellationException, int i2) {
        Object obj = c0042l.f544a;
        if ((i2 & 2) != 0) {
            abstractC0034d = c0042l.b;
        }
        AbstractC0034d abstractC0034d2 = abstractC0034d;
        p.l lVar = c0042l.f545c;
        Object obj2 = c0042l.d;
        Throwable th = cancellationException;
        if ((i2 & 16) != 0) {
            th = c0042l.f546e;
        }
        c0042l.getClass();
        return new C0042l(obj, abstractC0034d2, lVar, obj2, th);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0042l)) {
            return false;
        }
        C0042l c0042l = (C0042l) obj;
        return kotlin.jvm.internal.k.a(this.f544a, c0042l.f544a) && kotlin.jvm.internal.k.a(this.b, c0042l.b) && kotlin.jvm.internal.k.a(this.f545c, c0042l.f545c) && kotlin.jvm.internal.k.a(this.d, c0042l.d) && kotlin.jvm.internal.k.a(this.f546e, c0042l.f546e);
    }

    public final int hashCode() {
        Object obj = this.f544a;
        int iHashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        AbstractC0034d abstractC0034d = this.b;
        int iHashCode2 = (iHashCode + (abstractC0034d == null ? 0 : abstractC0034d.hashCode())) * 31;
        p.l lVar = this.f545c;
        int iHashCode3 = (iHashCode2 + (lVar == null ? 0 : lVar.hashCode())) * 31;
        Object obj2 = this.d;
        int iHashCode4 = (iHashCode3 + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Throwable th = this.f546e;
        return iHashCode4 + (th != null ? th.hashCode() : 0);
    }

    public final String toString() {
        return "CompletedContinuation(result=" + this.f544a + ", cancelHandler=" + this.b + ", onCancellation=" + this.f545c + ", idempotentResume=" + this.d + ", cancelCause=" + this.f546e + ')';
    }

    public /* synthetic */ C0042l(Object obj, AbstractC0034d abstractC0034d, p.l lVar, CancellationException cancellationException, int i2) {
        this(obj, (i2 & 2) != 0 ? null : abstractC0034d, (i2 & 4) != 0 ? null : lVar, (Object) null, (i2 & 16) != 0 ? null : cancellationException);
    }
}
