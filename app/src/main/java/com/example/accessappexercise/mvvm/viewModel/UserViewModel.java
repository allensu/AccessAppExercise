package com.example.accessappexercise.mvvm.viewModel;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.accessappexercise.AppConstants;
import com.example.accessappexercise.api.ApiObserver;
import com.example.accessappexercise.api.data.user.UserData;
import com.example.accessappexercise.api.request.UserApiRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;

public class UserViewModel extends Observable {

    private List<UserData> userList;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public UserViewModel(@NonNull Context context) {
        this.context = context;
        this.userList = new ArrayList<>();
    }

    public void loadUser(Activity activity, int since) {
        UserApiRequest.instance().users(since, AppConstants.USERS_PAGE_SIZE, new ApiObserver<List<UserData>>(activity) {
            @Override
            public void onNext(List<UserData> result) {
                if(since == 0) {
                    userList.clear();
                }

                updateUserDataList(result);
            }
        });
    }

    private void updateUserDataList(List<UserData> users) {
        userList.addAll(users);
        setChanged();
        notifyObservers();
    }

    public List<UserData> getUserList() {
        return userList;
    }

    public int getLastUserId() {

        if(userList == null || userList.size() == 0) {
            return 0;
        } else {
            UserData userData = userList.get(userList.size() - 1);
            return userData.getId();
        }
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }


}
