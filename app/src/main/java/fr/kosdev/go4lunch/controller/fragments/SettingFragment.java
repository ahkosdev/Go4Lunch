package fr.kosdev.go4lunch.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

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
        //final SharedPreferences sharedPref = getActivity().getSharedPreferences("disable", MODE_PRIVATE);
        //final SwitchPreferenceCompat preferenceCompat = (SwitchPreferenceCompat) findPreference(this.getResources().getString(R.string.disable_key));
        //preferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            //@Override
            //public boolean onPreferenceChange(Preference preference, Object newValue) {
                //Boolean disable = (Boolean) newValue;
                //if (disable){

                    //preferenceCompat.setChecked(false);

                //}else {

                    //preferenceCompat.setChecked(true);

                //}
               //SharedPreferences.Editor editor = sharedPref.edit();
                //editor.putBoolean(getString(R.string.disable_key), disable);
                //editor.apply();
                //return true;
            //}
        //});


    }

    public static SettingFragment newInstance() {

        return (new SettingFragment());
    }



}