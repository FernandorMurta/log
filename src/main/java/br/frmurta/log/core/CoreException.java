package br.frmurta.log.core;


import br.frmurta.log.util.LoggerMessage;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public abstract class CoreException extends Exception {

	/**
	 * Constructor
	 */
	public CoreException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 */
	public CoreException(String message) {
		super(message);
		this.logError(message);
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 * @param error   Caused Error
	 */
	public CoreException(String message, Throwable error) {
		super(message, error);
		this.logError(message);
		this.logTrace(error);
	}

	/**
	 * Send the Error to the Log
	 *
	 * @param message Message of error
	 */
	private void logError(String message) {
		LoggerMessage.error(message);
	}

	/**
	 * Send the Trace of the error to the Log
	 *
	 * @param error Error
	 */
	private void logTrace(Throwable error) {
		LoggerMessage.trace(error);
	}
}
