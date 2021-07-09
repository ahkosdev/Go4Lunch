package fr.kosdev.go4lunch.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.kosdev.go4lunch.model.autocomplete.AutocompletResult;
import fr.kosdev.go4lunch.model.pojo.Example;
import fr.kosdev.go4lunch.repositories.NearbySearchServiceRepository;

public class NearbyViewModel extends ViewModel {

    private MutableLiveData<Example> mLiveData;
    private NearbySearchServiceRepository nearbyRepository;
    private MutableLiveData<AutocompletResult> autocompleteLiveData;

    public void init(){

        if (mLiveData != null){
            return;
        }

        nearbyRepository = NearbySearchServiceRepository.getInstance();
        mLiveData = nearbyRepository.getData("restaurant", "46.6743,4.3634", 1500);
    }
    public LiveData<Example> getNearbyRepository(){

        return mLiveData;
    }
    public void initAutocomplete(){
        if (autocompleteLiveData != null){
            return;
        }


    }
    public LiveData<AutocompletResult> getAutocompleteResult(String input, String location){
        nearbyRepository = NearbySearchServiceRepository.getInstance();
        autocompleteLiveData = nearbyRepository.getAutocompleteInputText(input, location );
        return autocompleteLiveData;

    }
}
