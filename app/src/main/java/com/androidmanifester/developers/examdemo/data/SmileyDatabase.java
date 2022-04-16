package com.androidmanifester.developers.examdemo.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.developers.examdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

@Database(entities = {Smiley.class},version = 1,exportSchema = false)
public abstract class SmileyDatabase extends RoomDatabase {

    public abstract SmileyDao smileyDao();

    private static volatile SmileyDatabase sInstance = null;
    private static final String TAG = "SmileyDB";

    /**
     * Returns an instance of Room Database.
     *
     * @param context application context
     * @return The singleton SmileyDatabase
     */


    @NonNull
    public static synchronized SmileyDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (SmileyDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),  SmileyDatabase.class, context.getString(R.string.db_name))
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    Log.d(TAG, "onOpen: ");
                                }

                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d(TAG, "onCreate: ");
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            fillWithDemoData(context);
                                        }
                                    });
                                }
                            }).build();
                    SmileyDao dao = getInstance(context).smileyDao();
                    dao.getSmiley();
                    Log.d(TAG, "getInstance: ");
                }
            }
        }
        return sInstance;
    }


    @WorkerThread
    private static void fillWithDemoData(Context context) {
        SmileyDao dao = getInstance(context).smileyDao();
        JSONArray emoji = loadJsonArray(context);
        try {
            for (int i = 0; i < emoji.length();i++){
                    String ika = "Emoji item";
                    JSONObject item = emoji.getJSONObject(i);
                    dao.insert(new Smiley(item.getString("code"),
                        item.getString("name"),
                        item.getString("char"),String.valueOf(ika)));

                }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    private static JSONArray loadJsonArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.emoji);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("emojis");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}
