package ru.webapp.storage;

import ru.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Леха
 * 11.10.2016.
 */
public class ArrayStorage implements IStorage {

    private static final int LIMIT = 100;
    private int arraySize;
    //protected Logger LOGGER = Logger.getLogger(ArrayStorage.class.getName());
    private static Logger LOGGER = Logger.getLogger(ArrayStorage.class.getName());

    private Resume[] arrayResume = new Resume[LIMIT];

    @Override
    public void clear() {
        LOGGER.info("Delete all Object in array");
        Arrays.fill(arrayResume, null);
        arraySize = 0;
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save resume with uuid = " + resume.getUuid());
        int index = getIndex(resume.getUuid());
        if (index != -1) throw new WebAppException("Resume " + resume.getUuid() + " already exist", resume );
        arrayResume[arraySize++] = resume;
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info("Upload resume with " + resume.getUuid());
        int index = getIndex(resume.getUuid());
        if (index == -1) throw new WebAppException("Resume " + resume.getUuid() + " not exist");
        arrayResume[index] = resume;
    }

    @Override
    public Resume load(String uuid) {
        LOGGER.info("Load resume with " + uuid);
        int index = getIndex(uuid);
        if (index == -1) throw new WebAppException("Resume " + uuid + " not exist");
        return  arrayResume[index];
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete resume with " + uuid);
        int index = getIndex(uuid);
        if (index == -1) throw new WebAppException("Resume " + uuid + " not exist");
        int numMoved = arraySize - index - 1;
        if (numMoved > 0)
            System.arraycopy(arrayResume, index+1, arrayResume, index,
                    numMoved);
        arrayResume[--arraySize] = null;

    }

    @Override
    public Collection<Resume> getAllSorted() {
        LOGGER.info("Sort array");
        Arrays.sort(arrayResume, 0, arraySize);
        return Arrays.asList(Arrays.copyOf(arrayResume, arraySize));
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
