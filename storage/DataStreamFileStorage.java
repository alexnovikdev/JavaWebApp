package ru.webapp.storage;

import ru.webapp.model.ContactType;
import ru.webapp.model.Resume;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Леха
 * 25.10.2016.
 */
public class DataStreamFileStorage extends FileStorage {

    public DataStreamFileStorage(String path) {
        super(path);
    }

    protected void write(File file, Resume resume) {
        try (FileOutputStream fos = new FileOutputStream(file); DataOutputStream dos = new DataOutputStream(fos)) {
            // TODO fix NullPointException
            dos.writeUTF(resume.getFullName());
            dos.writeUTF(resume.getLocation());
            dos.writeUTF(resume.getHomePage());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeInt(entry.getKey().ordinal());
                dos.writeUTF(entry.getValue());
            }
            //TODO sections
        } catch (IOException e) {
            throw new WebAppException("Couldn't create file " + file.getAbsolutePath(), resume, e);
        }
    }

    protected Resume read(File file) {
        Resume r = new Resume();
        try (InputStream is = new FileInputStream(file); DataInputStream dis = new DataInputStream(is)) {
            r.setFullName(dis.readUTF());
            r.setLocation(dis.readUTF());
            r.setHomePage(dis.readUTF());
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++)
                r.addContact(ContactType.VALUES[dis.readInt()], dis.readUTF());
            //TODO sections
        } catch (IOException e) {
            throw new WebAppException("Couldn't read file " + file.getAbsolutePath(), e);
        }
        return null;
    }
}
