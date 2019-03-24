package com.android.david.iphonedroidtest.repository.presenter;

import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositoryDetails;
import com.android.david.iphonedroidtest.common.models.RepositoryTree;
import com.android.david.iphonedroidtest.common.rest.GithubApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class RepositoryInteractorImpl implements RepositoryInteractor {

    private GithubApi githubApi;

    @Override
    public void getTree(String name, String tree, final onGetTreeFinishedListener listener) {

        githubApi = GithubApi.retrofit.create(GithubApi.class);
        final Call<RepositoryTree> call =
                githubApi.getTree(name, tree);

        call.enqueue(new Callback<RepositoryTree>() {
            @Override
            public void onResponse(Call<RepositoryTree> call, Response<RepositoryTree> response) {
                listener.onGetTreeSuccess(response.body());
            }
            @Override
            public void onFailure(Call<RepositoryTree> call, Throwable t) {
                listener.onGetTreeFail();
            }
        });
    }

    @Override
    public void getRepository(String name, final onGetRepositoryFinishedListener listener) {

        githubApi = GithubApi.retrofit.create(GithubApi.class);
        final Call<RepositoryDetails> call =
                githubApi.getRepository(name);

        call.enqueue(new Callback<RepositoryDetails>() {
                @Override
                public void onResponse(Call<RepositoryDetails> call, Response<RepositoryDetails> response) {
                    listener.onGetRepositorySuccess(response.body());
                }
                @Override
                public void onFailure(Call<RepositoryDetails> call, Throwable t) {
                    listener.onGetRepositoryFail();
                }
            });


    }
}
