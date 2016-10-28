package ru.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Леха
 * 11.10.2016.
 */
public class OrganizationSection extends Section implements Serializable{
    static final long serialVersionUID = 1L;
    private List<Organization> values;

    public OrganizationSection(Organization... values) {
        this.values = new LinkedList<>(Arrays.asList(values));
    }
}
