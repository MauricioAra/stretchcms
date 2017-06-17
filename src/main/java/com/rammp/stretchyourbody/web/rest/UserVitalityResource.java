package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.UserVitalityService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.UserVitalityDTO;
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
 * REST controller for managing UserVitality.
 */
@RestController
@RequestMapping("/api")
public class UserVitalityResource {

    private final Logger log = LoggerFactory.getLogger(UserVitalityResource.class);

    private static final String ENTITY_NAME = "userVitality";
        
    private final UserVitalityService userVitalityService;

    public UserVitalityResource(UserVitalityService userVitalityService) {
        this.userVitalityService = userVitalityService;
    }

    /**
     * POST  /user-vitalities : Create a new userVitality.
     *
     * @param userVitalityDTO the userVitalityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userVitalityDTO, or with status 400 (Bad Request) if the userVitality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-vitalities")
    @Timed
    public ResponseEntity<UserVitalityDTO> createUserVitality(@RequestBody UserVitalityDTO userVitalityDTO) throws URISyntaxException {
        log.debug("REST request to save UserVitality : {}", userVitalityDTO);
        if (userVitalityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userVitality cannot already have an ID")).body(null);
        }
        UserVitalityDTO result = userVitalityService.save(userVitalityDTO);
        return ResponseEntity.created(new URI("/api/user-vitalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-vitalities : Updates an existing userVitality.
     *
     * @param userVitalityDTO the userVitalityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userVitalityDTO,
     * or with status 400 (Bad Request) if the userVitalityDTO is not valid,
     * or with status 500 (Internal Server Error) if the userVitalityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-vitalities")
    @Timed
    public ResponseEntity<UserVitalityDTO> updateUserVitality(@RequestBody UserVitalityDTO userVitalityDTO) throws URISyntaxException {
        log.debug("REST request to update UserVitality : {}", userVitalityDTO);
        if (userVitalityDTO.getId() == null) {
            return createUserVitality(userVitalityDTO);
        }
        UserVitalityDTO result = userVitalityService.save(userVitalityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userVitalityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-vitalities : get all the userVitalities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userVitalities in body
     */
    @GetMapping("/user-vitalities")
    @Timed
    public List<UserVitalityDTO> getAllUserVitalities() {
        log.debug("REST request to get all UserVitalities");
        return userVitalityService.findAll();
    }

    /**
     * GET  /user-vitalities/:id : get the "id" userVitality.
     *
     * @param id the id of the userVitalityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userVitalityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-vitalities/{id}")
    @Timed
    public ResponseEntity<UserVitalityDTO> getUserVitality(@PathVariable Long id) {
        log.debug("REST request to get UserVitality : {}", id);
        UserVitalityDTO userVitalityDTO = userVitalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userVitalityDTO));
    }

    /**
     * DELETE  /user-vitalities/:id : delete the "id" userVitality.
     *
     * @param id the id of the userVitalityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-vitalities/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserVitality(@PathVariable Long id) {
        log.debug("REST request to delete UserVitality : {}", id);
        userVitalityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
