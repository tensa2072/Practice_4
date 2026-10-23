package com.example.practice_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class MainActivity extends AppCompatActivity {

    private Button btnMorning, btnDay, btnEvening, btnNight;
    private ImageView imgMorning, imgDay, imgEvening, imgNight;
    private ConstraintLayout layoutMorning;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMorning = findViewById(R.id.btnMorning);
        btnDay = findViewById(R.id.btnDay);
        btnEvening = findViewById(R.id.btnEvening);
        btnNight = findViewById(R.id.btnNight);

        imgMorning = findViewById(R.id.imageMorning);
        imgDay = findViewById(R.id.imageDay);
        imgEvening = findViewById(R.id.imageEvening);
        imgNight = findViewById(R.id.imageNight);

        layoutMorning = findViewById(R.id.layoutMorning);
    }


    private void hideAll() {
        layoutMorning.setVisibility(View.INVISIBLE);
        imgDay.setVisibility(View.INVISIBLE);
        imgEvening.setVisibility(View.INVISIBLE);
        imgNight.setVisibility(View.INVISIBLE);
    }

    public void btnMorningClick(View view) {
        hideAll();
        layoutMorning.setVisibility(View.VISIBLE);

        showNotification(R.drawable.cleaning, "Утро",
                "Привести в порядок свою планету");
    }

    public void btnDayClick(View view) {
        hideAll();
        imgDay.setVisibility(View.VISIBLE);

        showNotification(R.drawable.rose, "День", "Полить розу");
    }

    public void btnEveningClick(View view) {
        hideAll();
        imgEvening.setVisibility(View.VISIBLE);

        showNotification(R.drawable.home, "Вечер",
                "Закрыть розу ширмой");
    }

    public void btnNightClick(View view) {
        hideAll();
        imgNight.setVisibility(View.VISIBLE);

        showNotification(R.drawable.sunset, "Ночь",
                "Полюбоваться звездами");
    }

    private void showNotification(int iconRes, String title, String text) {
        notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)              // завершение после прочтения
                        .setSmallIcon(iconRes)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setDefaults(NotificationCompat.DEFAULT_ALL) // звук + вибрация
                        .setPriority(PRIORITY_HIGH);

        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
    }


    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public void onPrinceClick(View view) {
        showToast("Маленький принц");
    }

    public void onPlanetClick(View view) {
        showToast("Астероид Б-612, планета на которой живет Маленький принц");
    }

    public void onVolcanoClick(View view) {
        showToast("Потухший вулкан. О нем нужно заботиться");
    }

    public void onRoseClick(View view) {
        showToast("Её нужно поливать, а на ночь укрывать ширмой и колпаком");
    }

    public void onBreakfastClick(View view) {
        showToast("Действующий вулкан, на нем удобно разогревать завтрак");
    }
}