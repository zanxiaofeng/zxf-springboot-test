package zxf.springboot.pa.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

public class PAService {
    public static ResponseEntity<Map<String, Object>> result(Integer task, String service) throws InterruptedException {
        HttpStatus httpStatus = HttpStatus.resolve(task);
        String realValue = "Value of " + service + " from PA: " + System.currentTimeMillis();
        switch (httpStatus) {
            case OK:
                return ResponseEntity.ok().body(Collections.singletonMap("value", realValue));
            case BAD_REQUEST:
                return ResponseEntity.status(BAD_REQUEST).build();
            case REQUEST_TIMEOUT:
                Thread.sleep(1000 * 10);
                return ResponseEntity.ok().body(Collections.singletonMap("value", realValue));
            case SERVICE_UNAVAILABLE:
                return ResponseEntity.status(SERVICE_UNAVAILABLE).build();
            default:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
