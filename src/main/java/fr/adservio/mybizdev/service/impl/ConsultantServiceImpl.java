package fr.adservio.mybizdev.service.impl;

import fr.adservio.mybizdev.service.ConsultantService;
import fr.adservio.mybizdev.domain.Consultant;
import fr.adservio.mybizdev.repository.ConsultantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Consultant.
 */
@Service
@Transactional
public class ConsultantServiceImpl implements ConsultantService {

    private final Logger log = LoggerFactory.getLogger(ConsultantServiceImpl.class);

    private final ConsultantRepository consultantRepository;

    public ConsultantServiceImpl(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    /**
     * Save a consultant.
     *
     * @param consultant the entity to save
     * @return the persisted entity
     */
    @Override
    public Consultant save(Consultant consultant) {
        log.debug("Request to save Consultant : {}", consultant);
        return consultantRepository.save(consultant);
    }

    /**
     * Get all the consultants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Consultant> findAll(Pageable pageable) {
        log.debug("Request to get all Consultants");
        return consultantRepository.findAll(pageable);
    }

    /**
     * Get one consultant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Consultant findOne(Long id) {
        log.debug("Request to get Consultant : {}", id);
        return consultantRepository.findOne(id);
    }

    /**
     * Delete the consultant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultant : {}", id);
        consultantRepository.delete(id);
    }
}
