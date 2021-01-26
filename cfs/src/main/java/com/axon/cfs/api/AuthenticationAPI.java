package com.axon.cfs.api;

import javax.security.auth.login.FailedLoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axon.cfs.model.JwtRequest;
import com.axon.cfs.model.JwtResponse;
import com.axon.cfs.security.JwtTokenUtil;
import com.axon.cfs.services.UserSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Security", produces = MediaType.APPLICATION_JSON_VALUE, tags = { "Authentication" })
@RestController
@RequestMapping("/cfs")
public class AuthenticationAPI {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserSerivce userSerivce;

	@ApiOperation(value = "Get Infomation User ", nickname = "getTokenInformation", notes = "", response = JwtResponse.class, authorizations = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = JwtResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Server Error") })
	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws FailedLoginException {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		if (userDetails == null) {
			throw new FailedLoginException("user name or passwords failed");
		}
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));

	}

	@PostMapping("/register")
	public ResponseEntity<String> registerAccount(@RequestBody JwtRequest authenticationRequest) throws FailedLoginException {	
		final UserDetails userDetails = userSerivce.registerUser(authenticationRequest.getUsername() , authenticationRequest.getPassword());
		if (userDetails == null) {
			return ResponseEntity.badRequest().body("User name exist");
		}
		return ResponseEntity.ok("Successfully");
	}

	private void authenticate(String username, String password) throws DisabledException , BadCredentialsException{
		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}

}
