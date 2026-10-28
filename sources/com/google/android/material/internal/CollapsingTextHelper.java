package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.color.utilities.Contrast;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TypefaceUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final String ELLIPSIS_NORMAL = "…";
    private static final float FADE_MODE_THRESHOLD_FRACTION_RELATIVE = 0.5f;
    private static final String TAG = "CollapsingTextHelper";
    private boolean boundsChanged;

    @NonNull
    private final Rect collapsedBounds;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private CancelableFontCallback collapsedFontCallback;
    private float collapsedLetterSpacing;
    private ColorStateList collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private float collapsedTextBlend;
    private ColorStateList collapsedTextColor;
    private float collapsedTextWidth;
    private Typeface collapsedTypeface;
    private Typeface collapsedTypefaceBold;
    private Typeface collapsedTypefaceDefault;

    @NonNull
    private final RectF currentBounds;
    private float currentDrawX;
    private float currentDrawY;
    private float currentLetterSpacing;
    private int currentOffsetY;
    private int currentShadowColor;
    private float currentShadowDx;
    private float currentShadowDy;
    private float currentShadowRadius;
    private float currentTextSize;
    private Typeface currentTypeface;

    @NonNull
    private final Rect expandedBounds;
    private float expandedDrawX;
    private float expandedDrawY;
    private CancelableFontCallback expandedFontCallback;
    private float expandedFraction;
    private float expandedLetterSpacing;
    private int expandedLineCount;
    private ColorStateList expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private float expandedTextBlend;
    private ColorStateList expandedTextColor;

    @Nullable
    private Bitmap expandedTitleTexture;
    private Typeface expandedTypeface;
    private Typeface expandedTypefaceBold;
    private Typeface expandedTypefaceDefault;
    private boolean fadeModeEnabled;
    private float fadeModeStartFraction;
    private float fadeModeThresholdFraction;
    private boolean isRtl;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;

    @Nullable
    private StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer;

    @Nullable
    private CharSequence text;
    private StaticLayout textLayout;

    @NonNull
    private final TextPaint textPaint;
    private TimeInterpolator textSizeInterpolator;

    @Nullable
    private CharSequence textToDraw;
    private CharSequence textToDrawCollapsed;
    private Paint texturePaint;

    @NonNull
    private final TextPaint tmpPaint;
    private boolean useTexture;
    private final View view;
    private static final boolean USE_SCALING_TEXTURE = false;

    @NonNull
    private static final Paint DEBUG_DRAW_PAINT = null;
    private int expandedTextGravity = 16;
    private int collapsedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private float collapsedTextSize = 15.0f;
    private TextUtils.TruncateAt titleTextEllipsize = TextUtils.TruncateAt.END;
    private boolean isRtlTextDirectionHeuristicsEnabled = true;
    private int maxLines = 1;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private int hyphenationFrequency = StaticLayoutBuilderCompat.DEFAULT_HYPHENATION_FREQUENCY;

    public CollapsingTextHelper(View view) {
        this.view = view;
        TextPaint textPaint = new TextPaint(129);
        this.textPaint = textPaint;
        this.tmpPaint = new TextPaint(textPaint);
        this.collapsedBounds = new Rect();
        this.expandedBounds = new Rect();
        this.currentBounds = new RectF();
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
        maybeUpdateFontWeightAdjustment(view.getContext().getResources().getConfiguration());
    }

    @ColorInt
    private static int blendARGB(@ColorInt int i2, @ColorInt int i3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2) {
        float f3 = 1.0f - f2;
        return Color.argb(Math.round((Color.alpha(i3) * f2) + (Color.alpha(i2) * f3)), Math.round((Color.red(i3) * f2) + (Color.red(i2) * f3)), Math.round((Color.green(i3) * f2) + (Color.green(i2) * f3)), Math.round((Color.blue(i3) * f2) + (Color.blue(i2) * f3)));
    }

    /* JADX WARN: Code duplicated, block: B:28:0x009a  */
    /* JADX WARN: Code duplicated, block: B:29:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:35:0x00af  */
    /* JADX WARN: Code duplicated, block: B:37:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:40:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:41:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:44:0x00d1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:45:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:46:0x00df  */
    /* JADX WARN: Code duplicated, block: B:48:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:51:0x00f9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:52:0x00fb  */
    /* JADX WARN: Code duplicated, block: B:54:0x0103  */
    /* JADX WARN: Code duplicated, block: B:56:0x010a  */
    private void calculateBaseOffsets(boolean z2) {
        float fCenterX;
        float f2;
        float f3;
        StaticLayout staticLayout;
        float height;
        StaticLayout staticLayout2;
        CharSequence charSequence;
        StaticLayout staticLayout3;
        int lineCount;
        int i2;
        float fDescent;
        int i3;
        float fCenterX2;
        float f4;
        StaticLayout staticLayout4;
        calculateUsingTextSize(1.0f, z2);
        CharSequence charSequence2 = this.textToDraw;
        if (charSequence2 != null && (staticLayout4 = this.textLayout) != null) {
            this.textToDrawCollapsed = TextUtils.ellipsize(charSequence2, this.textPaint, staticLayout4.getWidth(), this.titleTextEllipsize);
        }
        CharSequence charSequence3 = this.textToDrawCollapsed;
        float fMeasureTextWidth = 0.0f;
        if (charSequence3 != null) {
            this.collapsedTextWidth = measureTextWidth(this.textPaint, charSequence3);
        } else {
            this.collapsedTextWidth = 0.0f;
        }
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl ? 1 : 0);
        int i4 = absoluteGravity & 112;
        if (i4 != 48) {
            this.collapsedDrawY = i4 != 80 ? this.collapsedBounds.centerY() - ((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f) : this.textPaint.ascent() + this.collapsedBounds.bottom;
        } else {
            this.collapsedDrawY = this.collapsedBounds.top;
        }
        int i5 = absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i5 != 1) {
            if (i5 != 5) {
                f3 = this.collapsedBounds.left;
            } else {
                fCenterX = this.collapsedBounds.right;
                f2 = this.collapsedTextWidth;
            }
            this.collapsedDrawX = f3;
            calculateUsingTextSize(0.0f, z2);
            staticLayout = this.textLayout;
            if (staticLayout != null) {
                height = staticLayout.getHeight();
            } else {
                height = 0.0f;
            }
            staticLayout2 = this.textLayout;
            if (staticLayout2 != null || this.maxLines <= 1) {
                charSequence = this.textToDraw;
                if (charSequence != null) {
                    fMeasureTextWidth = measureTextWidth(this.textPaint, charSequence);
                }
            } else {
                fMeasureTextWidth = staticLayout2.getWidth();
            }
            staticLayout3 = this.textLayout;
            if (staticLayout3 != null) {
                lineCount = staticLayout3.getLineCount();
            } else {
                lineCount = 0;
            }
            this.expandedLineCount = lineCount;
            int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
            i2 = absoluteGravity2 & 112;
            if (i2 != 48) {
                if (i2 != 80) {
                    this.expandedDrawY = this.expandedBounds.centerY() - (height / 2.0f);
                } else {
                    fDescent = this.textPaint.descent() + (this.expandedBounds.bottom - height);
                }
                i3 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
                if (i3 != 1) {
                    if (i3 != 5) {
                        f4 = this.expandedBounds.left;
                    } else {
                        fCenterX2 = this.expandedBounds.right;
                    }
                    this.expandedDrawX = f4;
                    clearTexture();
                    setInterpolatedTextSize(this.expandedFraction);
                }
                fCenterX2 = this.expandedBounds.centerX();
                fMeasureTextWidth /= 2.0f;
                f4 = fCenterX2 - fMeasureTextWidth;
                this.expandedDrawX = f4;
                clearTexture();
                setInterpolatedTextSize(this.expandedFraction);
            }
            fDescent = this.expandedBounds.top;
            this.expandedDrawY = fDescent;
            i3 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
            if (i3 != 1) {
                if (i3 != 5) {
                    f4 = this.expandedBounds.left;
                } else {
                    fCenterX2 = this.expandedBounds.right;
                }
                this.expandedDrawX = f4;
                clearTexture();
                setInterpolatedTextSize(this.expandedFraction);
            }
            fCenterX2 = this.expandedBounds.centerX();
            fMeasureTextWidth /= 2.0f;
            f4 = fCenterX2 - fMeasureTextWidth;
            this.expandedDrawX = f4;
            clearTexture();
            setInterpolatedTextSize(this.expandedFraction);
        }
        fCenterX = this.collapsedBounds.centerX();
        f2 = this.collapsedTextWidth / 2.0f;
        f3 = fCenterX - f2;
        this.collapsedDrawX = f3;
        calculateUsingTextSize(0.0f, z2);
        staticLayout = this.textLayout;
        if (staticLayout != null) {
            height = staticLayout.getHeight();
        } else {
            height = 0.0f;
        }
        staticLayout2 = this.textLayout;
        if (staticLayout2 != null) {
            charSequence = this.textToDraw;
            if (charSequence != null) {
                fMeasureTextWidth = measureTextWidth(this.textPaint, charSequence);
            }
        } else {
            charSequence = this.textToDraw;
            if (charSequence != null) {
                fMeasureTextWidth = measureTextWidth(this.textPaint, charSequence);
            }
        }
        staticLayout3 = this.textLayout;
        if (staticLayout3 != null) {
            lineCount = staticLayout3.getLineCount();
        } else {
            lineCount = 0;
        }
        this.expandedLineCount = lineCount;
        int absoluteGravity3 = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
        i2 = absoluteGravity3 & 112;
        if (i2 != 48) {
            if (i2 != 80) {
                this.expandedDrawY = this.expandedBounds.centerY() - (height / 2.0f);
            } else {
                fDescent = this.textPaint.descent() + (this.expandedBounds.bottom - height);
            }
            i3 = absoluteGravity3 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
            if (i3 != 1) {
                if (i3 != 5) {
                    f4 = this.expandedBounds.left;
                } else {
                    fCenterX2 = this.expandedBounds.right;
                }
                this.expandedDrawX = f4;
                clearTexture();
                setInterpolatedTextSize(this.expandedFraction);
            }
            fCenterX2 = this.expandedBounds.centerX();
            fMeasureTextWidth /= 2.0f;
            f4 = fCenterX2 - fMeasureTextWidth;
            this.expandedDrawX = f4;
            clearTexture();
            setInterpolatedTextSize(this.expandedFraction);
        }
        fDescent = this.expandedBounds.top;
        this.expandedDrawY = fDescent;
        i3 = absoluteGravity3 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i3 != 1) {
            if (i3 != 5) {
                f4 = this.expandedBounds.left;
            } else {
                fCenterX2 = this.expandedBounds.right;
            }
            this.expandedDrawX = f4;
            clearTexture();
            setInterpolatedTextSize(this.expandedFraction);
        }
        fCenterX2 = this.expandedBounds.centerX();
        fMeasureTextWidth /= 2.0f;
        f4 = fCenterX2 - fMeasureTextWidth;
        this.expandedDrawX = f4;
        clearTexture();
        setInterpolatedTextSize(this.expandedFraction);
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.expandedFraction);
    }

    private float calculateFadeModeTextAlpha(@FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2) {
        float f3 = this.fadeModeThresholdFraction;
        return f2 <= f3 ? AnimationUtils.lerp(1.0f, 0.0f, this.fadeModeStartFraction, f3, f2) : AnimationUtils.lerp(0.0f, 1.0f, f3, 1.0f, f2);
    }

    private float calculateFadeModeThresholdFraction() {
        float f2 = this.fadeModeStartFraction;
        return androidx.appcompat.graphics.drawable.a.a(1.0f, f2, 0.5f, f2);
    }

    private boolean calculateIsRtl(@NonNull CharSequence charSequence) {
        boolean zIsDefaultIsRtl = isDefaultIsRtl();
        return this.isRtlTextDirectionHeuristicsEnabled ? isTextDirectionHeuristicsIsRtl(charSequence, zIsDefaultIsRtl) : zIsDefaultIsRtl;
    }

    private void calculateOffsets(float f2) {
        float f3;
        interpolateBounds(f2);
        if (!this.fadeModeEnabled) {
            this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, f2, this.positionInterpolator);
            this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, f2, this.positionInterpolator);
            setInterpolatedTextSize(f2);
            f3 = f2;
        } else if (f2 < this.fadeModeThresholdFraction) {
            this.currentDrawX = this.expandedDrawX;
            this.currentDrawY = this.expandedDrawY;
            setInterpolatedTextSize(0.0f);
            f3 = 0.0f;
        } else {
            this.currentDrawX = this.collapsedDrawX;
            this.currentDrawY = this.collapsedDrawY - Math.max(0, this.currentOffsetY);
            setInterpolatedTextSize(1.0f);
            f3 = 1.0f;
        }
        TimeInterpolator timeInterpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        setCollapsedTextBlend(1.0f - lerp(0.0f, 1.0f, 1.0f - f2, timeInterpolator));
        setExpandedTextBlend(lerp(1.0f, 0.0f, f2, timeInterpolator));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(blendARGB(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), f3));
        } else {
            this.textPaint.setColor(getCurrentCollapsedTextColor());
        }
        int i2 = Build.VERSION.SDK_INT;
        float f4 = this.collapsedLetterSpacing;
        float f5 = this.expandedLetterSpacing;
        if (f4 != f5) {
            this.textPaint.setLetterSpacing(lerp(f5, f4, f2, timeInterpolator));
        } else {
            this.textPaint.setLetterSpacing(f4);
        }
        this.currentShadowRadius = lerp(this.expandedShadowRadius, this.collapsedShadowRadius, f2, null);
        this.currentShadowDx = lerp(this.expandedShadowDx, this.collapsedShadowDx, f2, null);
        this.currentShadowDy = lerp(this.expandedShadowDy, this.collapsedShadowDy, f2, null);
        int iBlendARGB = blendARGB(getCurrentColor(this.expandedShadowColor), getCurrentColor(this.collapsedShadowColor), f2);
        this.currentShadowColor = iBlendARGB;
        this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, iBlendARGB);
        if (this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (calculateFadeModeTextAlpha(f2) * this.textPaint.getAlpha()));
            if (i2 >= 31) {
                TextPaint textPaint = this.textPaint;
                textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, textPaint.getAlpha()));
            }
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void calculateUsingTextSize(float f2) {
        calculateUsingTextSize(f2, false);
    }

    private void clearTexture() {
        Bitmap bitmap = this.expandedTitleTexture;
        if (bitmap != null) {
            bitmap.recycle();
            this.expandedTitleTexture = null;
        }
    }

    private StaticLayout createStaticLayout(int i2, float f2, boolean z2) {
        StaticLayout staticLayoutBuild;
        try {
            staticLayoutBuild = StaticLayoutBuilderCompat.obtain(this.text, this.textPaint, (int) f2).setEllipsize(this.titleTextEllipsize).setIsRtl(z2).setAlignment(i2 == 1 ? Layout.Alignment.ALIGN_NORMAL : getMultilineTextLayoutAlignment()).setIncludePad(false).setMaxLines(i2).setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier).setHyphenationFrequency(this.hyphenationFrequency).setStaticLayoutBuilderConfigurer(this.staticLayoutBuilderConfigurer).build();
        } catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException e2) {
            e2.getCause().getMessage();
            staticLayoutBuild = null;
        }
        return (StaticLayout) Preconditions.checkNotNull(staticLayoutBuild);
    }

    private void drawMultilineTransition(@NonNull Canvas canvas, float f2, float f3) {
        int alpha = this.textPaint.getAlpha();
        canvas.translate(f2, f3);
        if (!this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (this.expandedTextBlend * alpha));
            if (Build.VERSION.SDK_INT >= 31) {
                TextPaint textPaint = this.textPaint;
                textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, textPaint.getAlpha()));
            }
            this.textLayout.draw(canvas);
        }
        if (!this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (this.collapsedTextBlend * alpha));
        }
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            TextPaint textPaint2 = this.textPaint;
            textPaint2.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, textPaint2.getAlpha()));
        }
        int lineBaseline = this.textLayout.getLineBaseline(0);
        CharSequence charSequence = this.textToDrawCollapsed;
        float f4 = lineBaseline;
        canvas.drawText(charSequence, 0, charSequence.length(), 0.0f, f4, this.textPaint);
        if (i2 >= 31) {
            this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, this.currentShadowColor);
        }
        if (this.fadeModeEnabled) {
            return;
        }
        String strTrim = this.textToDrawCollapsed.toString().trim();
        if (strTrim.endsWith(ELLIPSIS_NORMAL)) {
            strTrim = strTrim.substring(0, strTrim.length() - 1);
        }
        String str = strTrim;
        this.textPaint.setAlpha(alpha);
        canvas.drawText(str, 0, Math.min(this.textLayout.getLineEnd(0), str.length()), 0.0f, f4, (Paint) this.textPaint);
    }

    private void ensureExpandedTexture() {
        if (this.expandedTitleTexture != null || this.expandedBounds.isEmpty() || TextUtils.isEmpty(this.textToDraw)) {
            return;
        }
        calculateOffsets(0.0f);
        int width = this.textLayout.getWidth();
        int height = this.textLayout.getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }
        this.expandedTitleTexture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.textLayout.draw(new Canvas(this.expandedTitleTexture));
        if (this.texturePaint == null) {
            this.texturePaint = new Paint(3);
        }
    }

    private float getCollapsedTextLeftBound(int i2, int i3) {
        if (i3 == 17 || (i3 & 7) == 1) {
            return (i2 / 2.0f) - (this.collapsedTextWidth / 2.0f);
        }
        if ((i3 & GravityCompat.END) == 8388613 || (i3 & 5) == 5) {
            return this.isRtl ? this.collapsedBounds.left : this.collapsedBounds.right - this.collapsedTextWidth;
        }
        return this.isRtl ? this.collapsedBounds.right - this.collapsedTextWidth : this.collapsedBounds.left;
    }

    private float getCollapsedTextRightBound(@NonNull RectF rectF, int i2, int i3) {
        if (i3 == 17 || (i3 & 7) == 1) {
            return (this.collapsedTextWidth / 2.0f) + (i2 / 2.0f);
        }
        if ((i3 & GravityCompat.END) == 8388613 || (i3 & 5) == 5) {
            return this.isRtl ? rectF.left + this.collapsedTextWidth : this.collapsedBounds.right;
        }
        return this.isRtl ? this.collapsedBounds.right : rectF.left + this.collapsedTextWidth;
    }

    @ColorInt
    private int getCurrentColor(@Nullable ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        int[] iArr = this.state;
        return iArr != null ? colorStateList.getColorForState(iArr, 0) : colorStateList.getDefaultColor();
    }

    @ColorInt
    private int getCurrentExpandedTextColor() {
        return getCurrentColor(this.expandedTextColor);
    }

    private Layout.Alignment getMultilineTextLayoutAlignment() {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0) & 7;
        if (absoluteGravity == 1) {
            return Layout.Alignment.ALIGN_CENTER;
        }
        if (absoluteGravity != 5) {
            return this.isRtl ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL;
        }
        return this.isRtl ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_OPPOSITE;
    }

    private void getTextPaintCollapsed(@NonNull TextPaint textPaint) {
        textPaint.setTextSize(this.collapsedTextSize);
        textPaint.setTypeface(this.collapsedTypeface);
        textPaint.setLetterSpacing(this.collapsedLetterSpacing);
    }

    private void getTextPaintExpanded(@NonNull TextPaint textPaint) {
        textPaint.setTextSize(this.expandedTextSize);
        textPaint.setTypeface(this.expandedTypeface);
        textPaint.setLetterSpacing(this.expandedLetterSpacing);
    }

    private void interpolateBounds(float f2) {
        if (this.fadeModeEnabled) {
            this.currentBounds.set(f2 < this.fadeModeThresholdFraction ? this.expandedBounds : this.collapsedBounds);
            return;
        }
        this.currentBounds.left = lerp(this.expandedBounds.left, this.collapsedBounds.left, f2, this.positionInterpolator);
        this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, f2, this.positionInterpolator);
        this.currentBounds.right = lerp(this.expandedBounds.right, this.collapsedBounds.right, f2, this.positionInterpolator);
        this.currentBounds.bottom = lerp(this.expandedBounds.bottom, this.collapsedBounds.bottom, f2, this.positionInterpolator);
    }

    private static boolean isClose(float f2, float f3) {
        return Math.abs(f2 - f3) < 1.0E-5f;
    }

    private boolean isDefaultIsRtl() {
        return ViewCompat.getLayoutDirection(this.view) == 1;
    }

    private boolean isTextDirectionHeuristicsIsRtl(@NonNull CharSequence charSequence, boolean z2) {
        return (z2 ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequence, 0, charSequence.length());
    }

    private static float lerp(float f2, float f3, float f4, @Nullable TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            f4 = timeInterpolator.getInterpolation(f4);
        }
        return AnimationUtils.lerp(f2, f3, f4);
    }

    private float measureTextWidth(TextPaint textPaint, CharSequence charSequence) {
        return textPaint.measureText(charSequence, 0, charSequence.length());
    }

    private static boolean rectEquals(@NonNull Rect rect, int i2, int i3, int i4, int i5) {
        return rect.left == i2 && rect.top == i3 && rect.right == i4 && rect.bottom == i5;
    }

    private void setCollapsedTextBlend(float f2) {
        this.collapsedTextBlend = f2;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private boolean setCollapsedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.collapsedTypefaceDefault == typeface) {
            return false;
        }
        this.collapsedTypefaceDefault = typeface;
        Typeface typefaceMaybeCopyWithFontWeightAdjustment = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), typeface);
        this.collapsedTypefaceBold = typefaceMaybeCopyWithFontWeightAdjustment;
        if (typefaceMaybeCopyWithFontWeightAdjustment == null) {
            typefaceMaybeCopyWithFontWeightAdjustment = this.collapsedTypefaceDefault;
        }
        this.collapsedTypeface = typefaceMaybeCopyWithFontWeightAdjustment;
        return true;
    }

    private void setExpandedTextBlend(float f2) {
        this.expandedTextBlend = f2;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private boolean setExpandedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.expandedTypefaceDefault == typeface) {
            return false;
        }
        this.expandedTypefaceDefault = typeface;
        Typeface typefaceMaybeCopyWithFontWeightAdjustment = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), typeface);
        this.expandedTypefaceBold = typefaceMaybeCopyWithFontWeightAdjustment;
        if (typefaceMaybeCopyWithFontWeightAdjustment == null) {
            typefaceMaybeCopyWithFontWeightAdjustment = this.expandedTypefaceDefault;
        }
        this.expandedTypeface = typefaceMaybeCopyWithFontWeightAdjustment;
        return true;
    }

    private void setInterpolatedTextSize(float f2) {
        calculateUsingTextSize(f2);
        boolean z2 = USE_SCALING_TEXTURE && this.scale != 1.0f;
        this.useTexture = z2;
        if (z2) {
            ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private boolean shouldDrawMultiline() {
        return this.maxLines > 1 && (!this.isRtl || this.fadeModeEnabled) && !this.useTexture;
    }

    public void draw(@NonNull Canvas canvas) {
        int iSave = canvas.save();
        if (this.textToDraw == null || this.currentBounds.width() <= 0.0f || this.currentBounds.height() <= 0.0f) {
            return;
        }
        this.textPaint.setTextSize(this.currentTextSize);
        float f2 = this.currentDrawX;
        float f3 = this.currentDrawY;
        boolean z2 = this.useTexture && this.expandedTitleTexture != null;
        float f4 = this.scale;
        if (f4 != 1.0f && !this.fadeModeEnabled) {
            canvas.scale(f4, f4, f2, f3);
        }
        if (z2) {
            canvas.drawBitmap(this.expandedTitleTexture, f2, f3, this.texturePaint);
            canvas.restoreToCount(iSave);
            return;
        }
        if (!shouldDrawMultiline() || (this.fadeModeEnabled && this.expandedFraction <= this.fadeModeThresholdFraction)) {
            canvas.translate(f2, f3);
            this.textLayout.draw(canvas);
        } else {
            drawMultilineTransition(canvas, this.currentDrawX - this.textLayout.getLineStart(0), f3);
        }
        canvas.restoreToCount(iSave);
    }

    public void getCollapsedTextActualBounds(@NonNull RectF rectF, int i2, int i3) {
        this.isRtl = calculateIsRtl(this.text);
        rectF.left = Math.max(getCollapsedTextLeftBound(i2, i3), this.collapsedBounds.left);
        rectF.top = this.collapsedBounds.top;
        rectF.right = Math.min(getCollapsedTextRightBound(rectF, i2, i3), this.collapsedBounds.right);
        rectF.bottom = getCollapsedTextHeight() + this.collapsedBounds.top;
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    public int getCollapsedTextGravity() {
        return this.collapsedTextGravity;
    }

    public float getCollapsedTextHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getCollapsedTextSize() {
        return this.collapsedTextSize;
    }

    public Typeface getCollapsedTypeface() {
        Typeface typeface = this.collapsedTypeface;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    @ColorInt
    public int getCurrentCollapsedTextColor() {
        return getCurrentColor(this.collapsedTextColor);
    }

    public int getExpandedLineCount() {
        return this.expandedLineCount;
    }

    public ColorStateList getExpandedTextColor() {
        return this.expandedTextColor;
    }

    public float getExpandedTextFullHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return this.tmpPaint.descent() + (-this.tmpPaint.ascent());
    }

    public int getExpandedTextGravity() {
        return this.expandedTextGravity;
    }

    public float getExpandedTextHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getExpandedTextSize() {
        return this.expandedTextSize;
    }

    public Typeface getExpandedTypeface() {
        Typeface typeface = this.expandedTypeface;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    public float getFadeModeThresholdFraction() {
        return this.fadeModeThresholdFraction;
    }

    @RequiresApi(23)
    public int getHyphenationFrequency() {
        return this.hyphenationFrequency;
    }

    public int getLineCount() {
        StaticLayout staticLayout = this.textLayout;
        if (staticLayout != null) {
            return staticLayout.getLineCount();
        }
        return 0;
    }

    @RequiresApi(23)
    public float getLineSpacingAdd() {
        return this.textLayout.getSpacingAdd();
    }

    @RequiresApi(23)
    public float getLineSpacingMultiplier() {
        return this.textLayout.getSpacingMultiplier();
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    @Nullable
    public TimeInterpolator getPositionInterpolator() {
        return this.positionInterpolator;
    }

    @Nullable
    public CharSequence getText() {
        return this.text;
    }

    @NonNull
    public TextUtils.TruncateAt getTitleTextEllipsize() {
        return this.titleTextEllipsize;
    }

    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.isRtlTextDirectionHeuristicsEnabled;
    }

    public final boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.collapsedTextColor;
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.expandedTextColor) != null && colorStateList.isStateful());
    }

    public void maybeUpdateFontWeightAdjustment(@NonNull Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 31) {
            Typeface typeface = this.collapsedTypefaceDefault;
            if (typeface != null) {
                this.collapsedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(configuration, typeface);
            }
            Typeface typeface2 = this.expandedTypefaceDefault;
            if (typeface2 != null) {
                this.expandedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(configuration, typeface2);
            }
            Typeface typeface3 = this.collapsedTypefaceBold;
            if (typeface3 == null) {
                typeface3 = this.collapsedTypefaceDefault;
            }
            this.collapsedTypeface = typeface3;
            Typeface typeface4 = this.expandedTypefaceBold;
            if (typeface4 == null) {
                typeface4 = this.expandedTypefaceDefault;
            }
            this.expandedTypeface = typeface4;
            recalculate(true);
        }
    }

    public void recalculate() {
        recalculate(false);
    }

    public void setCollapsedAndExpandedTextColor(@Nullable ColorStateList colorStateList) {
        if (this.collapsedTextColor == colorStateList && this.expandedTextColor == colorStateList) {
            return;
        }
        this.collapsedTextColor = colorStateList;
        this.expandedTextColor = colorStateList;
        recalculate();
    }

    public void setCollapsedBounds(int i2, int i3, int i4, int i5) {
        if (rectEquals(this.collapsedBounds, i2, i3, i4, i5)) {
            return;
        }
        this.collapsedBounds.set(i2, i3, i4, i5);
        this.boundsChanged = true;
    }

    public void setCollapsedTextAppearance(int i2) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), i2);
        if (textAppearance.getTextColor() != null) {
            this.collapsedTextColor = textAppearance.getTextColor();
        }
        if (textAppearance.getTextSize() != 0.0f) {
            this.collapsedTextSize = textAppearance.getTextSize();
        }
        ColorStateList colorStateList = textAppearance.shadowColor;
        if (colorStateList != null) {
            this.collapsedShadowColor = colorStateList;
        }
        this.collapsedShadowDx = textAppearance.shadowDx;
        this.collapsedShadowDy = textAppearance.shadowDy;
        this.collapsedShadowRadius = textAppearance.shadowRadius;
        this.collapsedLetterSpacing = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.1
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setCollapsedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
        recalculate();
    }

    public void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.collapsedTextColor != colorStateList) {
            this.collapsedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setCollapsedTextGravity(int i2) {
        if (this.collapsedTextGravity != i2) {
            this.collapsedTextGravity = i2;
            recalculate();
        }
    }

    public void setCollapsedTextSize(float f2) {
        if (this.collapsedTextSize != f2) {
            this.collapsedTextSize = f2;
            recalculate();
        }
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (setCollapsedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setCurrentOffsetY(int i2) {
        this.currentOffsetY = i2;
    }

    public void setExpandedBounds(int i2, int i3, int i4, int i5) {
        if (rectEquals(this.expandedBounds, i2, i3, i4, i5)) {
            return;
        }
        this.expandedBounds.set(i2, i3, i4, i5);
        this.boundsChanged = true;
    }

    public void setExpandedLetterSpacing(float f2) {
        if (this.expandedLetterSpacing != f2) {
            this.expandedLetterSpacing = f2;
            recalculate();
        }
    }

    public void setExpandedTextAppearance(int i2) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), i2);
        if (textAppearance.getTextColor() != null) {
            this.expandedTextColor = textAppearance.getTextColor();
        }
        if (textAppearance.getTextSize() != 0.0f) {
            this.expandedTextSize = textAppearance.getTextSize();
        }
        ColorStateList colorStateList = textAppearance.shadowColor;
        if (colorStateList != null) {
            this.expandedShadowColor = colorStateList;
        }
        this.expandedShadowDx = textAppearance.shadowDx;
        this.expandedShadowDy = textAppearance.shadowDy;
        this.expandedShadowRadius = textAppearance.shadowRadius;
        this.expandedLetterSpacing = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() { // from class: com.google.android.material.internal.CollapsingTextHelper.2
            @Override // com.google.android.material.resources.CancelableFontCallback.ApplyFont
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setExpandedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.expandedFontCallback);
        recalculate();
    }

    public void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.expandedTextColor != colorStateList) {
            this.expandedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setExpandedTextGravity(int i2) {
        if (this.expandedTextGravity != i2) {
            this.expandedTextGravity = i2;
            recalculate();
        }
    }

    public void setExpandedTextSize(float f2) {
        if (this.expandedTextSize != f2) {
            this.expandedTextSize = f2;
            recalculate();
        }
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (setExpandedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setExpansionFraction(float f2) {
        float fClamp = MathUtils.clamp(f2, 0.0f, 1.0f);
        if (fClamp != this.expandedFraction) {
            this.expandedFraction = fClamp;
            calculateCurrentOffsets();
        }
    }

    public void setFadeModeEnabled(boolean z2) {
        this.fadeModeEnabled = z2;
    }

    public void setFadeModeStartFraction(float f2) {
        this.fadeModeStartFraction = f2;
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
    }

    @RequiresApi(23)
    public void setHyphenationFrequency(int i2) {
        this.hyphenationFrequency = i2;
    }

    @RequiresApi(23)
    public void setLineSpacingAdd(float f2) {
        this.lineSpacingAdd = f2;
    }

    @RequiresApi(23)
    public void setLineSpacingMultiplier(@FloatRange(from = 0.0d) float f2) {
        this.lineSpacingMultiplier = f2;
    }

    public void setMaxLines(int i2) {
        if (i2 != this.maxLines) {
            this.maxLines = i2;
            clearTexture();
            recalculate();
        }
    }

    public void setPositionInterpolator(TimeInterpolator timeInterpolator) {
        this.positionInterpolator = timeInterpolator;
        recalculate();
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean z2) {
        this.isRtlTextDirectionHeuristicsEnabled = z2;
    }

    public final boolean setState(int[] iArr) {
        this.state = iArr;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    @RequiresApi(23)
    public void setStaticLayoutBuilderConfigurer(@Nullable StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer) {
        if (this.staticLayoutBuilderConfigurer != staticLayoutBuilderConfigurer) {
            this.staticLayoutBuilderConfigurer = staticLayoutBuilderConfigurer;
            recalculate(true);
        }
    }

    public void setText(@Nullable CharSequence charSequence) {
        if (charSequence == null || !TextUtils.equals(this.text, charSequence)) {
            this.text = charSequence;
            this.textToDraw = null;
            clearTexture();
            recalculate();
        }
    }

    public void setTextSizeInterpolator(TimeInterpolator timeInterpolator) {
        this.textSizeInterpolator = timeInterpolator;
        recalculate();
    }

    public void setTitleTextEllipsize(@NonNull TextUtils.TruncateAt truncateAt) {
        this.titleTextEllipsize = truncateAt;
        recalculate();
    }

    public void setTypefaces(Typeface typeface) {
        boolean collapsedTypefaceInternal = setCollapsedTypefaceInternal(typeface);
        boolean expandedTypefaceInternal = setExpandedTypefaceInternal(typeface);
        if (collapsedTypefaceInternal || expandedTypefaceInternal) {
            recalculate();
        }
    }

    private void calculateUsingTextSize(float f2, boolean z2) {
        float f3;
        float f4;
        Typeface typeface;
        if (this.text == null) {
            return;
        }
        float fWidth = this.collapsedBounds.width();
        float fWidth2 = this.expandedBounds.width();
        if (isClose(f2, 1.0f)) {
            f3 = this.collapsedTextSize;
            f4 = this.collapsedLetterSpacing;
            this.scale = 1.0f;
            typeface = this.collapsedTypeface;
        } else {
            float f5 = this.expandedTextSize;
            float f6 = this.expandedLetterSpacing;
            Typeface typeface2 = this.expandedTypeface;
            if (isClose(f2, 0.0f)) {
                this.scale = 1.0f;
            } else {
                this.scale = lerp(this.expandedTextSize, this.collapsedTextSize, f2, this.textSizeInterpolator) / this.expandedTextSize;
            }
            float f7 = this.collapsedTextSize / this.expandedTextSize;
            fWidth = (z2 || this.fadeModeEnabled || fWidth2 * f7 <= fWidth) ? fWidth2 : Math.min(fWidth / f7, fWidth2);
            f3 = f5;
            f4 = f6;
            typeface = typeface2;
        }
        boolean z3 = false;
        if (fWidth > 0.0f) {
            boolean z4 = this.currentTextSize != f3;
            boolean z5 = this.currentLetterSpacing != f4;
            boolean z6 = this.currentTypeface != typeface;
            StaticLayout staticLayout = this.textLayout;
            boolean z7 = z4 || z5 || (staticLayout != null && (fWidth > ((float) staticLayout.getWidth()) ? 1 : (fWidth == ((float) staticLayout.getWidth()) ? 0 : -1)) != 0) || z6 || this.boundsChanged;
            this.currentTextSize = f3;
            this.currentLetterSpacing = f4;
            this.currentTypeface = typeface;
            this.boundsChanged = false;
            this.textPaint.setLinearText(this.scale != 1.0f);
            z3 = z7;
        }
        if (this.textToDraw == null || z3) {
            this.textPaint.setTextSize(this.currentTextSize);
            this.textPaint.setTypeface(this.currentTypeface);
            this.textPaint.setLetterSpacing(this.currentLetterSpacing);
            this.isRtl = calculateIsRtl(this.text);
            StaticLayout staticLayoutCreateStaticLayout = createStaticLayout(shouldDrawMultiline() ? this.maxLines : 1, fWidth, this.isRtl);
            this.textLayout = staticLayoutCreateStaticLayout;
            this.textToDraw = staticLayoutCreateStaticLayout.getText();
        }
    }

    public void recalculate(boolean z2) {
        if ((this.view.getHeight() <= 0 || this.view.getWidth() <= 0) && !z2) {
            return;
        }
        calculateBaseOffsets(z2);
        calculateCurrentOffsets();
    }

    public void setCollapsedBounds(@NonNull Rect rect) {
        setCollapsedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setExpandedBounds(@NonNull Rect rect) {
        setExpandedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }
}
