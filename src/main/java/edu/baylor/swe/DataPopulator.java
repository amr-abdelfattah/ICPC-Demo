package edu.baylor.swe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import edu.baylor.swe.models.AmazonApplication;
import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Person;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.repositories.AmazonApplicationRepository;
import edu.baylor.swe.repositories.ContestRepository;

@Component
public class DataPopulator {

	private ContestRepository contestRepo;
	
	private AmazonApplicationRepository amazonRepo;
	
	public DataPopulator(AmazonApplicationRepository amazonRepo) {
		this.amazonRepo = amazonRepo;
	}
	
	public void populate() {
//		reset();
		
		AmazonApplication app = new AmazonApplication();
		app.setCreatedDate(new Date());
		app.setId(Long.valueOf(1));
		amazonRepo.save(app);
//		Team team1 = instantiateTeam();
//		List<Team> teams = new ArrayList<>();
//		teams.add(team1);
//		Contest mainContest = createContest("Main Contest", 10,
//				createPerson("New Main Manager", "m2@gmil.com", getDateFromAge(30), null), teams, null, contestRepo);
//
//		List<Team> teams2 = new ArrayList<>();
//		Team team2 = instantiateTeam0(); //new Team(team1, "team2");
//		Team team3 = instantiateDifferentTeam();//new Team(team1, "team3");
//		team3.setState(Team.State.Accepted);
//		Team team4 = instantiateDifferentTeam2();//new Team(team1, "team4");
//		teams2.add(team2);
//		teams2.add(team3);
//		teams2.add(team4);
//		team4.setRank(6);
//		Contest subContest = createContest("Sub Contest", 5,
//				createPerson("New Sub Manager", "m@gmil.com", getDateFromAge(33), null), teams2, mainContest,
//				contestRepo);
//		contestRepo.save(subContest);
	}
	
	private void reset() {
		contestRepo.deleteAll();
	}
	
	private Contest createContest(String name, int capacity, Person manager, List<Team> teams,
			Contest preliminaryContest, ContestRepository contestRepo) {
		Contest contest = new Contest();
		contest.setName(name);
		contest.setCapacity(capacity);
		contest.setDate(new Date());
		contest.setRegistrationAllowed(true);
		contest.setRegistrationFrom(new Date());
		contest.setRegistrationTo(new Date());
		contest.addManager(manager);
		teams.forEach(team -> contest.addTeam(team));
		contest.setAttendedTeams(teams);
		contest.setPreliminaryContest(preliminaryContest);
		return contest;
	}
 
	private Team createteam(String name, int rank) {
		Team team = new Team();
		team.setName(name);
		team.setRank(rank);
		return team;
	} 

	private Person createPerson(String name, String email, Date birthdate, String university) {
		Person person = new Person();
		person.setName(name);
		person.setUniversity(university);
		person.setEmail(email);
		person.setBirthdate(birthdate);
		return person;
	}

	private Team instantiateTeam0() {
		Team team1 = createteam("Team0", 3);
		team1.setId((long) 2);
		team1.addMember(createPerson("Amr0", "amr0@gmail.com", getDateFromAge(18), "BU"));
		team1.addMember(createPerson("Tomas0", "tomas0@gmail.com", getDateFromAge(23), "BU"));
		team1.addMember(createPerson("Salah0", "mo0@gmail.com", getDateFromAge(22), "BU"));
		team1.setCoach(createPerson("M.Salah0", "mo.salah0@gmail.com", getDateFromAge(23), "BU"));
		return team1;
	}
	
	private Team instantiateTeam() {
		Team team1 = createteam("Team1", 3);
		team1.setId((long) 1);
		team1.addMember(createPerson("Amr", "amr@gmail.com", getDateFromAge(18), "BU"));
		team1.addMember(createPerson("Tomas", "tomas@gmail.com", getDateFromAge(23), "BU"));
		team1.addMember(createPerson("Salah", "mo@gmail.com", getDateFromAge(22), "BU"));
		team1.setCoach(createPerson("M.Salah", "mo.salah@gmail.com", getDateFromAge(23), "BU"));
		return team1;
	}
	
	private Team instantiateDifferentTeam() {
		Team team1 = createteam("Team3", 3);
		team1.setId((long) 3);
		team1.addMember(createPerson("Amr3", "amr3@gmail.com", getDateFromAge(18), "BU"));
		team1.addMember(createPerson("Tomas3", "tomas3@gmail.com", getDateFromAge(23), "BU"));
		team1.addMember(createPerson("Salah3", "mo3@gmail.com", getDateFromAge(22), "BU"));
		team1.setCoach(createPerson("M.Salah3", "mo3.salah@gmail.com", getDateFromAge(23), "BU"));
		return team1;
	}
	
	private Team instantiateDifferentTeam2() {
		Team team1 = createteam("Team4", 3);
		team1.setId((long) 4);
		team1.addMember(createPerson("Amr4", "amr4@gmail.com", getDateFromAge(18), "BU"));
		team1.addMember(createPerson("Tomas4", "tomas4@gmail.com", getDateFromAge(23), "BU"));
		team1.addMember(createPerson("Salah4", "mo4@gmail.com", getDateFromAge(22), "BU"));
		team1.setCoach(createPerson("M.Salah4", "mo4.salah@gmail.com", getDateFromAge(23), "BU"));
		return team1;
	}

	private Date getDateFromAge(int age) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1 * age);
		return c.getTime();
	}
	
}
