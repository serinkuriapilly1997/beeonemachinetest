package com.example.beeonemachinetest.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.data.remote.bean.PhotosBean;
import com.example.beeonemachinetest.databinding.PhotosSingleItemBinding;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by SERIN_SEB on 11-10-2021.
 */
public class PhotosListAdapter extends RecyclerView.Adapter<PhotosListAdapter.ViewHolder> {
    List<PhotosBean> photosList;
    OnAdapterSelectedListener<PhotosBean> adapterSelectedListener;

    public PhotosListAdapter(List<PhotosBean> photosList, OnAdapterSelectedListener<PhotosBean> adapterSelectedListener) {
        this.photosList = photosList;
        this.adapterSelectedListener = adapterSelectedListener;
    }

    @NonNull
    @NotNull
    @Override
    public PhotosListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        PhotosSingleItemBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.photos_single_item, parent, false);

        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PhotosListAdapter.ViewHolder holder, int position) {


        if (photosList.get(position).getUrl() != null && !photosList.get(position).getUrl().isEmpty()) {

            Picasso.get()
                    .load(photosList.get(position).getUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.binding.roundedImageView);
        }
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PhotosSingleItemBinding binding;

        public ViewHolder(PhotosSingleItemBinding dataBinding) {
            super(dataBinding.getRoot());
            binding = dataBinding;
        }
    }
}
