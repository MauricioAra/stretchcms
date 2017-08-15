package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.BodyPointRegistryService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.BodyPointRegistryDTO;
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
 * REST controller for managing BodyPointRegistry.
 */
@RestController
@RequestMapping("/api")
public class BodyPointRegistryResource {

    private final Logger log = LoggerFactory.getLogger(BodyPointRegistryResource.class);

    private static final String ENTITY_NAME = "bodyPointRegistry";
        
    private final BodyPointRegistryService bodyPointRegistryService;

    public BodyPointRegistryResource(BodyPointRegistryService bodyPointRegistryService) {
        this.bodyPointRegistryService = bodyPointRegistryService;
    }

    /**
     * POST  /body-point-registries : Create a new bodyPointRegistry.
     *
     * @param bodyPointRegistryDTO the bodyPointRegistryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bodyPointRegistryDTO, or with status 400 (Bad Request) if the bodyPointRegistry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/body-point-registries")
    @Timed
    public ResponseEntity<BodyPointRegistryDTO> createBodyPointRegistry(@Valid @RequestBody BodyPointRegistryDTO bodyPointRegistryDTO) throws URISyntaxException {
        log.debug("REST request to save BodyPointRegistry : {}", bodyPointRegistryDTO);
        if (bodyPointRegistryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bodyPointRegistry cannot already have an ID")).body(null);
        }
        BodyPointRegistryDTO result = bodyPointRegistryService.save(bodyPointRegistryDTO);
        return ResponseEntity.created(new URI("/api/body-point-registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /body-point-registries : Updates an existing bodyPointRegistry.
     *
     * @param bodyPointRegistryDTO the bodyPointRegistryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bodyPointRegistryDTO,
     * or with status 400 (Bad Request) if the bodyPointRegistryDTO is not valid,
     * or with status 500 (Internal Server Error) if the bodyPointRegistryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/body-point-registries")
    @Timed
    public ResponseEntity<BodyPointRegistryDTO> updateBodyPointRegistry(@Valid @RequestBody BodyPointRegistryDTO bodyPointRegistryDTO) throws URISyntaxException {
        log.debug("REST request to update BodyPointRegistry : {}", bodyPointRegistryDTO);
        if (bodyPointRegistryDTO.getId() == null) {
            return createBodyPointRegistry(bodyPointRegistryDTO);
        }
        BodyPointRegistryDTO result = bodyPointRegistryService.save(bodyPointRegistryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bodyPointRegistryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /body-point-registries : get all the bodyPointRegistries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bodyPointRegistries in body
     */
    @GetMapping("/body-point-registries")
    @Timed
    public List<BodyPointRegistryDTO> getAllBodyPointRegistries() {
        log.debug("REST request to get all BodyPointRegistries");
        return bodyPointRegistryService.findAll();
    }

    /**
     * GET  /body-point-registries/:id : get the "id" bodyPointRegistry.
     *
     * @param id the id of the bodyPointRegistryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bodyPointRegistryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/body-point-registries/{id}")
    @Timed
    public ResponseEntity<BodyPointRegistryDTO> getBodyPointRegistry(@PathVariable Long id) {
        log.debug("REST request to get BodyPointRegistry : {}", id);
        BodyPointRegistryDTO bodyPointRegistryDTO = bodyPointRegistryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bodyPointRegistryDTO));
    }

    /**
     * DELETE  /body-point-registries/:id : delete the "id" bodyPointRegistry.
     *
     * @param id the id of the bodyPointRegistryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/body-point-registries/{id}")
    @Timed
    public ResponseEntity<Void> deleteBodyPointRegistry(@PathVariable Long id) {
        log.debug("REST request to delete BodyPointRegistry : {}", id);
        bodyPointRegistryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
