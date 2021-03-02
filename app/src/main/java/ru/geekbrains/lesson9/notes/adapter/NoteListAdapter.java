package ru.geekbrains.lesson9.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.geekbrains.lesson9.model.NoteModel;
import ru.geekbrains.lesson9.R;

public class NoteListAdapter extends ListAdapter<NoteModel, NoteViewHolder> {

    private final NoteAdapterCallbacks callbacks;

    public NoteListAdapter(
        @NonNull DiffUtil.ItemCallback<NoteModel> diffCallback,
        @NonNull NoteAdapterCallbacks callbacks) {
        super(diffCallback);
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, callbacks);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.onBind(position, getItem(position));
    }
}
