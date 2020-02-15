package com.example.accessappexercise.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.accessappexercise.R;
import com.example.accessappexercise.SuperActivity;
import com.example.accessappexercise.api.data.user.UserData;
import com.example.accessappexercise.databinding.ActivityUserDetailBinding;
import com.example.accessappexercise.mvvm.viewModel.UserDetailViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Observable;
import java.util.Observer;

public class UserDetailActivity extends SuperActivity implements Observer {

    private static final String INTENT_P_USER = "INTENT_P_USER";
    private ActivityUserDetailBinding activityUserDetailBinding;
    private UserDetailViewModel userDetailViewModel;
    private UserData user;
    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        getExtraData();
        setUpObserver(userDetailViewModel);
        loadUserDetail();
    }

    private void initDataBinding() {
        activityUserDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        activityUserDetailBinding.userDetailViewBody.setVisibility(View.GONE);

        userDetailViewModel = new UserDetailViewModel(this);
        activityUserDetailBinding.setUserDetailViewModel(userDetailViewModel);

        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .show();
    }

    private void getExtraData() {
        user = (UserData) getIntent().getSerializableExtra(INTENT_P_USER);
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    public void loadUserDetail() {
        progressHUD.show();
        userDetailViewModel.loadUserDetail(this, user.getLogin());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof  UserDetailViewModel) {
            activityUserDetailBinding.userDetailViewBody.setVisibility(View.VISIBLE);
            progressHUD.dismiss();
        }
    }

    public static Intent toDetail(Context context, UserData user) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(INTENT_P_USER, user);
        return intent;
    }
}
