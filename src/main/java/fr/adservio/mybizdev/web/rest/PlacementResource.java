package fr.adservio.mybizdev.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.adservio.mybizdev.domain.Placement;
import fr.adservio.mybizdev.service.PlacementService;
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
 * REST controller for managing Placement.
 */
@RestController
@RequestMapping("/api")
public class PlacementResource {

    private final Logger log = LoggerFactory.getLogger(PlacementResource.class);

    private static final String ENTITY_NAME = "placement";

    private final PlacementService placementService;

    public PlacementResource(PlacementService placementService) {
        this.placementService = placementService;
    }

    /**
     * POST  /placements : Create a new placement.
     *
     * @param placement the placement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new placement, or with status 400 (Bad Request) if the placement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/placements")
    @Timed
    public ResponseEntity<Placement> createPlacement(@Valid @RequestBody Placement placement) throws URISyntaxException {
        log.debug("REST request to save Placement : {}", placement);
        if (placement.getId() != null) {
            throw new BadRequestAlertException("A new placement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Placement result = placementService.save(placement);
        return ResponseEntity.created(new URI("/api/placements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /placements : Updates an existing placement.
     *
     * @param placement the placement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated placement,
     * or with status 400 (Bad Request) if the placement is not valid,
     * or with status 500 (Internal Server Error) if the placement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/placements")
    @Timed
    public ResponseEntity<Placement> updatePlacement(@Valid @RequestBody Placement placement) throws URISyntaxException {
        log.debug("REST request to update Placement : {}", placement);
        if (placement.getId() == null) {
            return createPlacement(placement);
        }
        Placement result = placementService.save(placement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, placement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /placements : get all the placements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of placements in body
     */
    @GetMapping("/placements")
    @Timed
    public ResponseEntity<List<Placement>> getAllPlacements(Pageable pageable) {
        log.debug("REST request to get a page of Placements");
        Page<Placement> page = placementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/placements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /placements/:id : get the "id" placement.
     *
     * @param id the id of the placement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the placement, or with status 404 (Not Found)
     */
    @GetMapping("/placements/{id}")
    @Timed
    public ResponseEntity<Placement> getPlacement(@PathVariable Long id) {
        log.debug("REST request to get Placement : {}", id);
        Placement placement = placementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(placement));
    }

    /**
     * DELETE  /placements/:id : delete the "id" placement.
     *
     * @param id the id of the placement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/placements/{id}")
    @Timed
    public ResponseEntity<Void> deletePlacement(@PathVariable Long id) {
        log.debug("REST request to delete Placement : {}", id);
        placementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}