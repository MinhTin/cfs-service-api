package com.axon.cfs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axon.cfs.model.Agency;
import com.axon.cfs.services.AgencyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Agency", produces = MediaType.APPLICATION_JSON_VALUE, tags = { "Agency API" })
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Server Error") })
@RestController
@RequestMapping("/cfs/agency")
public class AgencyAPI {
	
	@Autowired
	private AgencyService agencyService;
	
	@ApiOperation(value = "Create Agency ", nickname = "createAgency", notes = "Create new Agency", response = Agency.class, authorizations = {})
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK", response = Agency.class))
	@PostMapping("/create")
	public ResponseEntity<Agency> createAgency(@RequestBody Agency agency){
		return ResponseEntity.ok(agencyService.createAgency(agency));
	}
	
	@ApiOperation(value = "Get All Agency ", nickname = "getAllAgency", notes = "Get All Agency ", response = Agency.class, authorizations = {})
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK", response = Agency.class))
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
	            value = "Results page you want to retrieve (0..N)"),
	    @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
	            value = "Number of records per page."),
	    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
	            value = "Sorting criteria in the format: property(,asc|desc). " +
	                    "Default sort order is ascending. " +
	                    "Multiple sort criteria are supported.")
	})
	@GetMapping("/get")
	public ResponseEntity<List<Agency>> getAllAgency(Pageable pageable){
		return ResponseEntity.ok(agencyService.getAllAgency(pageable));
	}
	
	@ApiOperation(value = "Search Agency ", nickname = "createAgency", notes = "Search Agency by name , phone or id card ", response = Agency.class, authorizations = {})
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK", response = Agency.class))
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
	            value = "Results page you want to retrieve (0..N)"),
	    @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
	            value = "Number of records per page."),
	    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
	            value = "Sorting criteria in the format: property(,asc|desc). " +
	                    "Default sort order is ascending. " +
	                    "Multiple sort criteria are supported.")
	})
	@GetMapping("/search")
	public ResponseEntity<List<Agency>> searchAgency(@RequestParam(value = "query" ) String query , Pageable pageable){
		return ResponseEntity.ok(agencyService.searchAgency(query, pageable));
	}

}
