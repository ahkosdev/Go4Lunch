package fr.kosdev.go4lunch.repositories;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import fr.kosdev.go4lunch.model.autocomplete.AutocompleteResult;
import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;
import fr.kosdev.go4lunch.network.NearbySearchApiCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbySearchServiceRepository {


    private static NearbySearchServiceRepository nearbySearchServiceRepository;

    public static NearbySearchServiceRepository getInstance(){

        if (nearbySearchServiceRepository == null){
            nearbySearchServiceRepository = new NearbySearchServiceRepository();
        }

        return nearbySearchServiceRepository;
    }

    private NearbySearchApiCall nearbySearchApi;

    public NearbySearchServiceRepository() {
        nearbySearchApi = NearbySearchApiCall.retrofit.create(NearbySearchApiCall.class);
    }

    public MutableLiveData<Example> getData(String type, String location, int radius){

        MutableLiveData<Example> nearbyData = new MutableLiveData<>();
        nearbySearchApi.getNearbyPlace(type,location, radius).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                if (response.isSuccessful()){

                    nearbyData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                nearbyData.setValue(null);

            }

        });
        return nearbyData;
    }

    public MutableLiveData<ExampleDetail> getPlaceId(String placeId){
        MutableLiveData<ExampleDetail> detail = new MutableLiveData<>();
        nearbySearchApi.getPlaceId(placeId).enqueue(new Callback<ExampleDetail>() {
            @Override
            public void onResponse(Call<ExampleDetail> call, Response<ExampleDetail> response) {
                if (response.isSuccessful()){
                    detail.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ExampleDetail> call, Throwable t) {

                detail.setValue(null);

            }
        });
        return detail;
    }

    public MutableLiveData<AutocompleteResult> getAutocompleteInputText(String input, String location){
        MutableLiveData<AutocompleteResult> autocompleteData = new MutableLiveData<>();
        nearbySearchApi.getRestaurantsWithAutocomplete(input, location).enqueue(new Callback<AutocompleteResult>() {
            @Override
            public void onResponse(Call<AutocompleteResult> call, Response<AutocompleteResult> response) {
                if (response.isSuccessful()){
                    autocompleteData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<AutocompleteResult> call, Throwable t) {
                autocompleteData.setValue(null);

            }
        });
        return autocompleteData;

    }
}
