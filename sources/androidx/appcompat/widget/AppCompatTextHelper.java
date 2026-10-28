package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.TypedValueCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;

    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;

    @NonNull
    private final TextView mView;
    private int mStyle = 0;
    private int mFontWeight = -1;

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static Locale forLanguageTag(String str) {
            return Locale.forLanguageTag(str);
        }
    }

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        public static LocaleList forLanguageTags(String str) {
            return LocaleList.forLanguageTags(str);
        }

        @DoNotInline
        public static void setTextLocales(TextView textView, LocaleList localeList) {
            textView.setTextLocales(localeList);
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        @DoNotInline
        public static void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int i2, int i3, int i4, int i5) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i2, i3, i4, i5);
        }

        @DoNotInline
        public static void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, int[] iArr, int i2) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i2);
        }

        @DoNotInline
        public static boolean setFontVariationSettings(TextView textView, String str) {
            return textView.setFontVariationSettings(str);
        }
    }

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        public static Typeface create(Typeface typeface, int i2, boolean z2) {
            return Typeface.create(typeface, i2, z2);
        }
    }

    public AppCompatTextHelper(@NonNull TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(textView);
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable == null || tintInfo == null) {
            return;
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i2) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i2);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (drawable5 != null || drawable6 != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            TextView textView = this.mView;
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
            return;
        }
        if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
        Drawable drawable7 = compoundDrawablesRelative2[0];
        if (drawable7 != null || compoundDrawablesRelative2[2] != null) {
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative2[1];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative2[3];
            }
            this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, compoundDrawablesRelative2[2], drawable4);
            return;
        }
        Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
        TextView textView2 = this.mView;
        if (drawable == null) {
            drawable = compoundDrawables[0];
        }
        if (drawable2 == null) {
            drawable2 = compoundDrawables[1];
        }
        if (drawable3 == null) {
            drawable3 = compoundDrawables[2];
        }
        if (drawable4 == null) {
            drawable4 = compoundDrawables[3];
        }
        textView2.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setTextSizeInternal(int i2, float f2) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i2, f2);
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        Typeface typefaceCreate;
        Typeface typeface;
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            int i3 = tintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            this.mFontWeight = i3;
            if (i3 != -1) {
                this.mStyle &= 2;
            }
        }
        if (!tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) && !tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
                this.mAsyncFontPending = false;
                int i4 = tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1);
                if (i4 == 1) {
                    typeface = Typeface.SANS_SERIF;
                } else if (i4 == 2) {
                    typeface = Typeface.SERIF;
                } else if (i4 != 3) {
                    return;
                } else {
                    typeface = Typeface.MONOSPACE;
                }
                this.mFontTypeface = typeface;
                return;
            }
            return;
        }
        this.mFontTypeface = null;
        int i5 = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
        final int i6 = this.mFontWeight;
        final int i7 = this.mStyle;
        if (!context.isRestricted()) {
            final WeakReference weakReference = new WeakReference(this.mView);
            try {
                Typeface font = tintTypedArray.getFont(i5, this.mStyle, new ResourcesCompat.FontCallback() { // from class: androidx.appcompat.widget.AppCompatTextHelper.1
                    @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                    /* JADX INFO: renamed from: onFontRetrievalFailed */
                    public void lambda$callbackFailAsync$1(int i8) {
                    }

                    @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                    /* JADX INFO: renamed from: onFontRetrieved */
                    public void lambda$callbackSuccessAsync$0(@NonNull Typeface typeface2) {
                        int i8;
                        if (Build.VERSION.SDK_INT >= 28 && (i8 = i6) != -1) {
                            typeface2 = Api28Impl.create(typeface2, i8, (i7 & 2) != 0);
                        }
                        AppCompatTextHelper.this.onAsyncTypefaceReceived(weakReference, typeface2);
                    }
                });
                if (font != null) {
                    if (i2 >= 28 && this.mFontWeight != -1) {
                        font = Api28Impl.create(Typeface.create(font, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                    }
                    this.mFontTypeface = font;
                }
                this.mAsyncFontPending = this.mFontTypeface == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.mFontTypeface != null || (string = tintTypedArray.getString(i5)) == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
            typefaceCreate = Typeface.create(string, this.mStyle);
        } else {
            typefaceCreate = Api28Impl.create(Typeface.create(string, 0), this.mFontWeight, (this.mStyle & 2) != 0);
        }
        this.mFontTypeface = typefaceCreate;
    }

    public void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (this.mDrawableStartTint == null && this.mDrawableEndTint == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
        applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
        applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    @Nullable
    public ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    @Nullable
    public PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    /* JADX WARN: Code duplicated, block: B:163:0x034f  */
    /* JADX WARN: Code duplicated, block: B:165:0x0356  */
    /* JADX WARN: Code duplicated, block: B:168:0x035f  */
    /* JADX WARN: Code duplicated, block: B:170:0x0363  */
    /* JADX WARN: Code duplicated, block: B:171:0x0368  */
    /* JADX WARN: Code duplicated, block: B:173:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0118  */
    /* JADX WARN: Code duplicated, block: B:46:0x011f  */
    /* JADX WARN: Code duplicated, block: B:51:0x0131  */
    @SuppressLint({"NewApi"})
    public void loadFromAttributes(@Nullable AttributeSet attributeSet, int i2) {
        String string;
        ColorStateList colorStateList;
        boolean z2;
        boolean z3;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        String string2;
        boolean z4;
        int i3;
        int i4;
        float dimensionPixelSize;
        int i5;
        TextView textView;
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextHelper, i2, 0);
        TextView textView2 = this.mView;
        ViewCompat.saveAttributeDataForStyleable(textView2, textView2.getContext(), R.styleable.AppCompatTextHelper, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i2, 0);
        int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
            this.mDrawableStartTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.mDrawableEndTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        boolean z5 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId != -1) {
            TintTypedArray tintTypedArrayObtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, R.styleable.TextAppearance);
            if (z5 || !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z2 = false;
                z3 = false;
            } else {
                z2 = tintTypedArrayObtainStyledAttributes2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                z3 = true;
            }
            updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes2);
            int i6 = Build.VERSION.SDK_INT;
            if (i6 < 23) {
                colorStateList = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColor) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                colorStateList2 = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorHint) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                colorStateList3 = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorLink) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorLink) : null;
                if (tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textLocale)) {
                    string = tintTypedArrayObtainStyledAttributes2.getString(R.styleable.TextAppearance_textLocale);
                } else {
                    string = null;
                }
                if (i6 >= 26 || !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
                    string2 = null;
                } else {
                    string2 = tintTypedArrayObtainStyledAttributes2.getString(R.styleable.TextAppearance_fontVariationSettings);
                }
                tintTypedArrayObtainStyledAttributes2.recycle();
            } else {
                colorStateList = null;
                colorStateList2 = null;
            }
            if (tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textLocale)) {
                string = tintTypedArrayObtainStyledAttributes2.getString(R.styleable.TextAppearance_textLocale);
            } else {
                string = null;
            }
            if (i6 >= 26) {
                string2 = null;
            } else {
                string2 = null;
            }
            tintTypedArrayObtainStyledAttributes2.recycle();
        } else {
            string = null;
            colorStateList = null;
            z2 = false;
            z3 = false;
            colorStateList2 = null;
            colorStateList3 = null;
            string2 = null;
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextAppearance, i2, 0);
        if (z5 || !tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z4 = z3;
        } else {
            z2 = tintTypedArrayObtainStyledAttributes3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            z4 = true;
        }
        int i7 = Build.VERSION.SDK_INT;
        if (i7 < 23) {
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                colorStateList = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList2 = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList3 = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            }
        }
        if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textLocale)) {
            string = tintTypedArrayObtainStyledAttributes3.getString(R.styleable.TextAppearance_textLocale);
        }
        if (i7 >= 26 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
            string2 = tintTypedArrayObtainStyledAttributes3.getString(R.styleable.TextAppearance_fontVariationSettings);
        }
        if (i7 >= 28 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArrayObtainStyledAttributes3.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes3);
        tintTypedArrayObtainStyledAttributes3.recycle();
        if (colorStateList != null) {
            this.mView.setTextColor(colorStateList);
        }
        if (colorStateList2 != null) {
            this.mView.setHintTextColor(colorStateList2);
        }
        if (colorStateList3 != null) {
            this.mView.setLinkTextColor(colorStateList3);
        }
        if (!z5 && z4) {
            setAllCaps(z2);
        }
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface(typeface, this.mStyle);
            } else {
                this.mView.setTypeface(typeface);
            }
        }
        if (string2 != null) {
            Api26Impl.setFontVariationSettings(this.mView, string2);
        }
        if (string != null) {
            if (i7 >= 24) {
                Api24Impl.setTextLocales(this.mView, Api24Impl.forLanguageTags(string));
            } else {
                this.mView.setTextLocale(Api21Impl.forLanguageTag(string.split(",")[0]));
            }
        }
        this.mAutoSizeTextHelper.loadFromAttributes(attributeSet, i2);
        if (ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
            int[] autoSizeTextAvailableSizes = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
            if (autoSizeTextAvailableSizes.length > 0) {
                if (Api26Impl.getAutoSizeStepGranularity(this.mView) != -1.0f) {
                    Api26Impl.setAutoSizeTextTypeUniformWithConfiguration(this.mView, this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
                } else {
                    Api26Impl.setAutoSizeTextTypeUniformWithPresetSizes(this.mView, autoSizeTextAvailableSizes, 0);
                }
            }
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextView);
        int resourceId2 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableLeftCompat, -1);
        Drawable drawable = resourceId2 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId2) : null;
        int resourceId3 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableTopCompat, -1);
        Drawable drawable2 = resourceId3 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId3) : null;
        int resourceId4 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableRightCompat, -1);
        Drawable drawable3 = resourceId4 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId4) : null;
        int resourceId5 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableBottomCompat, -1);
        Drawable drawable4 = resourceId5 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId5) : null;
        int resourceId6 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableStartCompat, -1);
        Drawable drawable5 = resourceId6 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId6) : null;
        int resourceId7 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableEndCompat, -1);
        setCompoundDrawables(drawable, drawable2, drawable3, drawable4, drawable5, resourceId7 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId7) : null);
        if (tintTypedArrayObtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_drawableTint)) {
            TextViewCompat.setCompoundDrawableTintList(this.mView, tintTypedArrayObtainStyledAttributes4.getColorStateList(R.styleable.AppCompatTextView_drawableTint));
        }
        if (tintTypedArrayObtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_drawableTintMode)) {
            i3 = -1;
            TextViewCompat.setCompoundDrawableTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes4.getInt(R.styleable.AppCompatTextView_drawableTintMode, -1), null));
        } else {
            i3 = -1;
        }
        int dimensionPixelSize2 = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, i3);
        int dimensionPixelSize3 = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, i3);
        if (tintTypedArrayObtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_lineHeight)) {
            TypedValue typedValuePeekValue = tintTypedArrayObtainStyledAttributes4.peekValue(R.styleable.AppCompatTextView_lineHeight);
            if (typedValuePeekValue == null || typedValuePeekValue.type != 5) {
                i4 = -1;
                dimensionPixelSize = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, -1);
            } else {
                int unitFromComplexDimension = TypedValueCompat.getUnitFromComplexDimension(typedValuePeekValue.data);
                dimensionPixelSize = TypedValue.complexToFloat(typedValuePeekValue.data);
                i5 = unitFromComplexDimension;
                i4 = -1;
            }
            tintTypedArrayObtainStyledAttributes4.recycle();
            if (dimensionPixelSize2 != i4) {
                TextViewCompat.setFirstBaselineToTopHeight(this.mView, dimensionPixelSize2);
            }
            if (dimensionPixelSize3 != i4) {
                TextViewCompat.setLastBaselineToBottomHeight(this.mView, dimensionPixelSize3);
            }
            if (dimensionPixelSize != -1.0f) {
                textView = this.mView;
                if (i5 == i4) {
                    TextViewCompat.setLineHeight(textView, (int) dimensionPixelSize);
                } else {
                    TextViewCompat.setLineHeight(textView, i5, dimensionPixelSize);
                }
            }
        }
        i4 = -1;
        dimensionPixelSize = -1.0f;
        i5 = -1;
        tintTypedArrayObtainStyledAttributes4.recycle();
        if (dimensionPixelSize2 != i4) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != i4) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, dimensionPixelSize3);
        }
        if (dimensionPixelSize != -1.0f) {
            textView = this.mView;
            if (i5 == i4) {
                TextViewCompat.setLineHeight(textView, (int) dimensionPixelSize);
            } else {
                TextViewCompat.setLineHeight(textView, i5, dimensionPixelSize);
            }
        }
    }

    public void onAsyncTypefaceReceived(WeakReference<TextView> weakReference, final Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            final TextView textView = weakReference.get();
            if (textView != null) {
                if (!textView.isAttachedToWindow()) {
                    textView.setTypeface(typeface, this.mStyle);
                } else {
                    final int i2 = this.mStyle;
                    textView.post(new Runnable() { // from class: androidx.appcompat.widget.AppCompatTextHelper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            textView.setTypeface(typeface, i2);
                        }
                    });
                }
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE) {
            return;
        }
        autoSizeText();
    }

    public void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    public void onSetTextAppearance(Context context, int i2) {
        String string;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i2, R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        int i3 = Build.VERSION.SDK_INT;
        if (i3 < 23) {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList3 = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
                this.mView.setTextColor(colorStateList3);
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColorLink) && (colorStateList2 = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColorLink)) != null) {
                this.mView.setLinkTextColor(colorStateList2);
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColorHint) && (colorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColorHint)) != null) {
                this.mView.setHintTextColor(colorStateList);
            }
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes);
        if (i3 >= 26 && tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_fontVariationSettings) && (string = tintTypedArrayObtainStyledAttributes.getString(R.styleable.TextAppearance_fontVariationSettings)) != null) {
            Api26Impl.setFontVariationSettings(this.mView, string);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    public void populateSurroundingTextIfNeeded(@NonNull TextView textView, @Nullable InputConnection inputConnection, @NonNull EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT >= 30 || inputConnection == null) {
            return;
        }
        EditorInfoCompat.setInitialSurroundingText(editorInfo, textView.getText());
    }

    public void setAllCaps(boolean z2) {
        this.mView.setAllCaps(z2);
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int i2, int i3, int i4, int i5) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i2, i3, i4, i5);
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] iArr, int i2) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i2);
    }

    public void setAutoSizeTextTypeWithDefaults(int i2) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i2);
    }

    public void setCompoundDrawableTintList(@Nullable ColorStateList colorStateList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = colorStateList != null;
        setCompoundTints();
    }

    public void setCompoundDrawableTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = mode != null;
        setCompoundTints();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setTextSize(int i2, float f2) {
        if (ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE || isAutoSizeEnabled()) {
            return;
        }
        setTextSizeInternal(i2, f2);
    }
}
