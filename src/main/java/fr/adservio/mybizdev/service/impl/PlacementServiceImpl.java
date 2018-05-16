package fr.adservio.mybizdev.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adservio.mybizdev.domain.Consultant;
import fr.adservio.mybizdev.domain.Placement;
import fr.adservio.mybizdev.domain.enumeration.Statut;
import fr.adservio.mybizdev.repository.PlacementRepository;
import fr.adservio.mybizdev.service.ConsultantService;
import fr.adservio.mybizdev.service.PlacementService;

/**
 * Service Implementation for managing Placement.
 */
@Service
@Transactional
public class PlacementServiceImpl implements PlacementService {

	private final Logger log = LoggerFactory.getLogger(PlacementServiceImpl.class);

	private final PlacementRepository placementRepository;
	@Autowired
	private ConsultantService consultantService;

	public PlacementServiceImpl(PlacementRepository placementRepository) {
		this.placementRepository = placementRepository;
	}

	/**
	 * Save a placement.
	 *
	 * @param placement
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public Placement save(Placement placement) {
		log.debug("Request to save Placement : {}", placement);
		return placementRepository.save(placement);
	}

	/**
	 * Save a list of placements.
	 *
	 * @param placements
	 *            the array of placement entities to save
	 * @return the array of persisted entities
	 */
	@Override
	public List<Placement> save(List<Placement> placements) {
		log.debug("Request to save array of Placements : {}", placements);
		return placementRepository.save(placements);
	}

	/**
	 * Get all the placements.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Placement> findAll(Pageable pageable) {
		log.debug("Request to get all Placements");
		return placementRepository.findAll(pageable);
	}

	/**
	 * Get one placement by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Placement findOne(Long id) {
		log.debug("Request to get Placement : {}", id);
		return placementRepository.findOne(id);
	}

	/**
	 * Delete the placement by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Placement : {}", id);
		placementRepository.delete(id);
	}

	/**
	 * Archive the "id" placement.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public boolean archive(Long id) {
		log.debug("Request to archive Placement : {}", id);
		final Placement placement = findOne(id);
		if (placement != null) {
			placement.setArchived(true);
			return true;
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Placement goInMission(Long id, Integer tjmFinal) {
		final Placement placement = findOne(id);
		if (placement != null) {
			placement.setEtat(Statut.GO);
			placement.setTjmFinal(tjmFinal);
			if (placement.getConsultant() != null && placement.getConsultant().getId() != null) {
				final Consultant consultant = consultantService.findOne(placement.getConsultant().getId());
				if (consultant != null) {
					archiveOtherPlacements(placement.getId(), consultant.getId());
				}
			}
		}
		return placement;
	}

	private void archiveOtherPlacements(Long placementId, Long consultantId) {
		placementRepository.archiveOtherPlacements(placementId, consultantId);
	}
}
