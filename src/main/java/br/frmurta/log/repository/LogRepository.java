package br.frmurta.log.repository;

import br.frmurta.log.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Fernando Murta
 * @version 0.0.1
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

	Page<Log> findAllByLogDateBetweenAndIpContainingOrderByCreatedAt(Date dateStart, Date dateEnd, String ip, Pageable pageable);

	Page<Log> findAllByLogDateAfterAndIpContainingOrderByCreatedAt(Date dateStart, String ip, Pageable pageable);

	Page<Log> findAllByLogDateBeforeAndIpContainingOrderByCreatedAt(Date dateEnd, String ip, Pageable pageable);

	Page<Log> findAllByIpContainingOrderByCreatedAt(String ip, Pageable pageable);
}
