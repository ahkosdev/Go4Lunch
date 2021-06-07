package fr.kosdev.go4lunch.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.kosdev.go4lunch.R;


public class LunchFragment extends Fragment {


    public LunchFragment() {
        // Required empty public constructor
    }


    public static LunchFragment newInstance() {

        return (new LunchFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lunch, container, false);
    }
}