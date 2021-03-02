package ru.geekbrains.lesson9.notes.adapter;

public interface NoteAdapterCallbacks {

    void onItemClicked(int position);
    void onLongItemClicked(int position);
}
