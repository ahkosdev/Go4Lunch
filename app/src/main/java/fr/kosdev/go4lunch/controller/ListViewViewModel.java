package fr.kosdev.go4lunch.controller;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.model.pojo_detail.ExampleDetail;
import fr.kosdev.go4lunch.repositories.NearbySearchServiceRepository;

public class ListViewViewModel extends ViewModel {

    private MutableLiveData<Example> mLiveData;
    private NearbySearchServiceRepository nearbyRepository;
    private MutableLiveData<ExampleDetail> detailLiveData;
    private NearbySearchServiceRepository detailRepository;



    public void init(){

        if (mLiveData != null){
            return;
        }

        nearbyRepository = NearbySearchServiceRepository.getInstance();

    }
    public LiveData<Example> getNearbyRepository(String myLocation){
        mLiveData = nearbyRepository.getData("restaurant", myLocation, 1500);

        return mLiveData;
    }

    public LiveData<ExampleDetail> getDetailLiveData(String placeId) {

        detailRepository = NearbySearchServiceRepository.getInstance();
        detailLiveData = detailRepository.getPlaceId(placeId);
        return detailLiveData;
    }
}
