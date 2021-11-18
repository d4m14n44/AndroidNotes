package ch.zli.m335.androidnotes.Model;

import android.app.Application;

public class Note {

    private String title;
    private String text;

    public Note(String title, String text) {
        super();
        this.title = title;
        this.text = text;
    }

    public Note() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
