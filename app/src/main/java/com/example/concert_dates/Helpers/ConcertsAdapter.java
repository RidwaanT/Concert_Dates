package com.example.concert_dates.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concert_dates.ConcertActivity;
import com.example.concert_dates.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

//  Create the basic adapter extending from RecyclerView.Adapter
//  Note that we specify the custom ViewHolder which gives us access to our views
public class ConcertsAdapter extends
        RecyclerView.Adapter<ConcertsAdapter.ViewHolder> {

    //Store a member variable for the contacts
    private List<Concert> mConcerts;


    private String CONCERT_EXTRA_CLASS = "concert_extra_class";
    // ... constructor and member variables

    // usually involves inflating a layout from XML and returning the holder
    @Override
    public ConcertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View concertView = inflater.inflate(R.layout.card_concert_venue, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(concertView);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ConcertsAdapter.ViewHolder holder, int position){
        // Get the data model based on position
        Concert concert = mConcerts.get(position);

        //Set item views based on your views and data model
        TextView concertNameTextView = holder.concertNameTextView;
        TextView dateTextView = holder.dateTextView;
        TextView venueNameTextView = holder.venueNameTextView;
        TextView cityTextView = holder.cityTextView;

        // Set text values for the text views
        concertNameTextView.setText(concert.getConcertName());
        dateTextView.setText(concert.getStartDate());
        venueNameTextView.setText(concert.getVenueName());
        cityTextView.setText(concert.getCity());


        ImageView imageView = holder.concertImageView;
        Picasso.get().load(concert.getImageURL()).into(imageView);

        holder.itemView.setOnClickListener( new View.OnClickListener(){ // Make each card clickable.
            @Override
            public void onClick(View v){
                Intent intent = new Intent(holder.cardConstraintLayout.getContext(), ConcertActivity.class);
                String conc = (new Gson().toJson(concert)); // Convert concert to JSON to be sent to next intent.
                intent.putExtra(CONCERT_EXTRA_CLASS, conc);

                holder.cardConstraintLayout.getContext().startActivity(intent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mConcerts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // used to cache the views within the item Layout for fast access

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ConstraintLayout cardConstraintLayout;
        public TextView concertNameTextView;
        public TextView dateTextView;
        public TextView venueNameTextView;
        public TextView cityTextView;
        public ImageView concertImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview

        public ViewHolder(View itemView){
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            cardConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.card_cl_concert);
            concertNameTextView = (TextView) itemView.findViewById(R.id.card_tv_concertName);
            dateTextView = (TextView)  itemView.findViewById(R.id.card_tv_date);
            venueNameTextView = (TextView) itemView.findViewById(R.id.card_tv_venueName);
            cityTextView = (TextView) itemView.findViewById(R.id.card_tv_city);
            concertImageView = (ImageView) itemView.findViewById(R.id.card_iv_concertImage);
        }

    }

    public ConcertsAdapter(List<Concert> concerts){mConcerts = concerts;}

}
