package androidx.lifecycle;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class MutableLiveData<T> extends LiveData<T> {
    public MutableLiveData() {
    }

    @Override // androidx.lifecycle.LiveData
    public void postValue(T t2) {
        super.postValue(t2);
    }

    @Override // androidx.lifecycle.LiveData
    public void setValue(T t2) {
        super.setValue(t2);
    }

    public MutableLiveData(T t2) {
        super(t2);
    }
}
