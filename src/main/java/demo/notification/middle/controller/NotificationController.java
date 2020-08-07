package demo.notification.middle.controller;

import demo.notification.middle.dto.MessageNotification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Objects;

@Controller
public class NotificationController {


	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/javainuse")
	public MessageNotification sendMessage(@Payload MessageNotification webSocketChatMessage) {
		return webSocketChatMessage;
	}

	@MessageMapping("/chat.newUser")
	@SendTo("/topic/javainuse")
	public MessageNotification newUser(@Payload MessageNotification message,
									   SimpMessageHeaderAccessor headerAccessor) {
		Map<String, Object> sessionAttributes = Objects.requireNonNull(headerAccessor.getSessionAttributes());
		sessionAttributes.put("username", message.getAuthor());
		return message;
	}
}
