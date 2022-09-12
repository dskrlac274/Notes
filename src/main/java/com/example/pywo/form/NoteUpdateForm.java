package com.example.pywo.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class NoteUpdateForm {

    private Long idToUpdate;

    private String title;

    private String description;

}
