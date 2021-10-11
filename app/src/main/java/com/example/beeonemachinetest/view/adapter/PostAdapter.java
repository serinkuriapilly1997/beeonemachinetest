package com.example.beeonemachinetest.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.data.remote.bean.HomeBean;
import com.example.beeonemachinetest.databinding.PosSingleItemBinding;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SERIN_SEB on 10-10-2021.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    List<HomeBean> postList;
    OnAdapterSelectedListener<HomeBean> adapterSelectedListener;

    public PostAdapter(List<HomeBean> postList, OnAdapterSelectedListener<HomeBean> adapterSelectedListener) {
        this.postList = postList;
        this.adapterSelectedListener = adapterSelectedListener;
    }

    @NonNull
    @NotNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PosSingleItemBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.pos_single_item, parent, false);

        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostAdapter.ViewHolder holder, int position) {

        holder.binding.setItem(postList.get(position));
        holder.binding.viewCircle.setCardBackgroundColor(getRandomColorCode());
        holder.binding.setListener(adapterSelectedListener);


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PosSingleItemBinding binding;

        public ViewHolder(PosSingleItemBinding dataBinding) {
            super(dataBinding.getRoot());
            binding = dataBinding;
        }
    }

    public int getRandomColorCode() {

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

    }
}
