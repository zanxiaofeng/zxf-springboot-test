package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.rest.request.TaskRequest;
import zxf.springboot.pa.utils.SystemUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(classes = {PAService.class})
public class PAServiceTest {
    @Autowired
    private PAService paService;
    @MockitoBean
    private PAClient paClient;

    public PAServiceTest() {
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
    void a() throws Exception {
        //Given
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTask(200);
        Map<String, Object> result = new HashMap<>();
        Mockito.when(paClient.callDownstreamSyncByPost("/pa/a/json", taskRequest, false)).thenReturn(result);

        //When
        Map<String, Object> realResult = paService.a(taskRequest);

        //Then
        assertEquals(result, realResult.get("downstream"));
    }


    @Test
    void c() throws Exception {
        try(MockedStatic<SystemUtils> systemUtilsMockedStatic = Mockito.mockStatic(SystemUtils.class)) {
            //Given
            String taskId = "200";
            Map<String, Object> result = new HashMap<>();
            Mockito.when(paClient.callDownstreamSyncByGet("/pa/c/json?task=" + taskId, false)).thenReturn(result);
            systemUtilsMockedStatic.when(()-> SystemUtils.currentTimeMillis()).thenReturn(123456789L);


            //When
            Map<String, Object> realResult = paService.c(taskId);

            //Then
            assertEquals(result, realResult.get("downstream"));
            assertEquals(123456789L, realResult.get("currentTimeMillis"));
            systemUtilsMockedStatic.verify(()-> SystemUtils.currentTimeMillis());
        }
    }
}
