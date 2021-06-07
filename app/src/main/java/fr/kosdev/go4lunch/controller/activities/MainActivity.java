package fr.kosdev.go4lunch.controller.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 125;
    private String firstname;
    private String uid;
    private String urlPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startSigningActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void startSigningActivity(){

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme)
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                         new AuthUI.IdpConfig.FacebookBuilder().build(),
                         new AuthUI.IdpConfig.EmailBuilder().build() ))
                .setIsSmartLockEnabled(false, true)
                .build(), RC_SIGN_IN );
    }

    private void startHomepageActivity(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                this.startHomepageActivity();
                this.createWorkmateInFireStore();

            }
        }
    }

    private FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private void createWorkmateInFireStore(){
        if (this.getCurrentUser() != null){
            String uid = this.getCurrentUser().getUid();
            String firstname = this.getCurrentUser().getDisplayName();
            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ?
                    this.getCurrentUser().getPhotoUrl().toString():null;

            WorkmateHelper.createWorkmate(uid,firstname,urlPicture);
        }
    }


}