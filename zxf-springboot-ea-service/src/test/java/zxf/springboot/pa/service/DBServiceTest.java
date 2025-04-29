package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.Assertions;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DBServiceTest {
    @InjectMocks
    private DBService dbService;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public DBServiceTest() {
        log.info("***************************Ctor {}***************************", ProcessIdUtil.getProcessId());
    }

    @BeforeAll
    static void setupForAll() throws IOException {
        log.info("***************************Before all {}***************************", ProcessIdUtil.getProcessId());
    }

    @BeforeEach
    void setupForEach() throws IOException {
        log.info("***************************Before each {}***************************", ProcessIdUtil.getProcessId());
    }

    @Test
    void testQueryProjectById() throws Exception {
        //Given
        String projectId = "p-1";
        Map<String, Object> result = new HashMap<>();
        Mockito.when(namedParameterJdbcTemplate.queryForObject(Mockito.anyString(), Mockito.anyMap(), Mockito.any(ProjectRowMapper.class)))
                .thenReturn(result);

        //When
        Map<String, Object> realResult = dbService.queryProjectById(projectId);

        //Then
        Assertions.assertEquals(result, realResult);
    }
}
