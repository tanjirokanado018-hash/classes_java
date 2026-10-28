package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class AlertController {
    ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final int mButtonIconDimen;
    Button mButtonNegative;
    private Drawable mButtonNegativeIcon;
    Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    Button mButtonNeutral;
    private Drawable mButtonNeutralIcon;
    Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelSideLayout;
    Button mButtonPositive;
    private Drawable mButtonPositiveIcon;
    Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private final Context mContext;
    private View mCustomTitleView;
    final AppCompatDialog mDialog;
    Handler mHandler;
    private Drawable mIcon;
    private ImageView mIconView;
    int mListItemLayout;
    int mListLayout;
    ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    int mMultiChoiceItemLayout;
    NestedScrollView mScrollView;
    private boolean mShowTitle;
    int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private int mViewSpacingTop;
    private final Window mWindow;
    private boolean mViewSpacingSpecified = false;
    private int mIconId = 0;
    int mCheckedItem = -1;
    private int mButtonPanelLayoutHint = 0;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() { // from class: androidx.appcompat.app.AlertController.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Message messageObtain;
            Message message;
            Message message2;
            AlertController alertController = AlertController.this;
            if ((view != alertController.mButtonPositive || (message2 = alertController.mButtonPositiveMessage) == null) && (view != alertController.mButtonNegative || (message2 = alertController.mButtonNegativeMessage) == null)) {
                messageObtain = (view != alertController.mButtonNeutral || (message = alertController.mButtonNeutralMessage) == null) ? null : Message.obtain(message);
            } else {
                messageObtain = Message.obtain(message2);
            }
            if (messageObtain != null) {
                messageObtain.sendToTarget();
            }
            AlertController alertController2 = AlertController.this;
            alertController2.mHandler.obtainMessage(1, alertController2.mDialog).sendToTarget();
        }
    };

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public Drawable mNegativeButtonIcon;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public Drawable mNeutralButtonIcon;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public Drawable mPositiveButtonIcon;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public int mViewSpacingTop;
        public int mIconId = 0;
        public int mIconAttrId = 0;
        public boolean mViewSpacingSpecified = false;
        public int mCheckedItem = -1;
        public boolean mRecycleOnMeasure = true;
        public boolean mCancelable = true;

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        /* JADX WARN: Code duplicated, block: B:32:0x0091  */
        /* JADX WARN: Code duplicated, block: B:35:0x0098  */
        /* JADX WARN: Code duplicated, block: B:37:0x009d  */
        /* JADX WARN: Code duplicated, block: B:39:0x00a1  */
        /* JADX WARN: Instruction removed from duplicated block: B:35:0x0098, please report this as an issue */
        private void createListView(final AlertController alertController) {
            ListAdapter checkedItemAdapter;
            AdapterView.OnItemClickListener onItemClickListener;
            AdapterView.OnItemSelectedListener onItemSelectedListener;
            int i2;
            final RecycleListView recycleListView = (RecycleListView) this.mInflater.inflate(alertController.mListLayout, (ViewGroup) null);
            if (this.mIsMultiChoice) {
                checkedItemAdapter = this.mCursor == null ? new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, R.id.text1, this.mItems) { // from class: androidx.appcompat.app.AlertController.AlertParams.1
                    @Override // android.widget.ArrayAdapter, android.widget.Adapter
                    public View getView(int i3, View view, ViewGroup viewGroup) {
                        View view2 = super.getView(i3, view, viewGroup);
                        boolean[] zArr = AlertParams.this.mCheckedItems;
                        if (zArr != null && zArr[i3]) {
                            recycleListView.setItemChecked(i3, true);
                        }
                        return view2;
                    }
                } : new CursorAdapter(this.mContext, this.mCursor, false) { // from class: androidx.appcompat.app.AlertController.AlertParams.2
                    private final int mIsCheckedIndex;
                    private final int mLabelIndex;

                    {
                        Cursor cursor = getCursor();
                        this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                        this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                    }

                    @Override // android.widget.CursorAdapter
                    public void bindView(View view, Context context, Cursor cursor) {
                        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(this.mLabelIndex));
                        recycleListView.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1);
                    }

                    @Override // android.widget.CursorAdapter
                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                    }
                };
            } else {
                int i3 = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
                if (this.mCursor != null) {
                    checkedItemAdapter = new SimpleCursorAdapter(this.mContext, i3, this.mCursor, new String[]{this.mLabelColumn}, new int[]{R.id.text1});
                } else {
                    checkedItemAdapter = this.mAdapter;
                    if (checkedItemAdapter == null) {
                        checkedItemAdapter = new CheckedItemAdapter(this.mContext, i3, R.id.text1, this.mItems);
                    }
                }
            }
            OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
            if (onPrepareListViewListener != null) {
                onPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.mAdapter = checkedItemAdapter;
            alertController.mCheckedItem = this.mCheckedItem;
            if (this.mOnClickListener == null) {
                if (this.mOnCheckboxClickListener != null) {
                    onItemClickListener = new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.4
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i4, long j2) {
                            boolean[] zArr = AlertParams.this.mCheckedItems;
                            if (zArr != null) {
                                zArr[i4] = recycleListView.isItemChecked(i4);
                            }
                            AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialog, i4, recycleListView.isItemChecked(i4));
                        }
                    };
                }
                onItemSelectedListener = this.mOnItemSelectedListener;
                if (onItemSelectedListener != null) {
                    recycleListView.setOnItemSelectedListener(onItemSelectedListener);
                }
                if (this.mIsSingleChoice) {
                    i2 = this.mIsMultiChoice ? 2 : 1;
                    alertController.mListView = recycleListView;
                }
                recycleListView.setChoiceMode(i2);
                alertController.mListView = recycleListView;
            }
            onItemClickListener = new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.3
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i4, long j2) {
                    AlertParams.this.mOnClickListener.onClick(alertController.mDialog, i4);
                    if (AlertParams.this.mIsSingleChoice) {
                        return;
                    }
                    alertController.mDialog.dismiss();
                }
            };
            recycleListView.setOnItemClickListener(onItemClickListener);
            onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                recycleListView.setOnItemSelectedListener(onItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                if (this.mIsMultiChoice) {
                }
                alertController.mListView = recycleListView;
            }
            recycleListView.setChoiceMode(i2);
            alertController.mListView = recycleListView;
        }

        public void apply(AlertController alertController) {
            View view = this.mCustomTitleView;
            if (view != null) {
                alertController.setCustomTitle(view);
            } else {
                CharSequence charSequence = this.mTitle;
                if (charSequence != null) {
                    alertController.setTitle(charSequence);
                }
                Drawable drawable = this.mIcon;
                if (drawable != null) {
                    alertController.setIcon(drawable);
                }
                int i2 = this.mIconId;
                if (i2 != 0) {
                    alertController.setIcon(i2);
                }
                int i3 = this.mIconAttrId;
                if (i3 != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(i3));
                }
            }
            CharSequence charSequence2 = this.mMessage;
            if (charSequence2 != null) {
                alertController.setMessage(charSequence2);
            }
            CharSequence charSequence3 = this.mPositiveButtonText;
            if (charSequence3 != null || this.mPositiveButtonIcon != null) {
                alertController.setButton(-1, charSequence3, this.mPositiveButtonListener, null, this.mPositiveButtonIcon);
            }
            CharSequence charSequence4 = this.mNegativeButtonText;
            if (charSequence4 != null || this.mNegativeButtonIcon != null) {
                alertController.setButton(-2, charSequence4, this.mNegativeButtonListener, null, this.mNegativeButtonIcon);
            }
            CharSequence charSequence5 = this.mNeutralButtonText;
            if (charSequence5 != null || this.mNeutralButtonIcon != null) {
                alertController.setButton(-3, charSequence5, this.mNeutralButtonListener, null, this.mNeutralButtonIcon);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController);
            }
            View view2 = this.mView;
            if (view2 != null) {
                if (this.mViewSpacingSpecified) {
                    alertController.setView(view2, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                    return;
                } else {
                    alertController.setView(view2);
                    return;
                }
            }
            int i4 = this.mViewLayoutResId;
            if (i4 != 0) {
                alertController.setView(i4);
            }
        }
    }

    public static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i2 = message.what;
            if (i2 == -3 || i2 == -2 || i2 == -1) {
                ((DialogInterface.OnClickListener) message.obj).onClick(this.mDialog.get(), message.what);
            } else {
                if (i2 != 1) {
                    return;
                }
                ((DialogInterface) message.obj).dismiss();
            }
        }
    }

    public static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int i2, int i3, CharSequence[] charSequenceArr) {
            super(context, i2, i3, charSequenceArr);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }
    }

    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public void setHasDecor(boolean z2, boolean z3) {
            if (z3 && z2) {
                return;
            }
            setPadding(getPaddingLeft(), z2 ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), z3 ? getPaddingBottom() : this.mPaddingBottomNoButtons);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.appcompat.R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = typedArrayObtainStyledAttributes.getDimensionPixelOffset(androidx.appcompat.R.styleable.RecycleListView_paddingBottomNoButtons, -1);
            this.mPaddingTopNoTitle = typedArrayObtainStyledAttributes.getDimensionPixelOffset(androidx.appcompat.R.styleable.RecycleListView_paddingTopNoTitle, -1);
        }
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(appCompatDialog);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, androidx.appcompat.R.styleable.AlertDialog, androidx.appcompat.R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.mListLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = typedArrayObtainStyledAttributes.getResourceId(androidx.appcompat.R.styleable.AlertDialog_listItemLayout, 0);
        this.mShowTitle = typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.AlertDialog_showTitle, true);
        this.mButtonIconDimen = typedArrayObtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R.styleable.AlertDialog_buttonIconDimen, 0);
        typedArrayObtainStyledAttributes.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
    }

    public static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }

    public static void manageScrollIndicators(View view, View view2, View view3) {
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4);
        }
        if (view3 != null) {
            view3.setVisibility(view.canScrollVertically(1) ? 0 : 4);
        }
    }

    @Nullable
    private ViewGroup resolvePanel(@Nullable View view, @Nullable View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    private int selectContentView() {
        int i2 = this.mButtonPanelSideLayout;
        return (i2 != 0 && this.mButtonPanelLayoutHint == 1) ? i2 : this.mAlertDialogLayout;
    }

    private void setScrollIndicators(ViewGroup viewGroup, View view, int i2, int i3) {
        View view2;
        Runnable runnable;
        final View viewFindViewById = this.mWindow.findViewById(androidx.appcompat.R.id.scrollIndicatorUp);
        final View viewFindViewById2 = this.mWindow.findViewById(androidx.appcompat.R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT < 23) {
            if (viewFindViewById != null && (i2 & 1) == 0) {
                viewGroup.removeView(viewFindViewById);
                viewFindViewById = null;
            }
            if (viewFindViewById2 != null && (i2 & 2) == 0) {
                viewGroup.removeView(viewFindViewById2);
                viewFindViewById2 = null;
            }
            if (viewFindViewById == null && viewFindViewById2 == null) {
                return;
            }
            if (this.mMessage != null) {
                this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: androidx.appcompat.app.AlertController.2
                    @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
                    public void onScrollChange(NestedScrollView nestedScrollView, int i4, int i5, int i6, int i7) {
                        AlertController.manageScrollIndicators(nestedScrollView, viewFindViewById, viewFindViewById2);
                    }
                });
                view2 = this.mScrollView;
                runnable = new Runnable() { // from class: androidx.appcompat.app.AlertController.3
                    @Override // java.lang.Runnable
                    public void run() {
                        AlertController.manageScrollIndicators(AlertController.this.mScrollView, viewFindViewById, viewFindViewById2);
                    }
                };
            } else {
                ListView listView = this.mListView;
                if (listView != null) {
                    listView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: androidx.appcompat.app.AlertController.4
                        @Override // android.widget.AbsListView.OnScrollListener
                        public void onScroll(AbsListView absListView, int i4, int i5, int i6) {
                            AlertController.manageScrollIndicators(absListView, viewFindViewById, viewFindViewById2);
                        }

                        @Override // android.widget.AbsListView.OnScrollListener
                        public void onScrollStateChanged(AbsListView absListView, int i4) {
                        }
                    });
                    view2 = this.mListView;
                    runnable = new Runnable() { // from class: androidx.appcompat.app.AlertController.5
                        @Override // java.lang.Runnable
                        public void run() {
                            AlertController.manageScrollIndicators(AlertController.this.mListView, viewFindViewById, viewFindViewById2);
                        }
                    };
                } else {
                    if (viewFindViewById != null) {
                        viewGroup.removeView(viewFindViewById);
                    }
                    if (viewFindViewById2 == null) {
                        return;
                    }
                }
            }
            view2.post(runnable);
            return;
        }
        ViewCompat.setScrollIndicators(view, i2, i3);
        if (viewFindViewById != null) {
            viewGroup.removeView(viewFindViewById);
        }
        if (viewFindViewById2 == null) {
            return;
        }
        viewGroup.removeView(viewFindViewById2);
    }

    private void setupButtons(ViewGroup viewGroup) {
        int i2;
        Button button;
        Button button2 = (Button) viewGroup.findViewById(R.id.button1);
        this.mButtonPositive = button2;
        button2.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonPositiveText) && this.mButtonPositiveIcon == null) {
            this.mButtonPositive.setVisibility(8);
            i2 = 0;
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            Drawable drawable = this.mButtonPositiveIcon;
            if (drawable != null) {
                int i3 = this.mButtonIconDimen;
                drawable.setBounds(0, 0, i3, i3);
                this.mButtonPositive.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null);
            }
            this.mButtonPositive.setVisibility(0);
            i2 = 1;
        }
        Button button3 = (Button) viewGroup.findViewById(R.id.button2);
        this.mButtonNegative = button3;
        button3.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText) && this.mButtonNegativeIcon == null) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            Drawable drawable2 = this.mButtonNegativeIcon;
            if (drawable2 != null) {
                int i4 = this.mButtonIconDimen;
                drawable2.setBounds(0, 0, i4, i4);
                this.mButtonNegative.setCompoundDrawables(this.mButtonNegativeIcon, null, null, null);
            }
            this.mButtonNegative.setVisibility(0);
            i2 |= 2;
        }
        Button button4 = (Button) viewGroup.findViewById(R.id.button3);
        this.mButtonNeutral = button4;
        button4.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText) && this.mButtonNeutralIcon == null) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            Drawable drawable3 = this.mButtonNeutralIcon;
            if (drawable3 != null) {
                int i5 = this.mButtonIconDimen;
                drawable3.setBounds(0, 0, i5, i5);
                this.mButtonNeutral.setCompoundDrawables(this.mButtonNeutralIcon, null, null, null);
            }
            this.mButtonNeutral.setVisibility(0);
            i2 |= 4;
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (i2 == 1) {
                button = this.mButtonPositive;
            } else if (i2 == 2) {
                button = this.mButtonNegative;
            } else if (i2 == 4) {
                button = this.mButtonNeutral;
            }
            centerButton(button);
        }
        if (i2 != 0) {
            return;
        }
        viewGroup.setVisibility(8);
    }

    private void setupContent(ViewGroup viewGroup) {
        NestedScrollView nestedScrollView = (NestedScrollView) this.mWindow.findViewById(androidx.appcompat.R.id.scrollView);
        this.mScrollView = nestedScrollView;
        nestedScrollView.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        TextView textView = (TextView) viewGroup.findViewById(R.id.message);
        this.mMessageView = textView;
        if (textView == null) {
            return;
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            textView.setText(charSequence);
            return;
        }
        textView.setVisibility(8);
        this.mScrollView.removeView(this.mMessageView);
        if (this.mListView == null) {
            viewGroup.setVisibility(8);
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mScrollView.getParent();
        int iIndexOfChild = viewGroup2.indexOfChild(this.mScrollView);
        viewGroup2.removeViewAt(iIndexOfChild);
        viewGroup2.addView(this.mListView, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        View viewInflate = this.mView;
        if (viewInflate == null) {
            viewInflate = this.mViewLayoutResId != 0 ? LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null;
        }
        boolean z2 = viewInflate != null;
        if (!z2 || !canTextInput(viewInflate)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (!z2) {
            viewGroup.setVisibility(8);
            return;
        }
        FrameLayout frameLayout = (FrameLayout) this.mWindow.findViewById(androidx.appcompat.R.id.custom);
        frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
        if (this.mViewSpacingSpecified) {
            frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        }
        if (this.mListView != null) {
            ((LinearLayout.LayoutParams) ((LinearLayoutCompat.LayoutParams) viewGroup.getLayoutParams())).weight = 0.0f;
        }
    }

    private void setupTitle(ViewGroup viewGroup) {
        View viewFindViewById;
        if (this.mCustomTitleView != null) {
            viewGroup.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            viewFindViewById = this.mWindow.findViewById(androidx.appcompat.R.id.title_template);
        } else {
            this.mIconView = (ImageView) this.mWindow.findViewById(R.id.icon);
            if ((!TextUtils.isEmpty(this.mTitle)) && this.mShowTitle) {
                TextView textView = (TextView) this.mWindow.findViewById(androidx.appcompat.R.id.alertTitle);
                this.mTitleView = textView;
                textView.setText(this.mTitle);
                int i2 = this.mIconId;
                if (i2 != 0) {
                    this.mIconView.setImageResource(i2);
                    return;
                }
                Drawable drawable = this.mIcon;
                if (drawable != null) {
                    this.mIconView.setImageDrawable(drawable);
                    return;
                } else {
                    this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                    this.mIconView.setVisibility(8);
                    return;
                }
            }
            this.mWindow.findViewById(androidx.appcompat.R.id.title_template).setVisibility(8);
            this.mIconView.setVisibility(8);
            viewFindViewById = viewGroup;
        }
        viewFindViewById.setVisibility(8);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00a5 A[PHI: r1
      0x00a5: PHI (r1v6 android.view.View) = (r1v5 android.view.View), (r1v13 android.view.View) binds: [B:36:0x00a3, B:32:0x0098] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    private void setupView() {
        View viewFindViewById;
        ListAdapter listAdapter;
        View viewFindViewById2;
        View viewFindViewById3 = this.mWindow.findViewById(androidx.appcompat.R.id.parentPanel);
        View viewFindViewById4 = viewFindViewById3.findViewById(androidx.appcompat.R.id.topPanel);
        View viewFindViewById5 = viewFindViewById3.findViewById(androidx.appcompat.R.id.contentPanel);
        View viewFindViewById6 = viewFindViewById3.findViewById(androidx.appcompat.R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById3.findViewById(androidx.appcompat.R.id.customPanel);
        setupCustomContent(viewGroup);
        View viewFindViewById7 = viewGroup.findViewById(androidx.appcompat.R.id.topPanel);
        View viewFindViewById8 = viewGroup.findViewById(androidx.appcompat.R.id.contentPanel);
        View viewFindViewById9 = viewGroup.findViewById(androidx.appcompat.R.id.buttonPanel);
        ViewGroup viewGroupResolvePanel = resolvePanel(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupResolvePanel2 = resolvePanel(viewFindViewById8, viewFindViewById5);
        ViewGroup viewGroupResolvePanel3 = resolvePanel(viewFindViewById9, viewFindViewById6);
        setupContent(viewGroupResolvePanel2);
        setupButtons(viewGroupResolvePanel3);
        setupTitle(viewGroupResolvePanel);
        boolean z2 = viewGroup.getVisibility() != 8;
        boolean z3 = (viewGroupResolvePanel == null || viewGroupResolvePanel.getVisibility() == 8) ? 0 : 1;
        boolean z4 = (viewGroupResolvePanel3 == null || viewGroupResolvePanel3.getVisibility() == 8) ? false : true;
        if (!z4 && viewGroupResolvePanel2 != null && (viewFindViewById2 = viewGroupResolvePanel2.findViewById(androidx.appcompat.R.id.textSpacerNoButtons)) != null) {
            viewFindViewById2.setVisibility(0);
        }
        if (z3 != 0) {
            NestedScrollView nestedScrollView = this.mScrollView;
            if (nestedScrollView != null) {
                nestedScrollView.setClipToPadding(true);
            }
            viewFindViewById = (this.mMessage == null && this.mListView == null) ? null : viewGroupResolvePanel.findViewById(androidx.appcompat.R.id.titleDividerNoCustom);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(0);
            }
        } else if (viewGroupResolvePanel2 != null && (viewFindViewById = viewGroupResolvePanel2.findViewById(androidx.appcompat.R.id.textSpacerNoTitle)) != null) {
            viewFindViewById.setVisibility(0);
        }
        ListView listView = this.mListView;
        if (listView instanceof RecycleListView) {
            ((RecycleListView) listView).setHasDecor(z3, z4);
        }
        if (!z2) {
            View view = this.mListView;
            if (view == null) {
                view = this.mScrollView;
            }
            if (view != null) {
                setScrollIndicators(viewGroupResolvePanel2, view, z3 | (z4 ? 2 : 0), 3);
            }
        }
        ListView listView2 = this.mListView;
        if (listView2 == null || (listAdapter = this.mAdapter) == null) {
            return;
        }
        listView2.setAdapter(listAdapter);
        int i2 = this.mCheckedItem;
        if (i2 > -1) {
            listView2.setItemChecked(i2, true);
            listView2.setSelection(i2);
        }
    }

    private static boolean shouldCenterSingleButton(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(androidx.appcompat.R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public Button getButton(int i2) {
        if (i2 == -3) {
            return this.mButtonNeutral;
        }
        if (i2 == -2) {
            return this.mButtonNegative;
        }
        if (i2 != -1) {
            return null;
        }
        return this.mButtonPositive;
    }

    public int getIconAttributeResId(int i2) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i2, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public void installContent() {
        this.mDialog.setContentView(selectContentView());
        setupView();
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mScrollView;
        return nestedScrollView != null && nestedScrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mScrollView;
        return nestedScrollView != null && nestedScrollView.executeKeyEvent(keyEvent);
    }

    public void setButton(int i2, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message, Drawable drawable) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i2, onClickListener);
        }
        if (i2 == -3) {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = message;
            this.mButtonNeutralIcon = drawable;
        } else if (i2 == -2) {
            this.mButtonNegativeText = charSequence;
            this.mButtonNegativeMessage = message;
            this.mButtonNegativeIcon = drawable;
        } else {
            if (i2 != -1) {
                throw new IllegalArgumentException("Button does not exist");
            }
            this.mButtonPositiveText = charSequence;
            this.mButtonPositiveMessage = message;
            this.mButtonPositiveIcon = drawable;
        }
    }

    public void setButtonPanelLayoutHint(int i2) {
        this.mButtonPanelLayoutHint = i2;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setIcon(int i2) {
        this.mIcon = null;
        this.mIconId = i2;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (i2 == 0) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            }
        }
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setView(int i2) {
        this.mView = null;
        this.mViewLayoutResId = i2;
        this.mViewSpacingSpecified = false;
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (drawable == null) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable);
            }
        }
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int i2, int i3, int i4, int i5) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = i2;
        this.mViewSpacingTop = i3;
        this.mViewSpacingRight = i4;
        this.mViewSpacingBottom = i5;
    }
}
