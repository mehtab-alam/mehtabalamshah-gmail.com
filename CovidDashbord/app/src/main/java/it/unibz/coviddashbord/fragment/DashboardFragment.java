package it.unibz.coviddashbord.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import it.unibz.coviddashbord.MainActivity;
import it.unibz.coviddashbord.R;
import it.unibz.coviddashbord.model.CountryStats;
import it.unibz.coviddashbord.model.Dashboard;
import it.unibz.coviddashbord.service.ApiClient;
import it.unibz.coviddashbord.service.CovidInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout swipeRefreshLayout;
    TextView coronaCase;
    TextView totalRecovered;
    TextView totalDeaths;
    TextView currentlyInfected;
    TextView mild;
    TextView serious;
    TextView outcomeCase;
    TextView discharged;
    TextView deaths;
    TextView lastUpdated;
    TextView mildPercentage;
    TextView seriousPercentage;
    TextView deathsPercentage;
    TextView recoveredPercentage;

    public static DashboardFragment newInstance(MainActivity activity) {
        DashboardFragment f = new DashboardFragment();
        activity.firebaseAnalytics.setCurrentScreen(activity, "DashboardFragment", "DashboardFragment");

        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);

        initViews(view);
        setListeners();
        updateDashboardStats();
        return view;
    }

    public void updateDashboardStats(){
        if(getContext() == null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateDashboard();
                }
            }, 5000);

        }
        else{
            updateDashboard();
        }

    }

    void initViews(View view){
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        coronaCase = view.findViewById(R.id.corona_cases);
        totalRecovered = view.findViewById(R.id.recoveredCount);
        totalDeaths = view.findViewById(R.id.deathCount);
        currentlyInfected = view.findViewById(R.id.currently_infected);
        mild = view.findViewById(R.id.mildCount);
        serious = view.findViewById(R.id.seriousCount);
        outcomeCase = view.findViewById(R.id.outcome_cases);
        discharged = view.findViewById(R.id.recoveredClosedCount);
        deaths = view.findViewById(R.id.deathClosedCount);
        lastUpdated = view.findViewById(R.id.last_updated);
        mildPercentage = view.findViewById(R.id.mildPercentage);
        seriousPercentage = view.findViewById(R.id.seriousPercentage);
        deathsPercentage = view.findViewById(R.id.deathPercentage);
        recoveredPercentage = view.findViewById(R.id.recoveredPercentage);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        updateDashboard();
    }

    private void updateDashboard(){
        CovidInterface service = ApiClient.getRetrofitInstance().create(CovidInterface.class);
        Call<Dashboard> call = service.getDashbaord();

        call.enqueue(new Callback<Dashboard>() {
            @Override
            public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                loadDashboard(response.body());
               // Log.e("Dashboard Response", response.body().toString());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Dashboard> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Error: "+ t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void loadDashboard(Dashboard dashboard){
        dashboard.setPercentages();
        lastUpdated.setText(dashboard.getLastUpdated());
        coronaCase.setText(dashboard.getCoronaCases());
        totalRecovered.setText(dashboard.getRecovered());
        totalDeaths.setText(dashboard.getDeaths());

        currentlyInfected.setText(dashboard.getCurrentlyInfected());
        mildPercentage.setText("("+dashboard.getMildPercentage()+"%)");
        seriousPercentage.setText("("+dashboard.getSeriousPercentage()+"%)");
        mild.setText(dashboard.getMild());
        serious.setText(dashboard.getCritical());
        outcomeCase.setText(dashboard.getOutCome());
        deathsPercentage.setText("("+dashboard.getDeathPercentage()+"%)");
        recoveredPercentage.setText("("+dashboard.getRecoveredPercentage()+"%)");

        discharged.setText(dashboard.getRecovered());
        deaths.setText(dashboard.getDeaths());
    }

}
