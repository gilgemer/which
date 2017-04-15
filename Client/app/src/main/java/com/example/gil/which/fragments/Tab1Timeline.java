package com.example.gil.which.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gil.which.R;

/**
 * Created by gil on 14/04/17.
 */

public class Tab1Timeline extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1timeline, container, false);
        return rootView;
    }
}

