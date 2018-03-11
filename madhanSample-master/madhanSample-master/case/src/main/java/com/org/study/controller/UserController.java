package com.org.study.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.study.model.UserModel;
import com.org.study.repository.UserRepo;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepo userRepo;
	
	//create
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void insert(@RequestBody UserModel userModel) {
		userRepo.save(userModel);
	}

	//read
	@RequestMapping(value="/read/{id}")
	public Optional<UserModel> read(@PathVariable String id) {
		return userRepo.findById(id);
	}
}	