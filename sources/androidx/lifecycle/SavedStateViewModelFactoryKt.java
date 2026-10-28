package androidx.lifecycle;

import android.app.Application;
import androidx.annotation.RestrictTo;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class SavedStateViewModelFactoryKt {
    private static final List<Class<?>> ANDROID_VIEWMODEL_SIGNATURE;
    private static final List<Class<?>> VIEWMODEL_SIGNATURE;

    static {
        List<Class<?>> listAsList = Arrays.asList(Application.class, SavedStateHandle.class);
        k.f(listAsList, "asList(this)");
        ANDROID_VIEWMODEL_SIGNATURE = listAsList;
        List<Class<?>> listSingletonList = Collections.singletonList(SavedStateHandle.class);
        k.f(listSingletonList, "singletonList(element)");
        VIEWMODEL_SIGNATURE = listSingletonList;
    }

    public static final <T> Constructor<T> findMatchingConstructor(Class<T> modelClass, List<? extends Class<?>> signature) {
        List listSingletonList;
        k.g(modelClass, "modelClass");
        k.g(signature, "signature");
        Object[] constructors = modelClass.getConstructors();
        k.f(constructors, "modelClass.constructors");
        for (Object obj : constructors) {
            Constructor<T> constructor = (Constructor<T>) obj;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            k.f(parameterTypes, "constructor.parameterTypes");
            int length = parameterTypes.length;
            if (length == 0) {
                listSingletonList = h.g.b;
            } else if (length != 1) {
                listSingletonList = new ArrayList(new h.b(parameterTypes));
            } else {
                listSingletonList = Collections.singletonList(parameterTypes[0]);
                k.f(listSingletonList, "singletonList(element)");
            }
            if (k.a(signature, listSingletonList)) {
                return constructor;
            }
            if (signature.size() == listSingletonList.size() && listSingletonList.containsAll(signature)) {
                throw new UnsupportedOperationException("Class " + modelClass.getSimpleName() + " must have parameters in the proper order: " + signature);
            }
        }
        return null;
    }

    public static final <T extends ViewModel> T newInstance(Class<T> modelClass, Constructor<T> constructor, Object... params) {
        k.g(modelClass, "modelClass");
        k.g(constructor, "constructor");
        k.g(params, "params");
        try {
            return constructor.newInstance(Arrays.copyOf(params, params.length));
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Failed to access " + modelClass, e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException("A " + modelClass + " cannot be instantiated.", e3);
        } catch (InvocationTargetException e4) {
            throw new RuntimeException("An exception happened in constructor of " + modelClass, e4.getCause());
        }
    }
}
