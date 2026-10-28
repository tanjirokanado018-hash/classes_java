package e;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface a extends Future {
    void addListener(Runnable runnable, Executor executor);
}
