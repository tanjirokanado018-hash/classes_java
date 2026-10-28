package androidx.core.os;

import android.os.Handler;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class HandlerKt {
    public static final Runnable postAtTime(Handler handler, long j2, Object obj, p.a aVar) {
        HandlerKt$postAtTime$runnable$1 handlerKt$postAtTime$runnable$1 = new HandlerKt$postAtTime$runnable$1(aVar);
        handler.postAtTime(handlerKt$postAtTime$runnable$1, obj, j2);
        return handlerKt$postAtTime$runnable$1;
    }

    public static /* synthetic */ Runnable postAtTime$default(Handler handler, long j2, Object obj, p.a aVar, int i2, Object obj2) {
        if ((i2 & 2) != 0) {
            obj = null;
        }
        HandlerKt$postAtTime$runnable$1 handlerKt$postAtTime$runnable$1 = new HandlerKt$postAtTime$runnable$1(aVar);
        handler.postAtTime(handlerKt$postAtTime$runnable$1, obj, j2);
        return handlerKt$postAtTime$runnable$1;
    }

    public static final Runnable postDelayed(Handler handler, long j2, Object obj, p.a aVar) {
        HandlerKt$postDelayed$runnable$1 handlerKt$postDelayed$runnable$1 = new HandlerKt$postDelayed$runnable$1(aVar);
        if (obj == null) {
            handler.postDelayed(handlerKt$postDelayed$runnable$1, j2);
        } else {
            HandlerCompat.postDelayed(handler, handlerKt$postDelayed$runnable$1, obj, j2);
        }
        return handlerKt$postDelayed$runnable$1;
    }

    public static /* synthetic */ Runnable postDelayed$default(Handler handler, long j2, Object obj, p.a aVar, int i2, Object obj2) {
        if ((i2 & 2) != 0) {
            obj = null;
        }
        HandlerKt$postDelayed$runnable$1 handlerKt$postDelayed$runnable$1 = new HandlerKt$postDelayed$runnable$1(aVar);
        if (obj == null) {
            handler.postDelayed(handlerKt$postDelayed$runnable$1, j2);
        } else {
            HandlerCompat.postDelayed(handler, handlerKt$postDelayed$runnable$1, obj, j2);
        }
        return handlerKt$postDelayed$runnable$1;
    }
}
