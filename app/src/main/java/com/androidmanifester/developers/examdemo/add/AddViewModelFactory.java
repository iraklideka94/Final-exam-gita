package com.androidmanifester.developers.examdemo.add;

import android.app.Activity;
import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.androidmanifester.developers.examdemo.data.DataRepository;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory for creating a AddViewModel.
 */
public class AddViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;

    public static AddViewModelFactory createFactory(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Not yet attached to Application");
        }
        return new AddViewModelFactory(DataRepository.getInstance(application));
    }

    private AddViewModelFactory(DataRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(DataRepository.class).newInstance(mRepository);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
