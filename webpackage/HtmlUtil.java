package ru.webapp.webpackage;

import ru.webapp.model.ContactType;
import ru.webapp.model.Organization;
import ru.webapp.model.Resume;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Леха
 * 03.11.2016.
 */
public class HtmlUtil {

    public static String getContact(Resume resume, ContactType contactType) {
        String contact = resume.getContact(contactType);
        return contact == null ? "&nbsp;" : contactType.toHtml(contact);
    }

   /* public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    public static String format(LocalDate localDate) {
        return localDate.equals(Organization.Period.NOW) ? "Now" : localDate.format(DATE_FORMATTER);
    }*/
}
