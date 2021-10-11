package com.example.beeonemachinetest.ui.homedetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beeonemachinetest.data.models.ProgressModel;
import com.example.beeonemachinetest.data.remote.bean.CommentsBean;
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

/**
 * Created by SERIN_SEB on 10-10-2021.
 */
@HiltViewModel
public class HomeDetailViewModel extends ViewModel {

    private AppRepository appRepository;
    private final InternetUtils internetUtils;
    private final ToastUtils toastUtils;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<CommentsBean>> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<ProgressModel> mIsLoading = new MutableLiveData<ProgressModel>();

    @Inject
    public HomeDetailViewModel(AppRepository appRepository, InternetUtils internetUtils, ToastUtils toastUtils) {
        this.appRepository = appRepository;
        this.internetUtils = internetUtils;
        this.toastUtils = toastUtils;
    }

    public MutableLiveData<List<CommentsBean>> getResponseLiveData() {
        return responseLiveData;
    }

    public void fetchComments(String id) {

        if (internetUtils.isNetworkAvailable()) {

            setIsLoading(true);
            disposable.add(appRepository.getComments(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetCommentsSuccess, this::onGetCommentsFail));
        } else {
            toastUtils.showToast("Please connect to internet");
        }

    }

    private void onGetCommentsSuccess(List<CommentsBean> commentsList) {
        setIsLoading(false);
        responseLiveData.setValue(commentsList);
    }

    private void onGetCommentsFail(Throwable throwable) {

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
