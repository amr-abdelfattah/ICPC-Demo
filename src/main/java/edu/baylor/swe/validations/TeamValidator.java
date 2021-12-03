package edu.baylor.swe.validations;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.baylor.swe.models.Person;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.repositories.PersonRepository;

@Component("beforeSaveTeamValidator")
public class TeamValidator implements Validator {

	@Autowired
	private PersonRepository personRepo;
	
	public TeamValidator(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Team.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Team team = (Team) target;
        if (!checkCoachAvailability(team)) {
            errors.rejectValue("coach", "coach.empty");
        }
   
        if (!checkMembers(team)) {
            errors.rejectValue("members", "members.invalid");
        }
        
//        if (!checkWritable(team)) {
//            errors.rejectValue("attendedContest.isWritable", "attendedContest.isWritable");
//        }
	}
	
	private boolean checkCoachAvailability(Team team) {
		return team.getCoach() != null;
	}
	
	private boolean checkMembers(Team team) {
		int numberMembers = 3;
		boolean isThreeMembers = team.getMembers() != null && team.getMembers().size() == numberMembers;
		boolean areYoungerMembers = true;
		boolean isParticipatingInThisTeamOnly = true;
		if (isThreeMembers) {
			Set<String> members = new HashSet<String>();
			for (Person member : team.getMembers()) {
				members.add(member.getName()+member.getEmail()+member.getAge());
				isParticipatingInThisTeamOnly &= personRepo.findByNameAndEmail(member.getName(), member.getEmail(), team.getAttendedContest().getId()).isEmpty();
				System.err.println(member.getName() + "  " + isParticipatingInThisTeamOnly);
				int age = member.getAge();
				areYoungerMembers &= age < 24;
			}
			isThreeMembers &= members.size() == numberMembers;
			System.err.println(isThreeMembers + "   " + areYoungerMembers +"   " + isParticipatingInThisTeamOnly );
			return isThreeMembers && areYoungerMembers && isParticipatingInThisTeamOnly;
		}
		return false;
	}
	
//	private boolean checkWritable(Team team) {
//		return team.getAttendedContest().isWritable();
//	}
}
