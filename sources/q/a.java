package q;

import java.util.Random;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a extends e {
    @Override // q.e
    public final int a() {
        return c().nextInt();
    }

    @Override // q.e
    public final int b() {
        return c().nextInt(2147418112);
    }

    public abstract Random c();
}
