package r;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a extends q.a {
    @Override // q.a
    public final Random c() {
        ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
        k.f(threadLocalRandomCurrent, "current()");
        return threadLocalRandomCurrent;
    }
}
