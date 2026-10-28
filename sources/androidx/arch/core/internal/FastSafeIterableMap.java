package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
    private final HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap<>();

    @Nullable
    public Map.Entry<K, V> ceil(K k2) {
        if (contains(k2)) {
            return this.mHashMap.get(k2).mPrevious;
        }
        return null;
    }

    public boolean contains(K k2) {
        return this.mHashMap.containsKey(k2);
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    @Nullable
    public SafeIterableMap.Entry<K, V> get(K k2) {
        return this.mHashMap.get(k2);
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public V putIfAbsent(@NonNull K k2, @NonNull V v2) {
        SafeIterableMap.Entry<K, V> entry = get(k2);
        if (entry != null) {
            return entry.mValue;
        }
        this.mHashMap.put(k2, put(k2, v2));
        return null;
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public V remove(@NonNull K k2) {
        V v2 = (V) super.remove(k2);
        this.mHashMap.remove(k2);
        return v2;
    }
}
