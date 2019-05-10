package com.dap.meau.Service;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.UserModel;
import com.dap.meau.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String type = remoteMessage.getData().get("type");
        NotificationCompat.Builder mBuilder;

        if (type.equals("adopt")) {
            String namePet = remoteMessage.getData().get("pet");
            String nameUser = remoteMessage.getData().get("user");

            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notifications")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle("Adoção")
                    .setContentText(String.format("%s quer adotar seu animal $s", nameUser, namePet));
        }
        else {
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notifications")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle("Adoção")
                    .setContentText("Erro!");
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(0, mBuilder.build());
        
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        UserModel model = UserHelper.getUserModel(getApplicationContext());
        model.setToken(token);
        UserHelper.setUserModel(getApplicationContext(), model);
    }

}
