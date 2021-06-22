package fr.kosdev.go4lunch.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;
import fr.kosdev.go4lunch.model.pojo_detail.Result;

public class RestaurantDetailActivity extends AppCompatActivity {

    @BindView(R.id.restaurant_detail_name_txt)
    TextView restaurantName;
    @BindView(R.id.restaurant_detail_vicinity_txt)
    TextView restaurantVicinity;
    @BindView(R.id.restaurant_detail_img)
    ImageView restaurantImage;
    @BindView(R.id.restaurant_detail_rcv)
    RecyclerView restaurantDetailRcv;

    private RestaurantDetailsAdapter restaurantAdapter;
    private Result result;
    private RestaurantDetailViewModel restaurantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        this.configureRecyclerView();
    }


    private void configureRecyclerView(){

        restaurantAdapter = new RestaurantDetailsAdapter(getOptionForAdapter(WorkmateHelper.getWorkmates()));
        restaurantDetailRcv.setAdapter(restaurantAdapter);

    }

    private void configureViewModel(){
        restaurantViewModel = new ViewModelProvider(this).get(RestaurantDetailViewModel.class);
        restaurantViewModel.init();
    }

    private FirestoreRecyclerOptions<Workmate> getOptionForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Workmate>()
                .setQuery(query, Workmate.class)
                .setLifecycleOwner(this)
                .build();
    }

    private void getPlaceIdAndUpdateUI(){

        Intent intent = getIntent();
        if (intent != null){
           // String placeId = "";
            if (intent.hasExtra("KEY_DETAIL")){
               String placeId = intent.getStringExtra("KEY_DETAIL");
               if (placeId != null && placeId == result.getPlaceId() ){
                   restaurantViewModel.getDetailLiveData().observe(this, exampleDetail -> {
                       restaurantName.setText(exampleDetail.getResult().getName());
                   });

               }
            }
        }
    }
}