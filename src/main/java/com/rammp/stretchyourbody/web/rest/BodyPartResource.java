package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.BodyPartService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.BodyPartDTO;
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
 * REST controller for managing BodyPart.
 */
@RestController
@RequestMapping("/api")
public class BodyPartResource {

    private final Logger log = LoggerFactory.getLogger(BodyPartResource.class);

    private static final String ENTITY_NAME = "bodyPart";
        
    private final BodyPartService bodyPartService;

    public BodyPartResource(BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }

    /**
     * POST  /body-parts : Create a new bodyPart.
     *
     * @param bodyPartDTO the bodyPartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bodyPartDTO, or with status 400 (Bad Request) if the bodyPart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/body-parts")
    @Timed
    public ResponseEntity<BodyPartDTO> createBodyPart(@Valid @RequestBody BodyPartDTO bodyPartDTO) throws URISyntaxException {
        log.debug("REST request to save BodyPart : {}", bodyPartDTO);
        if (bodyPartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bodyPart cannot already have an ID")).body(null);
        }
        BodyPartDTO result = bodyPartService.save(bodyPartDTO);
        return ResponseEntity.created(new URI("/api/body-parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /body-parts : Updates an existing bodyPart.
     *
     * @param bodyPartDTO the bodyPartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bodyPartDTO,
     * or with status 400 (Bad Request) if the bodyPartDTO is not valid,
     * or with status 500 (Internal Server Error) if the bodyPartDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/body-parts")
    @Timed
    public ResponseEntity<BodyPartDTO> updateBodyPart(@Valid @RequestBody BodyPartDTO bodyPartDTO) throws URISyntaxException {
        log.debug("REST request to update BodyPart : {}", bodyPartDTO);
        if (bodyPartDTO.getId() == null) {
            return createBodyPart(bodyPartDTO);
        }
        BodyPartDTO result = bodyPartService.save(bodyPartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bodyPartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /body-parts : get all the bodyParts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bodyParts in body
     */
    @GetMapping("/body-parts")
    @Timed
    public List<BodyPartDTO> getAllBodyParts() {
        log.debug("REST request to get all BodyParts");
        return bodyPartService.findAll();
    }

    /**
     * GET  /body-parts/:id : get the "id" bodyPart.
     *
     * @param id the id of the bodyPartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bodyPartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/body-parts/{id}")
    @Timed
    public ResponseEntity<BodyPartDTO> getBodyPart(@PathVariable Long id) {
        log.debug("REST request to get BodyPart : {}", id);
        BodyPartDTO bodyPartDTO = bodyPartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bodyPartDTO));
    }

    /**
     * DELETE  /body-parts/:id : delete the "id" bodyPart.
     *
     * @param id the id of the bodyPartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/body-parts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBodyPart(@PathVariable Long id) {
        log.debug("REST request to delete BodyPart : {}", id);
        bodyPartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
