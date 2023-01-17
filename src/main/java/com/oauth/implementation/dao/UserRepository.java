package com.oauth.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oauth.implementation.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String emailId);
}
