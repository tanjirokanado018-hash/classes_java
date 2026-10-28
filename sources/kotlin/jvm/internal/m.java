package kotlin.jvm.internal;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class m implements d {
    public final Class b;

    public m(Class jClass) {
        k.g(jClass, "jClass");
        this.b = jClass;
    }

    @Override // kotlin.jvm.internal.d
    public final Class a() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof m) {
            if (k.a(this.b, ((m) obj).b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.b.hashCode();
    }

    public final String toString() {
        return this.b.toString() + " (Kotlin reflection is not available)";
    }
}
