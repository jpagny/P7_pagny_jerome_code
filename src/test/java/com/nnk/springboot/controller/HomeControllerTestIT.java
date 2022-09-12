package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be returned 200 when get page home")
    public void should_beReturned200_when_getPageHome() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Should be redirect to home page when get page /")
    public void should_beReturned200_when_getPageRacine() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be redirect to /admin/home page when get page /home and has admin role")
    @WithUserDetails("admin")
    public void should_beRedirectToAdminHomePage_when_getPageHomeAndHasAdminRole() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/home"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be redirect to /user/home page when get page /home and has user role")
    @WithUserDetails()
    public void should_beRedirectToUserHomePage_when_getPageHomeAndHasUserRole() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/home"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be redirect to /admin/bidList/list page when get page /admin/home and has admin role")
    @WithUserDetails("admin")
    public void should_beRedirectToAdminBidListList_when_getPageAdminHomeAndHasAdminRole() throws Exception {
        mockMvc.perform(get("/admin/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/bidList/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be load home user view when get page /user/home and has user role")
    @WithUserDetails()
    public void should_beLoadHomeUserView_when_getPageUserHomeAndHasUserRole() throws Exception {
        mockMvc.perform(get("/user/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("homeUser"))
                .andReturn();
    }


}
