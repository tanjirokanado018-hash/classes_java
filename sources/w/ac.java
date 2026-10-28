package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ac extends RuntimeException {
    public final i.j b;

    public ac(i.j jVar) {
        this.b = jVar;
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    @Override // java.lang.Throwable
    public final String getLocalizedMessage() {
        return this.b.toString();
    }
}
