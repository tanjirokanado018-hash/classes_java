package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f548a;
    public final p.l b;

    public n(Object obj, p.l lVar) {
        this.f548a = obj;
        this.b = lVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof n)) {
            return false;
        }
        n nVar = (n) obj;
        return kotlin.jvm.internal.k.a(this.f548a, nVar.f548a) && kotlin.jvm.internal.k.a(this.b, nVar.b);
    }

    public final int hashCode() {
        Object obj = this.f548a;
        return this.b.hashCode() + ((obj == null ? 0 : obj.hashCode()) * 31);
    }

    public final String toString() {
        return "CompletedWithCancellation(result=" + this.f548a + ", onCancellation=" + this.b + ')';
    }
}
