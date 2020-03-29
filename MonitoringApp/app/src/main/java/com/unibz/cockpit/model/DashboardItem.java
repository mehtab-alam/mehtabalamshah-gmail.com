package com.unibz.cockpit.model;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class DashboardItem {
    public XYMultipleSeriesDataset xyMultipleSeriesDataset;
    public XYMultipleSeriesRenderer xyMultipleSeriesRenderer;
    DefaultRenderer mRenderer;
    String chartType;
    String chartTitle;
    Integer projectProgress;
    Dashboard dashboard;
    private CategorySeries mSeries;
    private static int[] COLORS = new int[] { Color.MAGENTA,  Color.RED, Color.YELLOW, };


    public DashboardItem(String chartType, Dashboard dashboard) {
        this.chartType = chartType;
        this.chartTitle = chartType;
        this.projectProgress = dashboard.getProgress();
        this.dashboard = dashboard;
        if(dashboard.getActivityReport() != null)
            activityProgressChartSettings(dashboard.getActivityReport());


    }

    public Integer getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(Integer projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }




    public DefaultRenderer getPieRenderer(){
        mRenderer = new DefaultRenderer();
        mRenderer.setLabelsTextSize(35);
        mRenderer.setLegendTextSize(35);
        mRenderer.setMargins(new int[] { 40, 30, 15, 20 });
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(10);

        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setLabelsColor(Color.BLACK);
        return mRenderer;
    }

    public CategorySeries getPieChartSeries(){
        mSeries = new CategorySeries("");
        Random rnd = new Random();
        HashMap<String, Double> map = dashboard.getLocationReport();


        Iterator it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            mSeries.add( pair.getKey().toString(), (Double)pair.getValue());
            Log.e(pair.getKey().toString(), pair.getValue().toString());
//            it.remove();
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            mRenderer.addSeriesRenderer(renderer);

        }
        return mSeries;
    }



    public void activityProgressChartSettings(ArrayList<ActivityReport> activityReports){
        int[] x = new int[activityReports.size()];
        XYSeries progressSeries = new XYSeries("Progress");
        // Creating an  XYSeries for Expense
        XYSeries totalSeries = new XYSeries("Total");
        String[] acronyms = new String[activityReports.size()];
        for(int item = 0 ; item< activityReports.size();item ++){
            x[item] = 10 * item;
            progressSeries.add(item, activityReports.get(item).getProgress());
            totalSeries.add(item, activityReports.get(item).getUnits());
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(progressSeries);
        dataset.addSeries(totalSeries);
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.rgb(130, 130, 230));
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setChartValuesTextAlign(Paint.Align.CENTER);
        incomeRenderer.setChartValuesTextSize(18);
        incomeRenderer.setDisplayChartValues(true);


        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.rgb(220, 80, 80));
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setChartValuesTextAlign(Paint.Align.CENTER);
        expenseRenderer.setChartValuesTextSize(18);
        expenseRenderer.setDisplayChartValues(true);




        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setMargins(new int[]{30,50,60,0});
        multiRenderer.setXRoundedLabels(true);
        multiRenderer.setLegendTextSize(24);
        multiRenderer.setZoomRate(0.2f);
        multiRenderer.setZoomEnabled(false, false);
        multiRenderer.setBarSpacing(0.3f);
        multiRenderer.setXAxisMin(-1);
        multiRenderer.setXAxisMax(5);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(300);
        multiRenderer.setAxisTitleTextSize(20);
        multiRenderer.setAxesColor(Color.BLACK);
        multiRenderer.setGridColor(Color.GRAY);
        multiRenderer.setShowGridX(true);
        multiRenderer.setXLabels(0);

        multiRenderer.setPanLimits(new double[] { -1, x.length, 0, x.length});

        multiRenderer.setLabelsColor(Color.BLACK);
        multiRenderer.setLabelsTextSize(20);

        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setXTitle("Activities");
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setYTitle("Units");
        multiRenderer.setMarginsColor(Color.WHITE);
        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, activityReports.get(i).getActivity_acronym());
        }

        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);


        xyMultipleSeriesDataset = dataset;
        xyMultipleSeriesRenderer = multiRenderer;

    }

}
