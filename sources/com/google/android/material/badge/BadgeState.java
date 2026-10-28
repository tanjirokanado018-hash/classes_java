package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.annotation.XmlRes;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import java.util.Locale;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class BadgeState {
    private static final String BADGE_RESOURCE_TAG = "badge";
    final float badgeHeight;
    final float badgeRadius;
    final float badgeWidth;
    final float badgeWithTextHeight;
    final float badgeWithTextRadius;
    final float badgeWithTextWidth;
    private final State currentState;
    final int horizontalInset;
    final int horizontalInsetWithText;
    int offsetAlignmentMode;
    private final State overridingState;

    public static final class State implements Parcelable {
        private static final int BADGE_NUMBER_NONE = -1;
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() { // from class: com.google.android.material.badge.BadgeState.State.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NonNull
            public State createFromParcel(@NonNull Parcel parcel) {
                return new State(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NonNull
            public State[] newArray(int i2) {
                return new State[i2];
            }
        };
        private static final int NOT_SET = -2;

        @Dimension(unit = 1)
        private Integer additionalHorizontalOffset;

        @Dimension(unit = 1)
        private Integer additionalVerticalOffset;
        private int alpha;
        private Boolean autoAdjustToWithinGrandparentBounds;

        @ColorInt
        private Integer backgroundColor;
        private Integer badgeGravity;

        @Px
        private Integer badgeHorizontalPadding;

        @XmlRes
        private int badgeResId;

        @StyleRes
        private Integer badgeShapeAppearanceOverlayResId;

        @StyleRes
        private Integer badgeShapeAppearanceResId;

        @StyleRes
        private Integer badgeTextAppearanceResId;

        @ColorInt
        private Integer badgeTextColor;

        @Px
        private Integer badgeVerticalPadding;

        @StyleRes
        private Integer badgeWithTextShapeAppearanceOverlayResId;

        @StyleRes
        private Integer badgeWithTextShapeAppearanceResId;

        @StringRes
        private int contentDescriptionExceedsMaxBadgeNumberRes;

        @Nullable
        private CharSequence contentDescriptionForText;

        @Nullable
        private CharSequence contentDescriptionNumberless;

        @PluralsRes
        private int contentDescriptionQuantityStrings;

        @Dimension(unit = 1)
        private Integer horizontalOffsetWithText;

        @Dimension(unit = 1)
        private Integer horizontalOffsetWithoutText;
        private Boolean isVisible;

        @Dimension(unit = 1)
        private Integer largeFontVerticalOffsetAdjustment;
        private int maxCharacterCount;
        private int maxNumber;
        private int number;
        private Locale numberLocale;

        @Nullable
        private String text;

        @Dimension(unit = 1)
        private Integer verticalOffsetWithText;

        @Dimension(unit = 1)
        private Integer verticalOffsetWithoutText;

        public State() {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.maxNumber = -2;
            this.isVisible = Boolean.TRUE;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i2) {
            parcel.writeInt(this.badgeResId);
            parcel.writeSerializable(this.backgroundColor);
            parcel.writeSerializable(this.badgeTextColor);
            parcel.writeSerializable(this.badgeTextAppearanceResId);
            parcel.writeSerializable(this.badgeShapeAppearanceResId);
            parcel.writeSerializable(this.badgeShapeAppearanceOverlayResId);
            parcel.writeSerializable(this.badgeWithTextShapeAppearanceResId);
            parcel.writeSerializable(this.badgeWithTextShapeAppearanceOverlayResId);
            parcel.writeInt(this.alpha);
            parcel.writeString(this.text);
            parcel.writeInt(this.number);
            parcel.writeInt(this.maxCharacterCount);
            parcel.writeInt(this.maxNumber);
            CharSequence charSequence = this.contentDescriptionForText;
            parcel.writeString(charSequence != null ? charSequence.toString() : null);
            CharSequence charSequence2 = this.contentDescriptionNumberless;
            parcel.writeString(charSequence2 != null ? charSequence2.toString() : null);
            parcel.writeInt(this.contentDescriptionQuantityStrings);
            parcel.writeSerializable(this.badgeGravity);
            parcel.writeSerializable(this.badgeHorizontalPadding);
            parcel.writeSerializable(this.badgeVerticalPadding);
            parcel.writeSerializable(this.horizontalOffsetWithoutText);
            parcel.writeSerializable(this.verticalOffsetWithoutText);
            parcel.writeSerializable(this.horizontalOffsetWithText);
            parcel.writeSerializable(this.verticalOffsetWithText);
            parcel.writeSerializable(this.largeFontVerticalOffsetAdjustment);
            parcel.writeSerializable(this.additionalHorizontalOffset);
            parcel.writeSerializable(this.additionalVerticalOffset);
            parcel.writeSerializable(this.isVisible);
            parcel.writeSerializable(this.numberLocale);
            parcel.writeSerializable(this.autoAdjustToWithinGrandparentBounds);
        }

        public State(@NonNull Parcel parcel) {
            this.alpha = 255;
            this.number = -2;
            this.maxCharacterCount = -2;
            this.maxNumber = -2;
            this.isVisible = Boolean.TRUE;
            this.badgeResId = parcel.readInt();
            this.backgroundColor = (Integer) parcel.readSerializable();
            this.badgeTextColor = (Integer) parcel.readSerializable();
            this.badgeTextAppearanceResId = (Integer) parcel.readSerializable();
            this.badgeShapeAppearanceResId = (Integer) parcel.readSerializable();
            this.badgeShapeAppearanceOverlayResId = (Integer) parcel.readSerializable();
            this.badgeWithTextShapeAppearanceResId = (Integer) parcel.readSerializable();
            this.badgeWithTextShapeAppearanceOverlayResId = (Integer) parcel.readSerializable();
            this.alpha = parcel.readInt();
            this.text = parcel.readString();
            this.number = parcel.readInt();
            this.maxCharacterCount = parcel.readInt();
            this.maxNumber = parcel.readInt();
            this.contentDescriptionForText = parcel.readString();
            this.contentDescriptionNumberless = parcel.readString();
            this.contentDescriptionQuantityStrings = parcel.readInt();
            this.badgeGravity = (Integer) parcel.readSerializable();
            this.badgeHorizontalPadding = (Integer) parcel.readSerializable();
            this.badgeVerticalPadding = (Integer) parcel.readSerializable();
            this.horizontalOffsetWithoutText = (Integer) parcel.readSerializable();
            this.verticalOffsetWithoutText = (Integer) parcel.readSerializable();
            this.horizontalOffsetWithText = (Integer) parcel.readSerializable();
            this.verticalOffsetWithText = (Integer) parcel.readSerializable();
            this.largeFontVerticalOffsetAdjustment = (Integer) parcel.readSerializable();
            this.additionalHorizontalOffset = (Integer) parcel.readSerializable();
            this.additionalVerticalOffset = (Integer) parcel.readSerializable();
            this.isVisible = (Boolean) parcel.readSerializable();
            this.numberLocale = (Locale) parcel.readSerializable();
            this.autoAdjustToWithinGrandparentBounds = (Boolean) parcel.readSerializable();
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x02ae  */
    /* JADX WARN: Code duplicated, block: B:103:0x02c3  */
    /* JADX WARN: Code duplicated, block: B:104:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:107:0x02df  */
    /* JADX WARN: Code duplicated, block: B:108:0x02ee  */
    /* JADX WARN: Code duplicated, block: B:111:0x0303  */
    /* JADX WARN: Code duplicated, block: B:112:0x0312  */
    /* JADX WARN: Code duplicated, block: B:115:0x0327  */
    /* JADX WARN: Code duplicated, block: B:116:0x032e  */
    /* JADX WARN: Code duplicated, block: B:119:0x0343  */
    /* JADX WARN: Code duplicated, block: B:120:0x0345  */
    /* JADX WARN: Code duplicated, block: B:123:0x035a  */
    /* JADX WARN: Code duplicated, block: B:124:0x035c  */
    /* JADX WARN: Code duplicated, block: B:127:0x0371  */
    /* JADX WARN: Code duplicated, block: B:128:0x0378  */
    /* JADX WARN: Code duplicated, block: B:131:0x0390  */
    /* JADX WARN: Code duplicated, block: B:133:0x0396  */
    /* JADX WARN: Code duplicated, block: B:134:0x039f  */
    /* JADX WARN: Code duplicated, block: B:136:0x03a7  */
    /* JADX WARN: Code duplicated, block: B:21:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:23:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:25:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:28:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:29:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:32:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:33:0x0101  */
    /* JADX WARN: Code duplicated, block: B:36:0x010e  */
    /* JADX WARN: Code duplicated, block: B:37:0x0111  */
    /* JADX WARN: Code duplicated, block: B:40:0x011e  */
    /* JADX WARN: Code duplicated, block: B:46:0x0137  */
    /* JADX WARN: Code duplicated, block: B:47:0x013e  */
    /* JADX WARN: Code duplicated, block: B:50:0x014b  */
    /* JADX WARN: Code duplicated, block: B:51:0x0152  */
    /* JADX WARN: Code duplicated, block: B:54:0x015f  */
    /* JADX WARN: Code duplicated, block: B:55:0x0168  */
    /* JADX WARN: Code duplicated, block: B:58:0x017d  */
    /* JADX WARN: Code duplicated, block: B:59:0x0184  */
    /* JADX WARN: Code duplicated, block: B:62:0x0199  */
    /* JADX WARN: Code duplicated, block: B:63:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:66:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:67:0x01be  */
    /* JADX WARN: Code duplicated, block: B:70:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:71:0x01da  */
    /* JADX WARN: Code duplicated, block: B:74:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:75:0x01f8  */
    /* JADX WARN: Code duplicated, block: B:78:0x020d  */
    /* JADX WARN: Code duplicated, block: B:80:0x0215  */
    /* JADX WARN: Code duplicated, block: B:82:0x021d  */
    /* JADX WARN: Code duplicated, block: B:84:0x0228  */
    /* JADX WARN: Code duplicated, block: B:87:0x0244  */
    /* JADX WARN: Code duplicated, block: B:88:0x024e  */
    /* JADX WARN: Code duplicated, block: B:91:0x0263  */
    /* JADX WARN: Code duplicated, block: B:92:0x0270  */
    /* JADX WARN: Code duplicated, block: B:95:0x0285  */
    /* JADX WARN: Code duplicated, block: B:96:0x0292  */
    /* JADX WARN: Code duplicated, block: B:99:0x02a7  */
    public BadgeState(Context context, @XmlRes int i2, @AttrRes int i3, @StyleRes int i4, @Nullable State state) {
        int i5;
        CharSequence string;
        int i6;
        int i7;
        int i8;
        int i9;
        int iIntValue;
        int iIntValue2;
        int iIntValue3;
        int iIntValue4;
        int iIntValue5;
        int iIntValue6;
        int defaultColor;
        Integer numValueOf;
        int iIntValue7;
        int iIntValue8;
        int iIntValue9;
        int iIntValue10;
        int iIntValue11;
        int iIntValue12;
        int iIntValue13;
        int iIntValue14;
        int iIntValue15;
        int iIntValue16;
        boolean zBooleanValue;
        Locale locale;
        String string2;
        State state2 = new State();
        this.currentState = state2;
        state = state == null ? new State() : state;
        if (i2 != 0) {
            state.badgeResId = i2;
        }
        TypedArray typedArrayGenerateTypedArray = generateTypedArray(context, state.badgeResId, i3, i4);
        Resources resources = context.getResources();
        this.badgeRadius = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeRadius, -1);
        this.horizontalInset = context.getResources().getDimensionPixelSize(R.dimen.mtrl_badge_horizontal_edge_offset);
        this.horizontalInsetWithText = context.getResources().getDimensionPixelSize(R.dimen.mtrl_badge_text_horizontal_edge_offset);
        this.badgeWithTextRadius = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWithTextRadius, -1);
        this.badgeWidth = typedArrayGenerateTypedArray.getDimension(R.styleable.Badge_badgeWidth, resources.getDimension(R.dimen.m3_badge_size));
        this.badgeWithTextWidth = typedArrayGenerateTypedArray.getDimension(R.styleable.Badge_badgeWithTextWidth, resources.getDimension(R.dimen.m3_badge_with_text_size));
        this.badgeHeight = typedArrayGenerateTypedArray.getDimension(R.styleable.Badge_badgeHeight, resources.getDimension(R.dimen.m3_badge_size));
        this.badgeWithTextHeight = typedArrayGenerateTypedArray.getDimension(R.styleable.Badge_badgeWithTextHeight, resources.getDimension(R.dimen.m3_badge_with_text_size));
        boolean z2 = true;
        this.offsetAlignmentMode = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_offsetAlignmentMode, 1);
        state2.alpha = state.alpha == -2 ? 255 : state.alpha;
        if (state.number == -2) {
            if (typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_number)) {
                i5 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_number, 0);
            } else {
                state2.number = -1;
            }
            if (state.text != null) {
                string2 = typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_badgeText) ? typedArrayGenerateTypedArray.getString(R.styleable.Badge_badgeText) : string2;
                state2.contentDescriptionForText = state.contentDescriptionForText;
                if (state.contentDescriptionNumberless == null) {
                    string = context.getString(R.string.mtrl_badge_numberless_content_description);
                } else {
                    string = state.contentDescriptionNumberless;
                }
                state2.contentDescriptionNumberless = string;
                if (state.contentDescriptionQuantityStrings == 0) {
                    i6 = R.plurals.mtrl_badge_content_description;
                } else {
                    i6 = state.contentDescriptionQuantityStrings;
                }
                state2.contentDescriptionQuantityStrings = i6;
                if (state.contentDescriptionExceedsMaxBadgeNumberRes == 0) {
                    i7 = R.string.mtrl_exceed_max_badge_number_content_description;
                } else {
                    i7 = state.contentDescriptionExceedsMaxBadgeNumberRes;
                }
                state2.contentDescriptionExceedsMaxBadgeNumberRes = i7;
                if (state.isVisible != null && !state.isVisible.booleanValue()) {
                    z2 = false;
                }
                state2.isVisible = Boolean.valueOf(z2);
                if (state.maxCharacterCount == -2) {
                    i8 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxCharacterCount, -2);
                } else {
                    i8 = state.maxCharacterCount;
                }
                state2.maxCharacterCount = i8;
                if (state.maxNumber == -2) {
                    i9 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxNumber, -2);
                } else {
                    i9 = state.maxNumber;
                }
                state2.maxNumber = i9;
                if (state.badgeShapeAppearanceResId == null) {
                    iIntValue = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
                } else {
                    iIntValue = state.badgeShapeAppearanceResId.intValue();
                }
                state2.badgeShapeAppearanceResId = Integer.valueOf(iIntValue);
                if (state.badgeShapeAppearanceOverlayResId == null) {
                    iIntValue2 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearanceOverlay, 0);
                } else {
                    iIntValue2 = state.badgeShapeAppearanceOverlayResId.intValue();
                }
                state2.badgeShapeAppearanceOverlayResId = Integer.valueOf(iIntValue2);
                if (state.badgeWithTextShapeAppearanceResId == null) {
                    iIntValue3 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
                } else {
                    iIntValue3 = state.badgeWithTextShapeAppearanceResId.intValue();
                }
                state2.badgeWithTextShapeAppearanceResId = Integer.valueOf(iIntValue3);
                if (state.badgeWithTextShapeAppearanceOverlayResId == null) {
                    iIntValue4 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearanceOverlay, 0);
                } else {
                    iIntValue4 = state.badgeWithTextShapeAppearanceOverlayResId.intValue();
                }
                state2.badgeWithTextShapeAppearanceOverlayResId = Integer.valueOf(iIntValue4);
                if (state.backgroundColor == null) {
                    iIntValue5 = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_backgroundColor);
                } else {
                    iIntValue5 = state.backgroundColor.intValue();
                }
                state2.backgroundColor = Integer.valueOf(iIntValue5);
                if (state.badgeTextAppearanceResId == null) {
                    iIntValue6 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeTextAppearance, R.style.TextAppearance_MaterialComponents_Badge);
                } else {
                    iIntValue6 = state.badgeTextAppearanceResId.intValue();
                }
                state2.badgeTextAppearanceResId = Integer.valueOf(iIntValue6);
                if (state.badgeTextColor != null) {
                    numValueOf = state.badgeTextColor;
                } else {
                    if (typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_badgeTextColor)) {
                        defaultColor = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_badgeTextColor);
                    } else {
                        defaultColor = new TextAppearance(context, state2.badgeTextAppearanceResId.intValue()).getTextColor().getDefaultColor();
                    }
                    numValueOf = Integer.valueOf(defaultColor);
                }
                state2.badgeTextColor = numValueOf;
                if (state.badgeGravity == null) {
                    iIntValue7 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_badgeGravity, 8388661);
                } else {
                    iIntValue7 = state.badgeGravity.intValue();
                }
                state2.badgeGravity = Integer.valueOf(iIntValue7);
                if (state.badgeHorizontalPadding == null) {
                    iIntValue8 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
                } else {
                    iIntValue8 = state.badgeHorizontalPadding.intValue();
                }
                state2.badgeHorizontalPadding = Integer.valueOf(iIntValue8);
                if (state.badgeVerticalPadding == null) {
                    iIntValue9 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeVerticalPadding, resources.getDimensionPixelSize(R.dimen.m3_badge_with_text_vertical_padding));
                } else {
                    iIntValue9 = state.badgeVerticalPadding.intValue();
                }
                state2.badgeVerticalPadding = Integer.valueOf(iIntValue9);
                if (state.horizontalOffsetWithoutText == null) {
                    iIntValue10 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0);
                } else {
                    iIntValue10 = state.horizontalOffsetWithoutText.intValue();
                }
                state2.horizontalOffsetWithoutText = Integer.valueOf(iIntValue10);
                if (state.verticalOffsetWithoutText == null) {
                    iIntValue11 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0);
                } else {
                    iIntValue11 = state.verticalOffsetWithoutText.intValue();
                }
                state2.verticalOffsetWithoutText = Integer.valueOf(iIntValue11);
                if (state.horizontalOffsetWithText == null) {
                    iIntValue12 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, state2.horizontalOffsetWithoutText.intValue());
                } else {
                    iIntValue12 = state.horizontalOffsetWithText.intValue();
                }
                state2.horizontalOffsetWithText = Integer.valueOf(iIntValue12);
                if (state.verticalOffsetWithText == null) {
                    iIntValue13 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, state2.verticalOffsetWithoutText.intValue());
                } else {
                    iIntValue13 = state.verticalOffsetWithText.intValue();
                }
                state2.verticalOffsetWithText = Integer.valueOf(iIntValue13);
                if (state.largeFontVerticalOffsetAdjustment == null) {
                    iIntValue14 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_largeFontVerticalOffsetAdjustment, 0);
                } else {
                    iIntValue14 = state.largeFontVerticalOffsetAdjustment.intValue();
                }
                state2.largeFontVerticalOffsetAdjustment = Integer.valueOf(iIntValue14);
                if (state.additionalHorizontalOffset == null) {
                    iIntValue15 = 0;
                } else {
                    iIntValue15 = state.additionalHorizontalOffset.intValue();
                }
                state2.additionalHorizontalOffset = Integer.valueOf(iIntValue15);
                if (state.additionalVerticalOffset == null) {
                    iIntValue16 = 0;
                } else {
                    iIntValue16 = state.additionalVerticalOffset.intValue();
                }
                state2.additionalVerticalOffset = Integer.valueOf(iIntValue16);
                if (state.autoAdjustToWithinGrandparentBounds == null) {
                    zBooleanValue = typedArrayGenerateTypedArray.getBoolean(R.styleable.Badge_autoAdjustToWithinGrandparentBounds, false);
                } else {
                    zBooleanValue = state.autoAdjustToWithinGrandparentBounds.booleanValue();
                }
                state2.autoAdjustToWithinGrandparentBounds = Boolean.valueOf(zBooleanValue);
                typedArrayGenerateTypedArray.recycle();
                if (state.numberLocale == null) {
                    locale = state.numberLocale;
                } else if (Build.VERSION.SDK_INT >= 24) {
                    locale = Locale.getDefault(Locale.Category.FORMAT);
                } else {
                    locale = Locale.getDefault();
                }
                state2.numberLocale = locale;
                this.overridingState = state;
            }
            string2 = state.text;
            state2.text = string2;
            state2.contentDescriptionForText = state.contentDescriptionForText;
            if (state.contentDescriptionNumberless == null) {
                string = context.getString(R.string.mtrl_badge_numberless_content_description);
            } else {
                string = state.contentDescriptionNumberless;
            }
            state2.contentDescriptionNumberless = string;
            if (state.contentDescriptionQuantityStrings == 0) {
                i6 = R.plurals.mtrl_badge_content_description;
            } else {
                i6 = state.contentDescriptionQuantityStrings;
            }
            state2.contentDescriptionQuantityStrings = i6;
            if (state.contentDescriptionExceedsMaxBadgeNumberRes == 0) {
                i7 = R.string.mtrl_exceed_max_badge_number_content_description;
            } else {
                i7 = state.contentDescriptionExceedsMaxBadgeNumberRes;
            }
            state2.contentDescriptionExceedsMaxBadgeNumberRes = i7;
            if (state.isVisible != null) {
                z2 = false;
            }
            state2.isVisible = Boolean.valueOf(z2);
            if (state.maxCharacterCount == -2) {
                i8 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxCharacterCount, -2);
            } else {
                i8 = state.maxCharacterCount;
            }
            state2.maxCharacterCount = i8;
            if (state.maxNumber == -2) {
                i9 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxNumber, -2);
            } else {
                i9 = state.maxNumber;
            }
            state2.maxNumber = i9;
            if (state.badgeShapeAppearanceResId == null) {
                iIntValue = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
            } else {
                iIntValue = state.badgeShapeAppearanceResId.intValue();
            }
            state2.badgeShapeAppearanceResId = Integer.valueOf(iIntValue);
            if (state.badgeShapeAppearanceOverlayResId == null) {
                iIntValue2 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearanceOverlay, 0);
            } else {
                iIntValue2 = state.badgeShapeAppearanceOverlayResId.intValue();
            }
            state2.badgeShapeAppearanceOverlayResId = Integer.valueOf(iIntValue2);
            if (state.badgeWithTextShapeAppearanceResId == null) {
                iIntValue3 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
            } else {
                iIntValue3 = state.badgeWithTextShapeAppearanceResId.intValue();
            }
            state2.badgeWithTextShapeAppearanceResId = Integer.valueOf(iIntValue3);
            if (state.badgeWithTextShapeAppearanceOverlayResId == null) {
                iIntValue4 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearanceOverlay, 0);
            } else {
                iIntValue4 = state.badgeWithTextShapeAppearanceOverlayResId.intValue();
            }
            state2.badgeWithTextShapeAppearanceOverlayResId = Integer.valueOf(iIntValue4);
            if (state.backgroundColor == null) {
                iIntValue5 = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_backgroundColor);
            } else {
                iIntValue5 = state.backgroundColor.intValue();
            }
            state2.backgroundColor = Integer.valueOf(iIntValue5);
            if (state.badgeTextAppearanceResId == null) {
                iIntValue6 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeTextAppearance, R.style.TextAppearance_MaterialComponents_Badge);
            } else {
                iIntValue6 = state.badgeTextAppearanceResId.intValue();
            }
            state2.badgeTextAppearanceResId = Integer.valueOf(iIntValue6);
            if (state.badgeTextColor != null) {
                numValueOf = state.badgeTextColor;
            } else {
                if (typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_badgeTextColor)) {
                    defaultColor = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_badgeTextColor);
                } else {
                    defaultColor = new TextAppearance(context, state2.badgeTextAppearanceResId.intValue()).getTextColor().getDefaultColor();
                }
                numValueOf = Integer.valueOf(defaultColor);
            }
            state2.badgeTextColor = numValueOf;
            if (state.badgeGravity == null) {
                iIntValue7 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_badgeGravity, 8388661);
            } else {
                iIntValue7 = state.badgeGravity.intValue();
            }
            state2.badgeGravity = Integer.valueOf(iIntValue7);
            if (state.badgeHorizontalPadding == null) {
                iIntValue8 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
            } else {
                iIntValue8 = state.badgeHorizontalPadding.intValue();
            }
            state2.badgeHorizontalPadding = Integer.valueOf(iIntValue8);
            if (state.badgeVerticalPadding == null) {
                iIntValue9 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeVerticalPadding, resources.getDimensionPixelSize(R.dimen.m3_badge_with_text_vertical_padding));
            } else {
                iIntValue9 = state.badgeVerticalPadding.intValue();
            }
            state2.badgeVerticalPadding = Integer.valueOf(iIntValue9);
            if (state.horizontalOffsetWithoutText == null) {
                iIntValue10 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0);
            } else {
                iIntValue10 = state.horizontalOffsetWithoutText.intValue();
            }
            state2.horizontalOffsetWithoutText = Integer.valueOf(iIntValue10);
            if (state.verticalOffsetWithoutText == null) {
                iIntValue11 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0);
            } else {
                iIntValue11 = state.verticalOffsetWithoutText.intValue();
            }
            state2.verticalOffsetWithoutText = Integer.valueOf(iIntValue11);
            if (state.horizontalOffsetWithText == null) {
                iIntValue12 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, state2.horizontalOffsetWithoutText.intValue());
            } else {
                iIntValue12 = state.horizontalOffsetWithText.intValue();
            }
            state2.horizontalOffsetWithText = Integer.valueOf(iIntValue12);
            if (state.verticalOffsetWithText == null) {
                iIntValue13 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, state2.verticalOffsetWithoutText.intValue());
            } else {
                iIntValue13 = state.verticalOffsetWithText.intValue();
            }
            state2.verticalOffsetWithText = Integer.valueOf(iIntValue13);
            if (state.largeFontVerticalOffsetAdjustment == null) {
                iIntValue14 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_largeFontVerticalOffsetAdjustment, 0);
            } else {
                iIntValue14 = state.largeFontVerticalOffsetAdjustment.intValue();
            }
            state2.largeFontVerticalOffsetAdjustment = Integer.valueOf(iIntValue14);
            if (state.additionalHorizontalOffset == null) {
                iIntValue15 = 0;
            } else {
                iIntValue15 = state.additionalHorizontalOffset.intValue();
            }
            state2.additionalHorizontalOffset = Integer.valueOf(iIntValue15);
            if (state.additionalVerticalOffset == null) {
                iIntValue16 = 0;
            } else {
                iIntValue16 = state.additionalVerticalOffset.intValue();
            }
            state2.additionalVerticalOffset = Integer.valueOf(iIntValue16);
            if (state.autoAdjustToWithinGrandparentBounds == null) {
                zBooleanValue = typedArrayGenerateTypedArray.getBoolean(R.styleable.Badge_autoAdjustToWithinGrandparentBounds, false);
            } else {
                zBooleanValue = state.autoAdjustToWithinGrandparentBounds.booleanValue();
            }
            state2.autoAdjustToWithinGrandparentBounds = Boolean.valueOf(zBooleanValue);
            typedArrayGenerateTypedArray.recycle();
            if (state.numberLocale == null) {
                locale = state.numberLocale;
            } else if (Build.VERSION.SDK_INT >= 24) {
                locale = Locale.getDefault(Locale.Category.FORMAT);
            } else {
                locale = Locale.getDefault();
            }
            state2.numberLocale = locale;
            this.overridingState = state;
        }
        i5 = state.number;
        state2.number = i5;
        if (state.text != null) {
            if (typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_badgeText)) {
            }
            state2.contentDescriptionForText = state.contentDescriptionForText;
            if (state.contentDescriptionNumberless == null) {
                string = context.getString(R.string.mtrl_badge_numberless_content_description);
            } else {
                string = state.contentDescriptionNumberless;
            }
            state2.contentDescriptionNumberless = string;
            if (state.contentDescriptionQuantityStrings == 0) {
                i6 = R.plurals.mtrl_badge_content_description;
            } else {
                i6 = state.contentDescriptionQuantityStrings;
            }
            state2.contentDescriptionQuantityStrings = i6;
            if (state.contentDescriptionExceedsMaxBadgeNumberRes == 0) {
                i7 = R.string.mtrl_exceed_max_badge_number_content_description;
            } else {
                i7 = state.contentDescriptionExceedsMaxBadgeNumberRes;
            }
            state2.contentDescriptionExceedsMaxBadgeNumberRes = i7;
            if (state.isVisible != null) {
                z2 = false;
            }
            state2.isVisible = Boolean.valueOf(z2);
            if (state.maxCharacterCount == -2) {
                i8 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxCharacterCount, -2);
            } else {
                i8 = state.maxCharacterCount;
            }
            state2.maxCharacterCount = i8;
            if (state.maxNumber == -2) {
                i9 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxNumber, -2);
            } else {
                i9 = state.maxNumber;
            }
            state2.maxNumber = i9;
            if (state.badgeShapeAppearanceResId == null) {
                iIntValue = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
            } else {
                iIntValue = state.badgeShapeAppearanceResId.intValue();
            }
            state2.badgeShapeAppearanceResId = Integer.valueOf(iIntValue);
            if (state.badgeShapeAppearanceOverlayResId == null) {
                iIntValue2 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearanceOverlay, 0);
            } else {
                iIntValue2 = state.badgeShapeAppearanceOverlayResId.intValue();
            }
            state2.badgeShapeAppearanceOverlayResId = Integer.valueOf(iIntValue2);
            if (state.badgeWithTextShapeAppearanceResId == null) {
                iIntValue3 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
            } else {
                iIntValue3 = state.badgeWithTextShapeAppearanceResId.intValue();
            }
            state2.badgeWithTextShapeAppearanceResId = Integer.valueOf(iIntValue3);
            if (state.badgeWithTextShapeAppearanceOverlayResId == null) {
                iIntValue4 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearanceOverlay, 0);
            } else {
                iIntValue4 = state.badgeWithTextShapeAppearanceOverlayResId.intValue();
            }
            state2.badgeWithTextShapeAppearanceOverlayResId = Integer.valueOf(iIntValue4);
            if (state.backgroundColor == null) {
                iIntValue5 = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_backgroundColor);
            } else {
                iIntValue5 = state.backgroundColor.intValue();
            }
            state2.backgroundColor = Integer.valueOf(iIntValue5);
            if (state.badgeTextAppearanceResId == null) {
                iIntValue6 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeTextAppearance, R.style.TextAppearance_MaterialComponents_Badge);
            } else {
                iIntValue6 = state.badgeTextAppearanceResId.intValue();
            }
            state2.badgeTextAppearanceResId = Integer.valueOf(iIntValue6);
            if (state.badgeTextColor != null) {
                numValueOf = state.badgeTextColor;
            } else {
                if (typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_badgeTextColor)) {
                    defaultColor = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_badgeTextColor);
                } else {
                    defaultColor = new TextAppearance(context, state2.badgeTextAppearanceResId.intValue()).getTextColor().getDefaultColor();
                }
                numValueOf = Integer.valueOf(defaultColor);
            }
            state2.badgeTextColor = numValueOf;
            if (state.badgeGravity == null) {
                iIntValue7 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_badgeGravity, 8388661);
            } else {
                iIntValue7 = state.badgeGravity.intValue();
            }
            state2.badgeGravity = Integer.valueOf(iIntValue7);
            if (state.badgeHorizontalPadding == null) {
                iIntValue8 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
            } else {
                iIntValue8 = state.badgeHorizontalPadding.intValue();
            }
            state2.badgeHorizontalPadding = Integer.valueOf(iIntValue8);
            if (state.badgeVerticalPadding == null) {
                iIntValue9 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeVerticalPadding, resources.getDimensionPixelSize(R.dimen.m3_badge_with_text_vertical_padding));
            } else {
                iIntValue9 = state.badgeVerticalPadding.intValue();
            }
            state2.badgeVerticalPadding = Integer.valueOf(iIntValue9);
            if (state.horizontalOffsetWithoutText == null) {
                iIntValue10 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0);
            } else {
                iIntValue10 = state.horizontalOffsetWithoutText.intValue();
            }
            state2.horizontalOffsetWithoutText = Integer.valueOf(iIntValue10);
            if (state.verticalOffsetWithoutText == null) {
                iIntValue11 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0);
            } else {
                iIntValue11 = state.verticalOffsetWithoutText.intValue();
            }
            state2.verticalOffsetWithoutText = Integer.valueOf(iIntValue11);
            if (state.horizontalOffsetWithText == null) {
                iIntValue12 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, state2.horizontalOffsetWithoutText.intValue());
            } else {
                iIntValue12 = state.horizontalOffsetWithText.intValue();
            }
            state2.horizontalOffsetWithText = Integer.valueOf(iIntValue12);
            if (state.verticalOffsetWithText == null) {
                iIntValue13 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, state2.verticalOffsetWithoutText.intValue());
            } else {
                iIntValue13 = state.verticalOffsetWithText.intValue();
            }
            state2.verticalOffsetWithText = Integer.valueOf(iIntValue13);
            if (state.largeFontVerticalOffsetAdjustment == null) {
                iIntValue14 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_largeFontVerticalOffsetAdjustment, 0);
            } else {
                iIntValue14 = state.largeFontVerticalOffsetAdjustment.intValue();
            }
            state2.largeFontVerticalOffsetAdjustment = Integer.valueOf(iIntValue14);
            if (state.additionalHorizontalOffset == null) {
                iIntValue15 = 0;
            } else {
                iIntValue15 = state.additionalHorizontalOffset.intValue();
            }
            state2.additionalHorizontalOffset = Integer.valueOf(iIntValue15);
            if (state.additionalVerticalOffset == null) {
                iIntValue16 = 0;
            } else {
                iIntValue16 = state.additionalVerticalOffset.intValue();
            }
            state2.additionalVerticalOffset = Integer.valueOf(iIntValue16);
            if (state.autoAdjustToWithinGrandparentBounds == null) {
                zBooleanValue = typedArrayGenerateTypedArray.getBoolean(R.styleable.Badge_autoAdjustToWithinGrandparentBounds, false);
            } else {
                zBooleanValue = state.autoAdjustToWithinGrandparentBounds.booleanValue();
            }
            state2.autoAdjustToWithinGrandparentBounds = Boolean.valueOf(zBooleanValue);
            typedArrayGenerateTypedArray.recycle();
            if (state.numberLocale == null) {
                locale = state.numberLocale;
            } else if (Build.VERSION.SDK_INT >= 24) {
                locale = Locale.getDefault(Locale.Category.FORMAT);
            } else {
                locale = Locale.getDefault();
            }
            state2.numberLocale = locale;
            this.overridingState = state;
        }
        string2 = state.text;
        state2.text = string2;
        state2.contentDescriptionForText = state.contentDescriptionForText;
        if (state.contentDescriptionNumberless == null) {
            string = context.getString(R.string.mtrl_badge_numberless_content_description);
        } else {
            string = state.contentDescriptionNumberless;
        }
        state2.contentDescriptionNumberless = string;
        if (state.contentDescriptionQuantityStrings == 0) {
            i6 = R.plurals.mtrl_badge_content_description;
        } else {
            i6 = state.contentDescriptionQuantityStrings;
        }
        state2.contentDescriptionQuantityStrings = i6;
        if (state.contentDescriptionExceedsMaxBadgeNumberRes == 0) {
            i7 = R.string.mtrl_exceed_max_badge_number_content_description;
        } else {
            i7 = state.contentDescriptionExceedsMaxBadgeNumberRes;
        }
        state2.contentDescriptionExceedsMaxBadgeNumberRes = i7;
        if (state.isVisible != null) {
            z2 = false;
        }
        state2.isVisible = Boolean.valueOf(z2);
        if (state.maxCharacterCount == -2) {
            i8 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxCharacterCount, -2);
        } else {
            i8 = state.maxCharacterCount;
        }
        state2.maxCharacterCount = i8;
        if (state.maxNumber == -2) {
            i9 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_maxNumber, -2);
        } else {
            i9 = state.maxNumber;
        }
        state2.maxNumber = i9;
        if (state.badgeShapeAppearanceResId == null) {
            iIntValue = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
        } else {
            iIntValue = state.badgeShapeAppearanceResId.intValue();
        }
        state2.badgeShapeAppearanceResId = Integer.valueOf(iIntValue);
        if (state.badgeShapeAppearanceOverlayResId == null) {
            iIntValue2 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeShapeAppearanceOverlay, 0);
        } else {
            iIntValue2 = state.badgeShapeAppearanceOverlayResId.intValue();
        }
        state2.badgeShapeAppearanceOverlayResId = Integer.valueOf(iIntValue2);
        if (state.badgeWithTextShapeAppearanceResId == null) {
            iIntValue3 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearance, R.style.ShapeAppearance_M3_Sys_Shape_Corner_Full);
        } else {
            iIntValue3 = state.badgeWithTextShapeAppearanceResId.intValue();
        }
        state2.badgeWithTextShapeAppearanceResId = Integer.valueOf(iIntValue3);
        if (state.badgeWithTextShapeAppearanceOverlayResId == null) {
            iIntValue4 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeWithTextShapeAppearanceOverlay, 0);
        } else {
            iIntValue4 = state.badgeWithTextShapeAppearanceOverlayResId.intValue();
        }
        state2.badgeWithTextShapeAppearanceOverlayResId = Integer.valueOf(iIntValue4);
        if (state.backgroundColor == null) {
            iIntValue5 = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_backgroundColor);
        } else {
            iIntValue5 = state.backgroundColor.intValue();
        }
        state2.backgroundColor = Integer.valueOf(iIntValue5);
        if (state.badgeTextAppearanceResId == null) {
            iIntValue6 = typedArrayGenerateTypedArray.getResourceId(R.styleable.Badge_badgeTextAppearance, R.style.TextAppearance_MaterialComponents_Badge);
        } else {
            iIntValue6 = state.badgeTextAppearanceResId.intValue();
        }
        state2.badgeTextAppearanceResId = Integer.valueOf(iIntValue6);
        if (state.badgeTextColor != null) {
            numValueOf = state.badgeTextColor;
        } else {
            if (typedArrayGenerateTypedArray.hasValue(R.styleable.Badge_badgeTextColor)) {
                defaultColor = readColorFromAttributes(context, typedArrayGenerateTypedArray, R.styleable.Badge_badgeTextColor);
            } else {
                defaultColor = new TextAppearance(context, state2.badgeTextAppearanceResId.intValue()).getTextColor().getDefaultColor();
            }
            numValueOf = Integer.valueOf(defaultColor);
        }
        state2.badgeTextColor = numValueOf;
        if (state.badgeGravity == null) {
            iIntValue7 = typedArrayGenerateTypedArray.getInt(R.styleable.Badge_badgeGravity, 8388661);
        } else {
            iIntValue7 = state.badgeGravity.intValue();
        }
        state2.badgeGravity = Integer.valueOf(iIntValue7);
        if (state.badgeHorizontalPadding == null) {
            iIntValue8 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, resources.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
        } else {
            iIntValue8 = state.badgeHorizontalPadding.intValue();
        }
        state2.badgeHorizontalPadding = Integer.valueOf(iIntValue8);
        if (state.badgeVerticalPadding == null) {
            iIntValue9 = typedArrayGenerateTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeVerticalPadding, resources.getDimensionPixelSize(R.dimen.m3_badge_with_text_vertical_padding));
        } else {
            iIntValue9 = state.badgeVerticalPadding.intValue();
        }
        state2.badgeVerticalPadding = Integer.valueOf(iIntValue9);
        if (state.horizontalOffsetWithoutText == null) {
            iIntValue10 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0);
        } else {
            iIntValue10 = state.horizontalOffsetWithoutText.intValue();
        }
        state2.horizontalOffsetWithoutText = Integer.valueOf(iIntValue10);
        if (state.verticalOffsetWithoutText == null) {
            iIntValue11 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0);
        } else {
            iIntValue11 = state.verticalOffsetWithoutText.intValue();
        }
        state2.verticalOffsetWithoutText = Integer.valueOf(iIntValue11);
        if (state.horizontalOffsetWithText == null) {
            iIntValue12 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, state2.horizontalOffsetWithoutText.intValue());
        } else {
            iIntValue12 = state.horizontalOffsetWithText.intValue();
        }
        state2.horizontalOffsetWithText = Integer.valueOf(iIntValue12);
        if (state.verticalOffsetWithText == null) {
            iIntValue13 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, state2.verticalOffsetWithoutText.intValue());
        } else {
            iIntValue13 = state.verticalOffsetWithText.intValue();
        }
        state2.verticalOffsetWithText = Integer.valueOf(iIntValue13);
        if (state.largeFontVerticalOffsetAdjustment == null) {
            iIntValue14 = typedArrayGenerateTypedArray.getDimensionPixelOffset(R.styleable.Badge_largeFontVerticalOffsetAdjustment, 0);
        } else {
            iIntValue14 = state.largeFontVerticalOffsetAdjustment.intValue();
        }
        state2.largeFontVerticalOffsetAdjustment = Integer.valueOf(iIntValue14);
        if (state.additionalHorizontalOffset == null) {
            iIntValue15 = 0;
        } else {
            iIntValue15 = state.additionalHorizontalOffset.intValue();
        }
        state2.additionalHorizontalOffset = Integer.valueOf(iIntValue15);
        if (state.additionalVerticalOffset == null) {
            iIntValue16 = 0;
        } else {
            iIntValue16 = state.additionalVerticalOffset.intValue();
        }
        state2.additionalVerticalOffset = Integer.valueOf(iIntValue16);
        if (state.autoAdjustToWithinGrandparentBounds == null) {
            zBooleanValue = typedArrayGenerateTypedArray.getBoolean(R.styleable.Badge_autoAdjustToWithinGrandparentBounds, false);
        } else {
            zBooleanValue = state.autoAdjustToWithinGrandparentBounds.booleanValue();
        }
        state2.autoAdjustToWithinGrandparentBounds = Boolean.valueOf(zBooleanValue);
        typedArrayGenerateTypedArray.recycle();
        if (state.numberLocale == null) {
            locale = state.numberLocale;
        } else if (Build.VERSION.SDK_INT >= 24) {
            locale = Locale.getDefault(Locale.Category.FORMAT);
        } else {
            locale = Locale.getDefault();
        }
        state2.numberLocale = locale;
        this.overridingState = state;
    }

    private TypedArray generateTypedArray(Context context, @XmlRes int i2, @AttrRes int i3, @StyleRes int i4) {
        AttributeSet attributeSet;
        int styleAttribute;
        if (i2 != 0) {
            AttributeSet drawableXml = DrawableUtils.parseDrawableXml(context, i2, BADGE_RESOURCE_TAG);
            styleAttribute = drawableXml.getStyleAttribute();
            attributeSet = drawableXml;
        } else {
            attributeSet = null;
            styleAttribute = 0;
        }
        return ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Badge, i3, styleAttribute == 0 ? i4 : styleAttribute, new int[0]);
    }

    private static int readColorFromAttributes(Context context, @NonNull TypedArray typedArray, @StyleableRes int i2) {
        return MaterialResources.getColorStateList(context, typedArray, i2).getDefaultColor();
    }

    public void clearNumber() {
        setNumber(-1);
    }

    public void clearText() {
        setText(null);
    }

    @Dimension(unit = 1)
    public int getAdditionalHorizontalOffset() {
        return this.currentState.additionalHorizontalOffset.intValue();
    }

    @Dimension(unit = 1)
    public int getAdditionalVerticalOffset() {
        return this.currentState.additionalVerticalOffset.intValue();
    }

    public int getAlpha() {
        return this.currentState.alpha;
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.currentState.backgroundColor.intValue();
    }

    public int getBadgeGravity() {
        return this.currentState.badgeGravity.intValue();
    }

    @Px
    public int getBadgeHorizontalPadding() {
        return this.currentState.badgeHorizontalPadding.intValue();
    }

    public int getBadgeShapeAppearanceOverlayResId() {
        return this.currentState.badgeShapeAppearanceOverlayResId.intValue();
    }

    public int getBadgeShapeAppearanceResId() {
        return this.currentState.badgeShapeAppearanceResId.intValue();
    }

    @ColorInt
    public int getBadgeTextColor() {
        return this.currentState.badgeTextColor.intValue();
    }

    @Px
    public int getBadgeVerticalPadding() {
        return this.currentState.badgeVerticalPadding.intValue();
    }

    public int getBadgeWithTextShapeAppearanceOverlayResId() {
        return this.currentState.badgeWithTextShapeAppearanceOverlayResId.intValue();
    }

    public int getBadgeWithTextShapeAppearanceResId() {
        return this.currentState.badgeWithTextShapeAppearanceResId.intValue();
    }

    @StringRes
    public int getContentDescriptionExceedsMaxBadgeNumberStringResource() {
        return this.currentState.contentDescriptionExceedsMaxBadgeNumberRes;
    }

    public CharSequence getContentDescriptionForText() {
        return this.currentState.contentDescriptionForText;
    }

    public CharSequence getContentDescriptionNumberless() {
        return this.currentState.contentDescriptionNumberless;
    }

    @PluralsRes
    public int getContentDescriptionQuantityStrings() {
        return this.currentState.contentDescriptionQuantityStrings;
    }

    @Dimension(unit = 1)
    public int getHorizontalOffsetWithText() {
        return this.currentState.horizontalOffsetWithText.intValue();
    }

    @Dimension(unit = 1)
    public int getHorizontalOffsetWithoutText() {
        return this.currentState.horizontalOffsetWithoutText.intValue();
    }

    @Dimension(unit = 1)
    public int getLargeFontVerticalOffsetAdjustment() {
        return this.currentState.largeFontVerticalOffsetAdjustment.intValue();
    }

    public int getMaxCharacterCount() {
        return this.currentState.maxCharacterCount;
    }

    public int getMaxNumber() {
        return this.currentState.maxNumber;
    }

    public int getNumber() {
        return this.currentState.number;
    }

    public Locale getNumberLocale() {
        return this.currentState.numberLocale;
    }

    public State getOverridingState() {
        return this.overridingState;
    }

    public String getText() {
        return this.currentState.text;
    }

    @StyleRes
    public int getTextAppearanceResId() {
        return this.currentState.badgeTextAppearanceResId.intValue();
    }

    @Dimension(unit = 1)
    public int getVerticalOffsetWithText() {
        return this.currentState.verticalOffsetWithText.intValue();
    }

    @Dimension(unit = 1)
    public int getVerticalOffsetWithoutText() {
        return this.currentState.verticalOffsetWithoutText.intValue();
    }

    public boolean hasNumber() {
        return this.currentState.number != -1;
    }

    public boolean hasText() {
        return this.currentState.text != null;
    }

    public boolean isAutoAdjustedToGrandparentBounds() {
        return this.currentState.autoAdjustToWithinGrandparentBounds.booleanValue();
    }

    public boolean isVisible() {
        return this.currentState.isVisible.booleanValue();
    }

    public void setAdditionalHorizontalOffset(@Dimension(unit = 1) int i2) {
        this.overridingState.additionalHorizontalOffset = Integer.valueOf(i2);
        this.currentState.additionalHorizontalOffset = Integer.valueOf(i2);
    }

    public void setAdditionalVerticalOffset(@Dimension(unit = 1) int i2) {
        this.overridingState.additionalVerticalOffset = Integer.valueOf(i2);
        this.currentState.additionalVerticalOffset = Integer.valueOf(i2);
    }

    public void setAlpha(int i2) {
        this.overridingState.alpha = i2;
        this.currentState.alpha = i2;
    }

    public void setAutoAdjustToGrandparentBounds(boolean z2) {
        this.overridingState.autoAdjustToWithinGrandparentBounds = Boolean.valueOf(z2);
        this.currentState.autoAdjustToWithinGrandparentBounds = Boolean.valueOf(z2);
    }

    public void setBackgroundColor(@ColorInt int i2) {
        this.overridingState.backgroundColor = Integer.valueOf(i2);
        this.currentState.backgroundColor = Integer.valueOf(i2);
    }

    public void setBadgeGravity(int i2) {
        this.overridingState.badgeGravity = Integer.valueOf(i2);
        this.currentState.badgeGravity = Integer.valueOf(i2);
    }

    public void setBadgeHorizontalPadding(@Px int i2) {
        this.overridingState.badgeHorizontalPadding = Integer.valueOf(i2);
        this.currentState.badgeHorizontalPadding = Integer.valueOf(i2);
    }

    public void setBadgeShapeAppearanceOverlayResId(int i2) {
        this.overridingState.badgeShapeAppearanceOverlayResId = Integer.valueOf(i2);
        this.currentState.badgeShapeAppearanceOverlayResId = Integer.valueOf(i2);
    }

    public void setBadgeShapeAppearanceResId(int i2) {
        this.overridingState.badgeShapeAppearanceResId = Integer.valueOf(i2);
        this.currentState.badgeShapeAppearanceResId = Integer.valueOf(i2);
    }

    public void setBadgeTextColor(@ColorInt int i2) {
        this.overridingState.badgeTextColor = Integer.valueOf(i2);
        this.currentState.badgeTextColor = Integer.valueOf(i2);
    }

    public void setBadgeVerticalPadding(@Px int i2) {
        this.overridingState.badgeVerticalPadding = Integer.valueOf(i2);
        this.currentState.badgeVerticalPadding = Integer.valueOf(i2);
    }

    public void setBadgeWithTextShapeAppearanceOverlayResId(int i2) {
        this.overridingState.badgeWithTextShapeAppearanceOverlayResId = Integer.valueOf(i2);
        this.currentState.badgeWithTextShapeAppearanceOverlayResId = Integer.valueOf(i2);
    }

    public void setBadgeWithTextShapeAppearanceResId(int i2) {
        this.overridingState.badgeWithTextShapeAppearanceResId = Integer.valueOf(i2);
        this.currentState.badgeWithTextShapeAppearanceResId = Integer.valueOf(i2);
    }

    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(@StringRes int i2) {
        this.overridingState.contentDescriptionExceedsMaxBadgeNumberRes = i2;
        this.currentState.contentDescriptionExceedsMaxBadgeNumberRes = i2;
    }

    public void setContentDescriptionForText(CharSequence charSequence) {
        this.overridingState.contentDescriptionForText = charSequence;
        this.currentState.contentDescriptionForText = charSequence;
    }

    public void setContentDescriptionNumberless(CharSequence charSequence) {
        this.overridingState.contentDescriptionNumberless = charSequence;
        this.currentState.contentDescriptionNumberless = charSequence;
    }

    public void setContentDescriptionQuantityStringsResource(@PluralsRes int i2) {
        this.overridingState.contentDescriptionQuantityStrings = i2;
        this.currentState.contentDescriptionQuantityStrings = i2;
    }

    public void setHorizontalOffsetWithText(@Dimension(unit = 1) int i2) {
        this.overridingState.horizontalOffsetWithText = Integer.valueOf(i2);
        this.currentState.horizontalOffsetWithText = Integer.valueOf(i2);
    }

    public void setHorizontalOffsetWithoutText(@Dimension(unit = 1) int i2) {
        this.overridingState.horizontalOffsetWithoutText = Integer.valueOf(i2);
        this.currentState.horizontalOffsetWithoutText = Integer.valueOf(i2);
    }

    public void setLargeFontVerticalOffsetAdjustment(@Dimension(unit = 1) int i2) {
        this.overridingState.largeFontVerticalOffsetAdjustment = Integer.valueOf(i2);
        this.currentState.largeFontVerticalOffsetAdjustment = Integer.valueOf(i2);
    }

    public void setMaxCharacterCount(int i2) {
        this.overridingState.maxCharacterCount = i2;
        this.currentState.maxCharacterCount = i2;
    }

    public void setMaxNumber(int i2) {
        this.overridingState.maxNumber = i2;
        this.currentState.maxNumber = i2;
    }

    public void setNumber(int i2) {
        this.overridingState.number = i2;
        this.currentState.number = i2;
    }

    public void setNumberLocale(Locale locale) {
        this.overridingState.numberLocale = locale;
        this.currentState.numberLocale = locale;
    }

    public void setText(String str) {
        this.overridingState.text = str;
        this.currentState.text = str;
    }

    public void setTextAppearanceResId(@StyleRes int i2) {
        this.overridingState.badgeTextAppearanceResId = Integer.valueOf(i2);
        this.currentState.badgeTextAppearanceResId = Integer.valueOf(i2);
    }

    public void setVerticalOffsetWithText(@Dimension(unit = 1) int i2) {
        this.overridingState.verticalOffsetWithText = Integer.valueOf(i2);
        this.currentState.verticalOffsetWithText = Integer.valueOf(i2);
    }

    public void setVerticalOffsetWithoutText(@Dimension(unit = 1) int i2) {
        this.overridingState.verticalOffsetWithoutText = Integer.valueOf(i2);
        this.currentState.verticalOffsetWithoutText = Integer.valueOf(i2);
    }

    public void setVisible(boolean z2) {
        this.overridingState.isVisible = Boolean.valueOf(z2);
        this.currentState.isVisible = Boolean.valueOf(z2);
    }
}
