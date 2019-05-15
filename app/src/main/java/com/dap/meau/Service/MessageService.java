package com.dap.meau.Service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
        Log.d("NotificationTest", "Aqui");
        String type = remoteMessage.getData().get("type");
        String namePet = remoteMessage.getData().get("pet");
        String nameUser = remoteMessage.getData().get("user");
        String petUid = remoteMessage.getData().get("petUid");

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "meau_notification")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(getColor(R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (type.equals("adopt")) {
            mBuilder.setContentTitle("Adoção")
                    .setContentText(String.format("%s quer adotar %s", nameUser, namePet));
        } else {
            mBuilder.setContentTitle("Parabéns!")
                    .setContentText(String.format("Você adotou %s de %s", namePet, nameUser));
        }

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (petUid != null & !petUid.isEmpty()) {
            Log.d("NotificationTest", "Aqui2");

            PetDatabaseHelper.getPetWithUserUid(petUid, new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("NotificationTest", "Aqui5");
                    if (dataSnapshot.getValue() == null) return;

                    Log.d("NotificationTest", "Aqui6");
                    PetModel petModel = dataSnapshot.getValue(PetModel.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PetModel.class.getName(), petModel);

                    Intent intent = new Intent(getApplicationContext(), PerfilAnimal.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    mBuilder.setContentIntent(pendingIntent);

                    Log.d("NotificationTest", "Aqui7");
                    notificationManager.notify((int) (System.currentTimeMillis() / 1000), mBuilder.build());
                    Log.d("NotificationTest", "Aqui8");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("NotificationTest", "Aqui4");
                    notificationManager.notify((int) (System.currentTimeMillis() / 1000), mBuilder.build());
                }
            });
        } else {
            Log.d("NotificationTest", "Aqui3");
            notificationManager.notify((int) (System.currentTimeMillis() / 1000), mBuilder.build());
        }

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        UserModel model = UserHelper.getUserModel(getApplicationContext());
        model.setToken(token);
        UserHelper.setUserModel(getApplicationContext(), model);
    }

}
