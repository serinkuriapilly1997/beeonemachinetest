package com.example.beeonemachinetest.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.data.remote.bean.AlbumBean;
import com.example.beeonemachinetest.databinding.AlbumsSingleItemBinding;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by SERIN_SEB on 11-10-2021.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {

    List<AlbumBean> albumList;
    OnAdapterSelectedListener<AlbumBean> adapterSelectedListener;

    public AlbumListAdapter(List<AlbumBean> albumList, OnAdapterSelectedListener<AlbumBean> adapterSelectedListener) {
        this.albumList = albumList;
        this.adapterSelectedListener = adapterSelectedListener;
    }

    @NonNull
    @NotNull
    @Override
    public AlbumListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AlbumsSingleItemBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.albums_single_item, parent, false);

        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumListAdapter.ViewHolder holder, int position) {

        holder.binding.setItem(albumList.get(position));
        holder.binding.setListener(adapterSelectedListener);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AlbumsSingleItemBinding binding;

        public ViewHolder(AlbumsSingleItemBinding dataBinding) {
            super(dataBinding.getRoot());
            binding = dataBinding;
        }
    }
}
