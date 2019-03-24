package com.android.david.iphonedroidtest.repository.presenter;

import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositoryDetails;
import com.android.david.iphonedroidtest.common.models.RepositoryTree;

import java.util.List;

public class RepositoryPresenterImpl implements RepositoryPresenter, RepositoryInteractor.onGetRepositoryFinishedListener, RepositoryInteractor.onGetTreeFinishedListener {

    private RepositoryView repositoryView;
    private RepositoryInteractor repositoryInteractor;

    public RepositoryPresenterImpl(RepositoryView dashboardView) {
        this.repositoryView = dashboardView;
        this.repositoryInteractor = new RepositoryInteractorImpl();
    }

    @Override
    public void getRepository(String name) {
        if (repositoryView != null) {
            repositoryView.showProgressBar();
        }
        repositoryInteractor.getRepository(name, this);

    }

    @Override
    public void getTree(String name, String tree) {
        if (repositoryView != null) {
            repositoryView.showProgressBar();
        }
        repositoryInteractor.getTree(name, tree, this);
    }

    @Override
    public void onGetRepositoryFail() {
        if (repositoryView != null) {
            repositoryView.hideProgress();
            repositoryView.showError("Error");
        }
    }

    @Override
    public void onGetRepositorySuccess(RepositoryDetails repository) {
        if (repositoryView != null) {
            repositoryView.showRepository(repository);
            repositoryView.hideProgress();
        }
    }

    @Override
    public void onGetTreeSuccess(RepositoryTree tree) {
        if (repositoryView != null) {
            repositoryView.showTree(tree);
            repositoryView.hideProgress();
        }
    }

    @Override
    public void onGetTreeFail() {
        if (repositoryView != null) {
            repositoryView.hideProgress();
            repositoryView.showError("Error");
        }
    }

}
