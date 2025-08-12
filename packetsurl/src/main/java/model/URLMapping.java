package model;

import jakarta.persistence.*;

@Entity
@Table(name = "url_mapping")
public class URLMapping {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	 
	  @Column(nullable = false)
	   private String originalUrl;

	    @Column(nullable = true, unique = true)
	    private String shortCode;
	    
	    
	    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}


  
	    
}
