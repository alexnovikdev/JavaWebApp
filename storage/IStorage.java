package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.Collection;

/**
 * Леха
 * 11.10.2016.
 */
public interface IStorage {
    void clear();
    void save(Resume resume);
    void update(Resume resume);
    Resume load(String uuid);
    void delete(String uuid);
    Collection<Resume> getAllSorted();
    int size();
}
