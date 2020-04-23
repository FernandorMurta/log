package br.frmurta.log.repository;

import br.frmurta.log.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
