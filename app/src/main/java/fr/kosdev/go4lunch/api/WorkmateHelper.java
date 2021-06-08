package fr.kosdev.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import fr.kosdev.go4lunch.model.Workmate;

public class WorkmateHelper {

    private static final String COLLECTION_NAME = "workmates";

    public static CollectionReference getWorkmatesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Query getWorkmates(){
        return WorkmateHelper.getWorkmatesCollection();
    }

    public static Task<Void> createWorkmate(String uid, String firstname, String lastname, String urlPicture){

        Workmate workmateToCreate = new Workmate(uid, firstname, lastname, urlPicture);
        return WorkmateHelper.getWorkmatesCollection().document(uid).set(workmateToCreate);
    }

    public static Task<DocumentSnapshot> getWorkmate(String uid){

        return WorkmateHelper.getWorkmatesCollection().document(uid).get();
    }

    public static Task<Void> updateWorkmate(String firstname, String uid){
        return WorkmateHelper.getWorkmatesCollection().document(uid).update("firstname", firstname);
    }

    public static Task<Void> deleteWorkmate(String uid){
        return WorkmateHelper.getWorkmatesCollection().document(uid).delete();
    }
}
