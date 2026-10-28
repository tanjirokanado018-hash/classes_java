package h;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class b implements Collection {
    public final Object[] b;

    public b(Object[] objArr) {
        this.b = objArr;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        int i2;
        Object[] objArr = this.b;
        kotlin.jvm.internal.k.g(objArr, "<this>");
        if (obj == null) {
            int length = objArr.length;
            i2 = 0;
            while (i2 < length) {
                if (objArr[i2] != null) {
                    i2++;
                }
            }
            return false;
        }
        int length2 = objArr.length;
        for (int i3 = 0; i3 < length2; i3++) {
            if (kotlin.jvm.internal.k.a(obj, objArr[i3])) {
                i2 = i3;
            }
        }
        return false;
        return i2 >= 0;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection elements) {
        kotlin.jvm.internal.k.g(elements, "elements");
        if (elements.isEmpty()) {
            return true;
        }
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.b.length == 0;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        Object[] array = this.b;
        kotlin.jvm.internal.k.g(array, "array");
        return new kotlin.jvm.internal.a(array);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final int size() {
        return this.b.length;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        Object[] objArr = this.b;
        kotlin.jvm.internal.k.g(objArr, "<this>");
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length, Object[].class);
        kotlin.jvm.internal.k.f(objArrCopyOf, "copyOf(this, this.size, Array<Any?>::class.java)");
        return objArrCopyOf;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] array) {
        kotlin.jvm.internal.k.g(array, "array");
        return kotlin.jvm.internal.k.w(this, array);
    }
}
