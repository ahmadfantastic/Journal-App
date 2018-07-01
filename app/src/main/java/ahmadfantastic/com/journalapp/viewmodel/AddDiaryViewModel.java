package ahmadfantastic.com.journalapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import ahmadfantastic.com.journalapp.database.AppDatabase;
import ahmadfantastic.com.journalapp.database.DiaryEntry;

public class AddDiaryViewModel extends ViewModel {

    private LiveData<DiaryEntry> diary;

    public AddDiaryViewModel(AppDatabase database, int diaryId) {
        diary = database.diaryDao().loadDiaryById(diaryId);
    }

    public LiveData<DiaryEntry> getTask() {
        return diary;
    }
}
