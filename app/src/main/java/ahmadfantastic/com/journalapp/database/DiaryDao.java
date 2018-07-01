package ahmadfantastic.com.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DiaryDao {
    @Query("SELECT * FROM diary WHERE owner = :ownerEmail ORDER BY id")
    LiveData<List<DiaryEntry>> loadJournal(String ownerEmail);

    @Insert
    void insertDiary(DiaryEntry diaryEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDiary(DiaryEntry diaryEntry);

    @Delete
    void deleteDiary(DiaryEntry diaryEntry);

    @Query("SELECT * FROM diary WHERE id = :id")
    LiveData<DiaryEntry> loadDiaryById(int id);
}
