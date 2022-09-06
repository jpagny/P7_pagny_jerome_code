package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.implement.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RatingControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RatingService ratingService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be forbidden page when user had role USER and try to access rating pages")
    @WithUserDetails()
    public void should_beForbiddenPage_when_userHadRoleUSERAndTryToAccessRatingPage() throws Exception {
        this.mockMvc.perform(get("/admin/rating/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get rating page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetRatingPage() throws Exception {
        this.mockMvc.perform(get("/admin/rating/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get rating add page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetRatingAddPage() throws Exception {
        this.mockMvc.perform(get("/admin/rating/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to rating/list when added a new rating and there is not bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToRatingList_when_addedANewRatingAndThereIsNotBadRequest() throws Exception {

        mockMvc.perform(post("/admin/rating/validate")
                        .param("moodysRating", "Test")
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/rating/list"))
                .andReturn();

        RatingDTO rating = ratingService.findById(3);

        assertNotNull(rating);
        assertEquals(10, rating.getOrderNumber());
    }

    @Test
    @DisplayName("Should be redirect to rating/list and added a message error when there is a bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToRatingListAndAddedAMessageError_when_thereIsABadRequest() throws Exception {

        mockMvc.perform(post("/admin/rating/validate")
                        .param("moodysRating", "Test")
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(content().string(containsString("Order is mandatory")))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get rating update with id 1 page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetRatingUpdateWithRating1Page() throws Exception {
        this.mockMvc.perform(get("/admin/rating/update/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to rating/list when rating updated is success")
    @WithUserDetails("admin")
    public void should_beRedirectToRatingList_when_ratingUpdatedIsSuccess() throws Exception {

        mockMvc.perform(post("/admin/rating/update/1")
                        .param("moodysRating", "Test")
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", "3")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/rating/list"))
                .andReturn();

        RatingDTO rating = ratingService.findById(1);

        assertNotNull(rating);
        assertEquals(3, rating.getOrderNumber());
    }

    @Test
    @DisplayName("Should be returned page 400 when rating updated a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_ratingUpdatedAUnknownRating() throws Exception {
        mockMvc.perform(post("/admin/rating/update/100")
                        .param("moodysRating", "Test")
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", "30")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();

    }

    @Test
    @DisplayName("Should be redirect to rating/update when rating updated is fail")
    @WithUserDetails("admin")
    public void should_beRedirectToRatingUpdate_when_ratingUpdatedIsFail() throws Exception {

        mockMvc.perform(post("/admin/rating/update/1")
                        .param("moodysRating", "Test")
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(content().string(containsString("Order is mandatory")))
                .andReturn();

        RatingDTO rating = ratingService.findById(1);

        assertNotNull(rating);
        assertEquals(1, rating.getOrderNumber());
    }

    @Test
    @DisplayName("Should be redirect to rating/list when rating deleted is success")
    @WithUserDetails("admin")
    public void should_beRedirectToRatingList_when_ratingDeletedIsSuccess() throws Exception {

        mockMvc.perform(get("/admin/rating/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/rating/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned page 400 when rating deleted a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_ratingDeletedAUnknownId() throws Exception {

        mockMvc.perform(get("/admin/rating/delete/100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();
    }


}
