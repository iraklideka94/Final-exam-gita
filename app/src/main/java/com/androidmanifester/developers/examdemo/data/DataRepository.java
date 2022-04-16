package com.androidmanifester.developers.examdemo.data;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

/**
 * Handles data sources and makes sure to execute on the correct thread.
 */
public class DataRepository {

    private final SmileyDao mDao;
    private final ExecutorService mIoExecutor;
    private static volatile DataRepository sInstance = null;
    private static final String TAG = "Repo";

    public static DataRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    SmileyDatabase database = SmileyDatabase.getInstance(context);
                    sInstance = new DataRepository(database.smileyDao(),Executors.newSingleThreadExecutor());
                }
            }
        }
        return sInstance;
    }

    public DataRepository(SmileyDao dao, ExecutorService executor) {
        mIoExecutor = executor;
        mDao = dao;
    }

    public DataSource.Factory<Integer, Smiley> getSmileys() {
        return mDao.getAll();
    }

    public LiveData<List<Smiley>> getRandomSmileys(int limit) {
        try {
            return mIoExecutor.submit(new Callable<LiveData<List<Smiley>>>() {
                @Override
                public LiveData<List<Smiley>> call() throws Exception {
                    LiveData<List<Smiley>> data = mDao.getRandom(limit);
                    return data;
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Smiley smiley) {
        mIoExecutor.execute(() -> mDao.delete(smiley));
    }

    public void save(Smiley smiley){
        mIoExecutor.execute(() -> mDao.insert(smiley));
    }

    public Smiley getSmiley() {
        try {
            return mIoExecutor.submit(mDao::getSmiley).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
