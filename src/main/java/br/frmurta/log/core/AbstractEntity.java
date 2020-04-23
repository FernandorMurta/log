package br.frmurta.log.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public abstract class AbstractEntity implements Serializable {

	@Id
	private String id;

	/**
	 * Constructor
	 *
	 * @param id Id of the Entity
	 */
	public AbstractEntity(String id) {
		this.id = id;
	}
}
