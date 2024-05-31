package vk.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vk.bot.components.BotInitializer;

@SpringBootApplication
public class VkBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkBotApplication.class, args);
	}

}
