package br.frmurta.log.rest;

import br.frmurta.log.model.Log;
import br.frmurta.log.model.LogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public interface LogService {

	LogDTO findOneLog(Long id);

	LogDTO saveLog(LogDTO logDTO);

	void updateLog(Long id, LogDTO logDTO);

	void deleteLog(Long id);

	Page<Log> findAllWithParams(Date dateStart, Date dateEnd, String ip, Pageable pageable);
}
