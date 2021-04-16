package com.example.takehomeexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String cityName;
    EditText APIkeyText;
    Spinner cityNameSpinner;
    Button submitButton;
    ProgressDialog progressDialog;
    String defaultApiKeyInput = "ff9f895b2e884d6680530135202710";
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        APIkeyText = findViewById(R.id.apiKeyText);
        APIkeyText.setText(defaultApiKeyInput);


        cityNameSpinner = findViewById(R.id.cityNameSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityNameSpinner.setAdapter(adapter);
        cityNameSpinner.setOnItemSelectedListener(this);
        cityName = cityNameSpinner.getSelectedItem().toString();


        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cityName = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), cityName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        defaultApiKeyInput = APIkeyText.getText().toString();
        if (defaultApiKeyInput.equals("")) {
            Toast.makeText(this, "Please enter your API key", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog();
            getWeatherCurrent();
        }
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }

    private void progressDialog() {

        //initialize progress dialog
        progressDialog = new ProgressDialog(MainPage.this);
        //show dialog
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        //set content view
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000); //3 secs

    }

    private void getWeatherCurrent() {

        Call<LocationData> call = api.getWeatherData(defaultApiKeyInput, cityName);

        call.enqueue(new Callback<LocationData>() {
            @Override
            public void onResponse(Call<LocationData> call, Response<LocationData> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "No response: Please input a valid API Key", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Log.d("Error", String.valueOf(response.code()));
                    return;
                }

                LocationData locationData = response.body();
                String temp_c = response.body().getWeatherCurrent().getTemp_c();
                String temp_f = response.body().getWeatherCurrent().getTemp_f();
                Intent intent = new Intent(getApplicationContext(), ResultPage.class);
                intent.putExtra("tempInCelsius", temp_c);
                intent.putExtra("tempInFahrenheit", temp_f);
                startActivity(intent);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<LocationData> call, Throwable t) {

                Log.d("Error", t.getMessage());
                return;
            }
        });

    }
}