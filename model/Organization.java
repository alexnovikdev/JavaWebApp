package ru.webapp.model;

import ru.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * Леха
 * 08.10.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    static final long serialVersionUID = 1L;
    private Link link = Link.EMPTY;
    private List<Period> periods = new LinkedList<>();

    public Organization() {
    }

    public Organization(Link link, List<Period> periods) {
        this.link = link;
        this.periods = periods;
    }

    public Organization(String name, String url, Period... periods) {
        this(new Link(name, url), new LinkedList<>(Arrays.asList(periods)));
    }

    public Link getLink() {
        return link;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {

        static final long serialVersionUID = 1L;

        public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate = NOW;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        private String position;
        private String content = "";

        public Period(LocalDate startDate, LocalDate endDate, String position, String content) {
            Objects.requireNonNull(startDate, "startDate is null");
            Objects.requireNonNull(position, "position is null");
            this.startDate = startDate;
            this.endDate = endDate == null ? NOW : endDate;
            this.position = position;
            this.content = content == null ? "" : content;
        }

        public Period() {
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String position, String content) {
            this(LocalDate.of(startYear, startMonth, 1), LocalDate.of(endYear, endMonth, 1), position, content);
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (startDate != null ? !startDate.equals(period.startDate) : period.startDate != null) return false;
            if (endDate != null ? !endDate.equals(period.endDate) : period.endDate != null) return false;
            if (position != null ? !position.equals(period.position) : period.position != null) return false;
            return content != null ? content.equals(period.content) : period.content == null;

        }

        @Override
        public int hashCode() {
            int result = startDate != null ? startDate.hashCode() : 0;
            result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
            result = 31 * result + (position != null ? position.hashCode() : 0);
            result = 31 * result + (content != null ? content.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", position='" + position + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return periods != null ? periods.equals(that.periods) : that.periods == null;

    }

    @Override
    public int hashCode() {
        int result = link != null ? link.hashCode() : 0;
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", periods=" + periods +
                '}';
    }
}
