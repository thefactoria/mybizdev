package fr.adservio.mybizdev.repository;

import fr.adservio.mybizdev.domain.Placement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Placement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {

}
