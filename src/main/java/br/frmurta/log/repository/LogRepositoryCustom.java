package br.frmurta.log.repository;


import java.util.List;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public interface LogRepositoryCustom {

	/**
	 * Method to return all Ips saved in the database
	 *
	 * @return LIst of Distinct IPS
	 */
	List<String> findAllDistinctIps();
}
