package com.dap.meau.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.dap.meau.Helper.DatabaseFirebase.PetDatabaseHelper;
import com.dap.meau.Helper.UserHelper;
import com.dap.meau.Model.PetModel;
import com.dap.meau.Model.UserModel;
import com.dap.meau.PerfilAnimal;
import com.dap.meau.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("NotificationTeste", remoteMessage.getData().toString());
        final String type = remoteMessage.getData().get("type");
        final String namePet = remoteMessage.getData().get("pet");
        final String nameUser = remoteMessage.getData().get("user");
        final String petUid = remoteMessage.getData().get("petUid");

        Bundle bundle = new Bundle();
        bundle.putString("UID_PET", petUid);
        Intent intent = new Intent(getApplicationContext(), PerfilAnimal.class);
        intent.putExtras(bundle);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) (System.currentTimeMillis() / 1000), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "meau_notification")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(getColor(R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        if (type.equals("adopt")) {
            mBuilder.setContentTitle("Adoção")
                    .setContentText(String.format("%s quer adotar %s", nameUser, namePet));
        } else {
            mBuilder.setContentTitle("Parabéns!")
                    .setContentText(String.format("Você adotou %s de %s", namePet, nameUser));
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MessageService.this);

        Log.d("NotificationTeste", mBuilder.toString());
        notificationManager.notify((int) (System.currentTimeMillis() / 1000), mBuilder.build());

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        UserModel model = UserHelper.getUserModel(getApplicationContext());
        model.setToken(token);
        UserHelper.setUserModel(getApplicationContext(), model);
    }

}
