package com.androidmanifester.developers.examdemo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiAppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import com.androidmanifester.developers.examdemo.add.AddViewModel;
import com.androidmanifester.developers.examdemo.add.AddViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.developers.examdemo.R;

/**
 * Saves a Smiley to local data source and shows a hex value of character.
 */
public class AddSmileyActivity extends AppCompatActivity implements TextWatcher {

    private AddViewModel mViewModel;
    private EmojiAppCompatEditText mEmojiEditText;
    private TextInputEditText mName;
    TextView mUnicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddViewModelFactory viewModelFactory = AddViewModelFactory.createFactory(this);
        mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AddViewModel.class);

        EmojiCompat.init(new BundledEmojiCompatConfig(this));
        setContentView(R.layout.activity_add_smiley);

        mEmojiEditText = findViewById(R.id.emoji_char);
        mEmojiEditText.addTextChangedListener(this);
        mUnicode = findViewById(R.id.emoji_unicode);
        mName = findViewById(R.id.emoji_name);

        Button mSave = findViewById(R.id.save);
        mSave.setOnClickListener(this::saveOnClick);

        mViewModel.getUnicode().observe(this,s -> mUnicode.setText(s));
    }

    public void saveOnClick(View view) {
        String emoji = mEmojiEditText.getText().toString().trim();
        String name = mName.getText().toString();
        if (emoji.length() == 0) {
            mEmojiEditText.setError(getString(R.string.missing_input));
            return;
        }
        mViewModel.save(emoji,name);
        finish();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        mViewModel.emojiChanged(text);
    }

    /**
     * No action needed.
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * No action needed.
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
