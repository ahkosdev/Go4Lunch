package fr.kosdev.go4lunch.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.controller.activities.MainActivity;
import fr.kosdev.go4lunch.model.Workmate;

public class NotificationService extends FirebaseMessagingService {

    private final int NOTIFICATION_ID = 001;
    private final String NOTIFICATION_TAG = "GO4LUNCH";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("_", MODE_PRIVATE);
        //Boolean defaultValue = true;
        Boolean enableNotification = sharedPref.getBoolean(getString(R.string.setting_notification), false);
        if (enableNotification == true) {
            if (remoteMessage.getNotification() != null) {
                RemoteMessage.Notification notification = remoteMessage.getNotification();
                sendVisualNotification(notification);
            }
        }
    }

    private void sendVisualNotification(RemoteMessage.Notification notification){


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        WorkmateHelper.getWorkmate(this.getCurrentWorkmate().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Workmate currentWorkmate = documentSnapshot.toObject(Workmate.class);
                String restaurantName = currentWorkmate.getRestaurantName();
                String restaurantAddress = currentWorkmate.getRestaurantAddress();
                String placeId = currentWorkmate.getPlaceId();

                WorkmateHelper.getWorkmatesWithPlaceId(placeId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : documents) {
                            Workmate workmate = document.toObject(Workmate.class);


                            String chanelId = getString(R.string.default_notification_channel_id);
                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), chanelId)
                                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                    .setContentTitle(getString(R.string.notification_text) + " " + restaurantName)
                                    .setContentText(workmate.getFirstname())
                                    .setStyle(new NotificationCompat.InboxStyle().addLine(restaurantAddress).addLine(workmate.getFirstname()))
                                    .setAutoCancel(true)
                                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                    .setContentIntent(pendingIntent);

                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CharSequence chanelName = "Go4Lunch Notifications";
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel channel = new NotificationChannel(chanelId, chanelName, importance);
                                notificationManager.createNotificationChannel(channel);
                            }
                            notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());

                        }

                    }
                });



            }
        });


    }

    protected FirebaseUser getCurrentWorkmate() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
