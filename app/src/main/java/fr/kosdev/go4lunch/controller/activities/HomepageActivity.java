package fr.kosdev.go4lunch.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.fragments.ListViewFragment;
import fr.kosdev.go4lunch.controller.fragments.MapFragment;
import fr.kosdev.go4lunch.controller.fragments.WorkmatesFragment;

public class HomepageActivity extends AppCompatActivity  {

    @BindView(R.id.homepage_activity_bottom_navigation)
    BottomNavigationView mBottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        this.configureBottomNavigationView();
        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, new MapFragment()).commit();

       // FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private void configureBottomNavigationView(){

        mBottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.map_action:
                        selectedFragment = new MapFragment();
                        break;

                    case R.id.listView_action:
                        selectedFragment = new ListViewFragment();
                        break;

                    case R.id.workmates_action:
                        selectedFragment = new WorkmatesFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_container, selectedFragment).commit();

                return true;
            }
        });
    }




}