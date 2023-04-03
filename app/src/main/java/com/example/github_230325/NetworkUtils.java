package com.example.github_230325;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {

    private final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    private final static String PARAM_QUERY = "q";

    private final static String PARAM_SORT = "sort";
    private final static String SORT_BY = "stars";

    private static URL buildUrl(String gitHubSearchQuery) {
        //Create Full Link
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, gitHubSearchQuery)
                .appendQueryParameter(PARAM_SORT, SORT_BY)
                .build();
        //Convert URI To URL
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

    private static List<RepositoryData> jsonFormatter(String jsonResponse){
        List<RepositoryData> repositoryList = new ArrayList<>();

        try{
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.getJSONArray("items");
            //Get First 50 Repository
            //Log.d("json", String.valueOf(items.getJSONObject(0)));
            //JSONObject currentRepotest = items.getJSONObject(0);
            //Log.d("current id", currentRepotest.getString("avatar_url"));

            int dataLen = 50;
            if(items.length() < dataLen){
                dataLen = items.length();
            }
            for(int i = 0 ; i < dataLen ; i++){
                JSONObject currentRepo = items.getJSONObject(i);
                String ownerAvatar_url = currentRepo.getJSONObject("owner").getString("avatar_url");
                //Log.d("ownerAvatar_url", ownerAvatar_url);
                String repoName = currentRepo.getString("name");
                String repoDesc = currentRepo.getString("description");
                String repoOwner = currentRepo.getJSONObject("owner").getString("login");
                String repoStars = currentRepo.getString("stargazers_count");

                Log.v("Data","Number " + i);
                //Create Repository Object
                RepositoryData repository = new RepositoryData(ownerAvatar_url,repoName,repoDesc,repoOwner,repoStars);
                //Add This Repository To List
                Log.d("res", repository.toString());
                repositoryList.add(repository);
            }
        }
        catch (JSONException ex){
            //Log.d("error", jsonResponse);
            Log.v("Network","Can't Read Json");
        }
//        Log.d("res", repositoryList.toString());
        return repositoryList;
    }

    public static List<RepositoryData> getDataFromApi(String query) throws IOException {
        //First Create URL
        URL apiURL = buildUrl(query);
        String jsonResponse = getResponseFromHttpUrl(apiURL);
        List<RepositoryData> repositoryList = jsonFormatter(jsonResponse);
        return repositoryList;
    }

}
