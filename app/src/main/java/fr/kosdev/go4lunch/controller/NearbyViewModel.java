package fr.kosdev.go4lunch.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.repositories.NearbySearchServiceRepository;

public class NearbyViewModel extends ViewModel {

    private MutableLiveData<Example> mLiveData;
    private NearbySearchServiceRepository nearbyRepository;

    public void init(){

        if (mLiveData != null){
            return;
        }

        nearbyRepository = NearbySearchServiceRepository.getInstance();
        mLiveData = nearbyRepository.getData("restaurant","46.232193,2.209667", 1500);
    }
    public LiveData<Example> getNearbyRepository(){

        return mLiveData;
    }
}
