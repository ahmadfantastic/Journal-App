package ahmadfantastic.com.journalapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ahmadfantastic.com.journalapp.AppExecutors;
import ahmadfantastic.com.journalapp.database.AppDatabase;
import ahmadfantastic.com.journalapp.database.DiaryEntry;

public class MainViewModel extends ViewModel {

    private LiveData<List<DiaryEntry>> journal;
    private Bitmap image;

    MainViewModel(AppDatabase database, String ownerEmail,final Uri uri) {
        journal = database.diaryDao().loadJournal(ownerEmail);
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    image = BitmapFactory.decodeStream(new URL(uri.toString()).openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<List<DiaryEntry>> getJournal() {
        return journal;
    }

    public Bitmap getImage(){ return image; }
}
