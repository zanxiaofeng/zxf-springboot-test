package zxf.springboot.pa.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProjectRowMapper implements RowMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> project = new HashMap<>();
        project.put("id", rs.getString("ID"));
        project.put("name", rs.getString("NAME"));
        return project;
    }
}