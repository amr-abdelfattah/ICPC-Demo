package edu.baylor.swe.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.swe.DataPopulator;
import edu.baylor.swe.models.AmazonApplication;
import edu.baylor.swe.models.Applicant;
import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.services.AmazonService;
import edu.baylor.swe.services.ContestService;

@RestController
@RequestMapping("/amazon")
@Validated
public class AmazonApplicationController {
	
	@Autowired
	private AmazonService amazonService;
	
	
	
	public AmazonApplicationController(AmazonService amazonService) {
		this.amazonService = amazonService;
	}
	
	@GetMapping("/applications")
	public ResponseEntity<List<AmazonApplication>> applications() {
		List<AmazonApplication> applications = amazonService.allApplications();
		return new ResponseEntity<>(applications, HttpStatus.OK);
	}
	
	@PostMapping("/applications/{applicationId}/registration")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<AmazonApplication> registration(@PathVariable Long applicationId, @RequestBody Applicant applicant) {
		try {
			Optional<AmazonApplication> application = amazonService.registration(applicationId, applicant);
			if (application.isPresent()) {
				return new ResponseEntity<>(application.get(), HttpStatus.OK);
			}
		} catch(ConstraintViolationException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch(NullPointerException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/applications/{applicationId}")
	public ResponseEntity<AmazonApplication> application(@PathVariable long applicationId) {
	try {
		Optional<AmazonApplication> application = amazonService.getApplication(applicationId);
		
		if (application.isPresent()) {
			return new ResponseEntity<>(application.get(), HttpStatus.OK);
		}
	} catch(ConstraintViolationException ex) {
		ex.printStackTrace();
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	} catch(NullPointerException ex) {
		ex.printStackTrace();
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
}