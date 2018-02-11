package com.claycorp.nexstore.api;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.claycorp.nexstore.api.entity.Articles;
import com.claycorp.nexstore.api.exception.GlobalBaseException;
import com.claycorp.nexstore.api.model.CustomResponse;
import com.claycorp.nexstore.api.model.UserVo;
import com.claycorp.nexstore.api.repository.ArticlesRepository;
import com.claycorp.nexstore.api.service.CustomerRegistrationService;
import com.claycorp.nexstore.api.util.ResponseBuilder;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class CustomerRegistrationApplication {

	@Autowired
	private ArticlesRepository articleRepo;

	@Autowired
	private CustomerRegistrationService customerRegistrationService;

	@Autowired
	private ResponseBuilder<UserVo> responseBuilder;

	@GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomResponse<UserVo>> getAllCustomerDetail() {
		return ResponseEntity.ok().body(responseBuilder.buildResponse(customerRegistrationService.getAllUserDetails(),
				Collections.emptyList()));
	}

	@PostMapping(path = "/customers", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomResponse<UserVo>> addCustomerDetails(@RequestBody UserVo userRequest) {

		return ResponseEntity.created(URI.create("/api/dev/claycorp/api/v1/customers")).body(responseBuilder
				.buildResponse(customerRegistrationService.addUserDetails(userRequest), Collections.emptyList()));
	}

	@PutMapping(path = "/customers", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomResponse<UserVo>> updateCustomerDetails(@RequestBody UserVo userRequest)
			throws GlobalBaseException {

		return ResponseEntity.ok().body(responseBuilder
				.buildResponse(customerRegistrationService.updateUserDetails(userRequest), Collections.emptyList()));
	}

	@GetMapping(path = "/customers/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomResponse<UserVo>> findOneCustomerDetails(@PathVariable(value = "userId") String userId)
			throws GlobalBaseException {

		return ResponseEntity.ok().body(responseBuilder
				.buildResponse(customerRegistrationService.findUserDetails(userId), Collections.emptyList()));
	}

	@DeleteMapping("/customers/{userId}")
	public ResponseEntity<?> deleteCustomerDetails(@PathVariable(value = "userId") String userId)
			throws GlobalBaseException {
		customerRegistrationService.deleteUserDetails(userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(path = "/articles")
	public ResponseEntity<List<Articles>> getAllArticles() {
		List<Articles> articles = articleRepo.findAll();
		return ResponseEntity.ok().body(articles);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerRegistrationApplication.class, args);
	}
	
	@GetMapping("hello")
	public ResponseEntity<String> sayHello(){
		
		return ResponseEntity.ok().body("Hello Clayman");
	}
	
}
