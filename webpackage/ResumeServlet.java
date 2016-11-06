package ru.webapp.webpackage;

import ru.webapp.WebAppConfig;
import ru.webapp.model.ContactType;
import ru.webapp.model.Resume;
import ru.webapp.model.SectionType;
import ru.webapp.storage.IStorage;
import ru.webapp.storage.XmlFileStorage;
import ru.webapp.util.Util;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Леха
 * 02.11.2016.
 */
public class ResumeServlet extends HttpServlet {
    private IStorage storage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        Resume r = Util.isEmpty(uuid) ? new Resume(name, location) : storage.load(uuid);

        r.setFullName(name);
        r.setLocation(location);
        r.setHomePage(request.getParameter("home_page"));

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.isEmpty()) {
                r.removeContact(type);
            } else {
                r.addContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (type.getHtmlType() == SectionHtmlType.ORGANIZATION) {
                continue;
            }
            if (value == null || value.isEmpty()) {
                r.getSections().remove(type);
            } else {
                r.addSection(type, type.getHtmlType().createSection(value));
            }
        }
        if (Util.isEmpty(uuid)) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        String name = request.getParameter("name");
        writer.write("тест сервлет: привет) " + name);
        writer.close();*/

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        Resume resume;
        IStorage storage = WebAppConfig.get().getStorage();

        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("list");
                return;
            case "create":
                resume = Resume.EMPTY;
                break;
            case "view":
            case "edit":
                resume = storage.load(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")).forward(request, response);
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = WebAppConfig.get().getStorage();
    }
}
