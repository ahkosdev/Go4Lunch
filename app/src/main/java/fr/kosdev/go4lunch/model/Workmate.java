package fr.kosdev.go4lunch.model;

public class Workmate {
    private String uid;
    private String firstname;
    private String placeId;
    private String urlPicture;
    private String restaurantName;
    private String restaurantAddress;

    public Workmate(){}

    public Workmate(String uid, String firstname, String urlPicture, String placeId, String restaurantName, String restaurantAddress) {
        this.uid = uid;
        this.firstname = firstname;
        this.urlPicture = urlPicture;
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
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
}

