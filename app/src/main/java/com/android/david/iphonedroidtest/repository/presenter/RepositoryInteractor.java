package com.android.david.iphonedroidtest.repository.presenter;

import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositoryDetails;
import com.android.david.iphonedroidtest.common.models.RepositoryTree;

import java.util.List;

public interface RepositoryInteractor {

    interface onGetRepositoryFinishedListener {
        void onGetRepositoryFail();

        void onGetRepositorySuccess(RepositoryDetails repository);
    }

    interface onGetTreeFinishedListener {
        void onGetTreeSuccess(RepositoryTree tree);

        void onGetTreeFail();
    }

    void getRepository(String name, onGetRepositoryFinishedListener listener);

    void getTree(String name, String tree, onGetTreeFinishedListener listener);
}
