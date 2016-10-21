package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.*;
import java.util.logging.Logger;

/**
 * Леха
 * 17.10.2016.
 */
public abstract class AbstractStorage<C> implements IStorage {

    protected final Logger logger = Logger.getLogger(ArrayStorage.class.getName());

    @Override
    public void clear() {
        logger.info("Delete all resumes");
        doClear();
    }

    protected abstract void doClear();

    protected abstract C getContext(String uuid);

    protected abstract boolean exist(C ctx);

    @Override
    public void save(Resume resume) {
        logger.info("Save resume with uuid = " + resume.getUuid());
        C ctx = getContext(resume);
        if (exist(ctx)) throw new WebAppException("Resume " + resume.getUuid() + " already exists", resume );
        doSave(ctx, resume);
    }

    protected abstract void doSave(C ctx, Resume resume);

    @Override
    public void update(Resume resume) {
        logger.info("Upload resume with " + resume.getUuid());
        C ctx = getContext(resume);
        if (!exist(ctx)) throw new WebAppException("Resume " + resume.getUuid() + "  not exists", resume );
        doUpdate(ctx, resume);
    }

    protected abstract void doUpdate(C ctx, Resume resume);



    @Override
    public Resume load(String uuid) {
        logger.info("Load resume with " + uuid);
        C ctx = getContext(uuid);
        if (!exist(ctx)) throw new WebAppException("Resume " + uuid + "  not exists");
        return doLoad(ctx, uuid);
    }

    protected abstract Resume doLoad(C ctx, String uuid);

    @Override
    public void delete(String uuid) {
        logger.info("Delete resume with " + uuid);
        C ctx = getContext(uuid);
        if (!exist(ctx)) throw new WebAppException("Resume " + uuid + "  not exists");
        doDelete(ctx, uuid);
    }

    protected abstract void doDelete(C ctx, String uuid);

    @Override
    public Collection<Resume> getAllSorted() {
        logger.info("Sort collection");
        List<Resume> list = doGetAll();
        Collections.sort(list, (Resume o1, Resume o2) -> {
                int cmp = o1.getFullName().compareTo(o2.getFullName());
                if (cmp != 0) return cmp;
                return o1.getUuid().compareTo(o2.getUuid());
        });
        return list;
    }

    protected abstract List<Resume> doGetAll();

    @Override
    public abstract int size();

    private C getContext(Resume resume) {
        return getContext(resume.getUuid());
    }

}
