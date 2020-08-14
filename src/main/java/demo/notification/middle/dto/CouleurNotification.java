package demo.notification.middle.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class CouleurNotification implements Serializable {
	private int x;
	private int y;
	private String couleur;
}
