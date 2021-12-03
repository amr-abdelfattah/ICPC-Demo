package edu.baylor.swe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.baylor.swe.models.Contest;
import edu.baylor.swe.repositories.ContestRepository;

@SpringBootTest
class ContestTests {
	@Autowired
	private ContestRepository contestRepo;

	@BeforeEach
	void setup() {

	}

	// @Transactional
	// @Test
	// void testSelectAllTeams() {
	// 	Contest contest = contestRepo.findByName("Sub Contest").get();
	// 	int currentOccupancy = contest.getAttendedTeams().size();
	// 	int capacity = contest.getCapacity();
	// 	System.out.println("\n--- Contest Capacity Vs. Occupancy ---");
	// 	System.out.println("\n Current Occupancy = " + currentOccupancy + " ,Capacity = " + capacity);
	// 	assertEquals(3, currentOccupancy, "Not Matched current Occupancy Number");
	// 	assertEquals(5, capacity, "Not Matched current Capacity Number");
	// }

}
