package hello.exception.exHandler.advice;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import hello.exception.Entity.SlackEntity;
import hello.exception.exHandler.ErrorResult;
import hello.exception.exception.UserException;
import hello.exception.handler.SlackErrorHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExControllerAdvice {

    private final SlackErrorHandler slackErrorHandler;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        SlackEntity slack = new SlackEntity(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        slackErrorHandler.sendErrorMessage(slack);
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[userExHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        SlackEntity slack = new SlackEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        slackErrorHandler.sendErrorMessage(slack);
        log.error("[exHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
