package com.example.accessappexercise.mvvm.viewModel;

import android.content.Context;
import android.view.View;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableInt;
import com.example.accessappexercise.api.data.user.UserData;
import com.example.accessappexercise.mvvm.view.UserDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

public class ItemUserViewModel extends BaseObservable {

    private UserData user;
    private Context context;
    public ObservableInt siteAdmin;

    public ItemUserViewModel(UserData user, Context context){
        this.user = user;
        this.context = context;

        if(user.isSiteAdmin()) {
            siteAdmin = new ObservableInt(View.VISIBLE);
        } else {
            siteAdmin = new ObservableInt(View.GONE);
        }
    }

    public String getAvatarUrl() {
        return user.getAvatarUrl();
    }

    @BindingAdapter("imageUrl") public static void setImageUrl(SimpleDraweeView imageView, String url){
        imageView.setImageURI(url);
    }

    public String getLogin() {
        return user.getLogin();
    }

    public void onItemClick(View v){
        context.startActivity(UserDetailActivity.toDetail(v.getContext(), user));
    }

    public void setUser(UserData user) {
        this.user = user;
        notifyChange();
    }
}
