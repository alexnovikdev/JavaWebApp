package ru.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Леха
 * 10.10.2016.
 */
public class MultiTextSection extends Section implements Serializable {
    static final long serialVersionUID = 1L;

    private List<String> values = new LinkedList<>();

    public MultiTextSection() {

    }

    public MultiTextSection(List<String> values) {
        this.values = values;
    }

    public MultiTextSection(String... values) {
        this(new LinkedList<String>(Arrays.asList(values)));
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiTextSection that = (MultiTextSection) o;

        return values != null ? values.equals(that.values) : that.values == null;

    }

    @Override
    public int hashCode() {
        return values != null ? values.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MultiTextSection{" +
                "values=" + values +
                '}';
    }
}
