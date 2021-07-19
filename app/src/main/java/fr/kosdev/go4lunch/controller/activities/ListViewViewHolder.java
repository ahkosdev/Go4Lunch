package fr.kosdev.go4lunch.controller.activities;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.controller.NearbyViewModel;
import fr.kosdev.go4lunch.model.Restaurant;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.pojo.OpeningHours;
import fr.kosdev.go4lunch.model.pojo.Result;
import fr.kosdev.go4lunch.model.pojo_detail.Results;

import static com.google.api.AnnotationsProto.http;

public class ListViewViewHolder extends ViewHolder {

    @BindView(R.id.restaurant_name_txt)
    TextView restaurantName;
    @BindView(R.id.restaurant_vicinity_txt)
    TextView restaurantVicinity;
    @BindView(R.id.restaurant_open_hour_txt)
    TextView restaurantOpenHours;
    @BindView(R.id.restaurant_photo_img)
    ImageView restaurantImage;
    @BindView(R.id.distance_txt)TextView distanceTextView;
    @BindView(R.id.restaurant_rating)
    RatingBar restaurantRating;
    @BindView(R.id.workmates_count_txt)
    TextView workmateCount;

    Location destinationLoc;
    FusedLocationProviderClient mLocationProviderClient;
    Location lastLocation;
    Location location;




    public ListViewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @SuppressLint("MissingPermission")
    public void updateRestaurantList(Result result){


        restaurantName.setText(result.getName());
        restaurantVicinity.setText(result.getVicinity());
        OpeningHours openingHours = result.getOpeningHours();
        if (openingHours != null){
            if (result.getOpeningHours().getOpenNow()== true){
                restaurantOpenHours.setText(R.string.open_text);
            }else {
                restaurantOpenHours.setText(R.string.close_text);
            }
        }


            String restaurantPhoto = result.getPhotos().get(0).getPhotoReference();

            if (restaurantPhoto != null){

                Glide.with(restaurantImage.getContext())
                        .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference="+ restaurantPhoto +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                        .into(restaurantImage);

            }

            double rating = result.getRating();

            restaurantRating.setRating((float) rating);
              if (rating >1 && rating <= 2){
                  restaurantRating.setNumStars(1);
               }else if (rating > 2 && rating <= 3){
                  restaurantRating.setNumStars(2);
              }else if (rating > 4){
                  restaurantRating.setNumStars(3);
              }


            double lng = result.getGeometry().getLocation().getLng();
            double lat = result.getGeometry().getLocation().getLat();
            destinationLoc = new Location("custom_provider");
            destinationLoc.setLatitude(lat);
            destinationLoc.setLongitude(lng);
            mLocationProviderClient = LocationServices.getFusedLocationProviderClient(distanceTextView.getContext());
            Task<Location> task = mLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    double distance = location.distanceTo(destinationLoc);
                    int distanceInt = (int)distance;
                    distanceTextView.setText(distanceInt + "m");
                }

            }
        });

           String placeId = result.getPlaceId();
           workmateCount.setText("0");
            WorkmateHelper.getWorkmatesWithPlaceId(placeId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    workmateCount.setText(String.valueOf(queryDocumentSnapshots.getDocuments().size()));
                }
            });






    }
    @SuppressLint("MissingPermission")
    public void updateListWithDetailResult(Results results){

        restaurantName.setText(results.getName());
        restaurantVicinity.setText(results.getVicinity());
        //OpeningHours openingHours = results.getOpeningHours();
        //if (openingHours != null){
           // if (results.getOpeningHours().getOpenNow()== true){
               // restaurantOpenHours.setText(R.string.open_text);
           // }else {
                //restaurantOpenHours.setText(R.string.close_text);
            //}
        //}


        String restaurantPhoto = results.getPhotos().get(0).getPhotoReference();

        if (restaurantPhoto != null){

            Glide.with(restaurantImage.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference="+ restaurantPhoto +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                    .into(restaurantImage);

        }

        double rating = results.getRating();

        restaurantRating.setRating((float) rating);
        if (rating >1 && rating <= 2){
            restaurantRating.setNumStars(1);
        }else if (rating > 2 && rating <= 3){
            restaurantRating.setNumStars(2);
        }else if (rating > 4){
            restaurantRating.setNumStars(3);
        }


        double lng = results.getGeometry().getLocation().getLng();
        double lat = results.getGeometry().getLocation().getLat();
        destinationLoc = new Location("custom_provider");
        destinationLoc.setLatitude(lat);
        destinationLoc.setLongitude(lng);
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(distanceTextView.getContext());
        Task<Location> task = mLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    double distance = location.distanceTo(destinationLoc);
                    int distanceInt = (int)distance;
                    distanceTextView.setText(distanceInt + "m");
                }

            }
        });

        String placeId = results.getPlaceId();
        workmateCount.setText("0");
        WorkmateHelper.getWorkmatesWithPlaceId(placeId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                workmateCount.setText(String.valueOf(queryDocumentSnapshots.getDocuments().size()));
            }
        });




    }
}
