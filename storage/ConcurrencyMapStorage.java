package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Леха
 * 02.11.2016.
 */
public class ConcurrencyMapStorage extends AbstractStorage<String> {

    private Map<String, Resume> map = new ConcurrentHashMap<>();

    @Override
    protected String getContext(String uuid) {
        return uuid;
    }

    @Override
    protected boolean exist(String uuid) {
        return map.containsKey(uuid);
    }

    @Override
    protected void doClear() {
        map.clear();
    }

    @Override
    protected void doSave(String uuid, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(String uuid, Resume resume) {
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
