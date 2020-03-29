package com.unibz.cockpit;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ProgressBar;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
import com.unibz.cockpit.adapter.ProjectAdapter;
import com.unibz.cockpit.model.Project;
import com.unibz.cockpit.util.PrefUtil;
import com.unibz.cockpit.widget.RecyclerViewEmptySupport;

public class ProjectsFragment extends Fragment implements
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    private static View view;
    RecyclerViewEmptySupport recyclerView;
    ProjectAdapter projectAdapter;
    private static ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    List<MenuItem> menuItems;
    //Global Declaration
    SNavigationDrawer sNavigationDrawer;
    ArrayList<Project> allProjects;
    public static Fragment fragment;
    String tag = "";
    public ProjectsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_projects, container,
                false);
//        view = inflater.inflate(R.layout.drawer_projects, container,
//                false);

        sNavigationDrawer = view.findViewById(R.id.navigationDrawer);
        sNavigationDrawer.setAppbarTitleTV("Projects");

        initViews();
        setListeners();
        getAllProjects();





        return view;
    }

    // Initialize the views
    private void initViews() {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = getActivity().findViewById(R.id.progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);



        } catch (Exception e) {
        }
//        view.findViewById(R.id.close_action).setVisibility(View.GONE);
    }

    // Set Listeners over buttons
    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        getAllProjects();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }

    }



    public void getAllProjects(){
        progressBar.setVisibility(View.VISIBLE);
        new EasyWebservice(Utils.BASE_URL+"projects")
                .method(Method.GET)
                .call(new Callback.A<ArrayList<Project>>() { //the mapping for root elements should be empty
                    @Override
                    public void onSuccess(ArrayList<Project> projects) {

                        //work with the array
                        projectAdapter = new ProjectAdapter(getActivity(), getActivity(),projects);
                        recyclerView.setAdapter(projectAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setEmptyView(view.findViewById(R.id.empty_view));
                        projectAdapter.notifyDataSetChanged();
                        setDrawer(projects);
                        allProjects = projects;
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(String error) {
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
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
                            bundle.putSerializable("allprojects", allProjects);
                            bundle.putSerializable("project", projects.get(i));
                            fragment.setArguments(bundle);
                            tag = Utils.Dashboard_Fragment;
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



}