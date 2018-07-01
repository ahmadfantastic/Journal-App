package ahmadfantastic.com.journalapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ahmadfantastic.com.journalapp.database.AppDatabase;

public class AddDiaryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mDiaryId;

    public AddDiaryViewModelFactory(AppDatabase database, int diaryId) {
        mDb = database;
        mDiaryId = diaryId;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddDiaryViewModel(mDb, mDiaryId);
    }
}
