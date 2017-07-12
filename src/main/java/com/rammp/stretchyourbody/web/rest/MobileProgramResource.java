package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.ProgramService;
import com.rammp.stretchyourbody.service.dto.ProgramDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mbp on 7/10/17.
 */
@RestController
@RequestMapping("/api/app")
public class MobileProgramResource {

    private final ProgramService programService;

    public MobileProgramResource(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/my_programs/{id}")
    @Timed
    public List<ProgramDTO> getMyPrograms(@PathVariable Long id) {
        return programService.findByUser(id);
    }
}
