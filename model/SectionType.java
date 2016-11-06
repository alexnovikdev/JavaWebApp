package ru.webapp.model;

import ru.webapp.webpackage.SectionHtmlType;

import java.io.Serializable;

/**
 * Леха
 * 10.10.2016.
 */
public enum SectionType implements Serializable {
    OBJECTIVE("Позиция", SectionHtmlType.TEXT),
    ACHIEVEMENT("Достижения", SectionHtmlType.MULTI_TEXT),
    QUALIFICATION("Квалификация", SectionHtmlType.MULTI_TEXT),
    EXPERIENCE("Опыт работы", SectionHtmlType.ORGANIZATION),
    EDUCATION("Образование", SectionHtmlType.ORGANIZATION);

    private String title;
    private SectionHtmlType htmlType;
    static final long serialVersionUID = 1L;

    SectionType(String title, SectionHtmlType htmlType) {
        this.title = title;
        this.htmlType = htmlType;
    }

    public SectionHtmlType getHtmlType() {
        return htmlType;
    }

    public String getTitle(){
        return title;
    }
}
