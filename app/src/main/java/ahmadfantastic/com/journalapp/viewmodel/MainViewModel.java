package ahmadfantastic.com.journalapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import ahmadfantastic.com.journalapp.database.AppDatabase;
import ahmadfantastic.com.journalapp.database.DiaryEntry;

public class MainViewModel extends ViewModel {

    private LiveData<List<DiaryEntry>> journal;

    public MainViewModel(AppDatabase database, String ownerEmail) {
        journal = database.diaryDao().loadJournal(ownerEmail);
        Log.e("ME ->", "Size :" + journal.getValue());
    }

    public LiveData<List<DiaryEntry>> getJournal() {
        return journal;
    }
}
