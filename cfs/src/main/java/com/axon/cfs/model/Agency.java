package com.axon.cfs.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cfs_agency")
public class Agency extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonProperty("agency_id")
	private String id;
	
	@JsonProperty("phone_number")
	private String phone;
	
	@JsonProperty("id_card")
	private String idCard;
	
	private String name;
	
}

