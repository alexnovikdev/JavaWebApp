package ru.webapp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Леха
 * 07.10.2016.
 */
public class Link implements Serializable {
    static final long serialVersionUID = 1L;

    private final String name;
    private final String url;
    public static Link EMPTY = new Link();

    public Link() {
        this("", "");
    }

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name is null");
        this.name = name;
        this.url = url == null ? "" : url;
    }

    public Link(Link linkclone) {
        this.name = linkclone.name;
        this.url = linkclone.url;
    }

    public static Link empty() {
        return EMPTY;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (name != null ? !name.equals(link.name) : link.name != null) return false;
        return url != null ? url.equals(link.url) : link.url == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
