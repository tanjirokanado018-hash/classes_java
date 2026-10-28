package androidx.core.util;

import androidx.core.content.d;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class c {
    public static Predicate a(Predicate predicate, Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new a(predicate, predicate2, 1);
    }

    public static Predicate b(Predicate predicate) {
        return new b(1, predicate);
    }

    public static Predicate c(Predicate predicate, Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new a(predicate, predicate2, 0);
    }

    public static /* synthetic */ boolean d(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) && predicate2.test(obj);
    }

    public static /* synthetic */ boolean e(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static /* synthetic */ boolean f(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) || predicate2.test(obj);
    }

    public static Predicate g(Object obj) {
        return obj == null ? new d(11) : new b(0, obj);
    }

    public static /* synthetic */ boolean h(Object obj) {
        return obj == null;
    }

    public static Predicate j(Predicate predicate) {
        Objects.requireNonNull(predicate);
        return predicate.negate();
    }
}
