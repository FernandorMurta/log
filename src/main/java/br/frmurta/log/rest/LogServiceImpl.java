package br.frmurta.log.rest;

import br.frmurta.log.exceptions.LogIdDoesNotMatchException;
import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.HourDashboard;
import br.frmurta.log.model.Log;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.model.UserAgentDashboard;
import br.frmurta.log.repository.LogRepository;
import br.frmurta.log.util.ReadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Service
public class LogServiceImpl implements LogService {

	private final LogRepository logRepository;

	private final Path rootLocation = Paths.get("./upload/");

	/**
	 * Constructor to DI
	 *
	 * @param logRepository Log Repository Implementation
	 */
	@Autowired
	LogServiceImpl(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	/**
	 * Method to find one Log by ID
	 *
	 * @param id ID to use in the Search
	 * @return DTO Object from the Log found
	 */
	@Override
	public LogDTO findOneLog(Long id) {
		return LogDTO.fromEntity(this.logRepository.findById(id).orElseThrow(LogNotFoundException::new));
	}

	/**
	 * Method to save a new Log
	 *
	 * @param logDTO DTO Object representing a new Log entity
	 * @return DTO Object representing a new saved Log entity
	 */
	@Override
	public LogDTO saveLog(LogDTO logDTO) {
		return LogDTO.fromEntity(this.logRepository.save(LogDTO.toEntity(logDTO)));
	}

	/**
	 * Method to update a Log
	 *
	 * @param id     ID to use in the update
	 * @param logDTO DTO Object representing a new Log entity
	 */
	@Override
	public void updateLog(Long id, LogDTO logDTO) {
		if (!id.equals(logDTO.getId())) {
			throw new LogIdDoesNotMatchException();
		}
		this.logRepository.findById(id).orElseThrow(LogNotFoundException::new);
		this.logRepository.save(LogDTO.toEntity(logDTO));
	}

	/**
	 * Method to delete a Log
	 *
	 * @param id ID to use in the update
	 */
	@Override
	public void deleteLog(Long id) {
		this.logRepository.findById(id).orElseThrow(LogNotFoundException::new);
		this.logRepository.deleteById(id);
	}

	/**
	 * Method to find Logs with parameters
	 *
	 * @param dateStart Date start to the range
	 * @param dateEnd   Date end to the range
	 * @param ip        IP used
	 * @param pageable  Pageable object with Page and Qtd of results
	 * @return A Page Object
	 */
	@Override
	public Page<Log> findAllWithParams(Date dateStart, Date dateEnd, String ip, Pageable pageable) {
		if (dateStart == null && dateEnd == null) {
			return this.logRepository.findAllByIpContainingOrderByLogDate(ip, pageable);
		} else if (dateStart == null) {
			return this.logRepository.findAllByLogDateBeforeAndIpContainingOrderByLogDate(dateEnd, ip, pageable);
		} else if (dateEnd == null) {
			return this.logRepository.findAllByLogDateAfterAndIpContainingOrderByLogDate(dateStart, ip, pageable);
		}
		return this.logRepository.findAllByLogDateBetweenAndIpContainingOrderByLogDate(dateStart, dateEnd, ip, pageable);
	}

	/**
	 * Method to return all Ips saved in the database
	 *
	 * @return LIst of Distinct IPS
	 */
	@Override
	public List<String> findAllDistinctIps() {
		return this.logRepository.findAllDistinctIps();
	}

	/**
	 * Method to return a UserAgentDashboard based in a IP
	 *
	 * @param ip IP to use as parameter
	 * @return List of UserAgent to use in a dashboard
	 */
	@Override
	public List<UserAgentDashboard> userAgentDashboardsByIP(String ip) {

		List<Log> logs = this.logRepository.findAllByIp(ip);

		return logs.stream()
				.filter(distinctByKey(Log::getUserAgent))
				.map(element -> new UserAgentDashboard(
						element.getUserAgent(),
						countUserAgentResults(logs, element.getUserAgent())))
				.collect(Collectors.toList());
	}

	/**
	 * Method to return a UserAgentDashboard based in a IP
	 *
	 * @param ip IP to use as parameter
	 * @return List of Hour to use in a dashboard
	 */
	public List<HourDashboard> hourDashboardsByIP(String ip) {

		List<Log> logs = this.logRepository.findAllByIp(ip);

		Function<Log, Integer> functionToDistinctHours = p -> {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(p.getLogDate());
			return calendar.get(Calendar.HOUR_OF_DAY);
		};

		return logs.stream()
				.filter(distinctByKey(functionToDistinctHours))
				.map(functionToDistinctHours)
				.map(element -> new HourDashboard(
						element,
						countHoursResults(logs, element)))
				.collect(Collectors.toList());
	}

	/**
	 * Method to upload a file
	 *
	 * @param file File to be upload
	 */
	@Override
	public void uploadFile(MultipartFile file) throws IOException {

		Files.copy(file.getInputStream(), this.rootLocation.resolve(Objects.requireNonNull(file.getOriginalFilename())));
		String path = this.rootLocation.toString();
		File fileUploaded = new File(path + "\\" + file.getOriginalFilename());
		ReadFile read = new ReadFile(this.logRepository);
		read.setFile(fileUploaded);
		Thread thread = new Thread(read);
		thread.start();
	}

	/**
	 * Method to filter a list by some key
	 *
	 * @param keyExtractor Key used to be distinct
	 * @param <T>          Generic object
	 * @return Predicate to be used as parameter
	 */
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	/**
	 * Method to count the number os matches on that User Agent Result
	 *
	 * @param logs  List of logs
	 * @param param Parameter used to count
	 * @return Number of matches
	 */
	private static Long countUserAgentResults(List<Log> logs, String param) {
		Predicate<Log> predicate = log -> log.getUserAgent().equals(param);

		return logs.stream()
				.filter(predicate)
				.count();
	}

	/**
	 * Method to count the number os matches on that Hour
	 *
	 * @param logs  List of logs
	 * @param param Parameter used to count
	 * @return Number of matches
	 */
	private static Long countHoursResults(List<Log> logs, Integer param) {
		Predicate<Log> predicate = log -> {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(log.getLogDate());
			return calendar.get(Calendar.HOUR_OF_DAY) == param;
		};

		return logs.stream()
				.filter(predicate)
				.count();
	}
}
