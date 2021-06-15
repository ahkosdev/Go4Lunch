package fr.kosdev.go4lunch.model;

import com.google.android.gms.maps.model.LatLng;

public class Restaurant {

    private String name;
    LatLng location;

    public Restaurant(String name) {
        this.name = name;
    }

    public Restaurant(String name, LatLng location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
