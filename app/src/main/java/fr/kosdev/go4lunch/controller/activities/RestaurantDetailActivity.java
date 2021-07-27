package fr.kosdev.go4lunch.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.pojo_detail.Results;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class RestaurantDetailActivity extends AppCompatActivity {

    @BindView(R.id.restaurant_detail_name_txt)
    TextView restaurantName;
    @BindView(R.id.restaurant_detail_vicinity_txt)
    TextView restaurantVicinity;
    @BindView(R.id.restaurant_detail_img)
    ImageView restaurantImage;
    @BindView(R.id.call_button)
    ImageButton callButton;
    @BindView(R.id.website_button)
    ImageButton webImage;
    @BindView(R.id.like_button)
    ImageButton likeButton;
    @BindView(R.id.restaurant_detail_fab)
    FloatingActionButton restaurantFab;
    @BindView(R.id.restaurant_detail_rcv)
    RecyclerView restaurantDetailRcv;
    @BindView(R.id.like_img)
    ImageView likeImage;

    private RestaurantDetailsAdapter restaurantAdapter;
    private Results mResults;
    private RestaurantDetailViewModel restaurantViewModel;
    private String phoneNumber;
    private String restaurantUrl;
    private FirebaseUser currentWorkmate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        this.configureRecyclerView();
        this.configureViewModel();
        this.getPlaceIdAndUpdateUI();
        this.updateUIWithCurrentWorkmatePlaceId();
    }


    private void configureRecyclerView(){

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("KEY_DETAIL")){
                String placeId = intent.getStringExtra("KEY_DETAIL");
                restaurantAdapter = new RestaurantDetailsAdapter(getOptionForAdapter(WorkmateHelper.getWorkmatesWithPlaceId(placeId)));
                restaurantDetailRcv.setAdapter(restaurantAdapter);
            }
        }


    }

    private FirestoreRecyclerOptions<Workmate> getOptionForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Workmate>()
                .setQuery(query, Workmate.class)
                .setLifecycleOwner(this)
                .build();
    }


    private void configureViewModel(){
        restaurantViewModel = new ViewModelProvider(this).get(RestaurantDetailViewModel.class);
        restaurantViewModel.init();
    }

    private void getPlaceIdAndUpdateUI(){

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("KEY_DETAIL")){
               String placeId = intent.getStringExtra("KEY_DETAIL");
                   restaurantViewModel.getDetailLiveData(placeId).observe(this, exampleDetail -> {
                       restaurantName.setText(exampleDetail.getResults().getName());
                       restaurantVicinity.setText(exampleDetail.getResults().getVicinity());
                       String photoReference = exampleDetail.getResults().getPhotos().get(0).getPhotoReference();
                       if (photoReference != null)
                       Glide.with(restaurantImage.getContext())
                               .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=600&photoreference="+ photoReference +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                               .into(restaurantImage);
                       callButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               phoneNumber = exampleDetail.getResults().getFormattedPhoneNumber();
                               Intent callIntent = new Intent(Intent.ACTION_DIAL);
                               callIntent.setData(Uri.parse("tel:" + phoneNumber));
                               if (callIntent.resolveActivity(getPackageManager()) != null){
                                   startActivity(callIntent);
                               }
                           }
                       });

                       webImage.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               restaurantUrl = exampleDetail.getResults().getWebsite();
                               Uri webPage = Uri.parse(restaurantUrl);
                               Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
                               if (webIntent.resolveActivity(getPackageManager()) != null){
                                   startActivity(webIntent);
                               }
                           }
                       });

                       restaurantFab.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               currentWorkmate = FirebaseAuth.getInstance().getCurrentUser();
                               String restaurantName = exampleDetail.getResults().getName();
                               String restaurantAddress = exampleDetail.getResults().getVicinity();
                               WorkmateHelper.updatePlaceId(placeId,currentWorkmate.getUid());
                               WorkmateHelper.updateRestaurantName(restaurantName,currentWorkmate.getUid());
                               WorkmateHelper.updateRestaurantAddress(restaurantAddress,currentWorkmate.getUid());

                           }
                       });
                       likeButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               currentWorkmate = FirebaseAuth.getInstance().getCurrentUser();
                               String restaurantName = exampleDetail.getResults().getName();
                               WorkmateHelper.getWorkmate(currentWorkmate.getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                   @Override
                                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                                       Workmate workmate= documentSnapshot.toObject(Workmate.class);
                                       List<String> ratings = workmate.getRatings();
                                       if (ratings.contains(restaurantName)){
                                           ratings.remove(restaurantName);
                                       }else {
                                           ratings.add(restaurantName);
                                       }
                                       WorkmateHelper.updateRatingsList(ratings, currentWorkmate.getUid());
                                   }
                               });
                               likeImage.setImageResource(R.drawable.ic_baseline_star_yelow_24);

                           }
                       });


                   });


            }
        }
    }
    private void updateUIWithCurrentWorkmatePlaceId(){
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("CURRENT_KEY")){
                String placeId = intent.getStringExtra("CURRENT_KEY");
                restaurantViewModel.getDetailLiveData(placeId).observe(this, exampleDetail -> {
                    restaurantName.setText(exampleDetail.getResults().getName());
                    restaurantVicinity.setText(exampleDetail.getResults().getVicinity());
                    String photoReference = exampleDetail.getResults().getPhotos().get(0).getPhotoReference();
                    if (photoReference != null)
                        Glide.with(restaurantImage.getContext())
                                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=600&photoreference="+ photoReference +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                                .into(restaurantImage);
                    callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            phoneNumber = exampleDetail.getResults().getFormattedPhoneNumber();
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + phoneNumber));
                            if (callIntent.resolveActivity(getPackageManager()) != null){
                                startActivity(callIntent);
                            }
                        }
                    });
                    webImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            restaurantUrl = exampleDetail.getResults().getWebsite();
                            Uri webPage = Uri.parse(restaurantUrl);
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
                            if (webIntent.resolveActivity(getPackageManager()) != null){
                                startActivity(webIntent);
                            }
                        }
                    });
                });
            }
        }
    }

}