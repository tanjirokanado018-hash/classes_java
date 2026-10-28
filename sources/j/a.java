package j;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a {
    public static final a b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ a[] f430c;

    static {
        a aVar = new a("COROUTINE_SUSPENDED", 0);
        b = aVar;
        f430c = new a[]{aVar, new a("UNDECIDED", 1), new a("RESUMED", 2)};
    }

    public static a valueOf(String str) {
        return (a) Enum.valueOf(a.class, str);
    }

    public static a[] values() {
        return (a[]) f430c.clone();
    }
}
