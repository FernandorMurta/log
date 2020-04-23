package br.frmurta.log.repository;

import br.frmurta.log.core.QueryDslSupport;
import br.frmurta.log.model.Log;


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
}
