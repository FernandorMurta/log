package br.frmurta.log.rest;

import br.frmurta.log.model.Log;
import br.frmurta.log.model.LogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public interface LogService {

	/**
	 * Method to find one Log by ID
	 *
	 * @param id ID to use in the Search
	 * @return DTO Object from the Log found
	 */
	LogDTO findOneLog(Long id);

	/**
	 * Method to save a new Log
	 *
	 * @param logDTO DTO Object representing a new Log entity
	 * @return DTO Object representing a new saved Log entity
	 */
	LogDTO saveLog(LogDTO logDTO);

	/**
	 * Method to update a Log
	 *
	 * @param id     ID to use in the update
	 * @param logDTO DTO Object representing a new Log entity
	 */
	void updateLog(Long id, LogDTO logDTO);

	/**
	 * Method to delete a Log
	 *
	 * @param id ID to use in the update
	 */
	void deleteLog(Long id);

	/**
	 * Method to find Logs with parameters
	 *
	 * @param dateStart Date start to the range
	 * @param dateEnd   Date end to the range
	 * @param ip        IP used
	 * @param pageable  Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	Page<Log> findAllWithParams(Date dateStart, Date dateEnd, String ip, Pageable pageable);
}
