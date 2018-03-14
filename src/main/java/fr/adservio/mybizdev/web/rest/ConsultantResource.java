package fr.adservio.mybizdev.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.adservio.mybizdev.domain.Consultant;
import fr.adservio.mybizdev.service.ConsultantService;
import fr.adservio.mybizdev.web.rest.errors.BadRequestAlertException;
import fr.adservio.mybizdev.web.rest.util.HeaderUtil;
import fr.adservio.mybizdev.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Consultant.
 */
@RestController
@RequestMapping("/api")
public class ConsultantResource {

    private final Logger log = LoggerFactory.getLogger(ConsultantResource.class);

    private static final String ENTITY_NAME = "consultant";

    private final ConsultantService consultantService;

    public ConsultantResource(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    /**
     * POST  /consultants : Create a new consultant.
     *
     * @param consultant the consultant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultant, or with status 400 (Bad Request) if the consultant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultants")
    @Timed
    public ResponseEntity<Consultant> createConsultant(@Valid @RequestBody Consultant consultant) throws URISyntaxException {
        log.debug("REST request to save Consultant : {}", consultant);
        if (consultant.getId() != null) {
            throw new BadRequestAlertException("A new consultant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consultant result = consultantService.save(consultant);
        return ResponseEntity.created(new URI("/api/consultants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultants : Updates an existing consultant.
     *
     * @param consultant the consultant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultant,
     * or with status 400 (Bad Request) if the consultant is not valid,
     * or with status 500 (Internal Server Error) if the consultant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultants")
    @Timed
    public ResponseEntity<Consultant> updateConsultant(@Valid @RequestBody Consultant consultant) throws URISyntaxException {
        log.debug("REST request to update Consultant : {}", consultant);
        if (consultant.getId() == null) {
            return createConsultant(consultant);
        }
        Consultant result = consultantService.save(consultant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultants : get all the consultants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultants in body
     */
    @GetMapping("/consultants")
    @Timed
    public ResponseEntity<List<Consultant>> getAllConsultants(Pageable pageable) {
        log.debug("REST request to get a page of Consultants");
        Page<Consultant> page = consultantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultants/:id : get the "id" consultant.
     *
     * @param id the id of the consultant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultant, or with status 404 (Not Found)
     */
    @GetMapping("/consultants/{id}")
    @Timed
    public ResponseEntity<Consultant> getConsultant(@PathVariable Long id) {
        log.debug("REST request to get Consultant : {}", id);
        Consultant consultant = consultantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultant));
    }

    /**
     * DELETE  /consultants/:id : delete the "id" consultant.
     *
     * @param id the id of the consultant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultants/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultant(@PathVariable Long id) {
        log.debug("REST request to delete Consultant : {}", id);
        consultantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
