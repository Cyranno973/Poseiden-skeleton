package com.nnk.springboot.controllers;

import com.nnk.springboot.Service.BidService;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class BidListController {
    private final BidService bidService;
    private final BidListRepository bidListRepository;
    private static final Logger logger = LogManager.getLogger("You are on the BidListController");

    @Autowired
    public BidListController(BidService bidService, BidListRepository bidListRepository) {
        this.bidService = bidService;
        this.bidListRepository = bidListRepository;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.info("methode home : /bidList/list");
        model.addAttribute("bidList", bidService.bidLists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("methode addBidForm : /bidList/add");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.info("methode validate : /bidList/validate");
        if (!result.hasErrors()) {
            bidListRepository.save(bid);
            model.addAttribute("bidList", bidListRepository.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("methode showUpdateForm : /bidList/update/{id}");

        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        logger.info("methode updateBid : /bidList/update/{id}");

        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("methode deleteBid : /bidList/delete/{id}");

        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid ID" + id));
        bidListRepository.delete(bidList);
        return "redirect:/bidList/list";
    }
}
