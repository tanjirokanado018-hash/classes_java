package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi21Impl";
    private static Method sAddFontWeightStyle = null;
    private static Method sCreateFromFamiliesWithDefault = null;
    private static Class<?> sFontFamily = null;
    private static Constructor<?> sFontFamilyCtor = null;
    private static boolean sHasInitBeenCalled = false;

    private static boolean addFontWeightStyle(Object obj, String str, int i2, boolean z2) {
        init();
        try {
            return ((Boolean) sAddFontWeightStyle.invoke(obj, str, Integer.valueOf(i2), Boolean.valueOf(z2))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object obj) {
        init();
        try {
            Object objNewInstance = Array.newInstance(sFontFamily, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, objNewInstance);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private File getFile(@NonNull ParcelFileDescriptor parcelFileDescriptor) {
        try {
            String str = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd());
            if (OsConstants.S_ISREG(Os.stat(str).st_mode)) {
                return new File(str);
            }
        } catch (ErrnoException unused) {
        }
        return null;
    }

    private static void init() {
        Class<?> cls;
        Method method;
        Constructor<?> constructor;
        Method method2;
        if (sHasInitBeenCalled) {
            return;
        }
        sHasInitBeenCalled = true;
        try {
            cls = Class.forName(FONT_FAMILY_CLASS);
            constructor = cls.getConstructor(new Class[0]);
            method2 = cls.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, String.class, Integer.TYPE, Boolean.TYPE);
            method = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(cls, 1).getClass());
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            cls = null;
            method = null;
            constructor = null;
            method2 = null;
        }
        sFontFamilyCtor = constructor;
        sFontFamily = cls;
        sAddFontWeightStyle = method2;
        sCreateFromFamiliesWithDefault = method;
    }

    private static Object newFamily() {
        init();
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i2) {
        Object objNewFamily = newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
            File tempFile = TypefaceCompatUtil.getTempFile(context);
            if (tempFile == null) {
                return null;
            }
            try {
                if (!TypefaceCompatUtil.copyToFile(tempFile, resources, fontFileResourceEntry.getResourceId())) {
                    tempFile.delete();
                    return null;
                }
                boolean zAddFontWeightStyle = addFontWeightStyle(objNewFamily, tempFile.getPath(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic());
                tempFile.delete();
                if (!zAddFontWeightStyle) {
                    return null;
                }
            } catch (RuntimeException unused) {
                tempFile.delete();
                return null;
            } catch (Throwable th) {
                tempFile.delete();
                throw th;
            }
        }
        return createFromFamiliesWithDefault(objNewFamily);
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i2) {
        if (fontInfoArr.length < 1) {
            return null;
        }
        FontsContractCompat.FontInfo fontInfoFindBestInfo = findBestInfo(fontInfoArr, i2);
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(fontInfoFindBestInfo.getUri(), "r", cancellationSignal);
            if (parcelFileDescriptorOpenFileDescriptor == null) {
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
                return null;
            }
            try {
                File file = getFile(parcelFileDescriptorOpenFileDescriptor);
                if (file != null && file.canRead()) {
                    Typeface typefaceCreateFromFile = Typeface.createFromFile(file);
                    parcelFileDescriptorOpenFileDescriptor.close();
                    return typefaceCreateFromFile;
                }
                FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor());
                try {
                    Typeface typefaceCreateFromInputStream = super.createFromInputStream(context, fileInputStream);
                    fileInputStream.close();
                    parcelFileDescriptorOpenFileDescriptor.close();
                    return typefaceCreateFromInputStream;
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                try {
                    parcelFileDescriptorOpenFileDescriptor.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
                throw th3;
            }
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    @NonNull
    public Typeface createWeightStyle(@NonNull Context context, @NonNull Typeface typeface, int i2, boolean z2) {
        Typeface typefaceCreateWeightStyle;
        try {
            typefaceCreateWeightStyle = WeightTypefaceApi21.createWeightStyle(typeface, i2, z2);
        } catch (RuntimeException unused) {
            typefaceCreateWeightStyle = null;
        }
        return typefaceCreateWeightStyle == null ? super.createWeightStyle(context, typeface, i2, z2) : typefaceCreateWeightStyle;
    }
}
