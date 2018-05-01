package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    Map<Long, TimeEntry> timeEntryList = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntryList.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        TimeEntry local = this.timeEntryList.get(id);
        return local;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry temp = this.find(id);

        if(temp != null) {
            this.timeEntryList.put(temp.getId(), timeEntry);
            return this.timeEntryList.get(temp.getId());
        }
        return null;
    }

    public void delete(long id){
        TimeEntry temp = this.find(id);
        this.timeEntryList.remove(temp.getId());
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


    //<editor-fold desc="Getters & Setters">


    public Map<Long, TimeEntry> getTimeEntryList() {
        return timeEntryList;
    }

    public void setLocalRepo(Map<Long, TimeEntry> timeEntryList) {
        this.timeEntryList = timeEntryList;
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(this.timeEntryList.values());
    }


    //</editor-fold>
}
