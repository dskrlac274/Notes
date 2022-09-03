package com.example.pywo.service;

import com.example.pywo.model.Note;
import com.example.pywo.repository.NoteRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note addNote(Note note) {
        if(noteRepository.existsById(note.getId())){
            //throw new IdAlreadyExistsException("Id already exists!");
            System.out.println("Id already exist");
        }
        return noteRepository.save(note);
    }

}
