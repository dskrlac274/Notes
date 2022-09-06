package com.example.pywo.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Setter
@Getter
public class NoteForm {
    @NotNull
    private String title;
    @NotNull
    private String description;
}
