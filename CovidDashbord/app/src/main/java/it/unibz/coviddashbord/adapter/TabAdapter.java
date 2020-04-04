package it.unibz.coviddashbord.adapter;


import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import it.unibz.coviddashbord.MainActivity;
import it.unibz.coviddashbord.R;
import it.unibz.coviddashbord.fragment.CovidMeterFragment;
import it.unibz.coviddashbord.fragment.DashboardFragment;
import it.unibz.coviddashbord.fragment.MapFragment;

public class TabAdapter extends FragmentStatePagerAdapter {
    MainActivity activity;
    int totalTabs;
    FragmentManager fm;
    FragmentTransaction ft;
    public TabAdapter(@NonNull FragmentManager fm, MainActivity activity, int behavior) {
        super(fm, behavior);
        this.activity = activity;

    }



    /*
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Toast.makeText(this.activity, "Adapter Called", Toast.LENGTH_LONG).show();

        switch (position) {
            case 0:
                CovidMeterFragment covidMeterFragment = CovidMeterFragment.newInstance();
                ft = fm.beginTransaction();
                ft.replace(R.id.frameContainer, covidMeterFragment);
                ft.commit();
                return covidMeterFragment;
            case 1:
                DashboardFragment dashboardFragment =DashboardFragment.newInstance();
                ft = fm.beginTransaction();
                ft.replace(R.id.frameContainer, dashboardFragment);
                ft.commit();
                return dashboardFragment;
            case 2:
                MapFragment mapFragment =MapFragment.newInstance();
                ft = fm.beginTransaction();
                ft.replace(R.id.frameContainer, mapFragment);
                ft.commit();
                return mapFragment;
            default:
                return null;
        }
    }

     */



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                CovidMeterFragment covidMeterFragment = CovidMeterFragment.newInstance(activity);

                return covidMeterFragment;
            case 0:
                DashboardFragment dashboardFragment =DashboardFragment.newInstance(activity);

                return dashboardFragment;
            case 2:
                MapFragment mapFragment =MapFragment.newInstance();

                return mapFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}