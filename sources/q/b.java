package q;

import java.util.Random;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class b extends ThreadLocal {
    @Override // java.lang.ThreadLocal
    public final Object initialValue() {
        return new Random();
    }
}
