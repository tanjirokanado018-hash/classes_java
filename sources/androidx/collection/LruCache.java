package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = i2;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int safeSizeOf(K k2, V v2) {
        int iSizeOf = sizeOf(k2, v2);
        if (iSizeOf >= 0) {
            return iSizeOf;
        }
        throw new IllegalStateException("Negative size: " + k2 + "=" + v2);
    }

    @Nullable
    public V create(@NonNull K k2) {
        return null;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public void entryRemoved(boolean z2, @NonNull K k2, @NonNull V v2, @Nullable V v3) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    @Nullable
    public final V get(@NonNull K k2) {
        V vPut;
        if (k2 == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            try {
                V v2 = this.map.get(k2);
                if (v2 != null) {
                    this.hitCount++;
                    return v2;
                }
                this.missCount++;
                V vCreate = create(k2);
                if (vCreate == null) {
                    return null;
                }
                synchronized (this) {
                    try {
                        this.createCount++;
                        vPut = this.map.put(k2, vCreate);
                        if (vPut != null) {
                            this.map.put(k2, vPut);
                        } else {
                            this.size += safeSizeOf(k2, vCreate);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (vPut != null) {
                    entryRemoved(false, k2, vCreate, vPut);
                    return vPut;
                }
                trimToSize(this.maxSize);
                return vCreate;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    @Nullable
    public final V put(@NonNull K k2, @NonNull V v2) {
        V vPut;
        if (k2 == null || v2 == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            try {
                this.putCount++;
                this.size += safeSizeOf(k2, v2);
                vPut = this.map.put(k2, v2);
                if (vPut != null) {
                    this.size -= safeSizeOf(k2, vPut);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (vPut != null) {
            entryRemoved(false, k2, vPut, v2);
        }
        trimToSize(this.maxSize);
        return vPut;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    @Nullable
    public final V remove(@NonNull K k2) {
        V vRemove;
        if (k2 == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            try {
                vRemove = this.map.remove(k2);
                if (vRemove != null) {
                    this.size -= safeSizeOf(k2, vRemove);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (vRemove != null) {
            entryRemoved(false, k2, vRemove, null);
        }
        return vRemove;
    }

    public void resize(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = i2;
        }
        trimToSize(i2);
    }

    public final synchronized int size() {
        return this.size;
    }

    public int sizeOf(@NonNull K k2, @NonNull V v2) {
        return 1;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int i2;
        try {
            int i3 = this.hitCount;
            int i4 = this.missCount + i3;
            i2 = i4 != 0 ? (i3 * 100) / i4 : 0;
            Locale locale = Locale.US;
        } catch (Throwable th) {
            throw th;
        }
        return "LruCache[maxSize=" + this.maxSize + ",hits=" + this.hitCount + ",misses=" + this.missCount + ",hitRate=" + i2 + "%]";
    }

    public void trimToSize(int i2) {
        K key;
        V value;
        while (true) {
            synchronized (this) {
                try {
                    if (this.size < 0 || (this.map.isEmpty() && this.size != 0)) {
                        break;
                    }
                    if (this.size > i2 && !this.map.isEmpty()) {
                        Map.Entry<K, V> next = this.map.entrySet().iterator().next();
                        key = next.getKey();
                        value = next.getValue();
                        this.map.remove(key);
                        this.size -= safeSizeOf(key, value);
                        this.evictionCount++;
                    }
                    return;
                } catch (Throwable th) {
                    throw th;
                }
            }
            entryRemoved(true, key, value, null);
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }
}
