package fr.kosdev.go4lunch.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.repositories.NearbySearchServiceRepository;

public class NearbyViewModel extends ViewModel {

     double lat;
     double lng ;


    private MutableLiveData<Example> mLiveData;
    private NearbySearchServiceRepository nearbyRepository;

    public void init(){

        if (mLiveData != null){
            return;
        }

        nearbyRepository = NearbySearchServiceRepository.getInstance();
        mLiveData = nearbyRepository.getData("restaurant", "48,866667 , 2,333333", 1500);
    }
    public LiveData<Example> getNearbyRepository(){

        return mLiveData;
    }
}
