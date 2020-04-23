package br.frmurta.log.rest;

import br.frmurta.log.exceptions.LogNotFoundException;
import br.frmurta.log.model.LogDTO;
import br.frmurta.log.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Service
public class LogServiceImpl implements LogService {

	private final LogRepository logRepository;

	@Autowired
	LogServiceImpl(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@Override
	public LogDTO findOneLog(Long id) {
		return LogDTO.fromEntity(this.logRepository.findById(id).orElseThrow(LogNotFoundException::new));
	}

	@Override
	public LogDTO saveLog(LogDTO logDTO) {
		return LogDTO.fromEntity(this.logRepository.save(LogDTO.toEntity(logDTO)));
	}
}
