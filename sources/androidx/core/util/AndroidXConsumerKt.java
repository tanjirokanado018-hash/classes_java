package androidx.core.util;

import i.e;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class AndroidXConsumerKt {
    public static final <T> Consumer<T> asAndroidXConsumer(e eVar) {
        return new AndroidXContinuationConsumer(eVar);
    }
}
