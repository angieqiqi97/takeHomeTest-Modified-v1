package com.example.takehomeexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ResultPage extends AppCompatActivity {

    public EditText tempInCelsius, tempInFahrenheit;
    String temp_c, temp_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        tempInCelsius = findViewById(R.id.tempC);
        tempInFahrenheit = findViewById(R.id.tempF);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            temp_c = bundle.getString("tempInCelsius");
            temp_f = bundle.getString("tempInFahrenheit");
            tempInCelsius.setText(temp_c);
            tempInFahrenheit.setText(temp_f);
        }

        else {
            Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show();
        }
    }
}