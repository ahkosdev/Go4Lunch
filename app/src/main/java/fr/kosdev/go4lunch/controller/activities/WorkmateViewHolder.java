package fr.kosdev.go4lunch.controller.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.Base.BaseActivity;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.model.Workmate;

public class WorkmateViewHolder extends RecyclerView.ViewHolder {


@BindView(R.id.workmates_img)
    ImageView workmateImage;
@BindView(R.id.workmates_txt)
    TextView workmateTextView;


    public WorkmateViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateUIWhenCreating(Workmate workmate){


        Glide.with(workmateImage.getContext())
                .load(workmate.getUrlPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(workmateImage);
        if (workmate.getRestaurantName() != null){

            workmateTextView.setText(workmate.getFirstname() +" " + workmateTextView.getContext().getResources().getString(R.string.choice_text) + " "+ "("+( workmate.getRestaurantName())+")");

        }else {
            workmateTextView.setText(workmate.getFirstname() + " " + workmateTextView.getContext().getResources().getString(R.string.no_choice_text));
            workmateTextView.setTextColor(Color.parseColor("#b9b2b2"));
        }


        }



}
