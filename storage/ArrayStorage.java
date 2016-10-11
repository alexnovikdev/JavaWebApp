package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.Collection;

/**
 * Леха
 * 11.10.2016.
 */
public class ArrayStorage implements IStorage {

    private Resume[] resume = new Resume[100];

    @Override
    public void clear() {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public Resume load(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Collection<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
