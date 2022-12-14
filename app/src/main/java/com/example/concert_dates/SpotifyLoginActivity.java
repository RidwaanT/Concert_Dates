package com.example.concert_dates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SpotifyLoginActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "46092cda7f3f48c0a52c013ce2db5208";
    public static final String REDIRECT_URI = "com.example.concert_dates://callback";
    public static final int AUTH_TOKEN_REQUEST_CODE = 1017;
    public static final int AUTH_CODE_REQUEST_CODE = 1018;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken;
    private String mAccessCode;
    private Call mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_login);

        getSupportActionBar().setTitle(String.format(Locale.CANADA, "Spotify Login %s", BuildConfig.VERSION_NAME));

    }

    @Override
    protected void onDestroy(){
        cancelCall();
        super.onDestroy();
    }

    public void onGetUserProfileClicked(View view){
        if(mAccessToken == null){
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_spotify_login), R.string.warning_need_token, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
            return;
        }

        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setResponse("Failed to fetch data: " +e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void onRequestCodeClicked(View view){
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(this,AUTH_TOKEN_REQUEST_CODE,request);
    }

    public void onRequestTokenClicked(View view){
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(this,AUTH_TOKEN_REQUEST_CODE,request);
    }

    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type){
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email"})
                .setCampaign("your-campaign-token")
                .build();
    }



    final Request request = new Request.Builder()
            .url("https://api.spotify.com/v1/me")
            .addHeader("Authorization","Bearer " +mAccessToken)
            .build();




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check if result comes from the correct activity
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            updateTokenView();
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            updateCodeView();
        }
    }

    private void setResponse(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView responseView = findViewById(R.id.response_text_view);
                responseView.setText(text);
            }
        });
    }

    private void updateTokenView() {
        final TextView tokenView = findViewById(R.id.token_text_view);
        tokenView.setText(getString(R.string.token,mAccessToken));
    }

    private void updateCodeView() {
        final TextView codeView = findViewById(R.id.code_text_view);
        codeView.setText(getString(R.string.code, mAccessCode));
    }

    private void cancelCall() {
        if(mCall != null){
            mCall.cancel();
        }
    }

    private Uri getRedirectUri(){
        return Uri.parse(REDIRECT_URI);
    }




}