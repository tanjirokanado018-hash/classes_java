package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class j {
    private volatile /* synthetic */ Object _next = null;
    private volatile /* synthetic */ long _state = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final int f457a;
    public final boolean b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f458c;
    public final /* synthetic */ AtomicReferenceArray d;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final q f456g = new q("REMOVE_FROZEN");

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final /* synthetic */ AtomicReferenceFieldUpdater f454e = AtomicReferenceFieldUpdater.newUpdater(j.class, Object.class, "_next");

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final /* synthetic */ AtomicLongFieldUpdater f455f = AtomicLongFieldUpdater.newUpdater(j.class, "_state");

    public j(int i2, boolean z2) {
        this.f457a = i2;
        this.b = z2;
        int i3 = i2 - 1;
        this.f458c = i3;
        this.d = new AtomicReferenceArray(i2);
        if (i3 > 1073741823) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if ((i2 & i3) != 0) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    public final int a(Object obj) {
        while (true) {
            long j2 = this._state;
            if ((3458764513820540928L & j2) != 0) {
                return (2305843009213693952L & j2) != 0 ? 2 : 1;
            }
            int i2 = (int) (1073741823 & j2);
            int i3 = (int) ((1152921503533105152L & j2) >> 30);
            int i4 = this.f458c;
            if (((i3 + 2) & i4) == (i2 & i4)) {
                return 1;
            }
            if (!this.b && this.d.get(i3 & i4) != null) {
                int i5 = this.f457a;
                if (i5 < 1024 || ((i3 - i2) & 1073741823) > (i5 >> 1)) {
                    return 1;
                }
            } else if (f455f.compareAndSet(this, j2, ((-1152921503533105153L) & j2) | (((long) ((i3 + 1) & 1073741823)) << 30))) {
                this.d.set(i3 & i4, obj);
                j jVarE = this;
                while ((jVarE._state & 1152921504606846976L) != 0) {
                    jVarE = jVarE.e();
                    AtomicReferenceArray atomicReferenceArray = jVarE.d;
                    int i6 = jVarE.f458c & i3;
                    Object obj2 = atomicReferenceArray.get(i6);
                    if ((obj2 instanceof i) && ((i) obj2).f453a == i3) {
                        atomicReferenceArray.set(i6, obj);
                    } else {
                        jVarE = null;
                    }
                    if (jVarE == null) {
                        return 0;
                    }
                }
                return 0;
            }
        }
    }

    public final boolean b() {
        long j2;
        do {
            j2 = this._state;
            if ((j2 & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j2) != 0) {
                return false;
            }
        } while (!f455f.compareAndSet(this, j2, j2 | 2305843009213693952L));
        return true;
    }

    public final int c() {
        long j2 = this._state;
        return 1073741823 & (((int) ((j2 & 1152921503533105152L) >> 30)) - ((int) (1073741823 & j2)));
    }

    public final boolean d() {
        long j2 = this._state;
        return ((int) (1073741823 & j2)) == ((int) ((j2 & 1152921503533105152L) >> 30));
    }

    public final j e() {
        long j2;
        while (true) {
            j2 = this._state;
            if ((j2 & 1152921504606846976L) != 0) {
                break;
            }
            long j3 = j2 | 1152921504606846976L;
            if (f455f.compareAndSet(this, j2, j3)) {
                j2 = j3;
                break;
            }
        }
        while (true) {
            j jVar = (j) this._next;
            if (jVar != null) {
                return jVar;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f454e;
            j jVar2 = new j(this.f457a * 2, this.b);
            int i2 = (int) (1073741823 & j2);
            int i3 = (int) ((1152921503533105152L & j2) >> 30);
            while (true) {
                int i4 = this.f458c;
                int i5 = i2 & i4;
                if (i5 == (i4 & i3)) {
                    break;
                }
                Object iVar = this.d.get(i5);
                if (iVar == null) {
                    iVar = new i(i2);
                }
                jVar2.d.set(jVar2.f458c & i2, iVar);
                i2++;
            }
            jVar2._state = (-1152921504606846977L) & j2;
            while (!atomicReferenceFieldUpdater.compareAndSet(this, null, jVar2) && atomicReferenceFieldUpdater.get(this) == null) {
            }
        }
    }

    public final Object f() {
        while (true) {
            long j2 = this._state;
            if ((j2 & 1152921504606846976L) != 0) {
                return f456g;
            }
            int i2 = (int) (j2 & 1073741823);
            int i3 = this.f458c;
            int i4 = ((int) ((1152921503533105152L & j2) >> 30)) & i3;
            int i5 = i3 & i2;
            if (i4 == i5) {
                return null;
            }
            Object obj = this.d.get(i5);
            if (obj == null) {
                if (this.b) {
                    return null;
                }
            } else {
                if (obj instanceof i) {
                    return null;
                }
                long j3 = (i2 + 1) & 1073741823;
                if (f455f.compareAndSet(this, j2, (j2 & (-1073741824)) | j3)) {
                    this.d.set(this.f458c & i2, null);
                    return obj;
                }
                if (this.b) {
                    j jVarE = this;
                    while (true) {
                        long j4 = jVarE._state;
                        int i6 = (int) (j4 & 1073741823);
                        if ((j4 & 1152921504606846976L) != 0) {
                            jVarE = jVarE.e();
                        } else {
                            if (f455f.compareAndSet(jVarE, j4, (j4 & (-1073741824)) | j3)) {
                                jVarE.d.set(jVarE.f458c & i6, null);
                                jVarE = null;
                            } else {
                                continue;
                            }
                        }
                        if (jVarE == null) {
                            return obj;
                        }
                    }
                }
            }
        }
    }
}
