package br.frmurta.log.rest;

import br.frmurta.log.model.HourDashboard;
import br.frmurta.log.model.Log;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.model.UserAgentDashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.List;

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

	/**
	 * Method to return all Ips saved in the database
	 *
	 * @return LIst of Distinct IPS
	 */
	List<String> findAllDistinctIps();

	/**
	 * Method to return a UserAgentDashboard based in a IP
	 *
	 * @param ip IP to use as parameter
	 * @return List of UserAgent to use in a dashboard
	 */
	List<UserAgentDashboard> userAgentDashboardsByIP(String ip);

	/**
	 * Method to return a UserAgentDashboard based in a IP
	 *
	 * @param ip IP to use as parameter
	 * @return List of Hour to use in a dashboard
	 */
	List<HourDashboard> hourDashboardsByIP(String ip);


	/**
	 * Method to upload a file
	 *
	 * @param file File to be upload
	 * @throws IOException if file is not found
	 */
	void uploadFile(MultipartFile file) throws IOException;
}
