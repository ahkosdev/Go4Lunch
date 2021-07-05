package fr.kosdev.go4lunch.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
        if (remoteMessage.getNotification() != null){
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            sendVisualNotification(notification);
        }
    }

    private void sendVisualNotification(RemoteMessage.Notification notification){


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

       // WorkmateHelper.getWorkmate(this.getCurrentWorkmate().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           // @Override
           // public void onSuccess(DocumentSnapshot documentSnapshot) {
               // Workmate currentWorkmate = documentSnapshot.toObject(Workmate.class);
               // String restaurantName = currentWorkmate.getRestaurantName();


            //}
       // });
        String chanelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),chanelId)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence chanelName = "Go4Lunch Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(chanelId,chanelName,importance);
             notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());


    }

    protected FirebaseUser getCurrentWorkmate() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
