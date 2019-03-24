package com.android.david.iphonedroidtest.dashboard.presenter;

import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositorySearch;

import java.util.List;

public interface DashboardInteractor {

    interface onGetPublicRepositoriesSinceFinishedListener {
        void onGetPublicRepositoriesSinceFail();

        void onGetPublicRepositoriesSinceSuccess(List<Repository> list);
    }

    interface onSearchPublicRepositoriesFinishedListener {
        void onSearchPublicRepositoriesFail();

        void onSearchPublicRepositoriesSuccess(RepositorySearch list);
    }

    void getPublicRepositoriesSince(Integer since, onGetPublicRepositoriesSinceFinishedListener listener);

    void searchPublicRepositories(String query, onSearchPublicRepositoriesFinishedListener listener);
}
