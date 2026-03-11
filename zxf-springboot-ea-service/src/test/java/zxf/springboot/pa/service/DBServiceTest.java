package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import zxf.springboot.pa.jdbc.ProjectRowMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DBServiceTest {
    @InjectMocks
    private DBService dbService;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public DBServiceTest() {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Ctor {}***************************");
    }

    @BeforeAll
    static void setupForAll() throws IOException {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Before all {}***************************");
    }

    @BeforeEach
    void setupForEach() throws IOException {
        log.atInfo().addArgument(() -> ProcessIdUtil.getProcessId()).log("***************************Before each {}***************************");
    }

    @Test
    void queryProjectById() throws Exception {
        //Given
        String projectId = "p-1";
        Map<String, Object> result = new HashMap<>();
        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                eq("SELECT * FROM PROJECT WHERE ID=:projectId"),
                eq(Collections.singletonMap("projectId", "p-1")),
                any(ProjectRowMapper.class)
        )).thenReturn(result);

        //When
        Map<String, Object> realResult = dbService.queryProjectById(projectId);

        //Then
        assertThat(realResult).isSameAs(result);

        // Verify exact SQL and parameters
        ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
        verify(namedParameterJdbcTemplate).queryForObject(
                eq("SELECT * FROM PROJECT WHERE ID=:projectId"),
                captor.capture(),
                any(ProjectRowMapper.class)
        );
        assertThat(captor.getValue()).containsEntry("projectId", "p-1");
    }
}
