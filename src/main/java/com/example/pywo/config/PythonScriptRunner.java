package com.example.pywo.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonScriptRunner implements Runnable {
    private String thread;

    public PythonScriptRunner(String thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder ps = new ProcessBuilder("python", "src/main/resources/PyWo.py", thread);
            ps.redirectErrorStream(true);
            Process pr = ps.start();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
