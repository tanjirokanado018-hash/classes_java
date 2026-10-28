package androidx.core.util;

import android.annotation.SuppressLint;
import g.d;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PairKt {
    @SuppressLint({"UnknownNullness"})
    public static final <F, S> F component1(android.util.Pair<F, S> pair) {
        return (F) pair.first;
    }

    @SuppressLint({"UnknownNullness"})
    public static final <F, S> S component2(android.util.Pair<F, S> pair) {
        return (S) pair.second;
    }

    public static final <F, S> android.util.Pair<F, S> toAndroidPair(d dVar) {
        return new android.util.Pair<>(dVar.b, dVar.f420c);
    }

    public static final <F, S> Pair<F, S> toAndroidXPair(d dVar) {
        return new Pair<>(dVar.b, dVar.f420c);
    }

    public static final <F, S> d toKotlinPair(android.util.Pair<F, S> pair) {
        return new d(pair.first, pair.second);
    }

    @SuppressLint({"UnknownNullness"})
    public static final <F, S> F component1(Pair<F, S> pair) {
        return pair.first;
    }

    @SuppressLint({"UnknownNullness"})
    public static final <F, S> S component2(Pair<F, S> pair) {
        return pair.second;
    }

    public static final <F, S> d toKotlinPair(Pair<F, S> pair) {
        return new d(pair.first, pair.second);
    }
}
