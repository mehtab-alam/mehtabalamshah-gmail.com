package it.unibz.coviddashbord.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import it.unibz.coviddashbord.R;

public class MapFragment extends Fragment {

    public static MapFragment newInstance() {
        MapFragment f = new MapFragment();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_main, container, false);
    }
}
