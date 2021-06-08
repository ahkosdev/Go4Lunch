package fr.kosdev.go4lunch.controller.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.model.Workmate;

public class WorkmateRecyclerViewAdapter extends FirestoreRecyclerAdapter<Workmate,WorkmateViewHolder> {



    public WorkmateRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<Workmate> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkmateViewHolder workmateViewHolder, int i, @NonNull Workmate workmate) {

        workmateViewHolder.updateUIWhenCreating(workmate);

    }

    @NonNull
    @Override
    public WorkmateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_items, parent, false);
        return new WorkmateViewHolder(view);
    }


}
