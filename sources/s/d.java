package s;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d extends b implements a {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final d f502e = new d(1, 0, 1);

    public final boolean equals(Object obj) {
        if (obj instanceof d) {
            if (!isEmpty() || !((d) obj).isEmpty()) {
                d dVar = (d) obj;
                if (this.b == dVar.b) {
                    if (this.f499c == dVar.f499c) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override // s.a
    public final Comparable getEndInclusive() {
        return Integer.valueOf(this.f499c);
    }

    @Override // s.a
    public final Comparable getStart() {
        return Integer.valueOf(this.b);
    }

    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (this.b * 31) + this.f499c;
    }

    public final boolean isEmpty() {
        return this.b > this.f499c;
    }

    public final String toString() {
        return this.b + ".." + this.f499c;
    }
}
