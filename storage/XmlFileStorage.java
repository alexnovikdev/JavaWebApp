package ru.webapp.storage;

import ru.webapp.model.*;
import ru.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Леха
 * 30.10.2016.
 */
public class XmlFileStorage extends FileStorage {
    private XmlParser xmlParser;

    public XmlFileStorage(String path) {
        super(path);
        xmlParser = new XmlParser(Resume.class, Organization.class, OrganizationSection.class,
                Organization.Period.class, TextSection.class, Link.class, MultiTextSection.class);
    }

    @Override
    protected void write(OutputStream os, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
