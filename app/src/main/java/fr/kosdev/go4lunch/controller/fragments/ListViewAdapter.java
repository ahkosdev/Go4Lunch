package fr.kosdev.go4lunch.controller.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.activities.ListViewViewHolder;
import fr.kosdev.go4lunch.controller.activities.RestaurantDetailActivity;
import fr.kosdev.go4lunch.controller.activities.RestaurantDetailActivity_ViewBinding;
import fr.kosdev.go4lunch.model.Restaurant;
import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo.Result;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewViewHolder> {


    private List<Result> mResults;
    private String placeId;

    public ListViewAdapter( List<Result> results) {
        this.mResults = results;
    }

    @NonNull
    @Override
    public ListViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_items,parent,false);
        return new  ListViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewViewHolder holder, int position) {

        holder.updateRestaurantList(mResults.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeId = mResults.get(position).getPlaceId();
                Intent intent = new Intent(v.getContext(), RestaurantDetailActivity.class);
                intent.putExtra("KEY_DETAIL",  placeId );
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
