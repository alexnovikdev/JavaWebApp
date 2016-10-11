package ru.webapp.model;

/**
 * Леха
 * 07.10.2016.
 */
public class Link {
    private final String name;
    private final String url;

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Link(Link linkclone) {
        this.name = linkclone.name;
        this.url = linkclone.url;
    }
}
