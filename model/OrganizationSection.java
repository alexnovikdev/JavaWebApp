package ru.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
    private List<Organization> values = new LinkedList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... values) {
        this.values = new LinkedList<>(Arrays.asList(values));
    }

    public OrganizationSection(List<Organization> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return values != null ? values.equals(that.values) : that.values == null;

    }

    @Override
    public int hashCode() {
        return values != null ? values.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "values=" + values +
                '}';
    }

    public List<Organization> getValues() {
        return values;
    }
}
