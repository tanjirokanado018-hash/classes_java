package com.google.android.material.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;
import androidx.transition.PathMotion;
import androidx.transition.PatternPathMotion;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.color.utilities.Contrast;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class TransitionUtils {

    @AttrRes
    static final int NO_ATTR_RES_ID = 0;
    static final int NO_DURATION = -1;
    private static final int PATH_TYPE_ARC = 1;
    private static final int PATH_TYPE_LINEAR = 0;
    private static final RectF transformAlphaRectF = new RectF();

    public interface CornerSizeBinaryOperator {
        @NonNull
        CornerSize apply(@NonNull CornerSize cornerSize, @NonNull CornerSize cornerSize2);
    }

    private TransitionUtils() {
    }

    public static float calculateArea(@NonNull RectF rectF) {
        return rectF.height() * rectF.width();
    }

    public static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel shapeAppearanceModel, final RectF rectF) {
        return shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() { // from class: com.google.android.material.transition.a
            @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
            public final CornerSize apply(CornerSize cornerSize) {
                return RelativeCornerSize.createFromCornerSize(rectF, cornerSize);
            }
        });
    }

    public static Shader createColorShader(@ColorInt int i2) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 0.0f, i2, i2, Shader.TileMode.CLAMP);
    }

    @NonNull
    public static <T> T defaultIfNull(@Nullable T t2, @NonNull T t3) {
        return t2 != null ? t2 : t3;
    }

    public static View findAncestorById(View view, @IdRes int i2) {
        String resourceName = view.getResources().getResourceName(i2);
        while (view != null) {
            if (view.getId() != i2) {
                Object parent = view.getParent();
                if (!(parent instanceof View)) {
                    break;
                }
                view = (View) parent;
            } else {
                return view;
            }
        }
        throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.e(resourceName, " is not a valid ancestor"));
    }

    public static View findDescendantOrAncestorById(View view, @IdRes int i2) {
        View viewFindViewById = view.findViewById(i2);
        return viewFindViewById != null ? viewFindViewById : findAncestorById(view, i2);
    }

    public static RectF getLocationOnScreen(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        return new RectF(i2, i3, view.getWidth() + i2, view.getHeight() + i3);
    }

    public static RectF getRelativeBounds(View view) {
        return new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public static Rect getRelativeBoundsRect(View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel shapeAppearanceModel, RectF rectF) {
        return (shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(rectF) == 0.0f && shapeAppearanceModel.getTopRightCornerSize().getCornerSize(rectF) == 0.0f && shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(rectF) == 0.0f && shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(rectF) == 0.0f) ? false : true;
    }

    public static float lerp(float f2, float f3, float f4) {
        return androidx.appcompat.graphics.drawable.a.a(f3, f2, f4, f2);
    }

    public static void maybeAddTransition(TransitionSet transitionSet, @Nullable Transition transition) {
        if (transition != null) {
            transitionSet.addTransition(transition);
        }
    }

    public static boolean maybeApplyThemeDuration(Transition transition, Context context, @AttrRes int i2) {
        int iResolveThemeDuration;
        if (i2 == 0 || transition.getDuration() != -1 || (iResolveThemeDuration = MotionUtils.resolveThemeDuration(context, i2, -1)) == -1) {
            return false;
        }
        transition.setDuration(iResolveThemeDuration);
        return true;
    }

    public static boolean maybeApplyThemeInterpolator(Transition transition, Context context, @AttrRes int i2, TimeInterpolator timeInterpolator) {
        if (i2 == 0 || transition.getInterpolator() != null) {
            return false;
        }
        transition.setInterpolator(MotionUtils.resolveThemeInterpolator(context, i2, timeInterpolator));
        return true;
    }

    public static boolean maybeApplyThemePath(Transition transition, Context context, @AttrRes int i2) {
        PathMotion pathMotionResolveThemePath;
        if (i2 == 0 || (pathMotionResolveThemePath = resolveThemePath(context, i2)) == null) {
            return false;
        }
        transition.setPathMotion(pathMotionResolveThemePath);
        return true;
    }

    public static void maybeRemoveTransition(TransitionSet transitionSet, @Nullable Transition transition) {
        if (transition != null) {
            transitionSet.removeTransition(transition);
        }
    }

    @Nullable
    public static PathMotion resolveThemePath(Context context, @AttrRes int i2) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i2, typedValue, true)) {
            return null;
        }
        int i3 = typedValue.type;
        if (i3 != 16) {
            if (i3 == 3) {
                return new PatternPathMotion(PathParser.createPathFromPathData(String.valueOf(typedValue.string)));
            }
            throw new IllegalArgumentException("Motion path theme attribute must either be an enum value or path data string");
        }
        int i4 = typedValue.data;
        if (i4 == 0) {
            return null;
        }
        if (i4 == 1) {
            return new MaterialArcMotion();
        }
        throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.c("Invalid motion path type: ", i4));
    }

    private static int saveLayerAlphaCompat(Canvas canvas, Rect rect, int i2) {
        RectF rectF = transformAlphaRectF;
        rectF.set(rect);
        return canvas.saveLayerAlpha(rectF, i2);
    }

    public static void transform(Canvas canvas, Rect rect, float f2, float f3, float f4, int i2, CanvasCompat.CanvasOperation canvasOperation) {
        if (i2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(f2, f3);
        canvas.scale(f4, f4);
        if (i2 < 255) {
            saveLayerAlphaCompat(canvas, rect, i2);
        }
        canvasOperation.run(canvas);
        canvas.restoreToCount(iSave);
    }

    public static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, RectF rectF, CornerSizeBinaryOperator cornerSizeBinaryOperator) {
        return (isShapeAppearanceSignificant(shapeAppearanceModel, rectF) ? shapeAppearanceModel : shapeAppearanceModel2).toBuilder().setTopLeftCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
    }

    public static float lerp(float f2, float f3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f4, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f5, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f6) {
        return lerp(f2, f3, f4, f5, f6, false);
    }

    public static float lerp(float f2, float f3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f4, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f5, @FloatRange(from = 0.0d) float f6, boolean z2) {
        if (z2 && (f6 < 0.0f || f6 > 1.0f)) {
            return lerp(f2, f3, f6);
        }
        if (f6 < f4) {
            return f2;
        }
        return f6 > f5 ? f3 : lerp(f2, f3, (f6 - f4) / (f5 - f4));
    }

    public static int lerp(int i2, int i3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f4) {
        if (f4 < f2) {
            return i2;
        }
        return f4 > f3 ? i3 : (int) lerp(i2, i3, (f4 - f2) / (f3 - f2));
    }

    public static ShapeAppearanceModel lerp(ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, final RectF rectF, final RectF rectF2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) final float f2, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) final float f3, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) final float f4) {
        if (f4 < f2) {
            return shapeAppearanceModel;
        }
        return f4 > f3 ? shapeAppearanceModel2 : transformCornerSizes(shapeAppearanceModel, shapeAppearanceModel2, rectF, new CornerSizeBinaryOperator() { // from class: com.google.android.material.transition.TransitionUtils.1
            @Override // com.google.android.material.transition.TransitionUtils.CornerSizeBinaryOperator
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize, @NonNull CornerSize cornerSize2) {
                return new AbsoluteCornerSize(TransitionUtils.lerp(cornerSize.getCornerSize(rectF), cornerSize2.getCornerSize(rectF2), f2, f3, f4));
            }
        });
    }
}
