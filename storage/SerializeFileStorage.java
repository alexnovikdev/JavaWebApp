package ru.webapp.storage;

import ru.webapp.model.ContactType;
import ru.webapp.model.Resume;

import java.io.*;
import java.util.Map;

/**
 * Леха
 * 25.10.2016.
 */
public class SerializeFileStorage extends FileStorage {

    public SerializeFileStorage(String path) {
        super(path);
    }

    protected void write(File file, Resume resume) {
        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new WebAppException("Couldn't create file " + file.getAbsolutePath(), resume, e);
        }
    }

    protected Resume read(File file) {
        Resume r = new Resume();
        try (InputStream is = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (IOException e) {
            throw new WebAppException("Couldn't read file " + file.getAbsolutePath(), e);
        } catch (ClassNotFoundException e) {
            throw new WebAppException("Error read resume", e);
        }
    }
}
