package com.android.david.iphonedroidtest.dashboard.presenter;

import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositorySearch;
import com.android.david.iphonedroidtest.common.rest.GithubApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class DashboardInteractorImpl implements DashboardInteractor {

    private GithubApi githubApi;
    @Override
    public void getPublicRepositoriesSince(Integer since, final onGetPublicRepositoriesSinceFinishedListener listener) {

        githubApi = GithubApi.retrofit.create(GithubApi.class);
        final Call<List<Repository>> call =
                githubApi.getPublicRepositoriesSince(since.toString());

        call.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    listener.onGetPublicRepositoriesSinceSuccess(response.body());
                }
                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    listener.onGetPublicRepositoriesSinceFail();
                }
            });


    }

    public void searchPublicRepositories(String query, final onSearchPublicRepositoriesFinishedListener listener) {

        githubApi = GithubApi.retrofit.create(GithubApi.class);
        final Call<RepositorySearch> call =
                githubApi.searchPublicRepositories(query);

        call.enqueue(new Callback<RepositorySearch>() {
            @Override
            public void onResponse(Call<RepositorySearch> call, Response<RepositorySearch> response) {
                listener.onSearchPublicRepositoriesSuccess(response.body());
            }
            @Override
            public void onFailure(Call<RepositorySearch> call, Throwable t) {
                listener.onSearchPublicRepositoriesFail();
            }
        });


    }
}
