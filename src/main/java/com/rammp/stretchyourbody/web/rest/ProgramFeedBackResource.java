package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.ProgramFeedBackService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.ProgramFeedBackDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ProgramFeedBack.
 */
@RestController
@RequestMapping("/api")
public class ProgramFeedBackResource {

    private final Logger log = LoggerFactory.getLogger(ProgramFeedBackResource.class);

    private static final String ENTITY_NAME = "programFeedBack";
        
    private final ProgramFeedBackService programFeedBackService;

    public ProgramFeedBackResource(ProgramFeedBackService programFeedBackService) {
        this.programFeedBackService = programFeedBackService;
    }

    /**
     * POST  /program-feed-backs : Create a new programFeedBack.
     *
     * @param programFeedBackDTO the programFeedBackDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programFeedBackDTO, or with status 400 (Bad Request) if the programFeedBack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/program-feed-backs")
    @Timed
    public ResponseEntity<ProgramFeedBackDTO> createProgramFeedBack(@RequestBody ProgramFeedBackDTO programFeedBackDTO) throws URISyntaxException {
        log.debug("REST request to save ProgramFeedBack : {}", programFeedBackDTO);
        if (programFeedBackDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new programFeedBack cannot already have an ID")).body(null);
        }
        ProgramFeedBackDTO result = programFeedBackService.save(programFeedBackDTO);
        return ResponseEntity.created(new URI("/api/program-feed-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /program-feed-backs : Updates an existing programFeedBack.
     *
     * @param programFeedBackDTO the programFeedBackDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programFeedBackDTO,
     * or with status 400 (Bad Request) if the programFeedBackDTO is not valid,
     * or with status 500 (Internal Server Error) if the programFeedBackDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/program-feed-backs")
    @Timed
    public ResponseEntity<ProgramFeedBackDTO> updateProgramFeedBack(@RequestBody ProgramFeedBackDTO programFeedBackDTO) throws URISyntaxException {
        log.debug("REST request to update ProgramFeedBack : {}", programFeedBackDTO);
        if (programFeedBackDTO.getId() == null) {
            return createProgramFeedBack(programFeedBackDTO);
        }
        ProgramFeedBackDTO result = programFeedBackService.save(programFeedBackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, programFeedBackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /program-feed-backs : get all the programFeedBacks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of programFeedBacks in body
     */
    @GetMapping("/program-feed-backs")
    @Timed
    public List<ProgramFeedBackDTO> getAllProgramFeedBacks() {
        log.debug("REST request to get all ProgramFeedBacks");
        return programFeedBackService.findAll();
    }

    /**
     * GET  /program-feed-backs/:id : get the "id" programFeedBack.
     *
     * @param id the id of the programFeedBackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programFeedBackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/program-feed-backs/{id}")
    @Timed
    public ResponseEntity<ProgramFeedBackDTO> getProgramFeedBack(@PathVariable Long id) {
        log.debug("REST request to get ProgramFeedBack : {}", id);
        ProgramFeedBackDTO programFeedBackDTO = programFeedBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(programFeedBackDTO));
    }

    /**
     * DELETE  /program-feed-backs/:id : delete the "id" programFeedBack.
     *
     * @param id the id of the programFeedBackDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/program-feed-backs/{id}")
    @Timed
    public ResponseEntity<Void> deleteProgramFeedBack(@PathVariable Long id) {
        log.debug("REST request to delete ProgramFeedBack : {}", id);
        programFeedBackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
