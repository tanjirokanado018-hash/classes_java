package com.google.android.material.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MaterialDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @ColorInt
    private int color;

    @NonNull
    private Drawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private boolean lastItemDecorated;
    private int orientation;
    private final Rect tempRect;
    private int thickness;

    public MaterialDividerItemDecoration(@NonNull Context context, int i2) {
        this(context, null, i2);
    }

    private void drawForHorizontalOrientation(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView) {
        int height;
        int paddingTop;
        int i2;
        int i3;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            paddingTop = recyclerView.getPaddingTop();
            height = recyclerView.getHeight() - recyclerView.getPaddingBottom();
            canvas.clipRect(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getWidth() - recyclerView.getPaddingRight(), height);
        } else {
            height = recyclerView.getHeight();
            paddingTop = 0;
        }
        int i4 = paddingTop + this.insetStart;
        int i5 = height - this.insetEnd;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(recyclerView);
        int childCount = recyclerView.getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = recyclerView.getChildAt(i6);
            if (shouldDrawDivider(recyclerView, childAt)) {
                recyclerView.getLayoutManager().getDecoratedBoundsWithMargins(childAt, this.tempRect);
                int iRound = Math.round(childAt.getTranslationX());
                Rect rect = this.tempRect;
                if (zIsLayoutRtl) {
                    i3 = rect.left + iRound;
                    i2 = this.thickness + i3;
                } else {
                    i2 = iRound + rect.right;
                    i3 = i2 - this.thickness;
                }
                this.dividerDrawable.setBounds(i3, i4, i2, i5);
                this.dividerDrawable.setAlpha(Math.round(childAt.getAlpha() * 255.0f));
                this.dividerDrawable.draw(canvas);
            }
        }
        canvas.restore();
    }

    private void drawForVerticalOrientation(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView) {
        int width;
        int paddingLeft;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            paddingLeft = recyclerView.getPaddingLeft();
            width = recyclerView.getWidth() - recyclerView.getPaddingRight();
            canvas.clipRect(paddingLeft, recyclerView.getPaddingTop(), width, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            width = recyclerView.getWidth();
            paddingLeft = 0;
        }
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(recyclerView);
        int i2 = paddingLeft + (zIsLayoutRtl ? this.insetEnd : this.insetStart);
        int i3 = width - (zIsLayoutRtl ? this.insetStart : this.insetEnd);
        int childCount = recyclerView.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = recyclerView.getChildAt(i4);
            if (shouldDrawDivider(recyclerView, childAt)) {
                recyclerView.getLayoutManager().getDecoratedBoundsWithMargins(childAt, this.tempRect);
                int iRound = Math.round(childAt.getTranslationY()) + this.tempRect.bottom;
                this.dividerDrawable.setBounds(i2, iRound - this.thickness, i3, iRound);
                this.dividerDrawable.setAlpha(Math.round(childAt.getAlpha() * 255.0f));
                this.dividerDrawable.draw(canvas);
            }
        }
        canvas.restore();
    }

    @ColorInt
    public int getDividerColor() {
        return this.color;
    }

    @Px
    public int getDividerInsetEnd() {
        return this.insetEnd;
    }

    @Px
    public int getDividerInsetStart() {
        return this.insetStart;
    }

    @Px
    public int getDividerThickness() {
        return this.thickness;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        rect.set(0, 0, 0, 0);
        if (shouldDrawDivider(recyclerView, view)) {
            if (this.orientation == 1) {
                rect.bottom = this.thickness;
            } else if (ViewUtils.isLayoutRtl(recyclerView)) {
                rect.left = this.thickness;
            } else {
                rect.right = this.thickness;
            }
        }
    }

    public int getOrientation() {
        return this.orientation;
    }

    public boolean isLastItemDecorated() {
        return this.lastItemDecorated;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        if (recyclerView.getLayoutManager() == null) {
            return;
        }
        if (this.orientation == 1) {
            drawForVerticalOrientation(canvas, recyclerView);
        } else {
            drawForHorizontalOrientation(canvas, recyclerView);
        }
    }

    public void setDividerColor(@ColorInt int i2) {
        this.color = i2;
        Drawable drawableWrap = DrawableCompat.wrap(this.dividerDrawable);
        this.dividerDrawable = drawableWrap;
        DrawableCompat.setTint(drawableWrap, i2);
    }

    public void setDividerColorResource(@NonNull Context context, @ColorRes int i2) {
        setDividerColor(ContextCompat.getColor(context, i2));
    }

    public void setDividerInsetEnd(@Px int i2) {
        this.insetEnd = i2;
    }

    public void setDividerInsetEndResource(@NonNull Context context, @DimenRes int i2) {
        setDividerInsetEnd(context.getResources().getDimensionPixelOffset(i2));
    }

    public void setDividerInsetStart(@Px int i2) {
        this.insetStart = i2;
    }

    public void setDividerInsetStartResource(@NonNull Context context, @DimenRes int i2) {
        setDividerInsetStart(context.getResources().getDimensionPixelOffset(i2));
    }

    public void setDividerThickness(@Px int i2) {
        this.thickness = i2;
    }

    public void setDividerThicknessResource(@NonNull Context context, @DimenRes int i2) {
        setDividerThickness(context.getResources().getDimensionPixelSize(i2));
    }

    public void setLastItemDecorated(boolean z2) {
        this.lastItemDecorated = z2;
    }

    public void setOrientation(int i2) {
        if (i2 == 0 || i2 == 1) {
            this.orientation = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid orientation: " + i2 + ". It should be either HORIZONTAL or VERTICAL");
    }

    public boolean shouldDrawDivider(int i2, @Nullable RecyclerView.Adapter<?> adapter) {
        return true;
    }

    public MaterialDividerItemDecoration(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, R.attr.materialDividerStyle, i2);
    }

    private boolean shouldDrawDivider(@NonNull RecyclerView recyclerView, @NonNull View view) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        boolean z2 = adapter != null && childAdapterPosition == adapter.getItemCount() - 1;
        if (childAdapterPosition != -1) {
            return (!z2 || this.lastItemDecorated) && shouldDrawDivider(childAdapterPosition, (RecyclerView.Adapter<?>) adapter);
        }
        return false;
    }

    public MaterialDividerItemDecoration(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        this.tempRect = new Rect();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.MaterialDivider, i2, DEF_STYLE_RES, new int[0]);
        this.color = MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, R.styleable.MaterialDivider_dividerColor).getDefaultColor();
        this.thickness = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialDivider_dividerThickness, context.getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
        this.insetStart = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0);
        this.insetEnd = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0);
        this.lastItemDecorated = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialDivider_lastItemDecorated, true);
        typedArrayObtainStyledAttributes.recycle();
        this.dividerDrawable = new ShapeDrawable();
        setDividerColor(this.color);
        setOrientation(i3);
    }
}
