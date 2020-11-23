package demo.notification.middle.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;

@Log4j2
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, ApplicationListener<BrokerAvailabilityEvent> {

	private MessageBrokerRegistry localConfig;

	private AlivenessAwareStompBrokerConfiguration alivenessAwareStompBrokerConfiguration = getRollingStompBrokerConfiguration();

	//	@Bean
	public AlivenessAwareStompBrokerConfiguration getRollingStompBrokerConfiguration() {
		ArrayList<BrokerConfiguration> brokerConfigurationList = new ArrayList<>();
		brokerConfigurationList.add(new BrokerConfiguration("localhost", "51613"));
		brokerConfigurationList.add(new BrokerConfiguration("localhost", "61613"));
		return new AlivenessAwareStompBrokerConfiguration(brokerConfigurationList);
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor()
				.corePoolSize(20);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/websocket-vanilla").setAllowedOrigins("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		localConfig = config;
		configureBroker();
		config.setApplicationDestinationPrefixes("/app");
	}

	private void configureBroker() {
		BrokerConfiguration brokerConfig = alivenessAwareStompBrokerConfiguration.getBrokerConfig();
		localConfig.enableStompBrokerRelay("/topic")
				.setRelayHost(brokerConfig.getHost())
				.setRelayPort(brokerConfig.getPort())
//				.setClientLogin("admin")
//				.setClientPasscode("admin");
		;
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent e) {
		if (!e.isBrokerAvailable()) {
			log.warn("Broker unavailable. Reconfiguring to switch instance.");
			configureBroker();
		}
		log.info("broker available: " + e.isBrokerAvailable());
	}

}
