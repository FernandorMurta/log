package br.frmurta.log.core;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public abstract class QueryDslSupport extends QuerydslRepositorySupport {
	/**
	 * Constructor
	 *
	 * @param domainClass Class used on QueryDslOperations
	 */
	public QueryDslSupport(Class<?> domainClass) {
		super(domainClass);
	}
}