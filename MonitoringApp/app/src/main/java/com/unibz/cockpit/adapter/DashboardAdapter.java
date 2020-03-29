package com.unibz.cockpit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.infideap.stylishwidget.view.AMeter;
import com.unibz.cockpit.DashboardFragment;
import com.unibz.cockpit.R;
import com.unibz.cockpit.Utils;
import com.unibz.cockpit.model.DashboardItem;
import com.unibz.cockpit.util.PrefUtil;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.renderer.DefaultRenderer;
import java.util.ArrayList;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder>{
    ArrayList<DashboardItem> dashboardItems;
    FragmentManager fragmentManager;
    Context mContext;
    DashboardFragment dashboardFragment;
    FragmentActivity activity;
    public DashboardAdapter(FragmentActivity activity, Context mContext, DashboardFragment fragment, ArrayList<DashboardItem> dashboardItems) {
        this.fragmentManager = activity.getSupportFragmentManager();
        this.mContext = mContext;
        this.dashboardFragment = fragment;
        this.activity = activity;
        this.dashboardItems = dashboardItems;
    }

    @NonNull
    @Override
    public DashboardAdapter.DashboardHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card_dashboard, viewGroup, false);

        DashboardAdapter.DashboardHolder dashboardHolder = new DashboardAdapter.DashboardHolder(view);
        return dashboardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.DashboardHolder dashboardHolder, int i) {
        try {
            dashboardHolder.titleTextView.setText(dashboardItems.get(i).getChartTitle());
            if (dashboardItems.get(i).getChartTitle().equalsIgnoreCase(activity.getString(R.string.activity_progress))) {
                dashboardHolder.mChartView = ChartFactory.getBarChartView(mContext, dashboardItems.get(i).xyMultipleSeriesDataset, dashboardItems.get(i).xyMultipleSeriesRenderer, BarChart.Type.DEFAULT);
                dashboardHolder.chartView.addView(dashboardHolder.mChartView);

            } else if (dashboardItems.get(i).getChartTitle().equalsIgnoreCase(activity.getString(R.string.progress))) {
                dashboardHolder.lineMeter.setVisibility(View.VISIBLE);
                progress(dashboardHolder.lineMeter, 0, dashboardItems.get(i).getProjectProgress());

            } else {
                DefaultRenderer mRenderer = dashboardItems.get(i).getPieRenderer();
                dashboardHolder.mChartView = ChartFactory.getPieChartView(mContext, dashboardItems.get(i).getPieChartSeries(), mRenderer);
                dashboardHolder.chartView.addView(dashboardHolder.mChartView);

            }
        }catch(Exception exp){

        }

        if(i % 3 == 0){
            dashboardHolder.headerLayout.setBackgroundResource(R.color.colorYellow_600);
        }
        else if(i % 3 == 1){
            dashboardHolder.headerLayout.setBackgroundResource(R.color.colorSuccess);
        }
        else{
            dashboardHolder.headerLayout.setBackgroundResource(R.color.colorBlueGrey_500);
        }

        if(PrefUtil.isKeyExist(mContext,dashboardItems.get(i).getChartTitle())){
            dashboardHolder.likeImageView.setImageResource(R.drawable.ic_liked);
        }
        dashboardHolder.likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( dashboardHolder.likeImageView.getDrawable().getConstantState() == activity.getResources().getDrawable( R.drawable.ic_like).getConstantState()){
                    PrefUtil.savePreference(mContext, dashboardHolder.titleTextView.getText().toString(), true);
                    dashboardHolder.likeImageView.setImageResource(R.drawable.ic_liked);
                    dashboardFragment.spinnerClicked();
                }
                else{
                    PrefUtil.removePreference(mContext, dashboardHolder.titleTextView.getText().toString());
                    dashboardHolder.likeImageView.setImageResource(R.drawable.ic_like);
                    dashboardFragment.spinnerClicked();
                }
            }
        });

        dashboardHolder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.sendEmail(activity,  dashboardItems.get(i).getChartTitle(), dashboardHolder.shareLayout);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashboardItems.size();
    }

    public static class DashboardHolder extends RecyclerView.ViewHolder {

        LinearLayout chartView;
        TextView titleTextView;
        ImageView likeImageView;
        ImageView shareImageView;
        LinearLayout headerLayout;
        LinearLayout shareLayout;
        AMeter lineMeter;
        private GraphicalView mChartView;

        public DashboardHolder(View itemView) {
            super(itemView);
            this.shareLayout = itemView.findViewById(R.id.main_layout);
            this.chartView = itemView.findViewById(R.id.chartView);
            this.lineMeter = itemView.findViewById(R.id.linemeter);
            this.titleTextView = itemView.findViewById(R.id.titleTextView);
            this.likeImageView = itemView.findViewById(R.id.likeImageView);
            this.shareImageView = itemView.findViewById(R.id.shareImageView);
            this.headerLayout = itemView.findViewById(R.id.header);

            this.lineMeter.setMaxValue(100);
            this.lineMeter.setStartValue(0);
        }
    }

    private void progress(final AMeter meter, final int i, int percentage) {
        if (i <= 100 && i<=percentage)
            meter.postDelayed(new Runnable() {
                @Override
                public void run() {
                    meter.setValue(i);
                    progress(meter, i + 1, percentage);
                }
            }, 15);
    }

}
