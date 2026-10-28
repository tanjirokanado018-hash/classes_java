package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class MenuKt {
    public static final boolean contains(Menu menu, MenuItem menuItem) {
        int size = menu.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (kotlin.jvm.internal.k.a(menu.getItem(i2), menuItem)) {
                return true;
            }
        }
        return false;
    }

    public static final void forEach(Menu menu, p.l lVar) {
        int size = menu.size();
        for (int i2 = 0; i2 < size; i2++) {
            lVar.invoke(menu.getItem(i2));
        }
    }

    public static final void forEachIndexed(Menu menu, p.p pVar) {
        int size = menu.size();
        for (int i2 = 0; i2 < size; i2++) {
            pVar.invoke(Integer.valueOf(i2), menu.getItem(i2));
        }
    }

    public static final MenuItem get(Menu menu, int i2) {
        return menu.getItem(i2);
    }

    public static final u.g getChildren(final Menu menu) {
        return new u.g() { // from class: androidx.core.view.MenuKt$children$1
            @Override // u.g
            public Iterator<MenuItem> iterator() {
                return MenuKt.iterator(menu);
            }
        };
    }

    public static final int getSize(Menu menu) {
        return menu.size();
    }

    public static final boolean isEmpty(Menu menu) {
        return menu.size() == 0;
    }

    public static final boolean isNotEmpty(Menu menu) {
        return menu.size() != 0;
    }

    public static final Iterator<MenuItem> iterator(final Menu menu) {
        return new Iterator<MenuItem>() { // from class: androidx.core.view.MenuKt.iterator.1
            private int index;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < menu.size();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public MenuItem next() {
                Menu menu2 = menu;
                int i2 = this.index;
                this.index = i2 + 1;
                MenuItem item = menu2.getItem(i2);
                if (item != null) {
                    return item;
                }
                throw new IndexOutOfBoundsException();
            }

            @Override // java.util.Iterator
            public void remove() {
                g.i iVar;
                Menu menu2 = menu;
                int i2 = this.index - 1;
                this.index = i2;
                MenuItem item = menu2.getItem(i2);
                if (item != null) {
                    menu2.removeItem(item.getItemId());
                    iVar = g.i.f423a;
                } else {
                    iVar = null;
                }
                if (iVar == null) {
                    throw new IndexOutOfBoundsException();
                }
            }
        };
    }

    public static final void minusAssign(Menu menu, MenuItem menuItem) {
        menu.removeItem(menuItem.getItemId());
    }

    public static final void removeItemAt(Menu menu, int i2) {
        g.i iVar;
        MenuItem item = menu.getItem(i2);
        if (item != null) {
            menu.removeItem(item.getItemId());
            iVar = g.i.f423a;
        } else {
            iVar = null;
        }
        if (iVar == null) {
            throw new IndexOutOfBoundsException();
        }
    }
}
