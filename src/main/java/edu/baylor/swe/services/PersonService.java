package edu.baylor.swe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.swe.repositories.PersonRepository;
import edu.baylor.swe.repositories.StudentAgeGroup;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepo;
	
	public PersonService(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}

	public List<StudentAgeGroup> studentGroups() {
		List<StudentAgeGroup> studentAgeGroups = personRepo.groupByAge();
		return studentAgeGroups;
	}
}
