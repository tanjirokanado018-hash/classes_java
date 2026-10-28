package kotlinx.coroutines.scheduling;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d extends g {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final d f482c = new d(l.b, l.f487c, l.d);

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
    }

    @Override // w.s
    public final String toString() {
        return "Dispatchers.Default";
    }
}
