package ru.webapp.model;

import java.util.Date;
import java.util.List;

/**
 * Леха
 * 08.10.2016.
 */
public class Organization {

    Link link;
    private List<Period> periods;

    public static class Period {
        private Date startDate;
        private Date endDate;
        private String position;
        private String content;

        public Period(Date startDate, Date endDate, String position, String content) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.content = content;
        }

        public Period() {
        }
    }


}
