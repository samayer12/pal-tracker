package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class TimeEntryRepository {

    List<TimeEntry> localRepo = new ArrayList<>();

    public  TimeEntryRepository() {
    }

    public TimeEntry create(TimeEntry any) {
        localRepo.add(any);
        return any;
    }

    public TimeEntry find(long id) {
        for (TimeEntry t : this.localRepo) {
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry temp = this.find(id);
        int index = this.localRepo.indexOf(temp);
        this.localRepo.set(index, timeEntry);
        return this.localRepo.get(index);

    }

    public void delete(long id) {
        TimeEntry temp = this.find(id);
        int index = this.localRepo.indexOf(temp);
        this.localRepo.remove(index);
    }

    public List<TimeEntry> list() {
        return localRepo;
    }

    public void setLocalRepo(List<TimeEntry> localRepo) {
        this.localRepo = localRepo;
    }
}
