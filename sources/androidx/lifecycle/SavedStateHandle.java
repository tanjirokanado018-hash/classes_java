package androidx.lifecycle;

import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.core.os.BundleKt;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.k;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SavedStateHandle {
    private static final String KEYS = "keys";
    private static final String VALUES = "values";
    private final Map<String, y.c> flows;
    private final Map<String, SavingStateLiveData<?>> liveDatas;
    private final Map<String, Object> regular;
    private final SavedStateRegistry.SavedStateProvider savedStateProvider;
    private final Map<String, SavedStateRegistry.SavedStateProvider> savedStateProviders;
    public static final Companion Companion = new Companion(null);
    private static final Class<? extends Object>[] ACCEPTABLE_CLASSES = {Boolean.TYPE, boolean[].class, Double.TYPE, double[].class, Integer.TYPE, int[].class, Long.TYPE, long[].class, String.class, String[].class, Binder.class, Bundle.class, Byte.TYPE, byte[].class, Character.TYPE, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, Float.TYPE, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, Short.TYPE, short[].class, SparseArray.class, Size.class, SizeF.class};

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(kotlin.jvm.internal.f fVar) {
            this();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final SavedStateHandle createHandle(Bundle bundle, Bundle bundle2) {
            if (bundle == null) {
                if (bundle2 == null) {
                    return new SavedStateHandle();
                }
                HashMap map = new HashMap();
                for (String key : bundle2.keySet()) {
                    k.f(key, "key");
                    map.put(key, bundle2.get(key));
                }
                return new SavedStateHandle(map);
            }
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(SavedStateHandle.KEYS);
            ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(SavedStateHandle.VALUES);
            if (parcelableArrayList == null || parcelableArrayList2 == null || parcelableArrayList.size() != parcelableArrayList2.size()) {
                throw new IllegalStateException("Invalid bundle passed as restored state".toString());
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int size = parcelableArrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object obj = parcelableArrayList.get(i2);
                k.e(obj, "null cannot be cast to non-null type kotlin.String");
                linkedHashMap.put((String) obj, parcelableArrayList2.get(i2));
            }
            return new SavedStateHandle(linkedHashMap);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final boolean validateValue(Object obj) {
            if (obj == null) {
                return true;
            }
            for (Class cls : SavedStateHandle.ACCEPTABLE_CLASSES) {
                k.d(cls);
                if (cls.isInstance(obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class SavingStateLiveData<T> extends MutableLiveData<T> {
        private SavedStateHandle handle;
        private String key;

        public SavingStateLiveData(SavedStateHandle savedStateHandle, String key) {
            k.g(key, "key");
            this.key = key;
            this.handle = savedStateHandle;
        }

        public final void detach() {
            this.handle = null;
        }

        @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
        public void setValue(T t2) {
            SavedStateHandle savedStateHandle = this.handle;
            if (savedStateHandle != null) {
                savedStateHandle.regular.put(this.key, t2);
                y.c cVar = (y.c) savedStateHandle.flows.get(this.key);
                if (cVar != null) {
                    cVar.a(t2);
                }
            }
            super.setValue(t2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SavingStateLiveData(SavedStateHandle savedStateHandle, String key, T t2) {
            super(t2);
            k.g(key, "key");
            this.key = key;
            this.handle = savedStateHandle;
        }
    }

    public SavedStateHandle() {
        this.regular = new LinkedHashMap();
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        final int i2 = 0;
        this.savedStateProvider = new SavedStateRegistry.SavedStateProvider(this) { // from class: androidx.lifecycle.g
            public final /* synthetic */ SavedStateHandle b;

            {
                this.b = this;
            }

            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                int i3 = i2;
                SavedStateHandle savedStateHandle = this.b;
                switch (i3) {
                    case 0:
                        break;
                }
                return SavedStateHandle.savedStateProvider$lambda$0(savedStateHandle);
            }
        };
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final SavedStateHandle createHandle(Bundle bundle, Bundle bundle2) {
        return Companion.createHandle(bundle, bundle2);
    }

    private final <T> MutableLiveData<T> getLiveDataInternal(String str, boolean z2, T t2) {
        SavingStateLiveData<?> savingStateLiveData;
        SavingStateLiveData<?> savingStateLiveData2 = this.liveDatas.get(str);
        SavingStateLiveData<?> savingStateLiveData3 = savingStateLiveData2 instanceof MutableLiveData ? savingStateLiveData2 : null;
        if (savingStateLiveData3 != null) {
            return savingStateLiveData3;
        }
        if (this.regular.containsKey(str)) {
            savingStateLiveData = new SavingStateLiveData<>(this, str, this.regular.get(str));
        } else if (z2) {
            this.regular.put(str, t2);
            savingStateLiveData = new SavingStateLiveData<>(this, str, t2);
        } else {
            savingStateLiveData = new SavingStateLiveData<>(this, str);
        }
        this.liveDatas.put(str, savingStateLiveData);
        return savingStateLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Bundle savedStateProvider$lambda$0(SavedStateHandle this$0) {
        k.g(this$0, "this$0");
        Map<String, SavedStateRegistry.SavedStateProvider> map = this$0.savedStateProviders;
        k.g(map, "<this>");
        int size = map.size();
        Map linkedHashMap = size != 0 ? size != 1 ? new LinkedHashMap(map) : y.al(map) : h.h.b;
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            this$0.set((String) entry.getKey(), ((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState());
        }
        Set<String> setKeySet = this$0.regular.keySet();
        ArrayList arrayList = new ArrayList(setKeySet.size());
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (String str : setKeySet) {
            arrayList.add(str);
            arrayList2.add(this$0.regular.get(str));
        }
        return BundleKt.bundleOf(new g.d(KEYS, arrayList), new g.d(VALUES, arrayList2));
    }

    @MainThread
    public final void clearSavedStateProvider(String key) {
        k.g(key, "key");
        this.savedStateProviders.remove(key);
    }

    @MainThread
    public final boolean contains(String key) {
        k.g(key, "key");
        return this.regular.containsKey(key);
    }

    @MainThread
    public final <T> T get(String key) {
        k.g(key, "key");
        try {
            return (T) this.regular.get(key);
        } catch (ClassCastException unused) {
            remove(key);
            return null;
        }
    }

    @MainThread
    public final <T> MutableLiveData<T> getLiveData(String key) {
        k.g(key, "key");
        MutableLiveData<T> liveDataInternal = getLiveDataInternal(key, false, null);
        k.e(liveDataInternal, "null cannot be cast to non-null type androidx.lifecycle.MutableLiveData<T of androidx.lifecycle.SavedStateHandle.getLiveData>");
        return liveDataInternal;
    }

    @MainThread
    public final <T> y.b getStateFlow(String key, T t2) {
        k.g(key, "key");
        Map<String, y.c> map = this.flows;
        y.c cVar = map.get(key);
        if (cVar == null) {
            if (!this.regular.containsKey(key)) {
                this.regular.put(key, t2);
            }
            Object obj = this.regular.get(key);
            if (obj == null) {
                obj = z.a.f564a;
            }
            cVar = new y.c(obj);
            this.flows.put(key, cVar);
            map.put(key, cVar);
        }
        return new y.a();
    }

    @MainThread
    public final Set<String> keys() {
        return h.d.aq(h.d.aq(this.regular.keySet(), this.savedStateProviders.keySet()), this.liveDatas.keySet());
    }

    @MainThread
    public final <T> T remove(String key) {
        k.g(key, "key");
        T t2 = (T) this.regular.remove(key);
        SavingStateLiveData<?> savingStateLiveDataRemove = this.liveDatas.remove(key);
        if (savingStateLiveDataRemove != null) {
            savingStateLiveDataRemove.detach();
        }
        this.flows.remove(key);
        return t2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final SavedStateRegistry.SavedStateProvider savedStateProvider() {
        return this.savedStateProvider;
    }

    @MainThread
    public final <T> void set(String key, T t2) {
        k.g(key, "key");
        if (!Companion.validateValue(t2)) {
            StringBuilder sb = new StringBuilder("Can't put value with type ");
            k.d(t2);
            sb.append(t2.getClass());
            sb.append(" into saved state");
            throw new IllegalArgumentException(sb.toString());
        }
        SavingStateLiveData<?> savingStateLiveData = this.liveDatas.get(key);
        SavingStateLiveData<?> savingStateLiveData2 = savingStateLiveData instanceof MutableLiveData ? savingStateLiveData : null;
        if (savingStateLiveData2 != null) {
            savingStateLiveData2.setValue(t2);
        } else {
            this.regular.put(key, t2);
        }
        y.c cVar = this.flows.get(key);
        if (cVar == null) {
            return;
        }
        cVar.a(t2);
    }

    @MainThread
    public final void setSavedStateProvider(String key, SavedStateRegistry.SavedStateProvider provider) {
        k.g(key, "key");
        k.g(provider, "provider");
        this.savedStateProviders.put(key, provider);
    }

    public SavedStateHandle(Map<String, ? extends Object> initialState) {
        k.g(initialState, "initialState");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.regular = linkedHashMap;
        this.savedStateProviders = new LinkedHashMap();
        this.liveDatas = new LinkedHashMap();
        this.flows = new LinkedHashMap();
        final int i2 = 1;
        this.savedStateProvider = new SavedStateRegistry.SavedStateProvider(this) { // from class: androidx.lifecycle.g
            public final /* synthetic */ SavedStateHandle b;

            {
                this.b = this;
            }

            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                int i3 = i2;
                SavedStateHandle savedStateHandle = this.b;
                switch (i3) {
                    case 0:
                        break;
                }
                return SavedStateHandle.savedStateProvider$lambda$0(savedStateHandle);
            }
        };
        linkedHashMap.putAll(initialState);
    }

    @MainThread
    public final <T> MutableLiveData<T> getLiveData(String key, T t2) {
        k.g(key, "key");
        return getLiveDataInternal(key, true, t2);
    }
}
