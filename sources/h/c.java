package h;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c extends a {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final Object[] f424e = new Object[0];
    public int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object[] f425c = f424e;
    public int d;

    public final void a(Object obj) {
        c(this.d + 1);
        this.f425c[e(this.b + this.d)] = obj;
        this.d++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i2, Object obj) {
        int length;
        int i3 = this.d;
        if (i2 < 0 || i2 > i3) {
            throw new IndexOutOfBoundsException("index: " + i2 + ", size: " + i3);
        }
        if (i2 == i3) {
            a(obj);
            return;
        }
        int i4 = i3 + 1;
        if (i2 == 0) {
            c(i4);
            int length2 = this.b;
            if (length2 == 0) {
                Object[] objArr = this.f425c;
                kotlin.jvm.internal.k.g(objArr, "<this>");
                length2 = objArr.length;
            }
            int i5 = length2 - 1;
            this.b = i5;
            this.f425c[i5] = obj;
            this.d++;
            return;
        }
        c(i4);
        int iE = e(this.b + i2);
        int i6 = this.d;
        if (i2 < ((i6 + 1) >> 1)) {
            if (iE == 0) {
                Object[] objArr2 = this.f425c;
                kotlin.jvm.internal.k.g(objArr2, "<this>");
                length = objArr2.length - 1;
            } else {
                length = iE - 1;
            }
            int length3 = this.b;
            if (length3 == 0) {
                Object[] objArr3 = this.f425c;
                kotlin.jvm.internal.k.g(objArr3, "<this>");
                length3 = objArr3.length;
            }
            int i7 = length3 - 1;
            int i8 = this.b;
            Object[] objArr4 = this.f425c;
            if (length >= i8) {
                objArr4[i7] = objArr4[i8];
                d.ao(objArr4, objArr4, i8, i8 + 1, length + 1);
            } else {
                d.ao(objArr4, objArr4, i8 - 1, i8, objArr4.length);
                Object[] objArr5 = this.f425c;
                objArr5[objArr5.length - 1] = objArr5[0];
                d.ao(objArr5, objArr5, 0, 1, length + 1);
            }
            this.f425c[length] = obj;
            this.b = i7;
        } else {
            int iE2 = e(this.b + i6);
            Object[] objArr6 = this.f425c;
            if (iE < iE2) {
                d.ao(objArr6, objArr6, iE + 1, iE, iE2);
            } else {
                d.ao(objArr6, objArr6, 1, 0, iE2);
                Object[] objArr7 = this.f425c;
                objArr7[0] = objArr7[objArr7.length - 1];
                d.ao(objArr7, objArr7, iE + 1, iE, objArr7.length - 1);
            }
            this.f425c[iE] = obj;
        }
        this.d++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i2, Collection elements) {
        kotlin.jvm.internal.k.g(elements, "elements");
        int i3 = this.d;
        if (i2 < 0 || i2 > i3) {
            throw new IndexOutOfBoundsException("index: " + i2 + ", size: " + i3);
        }
        if (elements.isEmpty()) {
            return false;
        }
        int i4 = this.d;
        if (i2 == i4) {
            return addAll(elements);
        }
        c(elements.size() + i4);
        int iE = e(this.b + this.d);
        int iE2 = e(this.b + i2);
        int size = elements.size();
        if (i2 < ((this.d + 1) >> 1)) {
            int i5 = this.b;
            int length = i5 - size;
            if (iE2 < i5) {
                Object[] objArr = this.f425c;
                d.ao(objArr, objArr, length, i5, objArr.length);
                Object[] objArr2 = this.f425c;
                if (size >= iE2) {
                    d.ao(objArr2, objArr2, objArr2.length - size, 0, iE2);
                } else {
                    d.ao(objArr2, objArr2, objArr2.length - size, 0, size);
                    Object[] objArr3 = this.f425c;
                    d.ao(objArr3, objArr3, 0, size, iE2);
                }
            } else if (length >= 0) {
                Object[] objArr4 = this.f425c;
                d.ao(objArr4, objArr4, length, i5, iE2);
            } else {
                Object[] objArr5 = this.f425c;
                length += objArr5.length;
                int i6 = iE2 - i5;
                int length2 = objArr5.length - length;
                if (length2 >= i6) {
                    d.ao(objArr5, objArr5, length, i5, iE2);
                } else {
                    d.ao(objArr5, objArr5, length, i5, i5 + length2);
                    Object[] objArr6 = this.f425c;
                    d.ao(objArr6, objArr6, 0, this.b + length2, iE2);
                }
            }
            this.b = length;
            iE2 -= size;
            if (iE2 < 0) {
                iE2 += this.f425c.length;
            }
        } else {
            int length3 = iE2 + size;
            if (iE2 < iE) {
                int i7 = size + iE;
                Object[] objArr7 = this.f425c;
                if (i7 <= objArr7.length) {
                    d.ao(objArr7, objArr7, length3, iE2, iE);
                } else if (length3 >= objArr7.length) {
                    length3 -= objArr7.length;
                    d.ao(objArr7, objArr7, length3, iE2, iE);
                } else {
                    int length4 = iE - (i7 - objArr7.length);
                    d.ao(objArr7, objArr7, 0, length4, iE);
                    Object[] objArr8 = this.f425c;
                    d.ao(objArr8, objArr8, length3, iE2, length4);
                }
            } else {
                Object[] objArr9 = this.f425c;
                d.ao(objArr9, objArr9, size, 0, iE);
                Object[] objArr10 = this.f425c;
                if (length3 >= objArr10.length) {
                    d.ao(objArr10, objArr10, length3 - objArr10.length, iE2, objArr10.length);
                } else {
                    d.ao(objArr10, objArr10, 0, objArr10.length - size, objArr10.length);
                    Object[] objArr11 = this.f425c;
                    d.ao(objArr11, objArr11, length3, iE2, objArr11.length - size);
                }
            }
        }
        b(iE2, elements);
        return true;
    }

    public final void b(int i2, Collection collection) {
        Iterator it = collection.iterator();
        int length = this.f425c.length;
        while (i2 < length && it.hasNext()) {
            this.f425c[i2] = it.next();
            i2++;
        }
        int i3 = this.b;
        for (int i4 = 0; i4 < i3 && it.hasNext(); i4++) {
            this.f425c[i4] = it.next();
        }
        this.d = collection.size() + this.d;
    }

    public final void c(int i2) {
        if (i2 < 0) {
            throw new IllegalStateException("Deque is too big.");
        }
        Object[] objArr = this.f425c;
        if (i2 <= objArr.length) {
            return;
        }
        if (objArr == f424e) {
            if (i2 < 10) {
                i2 = 10;
            }
            this.f425c = new Object[i2];
            return;
        }
        int length = objArr.length;
        int i3 = length + (length >> 1);
        if (i3 - i2 < 0) {
            i3 = i2;
        }
        if (i3 - 2147483639 > 0) {
            i3 = i2 > 2147483639 ? Integer.MAX_VALUE : 2147483639;
        }
        Object[] objArr2 = new Object[i3];
        d.ao(objArr, objArr2, 0, this.b, objArr.length);
        Object[] objArr3 = this.f425c;
        int length2 = objArr3.length;
        int i4 = this.b;
        d.ao(objArr3, objArr2, length2 - i4, 0, i4);
        this.b = 0;
        this.f425c = objArr2;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        int iE = e(this.b + this.d);
        int i2 = this.b;
        if (i2 < iE) {
            Object[] objArr = this.f425c;
            kotlin.jvm.internal.k.g(objArr, "<this>");
            Arrays.fill(objArr, i2, iE, (Object) null);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.f425c;
            Arrays.fill(objArr2, this.b, objArr2.length, (Object) null);
            Object[] objArr3 = this.f425c;
            kotlin.jvm.internal.k.g(objArr3, "<this>");
            Arrays.fill(objArr3, 0, iE, (Object) null);
        }
        this.b = 0;
        this.d = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public final int d(int i2) {
        Object[] objArr = this.f425c;
        kotlin.jvm.internal.k.g(objArr, "<this>");
        if (i2 == objArr.length - 1) {
            return 0;
        }
        return i2 + 1;
    }

    public final int e(int i2) {
        Object[] objArr = this.f425c;
        return i2 >= objArr.length ? i2 - objArr.length : i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i2) {
        int i3 = this.d;
        if (i2 >= 0 && i2 < i3) {
            return this.f425c[e(this.b + i2)];
        }
        throw new IndexOutOfBoundsException("index: " + i2 + ", size: " + i3);
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        int iE = e(this.b + this.d);
        int length = this.b;
        if (length < iE) {
            while (length < iE) {
                if (!kotlin.jvm.internal.k.a(obj, this.f425c[length])) {
                    length++;
                }
            }
            return -1;
        }
        if (length < iE) {
            return -1;
        }
        int length2 = this.f425c.length;
        while (length < length2) {
            if (!kotlin.jvm.internal.k.a(obj, this.f425c[length])) {
                length++;
            }
        }
        for (int i2 = 0; i2 < iE; i2++) {
            if (kotlin.jvm.internal.k.a(obj, this.f425c[i2])) {
                length = i2 + this.f425c.length;
            }
        }
        return -1;
        return length - this.b;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.d == 0;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        int length;
        int iE = e(this.b + this.d);
        int i2 = this.b;
        if (i2 < iE) {
            length = iE - 1;
            if (i2 <= length) {
                while (!kotlin.jvm.internal.k.a(obj, this.f425c[length])) {
                    if (length != i2) {
                        length--;
                    }
                }
                return length - this.b;
            }
            return -1;
        }
        if (i2 > iE) {
            for (int i3 = iE - 1; -1 < i3; i3--) {
                if (kotlin.jvm.internal.k.a(obj, this.f425c[i3])) {
                    length = i3 + this.f425c.length;
                    return length - this.b;
                }
            }
            Object[] objArr = this.f425c;
            kotlin.jvm.internal.k.g(objArr, "<this>");
            length = objArr.length - 1;
            int i4 = this.b;
            if (i4 <= length) {
                while (!kotlin.jvm.internal.k.a(obj, this.f425c[length])) {
                    if (length != i4) {
                        length--;
                    }
                }
                return length - this.b;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection elements) {
        int iE;
        kotlin.jvm.internal.k.g(elements, "elements");
        boolean z2 = false;
        z2 = false;
        z2 = false;
        if (!isEmpty() && this.f425c.length != 0) {
            int iE2 = e(this.b + this.d);
            int i2 = this.b;
            if (i2 < iE2) {
                iE = i2;
                while (i2 < iE2) {
                    Object obj = this.f425c[i2];
                    if (!elements.contains(obj)) {
                        this.f425c[iE] = obj;
                        iE++;
                    } else {
                        z2 = true;
                    }
                    i2++;
                }
                Object[] objArr = this.f425c;
                kotlin.jvm.internal.k.g(objArr, "<this>");
                Arrays.fill(objArr, iE, iE2, (Object) null);
            } else {
                int length = this.f425c.length;
                int i3 = i2;
                boolean z3 = false;
                while (i2 < length) {
                    Object[] objArr2 = this.f425c;
                    Object obj2 = objArr2[i2];
                    objArr2[i2] = null;
                    if (!elements.contains(obj2)) {
                        this.f425c[i3] = obj2;
                        i3++;
                    } else {
                        z3 = true;
                    }
                    i2++;
                }
                iE = e(i3);
                for (int i4 = 0; i4 < iE2; i4++) {
                    Object[] objArr3 = this.f425c;
                    Object obj3 = objArr3[i4];
                    objArr3[i4] = null;
                    if (!elements.contains(obj3)) {
                        this.f425c[iE] = obj3;
                        iE = d(iE);
                    } else {
                        z3 = true;
                    }
                }
                z2 = z3;
            }
            if (z2) {
                int length2 = iE - this.b;
                if (length2 < 0) {
                    length2 += this.f425c.length;
                }
                this.d = length2;
            }
        }
        return z2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection elements) {
        int iE;
        kotlin.jvm.internal.k.g(elements, "elements");
        boolean z2 = false;
        z2 = false;
        z2 = false;
        if (!isEmpty() && this.f425c.length != 0) {
            int iE2 = e(this.b + this.d);
            int i2 = this.b;
            if (i2 < iE2) {
                iE = i2;
                while (i2 < iE2) {
                    Object obj = this.f425c[i2];
                    if (elements.contains(obj)) {
                        this.f425c[iE] = obj;
                        iE++;
                    } else {
                        z2 = true;
                    }
                    i2++;
                }
                Object[] objArr = this.f425c;
                kotlin.jvm.internal.k.g(objArr, "<this>");
                Arrays.fill(objArr, iE, iE2, (Object) null);
            } else {
                int length = this.f425c.length;
                int i3 = i2;
                boolean z3 = false;
                while (i2 < length) {
                    Object[] objArr2 = this.f425c;
                    Object obj2 = objArr2[i2];
                    objArr2[i2] = null;
                    if (elements.contains(obj2)) {
                        this.f425c[i3] = obj2;
                        i3++;
                    } else {
                        z3 = true;
                    }
                    i2++;
                }
                iE = e(i3);
                for (int i4 = 0; i4 < iE2; i4++) {
                    Object[] objArr3 = this.f425c;
                    Object obj3 = objArr3[i4];
                    objArr3[i4] = null;
                    if (elements.contains(obj3)) {
                        this.f425c[iE] = obj3;
                        iE = d(iE);
                    } else {
                        z3 = true;
                    }
                }
                z2 = z3;
            }
            if (z2) {
                int length2 = iE - this.b;
                if (length2 < 0) {
                    length2 += this.f425c.length;
                }
                this.d = length2;
            }
        }
        return z2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i2, Object obj) {
        int i3 = this.d;
        if (i2 < 0 || i2 >= i3) {
            throw new IndexOutOfBoundsException("index: " + i2 + ", size: " + i3);
        }
        int iE = e(this.b + i2);
        Object[] objArr = this.f425c;
        Object obj2 = objArr[iE];
        objArr[iE] = obj;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return toArray(new Object[this.d]);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] array) {
        kotlin.jvm.internal.k.g(array, "array");
        int length = array.length;
        int i2 = this.d;
        if (length < i2) {
            Object objNewInstance = Array.newInstance(array.getClass().getComponentType(), i2);
            kotlin.jvm.internal.k.e(objNewInstance, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.arrayOfNulls>");
            array = (Object[]) objNewInstance;
        }
        int iE = e(this.b + this.d);
        int i3 = this.b;
        if (i3 < iE) {
            d.ap(this.f425c, array, 0, i3, iE, 2);
        } else if (!isEmpty()) {
            Object[] objArr = this.f425c;
            d.ao(objArr, array, 0, this.b, objArr.length);
            Object[] objArr2 = this.f425c;
            d.ao(objArr2, array, objArr2.length - this.b, 0, iE);
        }
        int length2 = array.length;
        int i4 = this.d;
        if (length2 > i4) {
            array[i4] = null;
        }
        return array;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection elements) {
        kotlin.jvm.internal.k.g(elements, "elements");
        if (elements.isEmpty()) {
            return false;
        }
        c(elements.size() + this.d);
        b(e(this.b + this.d), elements);
        return true;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        a(obj);
        return true;
    }
}
