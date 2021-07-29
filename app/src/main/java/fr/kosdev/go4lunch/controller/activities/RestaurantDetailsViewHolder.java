package fr.kosdev.go4lunch.controller.activities;

import android.content.Context;
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
import fr.kosdev.go4lunch.utils.Utils;

public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workmates_img)
    ImageView restaurantImage;
    @BindView(R.id.workmates_txt)
    TextView workmateName;


    public RestaurantDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void showDetails(Workmate workmate, Context context){
        Glide.with(context)
                .load(workmate.getUrlPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(restaurantImage);
       workmateName.setText(Utils.getWorkmateDetailInfo(workmate,context));
        //workmateName.setText(workmate.getFirstname() + " " + context.getResources().getString(R.string.joining_text));

    }
}
