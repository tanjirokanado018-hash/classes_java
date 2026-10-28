package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class Month implements Comparable<Month>, Parcelable {
    public static final Parcelable.Creator<Month> CREATOR = new Parcelable.Creator<Month>() { // from class: com.google.android.material.datepicker.Month.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NonNull
        public Month createFromParcel(@NonNull Parcel parcel) {
            return Month.create(parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NonNull
        public Month[] newArray(int i2) {
            return new Month[i2];
        }
    };
    final int daysInMonth;
    final int daysInWeek;

    @NonNull
    private final Calendar firstOfMonth;

    @Nullable
    private String longName;
    final int month;
    final long timeInMillis;
    final int year;

    private Month(@NonNull Calendar calendar) {
        calendar.set(5, 1);
        Calendar dayCopy = UtcDates.getDayCopy(calendar);
        this.firstOfMonth = dayCopy;
        this.month = dayCopy.get(2);
        this.year = dayCopy.get(1);
        this.daysInWeek = dayCopy.getMaximum(7);
        this.daysInMonth = dayCopy.getActualMaximum(5);
        this.timeInMillis = dayCopy.getTimeInMillis();
    }

    @NonNull
    public static Month create(int i2, int i3) {
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        utcCalendar.set(1, i2);
        utcCalendar.set(2, i3);
        return new Month(utcCalendar);
    }

    @NonNull
    public static Month current() {
        return new Month(UtcDates.getTodayCalendar());
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull Month month) {
        return this.firstOfMonth.compareTo(month.firstOfMonth);
    }

    public int daysFromStartOfWeekToFirstOfMonth(int i2) {
        int i3 = this.firstOfMonth.get(7);
        if (i2 <= 0) {
            i2 = this.firstOfMonth.getFirstDayOfWeek();
        }
        int i4 = i3 - i2;
        return i4 < 0 ? i4 + this.daysInWeek : i4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Month)) {
            return false;
        }
        Month month = (Month) obj;
        return this.month == month.month && this.year == month.year;
    }

    public long getDay(int i2) {
        Calendar dayCopy = UtcDates.getDayCopy(this.firstOfMonth);
        dayCopy.set(5, i2);
        return dayCopy.getTimeInMillis();
    }

    public int getDayOfMonth(long j2) {
        Calendar dayCopy = UtcDates.getDayCopy(this.firstOfMonth);
        dayCopy.setTimeInMillis(j2);
        return dayCopy.get(5);
    }

    @NonNull
    public String getLongName() {
        if (this.longName == null) {
            this.longName = DateStrings.getYearMonth(this.firstOfMonth.getTimeInMillis());
        }
        return this.longName;
    }

    public long getStableId() {
        return this.firstOfMonth.getTimeInMillis();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.month), Integer.valueOf(this.year)});
    }

    @NonNull
    public Month monthsLater(int i2) {
        Calendar dayCopy = UtcDates.getDayCopy(this.firstOfMonth);
        dayCopy.add(2, i2);
        return new Month(dayCopy);
    }

    public int monthsUntil(@NonNull Month month) {
        if (!(this.firstOfMonth instanceof GregorianCalendar)) {
            throw new IllegalArgumentException("Only Gregorian calendars are supported.");
        }
        return (month.month - this.month) + ((month.year - this.year) * 12);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
    }

    @NonNull
    public static Month create(long j2) {
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        utcCalendar.setTimeInMillis(j2);
        return new Month(utcCalendar);
    }
}
