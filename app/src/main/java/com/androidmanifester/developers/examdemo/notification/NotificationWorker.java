package com.androidmanifester.developers.examdemo.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.androidmanifester.developers.examdemo.MainActivity;
import com.androidmanifester.developers.examdemo.data.DataRepository;
import com.androidmanifester.developers.examdemo.data.Smiley;
import com.google.developers.examdemo.R;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    private static final int NOTIFICATION_ID = 22;
    private static final String CHANNEL_ID = "notify-moji";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        NotificationManager notificationManager = getApplicationContext()
                .getSystemService(NotificationManager.class);
        DataRepository repository = DataRepository.getInstance(getApplicationContext());
        Smiley smiley = repository.getSmiley();

        if (notificationManager == null | smiley == null) {
            return Result.failure();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getApplicationContext()
                    .getString(R.string.notification_channel_name);
            String description = getApplicationContext()
                    .getString(R.string.notification_channel_description);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(getApplicationContext().getString(R.string.notification_title, smiley.getEmoji(), smiley.getCode()))
                .setContentText(smiley.getName())
                .setSmallIcon(R.drawable.ic_mood)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);

        return Result.success();
    }
}
