package fr.adservio.mybizdev.repository;

import fr.adservio.mybizdev.domain.Consultant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Consultant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

}
