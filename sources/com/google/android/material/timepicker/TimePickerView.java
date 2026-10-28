package com.google.android.material.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import java.util.Locale;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class TimePickerView extends ConstraintLayout implements TimePickerControls {
    static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private final ClockFaceView clockFace;
    private final ClockHandView clockHandView;
    private final Chip hourView;
    private final Chip minuteView;
    private OnDoubleTapListener onDoubleTapListener;
    private OnPeriodChangeListener onPeriodChangeListener;
    private OnSelectionChange onSelectionChangeListener;
    private final View.OnClickListener selectionListener;
    private final MaterialButtonToggleGroup toggle;

    public interface OnDoubleTapListener {
        void onDoubleTap();
    }

    public interface OnPeriodChangeListener {
        void onPeriodChange(int i2);
    }

    public interface OnSelectionChange {
        void onSelectionChanged(int i2);
    }

    public TimePickerView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(MaterialButtonToggleGroup materialButtonToggleGroup, int i2, boolean z2) {
        OnPeriodChangeListener onPeriodChangeListener;
        if (z2 && (onPeriodChangeListener = this.onPeriodChangeListener) != null) {
            onPeriodChangeListener.onPeriodChange(i2 == R.id.material_clock_period_pm_button ? 1 : 0);
        }
    }

    private void setUpDisplay() {
        this.minuteView.setTag(R.id.selection_type, 12);
        this.hourView.setTag(R.id.selection_type, 10);
        this.minuteView.setOnClickListener(this.selectionListener);
        this.hourView.setOnClickListener(this.selectionListener);
        this.minuteView.setAccessibilityClassName(GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
        this.hourView.setAccessibilityClassName(GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void setupDoubleTap() {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.google.android.material.timepicker.TimePickerView.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                OnDoubleTapListener onDoubleTapListener = TimePickerView.this.onDoubleTapListener;
                if (onDoubleTapListener == null) {
                    return false;
                }
                onDoubleTapListener.onDoubleTap();
                return true;
            }
        });
        View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: com.google.android.material.timepicker.TimePickerView.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (((Checkable) view).isChecked()) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
                return false;
            }
        };
        this.minuteView.setOnTouchListener(onTouchListener);
        this.hourView.setOnTouchListener(onTouchListener);
    }

    private void updateSelection(Chip chip, boolean z2) {
        chip.setChecked(z2);
        ViewCompat.setAccessibilityLiveRegion(chip, z2 ? 2 : 0);
    }

    public void addOnRotateListener(ClockHandView.OnRotateListener onRotateListener) {
        this.clockHandView.addOnRotateListener(onRotateListener);
    }

    public int getCurrentLevel() {
        return this.clockFace.getCurrentLevel();
    }

    @Override // android.view.View
    public void onVisibilityChanged(@NonNull View view, int i2) {
        super.onVisibilityChanged(view, i2);
        if (view == this && i2 == 0) {
            this.hourView.sendAccessibilityEvent(8);
        }
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    public void setActiveSelection(int i2) {
        updateSelection(this.minuteView, i2 == 12);
        updateSelection(this.hourView, i2 == 10);
    }

    public void setAnimateOnTouchUp(boolean z2) {
        this.clockHandView.setAnimateOnTouchUp(z2);
    }

    public void setCurrentLevel(int i2) {
        this.clockFace.setCurrentLevel(i2);
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    public void setHandRotation(float f2) {
        this.clockHandView.setHandRotation(f2);
    }

    public void setHourClickDelegate(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        ViewCompat.setAccessibilityDelegate(this.minuteView, accessibilityDelegateCompat);
    }

    public void setMinuteHourDelegate(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        ViewCompat.setAccessibilityDelegate(this.hourView, accessibilityDelegateCompat);
    }

    public void setOnActionUpListener(ClockHandView.OnActionUpListener onActionUpListener) {
        this.clockHandView.setOnActionUpListener(onActionUpListener);
    }

    public void setOnDoubleTapListener(@Nullable OnDoubleTapListener onDoubleTapListener) {
        this.onDoubleTapListener = onDoubleTapListener;
    }

    public void setOnPeriodChangeListener(OnPeriodChangeListener onPeriodChangeListener) {
        this.onPeriodChangeListener = onPeriodChangeListener;
    }

    public void setOnSelectionChangeListener(OnSelectionChange onSelectionChange) {
        this.onSelectionChangeListener = onSelectionChange;
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    public void setValues(String[] strArr, @StringRes int i2) {
        this.clockFace.setValues(strArr, i2);
    }

    public void showToggle() {
        this.toggle.setVisibility(0);
    }

    @Override // com.google.android.material.timepicker.TimePickerControls
    @SuppressLint({"DefaultLocale"})
    public void updateTime(int i2, int i3, int i4) {
        this.toggle.check(i2 == 1 ? R.id.material_clock_period_pm_button : R.id.material_clock_period_am_button);
        Locale locale = getResources().getConfiguration().locale;
        String str = String.format(locale, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i4));
        String str2 = String.format(locale, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i3));
        if (!TextUtils.equals(this.minuteView.getText(), str)) {
            this.minuteView.setText(str);
        }
        if (TextUtils.equals(this.hourView.getText(), str2)) {
            return;
        }
        this.hourView.setText(str2);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setHandRotation(float f2, boolean z2) {
        this.clockHandView.setHandRotation(f2, z2);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.selectionListener = new View.OnClickListener() { // from class: com.google.android.material.timepicker.TimePickerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TimePickerView.this.onSelectionChangeListener != null) {
                    TimePickerView.this.onSelectionChangeListener.onSelectionChanged(((Integer) view.getTag(R.id.selection_type)).intValue());
                }
            }
        };
        LayoutInflater.from(context).inflate(R.layout.material_timepicker, this);
        this.clockFace = (ClockFaceView) findViewById(R.id.material_clock_face);
        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) findViewById(R.id.material_clock_period_toggle);
        this.toggle = materialButtonToggleGroup;
        materialButtonToggleGroup.addOnButtonCheckedListener(new c(1, this));
        this.minuteView = (Chip) findViewById(R.id.material_minute_tv);
        this.hourView = (Chip) findViewById(R.id.material_hour_tv);
        this.clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        setupDoubleTap();
        setUpDisplay();
    }
}
