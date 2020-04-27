package br.frmurta.log;

import br.frmurta.log.exceptions.LogIdDoesNotMatchException;
import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.Log;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.repository.LogRepository;
import br.frmurta.log.rest.LogServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

	@Mock
	LogRepository logRepository;

	@InjectMocks
	@MockBean
	LogServiceImpl logService;

	Log log;

	LogDTO logDTO;

	/**
	 * Method to build objects before the execution of the test
	 */
	@Before
	public void init() {
		this.log = Log.builder()
				.id(1L)
				.logDate(new Date())
				.createdAt(new Date())
				.updatedAt(new Date())
				.status("200")
				.ip("192.168.0.0")
				.request("GET")
				.userAgent("USER AGENT")
				.build();

		this.logDTO = LogDTO.fromEntity(this.log);
	}

	/**
	 * Create a new Log
	 */
	@Test
	public void creatingNewLog() {
		Mockito.when(this.logRepository.save(this.log)).thenReturn(this.log);

		LogDTO log = this.logService.saveLog(this.logDTO);

		Assert.assertEquals(log, this.logDTO);
		Assert.assertEquals(LogDTO.toEntity(log), this.log);
	}

	/**
	 * Method to test if a Log will be created without a required value
	 */
	@Test(expected = NullPointerException.class)
	public void creatingNewLogButThrowErrorBecauseIPNotFound() {
		Log log = Log.builder()
				.id(1L)
				.logDate(new Date())
				.createdAt(new Date())
				.updatedAt(new Date())
				.status("200")
				.request("GET")
				.userAgent("USER AGENT")
				.build();

		Mockito.when(this.logRepository.save(log)).thenReturn(this.log);

		this.logService.saveLog(this.logDTO);

	}

	/**
	 * Method to test if a Log will be created without a required value
	 */
	@Test(expected = NullPointerException.class)
	public void creatingNewLogButThrowErrorBecauseStatusNotFound() {
		Log log = Log.builder()
				.id(1L)
				.logDate(new Date())
				.createdAt(new Date())
				.updatedAt(new Date())
				.ip("192.168.0.0")
				.request("GET")
				.userAgent("USER AGENT")
				.build();

		Mockito.when(this.logRepository.save(log)).thenReturn(this.log);

		this.logService.saveLog(this.logDTO);

	}

	/**
	 * Method to test if a Log will be created without a required value
	 */
	@Test(expected = NullPointerException.class)
	public void creatingNewLogButThrowErrorBecauseRequestNotFound() {
		Log log = Log.builder()
				.id(1L)
				.logDate(new Date())
				.createdAt(new Date())
				.updatedAt(new Date())
				.ip("192.168.0.0")
				.status("200")
				.userAgent("USER AGENT")
				.build();

		Mockito.when(this.logRepository.save(log)).thenReturn(this.log);

		this.logService.saveLog(this.logDTO);

	}

	/**
	 * Method to test if a Log will be created without a required value
	 */
	@Test(expected = NullPointerException.class)
	public void creatingNewLogButThrowErrorBecauseUserAgentNotFound() {
		Log log = Log.builder()
				.id(1L)
				.logDate(new Date())
				.createdAt(new Date())
				.updatedAt(new Date())
				.ip("192.168.0.0")
				.status("200")
				.request("GET")
				.build();

		Mockito.when(this.logRepository.save(log)).thenReturn(this.log);

		this.logService.saveLog(this.logDTO);
	}

	/**
	 * Update a Log
	 */
	@Test
	public void updateLog() {
		Mockito.when(this.logRepository.save(this.log)).thenReturn(this.log);
		Mockito.when(this.logRepository.findById(this.log.getId())).thenReturn(Optional.of(this.log));

		this.logService.updateLog(this.logDTO.getId(), this.logDTO);
	}

	/**
	 * Update a Log but the Log are not found
	 */
	@Test(expected = LogNotFoundException.class)
	public void updateLogButErrorBecauseLogAreNotFound() {
		Mockito.when(this.logRepository.save(this.log)).thenReturn(this.log);
		Mockito.when(this.logRepository.findById(this.log.getId())).thenThrow(LogNotFoundException.class);

		this.logService.updateLog(this.logDTO.getId(), this.logDTO);
	}

	/**
	 * Update a Log but the ID in the parameter are different
	 */
	@Test(expected = LogIdDoesNotMatchException.class)
	public void updateLogButErrorBecauseIdAreDifferent() {
		Mockito.when(this.logRepository.save(this.log)).thenReturn(this.log);
		Mockito.when(this.logRepository.findById(this.log.getId())).thenReturn(Optional.of(this.log));

		this.logService.updateLog(2L, this.logDTO);
	}

	/**
	 * Find one log by the Id
	 */
	@Test
	public void findOneLogById() {
		Mockito.when(this.logRepository.findById(this.log.getId())).thenReturn(Optional.of(this.log));

		LogDTO log = this.logService.findOneLog(this.logDTO.getId());

		Assert.assertEquals(log, this.logDTO);
	}

	/**
	 * Find one log by the Id but the log are not found
	 */
	@Test(expected = LogNotFoundException.class)
	public void findOneLogByIdButErrorBecauseLogAreNotFound() {

		Mockito.when(this.logRepository.findById(this.log.getId())).thenThrow(LogNotFoundException.class);

		this.logService.findOneLog(this.logDTO.getId());
	}


	/**
	 * Delete a Log
	 */
	@Test
	public void deleteLogById() {
		Mockito.when(this.logRepository.findById(this.log.getId())).thenReturn(Optional.of(this.log));

		this.logService.deleteLog(this.logDTO.getId());
	}

	/**
	 * Delete a Log but log are not found
	 */
	@Test(expected = LogNotFoundException.class)
	public void deleteLogByIdButErrorBecauseLogAreNotFound() {

		Mockito.when(this.logRepository.findById(this.log.getId())).thenThrow(LogNotFoundException.class);

		this.logService.deleteLog(this.logDTO.getId());
	}


}
