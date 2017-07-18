package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.BodyPartService;
import com.rammp.stretchyourbody.service.dto.BodyPartDTO;
import com.rammp.stretchyourbody.service.dto.SubCategoryDTO;
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
public class MobileFoodTagResource {

    private final BodyPartService bodyPartService;

    public MobileFoodTagResource(BodyPartService bodyPartService) {
        this.bodyPartService = bodyPartService;
    }


    @GetMapping("/bodyPartBySubcategory/{id}")
    @Timed
    public List<BodyPartDTO> getSubCategoryByCategory(@PathVariable Long id) {
        List<BodyPartDTO> bodyPartDTOS = bodyPartService.findBySubcategory(id);
        return bodyPartDTOS;
    }
}
