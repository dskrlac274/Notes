package com.example.pywo.converter;

import com.example.pywo.form.NoteForm;
import com.example.pywo.model.Note;
import com.example.pywo.model.User;
import com.example.pywo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class NoteConverter {
    private final UserService userService;

    public NoteConverter(UserService userService) {
        this.userService = userService;
    }


    public Note convertToNote(NoteForm noteForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        return new Note(noteForm.getDescription(), noteForm.getTitle(), user);
    }
}
