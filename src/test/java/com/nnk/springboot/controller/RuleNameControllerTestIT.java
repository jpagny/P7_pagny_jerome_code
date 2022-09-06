package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.implement.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class RuleNameControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RuleNameService ruleNameService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be forbidden page when user had role USER and try to access ruleName pages")
    @WithUserDetails()
    public void should_beForbiddenPage_when_userHadRoleUSERAndTryToAccessRuleNamePage() throws Exception {
        this.mockMvc.perform(get("/admin/ruleName/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get ruleName page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetRuleNamePage() throws Exception {
        this.mockMvc.perform(get("/admin/ruleName/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get ruleName add page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetRuleNameAddPage() throws Exception {
        this.mockMvc.perform(get("/admin/ruleName/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to ruleName/list when added a new ruleName and there is not bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToRuleNameList_when_addedANewRuleNameAndThereIsNotBadRequest() throws Exception {

        mockMvc.perform(post("/admin/ruleName/validate")
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "json.txt")
                        .param("template", "template3")
                        .param("sqlStr", "sql3.txt")
                        .param("sqlPart", "sql_part3.txt")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ruleName/list"))
                .andReturn();

        RuleNameDTO ruleName = ruleNameService.findById(3);

        assertNotNull(ruleName);
        assertEquals("sql_part3.txt", ruleName.getSqlPart());
    }

    @Test
    @DisplayName("Should be redirect to ruleName/list and added a message error when there is a bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToRuleNameListAndAddedAMessageError_when_thereIsABadRequest() throws Exception {

        mockMvc.perform(post("/admin/ruleName/validate")
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "json.txt")
                        .param("template", "template3")
                        .param("sqlStr", "sql3.txt")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(content().string(containsString("sqlPart is mandatory")))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get ruleName update with id 1 page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetRuleNameUpdateWithRuleName1Page() throws Exception {
        this.mockMvc.perform(get("/admin/ruleName/update/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to ruleName/list when ruleName updated is success")
    @WithUserDetails("admin")
    public void should_beRedirectToRuleNameList_when_ruleNameUpdatedIsSuccess() throws Exception {

        mockMvc.perform(post("/admin/ruleName/update/1")
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "json.txt")
                        .param("template", "Test")
                        .param("sqlStr", "sql")
                        .param("sqlPart", "sql_part1.txt")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ruleName/list"))
                .andReturn();

        RuleNameDTO ruleName = ruleNameService.findById(1);

        assertNotNull(ruleName);
        assertEquals("sql_part1.txt", ruleName.getSqlPart());
    }

    @Test
    @DisplayName("Should be returned page 400 when ruleName updated a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_ruleNameUpdatedAUnknownRuleName() throws Exception {
        mockMvc.perform(post("/admin/ruleName/update/100")
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "json.txt")
                        .param("template", "Test")
                        .param("sqlStr", "sql")
                        .param("sqlPart", "sql_part1.txt")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();

    }

    @Test
    @DisplayName("Should be redirect to ruleName/update when ruleName updated is fail")
    @WithUserDetails("admin")
    public void should_beRedirectToRuleNameUpdate_when_ruleNameUpdatedIsFail() throws Exception {

        mockMvc.perform(post("/admin/ruleName/update/1")
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "json.txt")
                        .param("template", "Test")
                        .param("sqlStr", "sql")
                        .param("sqlPart", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(content().string(containsString("sqlPart is mandatory")))
                .andReturn();

        RuleNameDTO ruleName = ruleNameService.findById(1);

        assertNotNull(ruleName);
        assertEquals("sql_part.txt", ruleName.getSqlPart());
    }

    @Test
    @DisplayName("Should be redirect to ruleName/list when ruleName deleted is success")
    @WithUserDetails("admin")
    public void should_beRedirectToRuleNameList_when_ruleNameDeletedIsSuccess() throws Exception {

        mockMvc.perform(get("/admin/ruleName/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ruleName/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned page 400 when ruleName deleted a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_ruleNameDeletedAUnknownId() throws Exception {

        mockMvc.perform(get("/admin/ruleName/delete/100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();
    }


}
