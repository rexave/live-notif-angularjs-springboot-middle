package demo.notification.middle.bll;

import demo.notification.middle.dto.MessageNotification;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Log4j2
public class AutresNotificationsBlo {

	@Resource
	private SimpMessagingTemplate template;

	public void publierDansTopic(final String topic, final String message, final String auteur) {
		MessageNotification messageNotification = MessageNotification.builder()
				.contenu(message)
				.author(auteur)
				.build();
		template.convertAndSend("/topic/" + topic, messageNotification);
	}

}
