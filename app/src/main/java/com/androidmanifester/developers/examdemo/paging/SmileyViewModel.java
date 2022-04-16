package com.androidmanifester.developers.examdemo.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.androidmanifester.developers.examdemo.data.DataRepository;
import com.androidmanifester.developers.examdemo.data.Smiley;

public class SmileyViewModel extends ViewModel {

    private final DataRepository mRepository;
    public static int PAGE_SIZE = 30;
    public static boolean PLACEHOLDERS = true;

    public SmileyViewModel(DataRepository repository) {
        mRepository = repository;
    }

    public void save(Smiley smiley) {

    }

    public void delete(Smiley smiley) {
        mRepository.delete(smiley);
    }

    public LiveData<PagedList<Smiley>> getAllSmileys(){
        DataSource.Factory<Integer, Smiley> smileys = mRepository.getSmileys();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(PLACEHOLDERS)
                .setPageSize(PAGE_SIZE).build();
        return new LivePagedListBuilder<>(smileys,config).build();
    }



}