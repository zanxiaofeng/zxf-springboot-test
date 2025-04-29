package zxf.springboot.pa.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import zxf.springboot.pa.client.PAClient;
import zxf.springboot.pa.rest.request.TaskRequest;
import zxf.springboot.pa.utils.SystemUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = {PAService.class})
public class PAServiceTest {
    @Autowired
    private PAService paService;
    @MockBean
    private PAClient paClient;

    public PAServiceTest() {
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
    void testA() throws Exception {
        //Given
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTask(200);
        Map<String, Object> result = new HashMap<>();
        Mockito.when(paClient.callDownstreamSyncByPost(Mockito.eq("/pa/a/json"), Mockito.eq(taskRequest), Mockito.eq(false))).thenReturn(result);

        //When
        Map<String, Object> realResult = paService.a(taskRequest);

        //Then
        Assertions.assertEquals(result, realResult.get("downstream"));
    }


    @Test
    void testC() throws Exception {
        try(MockedStatic<SystemUtils> systemUtilsMockedStatic = Mockito.mockStatic(SystemUtils.class)) {
            //Given
            String taskId = "200";
            Map<String, Object> result = new HashMap<>();
            Mockito.when(paClient.callDownstreamSyncByGet(Mockito.eq("/pa/c/json?task=" + taskId), Mockito.eq(false))).thenReturn(result);
            systemUtilsMockedStatic.when(()-> SystemUtils.currentTimeMillis()).thenReturn(123456789L);


            //When
            Map<String, Object> realResult = paService.c(taskId);

            //Then
            Assertions.assertEquals(result, realResult.get("downstream"));
            Assertions.assertEquals(123456789L, realResult.get("currentTimeMillis"));
            systemUtilsMockedStatic.verify(()-> SystemUtils.currentTimeMillis());
        }
    }
}
