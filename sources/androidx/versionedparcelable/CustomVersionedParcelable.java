package androidx.versionedparcelable;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class CustomVersionedParcelable implements VersionedParcelable {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onPostParceling() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onPreParceling(boolean z2) {
    }
}
