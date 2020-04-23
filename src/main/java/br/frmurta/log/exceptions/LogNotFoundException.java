package br.frmurta.log.exceptions;


import br.frmurta.log.core.CoreRuntimeException;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public class LogNotFoundException extends CoreRuntimeException {

	private static final String _DEFAULT_MSG = "Log não encontrado com este ID";

	/**
	 * Constructor
	 */
	public LogNotFoundException() {
		super(_DEFAULT_MSG);
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 */
	public LogNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 * @param error   Caused Error
	 */
	public LogNotFoundException(String message, Throwable error) {
		super(message, error);
	}

}
