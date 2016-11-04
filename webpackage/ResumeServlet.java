package ru.webapp.webpackage;

import ru.webapp.model.Resume;
import ru.webapp.storage.XmlFileStorage;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Леха
 * 02.11.2016.
 */
public class ResumeServlet extends HttpServlet {
    public static XmlFileStorage storage = new XmlFileStorage("E:\\JavaProjects\\JavaWebApp\\file_storage");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}
