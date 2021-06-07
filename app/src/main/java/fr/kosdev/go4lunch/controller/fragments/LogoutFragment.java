package fr.kosdev.go4lunch.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.kosdev.go4lunch.R;


public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }

    public static LogoutFragment newInstance() {

        return (new LogoutFragment());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}