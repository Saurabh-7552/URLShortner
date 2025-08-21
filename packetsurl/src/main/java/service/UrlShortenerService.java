package service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.URLMapping;
import repository.UrlMappingRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
@Service
public class  UrlShortenerService {
	  //private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	    @Autowired
	    private UrlMappingRepository repository;

	    /**
	     * Shorten a long URL using Base62 encoding of the DB ID.
	     */
	    public String shortenUrl(String originalUrl) {
	        // Step 1: Save entity with original URL (shortCode empty for now)
	    	  String shortCode = generateHash(originalUrl);

	          // Save to DB
	          URLMapping mapping = new URLMapping();
	          mapping.setOriginalUrl(originalUrl);
	          mapping.setShortCode(shortCode);
	          repository.save(mapping);

	          return shortCode;
	    }
	    private String generateHash(String url) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hashBytes = digest.digest(url.getBytes());

	            // URL-safe Base64 encode (no '+' or '/')
	            String base64Hash = Base64.getUrlEncoder()
	                                      .withoutPadding()
	                                      .encodeToString(hashBytes);

	            // Take first 8 characters for shortness
	            return base64Hash.substring(0, 8);

	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Error generating hash", e);
	        }
	    }

	    /**
	     * Retrieve the original URL for a given short code.
	     */
	    public Optional<String> getOriginalUrl(String shortCode) {
	        return repository.findByShortCode(shortCode)
	                .map(URLMapping::getOriginalUrl);
	    }

	    /**
	     * Encode a number to Base62 string.
	     */
	   /* private String encodeBase62(long value) {
	        if (value == 0) {
	            return "0";
	        }
	        StringBuilder sb = new StringBuilder();
	        while (value > 0) {
	            int index = (int) (value % 62);
	            sb.append(BASE62.charAt(index));
	            value /= 62;
	        }
	        return sb.reverse().toString();
	    }*/
}
