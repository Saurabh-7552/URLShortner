package controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.UrlShortenerService;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class URLController {

	 @Autowired
	    private UrlShortenerService service;

	    /**
	     * Create a shortened URL.
	     * Example: POST /api/shorten?url=https://example.com
	     */
	    @PostMapping("/shorten")
	    public ResponseEntity<String> shortenUrl(@RequestParam String url) {
	        String shortCode = service.shortenUrl(url);
	        return ResponseEntity.ok(shortCode);
	    }

	    /**
	     * Redirect to the original URL.
	     * Example: GET /api/{shortCode}
	     */
	    @GetMapping("/{shortCode}")
	    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
	       Optional<String> originalUrl = service.getOriginalUrl(shortCode);
	        if (originalUrl.isPresent()) {
	            return ResponseEntity.status(302).location(URI.create(originalUrl.get())).build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
}
