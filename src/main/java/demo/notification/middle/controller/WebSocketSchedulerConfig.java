package demo.notification.middle.controller;

import demo.notification.middle.dto.MessageNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.*;

@Configuration
@EnableScheduling
public class WebSocketSchedulerConfig {

	@Autowired
	private SimpMessagingTemplate template;

	@Scheduled(fixedRate = 3000)
	public void publishUpdates() {
		String message = "It's " + LocalDateTime.now().format(new DateTimeFormatterBuilder()
				.appendValue(HOUR_OF_DAY, 2)
				.appendLiteral(':')
				.appendValue(MINUTE_OF_HOUR, 2)
				.appendLiteral(':')
				.appendValue(SECOND_OF_MINUTE, 2)
				.appendLiteral(" - ")
				.appendValue(DAY_OF_MONTH, 2)
				.appendLiteral('/')
				.appendValue(MONTH_OF_YEAR, 2)
				.appendLiteral('/')
				.appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
				.toFormatter());
		MessageNotification messageNotification = new MessageNotification();
		messageNotification.setContenu(message);
		messageNotification.setAuthor("System");
		System.out.println("Message: " + message);
		template.convertAndSend("/topic/notifications", messageNotification);
	}

}