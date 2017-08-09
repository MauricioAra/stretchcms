package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.ProgramService;
import com.rammp.stretchyourbody.service.dto.ProgramDTO;
import com.rammp.stretchyourbody.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rammp.stretchyourbody.service.RecommendedService;
import com.rammp.stretchyourbody.service.dto.RecommendedDTO;

import java.util.List;

/**
 * Created by mbp on 7/10/17.
 */
@RestController
@RequestMapping("/api/app")
public class MobileProgramResource {


    private final ProgramService programService;
    private final RecommendedService recommendedService;
    private static final String ENTITY_NAME = "Programa";

    public MobileProgramResource(ProgramService programService, RecommendedService recommendedService) {
        this.programService = programService;
        this.recommendedService = recommendedService;
    }

    @GetMapping("/my_programs/{id}")
    @Timed
    public List<ProgramDTO> getMyPrograms(@PathVariable Long id) {
        return programService.findByUser(id);
    }


    @GetMapping("/programsRecommended")
    @Timed
    public List<RecommendedDTO> getAllProgramsRecommended() {
        return recommendedService.findByRecommended();
    }

    @PostMapping("/saveProgram")
    @Timed
    public ProgramDTO saveProgram(@RequestBody ProgramDTO programDTO){
        ProgramDTO programDTO1 = programService.save(programDTO);
        return programDTO1;
    }

    @GetMapping("/my_program/{id}")
    @Timed
    public ProgramDTO getMyProgram(@PathVariable Long id) {
        return programService.findOne(id);
    }

    @DeleteMapping("/my_program/{id}")
    @Timed
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
