package hello.exception.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlackEntity {
    private int errorCode;
    private String errorMessage;
}
