package zxf.springboot.pa.rest.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TaskRequest {
    private Integer task;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String projectId;
}
