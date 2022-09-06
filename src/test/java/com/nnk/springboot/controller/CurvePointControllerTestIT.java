package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.implement.CurvePointService;
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
public class CurvePointControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CurvePointService curvePointService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be forbidden page when user had role USER and try to access curvePoint pages")
    @WithUserDetails()
    public void should_beForbiddenPage_when_userHadRoleUSERAndTryToAccessCurvePointPage() throws Exception {
        this.mockMvc.perform(get("/admin/curvePoint/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get curvePoint page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetCurvePointPage() throws Exception {
        this.mockMvc.perform(get("/admin/curvePoint/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get curvePoint add page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetCurvePointAddPage() throws Exception {
        this.mockMvc.perform(get("/admin/curvePoint/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to curvePoint/list when added a new curvePoint and there is not bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToCurvePointList_when_addedANewCurvePointAndThereIsNotBadRequest() throws Exception {

        mockMvc.perform(post("/admin/curvePoint/validate")
                        .param("curveId", "4")
                        .param("term", "3")
                        .param("value", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/curvePoint/list"))
                .andReturn();

        CurvePointDTO curvePoint = curvePointService.findById(4);

        assertNotNull(curvePoint);
        assertEquals(3, curvePoint.getTerm());
    }

    @Test
    @DisplayName("Should be redirect to curvePoint/list and added a message error when there is a bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToCurvePointListAndAddedAMessageError_when_thereIsABadRequest() throws Exception {

        mockMvc.perform(post("/admin/curvePoint/validate")
                        .param("curveId", "3")
                        .param("term", "3")
                        .param("value", "-10")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(content().string(containsString("Value must be positive")))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get curvePoint update with id 1 page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetCurvePointUpdateWithCurvePoint1Page() throws Exception {
        this.mockMvc.perform(get("/admin/curvePoint/update/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to curvePoint/list when curvePoint updated is success")
    @WithUserDetails("admin")
    public void should_beRedirectToCurvePointList_when_curvePointUpdatedIsSuccess() throws Exception {

        mockMvc.perform(post("/admin/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "30")
                        .param("value", "70")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/curvePoint/list"))
                .andReturn();

        CurvePointDTO curvePoint = curvePointService.findById(1);

        assertNotNull(curvePoint);
        assertEquals(70, curvePoint.getValue());
    }

    @Test
    @DisplayName("Should be returned page 400 when curvePoint updated a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_curvePointUpdatedAUnknownCurvePoint() throws Exception {
        mockMvc.perform(post("/admin/curvePoint/update/100")
                        .param("curveId", "100")
                        .param("term", "30")
                        .param("value", "70")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();

    }

    @Test
    @DisplayName("Should be redirect to curvePoint/update when curvePoint updated is fail")
    @WithUserDetails("admin")
    public void should_beRedirectToCurvePointUpdate_when_curvePointUpdatedIsFail() throws Exception {

        mockMvc.perform(post("/admin/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "30")
                        .param("value", "-70")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(content().string(containsString("Value must be positive")))
                .andReturn();

        CurvePointDTO curvePoint = curvePointService.findById(1);

        assertNotNull(curvePoint);
        assertEquals(50, curvePoint.getValue());
    }

    @Test
    @DisplayName("Should be redirect to curvePoint/list when curvePoint deleted is success")
    @WithUserDetails("admin")
    public void should_beRedirectToCurvePointList_when_curvePointDeletedIsSuccess() throws Exception {

        mockMvc.perform(get("/admin/curvePoint/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/curvePoint/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned page 400 when curvePoint deleted a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_curvePointDeletedAUnknownId() throws Exception {

        mockMvc.perform(get("/admin/curvePoint/delete/100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();
    }


}
