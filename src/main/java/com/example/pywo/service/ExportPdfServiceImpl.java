package com.example.pywo.service;

import com.example.pywo.exception.IdDoesNotExist;
import com.example.pywo.model.Note;
import com.example.pywo.repository.NoteRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class ExportPdfServiceImpl implements ExportService{
    @Autowired
    private NoteRepository noteRepository;
    Document document = new Document();
    @Override
    public void initializeWorkspace() {
        document = new Document();
    }

    @SneakyThrows
    @Override
    public Document sendDocument(long id) {
        document.open();

        writeData(id);
        document.close();
        return document;
    }

    @SneakyThrows
    @Override
    public void writeData(long id) throws DocumentException {
        Note noteForm = noteRepository.findById(id).orElseThrow(
                ()->new IdDoesNotExist("Id does not exist"));

        Font fontTitle = FontFactory.getFont(FontFactory.COURIER_BOLD, 24, BaseColor.BLACK);
        Font fontDescription = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);


        Chunk title = new Chunk(noteForm.getTitle() + " ", fontTitle);
        Chunk description = new Chunk(noteForm.getDescription(), fontDescription);
        document.add(title);
        document.add(description);

    }

    @SneakyThrows
    @Override
    public void export(long id, HttpServletResponse response) throws IOException, DocumentException {
        initializeWorkspace();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();

        writeData(id);
        document.close();

        response.setContentType("application/pdf");
        response.setContentLength(baos.size());

        OutputStream os = response.getOutputStream();
        /*OutputStream outputStreamBuffer = new FileOutputStream("Note.pdf");
        baos.writeTo(outputStreamBuffer);*/
        baos.writeTo(os);
        os.flush();
        os.close();
        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        writeHeader();
        writeData();
        document.close();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",  "inline");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentLength(baos.size());

        outputStream.close();
        document.close();
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
        // if you also want to write these bytes to a file, add:
        OutputStream outputStreamBuffer = new FileOutputStream("Note.pdf");
        baos.writeTo(outputStreamBuffer);
        outputStreamBuffer.flush();
        outputStreamBuffer.close();*/
    }

    @SneakyThrows
    @Override
    public void get(long id, HttpServletResponse response) throws IOException, DocumentException {
        initializeWorkspace();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();

        writeData(id);
        document.close();

       /* response.setContentType("application/pdf");
        response.setContentLength(baos.size());*/

        OutputStream outputStreamBuffer = new FileOutputStream("Note.pdf");

        baos.writeTo(outputStreamBuffer);
        outputStreamBuffer.flush();
        outputStreamBuffer.close();
    }
}
