package dtb.api.rest;

import java.net.URI;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtb.api.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;

    @Before
    public void setUp() {
    }
    
	/*
	 * Prepare HTTP Headers.
	 */
	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */
	private static HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "user:password";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}
	
	@Test
	public void testCreateAndFind(){
		User createdUser = new User();
		createdUser.setName("Unit Test");
		
		HttpEntity<User> createRequest = new HttpEntity<User>(createdUser, getHeadersWithClientCredentials());
		
		ResponseEntity<String> createResponse = restTemplate
				  .exchange("/api/v1/user/", HttpMethod.POST, createRequest, String.class);
		
		System.out.println(createResponse);
		
		Assert.assertTrue(createResponse.getStatusCode() == HttpStatus.CREATED);
		
		HttpEntity<Object> findRequest = new HttpEntity<Object>(getHeadersWithClientCredentials());
		ResponseEntity<User> findResponse =
		        restTemplate.exchange("/api/v1/user/nq/" + createdUser.getName(),
		                    HttpMethod.GET, findRequest, User.class);
	
		System.out.println(findResponse);
		
		User foundUser = findResponse.getBody();

		Assert.assertTrue(findResponse.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(foundUser.getName().equals(createdUser.getName()));
	}
	
}
