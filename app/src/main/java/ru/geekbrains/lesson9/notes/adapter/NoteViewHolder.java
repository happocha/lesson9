package ru.geekbrains.lesson9.notes.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import ru.geekbrains.lesson9.R;
import ru.geekbrains.lesson9.model.NoteModel;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final MaterialTextView materialTextView;
    private final NoteAdapterCallbacks callbacks;

    public NoteViewHolder(@NonNull View itemView, NoteAdapterCallbacks callbacks) {
        super(itemView);
        this.callbacks = callbacks;
        materialTextView = itemView.findViewById(R.id.tv_item_note);
    }

    public void onBind(int position, NoteModel model) {
        materialTextView.setText(model.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onItemClicked(getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callbacks.onLongItemClicked(getAdapterPosition());
                return true;
            }
        });
    }
}
