package fr.kosdev.go4lunch.controller.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.model.Workmate;

public class RestaurantDetailsAdapter extends FirestoreRecyclerAdapter<Workmate, RestaurantDetailsViewHolder> {



    public RestaurantDetailsAdapter(@NonNull FirestoreRecyclerOptions<Workmate> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RestaurantDetailsViewHolder holder, int position, @NonNull Workmate workmate) {

        holder.showDetails(workmate, holder.itemView.getContext());

    }

    @NonNull
    @Override
    public RestaurantDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_items, parent, false);
        return new RestaurantDetailsViewHolder(view);
    }
}
