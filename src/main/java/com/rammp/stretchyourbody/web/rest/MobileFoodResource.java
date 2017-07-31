package com.rammp.stretchyourbody.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rammp.stretchyourbody.service.FoodService;
import com.rammp.stretchyourbody.service.dto.FoodDTO;
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
public class MobileFoodResource {
    private final FoodService foodService;

    public MobileFoodResource(FoodService foodService) {
        this.foodService = foodService;
    }
    @GetMapping("/recommended_food")
    @Timed
    public List<FoodDTO> getRecommendedFood() {
        return foodService.findFoodRecommended();
    }
}
