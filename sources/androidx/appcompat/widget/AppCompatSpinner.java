package androidx.appcompat.widget;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.annotation.DoNotInline;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleableRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.TintableBackgroundView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class AppCompatSpinner extends Spinner implements TintableBackgroundView {

    @SuppressLint({"ResourceType"})
    @StyleableRes
    private static final int[] ATTRS_ANDROID_SPINNERMODE = {R.attr.spinnerMode};
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    private SpinnerPopup mPopup;
    private final Context mPopupContext;
    private final boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    final Rect mTempRect;

    @RequiresApi(23)
    public static final class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static void setDropDownViewTheme(@NonNull android.widget.ThemedSpinnerAdapter themedSpinnerAdapter, @Nullable Resources.Theme theme) {
            if (ObjectsCompat.equals(themedSpinnerAdapter.getDropDownViewTheme(), theme)) {
                return;
            }
            themedSpinnerAdapter.setDropDownViewTheme(theme);
        }
    }

    @VisibleForTesting
    public class DialogPopup implements SpinnerPopup, DialogInterface.OnClickListener {
        private ListAdapter mListAdapter;

        @VisibleForTesting
        AlertDialog mPopup;
        private CharSequence mPrompt;

        public DialogPopup() {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void dismiss() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mPopup = null;
            }
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public Drawable getBackground() {
            return null;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public CharSequence getHintText() {
            return this.mPrompt;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public int getHorizontalOffset() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public int getHorizontalOriginalOffset() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public int getVerticalOffset() {
            return 0;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public boolean isShowing() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                return alertDialog.isShowing();
            }
            return false;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i2) {
            AppCompatSpinner.this.setSelection(i2);
            if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                AppCompatSpinner.this.performItemClick(null, i2, this.mListAdapter.getItemId(i2));
            }
            dismiss();
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setAdapter(ListAdapter listAdapter) {
            this.mListAdapter = listAdapter;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setBackgroundDrawable(Drawable drawable) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setHorizontalOffset(int i2) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setHorizontalOriginalOffset(int i2) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setPromptText(CharSequence charSequence) {
            this.mPrompt = charSequence;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setVerticalOffset(int i2) {
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void show(int i2, int i3) {
            if (this.mListAdapter == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(AppCompatSpinner.this.getPopupContext());
            CharSequence charSequence = this.mPrompt;
            if (charSequence != null) {
                builder.setTitle(charSequence);
            }
            AlertDialog alertDialogCreate = builder.setSingleChoiceItems(this.mListAdapter, AppCompatSpinner.this.getSelectedItemPosition(), this).create();
            this.mPopup = alertDialogCreate;
            ListView listView = alertDialogCreate.getListView();
            listView.setTextDirection(i2);
            listView.setTextAlignment(i3);
            this.mPopup.show();
        }
    }

    public static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) spinnerAdapter;
            }
            if (theme != null) {
                if (Build.VERSION.SDK_INT >= 23 && a.ac(spinnerAdapter)) {
                    Api23Impl.setDropDownViewTheme(a.z(spinnerAdapter), theme);
                } else if (spinnerAdapter instanceof ThemedSpinnerAdapter) {
                    ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
                    if (themedSpinnerAdapter.getDropDownViewTheme() == null) {
                        themedSpinnerAdapter.setDropDownViewTheme(theme);
                    }
                }
            }
        }

        @Override // android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        @Override // android.widget.SpinnerAdapter
        public View getDropDownView(int i2, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i2, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public Object getItem(int i2) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(i2);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return -1L;
            }
            return spinnerAdapter.getItemId(i2);
        }

        @Override // android.widget.Adapter
        public int getItemViewType(int i2) {
            return 0;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            return getDropDownView(i2, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.Adapter
        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        @Override // android.widget.Adapter
        public boolean isEmpty() {
            return getCount() == 0;
        }

        @Override // android.widget.ListAdapter
        public boolean isEnabled(int i2) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i2);
            }
            return true;
        }

        @Override // android.widget.Adapter
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override // android.widget.Adapter
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    @VisibleForTesting
    public class DropdownPopup extends ListPopupWindow implements SpinnerPopup {
        ListAdapter mAdapter;
        private CharSequence mHintText;
        private int mOriginalHorizontalOffset;
        private final Rect mVisibleRect;

        public DropdownPopup(Context context, AttributeSet attributeSet, int i2) {
            super(context, attributeSet, i2);
            this.mVisibleRect = new Rect();
            setAnchorView(AppCompatSpinner.this);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.DropdownPopup.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j2) {
                    AppCompatSpinner.this.setSelection(i3);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        DropdownPopup dropdownPopup = DropdownPopup.this;
                        AppCompatSpinner.this.performItemClick(view, i3, dropdownPopup.mAdapter.getItemId(i3));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        /* JADX WARN: Code duplicated, block: B:22:0x008d  */
        /* JADX WARN: Code duplicated, block: B:23:0x009a  */
        public void computeContentWidth() {
            int i2;
            int iMax;
            int horizontalOriginalOffset;
            Drawable background = getBackground();
            if (background != null) {
                background.getPadding(AppCompatSpinner.this.mTempRect);
                i2 = ViewUtils.isLayoutRtl(AppCompatSpinner.this) ? AppCompatSpinner.this.mTempRect.right : -AppCompatSpinner.this.mTempRect.left;
            } else {
                Rect rect = AppCompatSpinner.this.mTempRect;
                rect.right = 0;
                rect.left = 0;
                i2 = 0;
            }
            int paddingLeft = AppCompatSpinner.this.getPaddingLeft();
            int paddingRight = AppCompatSpinner.this.getPaddingRight();
            int width = AppCompatSpinner.this.getWidth();
            AppCompatSpinner appCompatSpinner = AppCompatSpinner.this;
            int i3 = appCompatSpinner.mDropDownWidth;
            if (i3 != -2) {
                if (i3 == -1) {
                    iMax = (width - paddingLeft) - paddingRight;
                } else {
                    setContentWidth(i3);
                }
                if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
                    horizontalOriginalOffset = (((width - paddingRight) - getWidth()) - getHorizontalOriginalOffset()) + i2;
                } else {
                    horizontalOriginalOffset = paddingLeft + getHorizontalOriginalOffset() + i2;
                }
                setHorizontalOffset(horizontalOriginalOffset);
            }
            int iCompatMeasureContentWidth = appCompatSpinner.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, getBackground());
            int i4 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels;
            Rect rect2 = AppCompatSpinner.this.mTempRect;
            int i5 = (i4 - rect2.left) - rect2.right;
            if (iCompatMeasureContentWidth > i5) {
                iCompatMeasureContentWidth = i5;
            }
            iMax = Math.max(iCompatMeasureContentWidth, (width - paddingLeft) - paddingRight);
            setContentWidth(iMax);
            if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
                horizontalOriginalOffset = (((width - paddingRight) - getWidth()) - getHorizontalOriginalOffset()) + i2;
            } else {
                horizontalOriginalOffset = paddingLeft + getHorizontalOriginalOffset() + i2;
            }
            setHorizontalOffset(horizontalOriginalOffset);
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public CharSequence getHintText() {
            return this.mHintText;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public int getHorizontalOriginalOffset() {
            return this.mOriginalHorizontalOffset;
        }

        public boolean isVisibleToUser(View view) {
            return view.isAttachedToWindow() && view.getGlobalVisibleRect(this.mVisibleRect);
        }

        @Override // androidx.appcompat.widget.ListPopupWindow, androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setHorizontalOriginalOffset(int i2) {
            this.mOriginalHorizontalOffset = i2;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        @Override // androidx.appcompat.widget.AppCompatSpinner.SpinnerPopup
        public void show(int i2, int i3) {
            ViewTreeObserver viewTreeObserver;
            boolean zIsShowing = isShowing();
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            ListView listView = getListView();
            listView.setChoiceMode(1);
            listView.setTextDirection(i2);
            listView.setTextAlignment(i3);
            setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (zIsShowing || (viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver()) == null) {
                return;
            }
            final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.DropdownPopup.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    DropdownPopup dropdownPopup = DropdownPopup.this;
                    if (!dropdownPopup.isVisibleToUser(AppCompatSpinner.this)) {
                        DropdownPopup.this.dismiss();
                    } else {
                        DropdownPopup.this.computeContentWidth();
                        DropdownPopup.super.show();
                    }
                }
            };
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
            setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.DropdownPopup.3
                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    ViewTreeObserver viewTreeObserver2 = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver2 != null) {
                        viewTreeObserver2.removeGlobalOnLayoutListener(onGlobalLayoutListener);
                    }
                }
            });
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<AppCompatSpinner> {
        private int mBackgroundTintId;
        private int mBackgroundTintModeId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(@NonNull PropertyMapper propertyMapper) {
            this.mBackgroundTintId = propertyMapper.mapObject("backgroundTint", androidx.appcompat.R.attr.backgroundTint);
            this.mBackgroundTintModeId = propertyMapper.mapObject("backgroundTintMode", androidx.appcompat.R.attr.backgroundTintMode);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(@NonNull AppCompatSpinner appCompatSpinner, @NonNull PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw androidx.activity.f.f();
            }
            propertyReader.readObject(this.mBackgroundTintId, appCompatSpinner.getBackgroundTintList());
            propertyReader.readObject(this.mBackgroundTintModeId, appCompatSpinner.getBackgroundTintMode());
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.appcompat.widget.AppCompatSpinner.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };
        boolean mShowDropdown;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mShowDropdown = parcel.readByte() != 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeByte(this.mShowDropdown ? (byte) 1 : (byte) 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    @VisibleForTesting
    public interface SpinnerPopup {
        void dismiss();

        Drawable getBackground();

        CharSequence getHintText();

        int getHorizontalOffset();

        int getHorizontalOriginalOffset();

        int getVerticalOffset();

        boolean isShowing();

        void setAdapter(ListAdapter listAdapter);

        void setBackgroundDrawable(Drawable drawable);

        void setHorizontalOffset(int i2);

        void setHorizontalOriginalOffset(int i2);

        void setPromptText(CharSequence charSequence);

        void setVerticalOffset(int i2);

        void show(int i2, int i3);
    }

    public AppCompatSpinner(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i2 = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int iMax = Math.max(0, getSelectedItemPosition());
        int iMin = Math.min(spinnerAdapter.getCount(), iMax + 15);
        View view = null;
        int iMax2 = 0;
        for (int iMax3 = Math.max(0, iMax - (15 - (iMin - iMax))); iMax3 < iMin; iMax3++) {
            int itemViewType = spinnerAdapter.getItemViewType(iMax3);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            view = spinnerAdapter.getView(iMax3, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax2 = Math.max(iMax2, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return iMax2;
        }
        drawable.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        return iMax2 + rect.left + rect.right;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.applySupportBackgroundTint();
        }
    }

    @Override // android.widget.Spinner
    public int getDropDownHorizontalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHorizontalOffset() : super.getDropDownHorizontalOffset();
    }

    @Override // android.widget.Spinner
    public int getDropDownVerticalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getVerticalOffset() : super.getDropDownVerticalOffset();
    }

    @Override // android.widget.Spinner
    public int getDropDownWidth() {
        return this.mPopup != null ? this.mDropDownWidth : super.getDropDownWidth();
    }

    @VisibleForTesting
    public final SpinnerPopup getInternalPopup() {
        return this.mPopup;
    }

    @Override // android.widget.Spinner
    public Drawable getPopupBackground() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getBackground() : super.getPopupBackground();
    }

    @Override // android.widget.Spinner
    public Context getPopupContext() {
        return this.mPopupContext;
    }

    @Override // android.widget.Spinner
    public CharSequence getPrompt() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHintText() : super.getPrompt();
    }

    @Override // androidx.core.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ColorStateList getSupportBackgroundTintList() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Override // androidx.core.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null || !spinnerPopup.isShowing()) {
            return;
        }
        this.mPopup.dismiss();
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.mPopup == null || View.MeasureSpec.getMode(i2) != Integer.MIN_VALUE) {
            return;
        }
        setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(i2)), getMeasuredHeight());
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        ViewTreeObserver viewTreeObserver;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (!savedState.mShowDropdown || (viewTreeObserver = getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.AppCompatSpinner.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (!AppCompatSpinner.this.getInternalPopup().isShowing()) {
                    AppCompatSpinner.this.showPopup();
                }
                ViewTreeObserver viewTreeObserver2 = AppCompatSpinner.this.getViewTreeObserver();
                if (viewTreeObserver2 != null) {
                    viewTreeObserver2.removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SpinnerPopup spinnerPopup = this.mPopup;
        savedState.mShowDropdown = spinnerPopup != null && spinnerPopup.isShowing();
        return savedState;
    }

    @Override // android.widget.Spinner, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ForwardingListener forwardingListener = this.mForwardingListener;
        if (forwardingListener == null || !forwardingListener.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.Spinner, android.view.View
    public boolean performClick() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) {
            return super.performClick();
        }
        if (spinnerPopup.isShowing()) {
            return true;
        }
        showPopup();
        return true;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundDrawable(drawable);
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@DrawableRes int i2) {
        super.setBackgroundResource(i2);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundResource(i2);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownHorizontalOffset(int i2) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) {
            super.setDropDownHorizontalOffset(i2);
        } else {
            spinnerPopup.setHorizontalOriginalOffset(i2);
            this.mPopup.setHorizontalOffset(i2);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownVerticalOffset(int i2) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setVerticalOffset(i2);
        } else {
            super.setDropDownVerticalOffset(i2);
        }
    }

    @Override // android.widget.Spinner
    public void setDropDownWidth(int i2) {
        if (this.mPopup != null) {
            this.mDropDownWidth = i2;
        } else {
            super.setDropDownWidth(i2);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundDrawable(Drawable drawable) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setBackgroundDrawable(drawable);
        } else {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundResource(@DrawableRes int i2) {
        setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), i2));
    }

    @Override // android.widget.Spinner
    public void setPrompt(CharSequence charSequence) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setPromptText(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    @Override // androidx.core.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @Override // androidx.core.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintMode(mode);
        }
    }

    public void showPopup() {
        this.mPopup.show(getTextDirection(), getTextAlignment());
    }

    public AppCompatSpinner(@NonNull Context context, int i2) {
        this(context, null, androidx.appcompat.R.attr.spinnerStyle, i2);
    }

    @Override // android.widget.AdapterView
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.mPopup != null) {
            Context context = this.mPopupContext;
            if (context == null) {
                context = getContext();
            }
            this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context.getTheme()));
        }
    }

    public AppCompatSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, androidx.appcompat.R.attr.spinnerStyle);
    }

    public AppCompatSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, -1);
    }

    public AppCompatSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        this(context, attributeSet, i2, i3, null);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0049 A[Catch: all -> 0x004e, Exception -> 0x0051, TRY_LEAVE, TryCatch #3 {Exception -> 0x0051, all -> 0x004e, blocks: (B:13:0x0043, B:15:0x0049), top: B:45:0x0043 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0068 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:33:0x006b  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:37:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:40:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:43:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public AppCompatSpinner(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3, Resources.Theme theme) throws Throwable {
        ContextThemeWrapper contextThemeWrapper;
        TypedArray typedArrayObtainStyledAttributes;
        CharSequence[] textArray;
        SpinnerAdapter spinnerAdapter;
        super(context, attributeSet, i2);
        this.mTempRect = new Rect();
        ThemeUtils.checkAppCompatTheme(this, getContext());
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, androidx.appcompat.R.styleable.Spinner, i2, 0);
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper(this);
        if (theme == null) {
            int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.Spinner_popupTheme, 0);
            if (resourceId != 0) {
                contextThemeWrapper = new ContextThemeWrapper(context, resourceId);
            } else {
                this.mPopupContext = context;
            }
            TypedArray typedArray = null;
            if (i3 == -1) {
                try {
                    typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS_ANDROID_SPINNERMODE, i2, 0);
                    try {
                        if (typedArrayObtainStyledAttributes.hasValue(0)) {
                            i3 = typedArrayObtainStyledAttributes.getInt(0, 0);
                        }
                    } catch (Exception unused) {
                        if (typedArrayObtainStyledAttributes != null) {
                        }
                        if (i3 == 0) {
                            DialogPopup dialogPopup = new DialogPopup();
                            this.mPopup = dialogPopup;
                            dialogPopup.setPromptText(tintTypedArrayObtainStyledAttributes.getString(androidx.appcompat.R.styleable.Spinner_android_prompt));
                        } else if (i3 == 1) {
                            final DropdownPopup dropdownPopup = new DropdownPopup(this.mPopupContext, attributeSet, i2);
                            TintTypedArray tintTypedArrayObtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attributeSet, androidx.appcompat.R.styleable.Spinner, i2, 0);
                            this.mDropDownWidth = tintTypedArrayObtainStyledAttributes2.getLayoutDimension(androidx.appcompat.R.styleable.Spinner_android_dropDownWidth, -2);
                            dropdownPopup.setBackgroundDrawable(tintTypedArrayObtainStyledAttributes2.getDrawable(androidx.appcompat.R.styleable.Spinner_android_popupBackground));
                            dropdownPopup.setPromptText(tintTypedArrayObtainStyledAttributes.getString(androidx.appcompat.R.styleable.Spinner_android_prompt));
                            tintTypedArrayObtainStyledAttributes2.recycle();
                            this.mPopup = dropdownPopup;
                            this.mForwardingListener = new ForwardingListener(this) { // from class: androidx.appcompat.widget.AppCompatSpinner.1
                                @Override // androidx.appcompat.widget.ForwardingListener
                                public ShowableListMenu getPopup() {
                                    return dropdownPopup;
                                }

                                @Override // androidx.appcompat.widget.ForwardingListener
                                public boolean onForwardingStarted() {
                                    if (AppCompatSpinner.this.getInternalPopup().isShowing()) {
                                        return true;
                                    }
                                    AppCompatSpinner.this.showPopup();
                                    return true;
                                }
                            };
                        }
                        textArray = tintTypedArrayObtainStyledAttributes.getTextArray(androidx.appcompat.R.styleable.Spinner_android_entries);
                        if (textArray != null) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
                            arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                            setAdapter((SpinnerAdapter) arrayAdapter);
                        }
                        tintTypedArrayObtainStyledAttributes.recycle();
                        this.mPopupSet = true;
                        spinnerAdapter = this.mTempAdapter;
                        if (spinnerAdapter != null) {
                            setAdapter(spinnerAdapter);
                            this.mTempAdapter = null;
                        }
                        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, i2);
                    } catch (Throwable th) {
                        th = th;
                        typedArray = typedArrayObtainStyledAttributes;
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                    typedArrayObtainStyledAttributes = null;
                } catch (Throwable th2) {
                    th = th2;
                }
                typedArrayObtainStyledAttributes.recycle();
            }
            if (i3 == 0) {
                DialogPopup dialogPopup2 = new DialogPopup();
                this.mPopup = dialogPopup2;
                dialogPopup2.setPromptText(tintTypedArrayObtainStyledAttributes.getString(androidx.appcompat.R.styleable.Spinner_android_prompt));
            } else if (i3 == 1) {
                final DropdownPopup dropdownPopup2 = new DropdownPopup(this.mPopupContext, attributeSet, i2);
                TintTypedArray tintTypedArrayObtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attributeSet, androidx.appcompat.R.styleable.Spinner, i2, 0);
                this.mDropDownWidth = tintTypedArrayObtainStyledAttributes3.getLayoutDimension(androidx.appcompat.R.styleable.Spinner_android_dropDownWidth, -2);
                dropdownPopup2.setBackgroundDrawable(tintTypedArrayObtainStyledAttributes3.getDrawable(androidx.appcompat.R.styleable.Spinner_android_popupBackground));
                dropdownPopup2.setPromptText(tintTypedArrayObtainStyledAttributes.getString(androidx.appcompat.R.styleable.Spinner_android_prompt));
                tintTypedArrayObtainStyledAttributes3.recycle();
                this.mPopup = dropdownPopup2;
                this.mForwardingListener = new ForwardingListener(this) { // from class: androidx.appcompat.widget.AppCompatSpinner.1
                    @Override // androidx.appcompat.widget.ForwardingListener
                    public ShowableListMenu getPopup() {
                        return dropdownPopup2;
                    }

                    @Override // androidx.appcompat.widget.ForwardingListener
                    public boolean onForwardingStarted() {
                        if (AppCompatSpinner.this.getInternalPopup().isShowing()) {
                            return true;
                        }
                        AppCompatSpinner.this.showPopup();
                        return true;
                    }
                };
            }
            textArray = tintTypedArrayObtainStyledAttributes.getTextArray(androidx.appcompat.R.styleable.Spinner_android_entries);
            if (textArray != null) {
                ArrayAdapter arrayAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
                arrayAdapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                setAdapter((SpinnerAdapter) arrayAdapter2);
            }
            tintTypedArrayObtainStyledAttributes.recycle();
            this.mPopupSet = true;
            spinnerAdapter = this.mTempAdapter;
            if (spinnerAdapter != null) {
                setAdapter(spinnerAdapter);
                this.mTempAdapter = null;
            }
            this.mBackgroundTintHelper.loadFromAttributes(attributeSet, i2);
        }
        contextThemeWrapper = new ContextThemeWrapper(context, theme);
        this.mPopupContext = contextThemeWrapper;
        TypedArray typedArray2 = null;
        if (i3 == -1) {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS_ANDROID_SPINNERMODE, i2, 0);
            if (typedArrayObtainStyledAttributes.hasValue(0)) {
                i3 = typedArrayObtainStyledAttributes.getInt(0, 0);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        if (i3 == 0) {
            DialogPopup dialogPopup3 = new DialogPopup();
            this.mPopup = dialogPopup3;
            dialogPopup3.setPromptText(tintTypedArrayObtainStyledAttributes.getString(androidx.appcompat.R.styleable.Spinner_android_prompt));
        } else if (i3 == 1) {
            final DropdownPopup dropdownPopup3 = new DropdownPopup(this.mPopupContext, attributeSet, i2);
            TintTypedArray tintTypedArrayObtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attributeSet, androidx.appcompat.R.styleable.Spinner, i2, 0);
            this.mDropDownWidth = tintTypedArrayObtainStyledAttributes4.getLayoutDimension(androidx.appcompat.R.styleable.Spinner_android_dropDownWidth, -2);
            dropdownPopup3.setBackgroundDrawable(tintTypedArrayObtainStyledAttributes4.getDrawable(androidx.appcompat.R.styleable.Spinner_android_popupBackground));
            dropdownPopup3.setPromptText(tintTypedArrayObtainStyledAttributes.getString(androidx.appcompat.R.styleable.Spinner_android_prompt));
            tintTypedArrayObtainStyledAttributes4.recycle();
            this.mPopup = dropdownPopup3;
            this.mForwardingListener = new ForwardingListener(this) { // from class: androidx.appcompat.widget.AppCompatSpinner.1
                @Override // androidx.appcompat.widget.ForwardingListener
                public ShowableListMenu getPopup() {
                    return dropdownPopup3;
                }

                @Override // androidx.appcompat.widget.ForwardingListener
                public boolean onForwardingStarted() {
                    if (AppCompatSpinner.this.getInternalPopup().isShowing()) {
                        return true;
                    }
                    AppCompatSpinner.this.showPopup();
                    return true;
                }
            };
        }
        textArray = tintTypedArrayObtainStyledAttributes.getTextArray(androidx.appcompat.R.styleable.Spinner_android_entries);
        if (textArray != null) {
            ArrayAdapter arrayAdapter3 = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
            arrayAdapter3.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            setAdapter((SpinnerAdapter) arrayAdapter3);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        this.mPopupSet = true;
        spinnerAdapter = this.mTempAdapter;
        if (spinnerAdapter != null) {
            setAdapter(spinnerAdapter);
            this.mTempAdapter = null;
        }
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, i2);
    }
}
