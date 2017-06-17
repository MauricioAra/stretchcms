package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.UserAppService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.UserAppDTO;
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
 * REST controller for managing UserApp.
 */
@RestController
@RequestMapping("/api")
public class UserAppResource {

    private final Logger log = LoggerFactory.getLogger(UserAppResource.class);

    private static final String ENTITY_NAME = "userApp";
        
    private final UserAppService userAppService;

    public UserAppResource(UserAppService userAppService) {
        this.userAppService = userAppService;
    }

    /**
     * POST  /user-apps : Create a new userApp.
     *
     * @param userAppDTO the userAppDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAppDTO, or with status 400 (Bad Request) if the userApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-apps")
    @Timed
    public ResponseEntity<UserAppDTO> createUserApp(@RequestBody UserAppDTO userAppDTO) throws URISyntaxException {
        log.debug("REST request to save UserApp : {}", userAppDTO);
        if (userAppDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userApp cannot already have an ID")).body(null);
        }
        UserAppDTO result = userAppService.save(userAppDTO);
        return ResponseEntity.created(new URI("/api/user-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-apps : Updates an existing userApp.
     *
     * @param userAppDTO the userAppDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAppDTO,
     * or with status 400 (Bad Request) if the userAppDTO is not valid,
     * or with status 500 (Internal Server Error) if the userAppDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-apps")
    @Timed
    public ResponseEntity<UserAppDTO> updateUserApp(@RequestBody UserAppDTO userAppDTO) throws URISyntaxException {
        log.debug("REST request to update UserApp : {}", userAppDTO);
        if (userAppDTO.getId() == null) {
            return createUserApp(userAppDTO);
        }
        UserAppDTO result = userAppService.save(userAppDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-apps : get all the userApps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userApps in body
     */
    @GetMapping("/user-apps")
    @Timed
    public List<UserAppDTO> getAllUserApps() {
        log.debug("REST request to get all UserApps");
        return userAppService.findAll();
    }

    /**
     * GET  /user-apps/:id : get the "id" userApp.
     *
     * @param id the id of the userAppDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAppDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-apps/{id}")
    @Timed
    public ResponseEntity<UserAppDTO> getUserApp(@PathVariable Long id) {
        log.debug("REST request to get UserApp : {}", id);
        UserAppDTO userAppDTO = userAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userAppDTO));
    }

    /**
     * DELETE  /user-apps/:id : delete the "id" userApp.
     *
     * @param id the id of the userAppDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-apps/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserApp(@PathVariable Long id) {
        log.debug("REST request to delete UserApp : {}", id);
        userAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
