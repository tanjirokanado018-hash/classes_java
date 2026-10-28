package androidx.core.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class TreeIterator<T> implements Iterator<T> {
    private final p.l getChildIterator;
    private Iterator<? extends T> iterator;
    private final List<Iterator<T>> stack = new ArrayList();

    public TreeIterator(Iterator<? extends T> it, p.l lVar) {
        this.getChildIterator = lVar;
        this.iterator = it;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private final void prepareNextIterator(T t2) {
        Iterator<? extends T> it = (Iterator) this.getChildIterator.invoke(t2);
        if (it != null && it.hasNext()) {
            this.stack.add((Iterator<T>) this.iterator);
            this.iterator = it;
            return;
        }
        while (!this.iterator.hasNext() && (!this.stack.isEmpty())) {
            List<Iterator<T>> list = this.stack;
            kotlin.jvm.internal.k.g(list, "<this>");
            if (list.isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
            this.iterator = list.get(y.p(list));
            List<Iterator<T>> list2 = this.stack;
            kotlin.jvm.internal.k.g(list2, "<this>");
            if (list2.isEmpty()) {
                throw new NoSuchElementException("List is empty.");
            }
            list2.remove(y.p(list2));
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        T next = this.iterator.next();
        prepareNextIterator(next);
        return next;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
