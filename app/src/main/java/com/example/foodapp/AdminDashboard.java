package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //firebase connection
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foods"), MainModel.class)   //pass the database name
                        .build();

        mainAdapter =  new MainAdapter(options);        //create mainAdapter object
        recyclerView.setAdapter(mainAdapter);           //connect the recyclerView , Adapters provide a binding from data set to views that are displayed within a RecyclerView.



        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));    //navigate to add a new record form
            }
        });
    }

    @Override

    //activity get visible to user
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override

    //activity not visible to user
    protected void onStop() {
        super.onStop();
        mainAdapter.startListening();
    }

    @Override

    //search menu bar

    public boolean onCreateOptionsMenu(Menu menu) {     //To specify the options menu for an activity

        getMenuInflater().inflate(R.menu.search,menu);      //convert the XML resource into a programmable object
        MenuItem item = menu.findItem(R.id.search);            //create a object from MenuItem
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);                           //call the txtSearch method
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);               //call the txtSearch method
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    //text search from database using the search bar

    private void txtSearch(String str){
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("foods").orderByChild("food_name").startAt(str).endAt(str+"~"), MainModel.class)  //search by food name
                        .build();

        mainAdapter =  new MainAdapter(options);
        mainAdapter.startListening();               //Start receiving changes of adapter
        recyclerView.setAdapter(mainAdapter);
    }




}