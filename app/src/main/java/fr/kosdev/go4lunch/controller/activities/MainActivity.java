package fr.kosdev.go4lunch.controller.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import fr.kosdev.go4lunch.R;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 125;

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

            }
        }
    }
}