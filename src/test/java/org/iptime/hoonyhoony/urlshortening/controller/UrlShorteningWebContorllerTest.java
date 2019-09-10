package org.iptime.hoonyhoony.urlshortening.controller;


import org.assertj.core.util.Lists;
import org.iptime.hoonyhoony.urlshortening.controller.dto.UrlInfo;
import org.iptime.hoonyhoony.urlshortening.service.UrlShorteningService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UrlShorteningWebContorller.class)
public class UrlShorteningWebContorllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UrlShorteningService mockUrlShorteningService;

    @Test
    public void rightUrlRedirect() throws Exception {
        when(mockUrlShorteningService.getOriginUrlByAlias(anyString())).thenReturn("www.daum.net");
        mockMvc.perform(get("/AqGEuc")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("www.daum.net"));
    }

    @Test
    public void wrongUrlRedirect() throws Exception {
        when(mockUrlShorteningService.getOriginUrlByAlias(anyString())).thenReturn(null);
        mockMvc.perform(get("/test")).andExpect(status().isOk()).andExpect(view().name("error"));
    }

    @Test
    public void home() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        UrlInfo urlInfo = new UrlInfo(1, "ABCD", "https://www.daum.net", new Date());
        List<UrlInfo> urlInfoList = Lists.newArrayList(urlInfo);
        when(mockUrlShorteningService.getAllUrlShortening()).thenReturn(urlInfoList);
        mockMvc.perform(get("/list")).andExpect(status().isOk());
    }
}