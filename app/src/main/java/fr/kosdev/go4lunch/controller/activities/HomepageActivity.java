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
import fr.kosdev.go4lunch.controller.fragments.LogoutFragment;
import fr.kosdev.go4lunch.controller.fragments.LunchFragment;
import fr.kosdev.go4lunch.controller.fragments.MapFragment;
import fr.kosdev.go4lunch.controller.fragments.SettingFragment;
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

    private Fragment lunchFragment;
    private Fragment settingFragment;
    private Fragment logoutFragment;

    private static final int LUNCH_FRAGMENT = 0;
    private static final int SETTING_FRAGMENT = 1;
    private static final int LOGOUT_FRAGMENT = 2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        this.configureBottomNavigationView();
        this.configureToolbar();
        this.configureNavigationView();
        this.configureDrawerLayout();
        getSupportFragmentManager().beginTransaction().add(R.id.homepage_frame_layout, new MapFragment()).commit();

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

                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_frame_layout, selectedFragment).commit();

                return true;
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.lunch_menu:
                this.showFragment(LUNCH_FRAGMENT);
                break;
            case R.id.settings_menu:
                this.showFragment(SETTING_FRAGMENT);
                break;
            case R.id.logout_menu:
                this.showFragment(LOGOUT_FRAGMENT);
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

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){

            getSupportFragmentManager().beginTransaction().replace(R.id.homepage_frame_layout, fragment).commit();
        }
    }

    private void  showLunchFragment(){
        if (lunchFragment == null) lunchFragment = LunchFragment.newInstance();
        this.startTransactionFragment(lunchFragment);
    }

    private void showSettingFragment(){

        if (settingFragment == null) settingFragment = SettingFragment.newInstance();
        this.startTransactionFragment(settingFragment);
    }

    private void showLogoutFragment(){

        if (logoutFragment == null) logoutFragment = LogoutFragment.newInstance();
        this.startTransactionFragment(logoutFragment);
    }
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case LUNCH_FRAGMENT :
                this.showLunchFragment();
                break;
            case SETTING_FRAGMENT:
                this.showSettingFragment();
                break;
            case LOGOUT_FRAGMENT:
                this.showLogoutFragment();
                break;
            default:
                break;
        }

    }

}