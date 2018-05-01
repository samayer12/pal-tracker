package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository localRepo;

    @Autowired
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
        TimeEntry temp = this.localRepo.update(l, expected);
        if(temp == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(temp, HttpStatus.OK);
    }

    public ResponseEntity<TimeEntry> delete(long l) {

        this.localRepo.delete(l);
        if(this.localRepo.find(l) == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
    }
}