package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(rs.getInt("TaskID"),rs.getString("Name"),rs.getString("Description"),
                rs.getInt("HourEstimate"),rs.getBoolean("Completed"),rs.getDate("Deadline"),
                rs.getDate("StartDate"),rs.getInt("ProjectID"),rs.getInt("ParentID"));
    }
}
