package demo.notification.middle.service;

import demo.notification.middle.bll.MatriceCouleurBlo;
import demo.notification.middle.dto.ReponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Log4j2
@RestController
public class CouleurService {

	@Resource
	private MatriceCouleurBlo matriceCouleurBlo;

	@CrossOrigin(origins = "http://localhost:8000")
	@GetMapping(path = "/couleur/activer/{nouvelleCouleur}/{maxX}/{maxY}")
	public ReponseDto activerCouleur(@PathVariable(name = "nouvelleCouleur") String nouvelleCouleur,
									 @PathVariable(name = "maxX") int maxX,
									 @PathVariable(name = "maxY") int maxY) {
		log.info("Demande nouvelle couleur {}", nouvelleCouleur);

		matriceCouleurBlo.activerMatriceCouleur(nouvelleCouleur, maxX, maxY);

		return ReponseDto.Ok();
	}
}
