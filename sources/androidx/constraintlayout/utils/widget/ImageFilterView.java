package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ImageFilterView extends AppCompatImageView {
    private float mCrossfade;
    private ImageMatrix mImageMatrix;
    LayerDrawable mLayer;
    Drawable[] mLayers;
    private boolean mOverlay;
    private Path mPath;
    RectF mRect;
    private float mRound;
    private float mRoundPercent;
    ViewOutlineProvider mViewOutlineProvider;

    public static class ImageMatrix {

        /* JADX INFO: renamed from: m, reason: collision with root package name */
        float[] f23m = new float[20];
        ColorMatrix mColorMatrix = new ColorMatrix();
        ColorMatrix mTmpColorMatrix = new ColorMatrix();
        float mBrightness = 1.0f;
        float mSaturation = 1.0f;
        float mContrast = 1.0f;
        float mWarmth = 1.0f;

        private void brightness(float f2) {
            float[] fArr = this.f23m;
            fArr[0] = f2;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = f2;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = f2;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void saturation(float f2) {
            float f3 = 1.0f - f2;
            float f4 = 0.2999f * f3;
            float f5 = 0.587f * f3;
            float f6 = f3 * 0.114f;
            float[] fArr = this.f23m;
            fArr[0] = f4 + f2;
            fArr[1] = f5;
            fArr[2] = f6;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = f4;
            fArr[6] = f5 + f2;
            fArr[7] = f6;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = f4;
            fArr[11] = f5;
            fArr[12] = f6 + f2;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void warmth(float f2) {
            float fLog;
            float fPow;
            float fLog2;
            if (f2 <= 0.0f) {
                f2 = 0.01f;
            }
            float f3 = (5000.0f / f2) / 100.0f;
            if (f3 > 66.0f) {
                double d = f3 - 60.0f;
                fPow = ((float) Math.pow(d, -0.13320475816726685d)) * 329.69873f;
                fLog = ((float) Math.pow(d, 0.07551484555006027d)) * 288.12216f;
            } else {
                fLog = (((float) Math.log(f3)) * 99.4708f) - 161.11957f;
                fPow = 255.0f;
            }
            if (f3 < 66.0f) {
                fLog2 = f3 > 19.0f ? (((float) Math.log(f3 - 10.0f)) * 138.51773f) - 305.0448f : 0.0f;
            } else {
                fLog2 = 255.0f;
            }
            float fMin = Math.min(255.0f, Math.max(fPow, 0.0f));
            float fMin2 = Math.min(255.0f, Math.max(fLog, 0.0f));
            float fMin3 = Math.min(255.0f, Math.max(fLog2, 0.0f));
            float fLog3 = (((float) Math.log(50.0f)) * 99.4708f) - 161.11957f;
            float fLog4 = (((float) Math.log(40.0f)) * 138.51773f) - 305.0448f;
            float fMin4 = Math.min(255.0f, Math.max(255.0f, 0.0f));
            float fMin5 = Math.min(255.0f, Math.max(fLog3, 0.0f));
            float fMin6 = fMin3 / Math.min(255.0f, Math.max(fLog4, 0.0f));
            float[] fArr = this.f23m;
            fArr[0] = fMin / fMin4;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = fMin2 / fMin5;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = fMin6;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        public void updateMatrix(ImageView imageView) {
            boolean z2;
            this.mColorMatrix.reset();
            float f2 = this.mSaturation;
            boolean z3 = true;
            if (f2 != 1.0f) {
                saturation(f2);
                this.mColorMatrix.set(this.f23m);
                z2 = true;
            } else {
                z2 = false;
            }
            float f3 = this.mContrast;
            if (f3 != 1.0f) {
                this.mTmpColorMatrix.setScale(f3, f3, f3, 1.0f);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
                z2 = true;
            }
            float f4 = this.mWarmth;
            if (f4 != 1.0f) {
                warmth(f4);
                this.mTmpColorMatrix.set(this.f23m);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
            } else {
                z3 = z2;
            }
            float f5 = this.mBrightness;
            if (f5 != 1.0f) {
                brightness(f5);
                this.mTmpColorMatrix.set(this.f23m);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
            } else if (!z3) {
                imageView.clearColorFilter();
                return;
            }
            imageView.setColorFilter(new ColorMatrixColorFilter(this.mColorMatrix));
        }
    }

    public ImageFilterView(Context context) {
        super(context);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        init(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ImageFilterView);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ImageFilterView_altSrc);
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ImageFilterView_crossfade) {
                    this.mCrossfade = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                } else if (index == R.styleable.ImageFilterView_warmth) {
                    setWarmth(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_saturation) {
                    setSaturation(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_contrast) {
                    setContrast(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_round) {
                    setRound(typedArrayObtainStyledAttributes.getDimension(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_roundPercent) {
                    setRoundPercent(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_overlay) {
                    setOverlay(typedArrayObtainStyledAttributes.getBoolean(index, this.mOverlay));
                }
            }
            typedArrayObtainStyledAttributes.recycle();
            if (drawable != null) {
                Drawable[] drawableArr = new Drawable[2];
                this.mLayers = drawableArr;
                drawableArr[0] = getDrawable();
                this.mLayers[1] = drawable;
                LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
                this.mLayer = layerDrawable;
                layerDrawable.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
                super.setImageDrawable(this.mLayer);
            }
        }
    }

    private void setOverlay(boolean z2) {
        this.mOverlay = z2;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public float getBrightness() {
        return this.mImageMatrix.mBrightness;
    }

    public float getContrast() {
        return this.mImageMatrix.mContrast;
    }

    public float getCrossfade() {
        return this.mCrossfade;
    }

    public float getRound() {
        return this.mRound;
    }

    public float getRoundPercent() {
        return this.mRoundPercent;
    }

    public float getSaturation() {
        return this.mImageMatrix.mSaturation;
    }

    public float getWarmth() {
        return this.mImageMatrix.mWarmth;
    }

    public void setBrightness(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mBrightness = f2;
        imageMatrix.updateMatrix(this);
    }

    public void setContrast(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mContrast = f2;
        imageMatrix.updateMatrix(this);
    }

    public void setCrossfade(float f2) {
        this.mCrossfade = f2;
        if (this.mLayers != null) {
            if (!this.mOverlay) {
                this.mLayer.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            this.mLayer.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            super.setImageDrawable(this.mLayer);
        }
    }

    @RequiresApi(21)
    public void setRound(float f2) {
        if (Float.isNaN(f2)) {
            this.mRound = f2;
            float f3 = this.mRoundPercent;
            this.mRoundPercent = -1.0f;
            setRoundPercent(f3);
            return;
        }
        boolean z2 = this.mRound != f2;
        this.mRound = f2;
        if (f2 != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (this.mViewOutlineProvider == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.2
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
                    }
                };
                this.mViewOutlineProvider = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
            this.mPath.reset();
            Path path = this.mPath;
            RectF rectF = this.mRect;
            float f4 = this.mRound;
            path.addRoundRect(rectF, f4, f4, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z2) {
            invalidateOutline();
        }
    }

    @RequiresApi(21)
    public void setRoundPercent(float f2) {
        boolean z2 = this.mRoundPercent != f2;
        this.mRoundPercent = f2;
        if (f2 != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (this.mViewOutlineProvider == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        int width = ImageFilterView.this.getWidth();
                        int height = ImageFilterView.this.getHeight();
                        outline.setRoundRect(0, 0, width, height, (ImageFilterView.this.mRoundPercent * Math.min(width, height)) / 2.0f);
                    }
                };
                this.mViewOutlineProvider = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            int width = getWidth();
            int height = getHeight();
            float fMin = (Math.min(width, height) * this.mRoundPercent) / 2.0f;
            this.mRect.set(0.0f, 0.0f, width, height);
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, fMin, fMin, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z2) {
            invalidateOutline();
        }
    }

    public void setSaturation(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mSaturation = f2;
        imageMatrix.updateMatrix(this);
    }

    public void setWarmth(float f2) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mWarmth = f2;
        imageMatrix.updateMatrix(this);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        init(context, attributeSet);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        init(context, attributeSet);
    }
}
