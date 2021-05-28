package fr.kosdev.go4lunch.repositories;

import androidx.lifecycle.MutableLiveData;

import fr.kosdev.go4lunch.model.pojo.Example;
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
        nearbySearchApi.getNearbyPlace(type, location, radius).enqueue(new Callback<Example>() {
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
}
