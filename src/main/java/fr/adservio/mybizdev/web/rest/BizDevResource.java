package fr.adservio.mybizdev.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.adservio.mybizdev.domain.BizDev;

import fr.adservio.mybizdev.repository.BizDevRepository;
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
 * REST controller for managing BizDev.
 */
@RestController
@RequestMapping("/api")
public class BizDevResource {

    private final Logger log = LoggerFactory.getLogger(BizDevResource.class);

    private static final String ENTITY_NAME = "bizDev";

    private final BizDevRepository bizDevRepository;

    public BizDevResource(BizDevRepository bizDevRepository) {
        this.bizDevRepository = bizDevRepository;
    }

    /**
     * POST  /biz-devs : Create a new bizDev.
     *
     * @param bizDev the bizDev to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bizDev, or with status 400 (Bad Request) if the bizDev has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/biz-devs")
    @Timed
    public ResponseEntity<BizDev> createBizDev(@Valid @RequestBody BizDev bizDev) throws URISyntaxException {
        log.debug("REST request to save BizDev : {}", bizDev);
        if (bizDev.getId() != null) {
            throw new BadRequestAlertException("A new bizDev cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BizDev result = bizDevRepository.save(bizDev);
        return ResponseEntity.created(new URI("/api/biz-devs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /biz-devs : Updates an existing bizDev.
     *
     * @param bizDev the bizDev to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bizDev,
     * or with status 400 (Bad Request) if the bizDev is not valid,
     * or with status 500 (Internal Server Error) if the bizDev couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/biz-devs")
    @Timed
    public ResponseEntity<BizDev> updateBizDev(@Valid @RequestBody BizDev bizDev) throws URISyntaxException {
        log.debug("REST request to update BizDev : {}", bizDev);
        if (bizDev.getId() == null) {
            return createBizDev(bizDev);
        }
        BizDev result = bizDevRepository.save(bizDev);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bizDev.getId().toString()))
            .body(result);
    }

    /**
     * GET  /biz-devs : get all the bizDevs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bizDevs in body
     */
    @GetMapping("/biz-devs")
    @Timed
    public List<BizDev> getAllBizDevs() {
        log.debug("REST request to get all BizDevs");
        return bizDevRepository.findAll();
        }

    /**
     * GET  /biz-devs/:id : get the "id" bizDev.
     *
     * @param id the id of the bizDev to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bizDev, or with status 404 (Not Found)
     */
    @GetMapping("/biz-devs/{id}")
    @Timed
    public ResponseEntity<BizDev> getBizDev(@PathVariable Long id) {
        log.debug("REST request to get BizDev : {}", id);
        BizDev bizDev = bizDevRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bizDev));
    }

    /**
     * DELETE  /biz-devs/:id : delete the "id" bizDev.
     *
     * @param id the id of the bizDev to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/biz-devs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBizDev(@PathVariable Long id) {
        log.debug("REST request to delete BizDev : {}", id);
        bizDevRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
