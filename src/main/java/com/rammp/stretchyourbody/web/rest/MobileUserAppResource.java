package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.domain.UserApp;
import com.rammp.stretchyourbody.service.UserAppService;
import com.rammp.stretchyourbody.service.dto.UserAppDTO;
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
public class MobileUserAppResource {

    private final UserAppService userAppService;


    public MobileUserAppResource(UserAppService userAppService) {
        this.userAppService = userAppService;
    }

    @GetMapping("/user/{id}")
    @Timed
    public UserAppDTO getUser(@PathVariable Long id) {
        return userAppService.findOne(id);
    }


    @PutMapping("/updateUser")
    @Timed
    public ResponseEntity<UserAppDTO> updateUserApp(@Valid @RequestBody UserAppDTO userAppDTO) throws URISyntaxException {


        UserAppDTO result = userAppService.save(userAppDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAppDTO.getId().toString()))
            .body(result);
    }

}

