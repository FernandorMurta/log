package br.frmurta.log.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDTO {

	private Long id;

	private Date createdAt;

	@NotNull
	private Date logDate;

	@NotNull
	private String ip;

	@NotNull
	private String request;

	@NotNull
	private String status;

	@NotNull
	private String userAgent;

	public static LogDTO fromEntity(Log log) {
		return LogDTO.builder()
				.id(log.getId())
				.createdAt(log.getCreatedAt())
				.logDate(log.getLogDate())
				.ip(log.getIp())
				.request(log.getRequest())
				.status(log.getStatus())
				.userAgent(log.getUserAgent())
				.build();
	}

}
