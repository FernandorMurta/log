package br.frmurta.log.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Log implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "globalSequenceGenerator")
	@SequenceGenerator(name = "globalSequenceGenerator", sequenceName = "global_sequence", allocationSize = 1)
	private Long id;

	@CreationTimestamp
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

}
