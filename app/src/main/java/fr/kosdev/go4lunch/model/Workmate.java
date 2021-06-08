package fr.kosdev.go4lunch.model;

public class Workmate {
    private String uid;
    private String firstname;
    private  String lastname;
    private String urlPicture;

    public Workmate(){}

    public Workmate(String uid, String firstname, String lastname, String urlPicture) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.urlPicture = urlPicture;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
