package demo.notification.middle.configuration;

import demo.notification.middle.dto.MessageNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		System.out.println("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			MessageNotification chatMessage = new MessageNotification();
			chatMessage.setContenu("has left the channel");
			chatMessage.setAuthor(username);
			messagingTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
}
