package ru.geekbrains.lesson9;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteSpaceDecorator extends RecyclerView.ItemDecoration {

    private final int defaultSpace;

    public NoteSpaceDecorator(int defaultSpace) {
        this.defaultSpace = defaultSpace;
    }

    @Override
    public void getItemOffsets(
        @NonNull Rect outRect,
        @NonNull View view,
        @NonNull RecyclerView parent,
        @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);

        if (position >= 0) {
            outRect.set(defaultSpace, defaultSpace, defaultSpace, outRect.bottom);
        }
    }
}
