package h;

import java.util.AbstractList;
import java.util.List;
import java.util.NoSuchElementException;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class a extends AbstractList implements List {
    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int i2) {
        c cVar = (c) this;
        int i3 = cVar.d;
        if (i2 < 0 || i2 >= i3) {
            throw new IndexOutOfBoundsException("index: " + i2 + ", size: " + i3);
        }
        if (i2 == y.p(cVar)) {
            if (cVar.isEmpty()) {
                throw new NoSuchElementException("ArrayDeque is empty.");
            }
            int iE = cVar.e(y.p(cVar) + cVar.b);
            Object[] objArr = cVar.f425c;
            Object obj = objArr[iE];
            objArr[iE] = null;
            cVar.d--;
            return obj;
        }
        if (i2 == 0) {
            if (cVar.isEmpty()) {
                throw new NoSuchElementException("ArrayDeque is empty.");
            }
            Object[] objArr2 = cVar.f425c;
            int i4 = cVar.b;
            Object obj2 = objArr2[i4];
            objArr2[i4] = null;
            cVar.b = cVar.d(i4);
            cVar.d--;
            return obj2;
        }
        int iE2 = cVar.e(cVar.b + i2);
        Object[] objArr3 = cVar.f425c;
        Object obj3 = objArr3[iE2];
        if (i2 < (cVar.d >> 1)) {
            int i5 = cVar.b;
            if (iE2 >= i5) {
                d.ao(objArr3, objArr3, i5 + 1, i5, iE2);
            } else {
                d.ao(objArr3, objArr3, 1, 0, iE2);
                Object[] objArr4 = cVar.f425c;
                objArr4[0] = objArr4[objArr4.length - 1];
                int i6 = cVar.b;
                d.ao(objArr4, objArr4, i6 + 1, i6, objArr4.length - 1);
            }
            Object[] objArr5 = cVar.f425c;
            int i7 = cVar.b;
            objArr5[i7] = null;
            cVar.b = cVar.d(i7);
        } else {
            int iE3 = cVar.e(y.p(cVar) + cVar.b);
            Object[] objArr6 = cVar.f425c;
            int i8 = iE2 + 1;
            if (iE2 <= iE3) {
                d.ao(objArr6, objArr6, iE2, i8, iE3 + 1);
            } else {
                d.ao(objArr6, objArr6, iE2, i8, objArr6.length);
                Object[] objArr7 = cVar.f425c;
                objArr7[objArr7.length - 1] = objArr7[0];
                d.ao(objArr7, objArr7, 0, 1, iE3 + 1);
            }
            cVar.f425c[iE3] = null;
        }
        cVar.d--;
        return obj3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return ((c) this).d;
    }
}
