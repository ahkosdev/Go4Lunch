package fr.kosdev.go4lunch.api;

import android.content.Intent;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import fr.kosdev.go4lunch.model.Workmate;
import fr.kosdev.go4lunch.model.pojo.Result;




public class WorkmateHelper {

    private static final String COLLECTION_NAME = "workmates";
    private static String restaurantId;
    private static List<Result> results;

    public static CollectionReference getWorkmatesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Query getWorkmates(){
        return WorkmateHelper.getWorkmatesCollection();
    }

    public static Query getWorkmatesWithPlaceId(){
        restaurantId = results.get(0).getPlaceId();
        return getWorkmates().whereEqualTo("placeId", restaurantId);
    }

    public static Task<Void> createWorkmate(String uid, String firstname, String urlPicture, String placeId, String restaurantName, String restaurantAddress){

        Workmate workmateToCreate = new Workmate(uid, firstname,urlPicture, placeId, restaurantName, restaurantAddress);
        return WorkmateHelper.getWorkmatesCollection().document(uid).set(workmateToCreate);
    }

    public static Task<DocumentSnapshot> getWorkmate(String uid){

        return WorkmateHelper.getWorkmatesCollection().document(uid).get();
    }

    public static Task<Void> updatePlaceId(String placeId, String uid){
        return WorkmateHelper.getWorkmatesCollection().document(uid).update("placeId", placeId);
    }

    public static Task<Void> updateRestaurantName(String restaurantName, String uid){
        return WorkmateHelper.getWorkmatesCollection().document(uid).update("restaurantName", restaurantName);
    }

    public static Task<Void> updateRestaurantAddress(String restaurantAddress, String uid) {
        return WorkmateHelper.getWorkmatesCollection().document(uid).update("restaurantAddress", restaurantAddress);
    }

        public static Task<Void> deleteWorkmate (String uid){
            return WorkmateHelper.getWorkmatesCollection().document(uid).delete();
        }

}
