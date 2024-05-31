package vk.bot.components;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vk.bot.config.BotProperties;
import vk.bot.service.UpdateChecker;

@Component
public class BotInitializer {

    private final BotProperties botProperties;
    private final UpdateChecker updateChecker;
    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;


    @Autowired
    public BotInitializer(BotProperties botProperties, UpdateChecker updateChecker, VkApiClient vkApiClient) {
        this.botProperties = botProperties;
        this.updateChecker = updateChecker;
        this.vkApiClient = vkApiClient;
        groupActor = initializeGroupActor();
    }


    private GroupActor initializeGroupActor() {
        return new GroupActor(botProperties.getGroupId(), botProperties.getAccessToken());
    }

    @PostConstruct
    public void startBot() throws ClientException, ApiException, InterruptedException {
        Integer ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        updateChecker.startCheckingForUpdates(ts, vkApiClient, groupActor);
    }

}
