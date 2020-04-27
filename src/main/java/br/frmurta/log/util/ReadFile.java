package br.frmurta.log.util;

import br.frmurta.log.model.Log;
import br.frmurta.log.repository.LogRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ReadFile implements Runnable {

	private File file;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	private LogRepository logRepository;

	/**
	 * Constructor
	 *
	 * @param logRepository Log Service to DI
	 */
	@Autowired
	public ReadFile(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		try {
			Slf4jLog.info("=== Thread Start  ");
			Scanner myReader = new Scanner(this.file);
			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split("\\|");
				Log log = Log.builder()
						.logDate(convertStringToDate(data[0]))
						.ip(data[1])
						.status(data[3])
						.request(data[2])
						.userAgent(data[4])
						.build();
				this.logRepository.save(log);
			}
		} catch (Exception e) {
			Slf4jLog.error("=== Thread Error  " + e.getMessage());
		}
	}

	/**
	 * Method to convert the String date in the File to a DA
	 *
	 * @param date Date String format from File
	 * @return Date
	 */
	private Date convertStringToDate(String date) {
		return Date.from(
				LocalDateTime.parse(date, formatter)
						.atZone(ZoneId.of("America/Sao_Paulo"))
						.toInstant());
	}
}
