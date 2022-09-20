package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FullDashboard extends AppCompatActivity implements View.OnClickListener{

    public CardView card1 , card2 ,card3, card4,card5,card6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_dashboard);

        //assign the ids for the card views
        card1 = (CardView) findViewById(R.id.cv1);
        card2 = (CardView) findViewById(R.id.cv2);
        card3 = (CardView) findViewById(R.id.cv3);
        card4 = (CardView) findViewById(R.id.cv4);
        card5 = (CardView) findViewById(R.id.cv5);
        card6 = (CardView) findViewById(R.id.cv6);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent i;

        //navigate between the pages
        switch (v.getId()) {
            case R.id.cv1:
                i= new Intent(this,AdminDashboard.class);
                startActivity(i);
                break;

            case R.id.cv5:
                i= new Intent(this,TempConverter.class);
                startActivity(i);
                break;
        }
    }


}