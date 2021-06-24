package fr.kosdev.go4lunch.controller.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.model.Workmate;

public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workmates_img)
    ImageView restaurantImage;
    @BindView(R.id.workmates_txt)
    TextView restaurantName;


    public RestaurantDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void showDetails(Workmate workmate, String currentWorkmateId){
        currentWorkmateId = workmate.getUid();
        Glide.with(restaurantImage.getContext())
                .load(workmate.getUrlPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(restaurantImage);
        restaurantName.setText(workmate.getFirstname() + " " + "is joining");

    }
}
