package androidx.activity.contextaware;

import android.content.Context;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ContextAwareHelper {
    private volatile Context context;
    private final Set<OnContextAvailableListener> listeners = new CopyOnWriteArraySet();

    public final void addOnContextAvailableListener(OnContextAvailableListener listener) {
        k.g(listener, "listener");
        Context context = this.context;
        if (context != null) {
            listener.onContextAvailable(context);
        }
        this.listeners.add(listener);
    }

    public final void clearAvailableContext() {
        this.context = null;
    }

    public final void dispatchOnContextAvailable(Context context) {
        k.g(context, "context");
        this.context = context;
        Iterator<OnContextAvailableListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onContextAvailable(context);
        }
    }

    public final Context peekAvailableContext() {
        return this.context;
    }

    public final void removeOnContextAvailableListener(OnContextAvailableListener listener) {
        k.g(listener, "listener");
        this.listeners.remove(listener);
    }
}
