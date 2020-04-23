package br.frmurta.log.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

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

	@Data
	@AllArgsConstructor
	public static class Fields {

		private String fieldName;

		private String defaultMessage;

		public Fields(ObjectError objectError) {
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				this.fieldName = fieldError.getField();
				this.defaultMessage = fieldError.getDefaultMessage();
			}
		}
	}


}