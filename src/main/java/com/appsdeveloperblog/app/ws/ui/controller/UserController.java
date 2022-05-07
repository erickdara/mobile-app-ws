package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users") //http://localhost:8080/users
public class UserController {

	Map<String, UserRest> users;
	
	@GetMapping
	public String getUsers(@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="limit", defaultValue="50") int limit,
			@RequestParam(value="sort", defaultValue="desc", required = false) String sort) {
		return "get user was called with page = " + page + " and limit = " + limit + " and sort = " + sort;
	}	

	@GetMapping(path= "/{userId}", produces = {
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity <UserRest> getUser(@PathVariable String userId) {

		if(users.containsKey(userId)){
			return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping(consumes = {
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE
	},
			produces = {
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE
	} )
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		returnValue.setEmail(userDetails.getEmail());
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());

		String userId = UUID.randomUUID().toString();
		returnValue.setUserId(userId);
		if(users == null) users = new HashMap<>();
		users.put(userId,returnValue);

		return new ResponseEntity<>(returnValue, HttpStatus.OK);
	}
	
	@PutMapping(path = "/{userId}", consumes = {
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE
	}, produces = {
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE
	})
	public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequestModel userDetails) {
		UserRest storeUserDetails = users.get(userId);
		storeUserDetails.setFirstName(userDetails.getFirstName());
		storeUserDetails.setLastName(userDetails.getLastName());

		return storeUserDetails;
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		users.remove(id);
		return ResponseEntity.noContent().build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
	

}
