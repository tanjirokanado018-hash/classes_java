package androidx.swiperefreshlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.R;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class CircleImageView extends ImageView {
    private static final int DEFAULT_BACKGROUND_COLOR = -328966;
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final int KEY_SHADOW_COLOR = 503316480;
    private static final int SHADOW_ELEVATION = 4;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 1.75f;
    private int mBackgroundColor;
    private Animation.AnimationListener mListener;
    private int mShadowRadius;

    public static class OvalShadow extends OvalShape {
        private CircleImageView mCircleImageView;
        private Paint mShadowPaint = new Paint();
        private int mShadowRadius;

        public OvalShadow(CircleImageView circleImageView, int i2) {
            this.mCircleImageView = circleImageView;
            this.mShadowRadius = i2;
            updateRadialGradient((int) rect().width());
        }

        private void updateRadialGradient(int i2) {
            float f2 = i2 / 2;
            this.mShadowPaint.setShader(new RadialGradient(f2, f2, this.mShadowRadius, new int[]{CircleImageView.FILL_SHADOW_COLOR, 0}, (float[]) null, Shader.TileMode.CLAMP));
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            int width = this.mCircleImageView.getWidth() / 2;
            float f2 = width;
            float height = this.mCircleImageView.getHeight() / 2;
            canvas.drawCircle(f2, height, f2, this.mShadowPaint);
            canvas.drawCircle(f2, height, width - this.mShadowRadius, paint);
        }

        @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void onResize(float f2, float f3) {
            super.onResize(f2, f3);
            updateRadialGradient((int) f2);
        }
    }

    public CircleImageView(Context context) {
        ShapeDrawable shapeDrawable;
        super(context);
        float f2 = getContext().getResources().getDisplayMetrics().density;
        int i2 = (int) (Y_OFFSET * f2);
        int i3 = (int) (0.0f * f2);
        this.mShadowRadius = (int) (SHADOW_RADIUS * f2);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(R.styleable.SwipeRefreshLayout);
        this.mBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SwipeRefreshLayout_swipeRefreshLayoutProgressSpinnerBackgroundColor, DEFAULT_BACKGROUND_COLOR);
        typedArrayObtainStyledAttributes.recycle();
        if (elevationSupported()) {
            shapeDrawable = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, f2 * 4.0f);
        } else {
            ShapeDrawable shapeDrawable2 = new ShapeDrawable(new OvalShadow(this, this.mShadowRadius));
            setLayerType(1, shapeDrawable2.getPaint());
            shapeDrawable2.getPaint().setShadowLayer(this.mShadowRadius, i3, i2, KEY_SHADOW_COLOR);
            int i4 = this.mShadowRadius;
            setPadding(i4, i4, i4, i4);
            shapeDrawable = shapeDrawable2;
        }
        shapeDrawable.getPaint().setColor(this.mBackgroundColor);
        ViewCompat.setBackground(this, shapeDrawable);
    }

    private boolean elevationSupported() {
        return true;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @Override // android.view.View
    public void onAnimationEnd() {
        super.onAnimationEnd();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationEnd(getAnimation());
        }
    }

    @Override // android.view.View
    public void onAnimationStart() {
        super.onAnimationStart();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationStart(getAnimation());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (elevationSupported()) {
            return;
        }
        setMeasuredDimension((this.mShadowRadius * 2) + getMeasuredWidth(), (this.mShadowRadius * 2) + getMeasuredHeight());
    }

    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i2);
            this.mBackgroundColor = i2;
        }
    }
}
