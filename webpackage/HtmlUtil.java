package ru.webapp.webpackage;

import ru.webapp.model.ContactType;
import ru.webapp.model.Organization;
import ru.webapp.model.Resume;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Леха
 * 03.11.2016.
 */
public class HtmlUtil {

    public static String mask(String str) {
        return (str == null || str.isEmpty()) ? "&nbsp;" : str;
    }

    public static String getContact(Resume resume, ContactType contactType) {
        String contact = resume.getContact(contactType);
        return contact == null ? "&nbsp;" : contactType.toHtml(contact);
    }

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String format(LocalDate localDate) {
        return localDate.equals(Organization.Period.NOW) ? "Сейчас" : localDate.format(DATE_FORMATTER);
    }

    public static String textArea(String name, List<String> list) {
        return String.format("<textarea name=%s cols=75 rows=5>%s</textarea>", name, String.join("\n", list));
    }

    public static String input(String name, String value) {
        return String.format("<input type='text' name='%s' size=75 value='%s'>", name, value);
    }
}
