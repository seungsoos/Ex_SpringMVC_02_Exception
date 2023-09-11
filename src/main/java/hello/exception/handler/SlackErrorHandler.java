package hello.exception.handler;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import hello.exception.Entity.SlackEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackErrorHandler {

    @Value(value = "${slack.token}")
    String slackToken;

    @Value(value = "${slack.channel.monitor}")
    String channel;

    private final MethodsClient methods = Slack.getInstance().methods(slackToken);


    public void sendErrorMessage(SlackEntity slack) {

        try {
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text("테스트입니다.")
                    .build();

            methods.chatPostMessage(request);

            log.info("sendMessage = {}", request);

        } catch (SlackApiException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

