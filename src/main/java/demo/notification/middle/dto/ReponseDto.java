package demo.notification.middle.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReponseDto {
	public String message;

	public static ReponseDto Ok() {
		return ReponseDto.builder().message("OK").build();
	}
}
