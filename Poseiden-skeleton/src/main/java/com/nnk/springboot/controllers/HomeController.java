package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger("You are on the controller HomeController");

    @RequestMapping("/")
    public String home(Model model) {
        logger.info("methode home : /");
        return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        logger.info("methode adminHome : /admin/home");
        return "redirect:/bidList/list";
    }
}
