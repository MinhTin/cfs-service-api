package com.axon.cfs.model;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_date")
    private Instant createdDate = Instant.now();
    
    @LastModifiedBy
    @JsonProperty("last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonProperty("last_modified_date")
    private Instant lastModifiedDate = Instant.now();
}
