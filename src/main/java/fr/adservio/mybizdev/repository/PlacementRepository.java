package fr.adservio.mybizdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.adservio.mybizdev.domain.Placement;

/**
 * Spring Data JPA repository for the Placement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {

	@Modifying
	@Query("update Placement p set p.archived = true where p.id != :placementId and p.consultant.id = :consultantId")
	void archiveOtherPlacements(@Param("placementId") Long placementId, @Param("consultantId") Long consultantId);

}
