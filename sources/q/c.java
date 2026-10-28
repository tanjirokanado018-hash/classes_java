package q;

import java.util.Random;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends a {
    public final b d = new b();

    @Override // q.a
    public final Random c() {
        Object obj = this.d.get();
        k.f(obj, "implStorage.get()");
        return (Random) obj;
    }
}
