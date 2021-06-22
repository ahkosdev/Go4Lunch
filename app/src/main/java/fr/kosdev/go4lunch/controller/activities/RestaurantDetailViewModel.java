package fr.kosdev.go4lunch.controller.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;
import fr.kosdev.go4lunch.repositories.NearbySearchServiceRepository;

public class RestaurantDetailViewModel extends ViewModel {

    private MutableLiveData<ExampleDetail> detailLiveData;
    private NearbySearchServiceRepository detailRepository;

    public void init(){

        if (detailLiveData != null){
            return;
        }

        detailRepository = NearbySearchServiceRepository.getInstance();
        detailLiveData = detailRepository.getPlaceId("placeId");
    }
    public LiveData<ExampleDetail> getDetailLiveData(){

        return detailLiveData;
    }
}
