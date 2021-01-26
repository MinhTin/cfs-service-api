package com.axon.cfs.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

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
@Document(collection = "cfs_event")
public class CFSEvent extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@JsonProperty("event_id")
	private String id;
	
	@JsonProperty("agency_id")
	private String agencyId;
	
	@JsonProperty("event_number")
	@NotBlank
	private String eventNumber;
	
	@JsonProperty("event_type_code")
	@NotBlank
	private String eventTypeCode;
	
	@JsonProperty("event_time")
	private Date eventTime;
	
	@JsonProperty("dispatch_time")
	private Date dispatchTime;
	
	@NotBlank
	private String responder;

}
