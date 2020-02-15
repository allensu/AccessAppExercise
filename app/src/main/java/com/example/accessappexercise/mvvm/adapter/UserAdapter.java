package com.example.accessappexercise.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accessappexercise.R;
import com.example.accessappexercise.api.data.user.UserData;
import com.example.accessappexercise.databinding.ItemUserBinding;
import com.example.accessappexercise.mvvm.viewModel.ItemUserViewModel;

import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder> {

    private List<UserData> userList;

    public UserAdapter() {this.userList = Collections.emptyList();}

    @Override
    public UserAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserBinding itemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user ,parent, false);
        return new UserAdapterViewHolder(itemUserBinding);
    }

    @Override
    public void onBindViewHolder(UserAdapterViewHolder holder, int position) {
        holder.bindUser(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return  userList.size();
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public static class UserAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding mItemUserBinding;

        public UserAdapterViewHolder(ItemUserBinding itemUserBinding) {
            super(itemUserBinding.itemUser);
            this.mItemUserBinding = itemUserBinding;

            mItemUserBinding.siteAdmin.setVisibility(View.GONE);
        }

        void bindUser(UserData user){
            if(mItemUserBinding.getUserViewModel() == null){
                mItemUserBinding.setUserViewModel(new ItemUserViewModel(user, itemView.getContext()));
            }else {
                mItemUserBinding.getUserViewModel().setUser(user);
            }
        }
    }
}
