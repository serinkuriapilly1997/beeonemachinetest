package com.example.beeonemachinetest.ui.album;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.base.BaseFragment;
import com.example.beeonemachinetest.data.remote.bean.AlbumBean;
import com.example.beeonemachinetest.databinding.FragmentAlbumBinding;
import com.example.beeonemachinetest.ui.albumdetail.AlbumDetailFragment;
import com.example.beeonemachinetest.ui.homedetail.HomeDetailFragment;
import com.example.beeonemachinetest.utils.DialogUtils;
import com.example.beeonemachinetest.view.adapter.AlbumListAdapter;
import com.example.beeonemachinetest.view.adapter.PostAdapter;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlbumFragment extends BaseFragment {

    private AlbumViewModel dashboardViewModel;
    private FragmentAlbumBinding binding;
    ProgressDialog mProgressDialog;
    List<AlbumBean> albumList = new ArrayList<>();
    OnAdapterSelectedListener<AlbumBean> adapterSelectedListener;
    AlbumListAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAlbumBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dashboardViewModel = createViewModel(AlbumViewModel.class);
        initUi();
        setupRecyclerView();
        subscribeAlbums();
        subscribeProgress();
    }

    private void subscribeProgress() {

        dashboardViewModel.getmIsLoading().observe(getViewLifecycleOwner(), progressModel -> {

            if (progressModel != null) {
                if (progressModel.isLoading()) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });
    }

    private void subscribeAlbums() {

        dashboardViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), albumBeans -> {


            if (albumBeans != null && !albumBeans.isEmpty()) {
                albumList.clear();
                albumList.addAll(albumBeans);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void setupRecyclerView() {

        binding.recyclerviewAlbums.setHasFixedSize(true);
        binding.recyclerviewAlbums.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        binding.recyclerviewAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AlbumListAdapter(albumList, adapterSelectedListener);
        binding.recyclerviewAlbums.setAdapter(adapter);
    }

    private void initUi() {

        adapterSelectedListener = new OnAdapterSelectedListener<AlbumBean>() {
            @Override
            public void onAdapterSelected(AlbumBean pos) {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().beginTransaction().
                            add(R.id.fragment_container, AlbumDetailFragment.newInstance(pos, ""))
                            .addToBackStack("Album")
                            .setCustomAnimations(0, 0)
                            .commit();
            }

            @Override
            public void onKeyValue(Object value) {

            }
        };

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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