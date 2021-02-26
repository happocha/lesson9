package ru.geekbrains.lesson9;

import java.io.Serializable;

public class NoteModel implements Serializable {

    private final String id;
    private final String title;
    private final String description;
    private final int backgroundColor;

    public NoteModel(String id, String title, String description, int backgroundColor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
