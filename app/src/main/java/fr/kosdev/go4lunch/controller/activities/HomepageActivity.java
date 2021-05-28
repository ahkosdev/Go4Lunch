package fr.kosdev.go4lunch.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.fragments.ListViewFragment;
import fr.kosdev.go4lunch.controller.fragments.MapFragment;
import fr.kosdev.go4lunch.controller.fragments.WorkmatesFragment;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.homepage_activity_bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.homepage_activity_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.homepage_activity_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.homepage_nav_drawer_view)
    NavigationView mNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        this.configureBottomNavigationView();
        this.configureToolbar();
        this.configureNavigationView();
        this.configureDrawerLayout();
        //getSupportFragmentManager().beginTransaction().add(R.id.homepage_container, new MapFragment()).commit();

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.lunch_menu:
                break;
            case R.id.settings_menu:
                break;

            case R.id.logout_menu:
                break;

            default:
                break;

        }
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
    }

    private void configureNavigationView(){
        mNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}