package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Леха
 * 11.10.2016.
 */
public class ArrayStorage implements IStorage {

    private static final int LIMIT = 100;
    private int index;

    private Resume[] arrayResume = new Resume[LIMIT];

    @Override
    public void clear() {
        for (int i = 0; i < LIMIT; i++) {
            arrayResume[i] = null;
        }
    }

    @Override
    public void save(Resume resume) {
        for (int i = 0; i < LIMIT; i++) {
            if (arrayResume[i] == null)
                arrayResume[i] = resume;
        }
    }

    @Override
    public void update(Resume resume) {
        for (int i = 0; i < LIMIT; i++) {
            if (arrayResume[i].getUuid() == resume.getUuid())
                arrayResume[i] = resume;
        }
    }

    @Override
    public Resume load(String uuid) {
        Resume found = null;
        for (int i = 0; i < LIMIT; i++) {
            if (arrayResume[i].getUuid() == uuid)
                found = arrayResume[i];
        }
        return found;
    }

    @Override
    public void delete(String uuid) {
        for (int i = 0; i < LIMIT; i++) {
            if (arrayResume[i].getUuid() == uuid)
                arrayResume[i] = null;
        }
    }

    @Override
    public Collection<Resume> getAllSorted() {
        Arrays.sort(arrayResume);
        List<Resume> list = new LinkedList<>();
        for (int i = 0; i < LIMIT; i++) {
            if (arrayResume[i] != null)
                list.add(arrayResume[i]);
        }
        return list;
    }

    @Override
    public int size() {
        int count = 0;
        for (int i =0; i < LIMIT; i++) {
            if (arrayResume[i] != null)
                count++;
        }
        return count;
    }
}
