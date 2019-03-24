package com.android.david.iphonedroidtest.repository;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.david.iphonedroidtest.R;
import com.android.david.iphonedroidtest.common.models.RepositoryDetails;
import com.android.david.iphonedroidtest.common.models.RepositoryTree;
import com.android.david.iphonedroidtest.common.models.TreeItem;
import com.android.david.iphonedroidtest.repository.presenter.RepositoryPresenter;
import com.android.david.iphonedroidtest.repository.presenter.RepositoryPresenterImpl;
import com.android.david.iphonedroidtest.repository.presenter.RepositoryView;

import java.util.List;

public class RepositoryViewActivity extends AppCompatActivity implements RepositoryView {

    RepositoryPresenter presenter;
    FrameLayout progressBarOverlay;
    String fullName;
    String tree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_view);

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        presenter = new RepositoryPresenterImpl(this);
        fullName = getIntent().getStringExtra("name");
        tree = getIntent().getStringExtra("tree");
        progressBarOverlay = findViewById(R.id.progressBarOverlay1);
        progressBarOverlay.setVisibility(View.GONE);
        presenter.getRepository(fullName);
        presenter.getTree(fullName,tree);
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

    }

    @Override
    public void showRepository(RepositoryDetails repository) {
        TextView fullName = findViewById(R.id.textView3);
        TextView description = findViewById(R.id.textView4);
        TextView language = findViewById(R.id.textView5);

        fullName.setText(repository.getFullName());
        description.setText(repository.getDescription());
        language.setText("Language: "+repository.getLanguage());
    }

    @Override
    public void showTree(RepositoryTree tree) {
        try {
            List<TreeItem> treeItems = tree.getTree();
            final LinearLayout files = findViewById(R.id.scrollView1);
            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            //Carpetas aparecen primero en la lista
            for (int i = 0; i < treeItems.size() - 1; i++) {
                if (treeItems.get(i).getType().equals("tree")) {
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(lparams);
                    textView.setTextColor(Color.WHITE);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setText(treeItems.get(i).getPath());
                    files.addView(textView);
                    final String sha = treeItems.get(i).getSha();
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(RepositoryViewActivity.this, RepositoryViewActivity.class);
                            i.putExtra("name", fullName);
                            i.putExtra("tree", sha);
                            startActivity(i);
                        }
                    });
                }
            }
            //Archivos aparecen despues
            for (int i = 0; i < treeItems.size() - 1; i++) {
                if (treeItems.get(i).getType().equals("blob")) {
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(lparams);
                    textView.setTextColor(Color.WHITE);
                    textView.setTypeface(null, Typeface.ITALIC);
                    textView.setText(treeItems.get(i).getPath());
                    files.addView(textView);
                }
            }
        } catch (Exception e) {
            showError("Repositorio vacio.");
        }

    }
}
