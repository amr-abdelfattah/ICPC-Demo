package edu.baylor.swe.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.swe.models.Team;
import edu.baylor.swe.services.TeamService;

@RestController
@RequestMapping(path = "/teams", produces = "application/json")
@CrossOrigin(origins = "*")
public class TeamAPIController {

	@Autowired
	private TeamService teamService;

	public TeamAPIController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping
	public ResponseEntity<List<Team>> teams(@RequestParam String contestName) {
		Optional<List<Team>> teams = teamService.teams(contestName);
		if (teams.isPresent()) {
			return new ResponseEntity<>(teams.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/all")
	public Iterable<Team> teams() {
		return teamService.teams();
	}

	@PatchMapping("/edit")
//	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Team> editTeam(@RequestBody Team team) {
		try {
			Optional<Team> updatedTeam = teamService.editTeam(team);
			if (updatedTeam.isPresent()) {
				return new ResponseEntity<>(updatedTeam.get(), HttpStatus.OK);
			}
		} catch (ConstraintViolationException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("{teamId}/promote/contest/{superContestId}")
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Team> promote(@PathVariable("superContestId") Long superContestId,
			@PathVariable("teamId") Long teamId) {
		try {
			Optional<Team> updatedTeam = teamService.promote(superContestId, teamId);
			if (updatedTeam.isPresent()) {
				return new ResponseEntity<>(updatedTeam.get(), HttpStatus.OK);
			}
		} catch (ConstraintViolationException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
