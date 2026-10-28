package com.google.android.material.datepicker;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.util.Pair;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import f.ViewOnClickListenerC0021a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class MaterialDatePicker<S> extends DialogFragment {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
    private static final String DAY_VIEW_DECORATOR_KEY = "DAY_VIEW_DECORATOR_KEY";
    public static final int INPUT_MODE_CALENDAR = 0;
    private static final String INPUT_MODE_KEY = "INPUT_MODE_KEY";
    public static final int INPUT_MODE_TEXT = 1;
    private static final String NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY = "NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY";
    private static final String NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY = "NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY";
    private static final String NEGATIVE_BUTTON_TEXT_KEY = "NEGATIVE_BUTTON_TEXT_KEY";
    private static final String NEGATIVE_BUTTON_TEXT_RES_ID_KEY = "NEGATIVE_BUTTON_TEXT_RES_ID_KEY";
    private static final String OVERRIDE_THEME_RES_ID = "OVERRIDE_THEME_RES_ID";
    private static final String POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY = "POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY";
    private static final String POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY = "POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY";
    private static final String POSITIVE_BUTTON_TEXT_KEY = "POSITIVE_BUTTON_TEXT_KEY";
    private static final String POSITIVE_BUTTON_TEXT_RES_ID_KEY = "POSITIVE_BUTTON_TEXT_RES_ID_KEY";
    private static final String TITLE_TEXT_KEY = "TITLE_TEXT_KEY";
    private static final String TITLE_TEXT_RES_ID_KEY = "TITLE_TEXT_RES_ID_KEY";

    @Nullable
    private MaterialShapeDrawable background;
    private MaterialCalendar<S> calendar;

    @Nullable
    private CalendarConstraints calendarConstraints;
    private Button confirmButton;

    @Nullable
    private DateSelector<S> dateSelector;

    @Nullable
    private DayViewDecorator dayViewDecorator;
    private boolean edgeToEdgeEnabled;

    @Nullable
    private CharSequence fullTitleText;
    private boolean fullscreen;
    private TextView headerSelectionText;
    private TextView headerTitleTextView;
    private CheckableImageButton headerToggleButton;
    private int inputMode;
    private CharSequence negativeButtonContentDescription;

    @StringRes
    private int negativeButtonContentDescriptionResId;
    private CharSequence negativeButtonText;

    @StringRes
    private int negativeButtonTextResId;

    @StyleRes
    private int overrideThemeResId;
    private PickerFragment<S> pickerFragment;
    private CharSequence positiveButtonContentDescription;

    @StringRes
    private int positiveButtonContentDescriptionResId;
    private CharSequence positiveButtonText;

    @StringRes
    private int positiveButtonTextResId;

    @Nullable
    private CharSequence singleLineTitleText;
    private CharSequence titleText;

    @StringRes
    private int titleTextResId;
    static final Object CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
    static final Object CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
    static final Object TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
    private final LinkedHashSet<MaterialPickerOnPositiveButtonClickListener<? super S>> onPositiveButtonClickListeners = new LinkedHashSet<>();
    private final LinkedHashSet<View.OnClickListener> onNegativeButtonClickListeners = new LinkedHashSet<>();
    private final LinkedHashSet<DialogInterface.OnCancelListener> onCancelListeners = new LinkedHashSet<>();
    private final LinkedHashSet<DialogInterface.OnDismissListener> onDismissListeners = new LinkedHashSet<>();

    public static final class Builder<S> {
        CalendarConstraints calendarConstraints;
        final DateSelector<S> dateSelector;

        @Nullable
        DayViewDecorator dayViewDecorator;
        int overrideThemeResId = 0;
        int titleTextResId = 0;
        CharSequence titleText = null;
        int positiveButtonTextResId = 0;
        CharSequence positiveButtonText = null;
        int positiveButtonContentDescriptionResId = 0;
        CharSequence positiveButtonContentDescription = null;
        int negativeButtonTextResId = 0;
        CharSequence negativeButtonText = null;
        int negativeButtonContentDescriptionResId = 0;
        CharSequence negativeButtonContentDescription = null;

        @Nullable
        S selection = null;
        int inputMode = 0;

        private Builder(DateSelector<S> dateSelector) {
            this.dateSelector = dateSelector;
        }

        private Month createDefaultOpenAt() {
            if (!this.dateSelector.getSelectedDays().isEmpty()) {
                Month monthCreate = Month.create(this.dateSelector.getSelectedDays().iterator().next().longValue());
                if (monthInValidRange(monthCreate, this.calendarConstraints)) {
                    return monthCreate;
                }
            }
            Month monthCurrent = Month.current();
            return monthInValidRange(monthCurrent, this.calendarConstraints) ? monthCurrent : this.calendarConstraints.getStart();
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static <S> Builder<S> customDatePicker(@NonNull DateSelector<S> dateSelector) {
            return new Builder<>(dateSelector);
        }

        @NonNull
        public static Builder<Long> datePicker() {
            return new Builder<>(new SingleDateSelector());
        }

        @NonNull
        public static Builder<Pair<Long, Long>> dateRangePicker() {
            return new Builder<>(new RangeDateSelector());
        }

        private static boolean monthInValidRange(Month month, CalendarConstraints calendarConstraints) {
            return month.compareTo(calendarConstraints.getStart()) >= 0 && month.compareTo(calendarConstraints.getEnd()) <= 0;
        }

        @NonNull
        public MaterialDatePicker<S> build() {
            if (this.calendarConstraints == null) {
                this.calendarConstraints = new CalendarConstraints.Builder().build();
            }
            if (this.titleTextResId == 0) {
                this.titleTextResId = this.dateSelector.getDefaultTitleResId();
            }
            S s2 = this.selection;
            if (s2 != null) {
                this.dateSelector.setSelection(s2);
            }
            if (this.calendarConstraints.getOpenAt() == null) {
                this.calendarConstraints.setOpenAt(createDefaultOpenAt());
            }
            return MaterialDatePicker.newInstance(this);
        }

        @NonNull
        public Builder<S> setCalendarConstraints(CalendarConstraints calendarConstraints) {
            this.calendarConstraints = calendarConstraints;
            return this;
        }

        @NonNull
        public Builder<S> setDayViewDecorator(@Nullable DayViewDecorator dayViewDecorator) {
            this.dayViewDecorator = dayViewDecorator;
            return this;
        }

        @NonNull
        public Builder<S> setInputMode(int i2) {
            this.inputMode = i2;
            return this;
        }

        @NonNull
        public Builder<S> setNegativeButtonContentDescription(@StringRes int i2) {
            this.negativeButtonContentDescriptionResId = i2;
            this.negativeButtonContentDescription = null;
            return this;
        }

        @NonNull
        public Builder<S> setNegativeButtonText(@StringRes int i2) {
            this.negativeButtonTextResId = i2;
            this.negativeButtonText = null;
            return this;
        }

        @NonNull
        public Builder<S> setPositiveButtonContentDescription(@StringRes int i2) {
            this.positiveButtonContentDescriptionResId = i2;
            this.positiveButtonContentDescription = null;
            return this;
        }

        @NonNull
        public Builder<S> setPositiveButtonText(@StringRes int i2) {
            this.positiveButtonTextResId = i2;
            this.positiveButtonText = null;
            return this;
        }

        @NonNull
        public Builder<S> setSelection(S s2) {
            this.selection = s2;
            return this;
        }

        @NonNull
        public Builder<S> setTextInputFormat(@Nullable SimpleDateFormat simpleDateFormat) {
            this.dateSelector.setTextInputFormat(simpleDateFormat);
            return this;
        }

        @NonNull
        public Builder<S> setTheme(@StyleRes int i2) {
            this.overrideThemeResId = i2;
            return this;
        }

        @NonNull
        public Builder<S> setTitleText(@StringRes int i2) {
            this.titleTextResId = i2;
            this.titleText = null;
            return this;
        }

        @NonNull
        public Builder<S> setNegativeButtonContentDescription(@Nullable CharSequence charSequence) {
            this.negativeButtonContentDescription = charSequence;
            this.negativeButtonContentDescriptionResId = 0;
            return this;
        }

        @NonNull
        public Builder<S> setNegativeButtonText(@Nullable CharSequence charSequence) {
            this.negativeButtonText = charSequence;
            this.negativeButtonTextResId = 0;
            return this;
        }

        @NonNull
        public Builder<S> setPositiveButtonContentDescription(@Nullable CharSequence charSequence) {
            this.positiveButtonContentDescription = charSequence;
            this.positiveButtonContentDescriptionResId = 0;
            return this;
        }

        @NonNull
        public Builder<S> setPositiveButtonText(@Nullable CharSequence charSequence) {
            this.positiveButtonText = charSequence;
            this.positiveButtonTextResId = 0;
            return this;
        }

        @NonNull
        public Builder<S> setTitleText(@Nullable CharSequence charSequence) {
            this.titleText = charSequence;
            this.titleTextResId = 0;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface InputMode {
    }

    @NonNull
    private static Drawable createHeaderToggleDrawable(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_checked}, AppCompatResources.getDrawable(context, com.google.android.material.R.drawable.material_ic_calendar_black_24dp));
        stateListDrawable.addState(new int[0], AppCompatResources.getDrawable(context, com.google.android.material.R.drawable.material_ic_edit_black_24dp));
        return stateListDrawable;
    }

    private void enableEdgeToEdgeIfNeeded(Window window) {
        if (this.edgeToEdgeEnabled) {
            return;
        }
        final View viewFindViewById = requireView().findViewById(com.google.android.material.R.id.fullscreen_header);
        EdgeToEdgeUtils.applyEdgeToEdge(window, true, ViewUtils.getBackgroundColor(viewFindViewById), null);
        final int paddingTop = viewFindViewById.getPaddingTop();
        final int i2 = viewFindViewById.getLayoutParams().height;
        ViewCompat.setOnApplyWindowInsetsListener(viewFindViewById, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                int i3 = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                if (i2 >= 0) {
                    viewFindViewById.getLayoutParams().height = i2 + i3;
                    View view2 = viewFindViewById;
                    view2.setLayoutParams(view2.getLayoutParams());
                }
                View view3 = viewFindViewById;
                view3.setPadding(view3.getPaddingLeft(), paddingTop + i3, viewFindViewById.getPaddingRight(), viewFindViewById.getPaddingBottom());
                return windowInsetsCompat;
            }
        });
        this.edgeToEdgeEnabled = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DateSelector<S> getDateSelector() {
        if (this.dateSelector == null) {
            this.dateSelector = (DateSelector) getArguments().getParcelable(DATE_SELECTOR_KEY);
        }
        return this.dateSelector;
    }

    @Nullable
    private static CharSequence getFirstLineBySeparator(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        String[] strArrSplit = TextUtils.split(String.valueOf(charSequence), "\n");
        return strArrSplit.length > 1 ? strArrSplit[0] : charSequence;
    }

    private String getHeaderContentDescription() {
        return getDateSelector().getSelectionContentDescription(requireContext());
    }

    private static int getPaddedPickerWidth(@NonNull Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(com.google.android.material.R.dimen.mtrl_calendar_content_padding);
        int i2 = Month.current().daysInWeek;
        return ((i2 - 1) * resources.getDimensionPixelOffset(com.google.android.material.R.dimen.mtrl_calendar_month_horizontal_padding)) + (resources.getDimensionPixelSize(com.google.android.material.R.dimen.mtrl_calendar_day_width) * i2) + (dimensionPixelOffset * 2);
    }

    private int getThemeResId(Context context) {
        int i2 = this.overrideThemeResId;
        return i2 != 0 ? i2 : getDateSelector().getDefaultThemeResId(context);
    }

    private void initHeaderToggle(Context context) {
        this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
        this.headerToggleButton.setImageDrawable(createHeaderToggleDrawable(context));
        this.headerToggleButton.setChecked(this.inputMode != 0);
        ViewCompat.setAccessibilityDelegate(this.headerToggleButton, null);
        updateToggleContentDescription(this.headerToggleButton);
        this.headerToggleButton.setOnClickListener(new ViewOnClickListenerC0021a(2, this));
    }

    public static boolean isFullscreen(@NonNull Context context) {
        return readMaterialCalendarStyleBoolean(context, R.attr.windowFullscreen);
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    public static boolean isNestedScrollable(@NonNull Context context) {
        return readMaterialCalendarStyleBoolean(context, com.google.android.material.R.attr.nestedScrollable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHeaderToggle$0(View view) {
        this.confirmButton.setEnabled(getDateSelector().isSelectionComplete());
        this.headerToggleButton.toggle();
        this.inputMode = this.inputMode == 1 ? 0 : 1;
        updateToggleContentDescription(this.headerToggleButton);
        startPickerFragment();
    }

    @NonNull
    public static <S> MaterialDatePicker<S> newInstance(@NonNull Builder<S> builder) {
        MaterialDatePicker<S> materialDatePicker = new MaterialDatePicker<>();
        Bundle bundle = new Bundle();
        bundle.putInt(OVERRIDE_THEME_RES_ID, builder.overrideThemeResId);
        bundle.putParcelable(DATE_SELECTOR_KEY, builder.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, builder.calendarConstraints);
        bundle.putParcelable(DAY_VIEW_DECORATOR_KEY, builder.dayViewDecorator);
        bundle.putInt(TITLE_TEXT_RES_ID_KEY, builder.titleTextResId);
        bundle.putCharSequence(TITLE_TEXT_KEY, builder.titleText);
        bundle.putInt(INPUT_MODE_KEY, builder.inputMode);
        bundle.putInt(POSITIVE_BUTTON_TEXT_RES_ID_KEY, builder.positiveButtonTextResId);
        bundle.putCharSequence(POSITIVE_BUTTON_TEXT_KEY, builder.positiveButtonText);
        bundle.putInt(POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY, builder.positiveButtonContentDescriptionResId);
        bundle.putCharSequence(POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY, builder.positiveButtonContentDescription);
        bundle.putInt(NEGATIVE_BUTTON_TEXT_RES_ID_KEY, builder.negativeButtonTextResId);
        bundle.putCharSequence(NEGATIVE_BUTTON_TEXT_KEY, builder.negativeButtonText);
        bundle.putInt(NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY, builder.negativeButtonContentDescriptionResId);
        bundle.putCharSequence(NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY, builder.negativeButtonContentDescription);
        materialDatePicker.setArguments(bundle);
        return materialDatePicker;
    }

    public static boolean readMaterialCalendarStyleBoolean(@NonNull Context context, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, com.google.android.material.R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[]{i2});
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return z2;
    }

    private void startPickerFragment() {
        int themeResId = getThemeResId(requireContext());
        MaterialTextInputPicker materialTextInputPickerNewInstance = MaterialCalendar.newInstance(getDateSelector(), themeResId, this.calendarConstraints, this.dayViewDecorator);
        this.calendar = materialTextInputPickerNewInstance;
        if (this.inputMode == 1) {
            materialTextInputPickerNewInstance = MaterialTextInputPicker.newInstance(getDateSelector(), themeResId, this.calendarConstraints);
        }
        this.pickerFragment = materialTextInputPickerNewInstance;
        updateTitle();
        updateHeader(getHeaderText());
        FragmentTransaction fragmentTransactionBeginTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(com.google.android.material.R.id.mtrl_calendar_frame, this.pickerFragment);
        fragmentTransactionBeginTransaction.commitNow();
        this.pickerFragment.addOnSelectionChangedListener(new OnSelectionChangedListener<S>() { // from class: com.google.android.material.datepicker.MaterialDatePicker.4
            @Override // com.google.android.material.datepicker.OnSelectionChangedListener
            public void onIncompleteSelectionChanged() {
                MaterialDatePicker.this.confirmButton.setEnabled(false);
            }

            @Override // com.google.android.material.datepicker.OnSelectionChangedListener
            public void onSelectionChanged(S s2) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.this;
                materialDatePicker.updateHeader(materialDatePicker.getHeaderText());
                MaterialDatePicker.this.confirmButton.setEnabled(MaterialDatePicker.this.getDateSelector().isSelectionComplete());
            }
        });
    }

    public static long thisMonthInUtcMilliseconds() {
        return Month.current().timeInMillis;
    }

    public static long todayInUtcMilliseconds() {
        return UtcDates.getTodayCalendar().getTimeInMillis();
    }

    private void updateTitle() {
        this.headerTitleTextView.setText((this.inputMode == 1 && isLandscape()) ? this.singleLineTitleText : this.fullTitleText);
    }

    private void updateToggleContentDescription(@NonNull CheckableImageButton checkableImageButton) {
        this.headerToggleButton.setContentDescription(checkableImageButton.getContext().getString(this.inputMode == 1 ? com.google.android.material.R.string.mtrl_picker_toggle_to_calendar_input_mode : com.google.android.material.R.string.mtrl_picker_toggle_to_text_input_mode));
    }

    public boolean addOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.onCancelListeners.add(onCancelListener);
    }

    public boolean addOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.onDismissListeners.add(onDismissListener);
    }

    public boolean addOnNegativeButtonClickListener(View.OnClickListener onClickListener) {
        return this.onNegativeButtonClickListeners.add(onClickListener);
    }

    public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.add(materialPickerOnPositiveButtonClickListener);
    }

    public void clearOnCancelListeners() {
        this.onCancelListeners.clear();
    }

    public void clearOnDismissListeners() {
        this.onDismissListeners.clear();
    }

    public void clearOnNegativeButtonClickListeners() {
        this.onNegativeButtonClickListeners.clear();
    }

    public void clearOnPositiveButtonClickListeners() {
        this.onPositiveButtonClickListeners.clear();
    }

    public String getHeaderText() {
        return getDateSelector().getSelectionDisplayString(getContext());
    }

    public int getInputMode() {
        return this.inputMode;
    }

    @Nullable
    public final S getSelection() {
        return getDateSelector().getSelection();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public final void onCancel(@NonNull DialogInterface dialogInterface) {
        Iterator<DialogInterface.OnCancelListener> it = this.onCancelListeners.iterator();
        while (it.hasNext()) {
            it.next().onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.overrideThemeResId = bundle.getInt(OVERRIDE_THEME_RES_ID);
        this.dateSelector = (DateSelector) bundle.getParcelable(DATE_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.dayViewDecorator = (DayViewDecorator) bundle.getParcelable(DAY_VIEW_DECORATOR_KEY);
        this.titleTextResId = bundle.getInt(TITLE_TEXT_RES_ID_KEY);
        this.titleText = bundle.getCharSequence(TITLE_TEXT_KEY);
        this.inputMode = bundle.getInt(INPUT_MODE_KEY);
        this.positiveButtonTextResId = bundle.getInt(POSITIVE_BUTTON_TEXT_RES_ID_KEY);
        this.positiveButtonText = bundle.getCharSequence(POSITIVE_BUTTON_TEXT_KEY);
        this.positiveButtonContentDescriptionResId = bundle.getInt(POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY);
        this.positiveButtonContentDescription = bundle.getCharSequence(POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY);
        this.negativeButtonTextResId = bundle.getInt(NEGATIVE_BUTTON_TEXT_RES_ID_KEY);
        this.negativeButtonText = bundle.getCharSequence(NEGATIVE_BUTTON_TEXT_KEY);
        this.negativeButtonContentDescriptionResId = bundle.getInt(NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY);
        this.negativeButtonContentDescription = bundle.getCharSequence(NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY);
        CharSequence text = this.titleText;
        if (text == null) {
            text = requireContext().getResources().getText(this.titleTextResId);
        }
        this.fullTitleText = text;
        this.singleLineTitleText = getFirstLineBySeparator(text);
    }

    @Override // androidx.fragment.app.DialogFragment
    @NonNull
    public final Dialog onCreateDialog(@Nullable Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), getThemeResId(requireContext()));
        Context context = dialog.getContext();
        this.fullscreen = isFullscreen(context);
        this.background = new MaterialShapeDrawable(context, null, com.google.android.material.R.attr.materialCalendarStyle, com.google.android.material.R.style.Widget_MaterialComponents_MaterialCalendar);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, com.google.android.material.R.styleable.MaterialCalendar, com.google.android.material.R.attr.materialCalendarStyle, com.google.android.material.R.style.Widget_MaterialComponents_MaterialCalendar);
        int color = typedArrayObtainStyledAttributes.getColor(com.google.android.material.R.styleable.MaterialCalendar_backgroundTint, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.background.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf(color));
        this.background.setElevation(ViewCompat.getElevation(dialog.getWindow().getDecorView()));
        return dialog;
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public final View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View viewFindViewById;
        LinearLayout.LayoutParams layoutParams;
        View viewInflate = layoutInflater.inflate(this.fullscreen ? com.google.android.material.R.layout.mtrl_picker_fullscreen : com.google.android.material.R.layout.mtrl_picker_dialog, viewGroup);
        Context context = viewInflate.getContext();
        DayViewDecorator dayViewDecorator = this.dayViewDecorator;
        if (dayViewDecorator != null) {
            dayViewDecorator.initialize(context);
        }
        if (this.fullscreen) {
            viewFindViewById = viewInflate.findViewById(com.google.android.material.R.id.mtrl_calendar_frame);
            layoutParams = new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -2);
        } else {
            viewFindViewById = viewInflate.findViewById(com.google.android.material.R.id.mtrl_calendar_main_pane);
            layoutParams = new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -1);
        }
        viewFindViewById.setLayoutParams(layoutParams);
        TextView textView = (TextView) viewInflate.findViewById(com.google.android.material.R.id.mtrl_picker_header_selection_text);
        this.headerSelectionText = textView;
        ViewCompat.setAccessibilityLiveRegion(textView, 1);
        this.headerToggleButton = (CheckableImageButton) viewInflate.findViewById(com.google.android.material.R.id.mtrl_picker_header_toggle);
        this.headerTitleTextView = (TextView) viewInflate.findViewById(com.google.android.material.R.id.mtrl_picker_title_text);
        initHeaderToggle(context);
        this.confirmButton = (Button) viewInflate.findViewById(com.google.android.material.R.id.confirm_button);
        if (getDateSelector().isSelectionComplete()) {
            this.confirmButton.setEnabled(true);
        } else {
            this.confirmButton.setEnabled(false);
        }
        this.confirmButton.setTag(CONFIRM_BUTTON_TAG);
        CharSequence charSequence = this.positiveButtonText;
        if (charSequence != null) {
            this.confirmButton.setText(charSequence);
        } else {
            int i2 = this.positiveButtonTextResId;
            if (i2 != 0) {
                this.confirmButton.setText(i2);
            }
        }
        CharSequence charSequence2 = this.positiveButtonContentDescription;
        if (charSequence2 != null) {
            this.confirmButton.setContentDescription(charSequence2);
        } else if (this.positiveButtonContentDescriptionResId != 0) {
            this.confirmButton.setContentDescription(getContext().getResources().getText(this.positiveButtonContentDescriptionResId));
        }
        this.confirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Iterator it = MaterialDatePicker.this.onPositiveButtonClickListeners.iterator();
                while (it.hasNext()) {
                    ((MaterialPickerOnPositiveButtonClickListener) it.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
                }
                MaterialDatePicker.this.dismiss();
            }
        });
        Button button = (Button) viewInflate.findViewById(com.google.android.material.R.id.cancel_button);
        button.setTag(CANCEL_BUTTON_TAG);
        CharSequence charSequence3 = this.negativeButtonText;
        if (charSequence3 != null) {
            button.setText(charSequence3);
        } else {
            int i3 = this.negativeButtonTextResId;
            if (i3 != 0) {
                button.setText(i3);
            }
        }
        CharSequence text = this.negativeButtonContentDescription;
        if (text != null) {
            button.setContentDescription(text);
        } else if (this.negativeButtonContentDescriptionResId != 0) {
            text = getContext().getResources().getText(this.negativeButtonContentDescriptionResId);
            button.setContentDescription(text);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Iterator it = MaterialDatePicker.this.onNegativeButtonClickListeners.iterator();
                while (it.hasNext()) {
                    ((View.OnClickListener) it.next()).onClick(view);
                }
                MaterialDatePicker.this.dismiss();
            }
        });
        return viewInflate;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(@NonNull DialogInterface dialogInterface) {
        Iterator<DialogInterface.OnDismissListener> it = this.onDismissListeners.iterator();
        while (it.hasNext()) {
            it.next().onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(OVERRIDE_THEME_RES_ID, this.overrideThemeResId);
        bundle.putParcelable(DATE_SELECTOR_KEY, this.dateSelector);
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder(this.calendarConstraints);
        MaterialCalendar<S> materialCalendar = this.calendar;
        Month currentMonth = materialCalendar == null ? null : materialCalendar.getCurrentMonth();
        if (currentMonth != null) {
            builder.setOpenAt(currentMonth.timeInMillis);
        }
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, builder.build());
        bundle.putParcelable(DAY_VIEW_DECORATOR_KEY, this.dayViewDecorator);
        bundle.putInt(TITLE_TEXT_RES_ID_KEY, this.titleTextResId);
        bundle.putCharSequence(TITLE_TEXT_KEY, this.titleText);
        bundle.putInt(INPUT_MODE_KEY, this.inputMode);
        bundle.putInt(POSITIVE_BUTTON_TEXT_RES_ID_KEY, this.positiveButtonTextResId);
        bundle.putCharSequence(POSITIVE_BUTTON_TEXT_KEY, this.positiveButtonText);
        bundle.putInt(POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY, this.positiveButtonContentDescriptionResId);
        bundle.putCharSequence(POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY, this.positiveButtonContentDescription);
        bundle.putInt(NEGATIVE_BUTTON_TEXT_RES_ID_KEY, this.negativeButtonTextResId);
        bundle.putCharSequence(NEGATIVE_BUTTON_TEXT_KEY, this.negativeButtonText);
        bundle.putInt(NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY, this.negativeButtonContentDescriptionResId);
        bundle.putCharSequence(NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY, this.negativeButtonContentDescription);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.background);
            enableEdgeToEdgeIfNeeded(window);
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(com.google.android.material.R.dimen.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            window.setBackgroundDrawable(new InsetDrawable((Drawable) this.background, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset));
            window.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), rect));
        }
        startPickerFragment();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        this.pickerFragment.clearOnSelectionChangedListeners();
        super.onStop();
    }

    public boolean removeOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.onCancelListeners.remove(onCancelListener);
    }

    public boolean removeOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.onDismissListeners.remove(onDismissListener);
    }

    public boolean removeOnNegativeButtonClickListener(View.OnClickListener onClickListener) {
        return this.onNegativeButtonClickListeners.remove(onClickListener);
    }

    public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.remove(materialPickerOnPositiveButtonClickListener);
    }

    @VisibleForTesting
    public void updateHeader(String str) {
        this.headerSelectionText.setContentDescription(getHeaderContentDescription());
        this.headerSelectionText.setText(str);
    }
}
