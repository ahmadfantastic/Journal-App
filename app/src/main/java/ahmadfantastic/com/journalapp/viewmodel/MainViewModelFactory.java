package ahmadfantastic.com.journalapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import ahmadfantastic.com.journalapp.database.AppDatabase;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final String mOwnerEmail;

    public MainViewModelFactory(AppDatabase database, String ownerEmail) {
        mDb = database;
        mOwnerEmail = ownerEmail;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainViewModel(mDb, mOwnerEmail);
    }
}
