package edu.baylor.swe.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.repositories.ContestRepository;
import edu.baylor.swe.validations.ContestValidator;
import edu.baylor.swe.validations.TeamValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
public class ContestService {

	@Autowired
	private ContestRepository contestRepo;
	
	@Autowired
	private TeamValidator teamValidator; 
	
	@Autowired
	private ContestValidator contestValidator;
	
	public ContestService(ContestRepository contestRepo, TeamValidator teamValidator, ContestValidator contestValidator) {
		this.contestRepo = contestRepo;
		this.teamValidator = teamValidator;
		this.contestValidator = contestValidator;
	}
	
	public List<Contest> allContests() {
		Iterable<Contest> allContests = contestRepo.findAll();
		return ((List<Contest>) IteratorUtils.toList(allContests.iterator()))
				.stream()
			    .filter(c -> !c.isDeleted())
			    .collect(Collectors.toList());
	}
	
	public Contest saveContest(Contest contest) {
		return contestRepo.save(contest);
	}
	
	public void delete(long id) {
		Contest c = new Contest();
		c.setId(id);
		c.setDeleted(true);
		edit(c);
	}
	
	public Optional<ContestCapacity> contestCapacity(String contestName) {
		Optional<Contest> contest = contestRepo.findByName(contestName);
		if (contest.isPresent()) {
			int currentOccupancy = contest.get().getNotCancelledTeams().size();
			int capacity = contest.get().getCapacity();
			return Optional.ofNullable(new ContestCapacity(currentOccupancy, capacity));
		}
		return Optional.empty();
	}
	
	public Optional<Contest> registration(Long contestId, Team team) {
		Optional<Contest> contest = contestRepo.findById(contestId);
		if (contest.isPresent()) {
			contest.get().addTeam(team);
			validate(contest.get(), team);
			return Optional.of(contestRepo.save(contest.get()));
		}
		return Optional.empty();
	}
	 
	public Optional<Contest> edit(Contest c) {
		
		Optional<Contest> contest = contestRepo.findById(c.getId());
		if (contest.isPresent() && contest.get().isWritable()) {
			contest.get().setCapacity(c.getCapacity());
			if (!c.getAttendedTeams().isEmpty()) {
				contest.get().setAttendedTeams(c.getAttendedTeams());
			}
			if (!c.getContests().isEmpty()) {
				contest.get().setContests(c.getContests());
			}
			if (!c.getManagers().isEmpty()) {
				contest.get().setManagers(c.getManagers());
			}
			if (c.getName() != null) {
				contest.get().setName(c.getName());
			}
			if (c.getDate() != null) {
				contest.get().setDate(c.getDate());
			}
			if (c.getRegistrationFrom() != null) {
				contest.get().setRegistrationFrom(c.getRegistrationFrom());
			}
			if (c.getRegistrationTo() != null) {
				contest.get().setRegistrationTo(c.getRegistrationTo());
			}
			if (c.isDeleted()) {
				contest.get().setDeleted(true);
			}
			validate(contest.get(), null);
			return Optional.of(contestRepo.save(contest.get()));
		}
		return Optional.empty();
	}
	
	public Optional<Contest> setEditable(Contest c) {
		return setEditable(c, true);
	}
	
	public Optional<Contest> setReadOnly(Contest c) {
		return setEditable(c, false);
	}
	
	public Optional<Contest> setEditable(String contestName) {
		Contest c = contestRepo.findByName(contestName).get();
		return setEditable(c);
	}
	
	public Optional<Contest> setReadOnly(String contestName) {
		Contest c = contestRepo.findByName(contestName).get();
		return setReadOnly(c);
	}
	
	public Optional<Contest> setEditable(Contest c, boolean isEditable) {
		Optional<Contest> contest = contestRepo.findById(c.getId());
		if (contest.isPresent()) {
			contest.get().setWritable(isEditable);
			return Optional.of(contestRepo.save(contest.get()));
		}
		return Optional.empty();
	}
	
	private void validate(Contest contest, Team team) {
		if (contest != null) {
			contestValidator.validate(contest, null);
		}
		if (team != null) {
			teamValidator.validate(team, null);
		}
	}
	
	@AllArgsConstructor
	@Data
	public class ContestCapacity {
		private int currentOccupancy;
		private int capacity;
	}
}
