package com.example.android.newnewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<News>> {

        /** Adapter for the list of newsItems */
        private NewsAdapter mAdapter;
        /** TextView that is displayed when the list is empty */
        private TextView mEmptyStateTextView;


        public static final String LOG_TAG = NewsActivity.class.getName();
        /** URL for news data from the NEWS dataset */
        private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search";

        /**
         * Constant value for the news loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private static final int NEWS_LOADER_ID = 1;

        @Override protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.news_activity);

            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // Get a reference to the LoaderManager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();
                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            } else {
                // Otherwise, display error
                // First, hide loading indicator so error message will be visible
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
                // Update empty state with no connection error message
                mEmptyStateTextView.setText(R.string.no_internet);
            }

            // Find a reference to the {@link ListView} in the layout
            ListView newsListView = (ListView) findViewById(R.id.list);

            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            newsListView.setEmptyView(mEmptyStateTextView);


            // Create a new adapter that takes an empty list of newsItems as input
            mAdapter = new NewsAdapter(this, new ArrayList<News>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            newsListView.setAdapter(mAdapter);

            // Set an item click listener on the ListView, which sends an intent to a web browser
            // to open a website with more information about the selected news.
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current news that was clicked on
                    News currentNews = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNews.getUrl());

                    // Create a new intent to view the news URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });
        }
    @Override public Loader<List<News>> onCreateLoader (int id, Bundle args){
        //rewrite the query according to users preference

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String sectionChoice = sharedPrefs.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default));

        //for date order
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //http://content.guardianapis.com/search?show-fields=thumbnail&show-tags=contributor&q=future&order-by=newest&from-date=2017-06-01&api-key=test

        // Append query parameter and its value.
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("q", "future");
        uriBuilder.appendQueryParameter("section", sectionChoice);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("from-date", "2015-01-01");
        uriBuilder.appendQueryParameter("api-key", "test");

        // Return the completed uri https://content.guardianapis.com/search?section=business&api-key=test
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override public void onLoadFinished
            (Loader<List<News>> loader, List<News> newsItems){

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No newsItems found."
        mEmptyStateTextView.setText(R.string.no_news);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsItems != null && !newsItems.isEmpty()) {
            mAdapter.addAll(newsItems);
        }
    }

    @Override public void onLoaderReset (Loader < List < News >> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    // for preferences
    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // for preferences
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //this method passes the menuitem that is selected
        int id = item.getItemId(); //In our case, our menu only has one item, android:id="@+id/action_settings".
        if (id == R.id.action_settings) { //in the main.xml
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
