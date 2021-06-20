package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowRuleNameAdmin() throws Exception {
        this.mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testShowRuleName() throws Exception {
        this.mockMvc.perform(get("/ruleName/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddRuleNameAdmin() throws Exception {
        this.mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddRuleName() throws Exception {
        this.mockMvc.perform(get("/ruleName/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateRuleNameAdmin() throws Exception {
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateRuleNameAdminHasError() throws Exception {
        this.mockMvc.perform(post("/ruleName/validate")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser
    @Test
    public void testValidateRuleName() throws Exception {
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowUpdateRuleNameAdmin() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));

        this.mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("name", Matchers.equalTo("name"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("description", Matchers.equalTo("description"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("json", Matchers.equalTo("json"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("template", Matchers.equalTo("template"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("sqlStr", Matchers.equalTo("sqlStr"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("sqlPart", Matchers.equalTo("sqlPart"))));
    }

    @WithMockUser
    @Test
    public void testShowUpdateRuleName() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));

        this.mockMvc.perform(get("/ruleName/update/" + ruleName.getId())).andExpect(status().isForbidden());
    }


    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateRuleNameAdmin() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        this.mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testUpdateRuleNameAdminHasError() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        this.mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testDeleteRuleNameAdmin() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));

        this.mockMvc.perform(get("/ruleName/delete/" + ruleName.getId())).andExpect(status().isFound()).andReturn();
    }

}
