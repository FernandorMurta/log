package br.frmurta.log.rest;

import br.frmurta.log.exceptions.LogIdDoesNotMatchException;
import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.Log;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Service
public class LogServiceImpl implements LogService {

	private final LogRepository logRepository;

	/**
	 * Constructor to DI
	 *
	 * @param logRepository Log Repository Implementation
	 */
	@Autowired
	LogServiceImpl(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	/**
	 * Method to find one Log by ID
	 *
	 * @param id ID to use in the Search
	 * @return DTO Object from the Log found
	 */
	@Override
	public LogDTO findOneLog(Long id) {
		return LogDTO.fromEntity(this.logRepository.findById(id).orElseThrow(LogNotFoundException::new));
	}

	/**
	 * Method to save a new Log
	 *
	 * @param logDTO DTO Object representing a new Log entity
	 * @return DTO Object representing a new saved Log entity
	 */
	@Override
	public LogDTO saveLog(LogDTO logDTO) {
		return LogDTO.fromEntity(this.logRepository.save(LogDTO.toEntity(logDTO)));
	}

	/**
	 * Method to update a Log
	 *
	 * @param id     ID to use in the update
	 * @param logDTO DTO Object representing a new Log entity
	 */
	@Override
	public void updateLog(Long id, LogDTO logDTO) {
		if (!id.equals(logDTO.getId())) {
			throw new LogIdDoesNotMatchException();
		}
		this.logRepository.findById(id).orElseThrow(LogNotFoundException::new);
		this.logRepository.save(LogDTO.toEntity(logDTO));
	}

	/**
	 * Method to delete a Log
	 *
	 * @param id ID to use in the update
	 */
	@Override
	public void deleteLog(Long id) {
		this.logRepository.findById(id).orElseThrow(LogNotFoundException::new);
		this.logRepository.deleteById(id);
	}

	/**
	 * Method to find Logs with parameters
	 *
	 * @param dateStart Date start to the range
	 * @param dateEnd   Date end to the range
	 * @param ip        IP used
	 * @param pageable  Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	@Override
	public Page<Log> findAllWithParams(Date dateStart, Date dateEnd, String ip, Pageable pageable) {
		if (dateStart == null && dateEnd == null) {
			return this.logRepository.findAllByIpContainingOrderByCreatedAt(ip, pageable);
		} else if (dateStart == null) {
			return this.logRepository.findAllByLogDateBeforeAndIpContainingOrderByCreatedAt(dateEnd, ip, pageable);
		} else if (dateEnd == null) {
			return this.logRepository.findAllByLogDateAfterAndIpContainingOrderByCreatedAt(dateStart, ip, pageable);
		}
		return this.logRepository.findAllByLogDateBetweenAndIpContainingOrderByCreatedAt(dateStart, dateEnd, ip, pageable);
	}
}
