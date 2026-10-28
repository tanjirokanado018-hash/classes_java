package com.google.android.material.internal;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.lang.reflect.Constructor;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
final class StaticLayoutBuilderCompat {
    static final int DEFAULT_HYPHENATION_FREQUENCY;
    static final float DEFAULT_LINE_SPACING_ADD = 0.0f;
    static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1.0f;
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_CLASS_LTR = "LTR";
    private static final String TEXT_DIR_CLASS_RTL = "RTL";

    @Nullable
    private static Constructor<StaticLayout> constructor;
    private static boolean initialized;

    @Nullable
    private static Object textDirection;
    private int end;
    private boolean isRtl;
    private final TextPaint paint;
    private CharSequence source;

    @Nullable
    private StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer;
    private final int width;
    private int start = 0;
    private Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
    private int maxLines = Integer.MAX_VALUE;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private int hyphenationFrequency = DEFAULT_HYPHENATION_FREQUENCY;
    private boolean includePad = true;

    @Nullable
    private TextUtils.TruncateAt ellipsize = null;

    public static class StaticLayoutBuilderCompatException extends Exception {
        public StaticLayoutBuilderCompatException(Throwable th) {
            super("Error thrown initializing StaticLayout " + th.getMessage(), th);
        }
    }

    static {
        DEFAULT_HYPHENATION_FREQUENCY = Build.VERSION.SDK_INT >= 23 ? 1 : 0;
    }

    private StaticLayoutBuilderCompat(CharSequence charSequence, TextPaint textPaint, int i2) {
        this.source = charSequence;
        this.paint = textPaint;
        this.width = i2;
        this.end = charSequence.length();
    }

    private void createConstructorWithReflection() throws StaticLayoutBuilderCompatException {
        if (initialized) {
            return;
        }
        try {
            textDirection = this.isRtl && Build.VERSION.SDK_INT >= 23 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
            Class cls = Integer.TYPE;
            Class cls2 = Float.TYPE;
            Constructor<StaticLayout> declaredConstructor = StaticLayout.class.getDeclaredConstructor(CharSequence.class, cls, cls, TextPaint.class, cls, Layout.Alignment.class, TextDirectionHeuristic.class, cls2, cls2, Boolean.TYPE, TextUtils.TruncateAt.class, cls, cls);
            constructor = declaredConstructor;
            declaredConstructor.setAccessible(true);
            initialized = true;
        } catch (Exception e2) {
            throw new StaticLayoutBuilderCompatException(e2);
        }
    }

    @NonNull
    public static StaticLayoutBuilderCompat obtain(@NonNull CharSequence charSequence, @NonNull TextPaint textPaint, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) int i2) {
        return new StaticLayoutBuilderCompat(charSequence, textPaint, i2);
    }

    public StaticLayout build() throws StaticLayoutBuilderCompatException {
        if (this.source == null) {
            this.source = "";
        }
        int iMax = Math.max(0, this.width);
        CharSequence charSequenceEllipsize = this.source;
        if (this.maxLines == 1) {
            charSequenceEllipsize = TextUtils.ellipsize(charSequenceEllipsize, this.paint, iMax, this.ellipsize);
        }
        int iMin = Math.min(charSequenceEllipsize.length(), this.end);
        this.end = iMin;
        if (Build.VERSION.SDK_INT < 23) {
            createConstructorWithReflection();
            try {
                return (StaticLayout) ((Constructor) Preconditions.checkNotNull(constructor)).newInstance(charSequenceEllipsize, Integer.valueOf(this.start), Integer.valueOf(this.end), this.paint, Integer.valueOf(iMax), this.alignment, Preconditions.checkNotNull(textDirection), Float.valueOf(1.0f), Float.valueOf(0.0f), Boolean.valueOf(this.includePad), null, Integer.valueOf(iMax), Integer.valueOf(this.maxLines));
            } catch (Exception e2) {
                throw new StaticLayoutBuilderCompatException(e2);
            }
        }
        if (this.isRtl && this.maxLines == 1) {
            this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
        }
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(charSequenceEllipsize, this.start, iMin, this.paint, iMax);
        builderObtain.setAlignment(this.alignment);
        builderObtain.setIncludePad(this.includePad);
        builderObtain.setTextDirection(this.isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
        TextUtils.TruncateAt truncateAt = this.ellipsize;
        if (truncateAt != null) {
            builderObtain.setEllipsize(truncateAt);
        }
        builderObtain.setMaxLines(this.maxLines);
        float f2 = this.lineSpacingAdd;
        if (f2 != 0.0f || this.lineSpacingMultiplier != 1.0f) {
            builderObtain.setLineSpacing(f2, this.lineSpacingMultiplier);
        }
        if (this.maxLines > 1) {
            builderObtain.setHyphenationFrequency(this.hyphenationFrequency);
        }
        StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer = this.staticLayoutBuilderConfigurer;
        if (staticLayoutBuilderConfigurer != null) {
            staticLayoutBuilderConfigurer.configure(builderObtain);
        }
        return builderObtain.build();
    }

    @NonNull
    public StaticLayoutBuilderCompat setAlignment(@NonNull Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setEllipsize(@Nullable TextUtils.TruncateAt truncateAt) {
        this.ellipsize = truncateAt;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setEnd(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) int i2) {
        this.end = i2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setHyphenationFrequency(int i2) {
        this.hyphenationFrequency = i2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setIncludePad(boolean z2) {
        this.includePad = z2;
        return this;
    }

    public StaticLayoutBuilderCompat setIsRtl(boolean z2) {
        this.isRtl = z2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setLineSpacing(float f2, float f3) {
        this.lineSpacingAdd = f2;
        this.lineSpacingMultiplier = f3;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setMaxLines(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) int i2) {
        this.maxLines = i2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setStart(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) int i2) {
        this.start = i2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setStaticLayoutBuilderConfigurer(@Nullable StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer) {
        this.staticLayoutBuilderConfigurer = staticLayoutBuilderConfigurer;
        return this;
    }
}
