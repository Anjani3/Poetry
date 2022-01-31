package com.anjani.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.anjani.poetry.Api.ApiClient;
import com.anjani.poetry.Api.ApiInterface;
import com.anjani.poetry.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class update_poetry extends AppCompatActivity {
    Toolbar toolbar;
    EditText poetrydata;
    AppCompatButton updatesubmitbtn;
    int poertyId;
    String poetryData;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_update_poetry);
        initialization ( );
        setuptoolbar ( );


        updatesubmitbtn.setOnClickListener (v -> {

            String p_data = poetrydata.getText ( ).toString ( );

            if (p_data.equals ("")) {
                poetrydata.setError ("Field is empty");
            } else {
                callapi (p_data,poertyId+"");
            }


        });


    }

    private void initialization() {
        toolbar = findViewById (R.id.update_poetry_toolbar);
        poetrydata = findViewById (R.id.update_poetry_data_edittext);
        updatesubmitbtn = findViewById (R.id.update_data_btn);

        poertyId = getIntent ( ).getIntExtra ("id", 0);
        poetryData = getIntent ( ).getStringExtra ("p_data");

        poetrydata.setText (poetryData);

        Retrofit retrofit = ApiClient.getClient ( );
        apiInterface = retrofit.create (ApiInterface.class);

    }


    private void setuptoolbar() {
        setSupportActionBar (toolbar);
        getSupportActionBar ( ).setDisplayHomeAsUpEnabled (true);

        toolbar.setNavigationOnClickListener (v -> finish ( ));

    }


    private void callapi(String poetry_data, String id) {

        apiInterface.update_poetry (poetry_data, id).enqueue (new Callback<DeleteResponse> ( ) {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {
                    if (response.body ( ).getStatus ( ).equals ("1")) {
                        Toast.makeText (update_poetry.this, "Data  Update successfully", Toast.LENGTH_SHORT).show ( );

                        Intent intent= new Intent ( update_poetry.this,MainActivity.class );
                        startActivity (intent);
                        finish ();

                    } else {
                        Toast.makeText (update_poetry.this, "Data not Updated", Toast.LENGTH_SHORT).show ( );
                    }

                } catch (Exception e) {
                    Log.e ("Updatexp", e.getLocalizedMessage ( ));
                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e ("Update fail", t.getLocalizedMessage ( ));
            }
        });
    }




}