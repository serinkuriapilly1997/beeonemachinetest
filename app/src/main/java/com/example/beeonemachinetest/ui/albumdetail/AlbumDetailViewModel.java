package com.example.beeonemachinetest.ui.albumdetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beeonemachinetest.data.models.ProgressModel;
import com.example.beeonemachinetest.data.remote.bean.CommentsBean;
import com.example.beeonemachinetest.data.remote.bean.PhotosBean;
import com.example.beeonemachinetest.data.repository.AppRepository;
import com.example.beeonemachinetest.utils.InternetUtils;
import com.example.beeonemachinetest.utils.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SERIN_SEB on 11-10-2021.
 */
@HiltViewModel
public class AlbumDetailViewModel extends ViewModel {

    private AppRepository appRepository;
    private final InternetUtils internetUtils;
    private final ToastUtils toastUtils;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<PhotosBean>> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<ProgressModel> mIsLoading = new MutableLiveData<ProgressModel>();

    @Inject
    public AlbumDetailViewModel(AppRepository appRepository, InternetUtils internetUtils, ToastUtils toastUtils) {
        this.appRepository = appRepository;
        this.internetUtils = internetUtils;
        this.toastUtils = toastUtils;
    }

    public MutableLiveData<List<PhotosBean>> getResponseLiveData() {
        return responseLiveData;
    }

    public void fetchPhotos(String id) {

        if (internetUtils.isNetworkAvailable()) {

            setIsLoading(true);
            disposable.add(appRepository.getPhotos(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetPhotosSuccess, this::onGetPhotosFail));
        } else {
            toastUtils.showToast("Please connect to internet");
        }

    }

    private void onGetPhotosSuccess(List<PhotosBean> photosList) {
        setIsLoading(false);
        responseLiveData.setValue(photosList);
    }

    private void onGetPhotosFail(Throwable throwable) {

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
