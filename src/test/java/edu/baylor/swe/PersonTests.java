package edu.baylor.swe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.baylor.swe.repositories.PersonRepository;
import edu.baylor.swe.repositories.StudentAgeGroup;

@SpringBootTest
class PersonTests {

	@Autowired
	private PersonRepository personRepo;

	@BeforeEach
	void setup() {

	}

	@Test
	void testGroupByAge() {
		List<StudentAgeGroup> studentAgeGroups = personRepo.groupByAge();
		System.out.println("\n--- Student Age Stats ---");
		print(studentAgeGroups);
		assertEquals(3, studentAgeGroups.size(), "Not Matched Age Groups Number");
	}

	private void print(List<StudentAgeGroup> studentAgeGroups) {
		studentAgeGroups.forEach(studenAgeGroup -> System.out.println(studenAgeGroup.toString()));
	}
}