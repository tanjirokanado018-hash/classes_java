package androidx.core.content;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import androidx.core.util.Predicate;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class IntentSanitizer {
    private static final String TAG = "IntentSanitizer";
    private boolean mAllowAnyComponent;
    private boolean mAllowClipDataText;
    private boolean mAllowIdentifier;
    private boolean mAllowSelector;
    private boolean mAllowSourceBounds;
    private Predicate<String> mAllowedActions;
    private Predicate<String> mAllowedCategories;
    private Predicate<ClipData> mAllowedClipData;
    private Predicate<Uri> mAllowedClipDataUri;
    private Predicate<ComponentName> mAllowedComponents;
    private Predicate<Uri> mAllowedData;
    private Map<String, Predicate<Object>> mAllowedExtras;
    private int mAllowedFlags;
    private Predicate<String> mAllowedPackages;
    private Predicate<String> mAllowedTypes;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static String getIdentifier(Intent intent) {
            return intent.getIdentifier();
        }

        @DoNotInline
        public static Intent setIdentifier(Intent intent, String str) {
            return intent.setIdentifier(str);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        public static void checkOtherMembers(int i2, ClipData.Item item, Consumer<String> consumer) {
            if (item.getHtmlText() == null && item.getIntent() == null && item.getTextLinks() == null) {
                return;
            }
            consumer.accept("ClipData item at position " + i2 + " contains htmlText, textLinks or intent: " + item);
        }
    }

    public static final class Builder {
        private static final int HISTORY_STACK_FLAGS = 2112614400;
        private static final int RECEIVER_FLAGS = 2015363072;
        private boolean mAllowAnyComponent;
        private boolean mAllowIdentifier;
        private boolean mAllowSelector;
        private boolean mAllowSomeComponents;
        private boolean mAllowSourceBounds;
        private int mAllowedFlags;
        private Predicate<String> mAllowedActions = new d(0);
        private Predicate<Uri> mAllowedData = new d(1);
        private Predicate<String> mAllowedTypes = new d(2);
        private Predicate<String> mAllowedCategories = new d(3);
        private Predicate<String> mAllowedPackages = new d(4);
        private Predicate<ComponentName> mAllowedComponents = new d(5);
        private Map<String, Predicate<Object>> mAllowedExtras = new HashMap();
        private boolean mAllowClipDataText = false;
        private Predicate<Uri> mAllowedClipDataUri = new d(6);
        private Predicate<ClipData> mAllowedClipData = new d(7);

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowAnyComponent$10(ComponentName componentName) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowClipDataUriWithAuthority$11(String str, Uri uri) {
            return str.equals(uri.getAuthority());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowComponentWithPackage$9(String str, ComponentName componentName) {
            return str.equals(componentName.getPackageName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowDataWithAuthority$8(String str, Uri uri) {
            return str.equals(uri.getAuthority());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowExtra$12(Object obj) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowExtra$13(Class cls, Predicate predicate, Object obj) {
            return cls.isInstance(obj) && predicate.test(cls.cast(obj));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowExtra$14(Object obj) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowExtraOutput$16(String str, Uri uri) {
            return str.equals(uri.getAuthority());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$allowExtraStreamUriWithAuthority$15(String str, Uri uri) {
            return str.equals(uri.getAuthority());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$0(String str) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$1(Uri uri) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$2(String str) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$3(String str) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$4(String str) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$5(ComponentName componentName) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$6(Uri uri) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$new$7(ClipData clipData) {
            return false;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowAction(@NonNull Predicate<String> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedActions = this.mAllowedActions.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowAnyComponent() {
            this.mAllowAnyComponent = true;
            this.mAllowedComponents = new d(9);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowCategory(@NonNull Predicate<String> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedCategories = this.mAllowedCategories.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowClipData(@NonNull Predicate<ClipData> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedClipData = this.mAllowedClipData.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowClipDataText() {
            this.mAllowClipDataText = true;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowClipDataUri(@NonNull Predicate<Uri> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedClipDataUri = this.mAllowedClipDataUri.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowClipDataUriWithAuthority(@NonNull String str) {
            Preconditions.checkNotNull(str);
            return allowClipDataUri(new c(str, 4));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowComponent(@NonNull ComponentName componentName) {
            Preconditions.checkNotNull(componentName);
            Objects.requireNonNull(componentName);
            return allowComponent(new e(0, componentName));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowComponentWithPackage(@NonNull String str) {
            Preconditions.checkNotNull(str);
            return allowComponent(new c(str, 2));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowData(@NonNull Predicate<Uri> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedData = this.mAllowedData.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowDataWithAuthority(@NonNull String str) {
            Preconditions.checkNotNull(str);
            allowData(new c(str, 3));
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowExtra(@NonNull String str, @NonNull Predicate<Object> predicate) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(predicate);
            Predicate<Object> dVar = this.mAllowedExtras.get(str);
            if (dVar == null) {
                dVar = new d(10);
            }
            this.mAllowedExtras.put(str, dVar.or(predicate));
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowExtraOutput(@NonNull Predicate<Uri> predicate) {
            allowExtra("output", Uri.class, predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowExtraStream(@NonNull Predicate<Uri> predicate) {
            allowExtra("android.intent.extra.STREAM", Uri.class, predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowExtraStreamUriWithAuthority(@NonNull String str) {
            Preconditions.checkNotNull(str);
            allowExtra("android.intent.extra.STREAM", Uri.class, new c(str, 8));
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowFlags(int i2) {
            this.mAllowedFlags = i2 | this.mAllowedFlags;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowHistoryStackFlags() {
            this.mAllowedFlags |= HISTORY_STACK_FLAGS;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowIdentifier() {
            this.mAllowIdentifier = true;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowPackage(@NonNull Predicate<String> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedPackages = this.mAllowedPackages.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowReceiverFlags() {
            this.mAllowedFlags |= RECEIVER_FLAGS;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowSelector() {
            this.mAllowSelector = true;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowSourceBounds() {
            this.mAllowSourceBounds = true;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowType(@NonNull Predicate<String> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowedTypes = this.mAllowedTypes.or(predicate);
            return this;
        }

        @NonNull
        public IntentSanitizer build() {
            boolean z2 = this.mAllowAnyComponent;
            if ((z2 && this.mAllowSomeComponents) || (!z2 && !this.mAllowSomeComponents)) {
                throw new SecurityException("You must call either allowAnyComponent or one or more of the allowComponent methods; but not both.");
            }
            IntentSanitizer intentSanitizer = new IntentSanitizer();
            intentSanitizer.mAllowedFlags = this.mAllowedFlags;
            intentSanitizer.mAllowedActions = this.mAllowedActions;
            intentSanitizer.mAllowedData = this.mAllowedData;
            intentSanitizer.mAllowedTypes = this.mAllowedTypes;
            intentSanitizer.mAllowedCategories = this.mAllowedCategories;
            intentSanitizer.mAllowedPackages = this.mAllowedPackages;
            intentSanitizer.mAllowAnyComponent = this.mAllowAnyComponent;
            intentSanitizer.mAllowedComponents = this.mAllowedComponents;
            intentSanitizer.mAllowedExtras = this.mAllowedExtras;
            intentSanitizer.mAllowClipDataText = this.mAllowClipDataText;
            intentSanitizer.mAllowedClipDataUri = this.mAllowedClipDataUri;
            intentSanitizer.mAllowedClipData = this.mAllowedClipData;
            intentSanitizer.mAllowIdentifier = this.mAllowIdentifier;
            intentSanitizer.mAllowSelector = this.mAllowSelector;
            intentSanitizer.mAllowSourceBounds = this.mAllowSourceBounds;
            return intentSanitizer;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowAction(@NonNull String str) {
            Preconditions.checkNotNull(str);
            Objects.requireNonNull(str);
            allowAction(new c(str, 7));
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowCategory(@NonNull String str) {
            Preconditions.checkNotNull(str);
            Objects.requireNonNull(str);
            return allowCategory(new c(str, 5));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowComponent(@NonNull Predicate<ComponentName> predicate) {
            Preconditions.checkNotNull(predicate);
            this.mAllowSomeComponents = true;
            this.mAllowedComponents = this.mAllowedComponents.or(predicate);
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowExtra(@NonNull String str, @NonNull Class<?> cls) {
            return allowExtra(str, cls, new d(8));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowExtraOutput(@NonNull String str) {
            allowExtra("output", Uri.class, new c(str, 0));
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowPackage(@NonNull String str) {
            Preconditions.checkNotNull(str);
            Objects.requireNonNull(str);
            return allowPackage(new c(str, 6));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder allowType(@NonNull String str) {
            Preconditions.checkNotNull(str);
            Objects.requireNonNull(str);
            return allowType(new c(str, 1));
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public <T> Builder allowExtra(@NonNull String str, @NonNull Class<T> cls, @NonNull Predicate<T> predicate) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(cls);
            Preconditions.checkNotNull(predicate);
            return allowExtra(str, new androidx.core.util.a(cls, predicate));
        }
    }

    private IntentSanitizer() {
    }

    private static void checkOtherMembers(int i2, ClipData.Item item, Consumer<String> consumer) {
        if (item.getHtmlText() == null && item.getIntent() == null) {
            return;
        }
        consumer.accept("ClipData item at position " + i2 + " contains htmlText, textLinks or intent: " + item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sanitizeByFiltering$0(String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sanitizeByThrowing$1(String str) {
        throw new SecurityException(str);
    }

    private void putExtra(Intent intent, String str, Object obj) {
        if (obj == null) {
            intent.getExtras().putString(str, null);
            return;
        }
        if (obj instanceof Parcelable) {
            intent.putExtra(str, (Parcelable) obj);
            return;
        }
        if (obj instanceof Parcelable[]) {
            intent.putExtra(str, (Parcelable[]) obj);
        } else if (obj instanceof Serializable) {
            intent.putExtra(str, (Serializable) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0096 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:36:0x0098 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x009a  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a9  */
    public static void sanitizeClipData(@NonNull Intent intent, Intent intent2, Predicate<ClipData> predicate, boolean z2, Predicate<Uri> predicate2, Consumer<String> consumer) {
        Uri uri;
        StringBuilder sb;
        ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return;
        }
        if (predicate != null && predicate.test(clipData)) {
            intent2.setClipData(clipData);
            return;
        }
        ClipData clipData2 = null;
        for (int i2 = 0; i2 < clipData.getItemCount(); i2++) {
            ClipData.Item itemAt = clipData.getItemAt(i2);
            if (Build.VERSION.SDK_INT >= 31) {
                Api31Impl.checkOtherMembers(i2, itemAt, consumer);
            } else {
                checkOtherMembers(i2, itemAt, consumer);
            }
            CharSequence text = itemAt.getText();
            if (!z2) {
                if (text != null) {
                    consumer.accept("Item text cannot contain value. Item position: " + i2 + ". Text: " + ((Object) itemAt.getText()));
                }
                text = null;
            }
            Uri uri2 = itemAt.getUri();
            if (predicate2 == null) {
                if (uri2 != null) {
                    sb = new StringBuilder("Item URI is not allowed. Item position: ");
                }
                uri = null;
                if (text == null || uri != null) {
                    if (clipData2 == null) {
                        clipData2 = new ClipData(clipData.getDescription(), new ClipData.Item(text, null, uri));
                    } else {
                        clipData2.addItem(new ClipData.Item(text, null, uri));
                    }
                }
            } else {
                if (uri2 == null || predicate2.test(itemAt.getUri())) {
                    uri = itemAt.getUri();
                } else {
                    sb = new StringBuilder("Item URI is not allowed. Item position: ");
                }
                if (text == null) {
                    if (clipData2 == null) {
                        clipData2 = new ClipData(clipData.getDescription(), new ClipData.Item(text, null, uri));
                    } else {
                        clipData2.addItem(new ClipData.Item(text, null, uri));
                    }
                } else if (clipData2 == null) {
                    clipData2 = new ClipData(clipData.getDescription(), new ClipData.Item(text, null, uri));
                } else {
                    clipData2.addItem(new ClipData.Item(text, null, uri));
                }
            }
            sb.append(i2);
            sb.append(". URI: ");
            sb.append(itemAt.getUri());
            consumer.accept(sb.toString());
            uri = null;
            if (text == null) {
                if (clipData2 == null) {
                    clipData2 = new ClipData(clipData.getDescription(), new ClipData.Item(text, null, uri));
                } else {
                    clipData2.addItem(new ClipData.Item(text, null, uri));
                }
            } else if (clipData2 == null) {
                clipData2 = new ClipData(clipData.getDescription(), new ClipData.Item(text, null, uri));
            } else {
                clipData2.addItem(new ClipData.Item(text, null, uri));
            }
        }
        if (clipData2 != null) {
            intent2.setClipData(clipData2);
        }
    }

    @NonNull
    public Intent sanitize(@NonNull Intent intent, @NonNull Consumer<String> consumer) {
        String str;
        Intent intent2 = new Intent();
        ComponentName component = intent.getComponent();
        if ((!this.mAllowAnyComponent || component != null) && !this.mAllowedComponents.test(component)) {
            consumer.accept("Component is not allowed: " + component);
            component = new ComponentName("android", "java.lang.Void");
        }
        intent2.setComponent(component);
        String str2 = intent.getPackage();
        if (str2 == null || this.mAllowedPackages.test(str2)) {
            intent2.setPackage(str2);
        } else {
            consumer.accept("Package is not allowed: ".concat(str2));
        }
        int flags = this.mAllowedFlags | intent.getFlags();
        int i2 = this.mAllowedFlags;
        if (flags == i2) {
            intent2.setFlags(intent.getFlags());
        } else {
            intent2.setFlags(intent.getFlags() & i2);
            consumer.accept("The intent contains flags that are not allowed: 0x" + Integer.toHexString(intent.getFlags() & (~this.mAllowedFlags)));
        }
        String action = intent.getAction();
        if (action == null || this.mAllowedActions.test(action)) {
            intent2.setAction(action);
        } else {
            consumer.accept("Action is not allowed: ".concat(action));
        }
        Uri data = intent.getData();
        if (data == null || this.mAllowedData.test(data)) {
            intent2.setData(data);
        } else {
            consumer.accept("Data is not allowed: " + data);
        }
        String type = intent.getType();
        if (type == null || this.mAllowedTypes.test(type)) {
            intent2.setDataAndType(intent2.getData(), type);
        } else {
            consumer.accept("Type is not allowed: ".concat(type));
        }
        Set<String> categories = intent.getCategories();
        if (categories != null) {
            for (String str3 : categories) {
                if (this.mAllowedCategories.test(str3)) {
                    intent2.addCategory(str3);
                } else {
                    consumer.accept("Category is not allowed: " + str3);
                }
            }
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String str4 : extras.keySet()) {
                if (str4.equals("android.intent.extra.STREAM") && (this.mAllowedFlags & 1) == 0) {
                    str = "Allowing Extra Stream requires also allowing at least  FLAG_GRANT_READ_URI_PERMISSION Flag.";
                } else if (!str4.equals("output") || ((~this.mAllowedFlags) & 3) == 0) {
                    Object obj = extras.get(str4);
                    Predicate<Object> predicate = this.mAllowedExtras.get(str4);
                    if (predicate == null || !predicate.test(obj)) {
                        str = "Extra is not allowed. Key: " + str4 + ". Value: " + obj;
                    } else {
                        putExtra(intent2, str4, obj);
                    }
                } else {
                    str = "Allowing Extra Output requires also allowing FLAG_GRANT_READ_URI_PERMISSION and FLAG_GRANT_WRITE_URI_PERMISSION Flags.";
                }
                consumer.accept(str);
            }
        }
        sanitizeClipData(intent, intent2, this.mAllowedClipData, this.mAllowClipDataText, this.mAllowedClipDataUri, consumer);
        if (Build.VERSION.SDK_INT >= 29) {
            if (this.mAllowIdentifier) {
                Api29Impl.setIdentifier(intent2, Api29Impl.getIdentifier(intent));
            } else if (Api29Impl.getIdentifier(intent) != null) {
                consumer.accept("Identifier is not allowed: " + Api29Impl.getIdentifier(intent));
            }
        }
        if (this.mAllowSelector) {
            intent2.setSelector(intent.getSelector());
        } else if (intent.getSelector() != null) {
            consumer.accept("Selector is not allowed: " + intent.getSelector());
        }
        if (this.mAllowSourceBounds) {
            intent2.setSourceBounds(intent.getSourceBounds());
        } else if (intent.getSourceBounds() != null) {
            consumer.accept("SourceBounds is not allowed: " + intent.getSourceBounds());
        }
        return intent2;
    }

    @NonNull
    public Intent sanitizeByFiltering(@NonNull Intent intent) {
        return sanitize(intent, new b(1));
    }

    @NonNull
    public Intent sanitizeByThrowing(@NonNull Intent intent) {
        return sanitize(intent, new b(0));
    }
}
