package androidx.activity;

import android.os.Build;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.annotation.DoNotInline;
import androidx.annotation.MainThread;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Consumer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import g.i;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class OnBackPressedDispatcher {
    private boolean backInvokedCallbackRegistered;
    private final Runnable fallbackOnBackPressed;
    private boolean hasEnabledCallbacks;
    private OnBackPressedCallback inProgressCallback;
    private OnBackInvokedDispatcher invokedDispatcher;
    private OnBackInvokedCallback onBackInvokedCallback;
    private final h.c onBackPressedCallbacks;
    private final Consumer<Boolean> onHasEnabledCallbacksChanged;

    /* JADX INFO: renamed from: androidx.activity.OnBackPressedDispatcher$1, reason: invalid class name */
    public static final class AnonymousClass1 extends l implements p.l {
        public AnonymousClass1() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((BackEventCompat) obj);
            return i.f423a;
        }

        public final void invoke(BackEventCompat backEvent) {
            k.g(backEvent, "backEvent");
            OnBackPressedDispatcher.this.onBackStarted(backEvent);
        }
    }

    /* JADX INFO: renamed from: androidx.activity.OnBackPressedDispatcher$2, reason: invalid class name */
    public static final class AnonymousClass2 extends l implements p.l {
        public AnonymousClass2() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((BackEventCompat) obj);
            return i.f423a;
        }

        public final void invoke(BackEventCompat backEvent) {
            k.g(backEvent, "backEvent");
            OnBackPressedDispatcher.this.onBackProgressed(backEvent);
        }
    }

    /* JADX INFO: renamed from: androidx.activity.OnBackPressedDispatcher$3, reason: invalid class name */
    public static final class AnonymousClass3 extends l implements p.a {
        public AnonymousClass3() {
            super(0);
        }

        @Override // p.a
        public /* bridge */ /* synthetic */ Object invoke() {
            m0invoke();
            return i.f423a;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m0invoke() {
            OnBackPressedDispatcher.this.onBackPressed();
        }
    }

    /* JADX INFO: renamed from: androidx.activity.OnBackPressedDispatcher$4, reason: invalid class name */
    public static final class AnonymousClass4 extends l implements p.a {
        public AnonymousClass4() {
            super(0);
        }

        @Override // p.a
        public /* bridge */ /* synthetic */ Object invoke() {
            m1invoke();
            return i.f423a;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m1invoke() {
            OnBackPressedDispatcher.this.onBackCancelled();
        }
    }

    /* JADX INFO: renamed from: androidx.activity.OnBackPressedDispatcher$5, reason: invalid class name */
    public static final class AnonymousClass5 extends l implements p.a {
        public AnonymousClass5() {
            super(0);
        }

        @Override // p.a
        public /* bridge */ /* synthetic */ Object invoke() {
            m2invoke();
            return i.f423a;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m2invoke() {
            OnBackPressedDispatcher.this.onBackPressed();
        }
    }

    @RequiresApi(33)
    public static final class Api33Impl {
        public static final Api33Impl INSTANCE = new Api33Impl();

        private Api33Impl() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void createOnBackInvokedCallback$lambda$0(p.a onBackInvoked) {
            k.g(onBackInvoked, "$onBackInvoked");
            onBackInvoked.invoke();
        }

        @DoNotInline
        public final OnBackInvokedCallback createOnBackInvokedCallback(final p.a onBackInvoked) {
            k.g(onBackInvoked, "onBackInvoked");
            return new OnBackInvokedCallback() { // from class: androidx.activity.g
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    OnBackPressedDispatcher.Api33Impl.createOnBackInvokedCallback$lambda$0(onBackInvoked);
                }
            };
        }

        @DoNotInline
        public final void registerOnBackInvokedCallback(Object dispatcher, int i2, Object callback) {
            k.g(dispatcher, "dispatcher");
            k.g(callback, "callback");
            ((OnBackInvokedDispatcher) dispatcher).registerOnBackInvokedCallback(i2, (OnBackInvokedCallback) callback);
        }

        @DoNotInline
        public final void unregisterOnBackInvokedCallback(Object dispatcher, Object callback) {
            k.g(dispatcher, "dispatcher");
            k.g(callback, "callback");
            ((OnBackInvokedDispatcher) dispatcher).unregisterOnBackInvokedCallback((OnBackInvokedCallback) callback);
        }
    }

    @RequiresApi(34)
    public static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        private Api34Impl() {
        }

        @DoNotInline
        public final OnBackInvokedCallback createOnBackAnimationCallback(final p.l onBackStarted, final p.l onBackProgressed, final p.a onBackInvoked, final p.a onBackCancelled) {
            k.g(onBackStarted, "onBackStarted");
            k.g(onBackProgressed, "onBackProgressed");
            k.g(onBackInvoked, "onBackInvoked");
            k.g(onBackCancelled, "onBackCancelled");
            return new OnBackAnimationCallback() { // from class: androidx.activity.OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1
                @Override // android.window.OnBackAnimationCallback
                public void onBackCancelled() {
                    onBackCancelled.invoke();
                }

                @Override // android.window.OnBackInvokedCallback
                public void onBackInvoked() {
                    onBackInvoked.invoke();
                }

                @Override // android.window.OnBackAnimationCallback
                public void onBackProgressed(BackEvent backEvent) {
                    k.g(backEvent, "backEvent");
                    onBackProgressed.invoke(new BackEventCompat(backEvent));
                }

                @Override // android.window.OnBackAnimationCallback
                public void onBackStarted(BackEvent backEvent) {
                    k.g(backEvent, "backEvent");
                    onBackStarted.invoke(new BackEventCompat(backEvent));
                }
            };
        }
    }

    public final class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, Cancellable {
        private Cancellable currentCancellable;
        private final Lifecycle lifecycle;
        private final OnBackPressedCallback onBackPressedCallback;
        final /* synthetic */ OnBackPressedDispatcher this$0;

        public LifecycleOnBackPressedCancellable(OnBackPressedDispatcher onBackPressedDispatcher, Lifecycle lifecycle, OnBackPressedCallback onBackPressedCallback) {
            k.g(lifecycle, "lifecycle");
            k.g(onBackPressedCallback, "onBackPressedCallback");
            this.this$0 = onBackPressedDispatcher;
            this.lifecycle = lifecycle;
            this.onBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.activity.Cancellable
        public void cancel() {
            this.lifecycle.removeObserver(this);
            this.onBackPressedCallback.removeCancellable(this);
            Cancellable cancellable = this.currentCancellable;
            if (cancellable != null) {
                cancellable.cancel();
            }
            this.currentCancellable = null;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            k.g(source, "source");
            k.g(event, "event");
            if (event == Lifecycle.Event.ON_START) {
                this.currentCancellable = this.this$0.addCancellableCallback$activity_release(this.onBackPressedCallback);
                return;
            }
            if (event != Lifecycle.Event.ON_STOP) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    cancel();
                }
            } else {
                Cancellable cancellable = this.currentCancellable;
                if (cancellable != null) {
                    cancellable.cancel();
                }
            }
        }
    }

    public final class OnBackPressedCancellable implements Cancellable {
        private final OnBackPressedCallback onBackPressedCallback;
        final /* synthetic */ OnBackPressedDispatcher this$0;

        public OnBackPressedCancellable(OnBackPressedDispatcher onBackPressedDispatcher, OnBackPressedCallback onBackPressedCallback) {
            k.g(onBackPressedCallback, "onBackPressedCallback");
            this.this$0 = onBackPressedDispatcher;
            this.onBackPressedCallback = onBackPressedCallback;
        }

        @Override // androidx.activity.Cancellable
        public void cancel() {
            this.this$0.onBackPressedCallbacks.remove(this.onBackPressedCallback);
            if (k.a(this.this$0.inProgressCallback, this.onBackPressedCallback)) {
                this.onBackPressedCallback.handleOnBackCancelled();
                this.this$0.inProgressCallback = null;
            }
            this.onBackPressedCallback.removeCancellable(this);
            p.a enabledChangedCallback$activity_release = this.onBackPressedCallback.getEnabledChangedCallback$activity_release();
            if (enabledChangedCallback$activity_release != null) {
                enabledChangedCallback$activity_release.invoke();
            }
            this.onBackPressedCallback.setEnabledChangedCallback$activity_release(null);
        }
    }

    /* JADX INFO: renamed from: androidx.activity.OnBackPressedDispatcher$addCallback$1, reason: invalid class name and case insensitive filesystem */
    public /* synthetic */ class C00041 extends j implements p.a {
        public C00041(Object obj) {
            super(0, obj, OnBackPressedDispatcher.class, "updateEnabledCallbacks", "updateEnabledCallbacks()V");
        }

        @Override // p.a
        public /* bridge */ /* synthetic */ Object invoke() {
            m3invoke();
            return i.f423a;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m3invoke() {
            ((OnBackPressedDispatcher) this.receiver).updateEnabledCallbacks();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public OnBackPressedDispatcher() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public final void onBackCancelled() {
        Object objPrevious;
        h.c cVar = this.onBackPressedCallbacks;
        ListIterator<E> listIterator = cVar.listIterator(cVar.size());
        do {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
        } while (!((OnBackPressedCallback) objPrevious).isEnabled());
        OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        this.inProgressCallback = null;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackCancelled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public final void onBackProgressed(BackEventCompat backEventCompat) {
        Object objPrevious;
        h.c cVar = this.onBackPressedCallbacks;
        ListIterator<E> listIterator = cVar.listIterator(cVar.size());
        do {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
        } while (!((OnBackPressedCallback) objPrevious).isEnabled());
        OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackProgressed(backEventCompat);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MainThread
    public final void onBackStarted(BackEventCompat backEventCompat) {
        Object objPrevious;
        h.c cVar = this.onBackPressedCallbacks;
        ListIterator<E> listIterator = cVar.listIterator(cVar.size());
        do {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
        } while (!((OnBackPressedCallback) objPrevious).isEnabled());
        OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        this.inProgressCallback = onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackStarted(backEventCompat);
        }
    }

    @RequiresApi(33)
    private final void updateBackInvokedCallbackState(boolean z2) {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.invokedDispatcher;
        OnBackInvokedCallback onBackInvokedCallback = this.onBackInvokedCallback;
        if (onBackInvokedDispatcher == null || onBackInvokedCallback == null) {
            return;
        }
        if (z2 && !this.backInvokedCallbackRegistered) {
            Api33Impl.INSTANCE.registerOnBackInvokedCallback(onBackInvokedDispatcher, 0, onBackInvokedCallback);
            this.backInvokedCallbackRegistered = true;
        } else {
            if (z2 || !this.backInvokedCallbackRegistered) {
                return;
            }
            Api33Impl.INSTANCE.unregisterOnBackInvokedCallback(onBackInvokedDispatcher, onBackInvokedCallback);
            this.backInvokedCallbackRegistered = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateEnabledCallbacks() {
        boolean z2 = this.hasEnabledCallbacks;
        h.c cVar = this.onBackPressedCallbacks;
        boolean z3 = false;
        if (!(cVar instanceof Collection) || !cVar.isEmpty()) {
            Iterator<E> it = cVar.iterator();
            while (it.hasNext()) {
                if (((OnBackPressedCallback) it.next()).isEnabled()) {
                    z3 = true;
                    break;
                }
            }
        }
        this.hasEnabledCallbacks = z3;
        if (z3 != z2) {
            Consumer<Boolean> consumer = this.onHasEnabledCallbacksChanged;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(z3));
            }
            if (Build.VERSION.SDK_INT >= 33) {
                updateBackInvokedCallbackState(z3);
            }
        }
    }

    @MainThread
    public final void addCallback(OnBackPressedCallback onBackPressedCallback) {
        k.g(onBackPressedCallback, "onBackPressedCallback");
        addCancellableCallback$activity_release(onBackPressedCallback);
    }

    @MainThread
    public final Cancellable addCancellableCallback$activity_release(OnBackPressedCallback onBackPressedCallback) {
        k.g(onBackPressedCallback, "onBackPressedCallback");
        this.onBackPressedCallbacks.a(onBackPressedCallback);
        OnBackPressedCancellable onBackPressedCancellable = new OnBackPressedCancellable(this, onBackPressedCallback);
        onBackPressedCallback.addCancellable(onBackPressedCancellable);
        updateEnabledCallbacks();
        onBackPressedCallback.setEnabledChangedCallback$activity_release(new OnBackPressedDispatcher$addCancellableCallback$1(this));
        return onBackPressedCancellable;
    }

    @MainThread
    @VisibleForTesting
    public final void dispatchOnBackCancelled() {
        onBackCancelled();
    }

    @MainThread
    @VisibleForTesting
    public final void dispatchOnBackProgressed(BackEventCompat backEvent) {
        k.g(backEvent, "backEvent");
        onBackProgressed(backEvent);
    }

    @MainThread
    @VisibleForTesting
    public final void dispatchOnBackStarted(BackEventCompat backEvent) {
        k.g(backEvent, "backEvent");
        onBackStarted(backEvent);
    }

    @MainThread
    public final boolean hasEnabledCallbacks() {
        return this.hasEnabledCallbacks;
    }

    @MainThread
    public final void onBackPressed() {
        Object objPrevious;
        h.c cVar = this.onBackPressedCallbacks;
        ListIterator<E> listIterator = cVar.listIterator(cVar.size());
        do {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
        } while (!((OnBackPressedCallback) objPrevious).isEnabled());
        OnBackPressedCallback onBackPressedCallback = (OnBackPressedCallback) objPrevious;
        this.inProgressCallback = null;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.handleOnBackPressed();
            return;
        }
        Runnable runnable = this.fallbackOnBackPressed;
        if (runnable != null) {
            runnable.run();
        }
    }

    @RequiresApi(33)
    public final void setOnBackInvokedDispatcher(OnBackInvokedDispatcher invoker) {
        k.g(invoker, "invoker");
        this.invokedDispatcher = invoker;
        updateBackInvokedCallbackState(this.hasEnabledCallbacks);
    }

    public OnBackPressedDispatcher(Runnable runnable) {
        this(runnable, null);
    }

    @MainThread
    public final void addCallback(LifecycleOwner owner, OnBackPressedCallback onBackPressedCallback) {
        k.g(owner, "owner");
        k.g(onBackPressedCallback, "onBackPressedCallback");
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        onBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(this, lifecycle, onBackPressedCallback));
        updateEnabledCallbacks();
        onBackPressedCallback.setEnabledChangedCallback$activity_release(new C00041(this));
    }

    public /* synthetic */ OnBackPressedDispatcher(Runnable runnable, int i2, kotlin.jvm.internal.f fVar) {
        this((i2 & 1) != 0 ? null : runnable);
    }

    public OnBackPressedDispatcher(Runnable runnable, Consumer<Boolean> consumer) {
        this.fallbackOnBackPressed = runnable;
        this.onHasEnabledCallbacksChanged = consumer;
        this.onBackPressedCallbacks = new h.c();
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 33) {
            this.onBackInvokedCallback = i2 >= 34 ? Api34Impl.INSTANCE.createOnBackAnimationCallback(new AnonymousClass1(), new AnonymousClass2(), new AnonymousClass3(), new AnonymousClass4()) : Api33Impl.INSTANCE.createOnBackInvokedCallback(new AnonymousClass5());
        }
    }
}
