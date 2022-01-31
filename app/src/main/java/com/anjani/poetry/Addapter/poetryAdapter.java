package com.anjani.poetry.Addapter;

import static com.anjani.poetry.R.id.delete_btn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjani.poetry.Api.ApiClient;
import com.anjani.poetry.Api.ApiInterface;
import com.anjani.poetry.Models.poetrymodel;
import com.anjani.poetry.R;
import com.anjani.poetry.Response.DeleteResponse;
import com.anjani.poetry.update_poetry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class poetryAdapter extends RecyclerView.Adapter<poetryAdapter.ViewHodler> {

    Context context;
    List<poetrymodel> poetrymodels;

    ApiInterface apiInterface;
    private ViewHodler holder;
    private int position;


    public poetryAdapter(Context context, List<poetrymodel> poetrymodels) {
        this.context = context;
        this.poetrymodels = poetrymodels;
        Retrofit retrofit = ApiClient.getClient ( );
        apiInterface = retrofit.create (ApiInterface.class);

    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.poetry_list_design, null);
        return new ViewHodler (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, @SuppressLint("RecyclerView") int position) {
        this.holder = holder;
        this.position = position;

        holder.poet.setText (poetrymodels.get (position).getPoet_name ( ));
        holder.poetry.setText (poetrymodels.get (position).getPoetry_data ( ));
        holder.date_time.setText (poetrymodels.get (position).getDate_time ( ));

        holder.delete.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                deletepoetry (poetrymodels.get (position).getId ( ) + " ", position);
            }
        });


        holder.update.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText (context, poetrymodels.get (position).getId ( ) + "\n" + poetrymodels.get (position).getPoetry_data ( ) + "", Toast.LENGTH_SHORT).show ( );
                Intent intent = new Intent (context, update_poetry.class);
                intent.putExtra ("id",poetrymodels.get (position).getId ());
                intent.putExtra ("p_data",poetrymodels.get (position).getPoetry_data ());
                context.startActivity (intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return poetrymodels.size ( );
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // HOLDS VIEWS
    public class ViewHodler extends RecyclerView.ViewHolder {


        TextView poet, poetry, date_time;
        Button update, delete;


        public ViewHodler(@NonNull View itemView) {
            super (itemView);

            poet = itemView.findViewById (R.id.poet_textView);
            poetry = itemView.findViewById (R.id.poetry_textView);
            date_time = itemView.findViewById (R.id.datetime_textView);

            update = itemView.findViewById (R.id.update_btn);
            delete = itemView.findViewById (R.id.delete_btn);



        }
    }


    // DELETE API CALL (RESPONSE)

    private void deletepoetry(String id, int pos) {
        apiInterface.deletepoetry (id).enqueue (new Callback<DeleteResponse> ( ) {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if (response != null) {
                        Toast.makeText (context, response.body ( ).getMessage ( ), Toast.LENGTH_LONG).show ( );

                        // CHECK DELETE OR IF YES THEN WITHOUT REFRESH IT GOES OUT FROM OUR MAIN SCREEN and VIEW

                        if (response.body ( ).getStatus ( ).equals ("1")) {
                            poetrymodels.remove (pos);
                            notifyDataSetChanged ( );
                        }

                    }
                } catch (Exception e) {
                    Log.e ("exp", e.getLocalizedMessage ( ));

                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e ("Failure", t.getLocalizedMessage ( ));
            }
        });
    }


}
