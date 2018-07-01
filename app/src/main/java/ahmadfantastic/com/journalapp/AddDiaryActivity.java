package ahmadfantastic.com.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

import ahmadfantastic.com.journalapp.database.AppDatabase;
import ahmadfantastic.com.journalapp.database.DiaryEntry;
import ahmadfantastic.com.journalapp.viewmodel.AddDiaryViewModel;
import ahmadfantastic.com.journalapp.viewmodel.AddDiaryViewModelFactory;

public class AddDiaryActivity extends AppCompatActivity {

    public static final String EXTRA_DIARY_ID = "extra_diary_id";
    private final String INSTANCE_DIARY_ID = "instance_diary_id";
    private final int DEFAULT_DIARY_ID = -1;

    private GoogleSignInClient mGoogleSignInClient;
    private AppDatabase mDb;

    private int mDiaryId = DEFAULT_DIARY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        final EditText titleField = findViewById(R.id.title_text_field);
        final EditText contentField = findViewById(R.id.content_text_field);
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDiary(titleField.getText().toString(), contentField.getText().toString(), account.getEmail());
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_DIARY_ID)) {
             mDiaryId = savedInstanceState.getInt(INSTANCE_DIARY_ID, DEFAULT_DIARY_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DIARY_ID)) {
            addButton.setText("Update");
            if (mDiaryId == DEFAULT_DIARY_ID) {
                mDiaryId = intent.getIntExtra(EXTRA_DIARY_ID, DEFAULT_DIARY_ID);

                AddDiaryViewModelFactory factory = new AddDiaryViewModelFactory(mDb, mDiaryId);
                final AddDiaryViewModel viewModel  =
                        ViewModelProviders.of(this, factory).get(AddDiaryViewModel.class);

                viewModel.getTask().observe(this, new Observer<DiaryEntry>() {
                    @Override
                    public void onChanged(@Nullable DiaryEntry diaryEntry) {
                        viewModel.getTask().removeObserver(this);
                        if (diaryEntry == null) return;

                        titleField.setText(diaryEntry.getTitle());
                        contentField.setText(diaryEntry.getContent());
                    }
                });
            }
        }
    }

    private void addDiary(String title, String content, String owner){
        Date date = new Date();
        final DiaryEntry diaryEntry = new DiaryEntry(owner, title, content, date);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mDiaryId == DEFAULT_DIARY_ID){
                    mDb.diaryDao().insertDiary(diaryEntry);
                }else{
                    diaryEntry.setId(mDiaryId);
                    mDb.diaryDao().updateDiary(diaryEntry);
                }
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_DIARY_ID, mDiaryId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_menu_item){
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(AddDiaryActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            return true;
        }else if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
