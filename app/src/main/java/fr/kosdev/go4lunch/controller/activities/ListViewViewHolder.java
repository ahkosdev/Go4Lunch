package fr.kosdev.go4lunch.controller.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
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



    public ListViewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

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
                        //.apply(RequestOptions.circleCropTransform())
                        .into(restaurantImage);

            }

    }
}
