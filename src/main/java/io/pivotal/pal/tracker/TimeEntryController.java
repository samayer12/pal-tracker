package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TimeEntryController {

    private TimeEntryRepository localRepo = new TimeEntryRepository();

    public TimeEntryController(TimeEntryRepository repo){
        this.localRepo = repo;
    }

    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        this.localRepo.create(timeEntryToCreate);

        return new ResponseEntity(timeEntryToCreate, HttpStatus.CREATED);
    }

    public ResponseEntity read(long l) {

        TimeEntry t = this.localRepo.find(l);
        if(t != null){
            return new ResponseEntity(t, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity(this.localRepo.list(), HttpStatus.OK);
    }

    public ResponseEntity update(long l, TimeEntry expected) {

        TimeEntry t = this.localRepo.find(l);
        if(t != null){
            return new ResponseEntity(this.localRepo.update(l, expected), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<TimeEntry> delete(long l) {

        this.localRepo.delete(l);
        if(this.localRepo.find(l) == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
    }
}