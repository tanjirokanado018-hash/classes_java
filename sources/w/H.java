package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class H {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final ThreadLocal f517a = new ThreadLocal();

    public static ak a() {
        ThreadLocal threadLocal = f517a;
        ak akVar = (ak) threadLocal.get();
        if (akVar != null) {
            return akVar;
        }
        C0033c c0033c = new C0033c(Thread.currentThread());
        threadLocal.set(c0033c);
        return c0033c;
    }
}
