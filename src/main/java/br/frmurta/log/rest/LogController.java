package br.frmurta.log.rest;

import br.frmurta.log.exceptions.LogIdDoesNotMatchException;
import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.exceptions.BodyValidationErrorAPI;
import br.frmurta.log.util.Slf4jLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@RestController
@RequestMapping(value = LogController._PATH)
@Api(value = "Log CRUD Controller", produces = "application/json", consumes = "application/json")
public class LogController {

	static final String _PATH = "/log";

	private final LogService logService;

	/**
	 * Constructor to DI
	 *
	 * @param logService Log Service Implementation
	 */
	@Autowired
	public LogController(LogServiceImpl logService) {
		this.logService = logService;
	}

	/**
	 * Method do expose a Endpoint to find one log using his ID
	 *
	 * @param id ID of Log to use in the search
	 * @return One LogDTO object representing the entity
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = LogDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Return one Log from the database with the ID in the Path")
	public ResponseEntity<?> findOneLog(
			@PathVariable(value = "id") Long id) {
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

	/**
	 * Method do expose a Endpoint to  find Logs with parameters
	 *
	 * @param dateStart Date start to the range
	 * @param dateEnd   Date end to the range
	 * @param ip        IP used
	 * @param page      Page number to search
	 * @param qtd       Qtd of objects returned in the list
	 * @return List of results using this parameters
	 */
	@GetMapping(value = "/find", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = Page.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Return A list of Log from the database with the Params used, " +
			"this list will be returned on content object from Page ")
	public ResponseEntity<?> findAllWithParams(
			@RequestParam(value = "dateStart", required = false)
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			@ApiParam(value = "dateStart", example = "25/04/2020") Date dateStart,
			@RequestParam(value = "dateEnd", required = false)
			@DateTimeFormat(pattern = "dd/MM/yyyy")
			@ApiParam(value = "dataEnd", example = "25/04/2020") Date dateEnd,
			@RequestParam(value = "ip", required = false)
			@ApiParam(value = "ip", example = "192.168.0.125") String ip,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "qtd", defaultValue = "10") Integer qtd) {

		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(this.logService.findAllWithParams(
							Optional.ofNullable(dateStart)
									.orElse(null),
							Optional.ofNullable(dateEnd)
									.orElse(null),
							Optional.ofNullable(ip)
									.orElse(""),
							PageRequest.of(page, qtd)));
		} catch (Exception exception) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	/**
	 * Method do expose a Endpoint to  save a new Log
	 *
	 * @param logDTO LogDTO Object to save
	 * @param errors Errors from the request
	 * @return A new LogDTO Object saved in the database
	 */
	@PostMapping(produces = "application/json", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = LogDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BodyValidationErrorAPI.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Save a new Log in the database")
	public ResponseEntity<?> saveOneLog(
			@Valid @RequestBody LogDTO logDTO,
			@ApiIgnore Errors errors) {

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

	/**
	 * Method do expose a Endpoint to  update a log
	 *
	 * @param id     Id to use in the Update
	 * @param logDTO LogDTO Object to save
	 * @param errors Errors from the request
	 * @return no Content if the object was updated
	 */
	@PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request", response = BodyValidationErrorAPI.class),
			@ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Edit one log with the ID in the Path with the values passed on body")
	public ResponseEntity<?> editOneLog(
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody LogDTO logDTO,
			@ApiIgnore Errors errors) {

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


	/**
	 * Method do expose a Endpoint to  delete a log
	 *
	 * @param id ID of the log to be deleted
	 * @return no Content if the object was deleted
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Remove one Log from the database with the ID in the Path")
	public ResponseEntity<?> deleteLog(
			@PathVariable(value = "id") Long id) {

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

	/**
	 * Method to return all Ips saved in the database
	 *
	 * @return LIst of Distinct IPS
	 */
	@GetMapping(value = "/get-ips", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Lisl all registred IP´s")
	public ResponseEntity<?> findAllDistinctIP() {
		Slf4jLog.info("=== Find all Distinct IP");
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(this.logService.findAllDistinctIps());
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	/**
	 * Method to return list of UserAgent with count in that IP
	 *
	 * @param ip IP used to the search
	 * @return LIst of Distinct IPS
	 */
	@GetMapping(value = "/user-agent-dashboard", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Information about User Agent by IP")
	public ResponseEntity<?> userAgentDashboards(
			@RequestParam(value = "ip")
			@ApiParam(value = "ip", example = "192.168.0.125") String ip) {

		Slf4jLog.info("=== UserAgentDashboards by IP: " + ip);
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(this.logService.userAgentDashboardsByIP(ip));
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	/**
	 * Method to return list of Hour with count in that IP
	 *
	 * @param ip IP used to the search
	 * @return LIst of Distinct IPS
	 */
	@GetMapping(value = "/hour-dashboard", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = List.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@ApiOperation(value = "Information about Hours by IP")
	public ResponseEntity<?> hourDashboards(
			@RequestParam(value = "ip")
			@ApiParam(value = "ip", example = "192.168.0.125") String ip) {

		Slf4jLog.info("=== UserAgentDashboards by IP: " + ip);
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(this.logService.hourDashboardsByIP(ip));
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}


	/**
	 * @param file File Uploaded
	 * @return No content
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseStatusException.class)
	})
	@PostMapping("/upload-file")
	@ApiOperation(value = "Upload a File")
	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) {

		Slf4jLog.info("=== File Upload: ");
		try {
			this.logService.uploadFile(file);
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.build();
		} catch (Exception exception) {

			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	/**
	 * Method to return a custom exception object when a validation of the entity goes wrong
	 *
	 * @param errors Errors from the request
	 * @return custom exception object
	 */
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
