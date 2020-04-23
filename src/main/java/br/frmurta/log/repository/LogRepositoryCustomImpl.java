package br.frmurta.log.repository;

import br.frmurta.log.core.QueryDslSupport;
import br.frmurta.log.model.Log;
import br.frmurta.log.model.QLog;

import java.util.List;
import java.util.Objects;


/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public class LogRepositoryCustomImpl extends QueryDslSupport implements LogRepositoryCustom {

	/**
	 * Constructor
	 */
	public LogRepositoryCustomImpl() {
		super(Log.class);
	}

	/**
	 * Method to return all Ips saved in the database
	 *
	 * @return LIst of Distinct IPS
	 */
	@Override
	public List<String> findAllDistinctIps() {

		QLog log = QLog.log;

		return Objects.requireNonNull(getQuerydsl()).createQuery()
				.select(log.ip)
				.from(log)
				.distinct()
				.fetch();
	}
}
