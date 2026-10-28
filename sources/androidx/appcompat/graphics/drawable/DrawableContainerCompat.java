package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DrawableContainerCompat extends Drawable implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_DITHER = true;
    private static final String TAG = "DrawableContainerCompat";
    private Runnable mAnimationRunnable;
    private BlockInvalidateCallback mBlockInvalidateCallback;
    private Drawable mCurrDrawable;
    private DrawableContainerState mDrawableContainerState;
    private long mEnterAnimationEnd;
    private long mExitAnimationEnd;
    private boolean mHasAlpha;
    private Rect mHotspotBounds;
    private Drawable mLastDrawable;
    private boolean mMutated;
    private int mAlpha = 255;
    private int mCurIndex = -1;

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        public static boolean canApplyTheme(Drawable.ConstantState constantState) {
            return constantState.canApplyTheme();
        }

        public static void getOutline(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }

        public static Resources getResources(Resources.Theme theme) {
            return theme.getResources();
        }
    }

    public static class BlockInvalidateCallback implements Drawable.Callback {
        private Drawable.Callback mCallback;

        @Override // android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(@NonNull Drawable drawable) {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.scheduleDrawable(drawable, runnable, j2);
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.unscheduleDrawable(drawable, runnable);
            }
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback;
            this.mCallback = null;
            return callback;
        }

        public BlockInvalidateCallback wrap(Drawable.Callback callback) {
            this.mCallback = callback;
            return this;
        }
    }

    public static abstract class DrawableContainerState extends Drawable.ConstantState {
        boolean mAutoMirrored;
        boolean mCanConstantState;
        int mChangingConfigurations;
        boolean mCheckedConstantSize;
        boolean mCheckedConstantState;
        boolean mCheckedOpacity;
        boolean mCheckedPadding;
        boolean mCheckedStateful;
        int mChildrenChangingConfigurations;
        ColorFilter mColorFilter;
        int mConstantHeight;
        int mConstantMinimumHeight;
        int mConstantMinimumWidth;
        Rect mConstantPadding;
        boolean mConstantSize;
        int mConstantWidth;
        int mDensity;
        boolean mDither;
        SparseArray<Drawable.ConstantState> mDrawableFutures;
        Drawable[] mDrawables;
        int mEnterFadeDuration;
        int mExitFadeDuration;
        boolean mHasColorFilter;
        boolean mHasTintList;
        boolean mHasTintMode;
        int mLayoutDirection;
        boolean mMutated;
        int mNumChildren;
        int mOpacity;
        final DrawableContainerCompat mOwner;
        Resources mSourceRes;
        boolean mStateful;
        ColorStateList mTintList;
        PorterDuff.Mode mTintMode;
        boolean mVariablePadding;

        public DrawableContainerState(DrawableContainerState drawableContainerState, DrawableContainerCompat drawableContainerCompat, Resources resources) {
            this.mVariablePadding = false;
            this.mConstantSize = false;
            this.mDither = DrawableContainerCompat.DEFAULT_DITHER;
            this.mEnterFadeDuration = 0;
            this.mExitFadeDuration = 0;
            this.mOwner = drawableContainerCompat;
            this.mSourceRes = resources != null ? resources : drawableContainerState != null ? drawableContainerState.mSourceRes : null;
            int iResolveDensity = DrawableContainerCompat.resolveDensity(resources, drawableContainerState != null ? drawableContainerState.mDensity : 0);
            this.mDensity = iResolveDensity;
            if (drawableContainerState == null) {
                this.mDrawables = new Drawable[10];
                this.mNumChildren = 0;
                return;
            }
            this.mChangingConfigurations = drawableContainerState.mChangingConfigurations;
            this.mChildrenChangingConfigurations = drawableContainerState.mChildrenChangingConfigurations;
            this.mCheckedConstantState = DrawableContainerCompat.DEFAULT_DITHER;
            this.mCanConstantState = DrawableContainerCompat.DEFAULT_DITHER;
            this.mVariablePadding = drawableContainerState.mVariablePadding;
            this.mConstantSize = drawableContainerState.mConstantSize;
            this.mDither = drawableContainerState.mDither;
            this.mMutated = drawableContainerState.mMutated;
            this.mLayoutDirection = drawableContainerState.mLayoutDirection;
            this.mEnterFadeDuration = drawableContainerState.mEnterFadeDuration;
            this.mExitFadeDuration = drawableContainerState.mExitFadeDuration;
            this.mAutoMirrored = drawableContainerState.mAutoMirrored;
            this.mColorFilter = drawableContainerState.mColorFilter;
            this.mHasColorFilter = drawableContainerState.mHasColorFilter;
            this.mTintList = drawableContainerState.mTintList;
            this.mTintMode = drawableContainerState.mTintMode;
            this.mHasTintList = drawableContainerState.mHasTintList;
            this.mHasTintMode = drawableContainerState.mHasTintMode;
            if (drawableContainerState.mDensity == iResolveDensity) {
                if (drawableContainerState.mCheckedPadding) {
                    this.mConstantPadding = drawableContainerState.mConstantPadding != null ? new Rect(drawableContainerState.mConstantPadding) : null;
                    this.mCheckedPadding = DrawableContainerCompat.DEFAULT_DITHER;
                }
                if (drawableContainerState.mCheckedConstantSize) {
                    this.mConstantWidth = drawableContainerState.mConstantWidth;
                    this.mConstantHeight = drawableContainerState.mConstantHeight;
                    this.mConstantMinimumWidth = drawableContainerState.mConstantMinimumWidth;
                    this.mConstantMinimumHeight = drawableContainerState.mConstantMinimumHeight;
                    this.mCheckedConstantSize = DrawableContainerCompat.DEFAULT_DITHER;
                }
            }
            if (drawableContainerState.mCheckedOpacity) {
                this.mOpacity = drawableContainerState.mOpacity;
                this.mCheckedOpacity = DrawableContainerCompat.DEFAULT_DITHER;
            }
            if (drawableContainerState.mCheckedStateful) {
                this.mStateful = drawableContainerState.mStateful;
                this.mCheckedStateful = DrawableContainerCompat.DEFAULT_DITHER;
            }
            Drawable[] drawableArr = drawableContainerState.mDrawables;
            this.mDrawables = new Drawable[drawableArr.length];
            this.mNumChildren = drawableContainerState.mNumChildren;
            SparseArray<Drawable.ConstantState> sparseArray = drawableContainerState.mDrawableFutures;
            this.mDrawableFutures = sparseArray != null ? sparseArray.clone() : new SparseArray<>(this.mNumChildren);
            int i2 = this.mNumChildren;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable != null) {
                    Drawable.ConstantState constantState = drawable.getConstantState();
                    if (constantState != null) {
                        this.mDrawableFutures.put(i3, constantState);
                    } else {
                        this.mDrawables[i3] = drawableArr[i3];
                    }
                }
            }
        }

        private void createAllFutures() {
            SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
            if (sparseArray != null) {
                int size = sparseArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mDrawables[this.mDrawableFutures.keyAt(i2)] = prepareDrawable(this.mDrawableFutures.valueAt(i2).newDrawable(this.mSourceRes));
                }
                this.mDrawableFutures = null;
            }
        }

        private Drawable prepareDrawable(Drawable drawable) {
            if (Build.VERSION.SDK_INT >= 23) {
                DrawableCompat.setLayoutDirection(drawable, this.mLayoutDirection);
            }
            Drawable drawableMutate = drawable.mutate();
            drawableMutate.setCallback(this.mOwner);
            return drawableMutate;
        }

        public final int addChild(Drawable drawable) {
            int i2 = this.mNumChildren;
            if (i2 >= this.mDrawables.length) {
                growArray(i2, i2 + 10);
            }
            drawable.mutate();
            drawable.setVisible(false, DrawableContainerCompat.DEFAULT_DITHER);
            drawable.setCallback(this.mOwner);
            this.mDrawables[i2] = drawable;
            this.mNumChildren++;
            this.mChildrenChangingConfigurations = drawable.getChangingConfigurations() | this.mChildrenChangingConfigurations;
            invalidateCache();
            this.mConstantPadding = null;
            this.mCheckedPadding = false;
            this.mCheckedConstantSize = false;
            this.mCheckedConstantState = false;
            return i2;
        }

        @RequiresApi(21)
        public final void applyTheme(Resources.Theme theme) {
            if (theme != null) {
                createAllFutures();
                int i2 = this.mNumChildren;
                Drawable[] drawableArr = this.mDrawables;
                for (int i3 = 0; i3 < i2; i3++) {
                    Drawable drawable = drawableArr[i3];
                    if (drawable != null && DrawableCompat.canApplyTheme(drawable)) {
                        DrawableCompat.applyTheme(drawableArr[i3], theme);
                        this.mChildrenChangingConfigurations |= drawableArr[i3].getChangingConfigurations();
                    }
                }
                updateDensity(Api21Impl.getResources(theme));
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @RequiresApi(21)
        public boolean canApplyTheme() {
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable == null) {
                    Drawable.ConstantState constantState = this.mDrawableFutures.get(i3);
                    if (constantState != null && Api21Impl.canApplyTheme(constantState)) {
                        return DrawableContainerCompat.DEFAULT_DITHER;
                    }
                } else if (DrawableCompat.canApplyTheme(drawable)) {
                    return DrawableContainerCompat.DEFAULT_DITHER;
                }
            }
            return false;
        }

        public boolean canConstantState() {
            if (this.mCheckedConstantState) {
                return this.mCanConstantState;
            }
            createAllFutures();
            this.mCheckedConstantState = DrawableContainerCompat.DEFAULT_DITHER;
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].getConstantState() == null) {
                    this.mCanConstantState = false;
                    return false;
                }
            }
            this.mCanConstantState = DrawableContainerCompat.DEFAULT_DITHER;
            return DrawableContainerCompat.DEFAULT_DITHER;
        }

        public final void clearMutated() {
            this.mMutated = false;
        }

        public void computeConstantSize() {
            this.mCheckedConstantSize = DrawableContainerCompat.DEFAULT_DITHER;
            createAllFutures();
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            this.mConstantHeight = -1;
            this.mConstantWidth = -1;
            this.mConstantMinimumHeight = 0;
            this.mConstantMinimumWidth = 0;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                int intrinsicWidth = drawable.getIntrinsicWidth();
                if (intrinsicWidth > this.mConstantWidth) {
                    this.mConstantWidth = intrinsicWidth;
                }
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicHeight > this.mConstantHeight) {
                    this.mConstantHeight = intrinsicHeight;
                }
                int minimumWidth = drawable.getMinimumWidth();
                if (minimumWidth > this.mConstantMinimumWidth) {
                    this.mConstantMinimumWidth = minimumWidth;
                }
                int minimumHeight = drawable.getMinimumHeight();
                if (minimumHeight > this.mConstantMinimumHeight) {
                    this.mConstantMinimumHeight = minimumHeight;
                }
            }
        }

        public final int getCapacity() {
            return this.mDrawables.length;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
        }

        public final Drawable getChild(int i2) {
            int iIndexOfKey;
            Drawable drawable = this.mDrawables[i2];
            if (drawable != null) {
                return drawable;
            }
            SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
            if (sparseArray == null || (iIndexOfKey = sparseArray.indexOfKey(i2)) < 0) {
                return null;
            }
            Drawable drawablePrepareDrawable = prepareDrawable(this.mDrawableFutures.valueAt(iIndexOfKey).newDrawable(this.mSourceRes));
            this.mDrawables[i2] = drawablePrepareDrawable;
            this.mDrawableFutures.removeAt(iIndexOfKey);
            if (this.mDrawableFutures.size() == 0) {
                this.mDrawableFutures = null;
            }
            return drawablePrepareDrawable;
        }

        public final int getChildCount() {
            return this.mNumChildren;
        }

        public final int getConstantHeight() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantHeight;
        }

        public final int getConstantMinimumHeight() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantMinimumHeight;
        }

        public final int getConstantMinimumWidth() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantMinimumWidth;
        }

        public final Rect getConstantPadding() {
            Rect rect = null;
            if (this.mVariablePadding) {
                return null;
            }
            Rect rect2 = this.mConstantPadding;
            if (rect2 != null || this.mCheckedPadding) {
                return rect2;
            }
            createAllFutures();
            Rect rect3 = new Rect();
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].getPadding(rect3)) {
                    if (rect == null) {
                        rect = new Rect(0, 0, 0, 0);
                    }
                    int i4 = rect3.left;
                    if (i4 > rect.left) {
                        rect.left = i4;
                    }
                    int i5 = rect3.top;
                    if (i5 > rect.top) {
                        rect.top = i5;
                    }
                    int i6 = rect3.right;
                    if (i6 > rect.right) {
                        rect.right = i6;
                    }
                    int i7 = rect3.bottom;
                    if (i7 > rect.bottom) {
                        rect.bottom = i7;
                    }
                }
            }
            this.mCheckedPadding = DrawableContainerCompat.DEFAULT_DITHER;
            this.mConstantPadding = rect;
            return rect;
        }

        public final int getConstantWidth() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize();
            }
            return this.mConstantWidth;
        }

        public final int getEnterFadeDuration() {
            return this.mEnterFadeDuration;
        }

        public final int getExitFadeDuration() {
            return this.mExitFadeDuration;
        }

        public final int getOpacity() {
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            createAllFutures();
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            int opacity = i2 > 0 ? drawableArr[0].getOpacity() : -2;
            for (int i3 = 1; i3 < i2; i3++) {
                opacity = Drawable.resolveOpacity(opacity, drawableArr[i3].getOpacity());
            }
            this.mOpacity = opacity;
            this.mCheckedOpacity = DrawableContainerCompat.DEFAULT_DITHER;
            return opacity;
        }

        public void growArray(int i2, int i3) {
            Drawable[] drawableArr = new Drawable[i3];
            Drawable[] drawableArr2 = this.mDrawables;
            if (drawableArr2 != null) {
                System.arraycopy(drawableArr2, 0, drawableArr, 0, i2);
            }
            this.mDrawables = drawableArr;
        }

        public void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }

        public final boolean isConstantSize() {
            return this.mConstantSize;
        }

        public final boolean isStateful() {
            if (this.mCheckedStateful) {
                return this.mStateful;
            }
            createAllFutures();
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            boolean z2 = false;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].isStateful()) {
                    z2 = DrawableContainerCompat.DEFAULT_DITHER;
                    break;
                }
            }
            this.mStateful = z2;
            this.mCheckedStateful = DrawableContainerCompat.DEFAULT_DITHER;
            return z2;
        }

        public void mutate() {
            int i2 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable != null) {
                    drawable.mutate();
                }
            }
            this.mMutated = DrawableContainerCompat.DEFAULT_DITHER;
        }

        public final void setConstantSize(boolean z2) {
            this.mConstantSize = z2;
        }

        public final void setEnterFadeDuration(int i2) {
            this.mEnterFadeDuration = i2;
        }

        public final void setExitFadeDuration(int i2) {
            this.mExitFadeDuration = i2;
        }

        public final boolean setLayoutDirection(int i2, int i3) {
            int i4 = this.mNumChildren;
            Drawable[] drawableArr = this.mDrawables;
            boolean z2 = false;
            for (int i5 = 0; i5 < i4; i5++) {
                Drawable drawable = drawableArr[i5];
                if (drawable != null) {
                    boolean layoutDirection = Build.VERSION.SDK_INT >= 23 ? DrawableCompat.setLayoutDirection(drawable, i2) : false;
                    if (i5 == i3) {
                        z2 = layoutDirection;
                    }
                }
            }
            this.mLayoutDirection = i2;
            return z2;
        }

        public final void setVariablePadding(boolean z2) {
            this.mVariablePadding = z2;
        }

        public final void updateDensity(Resources resources) {
            if (resources != null) {
                this.mSourceRes = resources;
                int iResolveDensity = DrawableContainerCompat.resolveDensity(resources, this.mDensity);
                int i2 = this.mDensity;
                this.mDensity = iResolveDensity;
                if (i2 != iResolveDensity) {
                    this.mCheckedConstantSize = false;
                    this.mCheckedPadding = false;
                }
            }
        }
    }

    private void initializeDrawableForDisplay(Drawable drawable) {
        if (this.mBlockInvalidateCallback == null) {
            this.mBlockInvalidateCallback = new BlockInvalidateCallback();
        }
        drawable.setCallback(this.mBlockInvalidateCallback.wrap(drawable.getCallback()));
        try {
            if (this.mDrawableContainerState.mEnterFadeDuration <= 0 && this.mHasAlpha) {
                drawable.setAlpha(this.mAlpha);
            }
            DrawableContainerState drawableContainerState = this.mDrawableContainerState;
            if (drawableContainerState.mHasColorFilter) {
                drawable.setColorFilter(drawableContainerState.mColorFilter);
            } else {
                if (drawableContainerState.mHasTintList) {
                    DrawableCompat.setTintList(drawable, drawableContainerState.mTintList);
                }
                DrawableContainerState drawableContainerState2 = this.mDrawableContainerState;
                if (drawableContainerState2.mHasTintMode) {
                    DrawableCompat.setTintMode(drawable, drawableContainerState2.mTintMode);
                }
            }
            drawable.setVisible(isVisible(), DEFAULT_DITHER);
            drawable.setDither(this.mDrawableContainerState.mDither);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
            if (Build.VERSION.SDK_INT >= 23) {
                DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            }
            DrawableCompat.setAutoMirrored(drawable, this.mDrawableContainerState.mAutoMirrored);
            Rect rect = this.mHotspotBounds;
            if (rect != null) {
                DrawableCompat.setHotspotBounds(drawable, rect.left, rect.top, rect.right, rect.bottom);
            }
        } finally {
            drawable.setCallback(this.mBlockInvalidateCallback.unwrap());
        }
    }

    private boolean needsMirroring() {
        if (isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1) {
            return DEFAULT_DITHER;
        }
        return false;
    }

    public static int resolveDensity(@Nullable Resources resources, int i2) {
        if (resources != null) {
            i2 = resources.getDisplayMetrics().densityDpi;
        }
        if (i2 == 0) {
            return 160;
        }
        return i2;
    }

    public void animate(boolean z2) {
        boolean z3;
        boolean z4 = DEFAULT_DITHER;
        this.mHasAlpha = DEFAULT_DITHER;
        long jUptimeMillis = SystemClock.uptimeMillis();
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            long j2 = this.mEnterAnimationEnd;
            if (j2 == 0) {
                z3 = false;
            } else if (j2 <= jUptimeMillis) {
                drawable.setAlpha(this.mAlpha);
                this.mEnterAnimationEnd = 0L;
                z3 = false;
            } else {
                drawable.setAlpha(((255 - (((int) ((j2 - jUptimeMillis) * 255)) / this.mDrawableContainerState.mEnterFadeDuration)) * this.mAlpha) / 255);
                z3 = DEFAULT_DITHER;
            }
        } else {
            this.mEnterAnimationEnd = 0L;
            z3 = false;
        }
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            long j3 = this.mExitAnimationEnd;
            if (j3 == 0) {
                z4 = z3;
            } else if (j3 <= jUptimeMillis) {
                drawable2.setVisible(false, false);
                this.mLastDrawable = null;
                this.mExitAnimationEnd = 0L;
                z4 = z3;
            } else {
                drawable2.setAlpha(((((int) ((j3 - jUptimeMillis) * 255)) / this.mDrawableContainerState.mExitFadeDuration) * this.mAlpha) / 255);
            }
        } else {
            this.mExitAnimationEnd = 0L;
            z4 = z3;
        }
        if (z2 && z4) {
            scheduleSelf(this.mAnimationRunnable, jUptimeMillis + 16);
        }
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void applyTheme(@NonNull Resources.Theme theme) {
        this.mDrawableContainerState.applyTheme(theme);
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public boolean canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme();
    }

    public void clearMutated() {
        this.mDrawableContainerState.clearMutated();
        this.mMutated = false;
    }

    public DrawableContainerState cloneConstantState() {
        return this.mDrawableContainerState;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (!this.mDrawableContainerState.canConstantState()) {
            return null;
        }
        this.mDrawableContainerState.mChangingConfigurations = getChangingConfigurations();
        return this.mDrawableContainerState;
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Drawable getCurrent() {
        return this.mCurrDrawable;
    }

    public int getCurrentIndex() {
        return this.mCurIndex;
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(@NonNull Rect rect) {
        Rect rect2 = this.mHotspotBounds;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth();
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.mCurrDrawable;
        if (drawable == null || !drawable.isVisible()) {
            return -2;
        }
        return this.mDrawableContainerState.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            Api21Impl.getOutline(drawable, outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        boolean padding;
        Rect constantPadding = this.mDrawableContainerState.getConstantPadding();
        if (constantPadding != null) {
            rect.set(constantPadding);
            padding = (constantPadding.right | ((constantPadding.left | constantPadding.top) | constantPadding.bottom)) != 0 ? DEFAULT_DITHER : false;
        } else {
            Drawable drawable = this.mCurrDrawable;
            padding = drawable != null ? drawable.getPadding(rect) : super.getPadding(rect);
        }
        if (needsMirroring()) {
            int i2 = rect.left;
            rect.left = rect.right;
            rect.right = i2;
        }
        return padding;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NonNull Drawable drawable) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState != null) {
            drawableContainerState.invalidateCache();
        }
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mDrawableContainerState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mDrawableContainerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        boolean z2;
        Drawable drawable = this.mLastDrawable;
        boolean z3 = DEFAULT_DITHER;
        if (drawable != null) {
            drawable.jumpToCurrentState();
            this.mLastDrawable = null;
            z2 = DEFAULT_DITHER;
        } else {
            z2 = false;
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha);
            }
        }
        if (this.mExitAnimationEnd != 0) {
            this.mExitAnimationEnd = 0L;
        } else {
            z3 = z2;
        }
        if (this.mEnterAnimationEnd != 0) {
            this.mEnterAnimationEnd = 0L;
        } else if (!z3) {
            return;
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableContainerState drawableContainerStateCloneConstantState = cloneConstantState();
            drawableContainerStateCloneConstantState.mutate();
            setConstantState(drawableContainerStateCloneConstantState);
            this.mMutated = DEFAULT_DITHER;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i2) {
        return this.mDrawableContainerState.setLayoutDirection(i2, getCurrentIndex());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLevelChange(int i2) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            return drawable.setLevel(i2);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setLevel(i2);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(@NonNull int[] iArr) {
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setState(iArr);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j2) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().scheduleDrawable(this, runnable, j2);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0055  */
    public boolean selectDrawable(int i2) {
        if (i2 == this.mCurIndex) {
            return false;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.mDrawableContainerState.mExitFadeDuration > 0) {
            Drawable drawable = this.mLastDrawable;
            if (drawable != null) {
                drawable.setVisible(false, false);
            }
            Drawable drawable2 = this.mCurrDrawable;
            if (drawable2 != null) {
                this.mLastDrawable = drawable2;
                this.mExitAnimationEnd = ((long) this.mDrawableContainerState.mExitFadeDuration) + jUptimeMillis;
            } else {
                this.mLastDrawable = null;
                this.mExitAnimationEnd = 0L;
            }
        } else {
            Drawable drawable3 = this.mCurrDrawable;
            if (drawable3 != null) {
                drawable3.setVisible(false, false);
            }
        }
        if (i2 >= 0) {
            DrawableContainerState drawableContainerState = this.mDrawableContainerState;
            if (i2 < drawableContainerState.mNumChildren) {
                Drawable child = drawableContainerState.getChild(i2);
                this.mCurrDrawable = child;
                this.mCurIndex = i2;
                if (child != null) {
                    int i3 = this.mDrawableContainerState.mEnterFadeDuration;
                    if (i3 > 0) {
                        this.mEnterAnimationEnd = jUptimeMillis + ((long) i3);
                    }
                    initializeDrawableForDisplay(child);
                }
            } else {
                this.mCurrDrawable = null;
                this.mCurIndex = -1;
            }
        } else {
            this.mCurrDrawable = null;
            this.mCurIndex = -1;
        }
        if (this.mEnterAnimationEnd != 0 || this.mExitAnimationEnd != 0) {
            Runnable runnable = this.mAnimationRunnable;
            if (runnable == null) {
                this.mAnimationRunnable = new Runnable() { // from class: androidx.appcompat.graphics.drawable.DrawableContainerCompat.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DrawableContainerCompat.this.animate(DrawableContainerCompat.DEFAULT_DITHER);
                        DrawableContainerCompat.this.invalidateSelf();
                    }
                };
            } else {
                unscheduleSelf(runnable);
            }
            animate(DEFAULT_DITHER);
        }
        invalidateSelf();
        return DEFAULT_DITHER;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        if (this.mHasAlpha && this.mAlpha == i2) {
            return;
        }
        this.mHasAlpha = DEFAULT_DITHER;
        this.mAlpha = i2;
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            if (this.mEnterAnimationEnd == 0) {
                drawable.setAlpha(i2);
            } else {
                animate(false);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z2) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState.mAutoMirrored != z2) {
            drawableContainerState.mAutoMirrored = z2;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                DrawableCompat.setAutoMirrored(drawable, z2);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.mHasColorFilter = DEFAULT_DITHER;
        if (drawableContainerState.mColorFilter != colorFilter) {
            drawableContainerState.mColorFilter = colorFilter;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public void setConstantState(DrawableContainerState drawableContainerState) {
        this.mDrawableContainerState = drawableContainerState;
        int i2 = this.mCurIndex;
        if (i2 >= 0) {
            Drawable child = drawableContainerState.getChild(i2);
            this.mCurrDrawable = child;
            if (child != null) {
                initializeDrawableForDisplay(child);
            }
        }
        this.mLastDrawable = null;
    }

    public void setCurrentIndex(int i2) {
        selectDrawable(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z2) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState.mDither != z2) {
            drawableContainerState.mDither = z2;
            Drawable drawable = this.mCurrDrawable;
            if (drawable != null) {
                drawable.setDither(z2);
            }
        }
    }

    public void setEnterFadeDuration(int i2) {
        this.mDrawableContainerState.mEnterFadeDuration = i2;
    }

    public void setExitFadeDuration(int i2) {
        this.mDrawableContainerState.mExitFadeDuration = i2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f2, float f3) {
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, f2, f3);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i2, int i3, int i4, int i5) {
        Rect rect = this.mHotspotBounds;
        if (rect == null) {
            this.mHotspotBounds = new Rect(i2, i3, i4, i5);
        } else {
            rect.set(i2, i3, i4, i5);
        }
        Drawable drawable = this.mCurrDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspotBounds(drawable, i2, i3, i4, i5);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(@ColorInt int i2) {
        setTintList(ColorStateList.valueOf(i2));
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.mHasTintList = DEFAULT_DITHER;
        if (drawableContainerState.mTintList != colorStateList) {
            drawableContainerState.mTintList = colorStateList;
            DrawableCompat.setTintList(this.mCurrDrawable, colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        drawableContainerState.mHasTintMode = DEFAULT_DITHER;
        if (drawableContainerState.mTintMode != mode) {
            drawableContainerState.mTintMode = mode;
            DrawableCompat.setTintMode(this.mCurrDrawable, mode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z2, boolean z3) {
        boolean visible = super.setVisible(z2, z3);
        Drawable drawable = this.mLastDrawable;
        if (drawable != null) {
            drawable.setVisible(z2, z3);
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setVisible(z2, z3);
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().unscheduleDrawable(this, runnable);
    }

    public final void updateDensity(Resources resources) {
        this.mDrawableContainerState.updateDensity(resources);
    }
}
