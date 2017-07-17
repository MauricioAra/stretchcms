package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.SubCategoryService;
import com.rammp.stretchyourbody.service.dto.SubCategoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by mbp on 7/10/17.
 */
@RestController
@RequestMapping("/api/app")
public class MobileSubCategoryResource {

    private final SubCategoryService subCategoryService;
    private final Logger log = LoggerFactory.getLogger(SubCategoryResource.class);


    public MobileSubCategoryResource(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("/subCategoryByCategory/{id}")
    @Timed
    public List<SubCategoryDTO> getSubCategoryByCategory(@PathVariable Long id) {
        List<SubCategoryDTO> subCategoryDTO = subCategoryService.subCategoryByCategory(id);
        return subCategoryDTO;
    }


}
