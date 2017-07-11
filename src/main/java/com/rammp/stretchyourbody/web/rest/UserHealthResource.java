package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.UserHealthService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.UserHealthDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing UserHealth.
 */
@RestController
@RequestMapping("/api")
public class UserHealthResource {

    private final Logger log = LoggerFactory.getLogger(UserHealthResource.class);

    private static final String ENTITY_NAME = "userHealth";
        
    private final UserHealthService userHealthService;

    public UserHealthResource(UserHealthService userHealthService) {
        this.userHealthService = userHealthService;
    }

    /**
     * POST  /user-healths : Create a new userHealth.
     *
     * @param userHealthDTO the userHealthDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userHealthDTO, or with status 400 (Bad Request) if the userHealth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-healths")
    @Timed
    public ResponseEntity<UserHealthDTO> createUserHealth(@Valid @RequestBody UserHealthDTO userHealthDTO) throws URISyntaxException {
        log.debug("REST request to save UserHealth : {}", userHealthDTO);
        if (userHealthDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userHealth cannot already have an ID")).body(null);
        }
        UserHealthDTO result = userHealthService.save(userHealthDTO);
        return ResponseEntity.created(new URI("/api/user-healths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-healths : Updates an existing userHealth.
     *
     * @param userHealthDTO the userHealthDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userHealthDTO,
     * or with status 400 (Bad Request) if the userHealthDTO is not valid,
     * or with status 500 (Internal Server Error) if the userHealthDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-healths")
    @Timed
    public ResponseEntity<UserHealthDTO> updateUserHealth(@Valid @RequestBody UserHealthDTO userHealthDTO) throws URISyntaxException {
        log.debug("REST request to update UserHealth : {}", userHealthDTO);
        if (userHealthDTO.getId() == null) {
            return createUserHealth(userHealthDTO);
        }
        UserHealthDTO result = userHealthService.save(userHealthDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userHealthDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-healths : get all the userHealths.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of userHealths in body
     */
    @GetMapping("/user-healths")
    @Timed
    public List<UserHealthDTO> getAllUserHealths(@RequestParam(required = false) String filter) {
        if ("userapp-is-null".equals(filter)) {
            log.debug("REST request to get all UserHealths where userApp is null");
            return userHealthService.findAllWhereUserAppIsNull();
        }
        log.debug("REST request to get all UserHealths");
        return userHealthService.findAll();
    }

    /**
     * GET  /user-healths/:id : get the "id" userHealth.
     *
     * @param id the id of the userHealthDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userHealthDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-healths/{id}")
    @Timed
    public ResponseEntity<UserHealthDTO> getUserHealth(@PathVariable Long id) {
        log.debug("REST request to get UserHealth : {}", id);
        UserHealthDTO userHealthDTO = userHealthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userHealthDTO));
    }

    /**
     * DELETE  /user-healths/:id : delete the "id" userHealth.
     *
     * @param id the id of the userHealthDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-healths/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserHealth(@PathVariable Long id) {
        log.debug("REST request to delete UserHealth : {}", id);
        userHealthService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
