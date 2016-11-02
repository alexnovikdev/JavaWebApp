package ru.webapp.storage;

import ru.webapp.model.Resume;
import ru.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Леха
 * 31.10.2016.
 */
public class JsonFileStorage extends FileStorage {

    public JsonFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(OutputStream os, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            JsonParser.write(resume, writer);
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            JsonParser.read(reader, Resume.class);
        }
        return null;
    }

    @Override
    public boolean isSectionSupported() {
        return false;
    }
}
