package g;

import java.io.Serializable;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class e implements Serializable {
    public final Throwable b;

    public e(Throwable exception) {
        k.g(exception, "exception");
        this.b = exception;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof e) {
            if (k.a(this.b, ((e) obj).b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.b.hashCode();
    }

    public final String toString() {
        return "Failure(" + this.b + ')';
    }
}
