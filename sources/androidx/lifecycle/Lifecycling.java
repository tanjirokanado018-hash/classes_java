package androidx.lifecycle;

import androidx.annotation.RestrictTo;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class Lifecycling {
    private static final int GENERATED_CALLBACK = 2;
    private static final int REFLECTIVE_CALLBACK = 1;
    public static final Lifecycling INSTANCE = new Lifecycling();
    private static final Map<Class<?>, Integer> callbackCache = new HashMap();
    private static final Map<Class<?>, List<Constructor<? extends GeneratedAdapter>>> classToAdapters = new HashMap();

    private Lifecycling() {
    }

    private final GeneratedAdapter createGeneratedAdapter(Constructor<? extends GeneratedAdapter> constructor, Object obj) {
        try {
            GeneratedAdapter generatedAdapterNewInstance = constructor.newInstance(obj);
            k.f(generatedAdapterNewInstance, "{\n            constructo…tance(`object`)\n        }");
            return generatedAdapterNewInstance;
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        } catch (InvocationTargetException e4) {
            throw new RuntimeException(e4);
        }
    }

    private final Constructor<? extends GeneratedAdapter> generatedConstructor(Class<?> cls) {
        try {
            Package r1 = cls.getPackage();
            String name = cls.getCanonicalName();
            String fullPackage = r1 != null ? r1.getName() : "";
            k.f(fullPackage, "fullPackage");
            if (fullPackage.length() != 0) {
                k.f(name, "name");
                name = name.substring(fullPackage.length() + 1);
                k.f(name, "this as java.lang.String).substring(startIndex)");
            }
            k.f(name, "if (fullPackage.isEmpty(…g(fullPackage.length + 1)");
            String adapterName = getAdapterName(name);
            if (fullPackage.length() != 0) {
                adapterName = fullPackage + '.' + adapterName;
            }
            Constructor declaredConstructor = Class.forName(adapterName).getDeclaredConstructor(cls);
            if (declaredConstructor.isAccessible()) {
                return declaredConstructor;
            }
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static final String getAdapterName(String className) {
        k.g(className, "className");
        StringBuilder sb = new StringBuilder();
        int iAa = v.d.aa(className, ".", 0, false);
        if (iAa >= 0) {
            int length = className.length();
            if (length < 0) {
                throw new OutOfMemoryError();
            }
            StringBuilder sb2 = new StringBuilder(length);
            int i2 = 0;
            do {
                sb2.append((CharSequence) className, i2, iAa);
                sb2.append("_");
                i2 = iAa + 1;
                if (iAa >= className.length()) {
                    break;
                }
                iAa = v.d.aa(className, ".", iAa + 1, false);
            } while (iAa > 0);
            sb2.append((CharSequence) className, i2, className.length());
            className = sb2.toString();
            k.f(className, "stringBuilder.append(this, i, length).toString()");
        }
        return androidx.appcompat.graphics.drawable.a.g(sb, className, "_LifecycleAdapter");
    }

    private final int getObserverConstructorType(Class<?> cls) {
        Map<Class<?>, Integer> map = callbackCache;
        Integer num = map.get(cls);
        if (num != null) {
            return num.intValue();
        }
        int iResolveObserverCallbackType = resolveObserverCallbackType(cls);
        map.put(cls, Integer.valueOf(iResolveObserverCallbackType));
        return iResolveObserverCallbackType;
    }

    private final boolean isLifecycleParent(Class<?> cls) {
        return cls != null && LifecycleObserver.class.isAssignableFrom(cls);
    }

    public static final LifecycleEventObserver lifecycleEventObserver(Object object) {
        k.g(object, "object");
        boolean z2 = object instanceof LifecycleEventObserver;
        boolean z3 = object instanceof DefaultLifecycleObserver;
        if (z2 && z3) {
            return new DefaultLifecycleObserverAdapter((DefaultLifecycleObserver) object, (LifecycleEventObserver) object);
        }
        if (z3) {
            return new DefaultLifecycleObserverAdapter((DefaultLifecycleObserver) object, null);
        }
        if (z2) {
            return (LifecycleEventObserver) object;
        }
        Class<?> cls = object.getClass();
        Lifecycling lifecycling = INSTANCE;
        if (lifecycling.getObserverConstructorType(cls) != 2) {
            return new ReflectiveGenericLifecycleObserver(object);
        }
        List<Constructor<? extends GeneratedAdapter>> list = classToAdapters.get(cls);
        k.d(list);
        List<Constructor<? extends GeneratedAdapter>> list2 = list;
        if (list2.size() == 1) {
            return new SingleGeneratedAdapterObserver(lifecycling.createGeneratedAdapter(list2.get(0), object));
        }
        int size = list2.size();
        GeneratedAdapter[] generatedAdapterArr = new GeneratedAdapter[size];
        for (int i2 = 0; i2 < size; i2++) {
            generatedAdapterArr[i2] = INSTANCE.createGeneratedAdapter(list2.get(i2), object);
        }
        return new CompositeGeneratedAdaptersObserver(generatedAdapterArr);
    }

    private final int resolveObserverCallbackType(Class<?> cls) {
        ArrayList arrayList;
        if (cls.getCanonicalName() == null) {
            return 1;
        }
        Constructor<? extends GeneratedAdapter> constructorGeneratedConstructor = generatedConstructor(cls);
        if (constructorGeneratedConstructor != null) {
            Map<Class<?>, List<Constructor<? extends GeneratedAdapter>>> map = classToAdapters;
            List<Constructor<? extends GeneratedAdapter>> listSingletonList = Collections.singletonList(constructorGeneratedConstructor);
            k.f(listSingletonList, "singletonList(element)");
            map.put(cls, listSingletonList);
            return 2;
        }
        if (ClassesInfoCache.sInstance.hasLifecycleMethods(cls)) {
            return 1;
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (isLifecycleParent(superclass)) {
            k.f(superclass, "superclass");
            if (getObserverConstructorType(superclass) == 1) {
                return 1;
            }
            List<Constructor<? extends GeneratedAdapter>> list = classToAdapters.get(superclass);
            k.d(list);
            arrayList = new ArrayList(list);
        } else {
            arrayList = null;
        }
        Class<?>[] interfaces = cls.getInterfaces();
        k.f(interfaces, "klass.interfaces");
        for (Class<?> intrface : interfaces) {
            if (isLifecycleParent(intrface)) {
                k.f(intrface, "intrface");
                if (getObserverConstructorType(intrface) == 1) {
                    return 1;
                }
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                List<Constructor<? extends GeneratedAdapter>> list2 = classToAdapters.get(intrface);
                k.d(list2);
                arrayList.addAll(list2);
            }
        }
        if (arrayList == null) {
            return 1;
        }
        classToAdapters.put(cls, arrayList);
        return 2;
    }
}
