package com.example.foodapp;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TempConverter extends AppCompatActivity {

    Button ansBtn, againBtn;
    TextView showAns;
    EditText getVal;
    RadioButton c2f, f2c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        ansBtn = (Button) findViewById(R.id.ansBtn);
        showAns = (TextView) findViewById(R.id.showAns);
        getVal = (EditText) findViewById(R.id.getVal);
        c2f = (RadioButton) findViewById(R.id.c2f);
        f2c = (RadioButton) findViewById(R.id.f2c);
        againBtn = (Button) findViewById(R.id.againBtn);
        ansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVal.getText().toString().isEmpty()) {
                    showAns.setText("Please give Temperature");

                } else {
                    double x = parseDouble(String.valueOf(getVal.getText()));
                    if (c2f.isChecked()) {
                        x = (x * 9) / 5 + 32;
                        // this is Equation
                        x = Double.parseDouble(new DecimalFormat("##.###").format(x));
                        showAns.setText(String.valueOf(x) + " Degree F ");
                    } else if (f2c.isChecked()) {
                        x = (x - 32) * 5 / 9;
                        x = Double.parseDouble(new DecimalFormat("##.###").format(x));
                        showAns.setText(String.valueOf(x) + " Degree C ");
                    } else {
                        showAns.setText("Please Select an option !");
                    }
                }

            }


        });
        againBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAns.setText("0.0");
                getVal.setText("");
                c2f.setChecked(false);
                f2c.setChecked(false);
            }
        });

    }
}