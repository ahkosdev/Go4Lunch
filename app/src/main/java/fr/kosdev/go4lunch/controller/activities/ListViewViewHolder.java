package fr.kosdev.go4lunch.controller.activities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.model.Restaurant;
import fr.kosdev.go4lunch.model.pojo.Result;

public class ListViewViewHolder extends ViewHolder {

    @BindView(R.id.restaurant_name_txt)
    TextView restaurantName;



    public ListViewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateRestaurantList(Result result){

        restaurantName.setText(result.getName());

    }
}
