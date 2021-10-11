package com.example.beeonemachinetest.data.repository;

import com.example.beeonemachinetest.data.remote.ApiService;
import com.example.beeonemachinetest.data.remote.bean.AlbumBean;
import com.example.beeonemachinetest.data.remote.bean.CommentsBean;
import com.example.beeonemachinetest.data.remote.bean.HomeBean;
import com.example.beeonemachinetest.data.remote.bean.PhotosBean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by SERIN_SEB on 09-10-2021.
 */
@Singleton
public class AppRepository {

    private ApiService apiService;

    @Inject
    public AppRepository(ApiService apiService) {
        this.apiService = apiService;
    }


    public Observable<List<HomeBean>> getHomePage() {
        return apiService.loadHomePage();
    }

    public Observable<List<AlbumBean>> getAlbums() {
        return apiService.loadAlbums();
    }

    public Observable<List<CommentsBean>> getComments(String id) {
        return apiService.loadComments(id);
    }

    public Observable<List<PhotosBean>> getPhotos(String id) {
        return apiService.loadPhotos(id);
    }
}