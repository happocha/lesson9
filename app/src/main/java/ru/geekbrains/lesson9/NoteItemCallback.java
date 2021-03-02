package ru.geekbrains.lesson9;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class NoteItemCallback extends DiffUtil.ItemCallback<NoteModel> {

    @Override
    public boolean areItemsTheSame(@NonNull NoteModel oldItem, @NonNull NoteModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull NoteModel oldItem, @NonNull NoteModel newItem) {
        return oldItem.getTitle().equals(newItem.getTitle()) &&
            oldItem.getDescription().equals(newItem.getDescription());
    }
}
