package com.example.beeonemachinetest.ui.album;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beeonemachinetest.data.models.ProgressModel;
import com.example.beeonemachinetest.data.remote.bean.AlbumBean;
import com.example.beeonemachinetest.data.remote.bean.HomeBean;
import com.example.beeonemachinetest.data.repository.AppRepository;
import com.example.beeonemachinetest.utils.InternetUtils;
import com.example.beeonemachinetest.utils.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class AlbumViewModel extends ViewModel {

    private AppRepository appRepository;
    private final InternetUtils internetUtils;
    private final ToastUtils toastUtils;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<AlbumBean>> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<ProgressModel> mIsLoading = new MutableLiveData<ProgressModel>();


    @Inject
    public AlbumViewModel(AppRepository appRepository, InternetUtils internetUtils, ToastUtils toastUtils) {
        this.appRepository = appRepository;
        this.internetUtils = internetUtils;
        this.toastUtils = toastUtils;
        fetchAlbums();
    }

    public MutableLiveData<List<AlbumBean>> getResponseLiveData() {

        return responseLiveData;
    }

    public void fetchAlbums() {

        if (internetUtils.isNetworkAvailable()) {

            setIsLoading(true);
            disposable.add(appRepository.getAlbums()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetAlbumSuccess, this::onGetAlbumFail));
        } else {
            toastUtils.showToast("Please connect to internet");
        }

    }

    private void onGetAlbumSuccess(List<AlbumBean> albumList) {
        setIsLoading(false);
        responseLiveData.setValue(albumList);
    }

    private void onGetAlbumFail(Throwable throwable) {

        setIsLoading(false);
        toastUtils.showToast("No response from server");
        responseLiveData.setValue(null);
    }

    public MutableLiveData<ProgressModel> getmIsLoading() {
        return mIsLoading;
    }


    public void setIsLoading(boolean isLoading) {
        mIsLoading.setValue(new ProgressModel(isLoading));
    }
}