package com.nnk.springboot.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListController bidListController;

    @WithMockUser(authorities = "USER")
    @Test
    public void showBidListTest() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void showBidListTestAdmin() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddBidListAdmin() throws Exception {
        this.mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddBidList() throws Exception {
        this.mockMvc.perform(get("/bidList/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateBidListAdmin() throws Exception {
        this.mockMvc.perform(post("/bidList/validate")
                .param("Account", "BobAccount")
                .param("type", "livret")
                .param("bidQuantity", "5")
                .with(csrf())
        ).andExpect(redirectedUrl("/bidList/list"));
    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateBidListAdminHasError() throws Exception {
        this.mockMvc.perform(post("/bidList/validate")
                .param("Account", "BobAccount")
                .param("type", "livret")
                .param("bidQuantity", "aaa")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }
    @WithMockUser
    @Test
    public void testValidBidList() throws Exception {
        this.mockMvc.perform(post("/bidList/validate")
                .param("Account", "BobAccount")
                .param("type", "livret")
                .param("bidQuantity", "5")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }
}
