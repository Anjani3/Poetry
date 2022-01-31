package com.anjani.poetry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anjani.poetry.Addapter.poetryAdapter;
import com.anjani.poetry.Api.ApiClient;
import com.anjani.poetry.Api.ApiInterface;
import com.anjani.poetry.Models.poetrymodel;
import com.anjani.poetry.Response.GetPoetryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    poetryAdapter poetryAdapter;
    ApiInterface apiInterface;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initialization ( );
        setSupportActionBar (toolbar);
        getdata ( );


    }


    private void initialization() {
        recyclerView = findViewById (R.id.poetry_recycleView);
        Retrofit retrofit = ApiClient.getClient ( );
        apiInterface = retrofit.create (ApiInterface.class);
        toolbar = findViewById (R.id.main_toolbar);


    }

    private void setadapter(List<poetrymodel> poetrymodels) {

        poetryAdapter = new poetryAdapter (this, poetrymodels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter (poetryAdapter);


    }

    // READ API CALL
    private void getdata() {

  //      recyclerView.clearFocus ();
        apiInterface.getpoetry ( ).enqueue (new Callback<GetPoetryResponse> ( ) {

            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {

                try {
                    if (response.body ( ).getStatus ( ).equals ("1")) {
                        setadapter (response.body ( ).getData ( ));
                    } else {
                        Toast.makeText (MainActivity.this, response.body ( ).getMessege ( ), Toast.LENGTH_SHORT).show ( );
                    }
                } catch (Exception e) {
                    Log.e ("exp", e.getLocalizedMessage ( ));


                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {

                Log.e ("failure", t.getLocalizedMessage ( ));
                Toast.makeText (MainActivity.this, "Found Error" + t.getLocalizedMessage ( ), Toast.LENGTH_LONG).show ( );

            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater ( ).inflate (R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId ( )) {
            case R.id.add_poetry:
                Intent intent = new Intent (MainActivity.this, AddPoetry.class);
                startActivity (intent);

                Toast.makeText (this, "Add Poetry Clicked", Toast.LENGTH_SHORT).show ( );
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }


}