package ru.webapp.model;

import java.io.Serializable;

/**
 * Леха
 * 10.10.2016.
 */
public enum SectionType implements Serializable {
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;
    static final long serialVersionUID = 1L;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
