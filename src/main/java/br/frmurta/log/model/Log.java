package br.frmurta.log.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Log implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
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

}
