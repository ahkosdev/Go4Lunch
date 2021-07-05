package fr.kosdev.go4lunch.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.controller.fragments.WorkmatesFragment;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;
import fr.kosdev.go4lunch.model.pojo_detail.Result;

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
    @BindView(R.id.restaurant_detail_fab)
    FloatingActionButton restaurantFab;
    @BindView(R.id.restaurant_detail_rcv)
    RecyclerView restaurantDetailRcv;

    private RestaurantDetailsAdapter restaurantAdapter;
    private Result result;
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
                       restaurantName.setText(exampleDetail.getResult().getName());
                       restaurantVicinity.setText(exampleDetail.getResult().getVicinity());
                       String photoReference = exampleDetail.getResult().getPhotos().get(0).getPhotoReference();
                       if (photoReference != null)
                       Glide.with(restaurantImage.getContext())
                               .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=600&photoreference="+ photoReference +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                               .into(restaurantImage);
                       callButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               phoneNumber = exampleDetail.getResult().getFormattedPhoneNumber();
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
                               restaurantUrl = exampleDetail.getResult().getWebsite();
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
                               String restaurantName = exampleDetail.getResult().getName();
                               String restaurantAddress = exampleDetail.getResult().getVicinity();
                               WorkmateHelper.updatePlaceId(placeId,currentWorkmate.getUid());
                               WorkmateHelper.updateRestaurantName(restaurantName,currentWorkmate.getUid());
                               WorkmateHelper.updateRestaurantAddress(restaurantAddress,currentWorkmate.getUid());

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
                    restaurantName.setText(exampleDetail.getResult().getName());
                    restaurantVicinity.setText(exampleDetail.getResult().getVicinity());
                    String photoReference = exampleDetail.getResult().getPhotos().get(0).getPhotoReference();
                    if (photoReference != null)
                        Glide.with(restaurantImage.getContext())
                                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=600&photoreference="+ photoReference +"&key=AIzaSyBk1fsJRc21Wlt0usxn_UtjPhY2waPqiRE")
                                .into(restaurantImage);
                    callButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            phoneNumber = exampleDetail.getResult().getFormattedPhoneNumber();
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
                            restaurantUrl = exampleDetail.getResult().getWebsite();
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