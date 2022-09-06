package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.implement.BidListService;
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
import static org.junit.jupiter.api.Assertions.*;
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
public class BidListControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BidListService bidListService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be forbidden page when user had role USER and try to access bidList pages")
    @WithUserDetails()
    public void should_beForbiddenPage_when_userHadRoleUSERAndTryToAccessBidListPage() throws Exception {
        this.mockMvc.perform(get("/admin/bidList/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get bidList page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetBidListPage() throws Exception {
        this.mockMvc.perform(get("/admin/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get bidList add page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetBidListAddPage() throws Exception {
        this.mockMvc.perform(get("/admin/bidList/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to bidList/list when added a new bidList and there is not bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToBidListList_when_addedANewBidListAndThereIsNotBadRequest() throws Exception {

        mockMvc.perform(post("/admin/bidList/validate")
                        .param("bidListId", "3")
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("bidQuantity", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/bidList/list"))
                .andReturn();

        BidListDTO bidList = bidListService.findById(3);

        assertNotNull(bidList);
        assertEquals(10, bidList.getBidQuantity());
    }

    @Test
    @DisplayName("Should be redirect to bidList/list and added a message error when there is a bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToBidListListAndAddedAMessageError_when_thereIsABadRequest() throws Exception {

        mockMvc.perform(post("/admin/bidList/validate")
                        .param("bidListId", "5")
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("bidQuantity", "-10")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(content().string(containsString("Bid Quantity must be positive")))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get bidList update with bidList 1 page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetBidListUpdateWithBidList1Page() throws Exception {
        this.mockMvc.perform(get("/admin/bidList/update/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Should be redirect to bidList/list when bidList updated is success")
    @WithUserDetails("admin")
    public void should_beRedirectToBidListList_when_bidListUpdatedIsSuccess() throws Exception {

        mockMvc.perform(post("/admin/bidList/update/1")
                        .param("bidListId", "1")
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("bidQuantity", "100")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/bidList/list"))
                .andReturn();

        BidListDTO bidList = bidListService.findById(1);

        assertNotNull(bidList);
        assertEquals(100, bidList.getBidQuantity());
    }

    @Test
    @DisplayName("Should be returned page 404 when bidList updated a unknown user")
    @WithUserDetails("admin")
    public void should_beReturnedPage404_when_bidListUpdatedAUnknownUser() throws Exception {
        mockMvc.perform(post("/admin/bidList/update/100")
                        .param("bidListId", "100")
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("bidQuantity", "100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404.html"))
                .andReturn();

    }
    @Test
    @DisplayName("Should be redirect to bidList/update when bidList updated is fail")
    @WithUserDetails("admin")
    public void should_beRedirectToBidListUpdate_when_bidListUpdatedIsFail() throws Exception {

        mockMvc.perform(post("/admin/bidList/update/1")
                        .param("bidListId", "1")
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("bidQuantity", "-100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(content().string(containsString("Bid Quantity must be positive")))
                .andReturn();

        BidListDTO bidList = bidListService.findById(1);

        assertNotNull(bidList);
        assertEquals(2, bidList.getBidQuantity());
    }
    @Test
    @DisplayName("Should be redirect to bidList/list when bidList deleted is success")
    @WithUserDetails("admin")
    public void should_beRedirectToBidListList_when_bidListDeletedIsSuccess() throws Exception {

        mockMvc.perform(get("/admin/bidList/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/bidList/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned page 404 when bidList deleted a unknown user")
    @WithUserDetails("admin")
    public void should_beReturnedPage404_when_bidListDeletedAUnknownUser() throws Exception {

        mockMvc.perform(get("/admin/bidList/delete/100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404.html"))
                .andReturn();
    }


}
