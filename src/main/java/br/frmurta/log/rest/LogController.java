package br.frmurta.log.rest;

import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.util.Slf4jLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

	@GetMapping(value = "/{id}")
	public ResponseEntity<LogDTO> findOneLog(@PathVariable(value = "id") Long id) {
		Slf4jLog.info("=== Find one Log with ID " + id);
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(this.logService.findOneLog(id));
		} catch (LogNotFoundException ex) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		} catch (Exception exception) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}
}
