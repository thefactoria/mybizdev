package fr.adservio.mybizdev.repository;

import fr.adservio.mybizdev.domain.Etat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Etat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatRepository extends JpaRepository<Etat, Long> {

}
