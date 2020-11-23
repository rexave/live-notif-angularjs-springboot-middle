package demo.notification.middle.configuration;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xcarl
 */
@Log4j2
public class AlivenessAwareStompBrokerConfiguration {

	private static final int CHECK_TIMEOUT_MS = 5000;
	private final List<BrokerConfiguration> brokerConfigurationList;

	public AlivenessAwareStompBrokerConfiguration(ArrayList<BrokerConfiguration> brokerConfigurationList) {
		this.brokerConfigurationList = brokerConfigurationList;
	}

	public BrokerConfiguration getBrokerConfig() {
		for (BrokerConfiguration brokerConfig : brokerConfigurationList) {
			log.info("Tentative de connexion au broker {}", brokerConfig);
			if (isHostReachableOnPort(brokerConfig, CHECK_TIMEOUT_MS)) {
				log.info("Broker {} est accessible, il est sélectionné", brokerConfig);
				return brokerConfig;
			} else {
				log.warn("Broker {} non accessible, je tente un autre.", brokerConfig);
			}
		}
		log.warn("Aucun broker de la liste n'est accessible. Selection du premier par default pour continuer le boot");
		return brokerConfigurationList.get(0);
	}

	public boolean isHostReachableOnPort(BrokerConfiguration brokerConfiguration, int timeout) {
		String host = brokerConfiguration.getHost();
		int port = brokerConfiguration.getPort();

		Socket s = new Socket();
		boolean checkResult = false;
		try {
			log.debug("Ouverture socket {}:{}", host, port);
			s.setReuseAddress(true);
			SocketAddress sa = new InetSocketAddress(host, port);
			s.connect(sa, timeout);
			checkResult = s.isConnected();
			log.debug("Connected ? {}", checkResult);
		} catch (IOException e) {
			log.warn("Connexion au broker en echec {}:{} [cause : {}]", host, port, e.getMessage());
		} finally {
			try {
				s.close();
				log.debug("Socket fermée {}:{}", host, port);
			} catch (IOException ignored) {
				log.error("Erreur sur fermeture de socket", ignored);
			}
		}
		return checkResult;
	}
}
