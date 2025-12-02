package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(rs.getInt("TaskID"), rs.getString("Name"), rs.getString("Description"),
                rs.getInt("HourEstimate"), rs.getBoolean("Completed"), rs.getDate("StartDate"),
                rs.getDate("Deadline"), rs.getInt("ProjectID"), rs.getInt("ParentID"));
    }
}
