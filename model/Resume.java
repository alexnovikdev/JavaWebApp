package ru.webapp.model;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Леха
 * 07.10.2016.
 */
public class Resume {

    private String uuid;
    private String fullName;
    private String location;
    private String homePage;
    private List<Contact> contacts = new LinkedList<>();
    private List<Section> sections = new LinkedList<>();

    public Resume(String fullName, String location) {
        this(UUID.randomUUID().toString(), fullName, location);
    }

    public Resume(String uuid, String fullName, String location) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.location = location;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        if (location != null ? !location.equals(resume.location) : resume.location != null) return false;
        if (homePage != null ? !homePage.equals(resume.homePage) : resume.homePage != null) return false;
        if (contacts != null ? !contacts.equals(resume.contacts) : resume.contacts != null) return false;
        return sections != null ? sections.equals(resume.sections) : resume.sections == null;

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (homePage != null ? homePage.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        return result;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLocation() {
        return location;
    }

    public String getHomePage() {
        return homePage;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<Section> getSections() {
        return sections;
    }
}
