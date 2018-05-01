package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository localRepo;

    @Autowired
    public TimeEntryController(TimeEntryRepository repo){
        this.localRepo = repo;
    }

    @PostMapping("/time/create")
    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        this.localRepo.create(timeEntryToCreate);

        return new ResponseEntity(timeEntryToCreate, HttpStatus.CREATED);
    }

    @GetMapping("/time/read")
    public ResponseEntity read(long l) {

        TimeEntry t = this.localRepo.find(l);
        if(t != null){
            return new ResponseEntity(t, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/time/list")
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity(this.localRepo.list(), HttpStatus.OK);
    }

    @PostMapping("/time/update")
    public ResponseEntity update(long l, TimeEntry expected) {
        TimeEntry temp = this.localRepo.update(l, expected);
        if(temp == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(temp, HttpStatus.OK);
    }

    @DeleteMapping("/time/delete")
    public ResponseEntity<TimeEntry> delete(long l) {

        this.localRepo.delete(l);
        if(this.localRepo.find(l) == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
    }
}