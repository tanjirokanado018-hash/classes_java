package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class o extends kotlin.jvm.internal.l implements p.p {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final o f549c = new o(0);
    public static final o d = new o(1);
    public final /* synthetic */ int b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o(int i2) {
        super(2);
        this.b = i2;
    }

    @Override // p.p
    public final Object invoke(Object obj, Object obj2) {
        switch (this.b) {
            case 0:
                return ((i.j) obj).plus((i.h) obj2);
            default:
                Boolean bool = (Boolean) obj;
                bool.getClass();
                return bool;
        }
    }
}
