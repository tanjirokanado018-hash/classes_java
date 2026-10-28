package androidx.core.content.pm;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ShortcutManagerCompat {

    @VisibleForTesting
    static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;
    private static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    public static final int FLAG_MATCH_CACHED = 8;
    public static final int FLAG_MATCH_DYNAMIC = 2;
    public static final int FLAG_MATCH_MANIFEST = 1;
    public static final int FLAG_MATCH_PINNED = 4;

    @VisibleForTesting
    static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
    private static final String SHORTCUT_LISTENER_INTENT_FILTER_ACTION = "androidx.core.content.pm.SHORTCUT_LISTENER";
    private static final String SHORTCUT_LISTENER_META_DATA_KEY = "androidx.core.content.pm.shortcut_listener_impl";
    private static volatile List<ShortcutInfoChangeListener> sShortcutInfoChangeListeners;
    private static volatile ShortcutInfoCompatSaver<?> sShortcutInfoCompatSaver;

    @RequiresApi(25)
    public static class Api25Impl {
        private Api25Impl() {
        }

        public static String getShortcutInfoWithLowestRank(@NonNull List<ShortcutInfo> list) {
            int rank = -1;
            String id = null;
            for (ShortcutInfo shortcutInfo : list) {
                if (shortcutInfo.getRank() > rank) {
                    id = shortcutInfo.getId();
                    rank = shortcutInfo.getRank();
                }
            }
            return id;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface ShortcutMatchFlags {
    }

    private ShortcutManagerCompat() {
    }

    public static boolean addDynamicShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        List<ShortcutInfoCompat> listRemoveShortcutsExcludedFromSurface = removeShortcutsExcludedFromSurface(list, 1);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 <= 29) {
            convertUriIconsToBitmapIcons(context, listRemoveShortcutsExcludedFromSurface);
        }
        if (i2 >= 25) {
            ArrayList arrayList = new ArrayList();
            Iterator<ShortcutInfoCompat> it = listRemoveShortcutsExcludedFromSurface.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toShortcutInfo());
            }
            if (!c.b(context.getSystemService(c.d())).addDynamicShortcuts(arrayList)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(listRemoveShortcutsExcludedFromSurface);
        Iterator<ShortcutInfoChangeListener> it2 = getShortcutInfoListeners(context).iterator();
        while (it2.hasNext()) {
            it2.next().onShortcutAdded(list);
        }
        return true;
    }

    @VisibleForTesting
    public static boolean convertUriIconToBitmapIcon(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Bitmap bitmapDecodeStream;
        IconCompat iconCompat = shortcutInfoCompat.mIcon;
        if (iconCompat == null) {
            return false;
        }
        int i2 = iconCompat.mType;
        if (i2 != 6 && i2 != 4) {
            return true;
        }
        InputStream uriInputStream = iconCompat.getUriInputStream(context);
        if (uriInputStream == null || (bitmapDecodeStream = BitmapFactory.decodeStream(uriInputStream)) == null) {
            return false;
        }
        shortcutInfoCompat.mIcon = i2 == 6 ? IconCompat.createWithAdaptiveBitmap(bitmapDecodeStream) : IconCompat.createWithBitmap(bitmapDecodeStream);
        return true;
    }

    @VisibleForTesting
    public static void convertUriIconsToBitmapIcons(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        for (ShortcutInfoCompat shortcutInfoCompat : new ArrayList(list)) {
            if (!convertUriIconToBitmapIcon(context, shortcutInfoCompat)) {
                list.remove(shortcutInfoCompat);
            }
        }
    }

    @NonNull
    public static Intent createShortcutResultIntent(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Intent intentCreateShortcutResultIntent = Build.VERSION.SDK_INT >= 26 ? c.b(context.getSystemService(c.d())).createShortcutResultIntent(shortcutInfoCompat.toShortcutInfo()) : null;
        if (intentCreateShortcutResultIntent == null) {
            intentCreateShortcutResultIntent = new Intent();
        }
        return shortcutInfoCompat.addToIntent(intentCreateShortcutResultIntent);
    }

    public static void disableShortcuts(@NonNull Context context, @NonNull List<String> list, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 25) {
            c.b(context.getSystemService(c.d())).disableShortcuts(list, charSequence);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
        Iterator<ShortcutInfoChangeListener> it = getShortcutInfoListeners(context).iterator();
        while (it.hasNext()) {
            it.next().onShortcutRemoved(list);
        }
    }

    public static void enableShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        List<ShortcutInfoCompat> listRemoveShortcutsExcludedFromSurface = removeShortcutsExcludedFromSurface(list, 1);
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<ShortcutInfoCompat> it = listRemoveShortcutsExcludedFromSurface.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().mId);
            }
            c.b(context.getSystemService(c.d())).enableShortcuts(arrayList);
        }
        getShortcutInfoSaverInstance(context).addShortcuts(listRemoveShortcutsExcludedFromSurface);
        Iterator<ShortcutInfoChangeListener> it2 = getShortcutInfoListeners(context).iterator();
        while (it2.hasNext()) {
            it2.next().onShortcutAdded(list);
        }
    }

    @NonNull
    public static List<ShortcutInfoCompat> getDynamicShortcuts(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 25) {
            try {
                return getShortcutInfoSaverInstance(context).getShortcuts();
            } catch (Exception unused) {
                return new ArrayList();
            }
        }
        List dynamicShortcuts = c.b(context.getSystemService(c.d())).getDynamicShortcuts();
        ArrayList arrayList = new ArrayList(dynamicShortcuts.size());
        Iterator it = dynamicShortcuts.iterator();
        while (it.hasNext()) {
            arrayList.add(new ShortcutInfoCompat.Builder(context, a.h(it.next())).build());
        }
        return arrayList;
    }

    private static int getIconDimensionInternal(@NonNull Context context, boolean z2) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        int iMax = Math.max(1, activityManager == null || activityManager.isLowRamDevice() ? 48 : DEFAULT_MAX_ICON_DIMENSION_DP);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (iMax * ((z2 ? displayMetrics.xdpi : displayMetrics.ydpi) / 160.0f));
    }

    public static int getIconMaxHeight(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        return Build.VERSION.SDK_INT >= 25 ? c.b(context.getSystemService(c.d())).getIconMaxHeight() : getIconDimensionInternal(context, false);
    }

    public static int getIconMaxWidth(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        return Build.VERSION.SDK_INT >= 25 ? c.b(context.getSystemService(c.d())).getIconMaxWidth() : getIconDimensionInternal(context, true);
    }

    public static int getMaxShortcutCountPerActivity(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return c.b(context.getSystemService(c.d())).getMaxShortcutCountPerActivity();
        }
        return 5;
    }

    @VisibleForTesting
    public static List<ShortcutInfoChangeListener> getShortcutInfoChangeListeners() {
        return sShortcutInfoChangeListeners;
    }

    private static String getShortcutInfoCompatWithLowestRank(@NonNull List<ShortcutInfoCompat> list) {
        int rank = -1;
        String id = null;
        for (ShortcutInfoCompat shortcutInfoCompat : list) {
            if (shortcutInfoCompat.getRank() > rank) {
                id = shortcutInfoCompat.getId();
                rank = shortcutInfoCompat.getRank();
            }
        }
        return id;
    }

    private static List<ShortcutInfoChangeListener> getShortcutInfoListeners(Context context) {
        Bundle bundle;
        String string;
        if (sShortcutInfoChangeListeners == null) {
            ArrayList arrayList = new ArrayList();
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(SHORTCUT_LISTENER_INTENT_FILTER_ACTION);
            intent.setPackage(context.getPackageName());
            Iterator<ResolveInfo> it = packageManager.queryIntentActivities(intent, 128).iterator();
            while (it.hasNext()) {
                ActivityInfo activityInfo = it.next().activityInfo;
                if (activityInfo != null && (bundle = activityInfo.metaData) != null && (string = bundle.getString(SHORTCUT_LISTENER_META_DATA_KEY)) != null) {
                    try {
                        arrayList.add((ShortcutInfoChangeListener) Class.forName(string, false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", Context.class).invoke(null, context));
                    } catch (Exception unused) {
                    }
                }
            }
            if (sShortcutInfoChangeListeners == null) {
                sShortcutInfoChangeListeners = arrayList;
            }
        }
        return sShortcutInfoChangeListeners;
    }

    private static ShortcutInfoCompatSaver<?> getShortcutInfoSaverInstance(Context context) {
        if (sShortcutInfoCompatSaver == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    sShortcutInfoCompatSaver = (ShortcutInfoCompatSaver) Class.forName("androidx.sharetarget.ShortcutInfoCompatSaverImpl", false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", Context.class).invoke(null, context);
                } catch (Exception unused) {
                }
            }
            if (sShortcutInfoCompatSaver == null) {
                sShortcutInfoCompatSaver = new ShortcutInfoCompatSaver.NoopImpl();
            }
        }
        return sShortcutInfoCompatSaver;
    }

    @NonNull
    public static List<ShortcutInfoCompat> getShortcuts(@NonNull Context context, int i2) {
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 30) {
            return ShortcutInfoCompat.fromShortcuts(context, c.b(context.getSystemService(c.d())).getShortcuts(i2));
        }
        if (i3 < 25) {
            if ((i2 & 2) != 0) {
                try {
                    return getShortcutInfoSaverInstance(context).getShortcuts();
                } catch (Exception unused) {
                }
            }
            return Collections.emptyList();
        }
        ShortcutManager shortcutManagerB = c.b(context.getSystemService(c.d()));
        ArrayList arrayList = new ArrayList();
        if ((i2 & 1) != 0) {
            arrayList.addAll(shortcutManagerB.getManifestShortcuts());
        }
        if ((i2 & 2) != 0) {
            arrayList.addAll(shortcutManagerB.getDynamicShortcuts());
        }
        if ((i2 & 4) != 0) {
            arrayList.addAll(shortcutManagerB.getPinnedShortcuts());
        }
        return ShortcutInfoCompat.fromShortcuts(context, arrayList);
    }

    public static boolean isRateLimitingActive(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return c.b(context.getSystemService(c.d())).isRateLimitingActive();
        }
        return getShortcuts(context, 3).size() == getMaxShortcutCountPerActivity(context);
    }

    public static boolean isRequestPinShortcutSupported(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            return c.b(context.getSystemService(c.d())).isRequestPinShortcutSupported();
        }
        if (ContextCompat.checkSelfPermission(context, INSTALL_SHORTCUT_PERMISSION) != 0) {
            return false;
        }
        Iterator<ResolveInfo> it = context.getPackageManager().queryBroadcastReceivers(new Intent(ACTION_INSTALL_SHORTCUT), 0).iterator();
        while (it.hasNext()) {
            String str = it.next().activityInfo.permission;
            if (TextUtils.isEmpty(str) || INSTALL_SHORTCUT_PERMISSION.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean pushDynamicShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(shortcutInfoCompat);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 <= 32 && shortcutInfoCompat.isExcludedFromSurfaces(1)) {
            Iterator<ShortcutInfoChangeListener> it = getShortcutInfoListeners(context).iterator();
            while (it.hasNext()) {
                it.next().onShortcutAdded(Collections.singletonList(shortcutInfoCompat));
            }
            return true;
        }
        int maxShortcutCountPerActivity = getMaxShortcutCountPerActivity(context);
        if (maxShortcutCountPerActivity == 0) {
            return false;
        }
        if (i2 <= 29) {
            convertUriIconToBitmapIcon(context, shortcutInfoCompat);
        }
        if (i2 >= 30) {
            c.b(context.getSystemService(c.d())).pushDynamicShortcut(shortcutInfoCompat.toShortcutInfo());
        } else if (i2 >= 25) {
            ShortcutManager shortcutManagerB = c.b(context.getSystemService(c.d()));
            if (shortcutManagerB.isRateLimitingActive()) {
                return false;
            }
            List dynamicShortcuts = shortcutManagerB.getDynamicShortcuts();
            if (dynamicShortcuts.size() >= maxShortcutCountPerActivity) {
                shortcutManagerB.removeDynamicShortcuts(Arrays.asList(Api25Impl.getShortcutInfoWithLowestRank(dynamicShortcuts)));
            }
            shortcutManagerB.addDynamicShortcuts(Arrays.asList(shortcutInfoCompat.toShortcutInfo()));
        }
        ShortcutInfoCompatSaver<?> shortcutInfoSaverInstance = getShortcutInfoSaverInstance(context);
        try {
            List<ShortcutInfoCompat> shortcuts = shortcutInfoSaverInstance.getShortcuts();
            if (shortcuts.size() >= maxShortcutCountPerActivity) {
                shortcutInfoSaverInstance.removeShortcuts(Arrays.asList(getShortcutInfoCompatWithLowestRank(shortcuts)));
            }
            shortcutInfoSaverInstance.addShortcuts(Arrays.asList(shortcutInfoCompat));
            return true;
        } catch (Exception unused) {
            return false;
        } finally {
            Iterator<ShortcutInfoChangeListener> it2 = getShortcutInfoListeners(context).iterator();
            while (it2.hasNext()) {
                it2.next().onShortcutAdded(Collections.singletonList(shortcutInfoCompat));
            }
            reportShortcutUsed(context, shortcutInfoCompat.getId());
        }
    }

    public static void removeAllDynamicShortcuts(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            c.b(context.getSystemService(c.d())).removeAllDynamicShortcuts();
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
        Iterator<ShortcutInfoChangeListener> it = getShortcutInfoListeners(context).iterator();
        while (it.hasNext()) {
            it.next().onAllShortcutsRemoved();
        }
    }

    public static void removeDynamicShortcuts(@NonNull Context context, @NonNull List<String> list) {
        if (Build.VERSION.SDK_INT >= 25) {
            c.b(context.getSystemService(c.d())).removeDynamicShortcuts(list);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
        Iterator<ShortcutInfoChangeListener> it = getShortcutInfoListeners(context).iterator();
        while (it.hasNext()) {
            it.next().onShortcutRemoved(list);
        }
    }

    public static void removeLongLivedShortcuts(@NonNull Context context, @NonNull List<String> list) {
        if (Build.VERSION.SDK_INT < 30) {
            removeDynamicShortcuts(context, list);
            return;
        }
        c.b(context.getSystemService(c.d())).removeLongLivedShortcuts(list);
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
        Iterator<ShortcutInfoChangeListener> it = getShortcutInfoListeners(context).iterator();
        while (it.hasNext()) {
            it.next().onShortcutRemoved(list);
        }
    }

    @NonNull
    private static List<ShortcutInfoCompat> removeShortcutsExcludedFromSurface(@NonNull List<ShortcutInfoCompat> list, int i2) {
        Objects.requireNonNull(list);
        if (Build.VERSION.SDK_INT > 32) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list);
        for (ShortcutInfoCompat shortcutInfoCompat : list) {
            if (shortcutInfoCompat.isExcludedFromSurfaces(i2)) {
                arrayList.remove(shortcutInfoCompat);
            }
        }
        return arrayList;
    }

    public static void reportShortcutUsed(@NonNull Context context, @NonNull String str) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(str);
        if (Build.VERSION.SDK_INT >= 25) {
            c.b(context.getSystemService(c.d())).reportShortcutUsed(str);
        }
        Iterator<ShortcutInfoChangeListener> it = getShortcutInfoListeners(context).iterator();
        while (it.hasNext()) {
            it.next().onShortcutUsageReported(Collections.singletonList(str));
        }
    }

    public static boolean requestPinShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat, @Nullable final IntentSender intentSender) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 <= 32 && shortcutInfoCompat.isExcludedFromSurfaces(1)) {
            return false;
        }
        if (i2 >= 26) {
            return c.b(context.getSystemService(c.d())).requestPinShortcut(shortcutInfoCompat.toShortcutInfo(), intentSender);
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false;
        }
        Intent intentAddToIntent = shortcutInfoCompat.addToIntent(new Intent(ACTION_INSTALL_SHORTCUT));
        if (intentSender == null) {
            context.sendBroadcast(intentAddToIntent);
            return true;
        }
        context.sendOrderedBroadcast(intentAddToIntent, null, new BroadcastReceiver() { // from class: androidx.core.content.pm.ShortcutManagerCompat.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                try {
                    intentSender.sendIntent(context2, 0, null, null, null);
                } catch (IntentSender.SendIntentException unused) {
                }
            }
        }, null, -1, null, null);
        return true;
    }

    public static boolean setDynamicShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(list);
        List<ShortcutInfoCompat> listRemoveShortcutsExcludedFromSurface = removeShortcutsExcludedFromSurface(list, 1);
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList(listRemoveShortcutsExcludedFromSurface.size());
            Iterator<ShortcutInfoCompat> it = listRemoveShortcutsExcludedFromSurface.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toShortcutInfo());
            }
            if (!c.b(context.getSystemService(c.d())).setDynamicShortcuts(arrayList)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
        getShortcutInfoSaverInstance(context).addShortcuts(listRemoveShortcutsExcludedFromSurface);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : getShortcutInfoListeners(context)) {
            shortcutInfoChangeListener.onAllShortcutsRemoved();
            shortcutInfoChangeListener.onShortcutAdded(list);
        }
        return true;
    }

    @VisibleForTesting
    public static void setShortcutInfoChangeListeners(List<ShortcutInfoChangeListener> list) {
        sShortcutInfoChangeListeners = list;
    }

    @VisibleForTesting
    public static void setShortcutInfoCompatSaver(ShortcutInfoCompatSaver<Void> shortcutInfoCompatSaver) {
        sShortcutInfoCompatSaver = shortcutInfoCompatSaver;
    }

    public static boolean updateShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        List<ShortcutInfoCompat> listRemoveShortcutsExcludedFromSurface = removeShortcutsExcludedFromSurface(list, 1);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 <= 29) {
            convertUriIconsToBitmapIcons(context, listRemoveShortcutsExcludedFromSurface);
        }
        if (i2 >= 25) {
            ArrayList arrayList = new ArrayList();
            Iterator<ShortcutInfoCompat> it = listRemoveShortcutsExcludedFromSurface.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toShortcutInfo());
            }
            if (!c.b(context.getSystemService(c.d())).updateShortcuts(arrayList)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(listRemoveShortcutsExcludedFromSurface);
        Iterator<ShortcutInfoChangeListener> it2 = getShortcutInfoListeners(context).iterator();
        while (it2.hasNext()) {
            it2.next().onShortcutUpdated(list);
        }
        return true;
    }
}
