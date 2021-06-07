package fr.kosdev.go4lunch.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.kosdev.go4lunch.R;


public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {

        return (new SettingFragment());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}