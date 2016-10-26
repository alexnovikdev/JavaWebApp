package ru.webapp.model;

import java.io.Serializable;

/**
 * Леха
 * 07.10.2016.
 */
public class Link implements Serializable {
    static final long serialVersionUID = 1L;
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
