package y;

import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c implements b {
    private volatile /* synthetic */ Object _state;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f563a;

    public c(Object obj) {
        this._state = obj;
    }

    public final void a(Object obj) {
        int i2;
        if (obj == null) {
            obj = z.a.f564a;
        }
        synchronized (this) {
            if (!k.a(this._state, obj)) {
                this._state = obj;
                int i3 = this.f563a;
                if ((i3 & 1) == 0) {
                    int i4 = i3 + 1;
                    this.f563a = i4;
                    while (true) {
                        synchronized (this) {
                            i2 = this.f563a;
                            if (i2 == i4) {
                                break;
                            }
                        }
                        i4 = i2;
                    }
                    this.f563a = i4 + 1;
                } else {
                    this.f563a = i3 + 2;
                }
            }
        }
    }
}
