package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Леха
 * 17.10.2016.
 */
public abstract class AbstractStorage implements IStorage {

    protected final Logger logger = Logger.getLogger(ArrayStorage.class.getName());

    @Override
    public void clear() {
        logger.info("Delete all resumes");
        doClear();
    }

    protected abstract void doClear();

    protected abstract boolean exist(String uuid);

    @Override
    public void save(Resume resume) {
        logger.info("Save resume with uuid = " + resume.getUuid());
        if (exist(resume.getUuid())) throw new WebAppException("Resume " + resume.getUuid() + " already exist", resume );
        doSave(resume);
    }

    protected abstract void doSave(Resume resume);

    @Override
    public void update(Resume resume) {
        logger.info("Upload resume with " + resume.getUuid());
        if (exist(resume.getUuid())) throw new WebAppException("Resume " + resume.getUuid() + " not exist", resume );
        doUpdate(resume);
    }

    protected abstract void doUpdate(Resume resume);



    @Override
    public Resume load(String uuid) {
        logger.info("Load resume with " + uuid);
        if (exist(uuid)) throw new WebAppException("Resume " + uuid + " not exist", uuid );
        return doLoad(uuid);
    }

    protected abstract Resume doLoad(String uuid);

    @Override
    public void delete(String uuid) {
        logger.info("Delete resume with " + uuid);
        if (exist(uuid)) throw new WebAppException("Resume " + uuid + " not exist", uuid );
        doDelete(uuid);
    }

    protected abstract void doDelete(String uuid);

    @Override
    public Collection<Resume> getAllSorted() {
        logger.info("Sort collection");
        List<Resume> list = doGetAll();
        Collections.sort(list);
        return list;
    }

    protected abstract List<Resume> doGetAll();

    @Override
    public abstract int size();

}
