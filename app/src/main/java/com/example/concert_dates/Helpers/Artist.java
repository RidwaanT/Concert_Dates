package com.example.concert_dates.Helpers;

/**
 * The recording artist who's information was taken from Spotify
 */
public class Artist {

    private String name; // Stage name of the artist.
    private String id; // The id of the artist as it applies to Spotify.
    private String imageURL; // The URL to the image of the artist.


    public Artist(){

    }

    /**
     * Initialize Artist
     * @param name Stage name of artist
     */
    public Artist(String name) {this.name = name;}

    /**
     * Initialize Artist
     * @param name Stage name of artist
     * @param id Spotify id of artist
     */
    public Artist(String name, String id){
        this.name = name;
        this.id = id;
    }

    /**
     * Initialize Artist
     * @param name Stage name of artist
     * @param id Spotify ID of artist
     * @param imageURL URL of artist image.
     */
    public Artist(String name, String id, String imageURL){
        this.name = name;
        this.id= id;
        this.imageURL = imageURL;
    }

    /**
     *
     * @return stage name of artist
     */
    public String getName() {
        return name;
    }

    /**
     * Set stage name of artist
     * @param name Stage name of artist
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Spotify ID
     * @return Spotify ID of artist
     */
    public String getId() {
        return id;
    }

    /**
     * Should not be used unless retrieving id from spotify
     * @param id Spotify id of artist
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get URL of artist image
     * @return image URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Should not be used unless retrieving this info from Spotify.
     * @param imageURL Get image url of artist
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
