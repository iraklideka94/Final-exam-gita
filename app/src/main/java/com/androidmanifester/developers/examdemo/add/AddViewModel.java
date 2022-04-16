package com.androidmanifester.developers.examdemo.add;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidmanifester.developers.examdemo.data.DataRepository;
import com.androidmanifester.developers.examdemo.data.Smiley;

/**
 * View mode for AddSmileyActivity.
 */
public class AddViewModel extends ViewModel {

    private final MutableLiveData<String> mUnicode = new MutableLiveData<>();
    private DataRepository mRepository;

    public AddViewModel(DataRepository repository) {
        mRepository = repository;
    }

    /**
     * @return a live data of updated unicode.
     */
    public MutableLiveData<String> getUnicode() {
        return mUnicode;
    }

    /**
     * The text value has changed update a unicode.
     */
    public void emojiChanged(String text) {
        mUnicode.setValue(getStringsHex(text));
    }

    public void save(String emoji, String name) {
        int min = 1;
        int max = 400;
        //Generate random double value from 1 to 400
        double a = Math.random()*(max-min+1)+min;
        Smiley smiley = new Smiley(getStringsHex(emoji), name, emoji,String.valueOf(a));
        mRepository.save(smiley);
    }

    /**
     * Returns a Unicode U+1F609.
     * Get the code point of a String, then get the hex value of it.
     *
     * @param text string of any characters
     * @return hex string of the code point
     */


    private String getStringsHex(String text) {
        if (text == null || text.isEmpty()) {
            return "U+";
        }
        return "U+" + Long.toHexString(text.codePointAt(0)).toUpperCase();
    }

}
