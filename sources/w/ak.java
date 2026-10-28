package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class ak extends s {
    public long b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f524c;
    public kotlinx.coroutines.internal.a d;

    public final void b() {
        long j2 = this.b - 4294967296L;
        this.b = j2;
        if (j2 <= 0 && this.f524c) {
            g();
        }
    }

    public final void c(ae aeVar) {
        kotlinx.coroutines.internal.a aVar = this.d;
        if (aVar == null) {
            aVar = new kotlinx.coroutines.internal.a();
            aVar.f438a = new Object[16];
            this.d = aVar;
        }
        Object[] objArr = aVar.f438a;
        int i2 = aVar.f439c;
        objArr[i2] = aeVar;
        int length = (objArr.length - 1) & (i2 + 1);
        aVar.f439c = length;
        int i3 = aVar.b;
        if (length == i3) {
            int length2 = objArr.length;
            Object[] objArr2 = new Object[length2 << 1];
            h.d.ap(objArr, objArr2, 0, i3, 0, 10);
            Object[] objArr3 = aVar.f438a;
            int length3 = objArr3.length;
            int i4 = aVar.b;
            h.d.ap(objArr3, objArr2, length3 - i4, 0, i4, 4);
            aVar.f438a = objArr2;
            aVar.b = 0;
            aVar.f439c = length2;
        }
    }

    public abstract Thread d();

    public final void e(boolean z2) {
        this.b = (z2 ? 4294967296L : 1L) + this.b;
        if (z2) {
            return;
        }
        this.f524c = true;
    }

    public final boolean f() {
        kotlinx.coroutines.internal.a aVar = this.d;
        if (aVar == null) {
            return false;
        }
        int i2 = aVar.b;
        Object obj = null;
        if (i2 != aVar.f439c) {
            Object[] objArr = aVar.f438a;
            Object obj2 = objArr[i2];
            objArr[i2] = null;
            aVar.b = (i2 + 1) & (objArr.length - 1);
            if (obj2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type T of kotlinx.coroutines.internal.ArrayQueue");
            }
            obj = obj2;
        }
        ae aeVar = (ae) obj;
        if (aeVar == null) {
            return false;
        }
        aeVar.run();
        return true;
    }

    public abstract void g();

    @Override // w.s
    public final s limitedParallelism(int i2) {
        kotlin.jvm.internal.k.h(i2);
        return this;
    }
}
