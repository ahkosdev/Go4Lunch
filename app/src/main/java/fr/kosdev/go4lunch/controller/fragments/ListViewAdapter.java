package fr.kosdev.go4lunch.controller.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.controller.activities.ListViewViewHolder;
import fr.kosdev.go4lunch.model.Restaurant;
import fr.kosdev.go4lunch.model.pojo.Result;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewViewHolder> {


    private List<Result> mResults;
    private List<Restaurant> mRestaurants;

    public ListViewAdapter( List<Result> results) {
        this.mResults = results;
       // this.mRestaurants = restaurants;
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

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
