package com.nnk.springboot.controllers;

import com.nnk.springboot.Service.RatingService;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {

    private final RatingService ratingService;
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingController(RatingService ratingService, RatingRepository ratingRepository) {
        this.ratingService = ratingService;
        this.ratingRepository= ratingRepository;
    }

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.allRating());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(!result.hasErrors()){
            ratingRepository.save(rating);
            model.addAttribute("curvePoint", rating);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Curvepoint ID" +id));
        model.addAttribute("curvePoint", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()){
            return "rating/update";
        }
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("curvePoint", rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid Curvepoint ID" +id));
        ratingRepository.delete(rating);
        return "redirect:/rating/list";
    }
}
