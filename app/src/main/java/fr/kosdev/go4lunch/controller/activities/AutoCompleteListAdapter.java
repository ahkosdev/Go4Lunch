package fr.kosdev.go4lunch.controller.activities;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.model.pojo_detail.Results;

public class AutoCompleteListAdapter extends RecyclerView.Adapter<ListViewViewHolder> {

    private List<Results> mDetailResult;
    private String placeId;

    public AutoCompleteListAdapter(List<Results> detailResult) {
        this.mDetailResult = detailResult;
    }

    @NonNull
    @Override
    public ListViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_items,parent,false);
        return new  ListViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewViewHolder holder, int position) {

        holder.updateListWithDetailResult(mDetailResult.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeId = mDetailResult.get(position).getPlaceId();
                Intent intent = new Intent(v.getContext(), RestaurantDetailActivity.class);
                intent.putExtra("KEY_DETAIL",  placeId );
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDetailResult.size();
    }
}
