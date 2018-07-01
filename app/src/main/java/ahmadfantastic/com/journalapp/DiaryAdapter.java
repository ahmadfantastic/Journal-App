/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ahmadfantastic.com.journalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ahmadfantastic.com.journalapp.database.DiaryEntry;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";
    private static final String TIME_FORMAT = "hh:mm a";

    private final ItemClickListener mItemClickListener;
    private List<DiaryEntry> mJournal;
    private Context mContext;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

    DiaryAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.diary_view_holder_layout, parent, false);

        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position) {
        DiaryEntry diaryEntry = mJournal.get(position);

        String title = diaryEntry.getTitle();
        String date = dateFormat.format(diaryEntry.getDate());
        String time = timeFormat.format(diaryEntry.getDate());

        holder.titleView.setText(title);
        holder.updatedAtDateView.setText(date);
        holder.updateAtTimeView.setText(time);
    }

    @Override
    public int getItemCount() {
        if (mJournal == null) {
            return 0;
        }
        return mJournal.size();
    }

    public List<DiaryEntry> getTasks() {
        return mJournal;
    }

    public void setJournal(List<DiaryEntry> journal) {
        mJournal = journal;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    //View Holder class
    class DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleView;
        TextView updatedAtDateView;
        TextView updateAtTimeView;

        private DiaryViewHolder(View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.title_text_view);
            updatedAtDateView = itemView.findViewById(R.id.date_text_view);
            updateAtTimeView = itemView.findViewById(R.id.time_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mJournal.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}