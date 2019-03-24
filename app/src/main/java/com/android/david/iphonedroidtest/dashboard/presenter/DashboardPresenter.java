package com.android.david.iphonedroidtest.dashboard.presenter;


public interface DashboardPresenter {

    void getPublicRepositoriesSince(Integer since);

    void searchPublicRepositories(String query);

}