package br.frmurta.log.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogDTO {

	private Long id;

	private Date createdAt;

	private Date updatedAt;

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

	/**
	 * Method to transform a entity to a DTO Object
	 *
	 * @param log Entity Log
	 * @return A new Object DTO
	 */
	public static LogDTO fromEntity(Log log) {
		return LogDTO.builder()
				.id(log.getId())
				.createdAt(log.getCreatedAt())
				.updatedAt(log.getUpdatedAt())
				.logDate(log.getLogDate())
				.ip(log.getIp())
				.request(log.getRequest())
				.status(log.getStatus())
				.userAgent(log.getUserAgent())
				.build();
	}

	/**
	 * Method to transform a DTP to a Entity
	 *
	 * @param log DTO Object representing a  Log
	 * @return A new Entity
	 */
	public static Log toEntity(LogDTO log) {
		return Log.builder()
				.id(log.getId())
				.createdAt(log.getCreatedAt())
				.updatedAt(log.getUpdatedAt())
				.logDate(log.getLogDate())
				.ip(log.getIp())
				.request(log.getRequest())
				.status(log.getStatus())
				.userAgent(log.getUserAgent())
				.build();
	}

}
