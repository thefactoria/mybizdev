package fr.adservio.mybizdev.service;

import fr.adservio.mybizdev.domain.Placement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Placement.
 */
public interface PlacementService {

    /**
     * Save a placement.
     *
     * @param placement the entity to save
     * @return the persisted entity
     */
    Placement save(Placement placement);
    

    /**
     * Save a list of placements.
     *
     * @param placements the array of placement entities to save
     * @return the array of persisted entities
     */
	List<Placement> save(List<Placement> placements);

    /**
     * Get all the placements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Placement> findAll(Pageable pageable);

    /**
     * Get the "id" placement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Placement findOne(Long id);

    /**
     * Delete the "id" placement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Archive the "id" placement.
     *
     * @param id the id of the entity
     */
	boolean archive(Long id);


	Placement goInMission(Long id, Integer tjmFinal);


	List<Placement> findAllPlacementsForConsultant(Long consultantId);
}
