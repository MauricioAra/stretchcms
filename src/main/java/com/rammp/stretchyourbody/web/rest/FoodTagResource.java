package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.FoodTagService;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import com.rammp.stretchyourbody.service.dto.FoodTagDTO;
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
 * REST controller for managing FoodTag.
 */
@RestController
@RequestMapping("/api")
public class FoodTagResource {

    private final Logger log = LoggerFactory.getLogger(FoodTagResource.class);

    private static final String ENTITY_NAME = "foodTag";
        
    private final FoodTagService foodTagService;

    public FoodTagResource(FoodTagService foodTagService) {
        this.foodTagService = foodTagService;
    }

    /**
     * POST  /food-tags : Create a new foodTag.
     *
     * @param foodTagDTO the foodTagDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foodTagDTO, or with status 400 (Bad Request) if the foodTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/food-tags")
    @Timed
    public ResponseEntity<FoodTagDTO> createFoodTag(@Valid @RequestBody FoodTagDTO foodTagDTO) throws URISyntaxException {
        log.debug("REST request to save FoodTag : {}", foodTagDTO);
        if (foodTagDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new foodTag cannot already have an ID")).body(null);
        }
        FoodTagDTO result = foodTagService.save(foodTagDTO);
        return ResponseEntity.created(new URI("/api/food-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /food-tags : Updates an existing foodTag.
     *
     * @param foodTagDTO the foodTagDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foodTagDTO,
     * or with status 400 (Bad Request) if the foodTagDTO is not valid,
     * or with status 500 (Internal Server Error) if the foodTagDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/food-tags")
    @Timed
    public ResponseEntity<FoodTagDTO> updateFoodTag(@Valid @RequestBody FoodTagDTO foodTagDTO) throws URISyntaxException {
        log.debug("REST request to update FoodTag : {}", foodTagDTO);
        if (foodTagDTO.getId() == null) {
            return createFoodTag(foodTagDTO);
        }
        FoodTagDTO result = foodTagService.save(foodTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foodTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /food-tags : get all the foodTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foodTags in body
     */
    @GetMapping("/food-tags")
    @Timed
    public List<FoodTagDTO> getAllFoodTags() {
        log.debug("REST request to get all FoodTags");
        return foodTagService.findAll();
    }

    /**
     * GET  /food-tags/:id : get the "id" foodTag.
     *
     * @param id the id of the foodTagDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foodTagDTO, or with status 404 (Not Found)
     */
    @GetMapping("/food-tags/{id}")
    @Timed
    public ResponseEntity<FoodTagDTO> getFoodTag(@PathVariable Long id) {
        log.debug("REST request to get FoodTag : {}", id);
        FoodTagDTO foodTagDTO = foodTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(foodTagDTO));
    }

    /**
     * DELETE  /food-tags/:id : delete the "id" foodTag.
     *
     * @param id the id of the foodTagDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/food-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoodTag(@PathVariable Long id) {
        log.debug("REST request to delete FoodTag : {}", id);
        foodTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
