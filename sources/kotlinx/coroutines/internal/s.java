package kotlinx.coroutines.internal;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class s extends kotlin.jvm.internal.l implements p.p {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final s f463c = new s(0);
    public static final s d = new s(1);

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final s f464e = new s(2);
    public final /* synthetic */ int b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ s(int i2) {
        super(2);
        this.b = i2;
    }

    @Override // p.p
    public final Object invoke(Object obj, Object obj2) {
        switch (this.b) {
            case 0:
                return obj;
            case 1:
                androidx.appcompat.graphics.drawable.a.j(obj);
                return null;
            default:
                return (t) obj;
        }
    }
}
