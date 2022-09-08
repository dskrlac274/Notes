package com.example.pywo.service;

import com.example.pywo.form.NoteForm;
import com.example.pywo.model.Note;
import com.example.pywo.model.User;

import java.util.List;

public interface NoteService {
    Note addNote(NoteForm noteForm);
    Note findNoteById(long id);
    List<Note> getAllNotesOfCurrentUser(User user);
    Note getNoteOfCurrentUser(User user, long id);
    //Note deleteNote(Note note);
}
