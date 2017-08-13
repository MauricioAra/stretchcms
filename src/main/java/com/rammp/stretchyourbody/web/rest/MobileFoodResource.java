package com.rammp.stretchyourbody.web.rest;

import com.rammp.stretchyourbody.service.FoodService;
import com.rammp.stretchyourbody.service.dto.FoodDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.codahale.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by mbp on 7/10/17.
 */
@RestController
@RequestMapping("/api/app")
public class MobileFoodResource {

    private final FoodService foodService;

    public MobileFoodResource(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/allfood")
    @Timed
    public List<FoodDTO> getAllFoods(){return foodService.findAll();
    }

    @GetMapping("/foodDetails/{id}")
    @Timed
    public FoodDTO getMyPrograms(@PathVariable Long id) {
        return foodService.findOne(id);
    }

}
