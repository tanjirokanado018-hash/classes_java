package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.Dimension;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class ClockHandView extends View {
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private boolean animatingOnTouchUp;
    private final int animationDuration;
    private final TimeInterpolator animationInterpolator;
    private final float centerDotRadius;
    private boolean changedDuringTouch;
    private int circleRadius;
    private int currentLevel;
    private double degRad;
    private float downX;
    private float downY;
    private boolean isInTapRegion;
    private boolean isMultiLevel;
    private final List<OnRotateListener> listeners;
    private OnActionUpListener onActionUpListener;
    private float originalDeg;
    private final Paint paint;
    private final ValueAnimator rotationAnimator;
    private final int scaledTouchSlop;
    private final RectF selectorBox;
    private final int selectorRadius;

    @Px
    private final int selectorStrokeWidth;

    public interface OnActionUpListener {
        void onActionUp(@FloatRange(from = 0.0d, to = 360.0d) float f2, boolean z2);
    }

    public interface OnRotateListener {
        void onRotate(@FloatRange(from = 0.0d, to = 360.0d) float f2, boolean z2);
    }

    public ClockHandView(Context context) {
        this(context, null);
    }

    private void adjustLevel(float f2, float f3) {
        this.currentLevel = MathUtils.dist((float) (getWidth() / 2), (float) (getHeight() / 2), f2, f3) > ((float) getLeveledCircleRadius(2)) + ViewUtils.dpToPx(getContext(), 12) ? 1 : 2;
    }

    private void drawSelector(Canvas canvas) {
        int height = getHeight() / 2;
        int width = getWidth() / 2;
        int leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float f2 = width;
        float f3 = leveledCircleRadius;
        float fCos = (((float) Math.cos(this.degRad)) * f3) + f2;
        float f4 = height;
        float fSin = (f3 * ((float) Math.sin(this.degRad))) + f4;
        this.paint.setStrokeWidth(0.0f);
        canvas.drawCircle(fCos, fSin, this.selectorRadius, this.paint);
        double dSin = Math.sin(this.degRad);
        double dCos = Math.cos(this.degRad);
        double d = leveledCircleRadius - this.selectorRadius;
        this.paint.setStrokeWidth(this.selectorStrokeWidth);
        canvas.drawLine(f2, f4, width + ((int) (dCos * d)), height + ((int) (d * dSin)), this.paint);
        canvas.drawCircle(f2, f4, this.centerDotRadius, this.paint);
    }

    private int getDegreesFromXY(float f2, float f3) {
        int degrees = (int) Math.toDegrees(Math.atan2(f3 - (getHeight() / 2), f2 - (getWidth() / 2)));
        int i2 = degrees + 90;
        return i2 < 0 ? degrees + 450 : i2;
    }

    @Dimension
    private int getLeveledCircleRadius(int i2) {
        return i2 == 2 ? Math.round(this.circleRadius * 0.66f) : this.circleRadius;
    }

    private Pair<Float, Float> getValuesForAnimation(float f2) {
        float handRotation = getHandRotation();
        if (Math.abs(handRotation - f2) > 180.0f) {
            if (handRotation > 180.0f && f2 < 180.0f) {
                f2 += 360.0f;
            }
            if (handRotation < 180.0f && f2 > 180.0f) {
                handRotation += 360.0f;
            }
        }
        return new Pair<>(Float.valueOf(handRotation), Float.valueOf(f2));
    }

    private boolean handleTouchInput(float f2, float f3, boolean z2, boolean z3, boolean z4) {
        float degreesFromXY = getDegreesFromXY(f2, f3);
        boolean z5 = false;
        boolean z6 = getHandRotation() != degreesFromXY;
        if (z3 && z6) {
            return true;
        }
        if (!z6 && !z2) {
            return false;
        }
        if (z4 && this.animatingOnTouchUp) {
            z5 = true;
        }
        setHandRotation(degreesFromXY, z5);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setHandRotation$0(ValueAnimator valueAnimator) {
        setHandRotationInternal(((Float) valueAnimator.getAnimatedValue()).floatValue(), true);
    }

    private void setHandRotationInternal(@FloatRange(from = 0.0d, to = 360.0d) float f2, boolean z2) {
        float f3 = f2 % 360.0f;
        this.originalDeg = f3;
        this.degRad = Math.toRadians(f3 - 90.0f);
        int height = getHeight() / 2;
        int width = getWidth() / 2;
        float leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float fCos = (((float) Math.cos(this.degRad)) * leveledCircleRadius) + width;
        float fSin = (leveledCircleRadius * ((float) Math.sin(this.degRad))) + height;
        RectF rectF = this.selectorBox;
        int i2 = this.selectorRadius;
        rectF.set(fCos - i2, fSin - i2, fCos + i2, fSin + i2);
        Iterator<OnRotateListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onRotate(f3, z2);
        }
        invalidate();
    }

    public void addOnRotateListener(OnRotateListener onRotateListener) {
        this.listeners.add(onRotateListener);
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public RectF getCurrentSelectorBox() {
        return this.selectorBox;
    }

    @FloatRange(from = 0.0d, to = 360.0d)
    public float getHandRotation() {
        return this.originalDeg;
    }

    public int getSelectorRadius() {
        return this.selectorRadius;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSelector(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.rotationAnimator.isRunning()) {
            return;
        }
        setHandRotation(getHandRotation());
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        boolean z3;
        boolean z4;
        OnActionUpListener onActionUpListener;
        int actionMasked = motionEvent.getActionMasked();
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (actionMasked != 0) {
            if (actionMasked == 1 || actionMasked == 2) {
                int i2 = (int) (x2 - this.downX);
                int i3 = (int) (y2 - this.downY);
                this.isInTapRegion = (i3 * i3) + (i2 * i2) > this.scaledTouchSlop;
                boolean z5 = this.changedDuringTouch;
                z2 = actionMasked == 1;
                if (this.isMultiLevel) {
                    adjustLevel(x2, y2);
                }
                z3 = z5;
            } else {
                z2 = false;
                z3 = false;
            }
            z4 = false;
        } else {
            this.downX = x2;
            this.downY = y2;
            this.isInTapRegion = true;
            this.changedDuringTouch = false;
            z2 = false;
            z3 = false;
            z4 = true;
        }
        boolean zHandleTouchInput = handleTouchInput(x2, y2, z3, z4, z2) | this.changedDuringTouch;
        this.changedDuringTouch = zHandleTouchInput;
        if (zHandleTouchInput && z2 && (onActionUpListener = this.onActionUpListener) != null) {
            onActionUpListener.onActionUp(getDegreesFromXY(x2, y2), this.isInTapRegion);
        }
        return true;
    }

    public void setAnimateOnTouchUp(boolean z2) {
        this.animatingOnTouchUp = z2;
    }

    public void setCircleRadius(@Dimension int i2) {
        this.circleRadius = i2;
        invalidate();
    }

    public void setCurrentLevel(int i2) {
        this.currentLevel = i2;
        invalidate();
    }

    public void setHandRotation(@FloatRange(from = 0.0d, to = 360.0d) float f2) {
        setHandRotation(f2, false);
    }

    public void setMultiLevel(boolean z2) {
        if (this.isMultiLevel && !z2) {
            this.currentLevel = 1;
        }
        this.isMultiLevel = z2;
        invalidate();
    }

    public void setOnActionUpListener(OnActionUpListener onActionUpListener) {
        this.onActionUpListener = onActionUpListener;
    }

    public ClockHandView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    public void setHandRotation(@FloatRange(from = 0.0d, to = 360.0d) float f2, boolean z2) {
        ValueAnimator valueAnimator = this.rotationAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (!z2) {
            setHandRotationInternal(f2, false);
            return;
        }
        Pair<Float, Float> valuesForAnimation = getValuesForAnimation(f2);
        this.rotationAnimator.setFloatValues(((Float) valuesForAnimation.first).floatValue(), ((Float) valuesForAnimation.second).floatValue());
        this.rotationAnimator.setDuration(this.animationDuration);
        this.rotationAnimator.setInterpolator(this.animationInterpolator);
        this.rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.timepicker.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f145a.lambda$setHandRotation$0(valueAnimator2);
            }
        });
        this.rotationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.timepicker.ClockHandView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                animator.end();
            }
        });
        this.rotationAnimator.start();
    }

    public ClockHandView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.rotationAnimator = new ValueAnimator();
        this.listeners = new ArrayList();
        Paint paint = new Paint();
        this.paint = paint;
        this.selectorBox = new RectF();
        this.currentLevel = 1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClockHandView, i2, R.style.Widget_MaterialComponents_TimePicker_Clock);
        this.animationDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2, 200);
        this.animationInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.circleRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClockHandView_materialCircleRadius, 0);
        this.selectorRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClockHandView_selectorSize, 0);
        Resources resources = getResources();
        this.selectorStrokeWidth = resources.getDimensionPixelSize(R.dimen.material_clock_hand_stroke_width);
        this.centerDotRadius = resources.getDimensionPixelSize(R.dimen.material_clock_hand_center_dot_radius);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.ClockHandView_clockHandColor, 0);
        paint.setAntiAlias(true);
        paint.setColor(color);
        setHandRotation(0.0f);
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ViewCompat.setImportantForAccessibility(this, 2);
        typedArrayObtainStyledAttributes.recycle();
    }
}
