package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
@ResponseBody
public class TimeEntryController {

    private TimeEntryRepository localRepo;

    private final CounterService counter;
    private final GaugeService gauge;

    @Autowired
    public TimeEntryController(TimeEntryRepository repo, CounterService counter, GaugeService gauge){
        this.counter = counter;
        this.gauge = gauge;
        this.localRepo = repo;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", this.localRepo.list().size());
        this.localRepo.create(timeEntryToCreate);

        return new ResponseEntity(timeEntryToCreate, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable("id") long l) {

        TimeEntry t = this.localRepo.find(l);
        if(t != null){
            counter.increment("TimeEntry.read");
            return new ResponseEntity(t, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity(this.localRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") long l, @RequestBody TimeEntry expected) {
        TimeEntry temp = this.localRepo.update(l, expected);
        if(temp == null){
            counter.increment("TimeEntry.updated");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(temp, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long l) {
        this.localRepo.delete(l);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", this.localRepo.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}