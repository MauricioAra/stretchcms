package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.ProgramService;
import com.rammp.stretchyourbody.service.RecommendedService;
import com.rammp.stretchyourbody.service.dto.ProgramDTO;
import com.rammp.stretchyourbody.service.dto.RecommendedDTO;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final RecommendedService recommendedService;
    public MobileProgramResource(ProgramService programService, RecommendedService recommendedService) {
        this.programService = programService;
        this.recommendedService = recommendedService;
    }

    @GetMapping("/programsRecommended")
    @Timed
    public List<RecommendedDTO> getAllProgramsRecommended() {
        return recommendedService.findByRecommended();
    }

}
