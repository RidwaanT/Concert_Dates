package com.example.concert_dates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = new Intent(this, SpotifyLoginActivity.class);
        startActivity(myIntent);
    }


    public void test(View view){
        System.out.print("Best");
    }
}