package com.axon.cfs.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.axon.cfs.model.CFSEvent;

@Service
public interface CFSEventService {
	
	CFSEvent createCFSEvent(CFSEvent event);
	
	List<CFSEvent> searchCFSEvent(String responder, Pageable pageable, Date startDate, Date endDate);

	List<CFSEvent> searchResponderCFSEvent(String responder, Pageable pageable);
}
