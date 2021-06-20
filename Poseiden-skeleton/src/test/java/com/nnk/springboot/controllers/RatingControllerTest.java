package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.hamcrest.Matchers;
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
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowRatingAdmin() throws Exception {
        this.mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @WithMockUser()
    @Test
    public void testShowRating() throws Exception {
        this.mockMvc.perform(get("/rating/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddRatingAdmin() throws Exception {
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddRating() throws Exception {
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateRatingAdmin() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateRatingAdminError() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser
    @Test
    public void testValidateRating() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowUpdateRatingAdmin() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));

        this.mockMvc.perform(get("/rating/update/" + rating.getId()))
                .andExpect(model().attribute("rating", Matchers.hasProperty("moodysRating", Matchers.equalTo("moodysRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("sandPRating", Matchers.equalTo("sandPRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("fitchRating", Matchers.equalTo("fitchRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("orderNumber", Matchers.equalTo(42))));
    }

    @WithMockUser
    @Test
    public void testShowUpdateRating() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));

        this.mockMvc.perform(get("/rating/update/" + rating.getId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateBidListAdmin() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateBidListAdminHasError() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testDeleteRatingAdmin() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));

        this.mockMvc.perform(get("/rating/delete/" + rating.getId())).andExpect(status().isFound()).andReturn();
    }

}
