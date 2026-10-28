package androidx.core.content;

import android.content.LocusId;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class LocusIdCompat {
    private final String mId;
    private final LocusId mWrapped;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @NonNull
        public static LocusId create(@NonNull String str) {
            return new LocusId(str);
        }

        @NonNull
        public static String getId(@NonNull LocusId locusId) {
            return locusId.getId();
        }
    }

    public LocusIdCompat(@NonNull String str) {
        this.mId = (String) Preconditions.checkStringNotEmpty(str, "id cannot be empty");
        this.mWrapped = Build.VERSION.SDK_INT >= 29 ? Api29Impl.create(str) : null;
    }

    @NonNull
    private String getSanitizedId() {
        return this.mId.length() + "_chars";
    }

    @NonNull
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static LocusIdCompat toLocusIdCompat(@NonNull LocusId locusId) {
        Preconditions.checkNotNull(locusId, "locusId cannot be null");
        return new LocusIdCompat((String) Preconditions.checkStringNotEmpty(Api29Impl.getId(locusId), "id cannot be empty"));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || LocusIdCompat.class != obj.getClass()) {
            return false;
        }
        String str = this.mId;
        String str2 = ((LocusIdCompat) obj).mId;
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        String str = this.mId;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    @NonNull
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public LocusId toLocusId() {
        return this.mWrapped;
    }

    @NonNull
    public String toString() {
        return androidx.appcompat.graphics.drawable.a.g(new StringBuilder("LocusIdCompat["), getSanitizedId(), "]");
    }
}
