package vk.bot.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateChecker {

    private final UpdateDispatcher updateDispatcher;

    @Autowired
    public UpdateChecker(UpdateDispatcher updateDispatcher) {
        this.updateDispatcher = updateDispatcher;
    }

    public void startCheckingForUpdates(Integer ts, VkApiClient vkApiClient, GroupActor groupActor)
            throws ClientException, ApiException, InterruptedException {
        while (true) {
            MessagesGetLongPollHistoryQuery historyQuery = getHistoryQuery(ts, vkApiClient, groupActor);
            updateDispatcher.distribute(historyQuery, vkApiClient, groupActor);
            ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();

            Thread.sleep(100);
        }
    }

    private MessagesGetLongPollHistoryQuery getHistoryQuery(Integer ts, VkApiClient vkApiClient, GroupActor groupActor) {
        return vkApiClient.messages().getLongPollHistory(groupActor).ts(ts);
    }
}
