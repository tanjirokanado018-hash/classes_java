package androidx.activity;

import androidx.annotation.MainThread;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class OnBackPressedCallback {
    private final CopyOnWriteArrayList<Cancellable> cancellables = new CopyOnWriteArrayList<>();
    private p.a enabledChangedCallback;
    private boolean isEnabled;

    public OnBackPressedCallback(boolean z2) {
        this.isEnabled = z2;
    }

    public final void addCancellable(Cancellable cancellable) {
        k.g(cancellable, "cancellable");
        this.cancellables.add(cancellable);
    }

    public final p.a getEnabledChangedCallback$activity_release() {
        return this.enabledChangedCallback;
    }

    @MainThread
    public void handleOnBackCancelled() {
    }

    @MainThread
    public abstract void handleOnBackPressed();

    @MainThread
    public void handleOnBackProgressed(BackEventCompat backEvent) {
        k.g(backEvent, "backEvent");
    }

    @MainThread
    public void handleOnBackStarted(BackEventCompat backEvent) {
        k.g(backEvent, "backEvent");
    }

    @MainThread
    public final boolean isEnabled() {
        return this.isEnabled;
    }

    @MainThread
    public final void remove() {
        Iterator<T> it = this.cancellables.iterator();
        while (it.hasNext()) {
            ((Cancellable) it.next()).cancel();
        }
    }

    public final void removeCancellable(Cancellable cancellable) {
        k.g(cancellable, "cancellable");
        this.cancellables.remove(cancellable);
    }

    @MainThread
    public final void setEnabled(boolean z2) {
        this.isEnabled = z2;
        p.a aVar = this.enabledChangedCallback;
        if (aVar != null) {
            aVar.invoke();
        }
    }

    public final void setEnabledChangedCallback$activity_release(p.a aVar) {
        this.enabledChangedCallback = aVar;
    }
}
