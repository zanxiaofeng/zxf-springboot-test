package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import zxf.springboot.pa.apitest.support.sql.DatabaseVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Sql(scripts = {"/sql/cleanup/clean-up.sql", "/sql/init/schema.sql", "/sql/init/data.sql"})
public class DBServiceSqlTest {

    @Autowired
    DBService dbService;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    DatabaseVerifier databaseVerifier;

    @AfterEach
    void tearDown() {
        // Clean up table after each test
        jdbcTemplate.update("DELETE FROM PROJECT", Collections.emptyMap());
    }

    @Test
    void insertProject_shouldInsertRecordSuccessfully() {
        // given
        databaseVerifier = new DatabaseVerifier(jdbcTemplate);
        String id = "test-project-1";
        String name = "Test Project";

        // when
        int result = dbService.insertProject(id, name);

        // then
        assertThat(result).isEqualTo(1);
        assertThat(databaseVerifier.recordExists("project", "id", id)).isTrue();
        assertThat(databaseVerifier.getValueById("project", "id", id, "name")).isEqualTo(name);
        assertThat(databaseVerifier.countRows("project")).isEqualTo(2); // p-1 + test-project-1
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(scripts = {"/sql/cases/project-p-test.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void insertProject_shouldInsertRecordWithExistingData() {
        // given - data.sql already inserts p-1
        databaseVerifier = new DatabaseVerifier(jdbcTemplate);
        String id = "test-project-2";
        String name = "Test Project 2";

        // when
        int result = dbService.insertProject(id, name);

        // then
        assertThat(result).isEqualTo(1);
        assertThat(databaseVerifier.recordExists("project", "id", id)).isTrue();
        assertThat(databaseVerifier.countRows("project")).isEqualTo(3); // p-1 + p-test + test-project-2
    }
}