package androidx.core.content.res;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.annotation.AnyRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ResourcesCompat {

    @AnyRes
    public static final int ID_NULL = 0;
    private static final String TAG = "ResourcesCompat";
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    @GuardedBy("sColorStateCacheLock")
    private static final WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);
    private static final Object sColorStateCacheLock = new Object();

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static Drawable getDrawable(Resources resources, int i2, Resources.Theme theme) {
            return resources.getDrawable(i2, theme);
        }

        @DoNotInline
        public static Drawable getDrawableForDensity(Resources resources, int i2, int i3, Resources.Theme theme) {
            return resources.getDrawableForDensity(i2, i3, theme);
        }
    }

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static int getColor(Resources resources, int i2, Resources.Theme theme) {
            return resources.getColor(i2, theme);
        }

        @NonNull
        @DoNotInline
        public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) {
            return resources.getColorStateList(i2, theme);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static float getFloat(@NonNull Resources resources, @DimenRes int i2) {
            return resources.getFloat(i2);
        }
    }

    public static class ColorStateListCacheEntry {
        final Configuration mConfiguration;
        final int mThemeHash;
        final ColorStateList mValue;

        public ColorStateListCacheEntry(@NonNull ColorStateList colorStateList, @NonNull Configuration configuration, @Nullable Resources.Theme theme) {
            this.mValue = colorStateList;
            this.mConfiguration = configuration;
            this.mThemeHash = theme == null ? 0 : theme.hashCode();
        }
    }

    public static final class ColorStateListCacheKey {
        final Resources mResources;
        final Resources.Theme mTheme;

        public ColorStateListCacheKey(@NonNull Resources resources, @Nullable Resources.Theme theme) {
            this.mResources = resources;
            this.mTheme = theme;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ColorStateListCacheKey.class != obj.getClass()) {
                return false;
            }
            ColorStateListCacheKey colorStateListCacheKey = (ColorStateListCacheKey) obj;
            return this.mResources.equals(colorStateListCacheKey.mResources) && ObjectsCompat.equals(this.mTheme, colorStateListCacheKey.mTheme);
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mResources, this.mTheme);
        }
    }

    public static abstract class FontCallback {
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static Handler getHandler(@Nullable Handler handler) {
            return handler == null ? new Handler(Looper.getMainLooper()) : handler;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void callbackFailAsync(int i2, @Nullable Handler handler) {
            getHandler(handler).post(new b(this, i2, 0));
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void callbackSuccessAsync(@NonNull Typeface typeface, @Nullable Handler handler) {
            getHandler(handler).post(new a(0, this, typeface));
        }

        /* JADX INFO: renamed from: onFontRetrievalFailed, reason: merged with bridge method [inline-methods] */
        public abstract void lambda$callbackFailAsync$1(int i2);

        /* JADX INFO: renamed from: onFontRetrieved, reason: merged with bridge method [inline-methods] */
        public abstract void lambda$callbackSuccessAsync$0(@NonNull Typeface typeface);
    }

    public static final class ThemeCompat {

        @RequiresApi(23)
        public static class Api23Impl {
            private static Method sRebaseMethod;
            private static boolean sRebaseMethodFetched;
            private static final Object sRebaseMethodLock = new Object();

            private Api23Impl() {
            }

            @SuppressLint({"BanUncheckedReflection"})
            public static void rebase(@NonNull Resources.Theme theme) {
                synchronized (sRebaseMethodLock) {
                    if (!sRebaseMethodFetched) {
                        try {
                            Method declaredMethod = Resources.Theme.class.getDeclaredMethod("rebase", new Class[0]);
                            sRebaseMethod = declaredMethod;
                            declaredMethod.setAccessible(true);
                        } catch (NoSuchMethodException unused) {
                        }
                        sRebaseMethodFetched = true;
                    }
                    Method method = sRebaseMethod;
                    if (method != null) {
                        try {
                            method.invoke(theme, new Object[0]);
                        } catch (IllegalAccessException | InvocationTargetException unused2) {
                            sRebaseMethod = null;
                        }
                    }
                }
            }
        }

        @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
        public static class Api29Impl {
            private Api29Impl() {
            }

            @DoNotInline
            public static void rebase(@NonNull Resources.Theme theme) {
                theme.rebase();
            }
        }

        private ThemeCompat() {
        }

        public static void rebase(@NonNull Resources.Theme theme) {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 29) {
                Api29Impl.rebase(theme);
            } else if (i2 >= 23) {
                Api23Impl.rebase(theme);
            }
        }
    }

    private ResourcesCompat() {
    }

    private static void addColorStateListToCache(@NonNull ColorStateListCacheKey colorStateListCacheKey, @ColorRes int i2, @NonNull ColorStateList colorStateList, @Nullable Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            try {
                WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> weakHashMap = sColorStateCaches;
                SparseArray<ColorStateListCacheEntry> sparseArray = weakHashMap.get(colorStateListCacheKey);
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                    weakHashMap.put(colorStateListCacheKey, sparseArray);
                }
                sparseArray.append(i2, new ColorStateListCacheEntry(colorStateList, colorStateListCacheKey.mResources.getConfiguration(), theme));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void clearCachesForTheme(@NonNull Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            try {
                Iterator<ColorStateListCacheKey> it = sColorStateCaches.keySet().iterator();
                while (it.hasNext()) {
                    ColorStateListCacheKey next = it.next();
                    if (next != null && theme.equals(next.mTheme)) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:
    
        if (r2.mThemeHash == r5.hashCode()) goto L22;
     */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static android.content.res.ColorStateList getCachedColorStateList(@androidx.annotation.NonNull androidx.core.content.res.ResourcesCompat.ColorStateListCacheKey r5, @androidx.annotation.ColorRes int r6) {
        /*
            java.lang.Object r0 = androidx.core.content.res.ResourcesCompat.sColorStateCacheLock
            monitor-enter(r0)
            java.util.WeakHashMap<androidx.core.content.res.ResourcesCompat$ColorStateListCacheKey, android.util.SparseArray<androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry>> r1 = androidx.core.content.res.ResourcesCompat.sColorStateCaches     // Catch: java.lang.Throwable -> L32
            java.lang.Object r1 = r1.get(r5)     // Catch: java.lang.Throwable -> L32
            android.util.SparseArray r1 = (android.util.SparseArray) r1     // Catch: java.lang.Throwable -> L32
            if (r1 == 0) goto L45
            int r2 = r1.size()     // Catch: java.lang.Throwable -> L32
            if (r2 <= 0) goto L45
            java.lang.Object r2 = r1.get(r6)     // Catch: java.lang.Throwable -> L32
            androidx.core.content.res.ResourcesCompat$ColorStateListCacheEntry r2 = (androidx.core.content.res.ResourcesCompat.ColorStateListCacheEntry) r2     // Catch: java.lang.Throwable -> L32
            if (r2 == 0) goto L45
            android.content.res.Configuration r3 = r2.mConfiguration     // Catch: java.lang.Throwable -> L32
            android.content.res.Resources r4 = r5.mResources     // Catch: java.lang.Throwable -> L32
            android.content.res.Configuration r4 = r4.getConfiguration()     // Catch: java.lang.Throwable -> L32
            boolean r3 = r3.equals(r4)     // Catch: java.lang.Throwable -> L32
            if (r3 == 0) goto L42
            android.content.res.Resources$Theme r5 = r5.mTheme     // Catch: java.lang.Throwable -> L32
            if (r5 != 0) goto L34
            int r3 = r2.mThemeHash     // Catch: java.lang.Throwable -> L32
            if (r3 == 0) goto L3e
            goto L34
        L32:
            r5 = move-exception
            goto L48
        L34:
            if (r5 == 0) goto L42
            int r3 = r2.mThemeHash     // Catch: java.lang.Throwable -> L32
            int r5 = r5.hashCode()     // Catch: java.lang.Throwable -> L32
            if (r3 != r5) goto L42
        L3e:
            android.content.res.ColorStateList r5 = r2.mValue     // Catch: java.lang.Throwable -> L32
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L32
            return r5
        L42:
            r1.remove(r6)     // Catch: java.lang.Throwable -> L32
        L45:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L32
            r5 = 0
            return r5
        L48:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L32
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.getCachedColorStateList(androidx.core.content.res.ResourcesCompat$ColorStateListCacheKey, int):android.content.res.ColorStateList");
    }

    @Nullable
    public static Typeface getCachedFont(@NonNull Context context, @FontRes int i2) {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, new TypedValue(), 0, null, null, false, true);
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) {
        return Build.VERSION.SDK_INT >= 23 ? Api23Impl.getColor(resources, i2, theme) : resources.getColor(i2);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) {
        ColorStateListCacheKey colorStateListCacheKey = new ColorStateListCacheKey(resources, theme);
        ColorStateList cachedColorStateList = getCachedColorStateList(colorStateListCacheKey, i2);
        if (cachedColorStateList != null) {
            return cachedColorStateList;
        }
        ColorStateList colorStateListInflateColorStateList = inflateColorStateList(resources, i2, theme);
        if (colorStateListInflateColorStateList == null) {
            return Build.VERSION.SDK_INT >= 23 ? Api23Impl.getColorStateList(resources, i2, theme) : resources.getColorStateList(i2);
        }
        addColorStateListToCache(colorStateListCacheKey, i2, colorStateListInflateColorStateList, theme);
        return colorStateListInflateColorStateList;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int i2, @Nullable Resources.Theme theme) {
        return Api21Impl.getDrawable(resources, i2, theme);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int i2, int i3, @Nullable Resources.Theme theme) {
        return Api21Impl.getDrawableForDensity(resources, i2, i3, theme);
    }

    public static float getFloat(@NonNull Resources resources, @DimenRes int i2) {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getFloat(resources, i2);
        }
        TypedValue typedValue = getTypedValue();
        resources.getValue(i2, typedValue, true);
        if (typedValue.type == 4) {
            return typedValue.getFloat();
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i2) + " type #0x" + Integer.toHexString(typedValue.type) + " is not valid");
    }

    @Nullable
    public static Typeface getFont(@NonNull Context context, @FontRes int i2) {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, new TypedValue(), 0, null, null, false, false);
    }

    @NonNull
    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = sTempTypedValue;
        TypedValue typedValue = threadLocal.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        threadLocal.set(typedValue2);
        return typedValue2;
    }

    @Nullable
    private static ColorStateList inflateColorStateList(Resources resources, int i2, @Nullable Resources.Theme theme) {
        if (isColorInt(resources, i2)) {
            return null;
        }
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i2), theme);
        } catch (Exception unused) {
            return null;
        }
    }

    private static boolean isColorInt(@NonNull Resources resources, @ColorRes int i2) {
        TypedValue typedValue = getTypedValue();
        resources.getValue(i2, typedValue, true);
        int i3 = typedValue.type;
        return i3 >= 28 && i3 <= 31;
    }

    private static Typeface loadFont(@NonNull Context context, int i2, @NonNull TypedValue typedValue, int i3, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z2, boolean z3) {
        Resources resources = context.getResources();
        resources.getValue(i2, typedValue, true);
        Typeface typefaceLoadFont = loadFont(context, resources, typedValue, i2, i3, fontCallback, handler, z2, z3);
        if (typefaceLoadFont != null || fontCallback != null || z3) {
            return typefaceLoadFont;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i2) + " could not be retrieved.");
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Typeface getFont(@NonNull Context context, @FontRes int i2, @NonNull TypedValue typedValue, int i3, @Nullable FontCallback fontCallback) {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, typedValue, i3, fontCallback, null, true, false);
    }

    private static Typeface loadFont(@NonNull Context context, Resources resources, @NonNull TypedValue typedValue, int i2, int i3, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z2, boolean z3) {
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i2) + "\" (" + Integer.toHexString(i2) + ") is not a Font: " + typedValue);
        }
        String string = charSequence.toString();
        if (!string.startsWith("res/")) {
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        }
        Typeface typefaceFindFromCache = TypefaceCompat.findFromCache(resources, i2, string, typedValue.assetCookie, i3);
        if (typefaceFindFromCache != null) {
            if (fontCallback != null) {
                fontCallback.callbackSuccessAsync(typefaceFindFromCache, handler);
            }
            return typefaceFindFromCache;
        }
        if (z3) {
            return null;
        }
        try {
            if (string.toLowerCase().endsWith(".xml")) {
                FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry = FontResourcesParserCompat.parse(resources.getXml(i2), resources);
                if (familyResourceEntry != null) {
                    return TypefaceCompat.createFromResourcesFamilyXml(context, familyResourceEntry, resources, i2, string, typedValue.assetCookie, i3, fontCallback, handler, z2);
                }
                if (fontCallback != null) {
                    fontCallback.callbackFailAsync(-3, handler);
                }
                return null;
            }
            Typeface typefaceCreateFromResourcesFontFile = TypefaceCompat.createFromResourcesFontFile(context, resources, i2, string, typedValue.assetCookie, i3);
            if (fontCallback != null) {
                if (typefaceCreateFromResourcesFontFile != null) {
                    fontCallback.callbackSuccessAsync(typefaceCreateFromResourcesFontFile, handler);
                } else {
                    fontCallback.callbackFailAsync(-3, handler);
                }
            }
            return typefaceCreateFromResourcesFontFile;
        } catch (IOException | XmlPullParserException unused) {
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        }
    }

    public static void getFont(@NonNull Context context, @FontRes int i2, @NonNull FontCallback fontCallback, @Nullable Handler handler) {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
        } else {
            loadFont(context, i2, new TypedValue(), 0, fontCallback, handler, false, false);
        }
    }
}
