package br.frmurta.log.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyValidationErrorAPI {

	@Getter
	private String status;

	@Getter
	private List<Fields> fields;

	@Getter
	private String message;

	/**
	 * @author Fernando Murta
	 * @version 0.0.1
	 */
	@Data
	@AllArgsConstructor
	public static class Fields {

		private String fieldName;

		private String defaultMessage;

		/**
		 * Constructor
		 *
		 * @param objectError Object Error from the Request
		 */
		public Fields(ObjectError objectError) {
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				this.fieldName = fieldError.getField();
				this.defaultMessage = fieldError.getDefaultMessage();
			}
		}
	}


}