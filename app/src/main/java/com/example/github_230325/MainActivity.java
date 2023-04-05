package com.example.github_230325;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RepositoryData>> {

    private EditText editText;
    private ListView dataListView;
    private TextView errorMessage;
    private ProgressBar loading;
    private RepositoryAdapter adapter;

    private static final int GITHUB_SEARCH_LOADER = 1;
    private static final String GITHUB_QUERY_TAG = "query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));


        loading = findViewById(R.id.loadingBar);
        errorMessage = findViewById(R.id.errorMessage);
        editText = findViewById(R.id.searchEditText);
        dataListView = findViewById(R.id.dataListView);

        dataListView.setEmptyView(errorMessage);

        adapter = new RepositoryAdapter(getApplicationContext());
        dataListView.setAdapter(adapter);

        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(GITHUB_QUERY_TAG);
            editText.setText(queryUrl);
        }

        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
                //makeGithubSearchQuery();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //makeGithubSearchQuery();
                //dataListView.setEmptyView(errorMessage);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
                makeGithubSearchQuery();
            }
        });


    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(GITHUB_QUERY_TAG, editText.getText().toString().trim());
    }

    @Override
    public Loader<List<RepositoryData>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<RepositoryData>>(this) {
            List<RepositoryData> mRepositoryList;

            @Override
            protected void onStartLoading() {

                errorMessage.setVisibility(View.INVISIBLE);

                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                loading.setVisibility(View.VISIBLE);


                if (mRepositoryList != null) {
                    deliverResult(mRepositoryList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<RepositoryData> loadInBackground() {
                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = args.getString(GITHUB_QUERY_TAG);

                /* Parse the URL from the passed in String and perform the search */
                try {
                    List<RepositoryData> githubSearchResults = NetworkUtils.getDataFromApi(searchQueryUrlString);
                    return githubSearchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<RepositoryData> githubJson) {
                mRepositoryList = githubJson;
                super.deliverResult(githubJson);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<RepositoryData>> loader, List<RepositoryData> data) {
        /* When we finish loading, we want to hide the loading indicator from the user. */
        loading.setVisibility(View.INVISIBLE);

        //Log.d("onLoadFinished: ", String.valueOf(data));
        if (data == null) {
            showErrorMessage();
        } else {
            //Clear ListView Old Data
            adapter.clear();
            adapter.addAll(data);
            showJsonDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<RepositoryData>> loader) {

    }

    private void showJsonDataView() {
        /* First, make sure the error is invisible */
        errorMessage.setVisibility(View.INVISIBLE);
        /* Then, make sure the JSON data is visible */
        dataListView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        /* First, hide the currently visible data */
        dataListView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorMessage.setVisibility(View.VISIBLE);
    }



    public void makeGithubSearchQuery() {
        String githubQuery = editText.getText().toString();

        Bundle queryBundle = new Bundle();
        queryBundle.putString(GITHUB_QUERY_TAG, githubQuery);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        }
    }

}