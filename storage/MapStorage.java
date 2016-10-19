package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.*;
import java.util.logging.Logger;

/**
 * Леха
 * 17.10.2016.
 */
public class MapStorage extends AbstractStorage {

    private Map<String, Resume> map = new HashMap<>();


    @Override
    protected void doClear() {
        map.clear();
    }

    @Override
    protected boolean exist(String uuid) {
        return map.containsKey(uuid);
    }

    @Override
    protected void doSave(Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doLoad(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
