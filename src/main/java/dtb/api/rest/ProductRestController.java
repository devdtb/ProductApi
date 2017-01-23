package dtb.api.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dtb.api.model.Product;

@RestController
@RequestMapping(value = "/api/v1")
public class ProductRestController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Product home", HttpStatus.OK);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> productList = new ArrayList<Product>();

		for (int i = 0; i < 100; i++) {
			productList.add(new Product(i, "name_" + i + "_" + getCurrentDate()));
		}

		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	private String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

		return dateFormat.format(new Date());
	}
}
