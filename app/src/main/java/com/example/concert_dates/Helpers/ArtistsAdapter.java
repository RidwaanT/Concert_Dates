package com.example.concert_dates.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.concert_dates.EventsActivity;
import com.example.concert_dates.R;

import com.squareup.picasso.Picasso;

import java.util.List;

//  Create the basic adapter extending from RecyclerView.Adapter
//  Note that we specify the custom ViewHolder which gives us access to our views
public class ArtistsAdapter extends
        RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    //Store a member variable for the contacts

    private List<Artist> mArtists;

    private String ARTIST_EXTRA_NAME = "artist_extra_name";
    private String ARTIST_EXTRA_IMAGEURL = "artist_extra_imageurl";
    // ... constructor and member variables

    // usually involves inflating a layout from XML and returning the holder
    @Override
    public ArtistsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View artistView = inflater.inflate(R.layout.card_artist, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(artistView);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArtistsAdapter.ViewHolder holder, int position){
        // Get the data model based on position
        Artist artist = mArtists.get(position);

        //Set item views based on your views and data model
        TextView textview = holder.nameTextView;
        int number = position+1; // Artists are 1-indexed
        textview.setText(number + ". " +artist.getName()); //index the artist and add artist name

        ImageView imageView = holder.artistImageView;
        Picasso.get().load(artist.getImageURL()).into(imageView); // Sets the card image to the artists image from spotify.

        holder.itemView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // the events activity will use the artist name and url to present the next page.
                Intent intent = new Intent(holder.cardLinearLayout.getContext(), EventsActivity.class);
                intent.putExtra(ARTIST_EXTRA_NAME, artist.getName());
                intent.putExtra(ARTIST_EXTRA_IMAGEURL, artist.getImageURL());
                holder.cardLinearLayout.getContext().startActivity(intent); //We need our current activities context which
                                                                            //is the main page.
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArtists.size();
    }



    // Provide a direct reference to each of the views within a data item
    // used to cache the views within the item Layout for fast access

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public LinearLayout cardLinearLayout;
        public TextView nameTextView;
        public ImageView artistImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview

        public ViewHolder(View itemView){
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            cardLinearLayout = (LinearLayout) itemView.findViewById(R.id.card_ll_CardLayout);
            nameTextView = (TextView) itemView.findViewById(R.id.card_tv_ArtistName);
            artistImageView = (ImageView) itemView.findViewById(R.id.card_iv_ArtistImage);
        }
    }

    public ArtistsAdapter(List<Artist> artists){
        mArtists = artists;
    }

}
