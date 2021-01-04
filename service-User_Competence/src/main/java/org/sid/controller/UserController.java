package org.sid.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.sid.classe.User;
import org.sid.dto.UserDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.sid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	public UserService userService;
	
	
	@GetMapping("/")
	public void init() throws ResultNotFoundException {
		
	}
	
	
	@PostMapping("/user")
	public void createUserController(@Valid @RequestBody UserDto userDto) throws ResultNotFoundException, EntityAlreadyExistException {
		userService.createUser(userDto);
	}
	
	@GetMapping("/user/{id}")
	public User getUserController(@PathVariable Long id) throws ResultNotFoundException {
		User user = userService.getUser(id);
		
		return user;
	}
	@PutMapping("/user")
	public void updateUserController(UserDto userDto) throws EntityAlreadyExistException, ResultNotFoundException  {
		userService.updateUser(userDto,Optional.empty());
	}
	@DeleteMapping("/user")
	public void deleteUserController(Long id) throws ResultNotFoundException, EntityAlreadyExistException {
		userService.deleteUser(id);
	}
	
}
