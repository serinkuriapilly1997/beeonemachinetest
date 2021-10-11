package com.example.beeonemachinetest.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.base.BaseFragment;
import com.example.beeonemachinetest.data.models.ProgressModel;
import com.example.beeonemachinetest.data.remote.bean.HomeBean;
import com.example.beeonemachinetest.databinding.FragmentHomeBinding;
import com.example.beeonemachinetest.ui.homedetail.HomeDetailFragment;
import com.example.beeonemachinetest.utils.DialogUtils;
import com.example.beeonemachinetest.utils.InternetUtils;
import com.example.beeonemachinetest.view.adapter.PostAdapter;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    PostAdapter adapter;
    ProgressDialog mProgressDialog;
    List<HomeBean> postList = new ArrayList<>();
    OnAdapterSelectedListener<HomeBean> adapterSelectedListener;
    @Inject
    InternetUtils internetUtils;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = createViewModel(HomeViewModel.class);
        initUi();
        setupRecyclerView();
        subscribeHome();
        subscribeProgress();

    }

    private void subscribeProgress() {

        homeViewModel.getmIsLoading().observe(getViewLifecycleOwner(), progressModel -> {

            if (progressModel != null) {
                if (progressModel.isLoading()) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });
    }

    private void subscribeHome() {

        homeViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), homeBeans -> {

            if (homeBeans != null && !homeBeans.isEmpty()) {
                postList.clear();
                postList.addAll(homeBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initUi() {

        adapterSelectedListener = new OnAdapterSelectedListener<HomeBean>() {
            @Override
            public void onAdapterSelected(HomeBean pos) {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().beginTransaction().
                            add(R.id.fragment_container, HomeDetailFragment.newInstance(pos, ""))
                            .addToBackStack("Home")
                            .setCustomAnimations(0, 0)
                            .commit();


            }

            @Override
            public void onKeyValue(Object value) {

            }
        };
    }

    private void setupRecyclerView() {

        binding.recyclerviewPosts.setHasFixedSize(true);
        binding.recyclerviewPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PostAdapter(postList, adapterSelectedListener);
        binding.recyclerviewPosts.setAdapter(adapter);

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