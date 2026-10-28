package s;

import h.j;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends j {
    public final int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f500c;
    public boolean d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f501e;

    public c(int i2, int i3, int i4) {
        this.b = i4;
        this.f500c = i3;
        boolean z2 = true;
        if (i4 <= 0 ? i2 < i3 : i2 > i3) {
            z2 = false;
        }
        this.d = z2;
        this.f501e = z2 ? i2 : i3;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.d;
    }

    @Override // h.j
    public final int nextInt() {
        int i2 = this.f501e;
        if (i2 != this.f500c) {
            this.f501e = this.b + i2;
        } else {
            if (!this.d) {
                throw new NoSuchElementException();
            }
            this.d = false;
        }
        return i2;
    }
}
