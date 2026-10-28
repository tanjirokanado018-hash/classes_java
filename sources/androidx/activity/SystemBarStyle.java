package androidx.activity;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.k;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SystemBarStyle {
    public static final Companion Companion = new Companion(null);
    private final int darkScrim;
    private final l detectDarkMode;
    private final int lightScrim;
    private final int nightMode;

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(kotlin.jvm.internal.f fVar) {
            this();
        }

        public static /* synthetic */ SystemBarStyle auto$default(Companion companion, int i2, int i3, l lVar, int i4, Object obj) {
            if ((i4 & 4) != 0) {
                lVar = SystemBarStyle$Companion$auto$1.INSTANCE;
            }
            return companion.auto(i2, i3, lVar);
        }

        public final SystemBarStyle auto(@ColorInt int i2, @ColorInt int i3) {
            return auto$default(this, i2, i3, null, 4, null);
        }

        public final SystemBarStyle dark(@ColorInt int i2) {
            return new SystemBarStyle(i2, i2, 2, SystemBarStyle$Companion$dark$1.INSTANCE, null);
        }

        public final SystemBarStyle light(@ColorInt int i2, @ColorInt int i3) {
            return new SystemBarStyle(i2, i3, 1, SystemBarStyle$Companion$light$1.INSTANCE, null);
        }

        public final SystemBarStyle auto(@ColorInt int i2, @ColorInt int i3, l detectDarkMode) {
            k.g(detectDarkMode, "detectDarkMode");
            return new SystemBarStyle(i2, i3, 0, detectDarkMode, null);
        }
    }

    private SystemBarStyle(int i2, int i3, int i4, l lVar) {
        this.lightScrim = i2;
        this.darkScrim = i3;
        this.nightMode = i4;
        this.detectDarkMode = lVar;
    }

    public static final SystemBarStyle auto(@ColorInt int i2, @ColorInt int i3) {
        return Companion.auto(i2, i3);
    }

    public static final SystemBarStyle dark(@ColorInt int i2) {
        return Companion.dark(i2);
    }

    public static final SystemBarStyle light(@ColorInt int i2, @ColorInt int i3) {
        return Companion.light(i2, i3);
    }

    public final int getDarkScrim$activity_release() {
        return this.darkScrim;
    }

    public final l getDetectDarkMode$activity_release() {
        return this.detectDarkMode;
    }

    public final int getNightMode$activity_release() {
        return this.nightMode;
    }

    public final int getScrim$activity_release(boolean z2) {
        return z2 ? this.darkScrim : this.lightScrim;
    }

    public final int getScrimWithEnforcedContrast$activity_release(boolean z2) {
        if (this.nightMode == 0) {
            return 0;
        }
        return z2 ? this.darkScrim : this.lightScrim;
    }

    public /* synthetic */ SystemBarStyle(int i2, int i3, int i4, l lVar, kotlin.jvm.internal.f fVar) {
        this(i2, i3, i4, lVar);
    }

    public static final SystemBarStyle auto(@ColorInt int i2, @ColorInt int i3, l lVar) {
        return Companion.auto(i2, i3, lVar);
    }
}
