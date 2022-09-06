package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.service.implement.UserService;
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
public class UserControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be forbidden page when user had role USER and try to access user pages")
    @WithUserDetails()
    public void should_beForbiddenPage_when_userHadRoleUSERAndTryToAccessUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/user/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get user page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetUserPage() throws Exception {
        this.mockMvc.perform(get("/admin/user/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get user add page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetUserAddPage() throws Exception {
        this.mockMvc.perform(get("/admin/user/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to user/list when added a new user and there is not bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToUserList_when_addedANewUserAndThereIsNotBadRequest() throws Exception {

        mockMvc.perform(post("/admin/user/validate")
                        .param("fullname", "User3")
                        .param("username", "user3")
                        .param("password", "xxxxxxxxxxxxxxxxxx")
                        .param("role","USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/list"))
                .andReturn();

        UserDTO user = userService.findById(3);

        assertNotNull(user);
        assertEquals("User3", user.getFullname());
    }

    @Test
    @DisplayName("Should be redirect to user/list and added a message error when there is a bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToUserListAndAddedAMessageError_when_thereIsABadRequest() throws Exception {

        mockMvc.perform(post("/admin/user/validate")
                        .param("fullname", "User3")
                        .param("username", "user3")
                        .param("password", "x")
                        .param("role","USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(content().string(containsString("Password must be at least 8 characters long")))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get user update with id 1 page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetUserUpdateWithUser1Page() throws Exception {
        this.mockMvc.perform(get("/admin/user/update/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to user/list when user updated is success")
    @WithUserDetails("admin")
    public void should_beRedirectToUserList_when_userUpdatedIsSuccess() throws Exception {

        mockMvc.perform(post("/admin/user/update/2")
                        .param("fullname", "User2")
                        .param("username", "user")
                        .param("password", "TestTest")
                        .param("role","USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/list"))
                .andReturn();

        UserDTO user = userService.findById(2);

        assertNotNull(user);
        assertEquals("User2", user.getFullname());
    }

    @Test
    @DisplayName("Should be returned page 400 when user updated a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_userUpdatedAUnknownUser() throws Exception {
        mockMvc.perform(post("/admin/user/update/100")
                        .param("fullname", "User2")
                        .param("username", "user")
                        .param("password", "TestTest")
                        .param("role","USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();

    }

    @Test
    @DisplayName("Should be redirect to user/update when user updated is fail")
    @WithUserDetails("admin")
    public void should_beRedirectToUserUpdate_when_userUpdatedIsFail() throws Exception {

        mockMvc.perform(post("/admin/user/update/2")
                        .param("fullname", "")
                        .param("username", "user")
                        .param("password", "Test")
                        .param("role","USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(content().string(containsString("Full Name is mandatory")))
                .andReturn();

        UserDTO user = userService.findById(2);

        assertNotNull(user);
        assertEquals("User", user.getFullname());
    }

    @Test
    @DisplayName("Should be redirect to user/list when user deleted is success")
    @WithUserDetails("admin")
    public void should_beRedirectToUserList_when_userDeletedIsSuccess() throws Exception {

        mockMvc.perform(get("/admin/user/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned page 400 when user deleted a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_userDeletedAUnknownId() throws Exception {

        mockMvc.perform(get("/admin/user/delete/100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();
    }


}
