package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.UserVitalityService;
import com.rammp.stretchyourbody.service.dto.ResultAverageDTO;
import com.rammp.stretchyourbody.service.dto.UserVitalityDTO;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Created by mbp on 7/10/17.
 */
@RestController
@RequestMapping("/api/app")
public class MobileUserVitalityResource {

    private final UserVitalityService userVitalityService;


    public MobileUserVitalityResource(UserVitalityService userVitalityService){

        this.userVitalityService = userVitalityService;
    }


    @GetMapping("/userVitality/{id}")
    @Timed
    public ResultAverageDTO getAverage(@PathVariable Long id) {
        return userVitalityService.getAverage(id);
    }

    @PostMapping("/userVitality")
    @Timed
    public ResponseEntity<UserVitalityDTO> createUserVitality(@Valid @RequestBody UserVitalityDTO userVitalityDTO) throws URISyntaxException {


        UserVitalityDTO result = userVitalityService.save(userVitalityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userVitalityDTO.getId().toString()))
            .body(result);
    }
}
