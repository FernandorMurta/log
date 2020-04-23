package br.frmurta.log.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * @param <T> Any object
 * @author Fernando Murta
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private T data;

	private List<String> errors;

	private String status;


	/**
	 * Constructor
	 *
	 * @param data data from response
	 */
	public Response(T data) {
		this.data = data;
	}

	/**
	 * Constructor
	 *
	 * @param errors Errors from response
	 */
	public Response(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * Constructor
	 *
	 * @param data   data from response
	 * @param errors Errors from response
	 */
	public Response(T data, List<String> errors) {
		this.data = data;
		this.errors = errors;
	}

}
