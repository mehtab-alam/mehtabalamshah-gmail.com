package com.unibz.cockpit.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unibz.cockpit.DashboardFragment;
import com.unibz.cockpit.R;
import com.unibz.cockpit.SignUpFragment;
import com.unibz.cockpit.Utils;
import com.unibz.cockpit.model.Project;
import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {
    private Context mContext;

    ArrayList<Project> projects;
    FragmentManager fragmentManager;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        RelativeLayout cardLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.cardLayout = (RelativeLayout) itemView.findViewById(R.id.card_main);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);

        }
    }

    public ProjectAdapter(FragmentActivity activity, Context mContext, ArrayList<Project> projects) {
        this.fragmentManager = activity.getSupportFragmentManager();
        this.mContext = mContext;
        this.projects = projects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {

        if( i % 3 == 1){
            holder.cardLayout.setBackgroundResource(R.color.golden);
        }
        if( i % 3 == 2){
            holder.cardLayout.setBackgroundResource(R.color.aqua);
        }
        if( i % 3 == 0){
            holder.cardLayout.setBackgroundResource(R.color.green);
        }
        holder.title.setText(projects.get(i).getProjectName());
        holder.description.setText(projects.get(i).getProjectDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardFragment fragment = new DashboardFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("project", projects.get(i));
                bundle.putSerializable("allprojects", projects);
                fragment.setArguments(bundle);
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, fragment,
                                Utils.Dashboard_Fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }


}