package com.anjani.poetry;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anjani.poetry.Api.ApiClient;
import com.anjani.poetry.Api.ApiInterface;
import com.anjani.poetry.Response.DeleteResponse;

import java.util.Objects;
import java.lang.String;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetry_data, poet_name;
    Button submitbtn;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_poetry);
        initialization ( );
        setToolbar ( );


        submitbtn.setOnClickListener (v -> {

            String poetryDataString = poetry_data.getText ( ).toString ();
            String poetryNameString = poet_name.getText ( ).toString ( );

            if (poetryDataString.equals ("")) {
                poetry_data.setError ("Field is empty ");
            } else if (poetryNameString.equals ("")) {
                poet_name.setError ("Field is empty ");
            } else {
                Toast.makeText (AddPoetry.this, "Call Api", Toast.LENGTH_SHORT).show ( );
                callapi (poetryDataString, poetryNameString);
            }



    });

}


    private void initialization() {

        toolbar = findViewById (R.id.add_poetry_toolbar);
        poetry_data = findViewById (R.id.add_poetry_data_edittext);
        poet_name = findViewById (R.id.add_poet_name_edittext);
        submitbtn = findViewById (R.id.submit_data_btn);

// API INTERFACE INITIALIZE

        Retrofit retrofit = ApiClient.getClient ( );
        apiInterface = retrofit.create (ApiInterface.class);

    }

    //implement back btn
    private void setToolbar() {
        setSupportActionBar (toolbar);
        Objects.requireNonNull (getSupportActionBar ( )).setDisplayHomeAsUpEnabled (true);
        toolbar.setNavigationOnClickListener (v -> finish ( ));

    }

    private void callapi(String poetry_data, String poet_name) {

        apiInterface.add_poetry (poetry_data, poet_name).enqueue (new Callback<DeleteResponse> ( ) {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {
                    if (response.body ( ).getStatus ( ).equals ("1")) {
                        Toast.makeText (AddPoetry.this, "Added successfully", Toast.LENGTH_SHORT).show ( );
                        Intent intent= new Intent (AddPoetry.this,MainActivity.class );
                        startActivity (intent);
                        finish ();

                    } else {
                        Toast.makeText (AddPoetry.this, "Not Added", Toast.LENGTH_SHORT).show ( );

                    }
                } catch (Exception e) {
                    Log.e ("failure", e.getLocalizedMessage ( ));

                }


            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e ("failure", t.getLocalizedMessage ( ));
            }
        });
    }


}