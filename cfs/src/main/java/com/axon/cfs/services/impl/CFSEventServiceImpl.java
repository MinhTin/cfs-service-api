package com.axon.cfs.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.axon.cfs.model.CFSEvent;
import com.axon.cfs.repository.CFSEventRepository;
import com.axon.cfs.services.CFSEventService;
import com.axon.cfs.utils.SecurityUtils;

@Service
public class CFSEventServiceImpl implements CFSEventService{

	@Autowired
	private CFSEventRepository cfsEventRepository;
	
	@Override
	public CFSEvent createCFSEvent(CFSEvent event) {
		String dispatcher = SecurityUtils.getCurrentUserLogin().orElse("");
		List<CFSEvent> cfsEvent = cfsEventRepository.findByResponderAndCreatedBy(event.getResponder(),dispatcher);
		if(CollectionUtils.isEmpty(cfsEvent) || !checkEventNumber(event.getAgencyId() , cfsEvent)){
			event.setId(UUID.randomUUID().toString());
			if(event.getEventTime() == null) {
				event.setEventTime(new Date());
			}
			if(event.getDispatchTime() == null) {
				event.setDispatchTime(new Date());
			}
			if(StringUtils.isBlank(event.getAgencyId())) {
				event.setAgencyId(UUID.randomUUID().toString());
			}
			SecurityUtils.saveAbtractEntity(event);
			return cfsEventRepository.save(event);
		}
		return null;
	}

	private boolean checkEventNumber(String eventNumber, List<CFSEvent> cfsEvent) {
		return cfsEvent.stream().anyMatch(event -> eventNumber.equals(event.getAgencyId()));
	}

	@Override
	public List<CFSEvent> searchCFSEvent(String responder, Pageable pageable, Date startDate, Date endDate) {
		if(StringUtils.isBlank(responder)) {
			return cfsEventRepository.findByEventTimeBetween(startDate, endDate, pageable);
		}else if(startDate == null || endDate == null) {
			return cfsEventRepository.findByResponderIgnoreCaseLike(responder , pageable);
		}else {
			return cfsEventRepository.findByResponderIgnoreCaseLikeAndEventTimeBetween(responder, startDate, endDate, pageable);
		}
	}

	@Override
	public List<CFSEvent> searchResponderCFSEvent(String responder, Pageable pageable) {
		return cfsEventRepository.findByResponderIgnoreCaseLike(responder , pageable);
	}
}
