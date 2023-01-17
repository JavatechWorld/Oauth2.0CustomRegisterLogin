package com.oauth.implementation.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.oauth.implementation.dto.UserRegisteredDTO;
import com.oauth.implementation.model.User;


public interface DefaultUserService extends UserDetailsService{

	User save(UserRegisteredDTO userRegisteredDTO);




	
}
