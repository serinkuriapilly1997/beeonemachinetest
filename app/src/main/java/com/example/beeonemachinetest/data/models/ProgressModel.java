package com.example.beeonemachinetest.data.models;

/**
 * Created by SERIN_SEB on 10-10-2021.
 */
public class ProgressModel {
    private boolean isLoading;
    private boolean hasInternet;
    private String errorMessage;

    public ProgressModel(boolean isLoading) {
        this.isLoading = isLoading;
        this.hasInternet = true;
    }

    public ProgressModel() {
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
