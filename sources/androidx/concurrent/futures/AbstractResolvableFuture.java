package androidx.concurrent.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import e.a;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class AbstractResolvableFuture<V> implements a {
    static final AtomicHelper ATOMIC_HELPER;
    private static final Object NULL;
    private static final long SPIN_THRESHOLD_NANOS = 1000;

    @Nullable
    volatile Listener listeners;

    @Nullable
    volatile Object value;

    @Nullable
    volatile Waiter waiters;
    static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Logger log = Logger.getLogger(AbstractResolvableFuture.class.getName());

    public static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        public abstract boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2);

        public abstract boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2);

        public abstract boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2);

        public abstract void putNext(Waiter waiter, Waiter waiter2);

        public abstract void putThread(Waiter waiter, Thread thread);
    }

    public static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;

        @Nullable
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
            } else {
                CAUSELESS_CANCELLED = new Cancellation(false, null);
                CAUSELESS_INTERRUPTED = new Cancellation(true, null);
            }
        }

        public Cancellation(boolean z2, @Nullable Throwable th) {
            this.wasInterrupted = z2;
            this.cause = th;
        }
    }

    public static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") { // from class: androidx.concurrent.futures.AbstractResolvableFuture.Failure.1
            @Override // java.lang.Throwable
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        public Failure(Throwable th) {
            this.exception = (Throwable) AbstractResolvableFuture.checkNotNull(th);
        }
    }

    public static final class Listener {
        static final Listener TOMBSTONE = new Listener(null, null);
        final Executor executor;

        @Nullable
        Listener next;
        final Runnable task;

        public Listener(Runnable runnable, Executor executor) {
            this.task = runnable;
            this.executor = executor;
        }
    }

    public static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater;

        public SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2) {
            AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> atomicReferenceFieldUpdater = this.listenersUpdater;
            while (!atomicReferenceFieldUpdater.compareAndSet(abstractResolvableFuture, listener, listener2)) {
                if (atomicReferenceFieldUpdater.get(abstractResolvableFuture) != listener) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2) {
            AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> atomicReferenceFieldUpdater = this.valueUpdater;
            while (!atomicReferenceFieldUpdater.compareAndSet(abstractResolvableFuture, obj, obj2)) {
                if (atomicReferenceFieldUpdater.get(abstractResolvableFuture) != obj) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> atomicReferenceFieldUpdater = this.waitersUpdater;
            while (!atomicReferenceFieldUpdater.compareAndSet(abstractResolvableFuture, waiter, waiter2)) {
                if (atomicReferenceFieldUpdater.get(abstractResolvableFuture) != waiter) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }
    }

    public static final class SetFuture<V> implements Runnable {
        final a future;
        final AbstractResolvableFuture<V> owner;

        public SetFuture(AbstractResolvableFuture<V> abstractResolvableFuture, a aVar) {
            this.owner = abstractResolvableFuture;
            this.future = aVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.owner.value != this) {
                return;
            }
            if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractResolvableFuture.getFutureValue(this.future))) {
                AbstractResolvableFuture.complete(this.owner);
            }
        }
    }

    public static final class SynchronizedHelper extends AtomicHelper {
        public SynchronizedHelper() {
            super();
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2) {
            synchronized (abstractResolvableFuture) {
                try {
                    if (abstractResolvableFuture.listeners != listener) {
                        return false;
                    }
                    abstractResolvableFuture.listeners = listener2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2) {
            synchronized (abstractResolvableFuture) {
                try {
                    if (abstractResolvableFuture.value != obj) {
                        return false;
                    }
                    abstractResolvableFuture.value = obj2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractResolvableFuture) {
                try {
                    if (abstractResolvableFuture.waiters != waiter) {
                        return false;
                    }
                    abstractResolvableFuture.waiters = waiter2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        public void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }
    }

    public static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);

        @Nullable
        volatile Waiter next;

        @Nullable
        volatile Thread thread;

        public Waiter() {
            AbstractResolvableFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        public Waiter(boolean z2) {
        }

        public void setNext(Waiter waiter) {
            AbstractResolvableFuture.ATOMIC_HELPER.putNext(this, waiter);
        }

        public void unpark() {
            Thread thread = this.thread;
            if (thread != null) {
                this.thread = null;
                LockSupport.unpark(thread);
            }
        }
    }

    static {
        AtomicHelper synchronizedHelper;
        try {
            synchronizedHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Object.class, "value"));
            th = null;
        } catch (Throwable th) {
            th = th;
            synchronizedHelper = new SynchronizedHelper();
        }
        ATOMIC_HELPER = synchronizedHelper;
        if (th != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        NULL = new Object();
    }

    private void addDoneString(StringBuilder sb) {
        String str = "]";
        try {
            Object uninterruptibly = getUninterruptibly(this);
            sb.append("SUCCESS, result=[");
            sb.append(userObjectToString(uninterruptibly));
            sb.append("]");
        } catch (CancellationException unused) {
            str = "CANCELLED";
            sb.append(str);
        } catch (RuntimeException e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            str = " thrown from get()]";
            sb.append(str);
        } catch (ExecutionException e3) {
            sb.append("FAILURE, cause=[");
            sb.append(e3.getCause());
            sb.append(str);
        }
    }

    private static CancellationException cancellationExceptionWithCause(@Nullable String str, @Nullable Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T t2) {
        t2.getClass();
        return t2;
    }

    private Listener clearListeners(Listener listener) {
        Listener listener2;
        do {
            listener2 = this.listeners;
        } while (!ATOMIC_HELPER.casListeners(this, listener2, Listener.TOMBSTONE));
        Listener listener3 = listener;
        Listener listener4 = listener2;
        while (listener4 != null) {
            Listener listener5 = listener4.next;
            listener4.next = listener3;
            listener3 = listener4;
            listener4 = listener5;
        }
        return listener3;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static void complete(AbstractResolvableFuture<?> abstractResolvableFuture) {
        Listener listener = null;
        while (true) {
            abstractResolvableFuture.releaseWaiters();
            abstractResolvableFuture.afterDone();
            Listener listenerClearListeners = abstractResolvableFuture.clearListeners(listener);
            while (listenerClearListeners != null) {
                listener = listenerClearListeners.next;
                Runnable runnable = listenerClearListeners.task;
                if (runnable instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable;
                    abstractResolvableFuture = setFuture.owner;
                    if (abstractResolvableFuture.value == setFuture) {
                        if (ATOMIC_HELPER.casValue(abstractResolvableFuture, setFuture, getFutureValue(setFuture.future))) {
                        }
                    } else {
                        continue;
                    }
                } else {
                    executeListener(runnable, listenerClearListeners.executor);
                }
                listenerClearListeners = listener;
            }
            return;
        }
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).cause);
        }
        if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        }
        if (obj == NULL) {
            return null;
        }
        return obj;
    }

    public static Object getFutureValue(a aVar) {
        if (aVar instanceof AbstractResolvableFuture) {
            Object obj = ((AbstractResolvableFuture) aVar).value;
            if (!(obj instanceof Cancellation)) {
                return obj;
            }
            Cancellation cancellation = (Cancellation) obj;
            if (cancellation.wasInterrupted) {
                return cancellation.cause != null ? new Cancellation(false, cancellation.cause) : Cancellation.CAUSELESS_CANCELLED;
            }
            return obj;
        }
        boolean zIsCancelled = aVar.isCancelled();
        if ((!GENERATE_CANCELLATION_CAUSES) && zIsCancelled) {
            return Cancellation.CAUSELESS_CANCELLED;
        }
        try {
            Object uninterruptibly = getUninterruptibly(aVar);
            return uninterruptibly == null ? NULL : uninterruptibly;
        } catch (CancellationException e2) {
            if (zIsCancelled) {
                return new Cancellation(false, e2);
            }
            return new Failure(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + aVar, e2));
        } catch (ExecutionException e3) {
            return new Failure(e3.getCause());
        } catch (Throwable th) {
            return new Failure(th);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static <V> V getUninterruptibly(Future<V> future) {
        V v2;
        boolean z2 = false;
        while (true) {
            try {
                v2 = future.get();
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            } catch (Throwable th) {
                if (z2) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return v2;
    }

    private void releaseWaiters() {
        Waiter waiter;
        do {
            waiter = this.waiters;
        } while (!ATOMIC_HELPER.casWaiters(this, waiter, Waiter.TOMBSTONE));
        while (waiter != null) {
            waiter.unpark();
            waiter = waiter.next;
        }
    }

    private void removeWaiter(Waiter waiter) {
        waiter.thread = null;
        while (true) {
            Waiter waiter2 = this.waiters;
            if (waiter2 == Waiter.TOMBSTONE) {
                return;
            }
            Waiter waiter3 = null;
            while (waiter2 != null) {
                Waiter waiter4 = waiter2.next;
                if (waiter2.thread != null) {
                    waiter3 = waiter2;
                } else if (waiter3 != null) {
                    waiter3.next = waiter4;
                    if (waiter3.thread == null) {
                    }
                } else if (!ATOMIC_HELPER.casWaiters(this, waiter2, waiter4)) {
                }
                waiter2 = waiter4;
            }
            return;
        }
    }

    private String userObjectToString(Object obj) {
        return obj == this ? "this future" : String.valueOf(obj);
    }

    @Override // e.a
    public final void addListener(Runnable runnable, Executor executor) {
        checkNotNull(runnable);
        checkNotNull(executor);
        Listener listener = this.listeners;
        if (listener != Listener.TOMBSTONE) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.next = listener;
                if (ATOMIC_HELPER.casListeners(this, listener, listener2)) {
                    return;
                } else {
                    listener = this.listeners;
                }
            } while (listener != Listener.TOMBSTONE);
        }
        executeListener(runnable, executor);
    }

    public void afterDone() {
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z2) {
        Cancellation cancellation;
        Object obj = this.value;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        if (GENERATE_CANCELLATION_CAUSES) {
            cancellation = new Cancellation(z2, new CancellationException("Future.cancel() was called."));
        } else {
            cancellation = z2 ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED;
        }
        boolean z3 = false;
        AbstractResolvableFuture<V> abstractResolvableFuture = this;
        while (true) {
            if (ATOMIC_HELPER.casValue(abstractResolvableFuture, obj, cancellation)) {
                if (z2) {
                    abstractResolvableFuture.interruptTask();
                }
                complete(abstractResolvableFuture);
                if (!(obj instanceof SetFuture)) {
                    return true;
                }
                a aVar = ((SetFuture) obj).future;
                if (!(aVar instanceof AbstractResolvableFuture)) {
                    aVar.cancel(z2);
                    return true;
                }
                abstractResolvableFuture = (AbstractResolvableFuture) aVar;
                obj = abstractResolvableFuture.value;
                if (!(obj == null) && !(obj instanceof SetFuture)) {
                    return true;
                }
                z3 = true;
            } else {
                obj = abstractResolvableFuture.value;
                if (!(obj instanceof SetFuture)) {
                    return z3;
                }
            }
        }
    }

    @Override // java.util.concurrent.Future
    public final V get() throws InterruptedException {
        Object obj;
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj2 = this.value;
        if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
            return getDoneValue(obj2);
        }
        Waiter waiter = this.waiters;
        if (waiter != Waiter.TOMBSTONE) {
            Waiter waiter2 = new Waiter();
            do {
                waiter2.setNext(waiter);
                if (ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) {
                    do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                            removeWaiter(waiter2);
                            throw new InterruptedException();
                        }
                        obj = this.value;
                    } while (!((obj != null) & (!(obj instanceof SetFuture))));
                    return getDoneValue(obj);
                }
                waiter = this.waiters;
            } while (waiter != Waiter.TOMBSTONE);
        }
        return getDoneValue(this.value);
    }

    public void interruptTask() {
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        Object obj = this.value;
        return (!(obj instanceof SetFuture)) & (obj != null);
    }

    public final void maybePropagateCancellationTo(@Nullable Future<?> future) {
        if ((future != null) && isCancelled()) {
            future.cancel(wasInterrupted());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public String pendingToString() {
        Object obj = this.value;
        if (obj instanceof SetFuture) {
            return androidx.appcompat.graphics.drawable.a.g(new StringBuilder("setFuture=["), userObjectToString(((SetFuture) obj).future), "]");
        }
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public boolean set(@Nullable V v2) {
        if (v2 == null) {
            v2 = (V) NULL;
        }
        if (!ATOMIC_HELPER.casValue(this, null, v2)) {
            return false;
        }
        complete(this);
        return true;
    }

    public boolean setException(Throwable th) {
        if (!ATOMIC_HELPER.casValue(this, null, new Failure((Throwable) checkNotNull(th)))) {
            return false;
        }
        complete(this);
        return true;
    }

    public boolean setFuture(a aVar) {
        Failure failure;
        checkNotNull(aVar);
        Object obj = this.value;
        if (obj == null) {
            if (aVar.isDone()) {
                if (!ATOMIC_HELPER.casValue(this, null, getFutureValue(aVar))) {
                    return false;
                }
                complete(this);
                return true;
            }
            SetFuture setFuture = new SetFuture(this, aVar);
            if (ATOMIC_HELPER.casValue(this, null, setFuture)) {
                try {
                    aVar.addListener(setFuture, DirectExecutor.INSTANCE);
                } catch (Throwable th) {
                    try {
                        failure = new Failure(th);
                    } catch (Throwable unused) {
                        failure = Failure.FALLBACK_INSTANCE;
                    }
                    ATOMIC_HELPER.casValue(this, setFuture, failure);
                }
                return true;
            }
            obj = this.value;
        }
        if (obj instanceof Cancellation) {
            aVar.cancel(((Cancellation) obj).wasInterrupted);
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0025  */
    public String toString() {
        String strPendingToString;
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (!isCancelled()) {
            if (isDone()) {
                addDoneString(sb);
            } else {
                try {
                    strPendingToString = pendingToString();
                } catch (RuntimeException e2) {
                    strPendingToString = "Exception thrown from implementation: " + e2.getClass();
                }
                if (strPendingToString != null && !strPendingToString.isEmpty()) {
                    sb.append("PENDING, info=[");
                    sb.append(strPendingToString);
                    sb.append("]");
                } else if (isDone()) {
                    addDoneString(sb);
                } else {
                    str = "PENDING";
                }
            }
            sb.append("]");
            return sb.toString();
        }
        str = "CANCELLED";
        sb.append(str);
        sb.append("]");
        return sb.toString();
    }

    public final boolean wasInterrupted() {
        Object obj = this.value;
        return (obj instanceof Cancellation) && ((Cancellation) obj).wasInterrupted;
    }

    @Override // java.util.concurrent.Future
    public final V get(long j2, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        long nanos = timeUnit.toNanos(j2);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.value;
        if ((obj != null) && (!(obj instanceof SetFuture))) {
            return getDoneValue(obj);
        }
        long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= SPIN_THRESHOLD_NANOS) {
            Waiter waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = new Waiter();
                while (true) {
                    waiter2.setNext(waiter);
                    if (ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) {
                        do {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.value;
                            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                return getDoneValue(obj2);
                            }
                            nanos = jNanoTime - System.nanoTime();
                        } while (nanos >= SPIN_THRESHOLD_NANOS);
                        removeWaiter(waiter2);
                        break;
                    }
                    waiter = this.waiters;
                    if (waiter == Waiter.TOMBSTONE) {
                    }
                }
            }
            return getDoneValue(this.value);
        }
        while (nanos > 0) {
            Object obj3 = this.value;
            if ((obj3 != null) && (!(obj3 instanceof SetFuture))) {
                return getDoneValue(obj3);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            nanos = jNanoTime - System.nanoTime();
        }
        String string = toString();
        String string2 = timeUnit.toString();
        Locale locale = Locale.ROOT;
        String lowerCase = string2.toLowerCase(locale);
        String strE = "Waited " + j2 + " " + timeUnit.toString().toLowerCase(locale);
        if (nanos + SPIN_THRESHOLD_NANOS < 0) {
            String strE2 = androidx.appcompat.graphics.drawable.a.e(strE, " (plus ");
            long j3 = -nanos;
            long jConvert = timeUnit.convert(j3, TimeUnit.NANOSECONDS);
            long nanos2 = j3 - timeUnit.toNanos(jConvert);
            boolean z2 = jConvert == 0 || nanos2 > SPIN_THRESHOLD_NANOS;
            if (jConvert > 0) {
                String strE3 = strE2 + jConvert + " " + lowerCase;
                if (z2) {
                    strE3 = androidx.appcompat.graphics.drawable.a.e(strE3, ",");
                }
                strE2 = androidx.appcompat.graphics.drawable.a.e(strE3, " ");
            }
            if (z2) {
                strE2 = strE2 + nanos2 + " nanoseconds ";
            }
            strE = androidx.appcompat.graphics.drawable.a.e(strE2, "delay)");
        }
        if (isDone()) {
            throw new TimeoutException(androidx.appcompat.graphics.drawable.a.e(strE, " but future completed as timeout expired"));
        }
        throw new TimeoutException(strE + " for " + string);
    }
}
