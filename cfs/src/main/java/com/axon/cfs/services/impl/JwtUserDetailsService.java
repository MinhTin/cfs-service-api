package com.axon.cfs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axon.cfs.model.Role;
import com.axon.cfs.model.UserModel;
import com.axon.cfs.repository.UserRepository;
import com.axon.cfs.services.UserSerivce;

@Service
public class JwtUserDetailsService implements UserDetailsService, UserSerivce {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String userName) {
		UserModel user = userRepository.findByUserName(userName);
		if (user != null) {
			return new User(user.getUserName(), user.getPassword(), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
					Boolean.TRUE, AuthorityUtils.NO_AUTHORITIES);
		}
		return null;
	}

	@Override
	public UserDetails registerUser(String userName, String password) {
		UserModel checkUserName = userRepository.findByUserName(userName);
		if(checkUserName == null) {
			UserModel userModel = new UserModel();
			userModel.setUserName(userName);
			userModel.setPassword(passwordEncoder.encode(password));
			userModel.setRole(Role.DISPATHCER);
			UserModel user = userRepository.save(userModel);
			return new User(user.getUserName(), user.getPassword(), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
					Boolean.TRUE, AuthorityUtils.NO_AUTHORITIES);
		}
		return null;

	}

}
