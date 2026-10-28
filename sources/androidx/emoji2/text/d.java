package androidx.emoji2.text;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ FontRequestEmojiCompatConfig.FontRequestMetadataLoader f63c;

    public /* synthetic */ d(FontRequestEmojiCompatConfig.FontRequestMetadataLoader fontRequestMetadataLoader, int i2) {
        this.b = i2;
        this.f63c = fontRequestMetadataLoader;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        FontRequestEmojiCompatConfig.FontRequestMetadataLoader fontRequestMetadataLoader = this.f63c;
        switch (i2) {
            case 0:
                fontRequestMetadataLoader.createMetadata();
                break;
            default:
                fontRequestMetadataLoader.loadInternal();
                break;
        }
    }
}
