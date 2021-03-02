package ru.geekbrains.lesson9.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.geekbrains.lesson9.model.NoteModel;

public interface NotesFirestoreCallbacks {

    void onSuccessNotes(@NonNull List<NoteModel> items);
    void onErrorNotes(@Nullable String message);
}
