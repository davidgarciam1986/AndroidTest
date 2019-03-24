package com.android.david.iphonedroidtest.dashboard.presenter;

import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositorySearch;

import java.util.List;

public class DashboardPresenterImpl implements DashboardPresenter, DashboardInteractor.onGetPublicRepositoriesSinceFinishedListener, DashboardInteractor.onSearchPublicRepositoriesFinishedListener {

    private DashboardView dashboardView;
    private DashboardInteractor dashboardInteractor;

    public DashboardPresenterImpl(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        this.dashboardInteractor = new DashboardInteractorImpl();
    }

    @Override
    public void getPublicRepositoriesSince(Integer since) {
        if (dashboardView != null) {
            dashboardView.showProgressBar();
        }
        dashboardInteractor.getPublicRepositoriesSince(since, this);

    }

    @Override
    public void searchPublicRepositories(String query) {
        if (dashboardView != null) {
            dashboardView.showProgressBar();
        }
        dashboardInteractor.searchPublicRepositories(query, this);

    }

    @Override
    public void onGetPublicRepositoriesSinceFail() {
        if (dashboardView != null) {
            dashboardView.hideProgress();
            dashboardView.showError("Error");
        }
    }

    @Override
    public void onGetPublicRepositoriesSinceSuccess(List<Repository> list) {
        if (dashboardView != null) {
            dashboardView.updateRepositoryList(list);
            dashboardView.hideProgress();
        }
    }

    @Override
    public void onSearchPublicRepositoriesFail() {
        if (dashboardView != null) {
            dashboardView.hideProgress();
            dashboardView.showError("Error");
        }
    }

    @Override
    public void onSearchPublicRepositoriesSuccess(RepositorySearch list) {
        if (dashboardView != null) {
            dashboardView.showSearchResults(list);
            dashboardView.hideProgress();
        }
    }
}
