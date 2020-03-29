package com.unibz.cockpit;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
import com.unibz.cockpit.adapter.DashboardAdapter;
import com.unibz.cockpit.model.Dashboard;
import com.unibz.cockpit.model.DashboardItem;
import com.unibz.cockpit.model.Project;
import com.unibz.cockpit.util.PrefUtil;
import com.unibz.cockpit.widget.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;


public class DashboardFragment extends Fragment implements
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static View view;
    RecyclerViewEmptySupport recyclerView;
    TextView tvTitle;
    private static ProgressBar progressBar;
    ArrayList<DashboardItem> dashboardItems;
    Project project;
    Bundle bundle;
    Spinner spinnerFilter;

    List<MenuItem> menuItems;
    //Global Declaration
    SNavigationDrawer sNavigationDrawer;
    String tag = "";
    public static Fragment fragment;
    ArrayList<Project> projects;
    DashboardAdapter dashboardAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_dashboard, container,
                false);



        initViews();
        initObjects();
        initDashboardItems();

        sNavigationDrawer = view.findViewById(R.id.navigationDrawer);
        sNavigationDrawer.setAppbarTitleTV(project.getProjectName() + " "+ getString(R.string.dashboard_title));
        setDrawer(projects);

        setListeners();

        return view;
    }

    // Set Listeners over buttons


    public void initObjects(){
        bundle = getArguments();
        project = (Project) bundle.getSerializable("project");
        projects = (ArrayList<Project>) bundle.getSerializable("allprojects");
    }

    public void initDashboardItems(){
        dashboardItems = new ArrayList<DashboardItem>();

        tvTitle=(TextView)view.findViewById(R.id.tv);
        tvTitle.setSelected(true);
        spinnerFilter = view.findViewById(R.id.spinner_filter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        String text = tvTitle.getText().toString();
        text = String.format(text, project.getProjectName());
        tvTitle.setText(text);
        tvTitle.setSelected(true);

        updateDashboard(project.getProjectId());

    }

    // Initialize the views
    private void initViews() {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = getActivity().findViewById(R.id.progressBar);




        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);



        } catch (Exception e) {
        }




    }

    @Override
    public void onRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        updateDashboard(project.getProjectId());
    }

    public void updateDashboard(int projectId){
        progressBar.setVisibility(View.VISIBLE);

        new EasyWebservice(Utils.BASE_URL+"dashboard?project="+projectId)
                .method(Method.GET)
                .call(new Callback.A<Dashboard>("dashboard") { //the mapping for root elements should be empty
                    @Override
                    public void onSuccess(Dashboard dashboard) {
                        progressBar.setVisibility(View.GONE);
                        dashboardItems.clear();
                        dashboardItems.add(new DashboardItem(getString(R.string.progress),dashboard));
                        dashboardItems.add(new DashboardItem(getString(R.string.activity_progress),dashboard));
                        dashboardItems.add(new DashboardItem(getString(R.string.location_progress),dashboard));

                        updateAdapter(dashboardItems);
                        swipeRefreshLayout.setRefreshing(false);
                        if(spinnerFilter.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.liked_item))){
                            spinnerClicked();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("ERROR",error);

                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }



    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerClicked();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        view.findViewById(R.id.empty_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDashboard(project.getProjectId());
            }
        });
    }

    public void removeAt(int position) {

        dashboardItems.remove(position);
        dashboardAdapter.notifyItemRemoved(position);
        dashboardAdapter.notifyItemRangeChanged(position, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }

    }

    public void spinnerClicked(){
        if(spinnerFilter.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.liked_item))){
            ArrayList<DashboardItem> likeItems = new ArrayList<DashboardItem>();
            for(DashboardItem dashboardItem : dashboardItems){
                if(PrefUtil.getBooleanPreference(getActivity(), dashboardItem.getChartTitle())) {
                    likeItems.add(dashboardItem);
                }
            }
            dashboardItems.clear();
            for(DashboardItem dashboardItem : likeItems)
                dashboardItems.add(dashboardItem);
            updateAdapter(dashboardItems);

        }
        else{
            updateDashboard(project.getProjectId());
        }
    }

    public void setDrawer(ArrayList<Project> projects){

        menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.
        MenuItem menuItem1 = new MenuItem(getString(R.string.projects_title), R.color.white);
        menuItems.add(menuItem1);
        for(Project project : projects) {
            MenuItem menuItem2 = new MenuItem(project.getProjectName() +" "+ getString(R.string.dashboard_title), R.color.white);
            menuItems.add(menuItem2);
        }
        MenuItem menuItem3 = new MenuItem(getString(R.string.logout), R.color.white);
        menuItems.add(menuItem3);

        sNavigationDrawer.setMenuItemList(menuItems);
        drawerListerner(projects);
    }


    public void drawerListerner(ArrayList<Project> projects){
        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position " + position);
                final int lastItem = menuItems.size() - 1;
                if(position == 0){
                    fragment = new ProjectsFragment();
                    tag = Utils.Project_Fragment;
                }
                else if(position == lastItem){
                    getActivity().findViewById(R.id.close_activity).setVisibility(View.VISIBLE);
                    PrefUtil.clearPreferences(getContext());
                    fragment = new LoginFragment();
                    tag = Utils.Login_Fragment;
                }
                else{
                    for(int i = 0 ; i < projects.size(); i++){
                        if(position == (i+1)) {
                            fragment = new DashboardFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("project", projects.get(i));
                            bundle.putSerializable("allprojects", projects);
                            tag = Utils.Dashboard_Fragment;
                            fragment.setArguments(bundle);
                        }
                    }
                }
                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameContainer, fragment, tag).commit();

                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });
    }



    public void updateAdapter(ArrayList<DashboardItem> items){
        dashboardAdapter = new DashboardAdapter(getActivity(), getActivity(), this, dashboardItems);
        recyclerView.setAdapter(dashboardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(view.findViewById(R.id.empty_view));
        dashboardAdapter.notifyDataSetChanged();
    }
}