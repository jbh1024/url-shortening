package org.iptime.hoonyhoony.urlshortening.controller;

import org.iptime.hoonyhoony.urlshortening.exception.GlobalExceptionHandler;
import org.iptime.hoonyhoony.urlshortening.service.UrlShorteningService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UrlShorteningApiContorllerTest {

    @Mock
    UrlShorteningService urlShorteningService;
    @InjectMocks
    private UrlShorteningApiContorller urlShorteningApiContorller;

    private MockMvc mockMvc;
    private static final StaticApplicationContext applicationContext = new StaticApplicationContext();
    private static final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        applicationContext.registerSingleton("exceptionHandler", GlobalExceptionHandler.class);
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        mockMvc = MockMvcBuilders.standaloneSetup(urlShorteningApiContorller)
                .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
                .alwaysDo(print()).build();

    }

    @Test
    public void urlShorteningApiContorllerTestbyRightUrl() throws Exception {
        String rightURL = "http://hoonyhoony.iptime.org";
        when(urlShorteningService.generateShorteningUrl(rightURL)).thenReturn("AqGEuc");
        mockMvc.perform(post("/api/generateShortening").contentType(MediaType.APPLICATION_JSON).content(rightURL)).
                andExpect(jsonPath("$.shorteningUrl").value("http://localhost/AqGEuc"));

        mockMvc.perform(post("/api/generateShortening").contentType(MediaType.APPLICATION_JSON).content(rightURL)).
                andExpect(status().isOk());

    }

    @Test
    public void urlShorteningApiContorllerTestbyWrongUrl() throws Exception {
        String wrongURL = "hoonyhoony";
        mockMvc.perform(post("/api/generateShortening").contentType(MediaType.APPLICATION_JSON).content(wrongURL))
                .andExpect(status().isBadRequest());


    }

}