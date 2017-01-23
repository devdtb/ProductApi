package dtb.api.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit4.SpringRunner;

import dtb.api.model.Product;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductRestControllerTest {
	
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//    @Before
//    public void setUp() {
//    	System.out.println("Setting up before!");
//    }
//    
//    
//	@Test
//	public void exampleTest() {
//		String plainCreds = "user:password";
//		byte[] plainCredsBytes = plainCreds.getBytes();
//		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
//		String base64Creds = new String(base64CredsBytes);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Basic " + base64Creds);
//		
//		HttpEntity<String> request = new HttpEntity<String>(headers);
//		
//		ResponseEntity<List<Product>> rateResponse =
//		        restTemplate.exchange("/api/v1/products",
//		                    HttpMethod.GET, request, new ParameterizedTypeReference<List<Product>>() {
//		            });
//		
//		List<Product> productList = rateResponse.getBody();
//				
//		assertThat(productList.size()).isEqualTo(100);
//	}
}
