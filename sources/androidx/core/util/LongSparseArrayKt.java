package androidx.core.util;

import android.util.LongSparseArray;
import h.k;
import java.util.Iterator;
import p.p;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LongSparseArrayKt {
    public static final <T> boolean contains(LongSparseArray<T> longSparseArray, long j2) {
        return longSparseArray.indexOfKey(j2) >= 0;
    }

    public static final <T> boolean containsKey(LongSparseArray<T> longSparseArray, long j2) {
        return longSparseArray.indexOfKey(j2) >= 0;
    }

    public static final <T> boolean containsValue(LongSparseArray<T> longSparseArray, T t2) {
        return longSparseArray.indexOfValue(t2) >= 0;
    }

    public static final <T> void forEach(LongSparseArray<T> longSparseArray, p pVar) {
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            pVar.invoke(Long.valueOf(longSparseArray.keyAt(i2)), longSparseArray.valueAt(i2));
        }
    }

    public static final <T> T getOrDefault(LongSparseArray<T> longSparseArray, long j2, T t2) {
        T t3 = longSparseArray.get(j2);
        return t3 == null ? t2 : t3;
    }

    public static final <T> T getOrElse(LongSparseArray<T> longSparseArray, long j2, p.a aVar) {
        T t2 = longSparseArray.get(j2);
        return t2 == null ? (T) aVar.invoke() : t2;
    }

    public static final <T> int getSize(LongSparseArray<T> longSparseArray) {
        return longSparseArray.size();
    }

    public static final <T> boolean isEmpty(LongSparseArray<T> longSparseArray) {
        return longSparseArray.size() == 0;
    }

    public static final <T> boolean isNotEmpty(LongSparseArray<T> longSparseArray) {
        return longSparseArray.size() != 0;
    }

    public static final <T> k keyIterator(final LongSparseArray<T> longSparseArray) {
        return new k() { // from class: androidx.core.util.LongSparseArrayKt.keyIterator.1
            private int index;

            public final int getIndex() {
                return this.index;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < longSparseArray.size();
            }

            @Override // h.k
            public long nextLong() {
                LongSparseArray<T> longSparseArray2 = longSparseArray;
                int i2 = this.index;
                this.index = i2 + 1;
                return longSparseArray2.keyAt(i2);
            }

            public final void setIndex(int i2) {
                this.index = i2;
            }
        };
    }

    public static final <T> LongSparseArray<T> plus(LongSparseArray<T> longSparseArray, LongSparseArray<T> longSparseArray2) {
        LongSparseArray<T> longSparseArray3 = new LongSparseArray<>(longSparseArray2.size() + longSparseArray.size());
        putAll(longSparseArray3, longSparseArray);
        putAll(longSparseArray3, longSparseArray2);
        return longSparseArray3;
    }

    public static final <T> void putAll(LongSparseArray<T> longSparseArray, LongSparseArray<T> longSparseArray2) {
        int size = longSparseArray2.size();
        for (int i2 = 0; i2 < size; i2++) {
            longSparseArray.put(longSparseArray2.keyAt(i2), longSparseArray2.valueAt(i2));
        }
    }

    public static final <T> boolean remove(LongSparseArray<T> longSparseArray, long j2, T t2) {
        int iIndexOfKey = longSparseArray.indexOfKey(j2);
        if (iIndexOfKey < 0 || !kotlin.jvm.internal.k.a(t2, longSparseArray.valueAt(iIndexOfKey))) {
            return false;
        }
        longSparseArray.removeAt(iIndexOfKey);
        return true;
    }

    public static final <T> void set(LongSparseArray<T> longSparseArray, long j2, T t2) {
        longSparseArray.put(j2, t2);
    }

    public static final <T> Iterator<T> valueIterator(final LongSparseArray<T> longSparseArray) {
        return new Iterator<T>() { // from class: androidx.core.util.LongSparseArrayKt.valueIterator.1
            private int index;

            public final int getIndex() {
                return this.index;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < longSparseArray.size();
            }

            @Override // java.util.Iterator
            public T next() {
                LongSparseArray<T> longSparseArray2 = longSparseArray;
                int i2 = this.index;
                this.index = i2 + 1;
                return longSparseArray2.valueAt(i2);
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            public final void setIndex(int i2) {
                this.index = i2;
            }
        };
    }
}
