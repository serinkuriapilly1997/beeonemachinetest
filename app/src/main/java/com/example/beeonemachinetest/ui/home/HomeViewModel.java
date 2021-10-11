package com.example.beeonemachinetest.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beeonemachinetest.data.models.ProgressModel;
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
public class HomeViewModel extends ViewModel {

    private AppRepository appRepository;
    private final InternetUtils internetUtils;
    private final ToastUtils toastUtils;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<HomeBean>> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<ProgressModel> mIsLoading = new MutableLiveData<ProgressModel>();


    @Inject
    public HomeViewModel(AppRepository appRepository, InternetUtils internetUtils,ToastUtils toastUtils) {
        this.appRepository = appRepository;
        this.internetUtils = internetUtils;
        this.toastUtils = toastUtils;
        fetchHomePage();
    }

    public MutableLiveData<List<HomeBean>> getResponseLiveData() {

        return responseLiveData;
    }

    public void fetchHomePage() {

        if (internetUtils.isNetworkAvailable()) {

            setIsLoading(true);
            disposable.add(appRepository.getHomePage()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetHomeSuccess, this::onGetHomeFail));
        } else {
            toastUtils.showToast("Please connect to internet");
        }

    }

    private void onGetHomeSuccess(List<HomeBean> homePageList) {
        setIsLoading(false);
        responseLiveData.setValue(homePageList);
    }

    private void onGetHomeFail(Throwable throwable) {

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