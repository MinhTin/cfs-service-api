package com.axon.cfs.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.axon.cfs.model.CFSEvent;

@Repository
public interface CFSEventRepository extends MongoRepository<CFSEvent, String>  {
	
	List<CFSEvent> findByResponderIgnoreCaseLike(String query, Pageable pageable);
	
	List<CFSEvent> findByEventTimeBetween(Date startDate ,Date endDate ,Pageable pageable);
	
	List<CFSEvent> findByResponderIgnoreCaseLikeAndEventTimeBetween(String query ,Date startDate ,Date endDate ,  Pageable pageable);
	
	List<CFSEvent> findByResponderAndCreatedBy(String responder ,String dispatcher);
	
}
