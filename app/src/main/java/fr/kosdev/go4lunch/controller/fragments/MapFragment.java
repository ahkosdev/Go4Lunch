package fr.kosdev.go4lunch.controller.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.controller.NearbyViewModel;
import fr.kosdev.go4lunch.controller.activities.RestaurantDetailActivity;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.autocomplete.Prediction;
import fr.kosdev.go4lunch.utils.Utils;


public class MapFragment extends Fragment implements LocationListener {



    private GoogleMap mMap;
    private FusedLocationProviderClient mLocationProviderClient;
    private SupportMapFragment mapFragment;
    private NearbyViewModel nearbyViewModel;
    private List<Prediction> mPredictions;
    private Workmate mWorkmate;
    private List<Workmate> mWorkmateList;





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
                            //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title("My house"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mMap.setMyLocationEnabled(true);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                            String myLocation = location.getLatitude() + "," + location.getLongitude();
                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();


                                nearbyViewModel.getNearbyRepository(Utils.getMyLocation(latitude,longitude)).observe(getViewLifecycleOwner(), example -> {

                                    try {

                                        mMap.clear();

                                        for (int i = 0; i < example.getResults().size(); i++) {

                                            Double lat = example.getResults().get(i).getGeometry().getLocation().getLat();
                                            Double lng = example.getResults().get(i).getGeometry().getLocation().getLng();
                                            String placeName = example.getResults().get(i).getName();
                                            String vicinity = example.getResults().get(i).getVicinity();
                                            String placeId = example.getResults().get(i).getPlaceId();
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            LatLng newLatLng = new LatLng(lat, lng);
                                            markerOptions.position(newLatLng);
                                            markerOptions.title(placeName + " : " + vicinity);
                                            mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                                    .setTag(example.getResults().get(i).getPlaceId());
                                            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
                                            mMap.setMyLocationEnabled(true);
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));
                                            WorkmateHelper.getWorkmatesWithPlaceId(placeId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    if (queryDocumentSnapshots.size() > 0){
                                                        mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(placeId);
                                                    }else {
                                                        mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))).setTag(placeId);
                                                    }

                                                }
                                            });

                                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                @Override
                                                public void onInfoWindowClick(@NonNull Marker marker) {
                                                    String placeId = (String) marker.getTag();
                                                    Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                                                    intent.putExtra("KEY_DETAIL", placeId);
                                                    startActivity(intent);
                                                }
                                            });
                                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                @Override
                                                public boolean onMarkerClick(@NonNull Marker marker) {
                                                    String placeId = (String) marker.getTag();
                                                    Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                                                    intent.putExtra("KEY_DETAIL", placeId);
                                                    startActivity(intent);


                                                    return false;
                                               }
                                            });

                                        }
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

    public void configureAutocomplete(List<Prediction> mPredictions) {
        mMap.clear();
        for (int i = 0; i < mPredictions.size(); i++) {
            if (mPredictions.get(i).getTypes().contains("restaurant"))
            nearbyViewModel.getDetailLiveData(mPredictions.get(i).getPlaceId()).observe(this, exampleDetail -> {
                try {


                    Double lat = exampleDetail.getResults().getGeometry().getLocation().getLat();
                    Double lng = exampleDetail.getResults().getGeometry().getLocation().getLng();
                    String placeName = exampleDetail.getResults().getName();
                    String vicinity = exampleDetail.getResults().getVicinity();
                    MarkerOptions newMarkerOptions = new MarkerOptions();
                    LatLng autocompleteLatLng = new LatLng(lat, lng);
                    newMarkerOptions.position(autocompleteLatLng);
                    newMarkerOptions.title(placeName + " : " + vicinity);
                   mMap.addMarker(newMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                           .setTag(exampleDetail.getResults().getPlaceId());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        if (mMap != null){
            LatLng locationChangedLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(locationChangedLatLng).title("My position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationChangedLatLng));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationChangedLatLng, 15));

        }
    }
}
