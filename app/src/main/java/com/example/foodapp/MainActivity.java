package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{   //onclickListener -Interface definition for a callback to be invoked when a view is clicked

    public Button btn1, btn2, btn3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {       //initial call of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieve the components in the UI

        btn1 = (Button) findViewById(R.id.btnSignup);
        btn2 = (Button) findViewById(R.id.btnLogin);
        btn3 = (Button) findViewById(R.id.btnstart);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {     //Called when a view has been clicked.
        Intent intent1;

        //navigate between the pages
        switch (v.getId()) {
            case R.id.btnSignup:
                intent1 = new Intent(this, Signup.class);
                startActivity(intent1);
                break;

            case R.id.btnLogin:
                intent1 = new Intent(this, Login.class);
                startActivity(intent1);
                break;

            case R.id.btnstart:
                intent1 = new Intent(this, FullDashboard.class);
                startActivity(intent1);
                break;

        }
    }
}