package it.unibz.coviddashbord.service;

import android.widget.Toast;

import java.util.List;

import it.unibz.coviddashbord.model.CountryStats;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //private static String BASE_URL= "http://192.168.43.125:8080";
    private static String BASE_URL= "https://ccpm.inf.unibz.it";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}