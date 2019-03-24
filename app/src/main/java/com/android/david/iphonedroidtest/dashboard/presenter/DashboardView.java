package com.android.david.iphonedroidtest.dashboard.presenter;

import java.util.List;
import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositorySearch;

public interface DashboardView {
    void showProgressBar();

    void hideProgress();

    void showError(String error);

    void updateRepositoryList(List<Repository> list);

    void showSearchResults(RepositorySearch list);
}
