package demo.notification.middle.configuration;

import lombok.Data;

@Data
public class BrokerConfiguration {

	private String host;
	private int port;

	public BrokerConfiguration(String hostParameter, String portParameter) {
		this.host = hostParameter;
		this.port = Integer.parseInt(portParameter);
	}
}
