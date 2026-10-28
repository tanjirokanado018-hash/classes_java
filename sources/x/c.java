package x;

import android.os.Handler;
import android.os.Looper;
import i.j;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.l;
import w.ab;
import w.af;
import w.ar;
import w.ay;
import w.az;
import w.t;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends az implements ab {
    private volatile c _immediate;
    public final Handler b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final String f561c;
    public final boolean d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final c f562e;

    public c(Handler handler) {
        this(handler, null, false);
    }

    @Override // w.s
    public final void dispatch(j jVar, Runnable runnable) {
        if (this.b.post(runnable)) {
            return;
        }
        CancellationException cancellationException = new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed");
        ar arVar = (ar) jVar.get(t.f551c);
        if (arVar != null) {
            ((ay) arVar).e(cancellationException);
        }
        af.b.dispatch(jVar, runnable);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof c) && ((c) obj).b == this.b;
    }

    public final int hashCode() {
        return System.identityHashCode(this.b);
    }

    @Override // w.s
    public final boolean isDispatchNeeded(j jVar) {
        return (this.d && k.a(Looper.myLooper(), this.b.getLooper())) ? false : true;
    }

    @Override // w.s
    public final String toString() {
        c cVar;
        String str;
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        az azVar = l.f459a;
        if (this == azVar) {
            str = "Dispatchers.Main";
        } else {
            try {
                cVar = ((c) azVar).f562e;
            } catch (UnsupportedOperationException unused) {
                cVar = null;
            }
            str = this == cVar ? "Dispatchers.Main.immediate" : null;
        }
        if (str != null) {
            return str;
        }
        String string = this.f561c;
        if (string == null) {
            string = this.b.toString();
        }
        return this.d ? androidx.appcompat.graphics.drawable.a.e(string, ".immediate") : string;
    }

    public c(Handler handler, String str, boolean z2) {
        this.b = handler;
        this.f561c = str;
        this.d = z2;
        this._immediate = z2 ? this : null;
        c cVar = this._immediate;
        if (cVar == null) {
            cVar = new c(handler, str, true);
            this._immediate = cVar;
        }
        this.f562e = cVar;
    }
}
