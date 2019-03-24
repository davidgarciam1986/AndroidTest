package com.android.david.iphonedroidtest.dashboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.android.david.iphonedroidtest.R;
import com.android.david.iphonedroidtest.common.adapters.RecyclerViewAdapter;
import com.android.david.iphonedroidtest.common.adapters.SearchRecyclerViewAdapter;
import com.android.david.iphonedroidtest.common.models.Repository;
import com.android.david.iphonedroidtest.common.models.RepositorySearch;
import com.android.david.iphonedroidtest.common.models.SearchItem;
import com.android.david.iphonedroidtest.dashboard.presenter.DashboardPresenter;
import com.android.david.iphonedroidtest.dashboard.presenter.DashboardPresenterImpl;
import com.android.david.iphonedroidtest.dashboard.presenter.DashboardView;
import com.android.david.iphonedroidtest.repository.RepositoryViewActivity;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DashboardView, RecyclerViewAdapter.ItemClickListener, SearchRecyclerViewAdapter.ItemClickListener {

    private DashboardPresenter presenter;
    private Integer since;

    private RecyclerViewAdapter adapter;
    private SearchRecyclerViewAdapter searchAdapter;
    private RecyclerView recyclerView;
    private RecyclerView searchRecyclerView;
    private FrameLayout progressBarOverlay;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        progressBarOverlay = findViewById(R.id.progressBarOverlay);
        progressBarOverlay.setVisibility(View.GONE);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                presenter.searchPublicRepositories(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }


        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                if (searchRecyclerView == null || searchRecyclerView.getVisibility() == View.GONE) {
                    searchView.clearFocus();
                    searchView.setVisibility(View.GONE);
                } else {
                    searchRecyclerView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    searchView.clearFocus();
                    searchView.setVisibility(View.GONE);
                }
                return false;
            }
        });

        ImageButton imageButton = findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.VISIBLE);
            }
        });

        presenter = new DashboardPresenterImpl(this);
        presenter.getPublicRepositoriesSince(0);
        since = 0;

    }

    @Override
    public void showProgressBar() {
        progressBarOverlay.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBarOverlay.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        //TODO mostrar error
    }

    @Override
    public void updateRepositoryList(final List<Repository> list) {
        try {
            since = list.get(list.size() - 1).getId();
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            if (recyclerView == null) {
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerViewAdapter(this, list);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount()-1) {
                                presenter.getPublicRepositoriesSince(since);
                            }
                        }
                    }

                });
            } else {
                adapter.addItems(list);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            showError("Has llegado al maximo de peticiones en esta IP. Prueba de nuevo en una hora.");
        }
    }

    @Override
    public void showSearchResults(RepositorySearch results) {
        try {
            List<SearchItem> list = results.getItems();
            if (searchRecyclerView == null) {
                searchRecyclerView = findViewById(R.id.recyclerViewSearch);
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                searchAdapter = new SearchRecyclerViewAdapter(this, list);
                searchAdapter.setClickListener(this);
                searchRecyclerView.setAdapter(searchAdapter);
            }
            recyclerView.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.VISIBLE);
            searchView.clearFocus();
        } catch (Exception e){
            //TODO mostrar error
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            if (searchRecyclerView  == null || searchRecyclerView.getVisibility() == View.GONE) {
                String name = adapter.repositories.get(position).getFullName();
                Intent i = new Intent(this, RepositoryViewActivity.class);
                i.putExtra("name", name);
                i.putExtra("tree", "master");
                startActivity(i);
            } else {
                String name = searchAdapter.repositories.get(position).getFullName();
                Intent i = new Intent(this, RepositoryViewActivity.class);
                i.putExtra("name", name);
                i.putExtra("tree", "master");
                startActivity(i);
            }
        }catch (Exception e) {
            //TODO mostrar error
        }
    }

    public void onBackPressed() {
        try {
            if (searchRecyclerView.getVisibility() == View.VISIBLE) {
                searchRecyclerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                searchView.setQuery("", false);
                searchView.clearFocus();
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            super.onBackPressed();
        }
    }
}
