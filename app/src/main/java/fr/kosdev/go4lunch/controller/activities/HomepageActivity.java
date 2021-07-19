package fr.kosdev.go4lunch.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.controller.NearbyViewModel;
import fr.kosdev.go4lunch.controller.fragments.ListViewFragment;
import fr.kosdev.go4lunch.controller.fragments.MapFragment;
import fr.kosdev.go4lunch.controller.fragments.SettingFragment;
import fr.kosdev.go4lunch.controller.fragments.WorkmatesFragment;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.autocomplete.Prediction;
import fr.kosdev.go4lunch.notification.NotificationService;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.homepage_activity_bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.homepage_activity_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.homepage_activity_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.homepage_nav_drawer_view)
    NavigationView mNavigationView;

    private Fragment settingFragment;
    private static final int SETTING_FRAGMENT = 1;
    private static final int LOGOUT_EVENT = 2;
    private NearbyViewModel autocompleteView;
    private MapFragment mapFragment;
    private Fragment selectedFragment;
    NotificationService mNotificationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        this.configureBottomNavigationView();
        this.configureToolbar();
        this.configureNavigationView();
        this.configureDrawerLayout();
        this.updateProfileUI();
        selectedFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.homepage_frame_layout,selectedFragment).commit();
        autocompleteView = new ViewModelProvider(this).get(NearbyViewModel.class);

    }

    private void configureBottomNavigationView(){

        mBottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.map_action:
                        selectedFragment = new MapFragment();
                        mToolbar.setTitle(R.string.map_toolbar_title);
                        break;

                    case R.id.listView_action:
                        selectedFragment = new ListViewFragment();
                        mToolbar.setTitle(R.string.map_toolbar_title);
                        break;

                    case R.id.workmates_action:
                        selectedFragment = new WorkmatesFragment();
                        mToolbar.setTitle(R.string.workmate_toolbar_title);
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
                this.startRestaurantDetailActivity();
                break;
            case R.id.settings_menu:
                this.showFragment(SETTING_FRAGMENT);
                break;
            case R.id.logout_menu:
                this.signOutWorkmateFromFirebase();
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

    private void showSettingFragment(){

        if (settingFragment == null) settingFragment = SettingFragment.newInstance();
        this.startTransactionFragment(settingFragment);
    }


    private void signOutWorkmateFromFirebase(){

        AuthUI.getInstance().signOut(this).addOnSuccessListener(this, this.updateUIAfterSignOut(LOGOUT_EVENT));
    }

    private OnSuccessListener<Void> updateUIAfterSignOut(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case LOGOUT_EVENT:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }


    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case SETTING_FRAGMENT:
                this.showSettingFragment();
                break;
            default:
                break;
        }

    }
    protected FirebaseUser getCurrentWorkmate(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private void updateProfileUI(){
        View header = mNavigationView.getHeaderView(0);
        AppCompatImageView profilePhoto = (AppCompatImageView) header.findViewById(R.id.profile_img);
        TextView profileName = (TextView) header.findViewById(R.id.profile_name);
        TextView profileEmail = (TextView) header.findViewById(R.id.profile_email);
        if (this.getCurrentWorkmate() != null){
            if (this.getCurrentWorkmate().getPhotoUrl() != null){
                Glide.with(profilePhoto.getContext())
                        .load(this.getCurrentWorkmate().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform()).into(profilePhoto);
            }
            String email = TextUtils.isEmpty(this.getCurrentWorkmate().getEmail())?
                    getString(R.string.info_no_email_found) : this.getCurrentWorkmate().getEmail();
            profileEmail.setText(email);

            String workmateName = TextUtils.isEmpty(this.getCurrentWorkmate().getDisplayName()) ?
                    getString(R.string.info_no_username_found) : this.getCurrentWorkmate().getDisplayName();
            profileName.setText(workmateName);

        }
    }

    private void startRestaurantDetailActivity(){
        WorkmateHelper.getWorkmate(this.getCurrentWorkmate().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Workmate currentWorkmate = documentSnapshot.toObject(Workmate.class);
                String placeId = currentWorkmate.getPlaceId();
                Intent intent = new Intent(getApplicationContext(), RestaurantDetailActivity.class);
                intent.putExtra("CURRENT_KEY", placeId );
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callAutocomplete(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void callAutocomplete(String input){
        autocompleteView.getAutocompleteResult(input,"46.6743,4.3634").observe(this, autocompleteResult -> {
             List<Prediction> autoResult = autocompleteResult.getPredictions();
             if (selectedFragment instanceof MapFragment){
                 mapFragment = (MapFragment) selectedFragment;
                 mapFragment.configureAutocomplete(autoResult);
             }else if (selectedFragment instanceof ListViewFragment){
                 ListViewFragment listViewFragment = (ListViewFragment) selectedFragment;
                 listViewFragment.updateWithPredictions(autoResult);

             }


        });
    }




}