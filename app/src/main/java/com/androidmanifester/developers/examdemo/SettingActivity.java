package com.androidmanifester.developers.examdemo;

import android.os.Bundle;

import com.androidmanifester.developers.examdemo.notification.NotificationWorker;
import com.google.developers.examdemo.R;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        public static final String WORKER_TAG = "notification_worker";

        @Override
        public void onCreatePreferences(Bundle bundle, String string) {
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            final String notifyKey = getString(R.string.pref_key_notification);
            if (preference.getKey().equals(notifyKey)) {
                boolean on = ((SwitchPreferenceCompat) preference).isChecked();
                PeriodicWorkRequest periodicWorkRequest =
                        new PeriodicWorkRequest.Builder(NotificationWorker.class, 24,
                                TimeUnit.HOURS)
                                .addTag(WORKER_TAG)
                                .build();
                if (on) {
                    WorkManager.getInstance(getContext()).enqueue(periodicWorkRequest);
                }else{
                    WorkManager.getInstance(getContext()).cancelAllWork();
                }
            }
            return super.onPreferenceTreeClick(preference);
        }
    }
}
