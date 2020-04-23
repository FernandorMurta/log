package br.frmurta.log.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogDTO {

	private Long id;

	private Date createdAt;

	@NotNull(message = "Data do Log não pode ser Nulo")
	private Date logDate;

	@NotNull(message = "IP não pode ser Nulo")
	private String ip;

	@NotNull(message = "Request não pode ser Nulo")
	private String request;

	@NotNull(message = "Status não pode ser Nulo")
	private String status;

	@NotNull(message = "User Agent não pode ser Nulo")
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

	public static Log toEntity(LogDTO log) {
		return Log.builder()
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
