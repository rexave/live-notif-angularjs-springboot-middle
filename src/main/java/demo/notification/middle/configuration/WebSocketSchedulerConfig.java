package demo.notification.middle.configuration;

import demo.notification.middle.bll.MatriceCouleurBlo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
@EnableScheduling
@Log4j2
public class WebSocketSchedulerConfig {

	@Autowired
	private SimpMessagingTemplate template;

	@Resource
	private MatriceCouleurBlo matriceCouleurBlo;

//	@Scheduled(fixedRate = 5000)
	public void publishUpdates() {
		List<String> givenList = Arrays.asList("red", "blue", "yellow", "green", "black", "purple");
		Random rand = new Random();
		String randomElement = givenList.get(rand.nextInt(givenList.size()));

		matriceCouleurBlo.activerMatriceCouleur(randomElement, 5, 100);
	}

}