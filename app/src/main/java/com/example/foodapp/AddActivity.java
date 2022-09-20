package com.example.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;



public class AddActivity extends AppCompatActivity {

    //initialize variables
    EditText food_name,price,furl;
    Button btnAdd, btnBack;

    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //assign variables

        food_name =(EditText)findViewById(R.id.txtName);
        price = (EditText)findViewById(R.id.txtPrice);
        furl= (EditText)findViewById(R.id.txtImageUrl);



        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //add validation for food name
        awesomeValidation.addValidation(this,R.id.txtName,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        //add validation for food price
        awesomeValidation.addValidation(this,R.id.txtPrice,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        //add validation for food url
        awesomeValidation.addValidation(this,R.id.txtImageUrl,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);



        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);




        //validation

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check validation
                if (awesomeValidation.validate()) {

                    //on success
                    Toast.makeText(getApplicationContext()
                            , "Data Inserted Successfully...", Toast.LENGTH_SHORT).show();
                    insertData();   //call the insertData method
                    clearAll();     //call the clearAll method
                } else {
                    Toast.makeText(getApplicationContext()
                            , "Validation Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }   //redirect to recycler view activity
        });

    }





    //insert function
    private void insertData(){
        //map the data which is taken from insert form
        Map<String,Object> map = new HashMap<>();
        map.put("food_name",food_name.getText().toString());
        map.put("price",price.getText().toString());
        map.put("furl",furl.getText().toString());

        //pass data to database
        FirebaseDatabase.getInstance().getReference().child("foods").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddActivity.this, "Error While Insertion", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //clear all the text fields after insert the data
    private void clearAll(){
        food_name.setText("");
        price.setText("");
        furl.setText("");
    }


}