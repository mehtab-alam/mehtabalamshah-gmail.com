package it.unibz.coviddashbord.service;

import java.util.List;

import it.unibz.coviddashbord.model.CountryStats;
import it.unibz.coviddashbord.model.Dashboard;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidInterface {


    @GET("/api/covid")
    public Call<List<CountryStats>> getCountryStats();

    @GET("/api/covidDashboard")
    public Call<Dashboard> getDashbaord();
//    @GET("/covid.json?dl=0")
//    public Call<List<CountryStats>> getCountryStats();


}