package com.nnk.springboot.controllers;

import com.nnk.springboot.Service.TradeService;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeController {
    private final TradeService tradeService;
    private final TradeRepository tradeRepository;
    private static final Logger logger = LogManager.getLogger("You are on the TradeController");


    @Autowired
    public TradeController(TradeService tradeService, TradeRepository tradeRepository) {
        this.tradeService = tradeService;
        this.tradeRepository = tradeRepository;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        logger.info("methode home : /trade/list");
        model.addAttribute("trades", tradeService.allTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        logger.info("methode addUser : /trade/add");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("methode validate : /trade/validate");
        if (!result.hasErrors()) {
            tradeRepository.save(trade);
            model.addAttribute("trade", trade);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/tradePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("methode showUpdateForm : /tradePoint/update/{id}");
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade ID" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        logger.info("methode updateTrade : /trade/update/{id}");
        if (result.hasErrors()) {
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeRepository.save(trade);
        model.addAttribute("trade", trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/tradePoint/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("methode deleteTrade : /tradePoint/delete/{id}");
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade ID" + id));
        tradeRepository.delete(trade);
        return "redirect:/trade/list";
    }
}
