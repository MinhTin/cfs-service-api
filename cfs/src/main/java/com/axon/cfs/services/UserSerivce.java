package com.axon.cfs.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserSerivce {

	UserDetails registerUser(final String userName , final String password);
	
}
