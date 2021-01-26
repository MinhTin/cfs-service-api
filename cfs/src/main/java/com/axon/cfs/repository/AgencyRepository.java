package com.axon.cfs.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.axon.cfs.model.Agency;

@Repository
public interface AgencyRepository extends MongoRepository<Agency, String>{
	
	List<Agency> findAllOrderByCreatedDate(Pageable pageable);
	
	@Query(sort = "{'createdDate': 1}")
	List<Agency> findByNameIgnoreCaseLikeOrPhoneIgnoreCaseLikeOrIdCardIgnoreCaseLike(String query ,String phone , String idCard , Pageable pageable);
	
}
