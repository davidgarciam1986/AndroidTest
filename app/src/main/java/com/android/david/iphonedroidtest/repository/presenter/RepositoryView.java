package com.android.david.iphonedroidtest.repository.presenter;

import java.util.List;
import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositoryDetails;
import com.android.david.iphonedroidtest.common.models.RepositoryTree;

public interface RepositoryView {
    void showProgressBar();

    void hideProgress();

    void showError(String error);

    void showRepository(RepositoryDetails repository);

    void showTree(RepositoryTree tree);
}
