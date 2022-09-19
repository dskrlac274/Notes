package com.example.pywo.controller;

import com.example.pywo.converter.NoteConverter;
import com.example.pywo.form.NoteForm;
import com.example.pywo.form.NoteUpdateForm;
import com.example.pywo.model.User;
import com.example.pywo.service.ExportService;
import com.example.pywo.service.NoteService;
import com.example.pywo.service.UserService;
import lombok.SneakyThrows;
import org.dom4j.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class NoteController {
    private final NoteService noteService;
    private final NoteConverter noteConverter;
    private final UserService userService;
    private final ExportService exportService;

    public NoteController(NoteService noteService, NoteConverter noteConverter, UserService userService, ExportService exportService) {
        this.noteService = noteService;
        this.noteConverter = noteConverter;
        this.userService = userService;
        this.exportService = exportService;
    }

    @PostMapping(value = "/addNote", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> addNote(@Valid @ModelAttribute("userAddNoteFormData") NoteForm noteForm) {
        return new ResponseEntity<>(noteService.addNote(noteForm), HttpStatus.CREATED);
    }
    @GetMapping(value = "/notes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        return new ResponseEntity<>(noteService.getAllNotesOfCurrentUser(user), HttpStatus.OK);
    }
    @PostMapping(value = "/note", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getSpecificNote(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        return new ResponseEntity<>(noteService.getNoteOfCurrentUser(user,id), HttpStatus.OK);
    }
    @SneakyThrows
    @PutMapping(value = "/note", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updateSpecificNote(@ModelAttribute NoteUpdateForm noteUpdateForm) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());



        return new ResponseEntity<>(noteService.updateNote(noteUpdateForm.getIdToUpdate(), noteConverter.ConvertUpdateNoteFormToNote(noteUpdateForm)), HttpStatus.OK);
    }


    @PostMapping(value = "/pdf", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getPdf(long id, HttpServletResponse response) throws IOException, DocumentException {
        exportService.get(id,response);

        Path path = Paths.get("Note.pdf");
        String fileName = "Note.pdf";
        File file = new File(String.valueOf(path.toAbsolutePath()));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);
        responseHeaders.add("Content-Transfer-Encoding", "binary");
        responseHeaders.add("Content-Description", "File-Transfer");
        responseHeaders.add("Content-Type",  "application/pdf; charset=utf-8");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        System.out.println("Source stream je:" + " " + resource);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }
    @SneakyThrows
    @GetMapping(value = "/python", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> pythonRunner()  {
        /*String command = "python3" + " " + resolvePythonScriptPath("PyWo.py");
        System.out.println(command);*/
        Process p = Runtime.getRuntime().exec("python3 /home/daniel/Desktop/PyWo/demo/src/main/resources/static/js/PyWo.py");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("Python script started");
    }

}
