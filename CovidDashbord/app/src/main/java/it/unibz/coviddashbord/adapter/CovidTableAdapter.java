package it.unibz.coviddashbord.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.app.feng.fixtablelayout.inter.IDataAdapter;

import java.util.List;

import it.unibz.coviddashbord.R;
import it.unibz.coviddashbord.model.CountryStats;

public class CovidTableAdapter implements IDataAdapter {

    public String[] titles;

    String regex = "\\d+";
    public List<CountryStats> data;

    public CovidTableAdapter(String[] titles,List<CountryStats> data) {
        this.titles = titles;
        this.data = data;
    }

    public void setData(List<CountryStats> data) {
        this.data = data;
    }

    @Override
    public String getTitleAt(int pos) {
        return titles[pos];
    }

    @Override
    public int getTitleCount() {
        return titles.length;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void convertData(int position,List<TextView> bindViews) {
        CountryStats countryStats = data.get(position);

        if (countryStats.getCountry().length() >= 11) {
            bindViews.get(0)
                    .setText(countryStats.getCountry().substring(0,11)+ "...");

        } else {
            bindViews.get(0)
                    .setText(countryStats.getCountry());
        }

        bindViews.get(0).setTypeface(bindViews.get(0).getTypeface(), Typeface.BOLD);
        bindViews.get(1)
                .setText(countryStats.getTotalCases());
        bindViews.get(1).setTypeface(bindViews.get(1).getTypeface(), Typeface.BOLD);
        bindViews.get(2)
                .setText(countryStats.getNewCases());
        if(countryStats.getNewCases().contains("+")){
            Log.e(position+ ". New Cases in "+ countryStats.getCountry(), countryStats.getNewCases());
            bindViews.get(2).setBackgroundResource(R.color.golden);
            bindViews.get(2).setTypeface(bindViews.get(2).getTypeface(), Typeface.BOLD);
        }
        else {
            if(position % 2 == 0){
                bindViews.get(2).setBackgroundResource(R.color.pale_green);
            }
            else{
                bindViews.get(2).setBackgroundResource(android.R.color.white);
            }
        }
        bindViews.get(3)
                .setText(countryStats.getTotalDeaths());
        bindViews.get(3).setTypeface(bindViews.get(3).getTypeface(), Typeface.BOLD);

        bindViews.get(4)
                .setText(countryStats.getNewDeaths());
        if(countryStats.getNewDeaths().contains("+")){
            Log.e(position+ ". New Deaths in "+ countryStats.getCountry(), countryStats.getNewDeaths());
            bindViews.get(4).setBackgroundResource(R.color.red);
            bindViews.get(4).setTextColor(Color.WHITE);
            bindViews.get(4).setTypeface(bindViews.get(4).getTypeface(), Typeface.BOLD);
        }
        else {
            if(position % 2 == 0){
                bindViews.get(4).setBackgroundResource(R.color.pale_green);
            }
            else{
                bindViews.get(4).setBackgroundResource(android.R.color.white);
            }
        }
        bindViews.get(5)
                .setText(countryStats.getTotalRecovered());
        bindViews.get(5).setTypeface(bindViews.get(5).getTypeface(), Typeface.BOLD);

        bindViews.get(6)
                .setText(countryStats.getSeriousCritical());
        bindViews.get(7)
                .setText(countryStats.getTotalCasesPerMillion());
        bindViews.get(8)
                .setText(countryStats.getDeathsPerMillion());
        bindViews.get(9)
                .setText(countryStats.getFirstCase());
        bindViews.get(6).setTypeface(bindViews.get(6).getTypeface(), Typeface.BOLD);
        bindViews.get(7).setTypeface(bindViews.get(7).getTypeface(), Typeface.BOLD);
        bindViews.get(8).setTypeface(bindViews.get(8).getTypeface(), Typeface.BOLD);
        bindViews.get(9).setTypeface(bindViews.get(9).getTypeface(), Typeface.BOLD);


         bindViews.get(1).setGravity(Gravity.RIGHT);
        bindViews.get(2).setGravity(Gravity.RIGHT);
        bindViews.get(3).setGravity(Gravity.RIGHT);
        bindViews.get(4).setGravity(Gravity.RIGHT);
        bindViews.get(5).setGravity(Gravity.RIGHT);
        bindViews.get(6).setGravity(Gravity.RIGHT);
        bindViews.get(7).setGravity(Gravity.RIGHT);
        bindViews.get(8).setGravity(Gravity.RIGHT);
        bindViews.get(9).setGravity(Gravity.RIGHT);
    }

    @Override
    public void convertLeftData(int position,TextView bindView) {
        bindView.setText(data.get(position).getCountry());
        bindView.setGravity(Gravity.LEFT);
        bindView.setTypeface(bindView.getTypeface(), Typeface.BOLD);
    }
}