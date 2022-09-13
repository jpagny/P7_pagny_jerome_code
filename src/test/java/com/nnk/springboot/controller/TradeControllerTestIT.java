package com.nnk.springboot.controller;

import com.nnk.springboot.Application;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.implement.TradeService;
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
public class TradeControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TradeService tradeService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be forbidden page when user had role USER and try to access trade pages")
    @WithUserDetails()
    public void should_beForbiddenPage_when_userHadRoleUSERAndTryToAccessTradePage() throws Exception {
        this.mockMvc.perform(get("/admin/trade/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get trade page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetTradePage() throws Exception {
        this.mockMvc.perform(get("/admin/trade/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get trade add page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetTradeAddPage() throws Exception {
        this.mockMvc.perform(get("/admin/trade/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to trade/list when added a new trade and there is not bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToTradeList_when_addedANewTradeAndThereIsNotBadRequest() throws Exception {

        mockMvc.perform(post("/admin/trade/validate")
                        .param("account", "Test")
                        .param("type", "Type1")
                        .param("buyQuantity", "10")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/trade/list"))
                .andReturn();

        TradeDTO trade = tradeService.findById(3);

        assertNotNull(trade);
        assertEquals("Type1", trade.getType());
    }

    @Test
    @DisplayName("Should be redirect to trade/list and added a message error when there is a bad request")
    @WithUserDetails("admin")
    public void should_beRedirectToTradeListAndAddedAMessageError_when_thereIsABadRequest() throws Exception {

        mockMvc.perform(post("/admin/trade/validate")
                        .param("account", "Test")
                        .param("type", "Type1")
                        .param("buyQuantity", "-10")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(content().string(containsString("Buy Quantity must be positive")))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned 200 when user has role ADMIN and get trade update with id 1 page")
    @WithUserDetails("admin")
    public void should_brReturned200_when_userHasRoleADMINAndGetTradeUpdateWithTrade1Page() throws Exception {
        this.mockMvc.perform(get("/admin/trade/update/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should be redirect to trade/list when trade updated is success")
    @WithUserDetails("admin")
    public void should_beRedirectToTradeList_when_tradeUpdatedIsSuccess() throws Exception {

        mockMvc.perform(post("/admin/trade/update/1")
                        .param("account", "Test")
                        .param("type", "Type1")
                        .param("buyQuantity", "200")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/trade/list"))
                .andReturn();

        TradeDTO trade = tradeService.findById(1);

        assertNotNull(trade);
        assertEquals(200, trade.getBuyQuantity());
    }

    @Test
    @DisplayName("Should be returned page 400 when trade updated a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_tradeUpdatedAUnknownTrade() throws Exception {
        mockMvc.perform(post("/admin/trade/update/100")
                        .param("account", "Test")
                        .param("type", "Type1")
                        .param("buyQuantity", "200")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();

    }

    @Test
    @DisplayName("Should be redirect to trade/update when trade updated is fail")
    @WithUserDetails("admin")
    public void should_beRedirectToTradeUpdate_when_tradeUpdatedIsFail() throws Exception {

        mockMvc.perform(post("/admin/trade/update/1")
                        .param("account", "Test")
                        .param("type", "Type1")
                        .param("buyQuantity", "-200")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(content().string(containsString("Buy Quantity must be positive")))
                .andReturn();

        TradeDTO trade = tradeService.findById(1);

        assertNotNull(trade);
        //assertEquals(50, trade.getValue());
    }

    @Test
    @DisplayName("Should be redirect to trade/list when trade deleted is success")
    @WithUserDetails("admin")
    public void should_beRedirectToTradeList_when_tradeDeletedIsSuccess() throws Exception {

        mockMvc.perform(get("/admin/trade/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/trade/list"))
                .andReturn();
    }

    @Test
    @DisplayName("Should be returned page 400 when trade deleted a unknown id")
    @WithUserDetails("admin")
    public void should_beReturnedPage400_when_tradeDeletedAUnknownId() throws Exception {

        mockMvc.perform(get("/admin/trade/delete/100")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/400.html"))
                .andReturn();
    }


}
