package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    List<TimeEntry> timeEntryList = new ArrayList<>();

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntryList.add(timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        for (TimeEntry t : this.timeEntryList) {
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry temp = this.find(id);

        if(temp != null) {
            int index = this.timeEntryList.indexOf(temp);
            this.timeEntryList.set(index, timeEntry);
            return this.timeEntryList.get(index);
        }
        return null;
    }

    public void delete(long id){
        TimeEntry temp = this.find(id);
        int index = this.timeEntryList.indexOf(temp);
        this.timeEntryList.remove(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InMemoryTimeEntryRepository that = (InMemoryTimeEntryRepository) o;

        return getTimeEntryList() != null ? getTimeEntryList().equals(that.getTimeEntryList()) : that.getTimeEntryList() == null;
    }

    @Override
    public int hashCode() {
        return getTimeEntryList() != null ? getTimeEntryList().hashCode() : 0;
    }

    public TimeEntry update(Long id, TimeEntry timeEntry) {
        TimeEntry temp = this.find(id);
        int index = this.timeEntryList.indexOf(temp);
        this.timeEntryList.set(index, timeEntry);
        return this.timeEntryList.get(index);
    }

    public void delete(Long id) {
        TimeEntry temp = this.find(id);
        int index = this.timeEntryList.indexOf(temp);
        this.timeEntryList.remove(index);
    }

    //<editor-fold desc="Getters & Setters">


    public List<TimeEntry> getTimeEntryList() {
        return timeEntryList;
    }

    public void setLocalRepo(List<TimeEntry> timeEntryList) {
        this.timeEntryList = timeEntryList;
    }

    public List<TimeEntry> list() {
        return this.timeEntryList;
    }


    //</editor-fold>
}
