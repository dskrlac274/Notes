package com.example.pywo.controller;

import com.example.pywo.converter.NoteConverter;
import com.example.pywo.form.NoteForm;
import com.example.pywo.model.User;
import com.example.pywo.service.ExportService;
import com.example.pywo.service.NoteService;
import com.example.pywo.service.UserService;
import com.itextpdf.text.Document;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hpsf.Blob;
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
import java.util.Base64;

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
    @PostMapping(value = "/pdf", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getPdf(long id, HttpServletResponse response) throws IOException, DocumentException {
        exportService.get(id,response);

        String filePath = "/home/daniel/Desktop/PyWo/demo/";
        String fileName = "Note.pdf";
        File file = new File(filePath+fileName);
        System.out.println("FILE JE:" + file);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);
        responseHeaders.add("Content-Transfer-Encoding", "binary");
        responseHeaders.add("Content-Description", "File-Transfer");
        responseHeaders.add("Content-Type",  "application/pdf; charset=utf-8");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        System.out.println("Source stream je:" + " " + resource);
        //byte[] sourceBytes = IOUtils.toByteArray(sourceStream);
        //System.out.println("SOurceBytes je:" + " " + sourceBytes);
        //String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
        //System.out.println("Encoded string je:" + " " + encodedString);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

}
