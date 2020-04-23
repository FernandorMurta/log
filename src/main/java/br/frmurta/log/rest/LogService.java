package br.frmurta.log.rest;

import br.frmurta.log.model.LogDTO;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
public interface LogService {

	LogDTO findOneLog(Long id);

	LogDTO saveLog(LogDTO logDTO);

	void updateLog(Long id, LogDTO logDTO);

	void deleteLog(Long id);
}
