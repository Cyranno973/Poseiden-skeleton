package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowCurvePointAdmin() throws Exception {
        this.mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }

    @WithMockUser()
    @Test
    public void testShowCurvePoint() throws Exception {
        this.mockMvc.perform(get("/curvePoint/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddCurvePointAdmin() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddCurvePoint() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateCurvePointAdmin() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "90")
                .param("term", "10.0")
                .param("value", "10.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateCurvePointAdminHasError() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "90")
                .param("term", "A")
                .param("value", "B")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser
    @Test
    public void testValidateCurvePoint() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "90")
                .param("term", "10.0")
                .param("value", "10.0")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowUpdateCurvePointAdmin() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));

        this.mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("curveId", Matchers.equalTo(42))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("term", Matchers.equalTo(10.0d))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("value", Matchers.equalTo(10.0d))));
    }

    @WithMockUser
    @Test
    public void testShowUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));

        this.mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateCurvePointAdmin() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));
        this.mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .param("curveId", "91")
                .param("term", "12.0")
                .param("value", "12.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateCurvePointAdminHasError() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));
        this.mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .param("curveId", "91")
                .param("term", "12.0")
                .param("value", "A")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testDeleteBidListAdmin() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));

        this.mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId())).andExpect(status().isFound()).andReturn();
    }
}
