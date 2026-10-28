package androidx.core.util;

import android.annotation.SuppressLint;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@SuppressLint({"UnknownNullness"})
public interface Predicate<T> {
    @SuppressLint({"MissingNullability"})
    Predicate<T> and(@SuppressLint({"MissingNullability"}) Predicate<? super T> predicate);

    @SuppressLint({"MissingNullability"})
    Predicate<T> negate();

    @SuppressLint({"MissingNullability"})
    Predicate<T> or(@SuppressLint({"MissingNullability"}) Predicate<? super T> predicate);

    boolean test(T t2);
}
