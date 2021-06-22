package fr.kosdev.go4lunch.controller.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.NearbyViewModel;
import fr.kosdev.go4lunch.controller.activities.RestaurantDetailActivity;
import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo.Result;


public class MapFragment extends Fragment  {



    private GoogleMap mMap;
    private FusedLocationProviderClient mLocationProviderClient;
    private SupportMapFragment mapFragment;
    private NearbyViewModel nearbyViewModel;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.bind(this, view);

        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

           getCurrentLocation();


        } else {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        this.configureViewModel();


        return view;

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        Task<Location> task = mLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


                                nearbyViewModel.getNearbyRepository().observe(getViewLifecycleOwner(), example -> {

                                    try {

                                        mMap.clear();

                                        for (int i = 0; i < example.getResults().size(); i++) {

                                            Double lat = example.getResults().get(i).getGeometry().getLocation().getLat();
                                            Double lng = example.getResults().get(i).getGeometry().getLocation().getLng();
                                            String placeName = example.getResults().get(i).getName();
                                            String vicinity = example.getResults().get(i).getVicinity();
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            LatLng newLatLng = new LatLng(lat, lng);
                                            markerOptions.position(newLatLng);
                                            markerOptions.title(placeName + " : " + vicinity);
                                            mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));
                                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                @Override
                                                public void onInfoWindowClick(@NonNull Marker marker) {
                                                    Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                           // mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                //@Override
                                                //public boolean onMarkerClick(@NonNull Marker marker) {

                                                   // if (marker.getTitle().equals(("placeName"))){
                                                        //startRestaurantDetailActivity();
                                                    }

                                                   // return false;
                                               // }
                                           // });

                                        //}
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    });


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

        nearbyViewModel = new ViewModelProvider(this).get(NearbyViewModel.class);
        nearbyViewModel.init();

    }

    private void startRestaurantDetailActivity(){

        Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
        startActivity(intent);

    }


}
