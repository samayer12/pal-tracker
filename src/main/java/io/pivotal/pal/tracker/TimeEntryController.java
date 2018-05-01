package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
@ResponseBody
public class TimeEntryController {

    private TimeEntryRepository localRepo;

    @Autowired
    public TimeEntryController(TimeEntryRepository repo){
        this.localRepo = repo;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        this.localRepo.create(timeEntryToCreate);

        return new ResponseEntity(timeEntryToCreate, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable("id") long l) {

        TimeEntry t = this.localRepo.find(l);
        if(t != null){
            return new ResponseEntity(t, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity(this.localRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") long l, @RequestBody TimeEntry expected) {
        TimeEntry temp = this.localRepo.update(l, expected);
        if(temp == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(temp, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long l) {
        this.localRepo.delete(l);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}