package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {

    private long id, projectId, userId;
    private LocalDate date;
    private int hours;

    public TimeEntry(){

    }

    public TimeEntry(long in0, long in1, long in2, LocalDate dateIn, int intIn){
        this.id = in0;
        this.projectId = in1;
        this.userId = in2;
        this.date = dateIn;
        this.hours = intIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeEntry timeEntry = (TimeEntry) o;

        if (getId() != timeEntry.getId()) return false;
        if (getProjectId() != timeEntry.getProjectId()) return false;
        if (getUserId() != timeEntry.getUserId()) return false;
        if (getHours() != timeEntry.getHours()) return false;
        return getDate() != null ? getDate().equals(timeEntry.getDate()) : timeEntry.getDate() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getProjectId() ^ (getProjectId() >>> 32));
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + getHours();
        return result;
    }

    //<editor-fold desc="Getters & Setters">
    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
    //</editor-fold>

}
