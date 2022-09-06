package com.example.pywo.service;

import com.example.pywo.form.NoteForm;
import com.itextpdf.text.Document;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    void writeData(long id) throws DocumentException;
    void export(long id, HttpServletResponse response) throws IOException, DocumentException;
    void get(long id, HttpServletResponse response) throws IOException, DocumentException;
    void initializeWorkspace();
    Document sendDocument(long id);
}
