package ru.webapp.storage;

import ru.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * Леха
 * 25.10.2016.
 */
public class DataStreamFileStorage extends FileStorage {

    private static final String NULL = "null";

    public DataStreamFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(OutputStream os, Resume resume) throws IOException  {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            writeString(dos, resume.getUuid());
            writeString(dos, resume.getFullName());
            writeString(dos, resume.getLocation());
            writeString(dos, resume.getHomePage());
            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeInt(entry.getKey().ordinal());
                writeString(dos, entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeInt(entry.getKey().ordinal());
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                writeString(dos, type.name());
                switch (type) {
                    case OBJECTIVE:
                        writeString(dos, ((TextSection) section).getValue());
                        break;
                    case ACHIEVEMENT:
                        break;
                    case QUALIFICATION:
                        writeCollection(dos, ((MultiTextSection) section).getValues(), value -> writeString(dos, value));
                        break;
                    case EDUCATION:

                        break;
                    case EXPERIENCE:
                        writeCollection(dos, ((OrganizationSection) section).getValues(), org -> {
                            writeString(dos, org.getLink().getName());
                            writeString(dos, org.getLink().getUrl());
                            DataStreamFileStorage.this.writeCollection(dos, org.getPeriods(), period -> {
                                DataStreamFileStorage.this.writeLocalDate(dos, period.getStartDate());
                                DataStreamFileStorage.this.writeLocalDate(dos, period.getEndDate());
                                writeString(dos, period.getPosition());
                                writeString(dos, period.getContent());
                            });
                        });
                        break;
                }
            }

        }
    }




    @Override
    protected Resume read(InputStream is) throws IOException {
        Resume r = new Resume();
        try (DataInputStream dis = new DataInputStream(is)) {
            r.setUuid(readString(dis));
            r.setFullName(readString(dis));
            r.setLocation(readString(dis));
            r.setHomePage(readString(dis));
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++)
                r.addContact(ContactType.VALUES[dis.readInt()], readString(dis));
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(readString(dis));
                switch (sectionType) {
                    case OBJECTIVE:
                        r.addObjective(readString(dis));
                        break;
                    case ACHIEVEMENT:

                        break;
                    case QUALIFICATION:
                        r.addSection(sectionType, new MultiTextSection(readList(dis, () -> readString(dis))));
                        break;
                    case EDUCATION:

                        break;
                    case EXPERIENCE:
                        r.addSection(sectionType,
                                new OrganizationSection(readList(dis, () -> new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                        readList(dis, () -> new Organization.Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))))));
                        break;
                }
        }
            return r;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        Objects.requireNonNull(ld, "LocalDate cannot be null, use Period.NOW");
        dos.writeInt(ld.getYear());
        dos.writeUTF(ld.getMonth().name());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), Month.valueOf(dis.readUTF()), 1);
    }

    private void writeString(DataOutputStream dos, String string) throws IOException {
        dos.writeUTF(string == null ? NULL : string);
     }

     private String readString(DataInputStream dis) throws IOException {
         String string = dis.readUTF();
         return string.equals(null) ? NULL : string;
     }

     private interface ElementWriter<T> {
         void write(T t) throws IOException;
     }

     private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
         dos.writeInt(collection.size());
         for (T item : collection) {
             writer.write(item);
         }
     }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }
}
