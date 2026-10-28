package androidx.concurrent.futures;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import e.a;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class ResolvableFuture<V> extends AbstractResolvableFuture<V> {
    private ResolvableFuture() {
    }

    public static <V> ResolvableFuture<V> create() {
        return new ResolvableFuture<>();
    }

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public boolean set(@Nullable V v2) {
        return super.set(v2);
    }

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public boolean setException(Throwable th) {
        return super.setException(th);
    }

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public boolean setFuture(a aVar) {
        return super.setFuture(aVar);
    }
}
