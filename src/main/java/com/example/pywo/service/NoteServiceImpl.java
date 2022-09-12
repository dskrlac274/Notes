package com.example.pywo.service;

import com.example.pywo.converter.NoteConverter;
import com.example.pywo.exception.IdDoesNotExist;
import com.example.pywo.form.NoteForm;
import com.example.pywo.model.Note;
import com.example.pywo.model.User;
import com.example.pywo.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final NoteConverter noteConverter;

    public NoteServiceImpl(NoteRepository noteRepository, NoteConverter noteConverter) {
        this.noteRepository = noteRepository;
        this.noteConverter = noteConverter;
    }
    @Override
    public Note addNote(NoteForm noteForm) {
        Note note = this.noteConverter.convertToNote(noteForm);
        return noteRepository.save(note);
    }
    @Override
    public Note findNoteById(long id) {
        return noteRepository.findById(id).orElseThrow(
                ()-> new IdDoesNotExist("Note ID does not exist"));
    }
    @Override
    public List<Note> getAllNotesOfCurrentUser(User user) {
        return noteRepository.findByUser(user);
    }

    @Override
    public Note getNoteOfCurrentUser(User user, long id) {
        return noteRepository.findByUserAndId(user,id).orElseThrow(
                ()->new IdDoesNotExist("Id does not exist")
        );
    }

    @Override
    public Note updateNote(long id, Note noteToUpdate) {
        System.out.println("Vrijednosti koje mapiram" + " " + noteToUpdate.toString());
        Note note = noteRepository.findById(id).map(
                nte->{
                    nte.mapFrom(noteToUpdate);
                    return noteRepository.save(nte);
                }).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));
        System.out.println("Updated note je:" + " " + note);
        return note;
    }

}
