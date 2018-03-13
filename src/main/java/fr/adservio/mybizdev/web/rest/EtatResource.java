package fr.adservio.mybizdev.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.adservio.mybizdev.domain.Etat;

import fr.adservio.mybizdev.repository.EtatRepository;
import fr.adservio.mybizdev.web.rest.errors.BadRequestAlertException;
import fr.adservio.mybizdev.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Etat.
 */
@RestController
@RequestMapping("/api")
public class EtatResource {

    private final Logger log = LoggerFactory.getLogger(EtatResource.class);

    private static final String ENTITY_NAME = "etat";

    private final EtatRepository etatRepository;

    public EtatResource(EtatRepository etatRepository) {
        this.etatRepository = etatRepository;
    }

    /**
     * POST  /etats : Create a new etat.
     *
     * @param etat the etat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etat, or with status 400 (Bad Request) if the etat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etats")
    @Timed
    public ResponseEntity<Etat> createEtat(@Valid @RequestBody Etat etat) throws URISyntaxException {
        log.debug("REST request to save Etat : {}", etat);
        if (etat.getId() != null) {
            throw new BadRequestAlertException("A new etat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etat result = etatRepository.save(etat);
        return ResponseEntity.created(new URI("/api/etats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etats : Updates an existing etat.
     *
     * @param etat the etat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etat,
     * or with status 400 (Bad Request) if the etat is not valid,
     * or with status 500 (Internal Server Error) if the etat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etats")
    @Timed
    public ResponseEntity<Etat> updateEtat(@Valid @RequestBody Etat etat) throws URISyntaxException {
        log.debug("REST request to update Etat : {}", etat);
        if (etat.getId() == null) {
            return createEtat(etat);
        }
        Etat result = etatRepository.save(etat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etats : get all the etats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of etats in body
     */
    @GetMapping("/etats")
    @Timed
    public List<Etat> getAllEtats() {
        log.debug("REST request to get all Etats");
        return etatRepository.findAll();
        }

    /**
     * GET  /etats/:id : get the "id" etat.
     *
     * @param id the id of the etat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etat, or with status 404 (Not Found)
     */
    @GetMapping("/etats/{id}")
    @Timed
    public ResponseEntity<Etat> getEtat(@PathVariable Long id) {
        log.debug("REST request to get Etat : {}", id);
        Etat etat = etatRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etat));
    }

    /**
     * DELETE  /etats/:id : delete the "id" etat.
     *
     * @param id the id of the etat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etats/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtat(@PathVariable Long id) {
        log.debug("REST request to delete Etat : {}", id);
        etatRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
