package edu.baylor.swe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.baylor.swe.repositories.StudentAgeGroup;
import edu.baylor.swe.services.PersonService;

@RestController
@RequestMapping("/members")
public class PersonAPIController {
	
	@Autowired
	private PersonService personService;
	
	public PersonAPIController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/age-group")
	public List<StudentAgeGroup> studentGroups() {
		return personService.studentGroups();
	}
}
