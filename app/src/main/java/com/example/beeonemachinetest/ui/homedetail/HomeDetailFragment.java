package com.example.beeonemachinetest.ui.homedetail;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.base.BaseFragment;
import com.example.beeonemachinetest.data.remote.bean.CommentsBean;
import com.example.beeonemachinetest.data.remote.bean.HomeBean;
import com.example.beeonemachinetest.databinding.FragmentHomeBinding;
import com.example.beeonemachinetest.databinding.FragmentHomeDetailBinding;
import com.example.beeonemachinetest.ui.settings.SettingsViewModel;
import com.example.beeonemachinetest.utils.DialogUtils;
import com.example.beeonemachinetest.view.adapter.CommentsListAdapter;
import com.example.beeonemachinetest.view.adapter.PostAdapter;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
public class HomeDetailFragment extends BaseFragment {

    HomeDetailViewModel homeDetailViewModel;
    FragmentHomeDetailBinding binding;
    ProgressDialog mProgressDialog;
    List<CommentsBean> commentsList = new ArrayList<>();
    OnAdapterSelectedListener<CommentsBean> adapterSelectedListener;
    CommentsListAdapter adapter;

    public HomeDetailFragment() {

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private HomeBean mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeDetailFragment newInstance(HomeBean param1, String param2) {
        HomeDetailFragment fragment = new HomeDetailFragment();
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
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeDetailViewModel = new ViewModelProvider(this, getDefaultViewModelProviderFactory()).get(HomeDetailViewModel.class);
        initUi();
        setupRecyclerView();
        subscribeComments();
        subscribeProgress();
    }

    private void subscribeProgress() {
        homeDetailViewModel.getmIsLoading().observe(getViewLifecycleOwner(), progressModel -> {

            if (progressModel != null) {
                if (progressModel.isLoading()) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });

    }

    private void subscribeComments() {

        homeDetailViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), commentsBeanList -> {

            if (commentsBeanList != null && !commentsBeanList.isEmpty()) {
                commentsList.clear();
                commentsList.addAll(commentsBeanList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupRecyclerView() {

        binding.recyclerviewComments.setHasFixedSize(true);
        binding.recyclerviewComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommentsListAdapter(commentsList, adapterSelectedListener);
        binding.recyclerviewComments.setAdapter(adapter);
    }

    private void initUi() {

        binding.setItem(mParam1);
        homeDetailViewModel.fetchComments(mParam1.getId().toString());

        adapterSelectedListener = new OnAdapterSelectedListener<CommentsBean>() {
            @Override
            public void onAdapterSelected(CommentsBean pos) {

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