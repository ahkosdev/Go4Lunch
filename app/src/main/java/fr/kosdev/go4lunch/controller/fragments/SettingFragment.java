package fr.kosdev.go4lunch.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.kosdev.go4lunch.R;

import static android.content.Context.MODE_PRIVATE;


public class SettingFragment extends PreferenceFragmentCompat {

    private int newSetting;



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("_", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.setting_notification), true);
        editor.apply();

    }

    public static SettingFragment newInstance() {

        return (new SettingFragment());
    }



}