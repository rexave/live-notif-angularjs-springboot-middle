package demo.notification.middle.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageNotification implements Serializable {
	private String contenu;
	private String author;
	private Date createdAt = new Date();
}
