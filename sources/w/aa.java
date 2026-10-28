package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class aa {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f520a = 0;

    static {
        String property;
        int i2 = kotlinx.coroutines.internal.r.f462a;
        try {
            property = System.getProperty("kotlinx.coroutines.main.delay");
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null || !Boolean.parseBoolean(property)) {
            z zVar = z.f559f;
            return;
        }
        kotlinx.coroutines.scheduling.d dVar = af.f522a;
        i.h hVar = kotlinx.coroutines.internal.l.f459a;
        x.c cVar = ((x.c) hVar).f562e;
        if (hVar instanceof ab) {
        } else {
            z zVar2 = z.f559f;
        }
    }
}
