package com.nnk.springboot.controllers;

import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest extends TestCase {
    @Autowired
    HomeController homeController;

}
