package fr.adservio.mybizdev.repository;

import fr.adservio.mybizdev.domain.BizDev;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BizDev entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BizDevRepository extends JpaRepository<BizDev, Long> {

}
