package com.androidmanifester.developers.examdemo.paging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiAppCompatTextView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidmanifester.developers.examdemo.data.Smiley;
import com.google.developers.examdemo.R;

public class SmileyAdapter extends PagedListAdapter<Smiley, SmileyAdapter.SmileyViewHolder> {

    public SmileyAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SmileyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_smiley, parent, false);
        return new SmileyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmileyViewHolder holder, int position) {
        Smiley item = getItem(position);
        holder.bindTo(item);
    }



    public class SmileyViewHolder extends RecyclerView.ViewHolder {

        private EmojiAppCompatTextView mEmoji;
        private TextView mName;
        private TextView mUnicode;
        private TextView Random_txt;

        private Smiley mSmiley;

        SmileyViewHolder(View itemView) {
            super(itemView);
            mEmoji = itemView.findViewById(R.id.txtEmoji);
            mName = itemView.findViewById(R.id.txtEmojiName);
            mUnicode = itemView.findViewById(R.id.txtEmojiUnicode);
            Random_txt=itemView.findViewById(R.id.txtRandomdata);
        }

        public Smiley getSmiley() {
            return mSmiley;
        }



        void bindTo(Smiley smiley) {
            mSmiley = smiley;
            mEmoji.setText(smiley.getEmoji());
            mName.setText(smiley.getName());
            mUnicode.setText(smiley.getCode());
            Random_txt.setText(smiley.getRandomData());
        }
    }

    private static final DiffUtil.ItemCallback<Smiley> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Smiley>() {
                @Override
                public boolean areItemsTheSame(@NonNull Smiley oldItem, @NonNull Smiley newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Smiley oldItem,
                                                  @NonNull Smiley newItem) {
                    return oldItem == newItem;
                }
            };
}