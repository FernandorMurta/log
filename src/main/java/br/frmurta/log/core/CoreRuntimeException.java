package br.frmurta.log.core;


import br.frmurta.log.util.Slf4jLog;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public abstract class CoreRuntimeException extends RuntimeException {

	/**
	 * Constructor
	 */
	public CoreRuntimeException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 */
	public CoreRuntimeException(String message) {
		super(message);
		this.logError(message);
	}

	/**
	 * Constructor
	 *
	 * @param message Message from error
	 * @param error   Caused Error
	 */
	public CoreRuntimeException(String message, Throwable error) {
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
		Slf4jLog.error(message);
	}

	/**
	 * Send the Trace of the error to the Log
	 *
	 * @param error Error
	 */
	private void logTrace(Throwable error) {
		Slf4jLog.trace(error);
	}
}
