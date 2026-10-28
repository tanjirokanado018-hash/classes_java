package androidx.profileinstaller;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class WritableFileSection {
    final byte[] mContents;
    final int mExpectedInflateSize;
    final boolean mNeedsCompression;
    final FileSectionType mType;

    public WritableFileSection(@NonNull FileSectionType fileSectionType, int i2, @NonNull byte[] bArr, boolean z2) {
        this.mType = fileSectionType;
        this.mExpectedInflateSize = i2;
        this.mContents = bArr;
        this.mNeedsCompression = z2;
    }
}
