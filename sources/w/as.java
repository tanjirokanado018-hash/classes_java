package w;

import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class as extends CancellationException {
    public final transient ar b;

    public as(String str, Throwable th, ar arVar) {
        super(str);
        this.b = arVar;
        if (th != null) {
            initCause(th);
        }
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof as) {
                as asVar = (as) obj;
                if (!kotlin.jvm.internal.k.a(asVar.getMessage(), getMessage()) || !kotlin.jvm.internal.k.a(asVar.b, this.b) || !kotlin.jvm.internal.k.a(asVar.getCause(), getCause())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    public final int hashCode() {
        String message = getMessage();
        kotlin.jvm.internal.k.d(message);
        int iHashCode = (this.b.hashCode() + (message.hashCode() * 31)) * 31;
        Throwable cause = getCause();
        return iHashCode + (cause != null ? cause.hashCode() : 0);
    }

    @Override // java.lang.Throwable
    public final String toString() {
        return super.toString() + "; job=" + this.b;
    }
}
