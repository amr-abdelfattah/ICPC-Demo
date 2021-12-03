package edu.baylor.swe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.models.Team.State;
import edu.baylor.swe.repositories.ContestRepository;
import edu.baylor.swe.repositories.TeamRepository;
import edu.baylor.swe.services.ContestService.ContestCapacity;
import edu.baylor.swe.validations.ContestValidator;
import edu.baylor.swe.validations.TeamValidator;

@Service
public class TeamService {
	
	@Autowired
	ContestRepository contestRepo;
	
	@Autowired
	TeamRepository teamRepo;
	
	@Autowired
	TeamValidator teamValidator;
	
	@Autowired
	private ContestValidator contestValidator;
	
	@Autowired
	private ContestService contestService;
	
	public TeamService(ContestService contestService, ContestRepository contestRepo, TeamRepository teamRepo, TeamValidator teamValidator, ContestValidator contestValidator) {
		this.contestService = contestService;
		this.contestRepo = contestRepo;
		this.teamRepo = teamRepo;
		this.teamValidator = teamValidator;
		this.contestValidator = contestValidator;
	}
	
	public Optional<List<Team>> teams(String contestName) {
		Optional<Contest> contest = contestRepo.findByName(contestName);
		if (contest.isPresent()) {
			return Optional.ofNullable(contest.get().getAttendedTeams());
		}
		return Optional.empty();
	}
	
	public Iterable<Team> teams() {
		return teamRepo.findAll();
	}
	
	public Optional<Team> promote(Long superContestId, Long teamId) {
		Optional<Team> team = teamRepo.findById(teamId);
		if (team.isPresent()) {
			if (isAllowedPromoteRank(team.get().getRank())) {
				Team promotedTeam = new Team(team.get(), team.get().getName());
				Optional<Contest> superContest = contestRepo.findById(superContestId);
				if (superContest.isPresent()) {
					superContest.get().addTeam(promotedTeam);
					validate(superContest.get(), promotedTeam);
					contestRepo.save(superContest.get());
					return Optional.ofNullable(promotedTeam);
				}
			}
		}
		return Optional.empty();
	}
	
	private boolean isAllowedPromoteRank(int rank) {
		return rank >= 1 && rank <= 5;
	}
	
	public Optional<Team> editTeam(Team updatedTeam) {
		Optional<Team> team = teamRepo.findById(updatedTeam.getId());
		if (team.isPresent() && team.get().getAttendedContest().isWritable()) {
			team.get().setRank(updatedTeam.getRank());
			if (updatedTeam.getAttendedContest() != null) {
				team.get().setAttendedContest(updatedTeam.getAttendedContest());
			}
			if (updatedTeam.getName() != null) {
				team.get().setName(updatedTeam.getName());
			}
			if (updatedTeam.getCoach() != null) {
				team.get().setCoach(updatedTeam.getCoach());
			}
			if (updatedTeam.getMembers() != null) {
				team.get().setMembers(updatedTeam.getMembers());
			}
			validate(null, team.get());
			return Optional.ofNullable(teamRepo.save(team.get()));
		}
		return Optional.empty();
	}
	
	public Optional<Team> changeTeamState(Long teamID, Team.State newState) {
		Optional<Team> team = teamRepo.findById(teamID);
		if (team.isPresent() 
				&& canChangeTeamState(team.get())) {
			team.get().setState(newState);
			return Optional.ofNullable(teamRepo.save(team.get()));
		}
		return Optional.empty();
	}
	
	private boolean canChangeTeamState(Team team) {
		Contest contest = team.getAttendedContest();
		ContestCapacity contestCapacity = contestService.contestCapacity(contest.getName()).get();
		boolean isContestReadOnly = !contest.isWritable();
		boolean isFullContestAndCancelledTeam = (contestCapacity.getCapacity() == contestCapacity.getCurrentOccupancy()
				&& team.getState() == State.Cancelled);
		return !(isContestReadOnly || isFullContestAndCancelledTeam);
	}
	
	private void validate(Contest contest, Team team) {
		if (contest != null) {
			contestValidator.validate(contest, null);
		}
		if (team != null) {
			teamValidator.validate(team, null);
		}
	}
	
}
