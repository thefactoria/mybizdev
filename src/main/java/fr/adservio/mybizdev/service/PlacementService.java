package fr.adservio.mybizdev.service;

import fr.adservio.mybizdev.domain.Placement;
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
}
