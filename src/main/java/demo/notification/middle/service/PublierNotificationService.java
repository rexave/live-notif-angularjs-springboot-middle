package demo.notification.middle.service;

import demo.notification.middle.bll.AutresNotificationsBlo;
import demo.notification.middle.dto.ReponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Log4j2
@RestController
public class PublierNotificationService {

	@Resource
	private AutresNotificationsBlo autresNotificationsBlo;

	@CrossOrigin(origins = "http://localhost:8000")
	@GetMapping(path = "/notification/publier/{nomTopic}/{message}/{auteur}")
	public ReponseDto publierSurTopic(@PathVariable(name = "nomTopic") String topic,
									  @PathVariable(name = "message") String message,
									  @PathVariable(name = "auteur") String auteur) {
		log.info("demande de publication topic [{}] message [{}]", topic, message);

		autresNotificationsBlo.publierDansTopic(topic, message, auteur);

		return ReponseDto.Ok();
	}
}
