package w;

import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class aw implements an {
    private volatile /* synthetic */ Object _rootCause;
    public final A b;
    private volatile /* synthetic */ int _isCompleting = 0;
    private volatile /* synthetic */ Object _exceptionsHolder = null;

    public aw(A a2, Throwable th) {
        this.b = a2;
        this._rootCause = th;
    }

    @Override // w.an
    public final boolean a() {
        return ((Throwable) this._rootCause) == null;
    }

    public final void b(Throwable th) {
        Throwable th2 = (Throwable) this._rootCause;
        if (th2 == null) {
            this._rootCause = th;
            return;
        }
        if (th == th2) {
            return;
        }
        Object obj = this._exceptionsHolder;
        if (obj == null) {
            this._exceptionsHolder = th;
            return;
        }
        if (!(obj instanceof Throwable)) {
            if (obj instanceof ArrayList) {
                ((ArrayList) obj).add(th);
                return;
            } else {
                throw new IllegalStateException(("State is " + obj).toString());
            }
        }
        if (th == obj) {
            return;
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(th);
        this._exceptionsHolder = arrayList;
    }

    public final Throwable c() {
        return (Throwable) this._rootCause;
    }

    @Override // w.an
    public final A d() {
        return this.b;
    }

    public final boolean e() {
        return ((Throwable) this._rootCause) != null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    public final boolean f() {
        return this._isCompleting;
    }

    public final boolean g() {
        return this._exceptionsHolder == y.f556f;
    }

    public final ArrayList h(Throwable th) {
        ArrayList arrayList;
        Object obj = this._exceptionsHolder;
        if (obj == null) {
            arrayList = new ArrayList(4);
        } else if (obj instanceof Throwable) {
            ArrayList arrayList2 = new ArrayList(4);
            arrayList2.add(obj);
            arrayList = arrayList2;
        } else {
            if (!(obj instanceof ArrayList)) {
                throw new IllegalStateException(("State is " + obj).toString());
            }
            arrayList = (ArrayList) obj;
        }
        Throwable th2 = (Throwable) this._rootCause;
        if (th2 != null) {
            arrayList.add(0, th2);
        }
        if (th != null && !kotlin.jvm.internal.k.a(th, th2)) {
            arrayList.add(th);
        }
        this._exceptionsHolder = y.f556f;
        return arrayList;
    }

    public final void i() {
        this._isCompleting = 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [boolean, int] */
    public final String toString() {
        return "Finishing[cancelling=" + e() + ", completing=" + ((boolean) this._isCompleting) + ", rootCause=" + ((Throwable) this._rootCause) + ", exceptions=" + this._exceptionsHolder + ", list=" + this.b + ']';
    }
}
