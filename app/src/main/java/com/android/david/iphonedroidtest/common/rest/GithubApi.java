package com.android.david.iphonedroidtest.common.rest;



import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositoryDetails;
import com.android.david.iphonedroidtest.common.models.RepositorySearch;
import com.android.david.iphonedroidtest.common.models.RepositoryTree;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {

    @Headers({"Content-Type:application/json"})
    @GET("/repositories")
    Call<List<Repository>> getPublicRepositoriesSince(@Query("since") String since);

    @Headers({"Content-Type:application/json"})
    @GET("/search/repositories")
    Call<RepositorySearch> searchPublicRepositories(@Query("q") String query);

    @Headers({"Content-Type:application/json"})
    @GET("/repos/{name}")
    Call<RepositoryDetails> getRepository(@Path(value = "name", encoded = true) String name);

    @Headers({"Content-Type:application/json"})
    @GET("/repos/{name}/git/trees/{tree}")
    Call<RepositoryTree> getTree(@Path(value = "name", encoded = true) String name, @Path(value = "tree", encoded = true) String tree);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}