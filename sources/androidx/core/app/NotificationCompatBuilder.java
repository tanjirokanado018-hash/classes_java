package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private RemoteViews mBigContentView;
    private final Notification.Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private final Context mContext;
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private final Bundle mExtras = new Bundle();

    @RequiresApi(20)
    public static class Api20Impl {
        private Api20Impl() {
        }

        @DoNotInline
        public static Notification.Builder addAction(Notification.Builder builder, Notification.Action action) {
            return builder.addAction(action);
        }

        @DoNotInline
        public static Notification.Action.Builder addExtras(Notification.Action.Builder builder, Bundle bundle) {
            return builder.addExtras(bundle);
        }

        @DoNotInline
        public static Notification.Action.Builder addRemoteInput(Notification.Action.Builder builder, android.app.RemoteInput remoteInput) {
            return builder.addRemoteInput(remoteInput);
        }

        @DoNotInline
        public static Notification.Action build(Notification.Action.Builder builder) {
            return builder.build();
        }

        @DoNotInline
        public static Notification.Action.Builder createBuilder(int i2, CharSequence charSequence, PendingIntent pendingIntent) {
            return new Notification.Action.Builder(i2, charSequence, pendingIntent);
        }

        @DoNotInline
        public static String getGroup(Notification notification) {
            return notification.getGroup();
        }

        @DoNotInline
        public static Notification.Builder setGroup(Notification.Builder builder, String str) {
            return builder.setGroup(str);
        }

        @DoNotInline
        public static Notification.Builder setGroupSummary(Notification.Builder builder, boolean z2) {
            return builder.setGroupSummary(z2);
        }

        @DoNotInline
        public static Notification.Builder setLocalOnly(Notification.Builder builder, boolean z2) {
            return builder.setLocalOnly(z2);
        }

        @DoNotInline
        public static Notification.Builder setSortKey(Notification.Builder builder, String str) {
            return builder.setSortKey(str);
        }
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static Notification.Builder addPerson(Notification.Builder builder, String str) {
            return builder.addPerson(str);
        }

        @DoNotInline
        public static Notification.Builder setCategory(Notification.Builder builder, String str) {
            return builder.setCategory(str);
        }

        @DoNotInline
        public static Notification.Builder setColor(Notification.Builder builder, int i2) {
            return builder.setColor(i2);
        }

        @DoNotInline
        public static Notification.Builder setPublicVersion(Notification.Builder builder, Notification notification) {
            return builder.setPublicVersion(notification);
        }

        @DoNotInline
        public static Notification.Builder setSound(Notification.Builder builder, Uri uri, Object obj) {
            return builder.setSound(uri, (AudioAttributes) obj);
        }

        @DoNotInline
        public static Notification.Builder setVisibility(Notification.Builder builder, int i2) {
            return builder.setVisibility(i2);
        }
    }

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static Notification.Action.Builder createBuilder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
            return new Notification.Action.Builder(icon, charSequence, pendingIntent);
        }

        @DoNotInline
        public static Notification.Builder setLargeIcon(Notification.Builder builder, Icon icon) {
            return builder.setLargeIcon(icon);
        }

        @DoNotInline
        public static Notification.Builder setSmallIcon(Notification.Builder builder, Object obj) {
            return builder.setSmallIcon((Icon) obj);
        }
    }

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        public static Notification.Action.Builder setAllowGeneratedReplies(Notification.Action.Builder builder, boolean z2) {
            return builder.setAllowGeneratedReplies(z2);
        }

        @DoNotInline
        public static Notification.Builder setCustomBigContentView(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomBigContentView(remoteViews);
        }

        @DoNotInline
        public static Notification.Builder setCustomContentView(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomContentView(remoteViews);
        }

        @DoNotInline
        public static Notification.Builder setCustomHeadsUpContentView(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomHeadsUpContentView(remoteViews);
        }

        @DoNotInline
        public static Notification.Builder setRemoteInputHistory(Notification.Builder builder, CharSequence[] charSequenceArr) {
            return builder.setRemoteInputHistory(charSequenceArr);
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static Notification.Builder createBuilder(Context context, String str) {
            return new Notification.Builder(context, str);
        }

        @DoNotInline
        public static Notification.Builder setBadgeIconType(Notification.Builder builder, int i2) {
            return builder.setBadgeIconType(i2);
        }

        @DoNotInline
        public static Notification.Builder setColorized(Notification.Builder builder, boolean z2) {
            return builder.setColorized(z2);
        }

        @DoNotInline
        public static Notification.Builder setGroupAlertBehavior(Notification.Builder builder, int i2) {
            return builder.setGroupAlertBehavior(i2);
        }

        @DoNotInline
        public static Notification.Builder setSettingsText(Notification.Builder builder, CharSequence charSequence) {
            return builder.setSettingsText(charSequence);
        }

        @DoNotInline
        public static Notification.Builder setShortcutId(Notification.Builder builder, String str) {
            return builder.setShortcutId(str);
        }

        @DoNotInline
        public static Notification.Builder setTimeoutAfter(Notification.Builder builder, long j2) {
            return builder.setTimeoutAfter(j2);
        }
    }

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        public static Notification.Builder addPerson(Notification.Builder builder, android.app.Person person) {
            return builder.addPerson(person);
        }

        @DoNotInline
        public static Notification.Action.Builder setSemanticAction(Notification.Action.Builder builder, int i2) {
            return builder.setSemanticAction(i2);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static Notification.Builder setAllowSystemGeneratedContextualActions(Notification.Builder builder, boolean z2) {
            return builder.setAllowSystemGeneratedContextualActions(z2);
        }

        @DoNotInline
        public static Notification.Builder setBubbleMetadata(Notification.Builder builder, Notification.BubbleMetadata bubbleMetadata) {
            return builder.setBubbleMetadata(bubbleMetadata);
        }

        @DoNotInline
        public static Notification.Action.Builder setContextual(Notification.Action.Builder builder, boolean z2) {
            return builder.setContextual(z2);
        }

        @DoNotInline
        public static Notification.Builder setLocusId(Notification.Builder builder, Object obj) {
            return builder.setLocusId((LocusId) obj);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        public static Notification.Action.Builder setAuthenticationRequired(Notification.Action.Builder builder, boolean z2) {
            return builder.setAuthenticationRequired(z2);
        }

        @DoNotInline
        public static Notification.Builder setForegroundServiceBehavior(Notification.Builder builder, int i2) {
            return builder.setForegroundServiceBehavior(i2);
        }
    }

    public NotificationCompatBuilder(NotificationCompat.Builder builder) {
        int i2;
        Object obj;
        this.mBuilderCompat = builder;
        Context context = builder.mContext;
        this.mContext = context;
        int i3 = Build.VERSION.SDK_INT;
        this.mBuilder = i3 >= 26 ? Api26Impl.createBuilder(context, builder.mChannelId) : new Notification.Builder(builder.mContext);
        Notification notification = builder.mNotification;
        this.mBuilder.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.mTickerView).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.mContentTitle).setContentText(builder.mContentText).setContentInfo(builder.mContentInfo).setContentIntent(builder.mContentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.mFullScreenIntent, (notification.flags & 128) != 0).setNumber(builder.mNumber).setProgress(builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate);
        if (i3 < 23) {
            Notification.Builder builder2 = this.mBuilder;
            IconCompat iconCompat = builder.mLargeIcon;
            builder2.setLargeIcon(iconCompat == null ? null : iconCompat.getBitmap());
        } else {
            Notification.Builder builder3 = this.mBuilder;
            IconCompat iconCompat2 = builder.mLargeIcon;
            Api23Impl.setLargeIcon(builder3, iconCompat2 == null ? null : iconCompat2.toIcon(context));
        }
        this.mBuilder.setSubText(builder.mSubText).setUsesChronometer(builder.mUseChronometer).setPriority(builder.mPriority);
        NotificationCompat.Style style = builder.mStyle;
        if (style instanceof NotificationCompat.CallStyle) {
            Iterator<NotificationCompat.Action> it = ((NotificationCompat.CallStyle) style).getActionsListWithSystemActions().iterator();
            while (it.hasNext()) {
                addAction(it.next());
            }
        } else {
            Iterator<NotificationCompat.Action> it2 = builder.mActions.iterator();
            while (it2.hasNext()) {
                addAction(it2.next());
            }
        }
        Bundle bundle = builder.mExtras;
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        int i4 = Build.VERSION.SDK_INT;
        this.mContentView = builder.mContentView;
        this.mBigContentView = builder.mBigContentView;
        this.mBuilder.setShowWhen(builder.mShowWhen);
        Api20Impl.setLocalOnly(this.mBuilder, builder.mLocalOnly);
        Api20Impl.setGroup(this.mBuilder, builder.mGroupKey);
        Api20Impl.setSortKey(this.mBuilder, builder.mSortKey);
        Api20Impl.setGroupSummary(this.mBuilder, builder.mGroupSummary);
        this.mGroupAlertBehavior = builder.mGroupAlertBehavior;
        Api21Impl.setCategory(this.mBuilder, builder.mCategory);
        Api21Impl.setColor(this.mBuilder, builder.mColor);
        Api21Impl.setVisibility(this.mBuilder, builder.mVisibility);
        Api21Impl.setPublicVersion(this.mBuilder, builder.mPublicVersion);
        Api21Impl.setSound(this.mBuilder, notification.sound, notification.audioAttributes);
        List listCombineLists = i4 < 28 ? combineLists(getPeople(builder.mPersonList), builder.mPeople) : builder.mPeople;
        if (listCombineLists != null && !listCombineLists.isEmpty()) {
            Iterator it3 = listCombineLists.iterator();
            while (it3.hasNext()) {
                Api21Impl.addPerson(this.mBuilder, (String) it3.next());
            }
        }
        this.mHeadsUpContentView = builder.mHeadsUpContentView;
        if (builder.mInvisibleActions.size() > 0) {
            Bundle bundle2 = builder.getExtras().getBundle("android.car.EXTENSIONS");
            bundle2 = bundle2 == null ? new Bundle() : bundle2;
            Bundle bundle3 = new Bundle(bundle2);
            Bundle bundle4 = new Bundle();
            for (int i5 = 0; i5 < builder.mInvisibleActions.size(); i5++) {
                bundle4.putBundle(Integer.toString(i5), NotificationCompatJellybean.getBundleForAction(builder.mInvisibleActions.get(i5)));
            }
            bundle2.putBundle("invisible_actions", bundle4);
            bundle3.putBundle("invisible_actions", bundle4);
            builder.getExtras().putBundle("android.car.EXTENSIONS", bundle2);
            this.mExtras.putBundle("android.car.EXTENSIONS", bundle3);
        }
        int i6 = Build.VERSION.SDK_INT;
        if (i6 >= 23 && (obj = builder.mSmallIcon) != null) {
            Api23Impl.setSmallIcon(this.mBuilder, obj);
        }
        if (i6 >= 24) {
            this.mBuilder.setExtras(builder.mExtras);
            Api24Impl.setRemoteInputHistory(this.mBuilder, builder.mRemoteInputHistory);
            RemoteViews remoteViews = builder.mContentView;
            if (remoteViews != null) {
                Api24Impl.setCustomContentView(this.mBuilder, remoteViews);
            }
            RemoteViews remoteViews2 = builder.mBigContentView;
            if (remoteViews2 != null) {
                Api24Impl.setCustomBigContentView(this.mBuilder, remoteViews2);
            }
            RemoteViews remoteViews3 = builder.mHeadsUpContentView;
            if (remoteViews3 != null) {
                Api24Impl.setCustomHeadsUpContentView(this.mBuilder, remoteViews3);
            }
        }
        if (i6 >= 26) {
            Api26Impl.setBadgeIconType(this.mBuilder, builder.mBadgeIcon);
            Api26Impl.setSettingsText(this.mBuilder, builder.mSettingsText);
            Api26Impl.setShortcutId(this.mBuilder, builder.mShortcutId);
            Api26Impl.setTimeoutAfter(this.mBuilder, builder.mTimeout);
            Api26Impl.setGroupAlertBehavior(this.mBuilder, builder.mGroupAlertBehavior);
            if (builder.mColorizedSet) {
                Api26Impl.setColorized(this.mBuilder, builder.mColorized);
            }
            if (!TextUtils.isEmpty(builder.mChannelId)) {
                this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        }
        if (i6 >= 28) {
            Iterator<Person> it4 = builder.mPersonList.iterator();
            while (it4.hasNext()) {
                Api28Impl.addPerson(this.mBuilder, it4.next().toAndroidPerson());
            }
        }
        int i7 = Build.VERSION.SDK_INT;
        if (i7 >= 29) {
            Api29Impl.setAllowSystemGeneratedContextualActions(this.mBuilder, builder.mAllowSystemGeneratedContextualActions);
            Api29Impl.setBubbleMetadata(this.mBuilder, NotificationCompat.BubbleMetadata.toPlatform(builder.mBubbleMetadata));
            LocusIdCompat locusIdCompat = builder.mLocusId;
            if (locusIdCompat != null) {
                Api29Impl.setLocusId(this.mBuilder, locusIdCompat.toLocusId());
            }
        }
        if (i7 >= 31 && (i2 = builder.mFgsDeferBehavior) != 0) {
            Api31Impl.setForegroundServiceBehavior(this.mBuilder, i2);
        }
        if (builder.mSilent) {
            if (this.mBuilderCompat.mGroupSummary) {
                this.mGroupAlertBehavior = 2;
            } else {
                this.mGroupAlertBehavior = 1;
            }
            this.mBuilder.setVibrate(null);
            this.mBuilder.setSound(null);
            int i8 = notification.defaults & (-4);
            notification.defaults = i8;
            this.mBuilder.setDefaults(i8);
            if (i7 >= 26) {
                if (TextUtils.isEmpty(this.mBuilderCompat.mGroupKey)) {
                    Api20Impl.setGroup(this.mBuilder, NotificationCompat.GROUP_KEY_SILENT);
                }
                Api26Impl.setGroupAlertBehavior(this.mBuilder, this.mGroupAlertBehavior);
            }
        }
    }

    private void addAction(NotificationCompat.Action action) {
        int i2 = Build.VERSION.SDK_INT;
        IconCompat iconCompat = action.getIconCompat();
        Notification.Action.Builder builderCreateBuilder = i2 >= 23 ? Api23Impl.createBuilder(iconCompat != null ? iconCompat.toIcon() : null, action.getTitle(), action.getActionIntent()) : Api20Impl.createBuilder(iconCompat != null ? iconCompat.getResId() : 0, action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (android.app.RemoteInput remoteInput : RemoteInput.fromCompat(action.getRemoteInputs())) {
                Api20Impl.addRemoteInput(builderCreateBuilder, remoteInput);
            }
        }
        Bundle bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 24) {
            Api24Impl.setAllowGeneratedReplies(builderCreateBuilder, action.getAllowGeneratedReplies());
        }
        bundle.putInt("android.support.action.semanticAction", action.getSemanticAction());
        if (i3 >= 28) {
            Api28Impl.setSemanticAction(builderCreateBuilder, action.getSemanticAction());
        }
        if (i3 >= 29) {
            Api29Impl.setContextual(builderCreateBuilder, action.isContextual());
        }
        if (i3 >= 31) {
            Api31Impl.setAuthenticationRequired(builderCreateBuilder, action.isAuthenticationRequired());
        }
        bundle.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
        Api20Impl.addExtras(builderCreateBuilder, bundle);
        Api20Impl.addAction(this.mBuilder, Api20Impl.build(builderCreateBuilder));
    }

    @Nullable
    private static List<String> combineLists(@Nullable List<String> list, @Nullable List<String> list2) {
        if (list == null) {
            return list2;
        }
        if (list2 == null) {
            return list;
        }
        ArraySet arraySet = new ArraySet(list2.size() + list.size());
        arraySet.addAll(list);
        arraySet.addAll(list2);
        return new ArrayList(arraySet);
    }

    @Nullable
    private static List<String> getPeople(@Nullable List<Person> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Person> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resolveToLegacyUri());
        }
        return arrayList;
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -4;
    }

    public Notification build() {
        Bundle extras;
        RemoteViews remoteViewsMakeHeadsUpContentView;
        RemoteViews remoteViewsMakeBigContentView;
        NotificationCompat.Style style = this.mBuilderCompat.mStyle;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews remoteViewsMakeContentView = style != null ? style.makeContentView(this) : null;
        Notification notificationBuildInternal = buildInternal();
        if (remoteViewsMakeContentView != null || (remoteViewsMakeContentView = this.mBuilderCompat.mContentView) != null) {
            notificationBuildInternal.contentView = remoteViewsMakeContentView;
        }
        if (style != null && (remoteViewsMakeBigContentView = style.makeBigContentView(this)) != null) {
            notificationBuildInternal.bigContentView = remoteViewsMakeBigContentView;
        }
        if (style != null && (remoteViewsMakeHeadsUpContentView = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this)) != null) {
            notificationBuildInternal.headsUpContentView = remoteViewsMakeHeadsUpContentView;
        }
        if (style != null && (extras = NotificationCompat.getExtras(notificationBuildInternal)) != null) {
            style.addCompatExtras(extras);
        }
        return notificationBuildInternal;
    }

    public Notification buildInternal() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            return this.mBuilder.build();
        }
        if (i2 >= 24) {
            Notification notificationBuild = this.mBuilder.build();
            if (this.mGroupAlertBehavior != 0) {
                if (Api20Impl.getGroup(notificationBuild) != null && (notificationBuild.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                    removeSoundAndVibration(notificationBuild);
                }
                if (Api20Impl.getGroup(notificationBuild) != null && (notificationBuild.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notificationBuild);
                }
            }
            return notificationBuild;
        }
        this.mBuilder.setExtras(this.mExtras);
        Notification notificationBuild2 = this.mBuilder.build();
        RemoteViews remoteViews = this.mContentView;
        if (remoteViews != null) {
            notificationBuild2.contentView = remoteViews;
        }
        RemoteViews remoteViews2 = this.mBigContentView;
        if (remoteViews2 != null) {
            notificationBuild2.bigContentView = remoteViews2;
        }
        RemoteViews remoteViews3 = this.mHeadsUpContentView;
        if (remoteViews3 != null) {
            notificationBuild2.headsUpContentView = remoteViews3;
        }
        if (this.mGroupAlertBehavior != 0) {
            if (Api20Impl.getGroup(notificationBuild2) != null && (notificationBuild2.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                removeSoundAndVibration(notificationBuild2);
            }
            if (Api20Impl.getGroup(notificationBuild2) != null && (notificationBuild2.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                removeSoundAndVibration(notificationBuild2);
            }
        }
        return notificationBuild2;
    }

    @Override // androidx.core.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.mBuilder;
    }

    public Context getContext() {
        return this.mContext;
    }
}
