package com.example.concert_dates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concert_dates.Helpers.Concert;
import com.example.concert_dates.Helpers.ConcertsAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.squareup.picasso.Picasso;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private String artistName = null;
    private String artistTicketMasterID = null;
    private String artistImageURL = null;

    private String ARTIST_EXTRA = "artist_extra_name";
    private String ARTIST_EXTRA_IMAGEURL = "artist_extra_imageurl";
    private String tmAPI = BuildConfig.tm_api_key;
    private String artistRequestURL = "https://app.ticketmaster.com/discovery/v2/attractions";
    private String concertRequestURL = "https://app.ticketmaster.com/discovery/v2/events";

    private ArrayList<Concert> concerts = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;

    private LatLng latLng;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private Call mCall;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        artistName = getIntent().getStringExtra(ARTIST_EXTRA);
        getLastLocation();

        setViews();
        getConcerts(artistName);

    }

    private void setViews(){
        // Add the artist name to the view
        TextView tvArtistName = (TextView) findViewById(R.id.events_tv_ArtistName);
        tvArtistName.setText(artistName);

        // Add the artist image from spotify to the view in case TicketMaster doesn't have one.
        ImageView ivArtistImage = (ImageView) findViewById(R.id.events_iv_ArtistImage);
        String imageURL = getIntent().getStringExtra(ARTIST_EXTRA_IMAGEURL);
        Picasso.get().load(imageURL).into(ivArtistImage);

    }


    @SuppressLint("MissingPermission")
    public void getLastLocation(){
        checkAndRequestPermissions();
        fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_LOW_POWER, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        }).addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                latLng = new LatLng(latitude, longitude); // Set our latlng
            }
        });
    }

    /**
     * Gets the concerts info by making an API request to ticket master.
     * @param artistName Name of the performer
     */
    public void getConcerts(String artistName){
        // We create a url that searches for the artist using keyword and has our API
        HttpUrl url = HttpUrl.parse(artistRequestURL).newBuilder()
                .addQueryParameter("apikey",tmAPI)
                .addQueryParameter("keyword",artistName)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setResponse("Failed to fetch data: " + e); // If call fails we show error to screen.
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    getArtistFromTicketMaster(response.body().string());
                    final ImageView imageView = (ImageView) findViewById(R.id.events_iv_ArtistImage);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get().load(artistImageURL).into(imageView);
                        }
                    });

                } catch (JSONException e) {
                    setResponse("Failed to parse data: " + e);
                }

                if (artistTicketMasterID != null) {
                    HttpUrl url2;
                    if(latLng != null){
                        url2 = HttpUrl.parse(concertRequestURL).newBuilder()
                                .addQueryParameter("apikey", tmAPI)
                                .addQueryParameter("attractionId", artistTicketMasterID)
                                .addQueryParameter("latlong", Double.toString(latLng.latitude) +"," + Double.toString(latLng.longitude))
                                .build();
                    } else {
                        url2 = HttpUrl.parse(concertRequestURL).newBuilder()
                            .addQueryParameter("apikey", tmAPI)
                            .addQueryParameter("attractionId", artistTicketMasterID)
                            .build();
                    }



                    final Request request = new Request.Builder()
                            .url(url2)
                            .build();

                    Call bCall = mOkHttpClient.newCall(request);

                    bCall.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            setResponse("Failed to fetch data: " + e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                addConcertsToArrayFromJSON(response.body().string());
                            } catch (JSONException e) {
                                setResponse("Failed to parse data: " + e);
                            }
                            if (concerts.size() > 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Create adapter passing in the sample user data
                                        ConcertsAdapter adapter = new ConcertsAdapter(concerts);
                                        RecyclerView rvArtists = (RecyclerView) findViewById(R.id.events_rv_concerts);

                                        //Attach the adapter to the recyclerview to populate items
                                        rvArtists.setAdapter(adapter);
                                        //Set layout manager to position the items
                                        rvArtists.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextView noneMessage = (TextView) findViewById(R.id.events_tv_NoConcerts);
                                        noneMessage.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });
    }


    /**
     * Searches and gets the Aritst ID from ticket Master
     * @param jsonData the raw data obtained from Ticket Master API
     * @throws JSONException
     */
    private void getArtistFromTicketMaster(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        jsonObject = jsonObject.getJSONObject("_embedded"); // This is the head object for the json
        JSONArray artistsResponse = jsonObject.getJSONArray("attractions");
        if(artistsResponse!= null && artistsResponse.length()>0){
            for (int i = 0; i <artistsResponse.length() ; i++) {
                JSONObject object = artistsResponse.getJSONObject(i);
                String curArtistName = object.getString("name");

                if(curArtistName.equalsIgnoreCase(artistName)){
                    artistTicketMasterID = object.getString("id");
                    artistImageURL = object.getJSONArray("images").getJSONObject(0).getString("url");
                    return;
                }
            }
        }

        setResponse("Can't find artist");

    }

    /**
     * Takes the concerts found from the api call and creates a Concert object that is added to the array list.
     * @param jsonData Raw data obtained from the Ticket Master API call
     * @throws JSONException When we access an item that doesn't exist this triggers.
     */
    public void addConcertsToArrayFromJSON(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        try{ // Basically checks if there are any concerts.
            jsonObject = jsonObject.getJSONObject("_embedded");
        } catch (JSONException e){
            return; // No concerts than we can set this to null.
        }

        JSONArray concertsResponse = jsonObject.getJSONArray("events"); // if statement is useless
        for (int i = 0; i <concertsResponse.length() ; i++) {

            JSONObject object = concertsResponse.getJSONObject(i);
            String concertName = object.getString("name");
            String concertImageURL = object.getJSONArray("images").getJSONObject(0).getString("url");
            String seatMapURL;
            try {// Heat maps may not exist so we check if it does.
                seatMapURL=object.getJSONObject("seatmap").getString("staticUrl");
            }catch (JSONException e){
                seatMapURL = null;
            }


            String startDate = object.getJSONObject("dates").getJSONObject("start").getString("localDate");
            String time = null;
            // Start time may not show so we check for start time.
            if(!object.getJSONObject("dates").getJSONObject("start").getBoolean("noSpecificTime")){
                time = object.getJSONObject("dates").getJSONObject("start").getString("localTime");
            } else {
                time = "No set time";
            }

            JSONArray jsonVenuesArray = object.getJSONObject("_embedded").getJSONArray("venues");
            JSONObject jsonVenueObject = jsonVenuesArray.getJSONObject(0);

            String venueName = jsonVenueObject.getString("name");
            String city = jsonVenueObject.getJSONObject("city").getString("name");
            String address = jsonVenueObject.getJSONObject("address").getString("line1");
            Double longitude = jsonVenueObject.getJSONObject("location").getDouble("longitude");
            Double latitude = jsonVenueObject.getJSONObject("location").getDouble("latitude");
            Concert concert;
            if(seatMapURL != null){  // We use different methods depending on if we have a heat map.
                concert = new Concert(artistName,concertName,venueName,city,address,startDate,time,longitude,latitude,concertImageURL,seatMapURL);
            } else {
                concert = new Concert(artistName,concertName,venueName,city,address,startDate,time,longitude,latitude,concertImageURL);
            }
            concerts.add(concert);
        }
    }

    /**
     * Place message in heading text view. Not an attractive view.
     * @param text
     */
    private void setResponse(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView responseView = (TextView) findViewById(R.id.events_tv_ArtistName);
                responseView.setText(text);
            }
        });
    }

    public boolean checkAndRequestPermissions() {
        int loc = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }
}