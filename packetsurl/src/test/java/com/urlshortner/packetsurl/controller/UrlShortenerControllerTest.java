package com.urlshortner.packetsurl.controller;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import controller.URLController;
import service.UrlShortenerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@WebMvcTest(URLController.class)
public class UrlShortenerControllerTest {
	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private UrlShortenerService service;

	    @Test
	    void testShortenUrlEndpoint() throws Exception {
	        Mockito.when(service.shortenUrl("https://www.google.com")).thenReturn("abc123");

	        mockMvc.perform(get("/api/shorten")
	                .param("url", "https://www.google.com"))
	                .andExpect(status().isOk())
	                .andExpect(content().string("abc123"));
	    }

	    @Test
	    void testRedirectToOriginalUrl_Found() throws Exception {
	        Mockito.when(service.getOriginalUrl("abc123")).thenReturn(Optional.of("https://www.google.com"));

	        mockMvc.perform(get("/api/abc123"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("https://www.google.com"));
	    }

	    @Test
	    void testRedirectToOriginalUrl_NotFound() throws Exception {
	        Mockito.when(service.getOriginalUrl("xyz999")).thenReturn(null);

	        mockMvc.perform(get("/api/xyz999"))
	                .andExpect(status().isNotFound());
	    }
	
}
