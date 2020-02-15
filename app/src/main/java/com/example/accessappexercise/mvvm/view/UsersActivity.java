package com.example.accessappexercise.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.accessappexercise.R;
import com.example.accessappexercise.SuperActivity;
import com.example.accessappexercise.databinding.ActivityUsersBinding;
import com.example.accessappexercise.mvvm.adapter.UserAdapter;
import com.example.accessappexercise.mvvm.viewModel.UserViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Observable;
import java.util.Observer;

public class UsersActivity extends SuperActivity implements Observer {

    private ActivityUsersBinding usersActivityBinding;
    private UserViewModel userViewModel;

    LinearLayoutManager layoutManager;
    KProgressHUD progressHUD;
    int pastVisiblesItems;
    int visibleItemCount;
    int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setUpListOfUsersView();
        setUpObserver(userViewModel);

        userViewModel.loadUser(this, 0);
    }

    private void initDataBinding() {
        usersActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        userViewModel = new UserViewModel(this);
        usersActivityBinding.setUserViewModel(userViewModel);
    }

    private void setUpListOfUsersView() {
        layoutManager = new LinearLayoutManager(this);
        UserAdapter userAdapter = new UserAdapter();
        userAdapter.setUserList(userViewModel.getUserList());
        usersActivityBinding.listUser.setAdapter(userAdapter);
        usersActivityBinding.listUser.setLayoutManager(layoutManager);

        usersActivityBinding.listUserSwipeRefresh.setOnRefreshListener(onSwipeToRefresh);
        usersActivityBinding.listUserSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .show();

        usersActivityBinding.listUser.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            private static final int HIDE_THRESHOLD = 20;
            private int scrolledDistance = 0;
            private boolean controlsVisible = true;

            private void onHide() { }
            private void onShow() { }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    onHide();
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    onShow();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }

                if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                    scrolledDistance += dy;
                }

                if(dy > 0)
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (!progressHUD.isShowing()) {
                            progressHUD.show();
                            userViewModel.loadUser(UsersActivity.this, userViewModel.getLastUserId());
                        }
                    }
                }
            }
        });
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            usersActivityBinding.listUserSwipeRefresh.setRefreshing(true);
            userViewModel.loadUser(UsersActivity.this, 0);
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof  UserViewModel) {
            UserAdapter userAdapter = (UserAdapter) usersActivityBinding.listUser.getAdapter();
//            UserViewModel userViewModel = (UserViewModel) o;
//            userAdapter.setUserList(userViewModel.getUserList());
            userAdapter.notifyDataSetChanged();

            usersActivityBinding.listUserSwipeRefresh.setRefreshing(false);
            if(progressHUD.isShowing()) {
                progressHUD.dismiss();
            }
        }
    }

}
