package vk.bot.service.handlers;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MessageHandler {

    private final Random random = new Random();

    public void mock(Message message, VkApiClient vkApiClient, GroupActor groupActor) throws ClientException, ApiException {
        String messageToSend = "Вы написали: " + message.getText();
        vkApiClient.messages()
                .send(groupActor)
                .message(messageToSend)
                .userId(message.getFromId())
                .randomId(random.nextInt(Integer.MAX_VALUE))
                .execute();
    }
}
