package it.unibz.coviddashbord.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.feng.fixtablelayout.FixTableLayout;

import java.util.List;

import it.unibz.coviddashbord.MainActivity;
import it.unibz.coviddashbord.R;
import it.unibz.coviddashbord.adapter.CovidTableAdapter;
import it.unibz.coviddashbord.model.CountryStats;
import it.unibz.coviddashbord.service.ApiClient;
import it.unibz.coviddashbord.service.CovidInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidMeterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    CovidTableAdapter fixTableAdapter;
    int currentPage = 1;
    int totalPage = 5;
    FixTableLayout fixTableLayout;

    public static CovidMeterFragment newInstance(MainActivity activity) {
        CovidMeterFragment f = new CovidMeterFragment();

        activity.firebaseAnalytics.setCurrentScreen(activity, "CovidMeterFragment", "CovidMeterFragment");

        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);

        fixTableLayout = (FixTableLayout) view.findViewById(R.id.covidTableLayout);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        setListeners();
        updateCovidData();
        return view;
    }

    public void updateCovidStats(){
        if(getContext() == null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateCovidData();
                }
            }, 5000);

        }else{
            updateCovidData();
        }

    }
    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        updateCovidData();
    }

    private void updateCovidData(){

        CovidInterface service = ApiClient.getRetrofitInstance().create(CovidInterface.class);
        Call<List<CountryStats>> call = service.getCountryStats();

        call.enqueue(new Callback<List<CountryStats>>() {
            @Override
            public void onResponse(Call<List<CountryStats>> call, Response<List<CountryStats>> response) {

                loadCovidData(response.body());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<CountryStats>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Error: "+ t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void loadCovidData(List<CountryStats> countryStatsList){
        String []title = {getString(R.string.country),getString(R.string.total_cases),
                getString(R.string.new_cases),getString(R.string.total_deaths),
                getString(R.string.new_deaths),getString(R.string.total_recovered),
                getString(R.string.serious_critical),getString(R.string.total_cases_million),
                getString(R.string.total_deaths_million),getString(R.string.first_case)};

        if(countryStatsList.size() > 0) {
            countryStatsList.remove(0);
            countryStatsList.add(0, countryStatsList.get(countryStatsList.size() - 1));
            countryStatsList.get(0).setCountry("World");
        }
        fixTableAdapter = new CovidTableAdapter(title,countryStatsList);
        fixTableLayout.setAdapter(fixTableAdapter);

       // fixTableLayout.getChildAt(0).setBackgroundResource(R.color.grey_header);

    }
}
