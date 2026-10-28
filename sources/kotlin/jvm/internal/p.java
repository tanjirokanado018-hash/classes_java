package kotlin.jvm.internal;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class p {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final q f437a;

    static {
        q qVar = null;
        try {
            qVar = (q) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (qVar == null) {
            qVar = new q();
        }
        f437a = qVar;
    }
}
