package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.BodyPointService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.BodyPointDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing BodyPoint.
 */
@RestController
@RequestMapping("/api")
public class BodyPointResource {

    private final Logger log = LoggerFactory.getLogger(BodyPointResource.class);

    private static final String ENTITY_NAME = "bodyPoint";
        
    private final BodyPointService bodyPointService;

    public BodyPointResource(BodyPointService bodyPointService) {
        this.bodyPointService = bodyPointService;
    }

    /**
     * POST  /body-points : Create a new bodyPoint.
     *
     * @param bodyPointDTO the bodyPointDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bodyPointDTO, or with status 400 (Bad Request) if the bodyPoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/body-points")
    @Timed
    public ResponseEntity<BodyPointDTO> createBodyPoint(@Valid @RequestBody BodyPointDTO bodyPointDTO) throws URISyntaxException {
        log.debug("REST request to save BodyPoint : {}", bodyPointDTO);
        if (bodyPointDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bodyPoint cannot already have an ID")).body(null);
        }
        BodyPointDTO result = bodyPointService.save(bodyPointDTO);
        return ResponseEntity.created(new URI("/api/body-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /body-points : Updates an existing bodyPoint.
     *
     * @param bodyPointDTO the bodyPointDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bodyPointDTO,
     * or with status 400 (Bad Request) if the bodyPointDTO is not valid,
     * or with status 500 (Internal Server Error) if the bodyPointDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/body-points")
    @Timed
    public ResponseEntity<BodyPointDTO> updateBodyPoint(@Valid @RequestBody BodyPointDTO bodyPointDTO) throws URISyntaxException {
        log.debug("REST request to update BodyPoint : {}", bodyPointDTO);
        if (bodyPointDTO.getId() == null) {
            return createBodyPoint(bodyPointDTO);
        }
        BodyPointDTO result = bodyPointService.save(bodyPointDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bodyPointDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /body-points : get all the bodyPoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bodyPoints in body
     */
    @GetMapping("/body-points")
    @Timed
    public List<BodyPointDTO> getAllBodyPoints() {
        log.debug("REST request to get all BodyPoints");
        return bodyPointService.findAll();
    }

    /**
     * GET  /body-points/:id : get the "id" bodyPoint.
     *
     * @param id the id of the bodyPointDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bodyPointDTO, or with status 404 (Not Found)
     */
    @GetMapping("/body-points/{id}")
    @Timed
    public ResponseEntity<BodyPointDTO> getBodyPoint(@PathVariable Long id) {
        log.debug("REST request to get BodyPoint : {}", id);
        BodyPointDTO bodyPointDTO = bodyPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bodyPointDTO));
    }

    /**
     * DELETE  /body-points/:id : delete the "id" bodyPoint.
     *
     * @param id the id of the bodyPointDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/body-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteBodyPoint(@PathVariable Long id) {
        log.debug("REST request to delete BodyPoint : {}", id);
        bodyPointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
