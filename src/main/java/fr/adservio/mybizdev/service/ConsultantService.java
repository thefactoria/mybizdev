package fr.adservio.mybizdev.service;

import fr.adservio.mybizdev.domain.Consultant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Consultant.
 */
public interface ConsultantService {

    /**
     * Save a consultant.
     *
     * @param consultant the entity to save
     * @return the persisted entity
     */
    Consultant save(Consultant consultant);

    /**
     * Get all the consultants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Consultant> findAll(Pageable pageable);

    /**
     * Get the "id" consultant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Consultant findOne(Long id);

    /**
     * Delete the "id" consultant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
