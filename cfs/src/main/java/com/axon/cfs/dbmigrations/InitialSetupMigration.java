package com.axon.cfs.dbmigrations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.axon.cfs.model.UserModel;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

@ChangeLog(order = "001")
public class InitialSetupMigration {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/* Initial data for User data */
	@ChangeSet(order = "01", author = "initiator", id = "01-addUser")
	public void addTopicDummyData(MongoTemplate mongoTemplate) {
		UserModel user = new UserModel();
		user.setUserName("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		mongoTemplate.save(user);
	}

}