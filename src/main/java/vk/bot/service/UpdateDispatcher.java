package vk.bot.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vk.bot.service.handlers.MessageHandler;

import java.util.List;

@Service
public class UpdateDispatcher {

    MessageHandler messageHandler;


    @Autowired
    public UpdateDispatcher(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }


    public void distribute(MessagesGetLongPollHistoryQuery historyQuery, VkApiClient vkApiClient, GroupActor groupActor)
            throws ClientException, ApiException {
        if (!historyQuery.execute().getMessages().getItems().isEmpty()){
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            for (Message message : messages) {
                messageHandler.mock(message, vkApiClient, groupActor);
            }
        }
    }
}
