package com.example.concert_dates.Helpers;

import com.google.android.gms.maps.model.LatLng;

public class Concert {

    private String artist;
    private String concertName;
    private String city;
    private String venueName;
    private String venueAddress;
    private String startDate;
    private String time;
    private String seatmapURL;
    private String imageURL;
    private Double longitude;
    private Double latitude;
    private LatLng latlng;

    /**
     *
     * @param artist Artist performing
     * @param concertName Name of the concert
     * @param venueName The venue name of the concert
     * @param city City the concert will be hosted in
     * @param venueAddress Address of the venue
     * @param startDate Local start date of the venue
     * @param time Time the venue starts at
     * @param longitude the Longitude of the venue
     * @param latitude The latitude of the venue
     */
    public Concert(String artist, String concertName, String venueName, String city, String venueAddress, String startDate, String time, Double longitude, Double latitude) {
        this.artist = artist;
        this.concertName = concertName;
        this.venueName = venueName;
        this.city = city;
        this.venueAddress = venueAddress;
        this.startDate = startDate;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;

        this.latlng = new LatLng(latitude,longitude);
    }

    /**
     *
     * @param artist Artist performing
     * @param concertName Name of the concert
     * @param venueName The venue name of the concert
     * @param city City the concert will be hosted in
     * @param venueAddress Address of the venue
     * @param startDate Local start date of the venue
     * @param time Time the venue starts at
     * @param longitude the Longitude of the venue
     * @param latitude The latitude of the venue
     * @param imageURL the url to the poster image for the concert.
     */
    public Concert(String artist, String concertName, String venueName, String city, String venueAddress, String startDate, String time, Double longitude, Double latitude, String imageURL) {
        this.artist = artist;
        this.concertName = concertName;
        this.venueName = venueName;
        this.city = city;
        this.venueAddress = venueAddress;
        this.startDate = startDate;
        this.time = time;
        this.imageURL = imageURL;
        this.latitude = latitude;
        this.longitude = longitude;

        this.latlng = new LatLng(latitude,longitude);


    }


    /**
     *
     * @param artist Artist performing
     * @param concertName Name of the concert
     * @param venueName The venue name of the concert
     * @param city City the concert will be hosted in
     * @param venueAddress Address of the venue
     * @param startDate Local start date of the venue
     * @param time Time the venue starts at
     * @param longitude the Longitude of the venue
     * @param latitude The latitude of the venue
     * @param imageURL the url to the poster image for the concert.
     * @param seatmapURL the url to the seatmap image for the concert.
     */
    public Concert(String artist, String concertName, String venueName, String city, String venueAddress, String startDate, String time, Double longitude, Double latitude, String imageURL, String seatmapURL) {
        this.artist = artist;
        this.concertName = concertName;
        this.venueName = venueName;
        this.city = city;
        this.venueAddress = venueAddress;
        this.startDate = startDate;
        this.time = time;
        this.seatmapURL = seatmapURL;
        this.imageURL = imageURL;
        this.latitude = latitude;
        this.longitude = longitude;

        this.latlng = new LatLng(latitude,longitude);
    }



    public String getConcertName() {
        return concertName;
    }

    public String getCity() {
        return city;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTime() {
        return time;
    }

    public String getSeatmapURL() {
        return seatmapURL;
    }

    public String getArtist() {
        return artist;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
