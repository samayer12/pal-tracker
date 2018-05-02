package io.pivotal.pal.tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    JdbcTemplate jdbcRepo;
    DataSource dataSource;

    @Autowired
    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcRepo = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }


    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcRepo.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {

        ResultSetExtractor<TimeEntry> rse = new ResultSetExtractor<TimeEntry>() {
            @Override
            public TimeEntry extractData(ResultSet rs) throws SQLException, DataAccessException {
                TimeEntry timeEntry;
                if (rs.next()){
                    timeEntry = new TimeEntry(rs.getLong("id"),
                            rs.getLong("project_id"),
                            rs.getLong("user_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("hours"));

                    return timeEntry;
                }
                else{
                    return null;
                }
            }
        };
        TimeEntry timeEntry  = this.jdbcRepo.query("Select * from time_entries where id = " +  id, rse);

        return timeEntry;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        this.jdbcRepo.execute("UPDATE time_entries SET id = '" + id + "', project_id = '"
                + timeEntry.getProjectId() + "', user_id = '" + timeEntry.getUserId() + "', date = '"
                + timeEntry.getDate() + "', hours = '" + timeEntry.getHours() + "' WHERE id="
                + id);

        return this.find(id);
    }

    @Override
    public void delete(long id) {

        this.jdbcRepo.execute("DELETE from time_entries where id = " +  id);

    }

    @Override
    public List<TimeEntry> list() {

        RowMapper<TimeEntry> rowMapper = new RowMapper<TimeEntry>() {
            @Override
            public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
                TimeEntry timeEntry = new TimeEntry(rs.getLong("id"),
                            rs.getLong("project_id"),
                            rs.getLong("user_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("hours"));
                return timeEntry;
            }
        };

        return this.jdbcRepo.query("Select * from time_entries", rowMapper);
    }

    @Override
    public void setLocalRepo(Map<Long, TimeEntry> localRepo) {

    }
}
