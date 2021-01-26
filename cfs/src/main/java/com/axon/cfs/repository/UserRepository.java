package com.axon.cfs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.axon.cfs.model.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

	UserModel findByUserName(final String userName);
	
}
