package androidx.core.util;

import i.e;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class RunnableKt {
    public static final Runnable asRunnable(e eVar) {
        return new ContinuationRunnable(eVar);
    }
}
