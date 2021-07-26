package fr.kosdev.go4lunch.model;

import java.util.ArrayList;
import java.util.List;

public class Workmate {
    private String uid;
    private String firstname;
    private String placeId;
    private String urlPicture;
    private String restaurantName;
    private String restaurantAddress;
    private List<String> ratings = new ArrayList<>();

    public Workmate(){}

    public Workmate(String uid, String firstname, String urlPicture, String placeId, String restaurantName, String restaurantAddress, List<String> ratings) {
        this.uid = uid;
        this.firstname = firstname;
        this.urlPicture = urlPicture;
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.ratings = ratings;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public List<String> getRatings() {
        return ratings;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }
}

