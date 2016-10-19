package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Леха
 * 11.10.2016.
 */
public class ArrayStorage extends AbstractStorage {

    private static final int LIMIT = 100;
    private int arraySize;

    private Resume[] arrayResume = new Resume[LIMIT];

    @Override
    protected void doClear() {
        Arrays.fill(arrayResume, null);
        arraySize = 0;
    }

    @Override
    protected boolean exist(String uuid) {
        return getIndex(uuid) != -1;
    }

    @Override
    protected void doSave(Resume resume) {
        arrayResume[arraySize++] = resume;
    }

    @Override
    protected void doUpdate(Resume resume) {
        int index = getIndex(resume.getUuid());
        arrayResume[index] = resume;
    }


    @Override
    protected Resume doLoad(String uuid) {
        int index = getIndex(uuid);
        return  arrayResume[index];
    }

    @Override
    protected void doDelete(String uuid) {

        int index = getIndex(uuid);
        int numMoved = arraySize - index - 1;
        if (numMoved > 0)
            System.arraycopy(arrayResume, index+1, arrayResume, index,
                    numMoved);
        arrayResume[--arraySize] = null;

    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(arrayResume, arraySize));
    }

    @Override
    public int size() {
        return arraySize;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < LIMIT; i++) {
            if (arrayResume[i] != null) {
                if (arrayResume[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }return -1;
    }
}
