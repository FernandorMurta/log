package br.frmurta.log.repository;

import br.frmurta.log.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Repository
public interface LogRepository extends QuerydslPredicateExecutor<Log>, JpaRepository<Log, Long>, LogRepositoryCustom {

	/**
	 * Method to find Logs with parameters
	 *
	 * @param dateStart Date start to the range
	 * @param dateEnd   Date end to the range
	 * @param ip        IP used
	 * @param pageable  Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	Page<Log> findAllByLogDateBetweenAndIpContainingOrderByLogDate(Date dateStart, Date dateEnd, String ip, Pageable pageable);

	/**
	 * Method to find Logs with parameters
	 *
	 * @param dateStart Date start to the range
	 * @param ip        IP used
	 * @param pageable  Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	Page<Log> findAllByLogDateAfterAndIpContainingOrderByLogDate(Date dateStart, String ip, Pageable pageable);

	/**
	 * Method to find Logs with parameters
	 *
	 * @param dateEnd  Date end to the range
	 * @param ip       IP used
	 * @param pageable Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	Page<Log> findAllByLogDateBeforeAndIpContainingOrderByLogDate(Date dateEnd, String ip, Pageable pageable);

	/**
	 * Method to find Logs with parameters
	 *
	 * @param ip       IP used
	 * @param pageable Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	Page<Log> findAllByIpContainingOrderByLogDate(String ip, Pageable pageable);

	/**
	 * Return a list of log´s from that IP
	 *
	 * @param ip IP to use in search
	 * @return List of Logs
	 */
	List<Log> findAllByIp(String ip);
}
