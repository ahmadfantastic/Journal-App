package ahmadfantastic.com.journalapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "diary")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String owner;
    private String title;
    private String content;
    private Date date;


    public DiaryEntry(int id, String owner, String title, String content, Date date) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    @Ignore
    public DiaryEntry(String owner, String title, String content, Date date) {
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DiaryEntry{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
