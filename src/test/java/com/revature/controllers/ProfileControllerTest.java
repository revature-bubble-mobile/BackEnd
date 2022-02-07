package com.revature.controllers;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.SecurityUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProfileControllerTest {
	 private static final String USERNAME = "dummyUsername";
	 private static final String PASSWORD = "abc123";
	 private static final String EMAIL = "dummy@email.com";
	 private static final boolean VERIFICATION = true;
	 private Profile expected = new Profile();

	 private static final String USERNAME2 = "dummyUsername2";
	 private static final String PASSWORD2 = "abc123";
	 private static final String EMAIL2 = "dummy2@email.com";
	 private Profile expected2 = new Profile();
	 
	 private static final String TOKEN_NAME = "Authorization";
	
	 @Autowired
	 private WebApplicationContext webApplicationContext;
	 
	 private ProfileDTO profiledto;
	 private ProfileDTO testprofiledto;
	 
	 
	 private MockMvc mockMvc;
	  
	 private static String baseUrl = "/profile";

	 @Mock
	 private ProfileRepo profileRepo;
	 private Profile profile;

	 @BeforeEach
	 void initMock() {
	      MockitoAnnotations.openMocks(this);
	      String name = "dummyName";
	      String passkey = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4rA==jnW" +
	                "2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3YXs" +
	                "UH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS3" +
	                "1Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22" +
	                "qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPj" +
	                "mUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9Y" +
	                "CYoZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9" +
	                "pQbtVcbqyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";

	       expected = new Profile(USERNAME, passkey, name, name, EMAIL, VERIFICATION);
	       String name2 = "dummyName2";
	       String passkey2 = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4rA==jnW"
	                +
	                "2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3YXs" +
	                "UH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS3" +
	                "1Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22" +
	                "qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPj" +
	                "mUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9Y" +
	                "CYoZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9" +
	                "pQbtVcbqyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";
	       expected2 = new Profile(USERNAME2, passkey2, name2, name2, EMAIL2, VERIFICATION);
	       profiledto = new ProfileDTO();
	       profiledto.setUsername(USERNAME);
	       profiledto.setPasskey(passkey);
	       profiledto.setFirstName(name);
	       profiledto.setLastName(name);
	       profiledto.setEmail(EMAIL);
	       profiledto.setVerification(VERIFICATION);
	       
	       testprofiledto = new ProfileDTO();
	       testprofiledto.setUsername(USERNAME2);
	       testprofiledto.setPasskey(passkey2);
	       testprofiledto.setFirstName(name2);
	       testprofiledto.setLastName(name2);
	       testprofiledto.setEmail(EMAIL2);
	       testprofiledto.setVerification(VERIFICATION);
	       //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	       ProfileController pc = new ProfileController();
	       mockMvc = MockMvcBuilders.standaloneSetup(pc).build();
	  }
	 
	  @Test
	  public void testRegister() throws Exception {
		  Profile newProfile = profiledto.toProfile();
		  HttpHeaders responseHeaders = new HttpHeaders();
          String token = SecurityUtil.generateToken(new ProfileDTO(newProfile));
          assertNotNull(SecurityUtil.validateToken(token));
          responseHeaders.set(TOKEN_NAME, token);
          assertNotNull(responseHeaders);
          ObjectMapper objectMapper = new ObjectMapper();
          String profileJSON = objectMapper.writeValueAsString(newProfile);
          log.info(profileJSON);
		  mockMvc.perform(post(baseUrl + "/register")
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(profileJSON))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentType("application/json"))
		  	;
		  
	  }
	 
//	  @Test
//	  public void testLogin() {
//		 
//	  }
//	  
//	  @Test
//	  public void testGetProfile() {
//		  
//	  }
//	  
//	  @Test
//	  public void testUpdateProfile() {
//		  
//	  }
//	  
//	  @Test
//	  public void testFollow() {
//		  
//	  }
//	  
//	  @Test
//	  public void testUnfollow() {
//		  
//	  }
//	  
//	  @Test
//	  public void testGetAll() {
//		 
//	  }
//	  
//	  @Test
//	  public void testSearch() {
//		  
//	  }
//	  
//	  @Test
//	  public void testGetFollowing() {
//		  
//	  }
}
