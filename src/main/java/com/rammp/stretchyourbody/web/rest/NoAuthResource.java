package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.BodyPartService;
import com.rammp.stretchyourbody.service.dto.BodyPartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing No authentication requests.
 */
@RestController
@RequestMapping("/api/noAuthResource")
public class NoAuthResource {

    private final Logger log = LoggerFactory.getLogger(UserAppResource.class);

    private final BodyPartService bodyPartService;

    public NoAuthResource(BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }

    @GetMapping("/bodyParts")
    @Timed
    public  List<BodyPartDTO> getBodyPartList() {
        log.debug("REST request to get all BodyParts");
        return bodyPartService.findAll();
    }
}
