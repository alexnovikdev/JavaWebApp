package ru.webapp;

import ru.webapp.storage.IStorage;
import ru.webapp.storage.XmlFileStorage;

import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * Леха
 * 05.11.2016.
 */
public class WebAppConfig {
    private static final WebAppConfig INSTANCE = new WebAppConfig();
    private IStorage storage;

    public static WebAppConfig get() {
        return INSTANCE;
    }

    public IStorage getStorage() {
        return storage;
    }

    private WebAppConfig() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
            storage = new XmlFileStorage("E:\\JavaProjects\\JavaWebApp\\file_storage");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
