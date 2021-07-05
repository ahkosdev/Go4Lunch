package fr.kosdev.go4lunch.controller.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.ListViewViewModel;
import fr.kosdev.go4lunch.model.Restaurant;
import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo.Photo;
import fr.kosdev.go4lunch.model.pojo.Result;

public class ListViewFragment extends Fragment {


    @BindView(R.id.list_view_rcv)
    RecyclerView listViewRecyclerView;


    GoogleMap mMap;
    private FusedLocationProviderClient mLocationProviderClient;
    private ListViewViewModel listViewModel;
    List<Restaurant> restaurants;
    List<Result> results;
    ListViewAdapter listAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);
        ButterKnife.bind(this, view);

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            getCurrentLocation();


        } else {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        this.configureViewModel();
        this.configureRecyclerView();
        return view;
    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation(){

        Task<Location> task = mLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null){

                    listViewModel.getNearbyRepository().observe(getViewLifecycleOwner(),example -> {

                        try {

                            results.clear();
                            results.addAll(example.getResults());
                           // listAdapter = new ListViewAdapter(results);
                            listAdapter.notifyDataSetChanged();


                            }catch (Exception e) {
                            e.printStackTrace();
                        }


                    });
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 44){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getCurrentLocation();

            }
        }

    }

    private void configureViewModel(){

        listViewModel = new ViewModelProvider(this).get(ListViewViewModel.class);
        listViewModel.init();

    }

    private void configureRecyclerView(){
        results = new ArrayList<>();
        listAdapter = new ListViewAdapter(results);
        listViewRecyclerView.setAdapter(listAdapter);
        listViewRecyclerView.setHasFixedSize(true);
    }
}
