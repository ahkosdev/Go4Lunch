package fr.kosdev.go4lunch.controller.activities;

import android.annotation.SuppressLint;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.NearbyViewModel;
import fr.kosdev.go4lunch.model.Restaurant;
import fr.kosdev.go4lunch.model.pojo.OpeningHours;
import fr.kosdev.go4lunch.model.pojo.Result;

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

    private Location destinationLoc;
    private FusedLocationProviderClient mLocationProviderClient;
    private Location lastLocation;




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
                restaurantOpenHours.setText("ouvert");
            }else {
                restaurantOpenHours.setText("Fermé");
            }
        }


            String restaurantPhoto = result.getPhotos().get(0).getPhotoReference();

            if (restaurantPhoto != null){

                Glide.with(restaurantImage.getContext())
                        .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference="+ restaurantPhoto +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                        .into(restaurantImage);

            }


            double lng = result.getGeometry().getLocation().getLng();
            double lat = result.getGeometry().getLocation().getLat();
            destinationLoc.setLatitude(lat);
            destinationLoc.setLongitude(lng);

        Task<Location> task = mLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    double originLat = location.getLatitude();
                    double originLng = location.getLongitude();
                    lastLocation.setLongitude(originLng);
                    lastLocation.setLatitude(originLat);
                    double distance = lastLocation.distanceTo(destinationLoc);
                    distanceTextView.setText((int) distance);
                }

            }
        });




    }
}
