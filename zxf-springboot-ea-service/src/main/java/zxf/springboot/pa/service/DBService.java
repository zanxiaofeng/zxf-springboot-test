package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import zxf.springboot.pa.jdbc.ProjectRowMapper;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class DBService {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DBService() {
        log.info("::ctor");
    }

    public Map<String, Object> queryProjectById(String projectId) {
        String query = "SELECT * FROM PROJECT WHERE ID=:projectId";
        log.info("queryProjectById:: query={}, parameters={}", query, projectId);
        Map<String, Object> parameters = Collections.singletonMap("projectId", projectId);
        return namedParameterJdbcTemplate.queryForObject(query, parameters, new ProjectRowMapper());
    }
}
