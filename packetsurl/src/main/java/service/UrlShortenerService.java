package service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.URLMapping;
import repository.UrlMappingRepository;

@Service
public class UrlShortenerService {
	  private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	    @Autowired
	    private UrlMappingRepository repository;

	    /**
	     * Shorten a long URL using Base62 encoding of the DB ID.
	     */
	    public String shortenUrl(String originalUrl) {
	        // Step 1: Save entity with original URL (shortCode empty for now)
	        URLMapping mapping = new URLMapping();
	        mapping.setOriginalUrl(originalUrl);
	        mapping = repository.save(mapping); // now it has a generated ID

	        // Step 2: Generate Base62 short code from ID
	        String shortCode = encodeBase62(mapping.getId());

	        // Step 3: Update entity with short code
	        mapping.setShortCode(shortCode);
	        repository.save(mapping);

	        return shortCode;
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
	    private String encodeBase62(long value) {
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
	    }
}
