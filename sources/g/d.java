package g;

import java.io.Serializable;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d implements Serializable {
    public final Object b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Object f420c;

    public d(Object obj, Object obj2) {
        this.b = obj;
        this.f420c = obj2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return k.a(this.b, dVar.b) && k.a(this.f420c, dVar.f420c);
    }

    public final int hashCode() {
        Object obj = this.b;
        int iHashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        Object obj2 = this.f420c;
        return iHashCode + (obj2 != null ? obj2.hashCode() : 0);
    }

    public final String toString() {
        return "(" + this.b + ", " + this.f420c + ')';
    }
}
