package com.axon.cfs.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.axon.cfs.model.Agency;
import com.axon.cfs.repository.AgencyRepository;
import com.axon.cfs.services.AgencyService;
import com.axon.cfs.utils.SecurityUtils;

@Service
public class AgencyServiceImpl implements AgencyService{
	
	@Autowired
	AgencyRepository agencyRepository;

	@Override
	public Agency createAgency(Agency agency) {
		agency.setId(UUID.randomUUID().toString());
		SecurityUtils.saveAbtractEntity(agency);
		return agencyRepository.save(agency);
	}

	@Override
	public List<Agency> getAllAgency(Pageable pageable) {
		List<Agency> agencies = agencyRepository.findAll(pageable).get().collect(Collectors.toList());
		return agencies;
	}

	@Override
	public List<Agency> searchAgency(String query, Pageable pageable) {
		return agencyRepository.findByNameIgnoreCaseLikeOrPhoneIgnoreCaseLikeOrIdCardIgnoreCaseLike(query , query , query, pageable);
	}

}
