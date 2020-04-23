package br.frmurta.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@SpringBootApplication
public class LogApplication {

	/**
	 * Method to run the application
	 *
	 * @param args Arguments passed in the initialization
	 */
	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);
	}

}
