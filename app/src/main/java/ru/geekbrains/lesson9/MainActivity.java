package ru.geekbrains.lesson9;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.geekbrains.lesson9.notes.NotesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_container);

        if (savedInstanceState == null) {
            Fragment fragment = new NotesFragment();
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment)
                .commit();
        }
    }
}
