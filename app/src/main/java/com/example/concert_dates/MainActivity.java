package com.example.concert_dates;

import static com.spotify.sdk.android.auth.AuthorizationResponse.Type.TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.concert_dates.Helpers.Artist;
import com.example.concert_dates.Helpers.ArtistsAdapter;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "46092cda7f3f48c0a52c013ce2db5208";
    public static final String REDIRECT_URI = "http://localhost:8888/callback";
    public static final int AUTH_TOKEN_REQUEST_CODE = 1017;
    public static final int AUTH_CODE_REQUEST_CODE = 1018;


    public ArrayList<Artist> artists = new ArrayList<>();
    private String topArtistsAPIURL = "https://api.spotify.com/v1/me/top/artists";


    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken;
    private String mAccessCode;
    private Call mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginActivityAuthentication();
    }

    @Override
    protected  void onDestroy(){
        cancelCall();
        super.onDestroy();
    }

    /**
     * Makes a request to Spotify for the users top 20 artists and input the users in the
     * main activity recycler view.
     * @param act
     */
    private void getTopArtists(Context act){

        if(mAccessToken == null){ // if our token hasn't been set then we won't be able to make a request
            return;
        }

        // Spotify API for getting the top 20 artists.
        final Request request = new Request.Builder()
                .url(topArtistsAPIURL)
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        mCall = mOkHttpClient.newCall(request); // Create call for the request.

        mCall.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                setResponse("Failed to fetch data: " +e); // If call fails we show error to screen.
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    addArtistsToArrayFromJSON(response.body().string());

                } catch (JSONException e) {
                    setResponse("Failed to parse data: " +e);
                }
                if (artists.size() > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Create adapter passing in the sample user data
                            ArtistsAdapter adapter = new ArtistsAdapter(artists);
                            RecyclerView rvArtists = (RecyclerView) findViewById(R.id.main_rv_Artists);

                            //Attach the adapter to the recyclerview to populate items
                            rvArtists.setAdapter(adapter);
                            //Set layout manager to position the items in rows of 2
                            rvArtists.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                            // Sets the Sub heading to Artists
                            final TextView responseView = (TextView) findViewById(R.id.main_txv_Category);
                            responseView.setText("Artists");
                        }
                    });
                }
            }
        });
    }

    /**
     * Checks to see if the user has active token to make spotify requests and has accepted the
     * user top read scope. If the user does not have this a request is made for the user to login
     * to their account.
     */
    protected void loginActivityAuthentication(){
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming user-top-read"});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
        // Check if response comes from the correct activity

        if (response.getError() != null && !response.getError().isEmpty()){
            setResponse(response.getError());
        }

        if(AUTH_TOKEN_REQUEST_CODE == requestCode){
            mAccessToken = response.getAccessToken();
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
        }

        getTopArtists(getApplicationContext());


    }

    /**
     * Displays error message to sub heading in a not so beautiful way.
     * @param text message to display
     */
    private void setResponse(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView responseView = (TextView) findViewById(R.id.main_txv_Category);
                responseView.setText(text);
            }
        });
    }

    private void cancelCall() {
        if(mCall != null){
            mCall.cancel();
        }
    }

    /**
     * After retrieving the response from spotify for top artists this will add the top artists into the array.
     * @param jsonData response string of  Spotify's API of top artists
     * @throws JSONException
     */
    public void addArtistsToArrayFromJSON(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray artistsResponse = jsonObject.getJSONArray("items");
        if(artistsResponse != null && artistsResponse.length()>0){
            for (int i = 0; i < artistsResponse.length(); i++) {
                JSONObject object = artistsResponse.getJSONObject(i);
                String artistName = object.getString("name");

                JSONArray images = object.getJSONArray("images");
                String artistImageURL = images.getJSONObject(1).getString("url");

                String artistSpotifyID = object.getString("id");

                Artist artist = new Artist(artistName, artistSpotifyID, artistImageURL);
                artists.add(artist);
            }
        }
    }



    /// user-top-read

}