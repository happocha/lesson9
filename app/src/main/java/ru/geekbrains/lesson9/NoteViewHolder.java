package ru.geekbrains.lesson9;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final MaterialTextView materialTextView;
    private final MaterialCardView materialCardView;
    private final NoteAdapterCallbacks callbacks;

    public NoteViewHolder(@NonNull View itemView, NoteAdapterCallbacks callbacks) {
        super(itemView);
        this.callbacks = callbacks;
        materialTextView = itemView.findViewById(R.id.tv_item_note);
        materialCardView = itemView.findViewById(R.id.cv_item_note);
    }

    public void onBind(int position, NoteModel model) {
        materialTextView.setText(model.getTitle());
        materialCardView.setCardBackgroundColor(
            ContextCompat.getColor(
                materialCardView.getContext(),
                model.getBackgroundColor()
            )
        );
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbacks.onItemClicked(getAdapterPosition());
            }
        });
    }
}
