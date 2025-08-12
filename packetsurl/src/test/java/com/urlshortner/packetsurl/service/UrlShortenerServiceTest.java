package com.urlshortner.packetsurl.service;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import model.URLMapping;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import repository.UrlMappingRepository;
import service.UrlShortenerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlShortenerServiceTest {
	@Mock
    private UrlMappingRepository repository;

    @InjectMocks
    private UrlShortenerService service;

    public UrlShortenerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl() {
        String originalUrl = "https://www.google.com";
        URLMapping mockMapping = new URLMapping();
        mockMapping.setId(1L);
        mockMapping.setOriginalUrl(originalUrl);
        mockMapping.setShortCode("abc123");

        when(repository.save(any(URLMapping.class))).thenReturn(mockMapping);

        String shortCode = service.shortenUrl(originalUrl);

        assertNotNull(shortCode);
        assertEquals("abc123", shortCode);
        verify(repository, times(1)).save(any(URLMapping.class));
    }

    @Test
    void testGetOriginalUrl_Found() {
        URLMapping mockMapping = new URLMapping();
        mockMapping.setOriginalUrl("https://www.google.com");

        when(repository.findByShortCode("abc123")).thenReturn(Optional.of(mockMapping));

        Optional<String> result = service.getOriginalUrl("abc123");

        assertEquals("https://www.google.com", result.toString());
    }

    @Test
    void testGetOriginalUrl_NotFound() {
        when(repository.findByShortCode("xyz999")).thenReturn(Optional.empty());

        Optional<String> result = service.getOriginalUrl("xyz999");

        assertNull(result);
    }
	

}
