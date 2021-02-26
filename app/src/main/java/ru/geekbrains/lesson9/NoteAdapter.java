package ru.geekbrains.lesson9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final List<NoteModel> items = new ArrayList<>();
    private final NoteAdapterCallbacks callbacks;

    public NoteAdapter(NoteAdapterCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void setItems(List<NoteModel> noteModelList) {
        items.clear();
        items.addAll(noteModelList);
        notifyDataSetChanged();
    }

    private void removeItem(int position) {
        //todo
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, callbacks);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.onBind(position, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
