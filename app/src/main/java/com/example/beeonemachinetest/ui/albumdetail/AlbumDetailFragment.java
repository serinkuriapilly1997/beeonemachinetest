package com.example.beeonemachinetest.ui.albumdetail;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.base.BaseFragment;
import com.example.beeonemachinetest.data.remote.bean.AlbumBean;
import com.example.beeonemachinetest.data.remote.bean.PhotosBean;
import com.example.beeonemachinetest.databinding.FragmentAlbumDetailBinding;
import com.example.beeonemachinetest.utils.DialogUtils;
import com.example.beeonemachinetest.view.adapter.PhotosListAdapter;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class AlbumDetailFragment extends BaseFragment {

    FragmentAlbumDetailBinding binding;
    AlbumDetailViewModel albumDetailViewModel;
    List<PhotosBean> photosList = new ArrayList<>();
    OnAdapterSelectedListener<PhotosBean> adapterSelectedListener;
    PhotosListAdapter adapter;
    ProgressDialog mProgressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private AlbumBean mParam1;
    private String mParam2;

    public AlbumDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumDetailFragment newInstance(AlbumBean param1, String param2) {
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        albumDetailViewModel = createViewModel(AlbumDetailViewModel.class);
        initUi();
        setupRecyclerView();
        subscribeAlbumDetail();
        subscribeProgress();
    }

    private void subscribeProgress() {
        albumDetailViewModel.getmIsLoading().observe(getViewLifecycleOwner(), progressModel -> {

            if (progressModel != null) {
                if (progressModel.isLoading()) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });
    }

    private void subscribeAlbumDetail() {

        albumDetailViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), photosBeanList -> {
            if (photosBeanList != null && !photosBeanList.isEmpty()) {
                photosList.clear();
                photosList.addAll(photosBeanList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupRecyclerView() {

        binding.recyclerviewPhotos.setHasFixedSize(true);
        binding.recyclerviewPhotos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new PhotosListAdapter(photosList, adapterSelectedListener);
        binding.recyclerviewPhotos.setAdapter(adapter);
    }

    private void initUi() {

        binding.setItem(mParam1);
        albumDetailViewModel.fetchPhotos(mParam1.getId().toString());
        adapterSelectedListener = new OnAdapterSelectedListener<PhotosBean>() {
            @Override
            public void onAdapterSelected(PhotosBean pos) {

            }

            @Override
            public void onKeyValue(Object value) {

            }
        };
    }

    public void hideLoading() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showLoading() {

        hideLoading();
        mProgressDialog = DialogUtils.showLoadingDialog(getActivity());

    }
}