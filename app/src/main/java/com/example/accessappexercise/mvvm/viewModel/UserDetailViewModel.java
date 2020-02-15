package com.example.accessappexercise.mvvm.viewModel;

import android.app.Activity;
import android.view.View;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import com.example.accessappexercise.api.ApiObserver;
import com.example.accessappexercise.api.data.userDetail.UserDetailData;
import com.example.accessappexercise.api.request.UserApiRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.Observable;

public class UserDetailViewModel extends Observable {

    private UserDetailData userDetail;
    private Activity activity;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> bio = new ObservableField<>();
    public ObservableField<String> userLogin = new ObservableField<>();
    public ObservableInt siteAdmin = new ObservableInt();
    public ObservableField<String> location = new ObservableField<>();
    public ObservableField<String> blog = new ObservableField<>();
    public ObservableField<String> avatarUrl = new ObservableField<>();

    public UserDetailViewModel(Activity activity) {
        this.activity = activity;
    }

    public void loadUserDetail(Activity activity, String login) {
        UserApiRequest.instance().userDetail(login, new ApiObserver<UserDetailData>(activity) {
            @Override
            public void onNext(UserDetailData userDetailData) {
                userDetail = userDetailData;

                avatarUrl.set(userDetail.getAvatarUrl());
                name.set(userDetail.getName());
                bio.set(userDetail.getBio());
                userLogin.set(userDetail.getLogin());
                if(userDetail.isSiteAdmin()) {
                    siteAdmin.set(View.VISIBLE);
                } else {
                    siteAdmin.set(View.GONE);
                }
                location.set(userDetail.getLocation());
                blog.set(userDetail.getBlog());

                update();
            }
        });
    }

    private void update() {
        setChanged();
        notifyObservers();
    }

    public void onBackEnd(View view) {
        this.activity.finish();
    }

    @BindingAdapter("imageUrl") public static void setImageUrl(SimpleDraweeView imageView, String url){
        imageView.setImageURI(url);
    }

}
