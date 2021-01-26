package com.axon.cfs.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.axon.cfs.model.Agency;

@Service
public interface AgencyService {
	
	Agency createAgency(Agency agency);
	
	List<Agency> getAllAgency(Pageable pageable);
	
	List<Agency> searchAgency(String query, Pageable pageable);

}
