package m;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Integer f496a;

    static {
        Integer num;
        Integer num2 = null;
        try {
            Object obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            num = obj instanceof Integer ? (Integer) obj : null;
        } catch (Throwable unused) {
        }
        if (num != null && num.intValue() > 0) {
            num2 = num;
        }
        f496a = num2;
    }
}
