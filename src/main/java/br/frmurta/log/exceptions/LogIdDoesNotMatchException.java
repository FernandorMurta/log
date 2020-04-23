package br.frmurta.log.exceptions;


import br.frmurta.log.core.CoreRuntimeException;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public class LogIdDoesNotMatchException extends CoreRuntimeException {

	private static final String _DEFAULT_MSG = "ID enviado na entidade não é igual ao id enviado na request!";

	/**
	 * Constructor
	 */
	public LogIdDoesNotMatchException() {
		super(_DEFAULT_MSG);
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 */
	public LogIdDoesNotMatchException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 * @param error   Caused Error
	 */
	public LogIdDoesNotMatchException(String message, Throwable error) {
		super(message, error);
	}

}
