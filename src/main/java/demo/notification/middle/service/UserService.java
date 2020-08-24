package demo.notification.middle.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
public class UserService {

	@GetMapping("/me")
	public Principal whoAmI(@AuthenticationPrincipal Principal user) {
		return user;
	}

	@PostMapping("/me")
	public Principal whoAmIPost(@AuthenticationPrincipal Principal user) {
		return user;
	}

}
