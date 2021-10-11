package com.example.beeonemachinetest.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeonemachinetest.R;
import com.example.beeonemachinetest.data.remote.bean.CommentsBean;
import com.example.beeonemachinetest.databinding.CommentsSingleItemBinding;
import com.example.beeonemachinetest.databinding.PosSingleItemBinding;
import com.example.beeonemachinetest.view.listener.OnAdapterSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by SERIN_SEB on 11-10-2021.
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {

    List<CommentsBean> commentsList;
    OnAdapterSelectedListener<CommentsBean> adapterSelectedListener;

    public CommentsListAdapter(List<CommentsBean> commentsList, OnAdapterSelectedListener<CommentsBean> adapterSelectedListener) {
        this.commentsList = commentsList;
        this.adapterSelectedListener = adapterSelectedListener;
    }

    @NonNull
    @NotNull
    @Override
    public CommentsListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CommentsSingleItemBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.comments_single_item, parent, false);

        return new ViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentsListAdapter.ViewHolder holder, int position) {

        holder.binding.setItem(commentsList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CommentsSingleItemBinding binding;

        public ViewHolder(CommentsSingleItemBinding dataBinding) {
            super(dataBinding.getRoot());
            binding = dataBinding;
        }
    }
}
