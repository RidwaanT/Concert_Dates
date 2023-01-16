package com.example.concert_dates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concert_dates.Helpers.Concert;
import com.example.concert_dates.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ConcertActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;

    private String CONCERT_EXTRA_CLASS = "concert_extra_class";
    Concert concert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);

        String json = getIntent().getStringExtra(CONCERT_EXTRA_CLASS);
        this.concert = new Gson().fromJson(json,Concert.class);

        setupViews();
    }

    private void setupViews(){
        ImageView concertImage = (ImageView) findViewById(R.id.concert_iv_ConcertImage);
        Picasso.get().load(concert.getImageURL()).into(concertImage);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.concert_f_map);
        supportMapFragment.getMapAsync(this);

        setConcertDetails();
        setVenueInfo();
        setSeatMap();



    }

    /**
     * Moves the camera to the location of the concert and adds a marker.
     * @param googleMap Auto inserted by Override.
     */
    @Override
    public void onMapReady(GoogleMap googleMap){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(concert.getLatlng(),13F));
        googleMap.addMarker(new MarkerOptions()
                .position(concert.getLatlng())
                .title("Venue"));
    }

    /**
     * Adds Venue Name, Venue Address, and Venue city to the view.
     */
    private void setVenueInfo(){
        TextView venueName = (TextView) findViewById(R.id.concert_tv_VenueName);
        venueName.setText(concert.getVenueName());

        TextView venueAddress = (TextView) findViewById(R.id.concert_tv_VenueAddress);
        venueAddress.setText(concert.getVenueAddress());


        TextView venueCity = (TextView) findViewById(R.id.concert_tv_VenueCity);
        venueCity.setText(concert.getCity());
    }

    /**
     * Adds Start Date and Start time to the View
     */
    private void setConcertDetails(){
        TextView concertDate = (TextView) findViewById(R.id.concert_tv_Date);
        concertDate.setText(concert.getStartDate());

        TextView concertTime = (TextView) findViewById(R.id.concert_tv_Time);
        concertTime.setText(concert.getTime());
    }

    /**
     * If there is a seat map available it adds a seat map to the view, depending on upload data from src.
     */
    private void setSeatMap(){
        ImageView seatMap = (ImageView) findViewById(R.id.concert_iv_SeatMap);
        if(concert.getSeatmapURL() != null){
            Picasso.get().load(concert.getSeatmapURL()).into(seatMap);
        } else {
            seatMap.setVisibility(View.INVISIBLE);
            TextView seatMapTitle = (TextView) findViewById(R.id.seatmap_title);
            seatMapTitle.setVisibility(View.INVISIBLE);
        }


    }
}