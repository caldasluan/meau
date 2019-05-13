package com.dap.meau.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
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
        String namePet = remoteMessage.getData().get("pet");
        String nameUser = remoteMessage.getData().get("user");
        NotificationCompat.Builder mBuilder;

        if (type.equals("adopt")) {
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "meau_notification")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setColor(getColor(R.color.colorPrimary))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle("Adoção")
                    .setContentText(String.format("%s quer adotar %s", nameUser, namePet));
        }
        else {
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "meau_notification")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setColor(getColor(R.color.colorPrimary))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle("Parabéns!")
                    .setContentText(String.format("Você adotou %s de %s", namePet, nameUser));
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Log.d("NotificationTeste", mBuilder.toString());
        notificationManager.notify((int)(System.currentTimeMillis()/1000), mBuilder.build());

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        UserModel model = UserHelper.getUserModel(getApplicationContext());
        model.setToken(token);
        UserHelper.setUserModel(getApplicationContext(), model);
    }

}
