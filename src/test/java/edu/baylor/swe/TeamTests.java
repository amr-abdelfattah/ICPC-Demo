package edu.baylor.swe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.repositories.ContestRepository;
import edu.baylor.swe.repositories.TeamRepository;

@SpringBootTest
class TeamTests {

	// @Autowired
	// private TeamRepository teamRepo;

	// @Autowired
	// private ContestRepository contestRepo;

	// @BeforeEach
	// void setup() {

	// }
	
	// @Test
	// @Transactional
	// void testSelectAllTeams() {
	// 	Contest contest = contestRepo.findByName("Sub Contest").get();
	// 	List<Team> contestTeams = teamRepo.findByAttendedContest_Id(contest.getId());
	// 	System.out.println("\n--- All Teams ---");
	// 	print(contestTeams);
	// 	assertEquals(3, contestTeams.size(), "Not Matched Number Attended Teams");
	// }

	// @Test
	// @Transactional
	// void testSelectAllTeams2() {
	// 	Contest contest = contestRepo.findByName("Sub Contest").get();
	// 	List<Team> contestTeams = contest.getAttendedTeams();
	// 	System.out.println("\n--- All Teams With Another Approach ---");
	// 	print(contestTeams);
	// 	assertEquals(3, contestTeams.size(), "Not Matched Number Attended Teams");
	// }

	// private void print(List<Team> teams) {
	// 	teams.forEach(team -> System.out.println(team.toString()));
	// }

}
