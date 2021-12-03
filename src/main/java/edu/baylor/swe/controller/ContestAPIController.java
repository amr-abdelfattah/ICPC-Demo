package edu.baylor.swe.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.swe.DataPopulator;
import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.repositories.ContestRepository;
import edu.baylor.swe.services.ContestService;
import edu.baylor.swe.services.ContestService.ContestCapacity;

@CrossOrigin(origins="http://localhost:3000")
//@CrossOrigin(origins="*")
@RestController
@RequestMapping("/contests")
@Validated
public class ContestAPIController {
	
	@Autowired
	private ContestService contestService;
	
	@Autowired
	private DataPopulator dataPopulator;
	
	public ContestAPIController(ContestService contestService, DataPopulator dataPopulator) {
		this.contestService = contestService;
		this.dataPopulator = dataPopulator;
	}
	
	@GetMapping
	public ResponseEntity<List<Contest>> contests() {
		List<Contest> contests = contestService.allContests();
		return new ResponseEntity<>(contests, HttpStatus.OK);
	}
	
	@PostMapping
	public Contest insert(@RequestBody Contest contest) {
		return contestService.saveContest(contest);
	}
	
	@GetMapping("/capacity")
	public ResponseEntity<ContestCapacity> contestCapacity(@RequestParam String contestName) {
		Optional<ContestCapacity> contestCapacity = contestService.contestCapacity(contestName);
		if (contestCapacity.isPresent()) {
			return new ResponseEntity<>(contestCapacity.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("{contestId}/registration")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Contest> registration(@PathVariable Long contestId, @RequestBody Team team) {
		try {
			Optional<Contest> contest = contestService.registration(contestId, team);
			if (contest.isPresent()) {
				return new ResponseEntity<>(contest.get(), HttpStatus.OK);
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
	
	@PatchMapping("/edit")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Contest> editContest(@RequestBody Contest contest) {
		try {
			Optional<Contest> updatedContest = contestService.edit(contest);
			if (updatedContest.isPresent()) {
				return new ResponseEntity<>(updatedContest.get(), HttpStatus.OK);
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
	
	@DeleteMapping("/delete/{contestId}")
//	@ExceptionHandler(ConstraintViolationException.class)
	public void delete(@PathVariable long contestId) {
		contestService.delete(contestId);
	}
	
	@PatchMapping("/set-editable")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Contest> setEditable(@RequestBody Contest contest) {
		try {
			Optional<Contest> updatedContest = contestService.setEditable(contest);
			if (updatedContest.isPresent()) {
				return new ResponseEntity<>(updatedContest.get(), HttpStatus.OK);
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
	
	@PatchMapping("/set-read-only")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Contest> setReadOnly(@RequestBody Contest contest) {
		try {
			Optional<Contest> updatedContest = contestService.setReadOnly(contest);
			if (updatedContest.isPresent()) {
				return new ResponseEntity<>(updatedContest.get(), HttpStatus.OK);
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
	
	@GetMapping("/populate")
	public ResponseEntity<Boolean> populate() {
		try {
			dataPopulator.populate();
			return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{contestName}/set-editable2")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Contest> setEditable(@PathVariable String contestName) {
		try {
			Optional<Contest> updatedContest = contestService.setEditable(contestName);
			if (updatedContest.isPresent()) {
				return new ResponseEntity<>(updatedContest.get(), HttpStatus.OK);
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
	
	@PostMapping("/{contestName}/set-read-only2")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Contest> setReadOnly(@PathVariable String contestName) {
		try {
			Optional<Contest> updatedContest = contestService.setReadOnly(contestName);
			if (updatedContest.isPresent()) {
				return new ResponseEntity<>(updatedContest.get(), HttpStatus.OK);
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
