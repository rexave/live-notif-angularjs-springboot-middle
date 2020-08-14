package demo.notification.middle.bll;

import demo.notification.middle.dto.CouleurNotification;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Log4j2
public class MatriceCouleurBlo {

	@Resource
	private SimpMessagingTemplate template;

	public void activerCouleur(final String couleur) {
		CouleurNotification couleurNotification = CouleurNotification.builder()
				.couleur(couleur)
				.x(0)
				.y(0).build();
		template.convertAndSend("/topic/couleurs", couleurNotification);
	}

	public void activerMatriceCouleur(final String couleur, final int maxX, final int maxY) {
		for (int x = 0; x < maxX; x++) {
			for (int y = 0; y < maxY; y++) {
				CouleurNotification couleurNotification = CouleurNotification.builder()
						.couleur(couleur)
						.x(x)
						.y(y).build();
				template.convertAndSend("/topic/couleurs", couleurNotification);
				log.info("Published {}", couleurNotification);
			}
		}
	}
}
