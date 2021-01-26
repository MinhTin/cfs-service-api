package com.axon.cfs.api;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
import com.axon.cfs.model.CFSEvent;
import com.axon.cfs.services.CFSEventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Dispatcher", produces = MediaType.APPLICATION_JSON_VALUE, tags = { "Dispatcher API" })
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Server Error") })
@RestController
@RequestMapping("/cfs/dispatcher")
public class DispatcherAPI {
	
	@Autowired
	private CFSEventService cfsEventService;

	@ApiOperation(value = "Create CFS Event ", nickname = "createCFSEvent", notes = "Dispatcher should be able to create a CFS with the following information: event number, event type (with type\r\n" + 
			"code), event time, dispatch time, responder", response = CFSEvent.class, authorizations = {})
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK", response = CFSEvent.class))
	@PostMapping("/create")
	public ResponseEntity<?> createCFSEvent(@RequestBody CFSEvent cfsEvent){
		CFSEvent event = cfsEventService.createCFSEvent(cfsEvent) ;
		if(Objects.nonNull(event)) {
			return ResponseEntity.ok(event);
		}
		return ResponseEntity.badRequest().body("Dispatcher and responder should belong to only one agency");
	}
	
	@ApiOperation(value = "Search CFS Event ", nickname = "searchCFSEvent", notes = "Dispatcher should be able to search for CFS within a time range with paging and sorting order. ", response = CFSEvent.class, authorizations = {})
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
	public ResponseEntity<List<CFSEvent>> searchCFSEvent(@RequestParam(value = "responder" , required = false) String responder , @RequestParam(value = "startDate" , required = true) Date startDate 
			, @RequestParam(value = "endDate" , required = true ) Date endDate , Pageable pageable ){
		return ResponseEntity.ok(cfsEventService.searchCFSEvent(responder, pageable  , startDate , endDate));
	}

	
	@ApiOperation(value = "Search Responder CFS Event ", nickname = "searchCFSEvent", notes = "Dispatcher should be able to search for CFS that assigned to a responder.", response = CFSEvent.class, authorizations = {})
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
	@GetMapping("/search-responder")
	public ResponseEntity<List<CFSEvent>> searchResponderCFSEvent(@RequestParam(value = "responder" , required = true) String responder , Pageable pageable ){
		return ResponseEntity.ok(cfsEventService.searchResponderCFSEvent(responder, pageable));
	}
}
