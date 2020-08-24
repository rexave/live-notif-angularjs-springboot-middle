package demo.notification.middle.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static demo.notification.middle.configuration.security.RolesEnum.CONSULTATION;
import static demo.notification.middle.configuration.security.RolesEnum.STANDARD;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
				.simpDestMatchers("/socket/lecture").hasRole(CONSULTATION)
				.simpDestMatchers("/socket/prive").hasRole(STANDARD)
				.simpDestMatchers("/socket/**").authenticated()
				.anyMessage().authenticated();
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
