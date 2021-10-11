package com.example.beeonemachinetest.data.remote;

import com.example.beeonemachinetest.data.remote.bean.AlbumBean;
import com.example.beeonemachinetest.data.remote.bean.CommentsBean;
import com.example.beeonemachinetest.data.remote.bean.HomeBean;
import com.example.beeonemachinetest.data.remote.bean.PhotosBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by SERIN_SEB on 09-10-2021.
 */
public interface ApiService {


    @GET(ServiceNames.GET_HOME)
    Observable<List<HomeBean>> loadHomePage();

    @GET(ServiceNames.GET_ALBUM)
    Observable<List<AlbumBean>> loadAlbums();

    @GET(ServiceNames.GET_COMMENTS + "/{id}" + ServiceNames.COMMENTS)
    Observable<List<CommentsBean>> loadComments(@Path("id") String id);

    @GET(ServiceNames.GET_ALBUM + "/{id}" + ServiceNames.PHOTOS)
    Observable<List<PhotosBean>> loadPhotos(@Path("id") String id);
}