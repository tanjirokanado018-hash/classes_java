package g;

import java.io.Serializable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class f implements Serializable {
    public static final Throwable a(Object obj) {
        if (obj instanceof e) {
            return ((e) obj).b;
        }
        return null;
    }
}
