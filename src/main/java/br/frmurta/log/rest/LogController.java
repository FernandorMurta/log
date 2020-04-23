package br.frmurta.log.rest;

import br.frmurta.log.exceptions.LogIdDoesNotMatchException;
import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.exceptions.BodyValidationErrorAPI;
import br.frmurta.log.util.Slf4jLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@RestController
@RequestMapping(value = LogController._PATH)
public class LogController {

	static final String _PATH = "/log";

	private final LogService logService;

	@Autowired
	public LogController(LogServiceImpl logService) {
		this.logService = logService;
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = LogDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Return one Log from the database with the ID in the Path")
	public ResponseEntity<?> findOneLog(@PathVariable(value = "id") Long id) {
		Slf4jLog.info("=== Find one Log with ID: " + id);
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(this.logService.findOneLog(id));
		} catch (LogNotFoundException logNotFoundException) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, logNotFoundException.getMessage(), logNotFoundException);
		} catch (Exception exception) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	@PostMapping(produces = "application/json", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = LogDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BodyValidationErrorAPI.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Save a new Log in the database")
	public ResponseEntity<?> saveOneLog(@Valid @RequestBody LogDTO logDTO, @ApiIgnore Errors errors) {

		Slf4jLog.info("=== Save one Log with values: " + logDTO.toString());
		if (errors.hasErrors()) {
			return this.validationErrorAPIResponseEntity(errors);
		}

		try {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(this.logService.saveLog(logDTO));
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	@PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request", response = BodyValidationErrorAPI.class),
			@ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Edit one log with the ID in the Path with the values passed on body")
	public ResponseEntity<?> EditOneLog(@PathVariable(value = "id") Long id,
										@Valid @RequestBody LogDTO logDTO, @ApiIgnore Errors errors) {

		Slf4jLog.info("=== Edit one Log with ID: " + id + " and values: " + logDTO.toString());

		if (errors.hasErrors()) {
			return this.validationErrorAPIResponseEntity(errors);
		}

		try {
			this.logService.updateLog(id, logDTO);
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.build();
		} catch (LogNotFoundException logNotFoundException) {

			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, logNotFoundException.getMessage(), logNotFoundException);
		} catch (LogIdDoesNotMatchException logIdDoesNotMatchException) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BodyValidationErrorAPI(
					HttpStatus.BAD_REQUEST.toString(),
					Collections.singletonList(
							new BodyValidationErrorAPI.Fields("ID",
									"ID enviado na entidade não é igual ao id enviado na request!")),
					"Erro na validação da entidade LOG"));
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}


	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Remove one Log from the database with the ID in the Path")
	public ResponseEntity<?> deleteLog(@PathVariable(value = "id") Long id) {
		Slf4jLog.info("=== Delete one Log with ID: " + id);
		try {
			this.logService.deleteLog(id);
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.build();
		} catch (LogNotFoundException logNotFoundException) {

			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, logNotFoundException.getMessage(), logNotFoundException);
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	private ResponseEntity<BodyValidationErrorAPI> validationErrorAPIResponseEntity(Errors errors) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BodyValidationErrorAPI(
				HttpStatus.BAD_REQUEST.toString(),
				errors.getAllErrors()
						.stream()
						.map(BodyValidationErrorAPI.Fields::new)
						.collect(Collectors.toList()),
				"Erro na validação da entidade LOG"));
	}
}
