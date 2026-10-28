package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.XmlRes;
import androidx.core.R;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import com.google.android.material.color.utilities.Contrast;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class ColorStateListInflaterCompat {
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    private ColorStateListInflaterCompat() {
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(resources, xmlPullParser, attributeSetAsAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @NonNull
    public static ColorStateList createFromXmlInner(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException {
        String name = xmlPullParser.getName();
        if (name.equals("selector")) {
            return inflate(resources, xmlPullParser, attributeSet, theme);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
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
    public static ColorStateList inflate(@NonNull Resources resources, @XmlRes int i2, @Nullable Resources.Theme theme) {
        try {
            return createFromXml(resources, resources.getXml(i2), theme);
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

    @ColorInt
    private static int modulateColorAlpha(@ColorInt int i2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, @FloatRange(from = 0.0d, to = 100.0d) float f3) {
        boolean z2 = f3 >= 0.0f && f3 <= 100.0f;
        if (f2 == 1.0f && !z2) {
            return i2;
        }
        int iClamp = MathUtils.clamp((int) ((Color.alpha(i2) * f2) + 0.5f), 0, 255);
        if (z2) {
            CamColor camColorFromColor = CamColor.fromColor(i2);
            i2 = CamColor.toColor(camColorFromColor.getHue(), camColorFromColor.getChroma(), f3);
        }
        return (i2 & ViewCompat.MEASURED_SIZE_MASK) | (iClamp << 24);
    }

    private static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0093  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a3  */
    private static ColorStateList inflate(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int color;
        int i2;
        int i3;
        int attributeCount;
        int i4;
        int attributeNameResource;
        int i5 = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr = new int[20][];
        int[] iArrAppend = new int[20];
        int i6 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == i5 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.ColorStateListItem);
                int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.ColorStateListItem_android_color, -1);
                if (resourceId == -1 || isColorInt(resources, resourceId)) {
                    color = typedArrayObtainAttributes.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                } else {
                    try {
                        color = createFromXml(resources, resources.getXml(resourceId), theme).getDefaultColor();
                    } catch (Exception unused) {
                        color = typedArrayObtainAttributes.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                    }
                }
                float f2 = 1.0f;
                if (typedArrayObtainAttributes.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                    i2 = R.styleable.ColorStateListItem_android_alpha;
                } else {
                    if (typedArrayObtainAttributes.hasValue(R.styleable.ColorStateListItem_alpha)) {
                        i2 = R.styleable.ColorStateListItem_alpha;
                    }
                    if (Build.VERSION.SDK_INT >= 31 || !typedArrayObtainAttributes.hasValue(R.styleable.ColorStateListItem_android_lStar)) {
                        i3 = R.styleable.ColorStateListItem_lStar;
                    } else {
                        i3 = R.styleable.ColorStateListItem_android_lStar;
                    }
                    float f3 = typedArrayObtainAttributes.getFloat(i3, -1.0f);
                    typedArrayObtainAttributes.recycle();
                    attributeCount = attributeSet.getAttributeCount();
                    int[] iArr2 = new int[attributeCount];
                    int i7 = 0;
                    for (i4 = 0; i4 < attributeCount; i4++) {
                        attributeNameResource = attributeSet.getAttributeNameResource(i4);
                        if (attributeNameResource == 16843173 && attributeNameResource != 16843551 && attributeNameResource != R.attr.alpha && attributeNameResource != R.attr.lStar) {
                            int i8 = i7 + 1;
                            if (!attributeSet.getAttributeBooleanValue(i4, false)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr2[i7] = attributeNameResource;
                            i7 = i8;
                        }
                    }
                    int[] iArrTrimStateSet = StateSet.trimStateSet(iArr2, i7);
                    iArrAppend = GrowingArrayUtils.append(iArrAppend, i6, modulateColorAlpha(color, f2, f3));
                    iArr = (int[][]) GrowingArrayUtils.append(iArr, i6, iArrTrimStateSet);
                    i6++;
                }
                f2 = typedArrayObtainAttributes.getFloat(i2, 1.0f);
                if (Build.VERSION.SDK_INT >= 31) {
                    i3 = R.styleable.ColorStateListItem_lStar;
                } else {
                    i3 = R.styleable.ColorStateListItem_lStar;
                }
                float f4 = typedArrayObtainAttributes.getFloat(i3, -1.0f);
                typedArrayObtainAttributes.recycle();
                attributeCount = attributeSet.getAttributeCount();
                int[] iArr3 = new int[attributeCount];
                int i9 = 0;
                while (i4 < attributeCount) {
                    attributeNameResource = attributeSet.getAttributeNameResource(i4);
                    if (attributeNameResource == 16843173) {
                    }
                }
                int[] iArrTrimStateSet2 = StateSet.trimStateSet(iArr3, i9);
                iArrAppend = GrowingArrayUtils.append(iArrAppend, i6, modulateColorAlpha(color, f2, f4));
                iArr = (int[][]) GrowingArrayUtils.append(iArr, i6, iArrTrimStateSet2);
                i6++;
            }
            i5 = 1;
        }
        int[] iArr4 = new int[i6];
        int[][] iArr5 = new int[i6][];
        System.arraycopy(iArrAppend, 0, iArr4, 0, i6);
        System.arraycopy(iArr, 0, iArr5, 0, i6);
        return new ColorStateList(iArr5, iArr4);
    }
}
