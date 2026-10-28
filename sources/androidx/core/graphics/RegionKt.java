package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import java.util.Iterator;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class RegionKt {
    public static final Region and(Region region, Rect rect) {
        Region region2 = new Region(region);
        region2.op(rect, Region.Op.INTERSECT);
        return region2;
    }

    public static final boolean contains(Region region, Point point) {
        return region.contains(point.x, point.y);
    }

    public static final void forEach(Region region, l lVar) {
        RegionIterator regionIterator = new RegionIterator(region);
        while (true) {
            Rect rect = new Rect();
            if (!regionIterator.next(rect)) {
                return;
            } else {
                lVar.invoke(rect);
            }
        }
    }

    public static final Iterator<Rect> iterator(Region region) {
        return new Iterator<Rect>(region) { // from class: androidx.core.graphics.RegionKt.iterator.1
            private boolean hasMore;
            private final RegionIterator iterator;
            private final Rect rect;

            {
                RegionIterator regionIterator = new RegionIterator(region);
                this.iterator = regionIterator;
                Rect rect = new Rect();
                this.rect = rect;
                this.hasMore = regionIterator.next(rect);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.hasMore;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public Rect next() {
                if (!this.hasMore) {
                    throw new IndexOutOfBoundsException();
                }
                Rect rect = new Rect(this.rect);
                this.hasMore = this.iterator.next(this.rect);
                return rect;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }

    public static final Region minus(Region region, Rect rect) {
        Region region2 = new Region(region);
        region2.op(rect, Region.Op.DIFFERENCE);
        return region2;
    }

    public static final Region not(Region region) {
        Region region2 = new Region(region.getBounds());
        region2.op(region, Region.Op.DIFFERENCE);
        return region2;
    }

    public static final Region or(Region region, Rect rect) {
        Region region2 = new Region(region);
        region2.union(rect);
        return region2;
    }

    public static final Region plus(Region region, Rect rect) {
        Region region2 = new Region(region);
        region2.union(rect);
        return region2;
    }

    public static final Region unaryMinus(Region region) {
        Region region2 = new Region(region.getBounds());
        region2.op(region, Region.Op.DIFFERENCE);
        return region2;
    }

    public static final Region xor(Region region, Rect rect) {
        Region region2 = new Region(region);
        region2.op(rect, Region.Op.XOR);
        return region2;
    }

    public static final Region and(Region region, Region region2) {
        Region region3 = new Region(region);
        region3.op(region2, Region.Op.INTERSECT);
        return region3;
    }

    public static final Region minus(Region region, Region region2) {
        Region region3 = new Region(region);
        region3.op(region2, Region.Op.DIFFERENCE);
        return region3;
    }

    public static final Region or(Region region, Region region2) {
        Region region3 = new Region(region);
        region3.op(region2, Region.Op.UNION);
        return region3;
    }

    public static final Region plus(Region region, Region region2) {
        Region region3 = new Region(region);
        region3.op(region2, Region.Op.UNION);
        return region3;
    }

    public static final Region xor(Region region, Region region2) {
        Region region3 = new Region(region);
        region3.op(region2, Region.Op.XOR);
        return region3;
    }
}
